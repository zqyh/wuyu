package com.whackode.itrip.dao;

import com.whackode.itrip.pojo.entity.HotelOrder;
import com.whackode.itrip.pojo.vo.ListHotelOrderVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <b>爱旅行-酒店订单数据持久层接口</b>
 * @author Arthur
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface HotelOrderDao {
	/**
	 * <b>根据查询条件查询未支付和已支付的订单中所下单的房间总数</b>
	 * @param queryMap
	 * @return
	 * @throws Exception
	 */
	Integer findOrderRoomCountByQuery(Map<String, Object> queryMap) throws Exception;

	/**
	 * <b>根据查询条件获得列表信息</b>
	 * @param query
	 * @return
	 * @throws Exception
	 */
	List<HotelOrder> findHotelOrderListByQuery(HotelOrder query) throws Exception;

	/**
	 * <b>保存订单对象</b>
	 * @param hotelOrder
	 * @return
	 * @throws Exception
	 */
	int save(HotelOrder hotelOrder) throws Exception;

	/**
	 * <b>修改订单对象</b>
	 * @param hotelOrder
	 * @return
	 * @throws Exception
	 */
	int update(HotelOrder hotelOrder) throws Exception;

	/***
	 * 获取订单数量
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Integer getOrderCountByMap(Map<String,Object> param)throws Exception;

	/**
	 * 获取订单列表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<ListHotelOrderVO> getOrderListByMap(Map<String,Object> param)throws Exception;

	/**
	 * 根据订单ID获取订单信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public HotelOrder getHotelOrderById(@Param(value = "id") Long id)throws Exception;
}
