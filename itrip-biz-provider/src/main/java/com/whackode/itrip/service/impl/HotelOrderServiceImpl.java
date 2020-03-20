package com.whackode.itrip.service.impl;

import com.whackode.itrip.dao.HotelOrderDao;
import com.whackode.itrip.pojo.entity.HotelOrder;
import com.whackode.itrip.service.HotelOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("hotelOrderService")
@Transactional
public class HotelOrderServiceImpl implements HotelOrderService {
	@Autowired
	private HotelOrderDao hotelOrderDao;

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
}
