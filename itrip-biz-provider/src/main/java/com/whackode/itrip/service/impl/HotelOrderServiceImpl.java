package com.whackode.itrip.service.impl;

import com.whackode.itrip.dao.HotelOrderDao;
import com.whackode.itrip.dao.HotelRoomDao;
import com.whackode.itrip.pojo.entity.HotelOrder;
import com.whackode.itrip.pojo.entity.HotelRoom;
import com.whackode.itrip.pojo.vo.ListHotelOrderVO;
import com.whackode.itrip.service.HotelOrderService;
import com.whackode.itrip.util.BigDecimalUtil;
import com.whackode.itrip.util.EmptyUtils;
import com.whackode.itrip.util.Page;
import com.whackode.itrip.util.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.math.BigDecimal.ROUND_DOWN;

@Service("hotelOrderService")
@Transactional
public class HotelOrderServiceImpl implements HotelOrderService {
	@Autowired
	private HotelOrderDao hotelOrderDao;
	@Autowired
	private HotelRoomDao hotelRoomDao;

	public List<HotelOrder> getListByQuery(HotelOrder query) throws Exception {
		List<HotelOrder> hotelOrderList = hotelOrderDao.findHotelOrderListByQuery(query);
		if (hotelOrderList != null) {
			return hotelOrderList;
		}
		return new ArrayList<HotelOrder>();
	}

	public boolean save(HotelOrder hotelOrder) throws Exception {
		int count = hotelOrderDao.save(hotelOrder);
		if (count > 0) {
			return true;
		}
		return false;
	}

	public boolean update(HotelOrder hotelOrder) throws Exception {
		int count = hotelOrderDao.update(hotelOrder);
		if (count > 0) {
			return true;
		}
		return false;
	}

	public HotelOrder getHotelOrderById(Long orderId) throws Exception {
		HotelOrder query = new HotelOrder();
		query.setId(orderId);

		List<HotelOrder> hotelOrderList = hotelOrderDao.findHotelOrderListByQuery(query);
		if (hotelOrderList != null && hotelOrderList.size() > 0) {
			return hotelOrderList.get(0);
		}
		return null;
	}

	public HotelOrder getHotelOrderByNo(String orderNo) throws Exception {
		HotelOrder query = new HotelOrder();
		query.setOrderNo(orderNo);

		List<HotelOrder> hotelOrderList = hotelOrderDao.findHotelOrderListByQuery(query);
		if (hotelOrderList != null && hotelOrderList.size() > 0) {
			return hotelOrderList.get(0);
		}
		return null;
	}

	@Override
	public Page<ListHotelOrderVO> queryOrderPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
		Integer total = hotelOrderDao.getOrderCountByMap(param);
		pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
		pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
		Page page = new Page(pageNo, pageSize, total);
		param.put("beginPos", page.getBeginPos());
		param.put("pageSize", page.getPageSize());
		List<ListHotelOrderVO> itripHotelOrderList = hotelOrderDao.getOrderListByMap(param);
		page.setRows(itripHotelOrderList);
		return page;
	}

	@Override
	public BigDecimal getOrderPayAmount(int count, Long roomId) throws Exception {
		//组装条件
		HotelRoom hotelRoom =new HotelRoom();
		hotelRoom.setId(roomId);

		BigDecimal payAmount = null;
		BigDecimal roomPrice = hotelRoomDao.findListByQuery(hotelRoom).get(0).getRoomPrice();
		payAmount = BigDecimalUtil.OperationASMD(count, roomPrice,
				BigDecimalUtil.BigDecimalOprations.multiply,
				2, ROUND_DOWN);
		return payAmount;
	}
}
