package com.whackode.itrip.controller;

import com.whackode.itrip.base.controller.BaseController;
import com.whackode.itrip.base.enums.ImageTypeEnum;
import com.whackode.itrip.base.pojo.vo.ResponseDto;
import com.whackode.itrip.pojo.entity.HotelRoom;
import com.whackode.itrip.pojo.entity.ItripImage;
import com.whackode.itrip.pojo.entity.LabelDic;
import com.whackode.itrip.pojo.vo.SearchHotelRoomVO;
import com.whackode.itrip.transport.HotelRoomTransport;
import com.whackode.itrip.transport.ItripImageTransport;
import com.whackode.itrip.transport.LabelDicTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>爱旅行-酒店房间控制器</b>
 * @author Arthur
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController("hotelRoomController")
@RequestMapping("/biz/api/hotelroom")
public class HotelRoomController extends BaseController {
	@Autowired
	private HotelRoomTransport hotelRoomTransport;
	@Autowired
	private LabelDicTransport labelDicTransport;
	@Autowired
	private ItripImageTransport itripImageTransport;

	/**
	 * <b>查询酒店房间列表-此刻可以预定的房间列表</b>
	 * @param searchHotelRoomVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/queryhotelroombyhotel")
	public ResponseDto<Object> queryHotelRoombyHotel(@RequestBody SearchHotelRoomVO searchHotelRoomVO) throws Exception {
		List<List<HotelRoom>> resultList = new ArrayList<List<HotelRoom>>();
		// 查询可用酒店房间列表
		List<HotelRoom> hotelRoomList = hotelRoomTransport.queryHotelRoomByHotel(searchHotelRoomVO);
		// 循环遍历集合
		if (hotelRoomList != null && hotelRoomList.size() > 0) {
			for (HotelRoom hotelRoom : hotelRoomList) {
				List<HotelRoom> list = new ArrayList<HotelRoom>();
				list.add(hotelRoom);
				resultList.add(list);
			}
		}
		return ResponseDto.success(resultList);
	}

	/**
	 * <b>查询酒店房间床型列表</b>
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/queryhotelroombed")
	public ResponseDto<Object> queryHotelRoombed() throws Exception {
		// 封装查询对象
		LabelDic query = new LabelDic();
		// 查询所有的床型
		query.setParentId(1L);
		return ResponseDto.success(labelDicTransport.getListByQuery(query));
	}

	/**
	 * <b>根据targetId查询酒店房型图片(type=1)</b>
	 * @param targetId
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/getimg/{targetId}")
	public ResponseDto<Object> getImgForHotel(@PathVariable("targetId") Long targetId) throws Exception {
		ItripImage query = new ItripImage();
		query.setTargetId(targetId);
		query.setType(String.valueOf(ImageTypeEnum.IMAGE_TYPE_HOTEL.getCode()));

		List<ItripImage> itripImageList = itripImageTransport.getItripImageListByQuery(query);

		return ResponseDto.success(itripImageList);
	}
}
