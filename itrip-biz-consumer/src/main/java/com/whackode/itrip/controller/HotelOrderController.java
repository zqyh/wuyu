package com.whackode.itrip.controller;

import com.alibaba.fastjson.JSONArray;
import com.whackode.itrip.base.controller.BaseController;
import com.whackode.itrip.base.enums.OrderStatusEnum;
import com.whackode.itrip.pojo.dto.Dto;
import com.whackode.itrip.pojo.entity.*;
import com.whackode.itrip.pojo.vo.*;
import com.whackode.itrip.transport.HotelOrderTransport;
import com.whackode.itrip.transport.HotelRoomTransport;
import com.whackode.itrip.transport.HotelTransport;
import com.whackode.itrip.transport.UserTransport;
import com.whackode.itrip.util.*;
import com.whackode.itrip.util.constant.Constants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>爱旅行-主业务酒店订单模块控制器</b>
 * @author Arthur
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController("hotelOrderController")
@RequestMapping("/biz/api/hotelorder")
public class HotelOrderController extends BaseController {
	@Autowired
	private HotelTransport hotelTransport;
	@Autowired
	private HotelRoomTransport hotelRoomTransport;
	@Autowired
	private HotelOrderTransport hotelOrderTransport;
	@Autowired
	private UserTransport userTransport;
	@Autowired
    private RedisUtils redisUtils;
	@Autowired
	ValidationToken validationToken;

