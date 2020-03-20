package com.whackode.itrip.service;

import com.whackode.itrip.pojo.entity.HotelOrder;

import java.util.List;

public interface HotelOrderService {
	List<HotelOrder> getListByQuery(HotelOrder query) throws Exception;

	boolean save(HotelOrder hotelOrder) throws Exception;

	boolean update(HotelOrder hotelOrder) throws Exception;

	HotelOrder getHotelOrderById(Long orderId) throws Exception;

	HotelOrder getHotelOrderByNo(String orderNo) throws Exception;
}
