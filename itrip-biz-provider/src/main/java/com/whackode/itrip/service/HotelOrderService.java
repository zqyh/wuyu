package com.whackode.itrip.service;

import com.whackode.itrip.pojo.entity.HotelOrder;
import com.whackode.itrip.pojo.vo.ListHotelOrderVO;
import com.whackode.itrip.util.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface HotelOrderService {
	List<HotelOrder> getListByQuery(HotelOrder query) throws Exception;

	boolean save(HotelOrder hotelOrder) throws Exception;

	boolean update(HotelOrder hotelOrder) throws Exception;

	HotelOrder getHotelOrderById(Long orderId) throws Exception;

	HotelOrder getHotelOrderByNo(String orderNo) throws Exception;

	public Page<ListHotelOrderVO> queryOrderPageByMap(Map<String,Object> param, Integer pageNo, Integer pageSize)throws Exception;

	/**
	 * 根据订单的预定天数和房间的单价计算订单总金额 -add by donghai
	 * @param count ,roomId count为天数和房间数量的乘积
	 * @return
	 * @throws Exception
	 */
	public BigDecimal getOrderPayAmount(int count, Long roomId) throws Exception;
}