	/**
	 * <b>生成订单前,获取预订信息</b>
	 * @param validateRoomStoreVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/getpreorderinfo", produces = "application/json")
	public Dto<RoomStoreVO> getPreOrderInfo(@RequestBody ValidateRoomStoreVO validateRoomStoreVO) throws Exception {
		RoomStoreVO roomStoreVO = new RoomStoreVO();

		String tokenString = request.getHeader("token");
		User currentUser = validationToken.getCurrentUser(tokenString);

		if (EmptyUtils.isEmpty(currentUser)) {
			return DtoUtil.returnFail("token失效，请重登录", Constants.ErrorCode.AUTH_TOKEN_INVALID);
		}

		// 根据 hotelId 查询对应的 Hotel 对象
		Hotel hotel = hotelTransport.getHotelById(validateRoomStoreVO.getHotelId());
		roomStoreVO.setHotelId(hotel.getId());
		roomStoreVO.setHotelName(hotel.getHotelName());

		// 根据 roomId 查询对应的 HotelRoom 对象
		HotelRoom hotelRoom = hotelRoomTransport.queryHotelRoomById(validateRoomStoreVO.getRoomId());
		roomStoreVO.setRoomId(hotelRoom.getId());
		roomStoreVO.setPrice(hotelRoom.getRoomPrice());

		// 根据入住时间和退房时间，查询该房间所剩数量
		int store = hotelRoomTransport.queryHotelRoomStoreByDate(validateRoomStoreVO);
		roomStoreVO.setStore(store);

		roomStoreVO.setCheckInDate(validateRoomStoreVO.getCheckInDate());
		roomStoreVO.setCheckOutDate(validateRoomStoreVO.getCheckOutDate());
		roomStoreVO.setCount(validateRoomStoreVO.getCount());

		return DtoUtil.returnSuccess("获取成功", roomStoreVO);
	}

	/**
	 * <b>生成订单</b>
	 * @param addHotelOrderVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/addhotelorder", produces = "application/json")
	public Dto<Object> addHotelOrder(@RequestBody AddHotelOrderVO addHotelOrderVO) throws Exception {
		Dto<Object> dto = new Dto<Object>();
		String tokenString = request.getHeader("token");
		User currentUser = validationToken.getCurrentUser(tokenString);

		if (EmptyUtils.isEmpty(currentUser)) {
			return DtoUtil.returnFail("token失效，请重登录", Constants.ErrorCode.AUTH_TOKEN_INVALID);
		}

		// 查询此时是否有房
		ValidateRoomStoreVO validateRoomStoreVO = new ValidateRoomStoreVO();
		BeanUtils.copyProperties(addHotelOrderVO, validateRoomStoreVO);
		int store = hotelRoomTransport.queryHotelRoomStoreByDate(validateRoomStoreVO);

		if (store >= addHotelOrderVO.getCount()) {
			//计算订单的预定天数
			Integer days = DateUtil.getBetweenDates(
					addHotelOrderVO.getCheckInDate(), addHotelOrderVO.getCheckOutDate()
			).size()-1;

			if(days<=0){
				return DtoUtil.returnFail("退房日期必须大于入住日期", "100505");
			}

			// 有房的情况下，保存订单数据表
			HotelOrder hotelOrder = new HotelOrder();
			hotelOrder.setUserId(currentUser.getId());
			BeanUtils.copyProperties(addHotelOrderVO, hotelOrder);
			String orderNo = HotelOrderNoCreator.createHotelOrderNo(addHotelOrderVO.getHotelId(), addHotelOrderVO.getRoomId());
			hotelOrder.setOrderNo(orderNo);
			// 交易编号
			hotelOrder.setTradeNo(orderNo);
			// 订单状态，未支付为0
			hotelOrder.setOrderStatus(OrderStatusEnum.ORDER_STATUS_PREPAY.getCode());
			// 订单价格
			HotelRoom hotelRoom = hotelRoomTransport.queryHotelRoomById(addHotelOrderVO.getRoomId());
			hotelOrder.setPayAmount(hotelOrderTransport.getOrderPayAmount(days*addHotelOrderVO.getCount(),
					addHotelOrderVO.getRoomId()));

			// 添加联系人信息
			List<UserLinkUser> userLinkUserList = addHotelOrderVO.getLinkUser();
			StringBuffer sb = new StringBuffer();
			for (UserLinkUser userLinkUser : userLinkUserList) {
				sb.append(userLinkUser.getLinkUserName() + ",");
			}
			hotelOrder.setLinkUserName(sb.toString().substring(0, sb.toString().length() - 1));
			hotelOrder.setBookingDays(days);

			if (tokenString.startsWith("token:PC")) {
				hotelOrder.setBookType(0);
			} else if (tokenString.startsWith("token:MOBILE")) {
				hotelOrder.setBookType(1);
			} else {
				hotelOrder.setBookType(2);
			}

			hotelOrder.setCreationDate(new Date());

			// 保存订单
			hotelOrderTransport.save(hotelOrder);
			// 获得 HotelOrder 对象的 id 和 OrderId 添加为 Map 集合
			// 查询对象
			HotelOrder query = new HotelOrder();
			query.setOrderNo(orderNo);
			HotelOrder order = hotelOrderTransport.getHotelOrderByNo(orderNo);

			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("id", order.getId());
			resultMap.put("orderNo", order.getOrderNo());

			dto = DtoUtil.returnSuccess("生成订单成功", resultMap);
		}else {
			dto = DtoUtil.returnFail("库存不足", "100507");
		}
		return dto;
	}

	@PostMapping(value = "/getpersonalorderlist", produces = "application/json")
	public Dto<Object> getPersonalOrderList(@RequestBody SearchOrderVO itripSearchOrderVO){
		Integer orderType = itripSearchOrderVO.getOrderType();
		Integer orderStatus = itripSearchOrderVO.getOrderStatus();
		Dto<Object> dto = null;

		//获取token
        String tokenString = request.getHeader("token");
        User currentUser = validationToken.getCurrentUser(tokenString);

		if (null != currentUser) {
			if (orderType == null) {
				return DtoUtil.returnFail("请传递参数：orderType", "100501");
			}
			if (orderStatus == null) {
				return DtoUtil.returnFail("请传递参数：orderStatus", "100502");
			}

			Map<String, Object> param = new HashMap<>();
			param.put("orderType", orderType == -1 ? null : orderType);
			param.put("orderStatus", orderStatus == -1 ? null : orderStatus);
			param.put("userId", currentUser.getId());
			param.put("orderNo", itripSearchOrderVO.getOrderNo());
			param.put("linkUserName", itripSearchOrderVO.getLinkUserName());
			param.put("startDate", itripSearchOrderVO.getStartDate());
			param.put("endDate", itripSearchOrderVO.getEndDate());
			try {
				Page<ListHotelOrderVO> page = hotelOrderTransport.getPersonalOrderList(param,
						itripSearchOrderVO.getPageNo(),
						itripSearchOrderVO.getPageSize());
				dto = DtoUtil.returnSuccess("获取个人订单列表成功", page);
			} catch (Exception e) {
				e.printStackTrace();
				dto = DtoUtil.returnFail("获取个人订单列表错误", "100503");
			}

		} else {
			dto = DtoUtil.returnFail("token失效，请重登录", "100000");
		}
		return dto;
	}

	@GetMapping(value="/getpersonalorderinfo/{orderId}",produces = "application/json")
    public Dto<Object> getPersonalOrderInfo(@PathVariable String orderId) {
        Dto<Object> dto = null;
        //获取token
        String tokenString = request.getHeader("token");
        User currentUser = validationToken.getCurrentUser(tokenString);

        if (null != currentUser){
            if (null == orderId || "".equals(orderId)) {
                return DtoUtil.returnFail("请传递参数：orderId", "100525");
            }

            try {
				//获得订单信息
				HotelOrder hotelOrder=hotelOrderTransport.getHotelOrderById(Long.valueOf(orderId));

				if(hotelOrder !=null){
					PersonalHotelOrderVO personalHotelOrderVO = new PersonalHotelOrderVO();
					personalHotelOrderVO.setId(hotelOrder.getId());
					personalHotelOrderVO.setBookType(hotelOrder.getBookType());
					personalHotelOrderVO.setCreationDate(hotelOrder.getCreationDate());
					personalHotelOrderVO.setOrderNo(hotelOrder.getOrderNo());

					//查询预订房间的信息
					HotelRoom hotelRoom=hotelRoomTransport.queryHotelRoomById(hotelOrder.getRoomId());

					if(EmptyUtils.isEmpty(hotelRoom)){
						personalHotelOrderVO.setRoomPayType(hotelRoom.getPayType());
					}

					Integer orderStatus = hotelOrder.getOrderStatus();
					personalHotelOrderVO.setOrderStatus(orderStatus);
					//订单状态（0：待支付 1:已取消 2:支付成功 3:已消费 4:已点评）
					//{"1":"订单提交","2":"订单支付","3":"支付成功","4":"入住","5":"订单点评","6":"订单完成"}
					//{"1":"订单提交","2":"订单支付","3":"订单取消"}
					if (orderStatus == 1) {
						personalHotelOrderVO.setOrderProcess(JSONArray.parse(Constants.OrderCode.ORDER_PROCESS_CANCEL));
						personalHotelOrderVO.setProcessNode("3");
					} else if (orderStatus == 0) {
						personalHotelOrderVO.setOrderProcess(JSONArray.parse(Constants.OrderCode.ORDER_PROCESS_OK));
						personalHotelOrderVO.setProcessNode("2");//订单支付
					} else if (orderStatus == 2) {
						personalHotelOrderVO.setOrderProcess(JSONArray.parse(Constants.OrderCode.ORDER_PROCESS_OK));
						personalHotelOrderVO.setProcessNode("3");//支付成功（未出行）
					} else if (orderStatus == 3) {
						personalHotelOrderVO.setOrderProcess(JSONArray.parse(Constants.OrderCode.ORDER_PROCESS_OK));
						personalHotelOrderVO.setProcessNode("5");//订单点评
					} else if (orderStatus == 4) {
						personalHotelOrderVO.setOrderProcess(JSONArray.parse(Constants.OrderCode.ORDER_PROCESS_OK));
						personalHotelOrderVO.setProcessNode("6");//订单完成
					} else {
						personalHotelOrderVO.setOrderProcess(null);
						personalHotelOrderVO.setProcessNode(null);
					}
					personalHotelOrderVO.setPayAmount(hotelOrder.getPayAmount());
					personalHotelOrderVO.setPayType(hotelOrder.getPayType());
					personalHotelOrderVO.setNoticePhone(hotelOrder.getNoticePhone());
					dto = DtoUtil.returnSuccess("获取个人订单信息成功", personalHotelOrderVO);

				}else {
					dto = DtoUtil.returnFail("没有相关订单信息", "100526");
				}

            }catch (Exception e){
                e.printStackTrace();
                dto = DtoUtil.returnFail("获取个人订单信息错误", "100527");
            }
        }else{
            dto = DtoUtil.returnFail("token失效，请重登录", "100000");
        }



        return dto;
    }
}
