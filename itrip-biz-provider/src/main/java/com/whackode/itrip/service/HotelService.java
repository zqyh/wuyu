package com.whackode.itrip.service;

import com.whackode.itrip.pojo.entity.Hotel;
import com.whackode.itrip.pojo.vo.HotelVO;
import com.whackode.itrip.pojo.vo.SearchHotCityVO;

import java.util.List;

/**
 * <b>爱旅行-酒店模块业务层接口</b>
 * @author Arthur
 * @version 1.0.0
 * @since 1.0.0
 */
public interface HotelService {
	/**
	 * <b>根据热门城市查询酒店</b>
	 * @param queryVO
	 * @return
	 * @throws Exception
	 */
	List<HotelVO> searchItripHotelListByHotCity(SearchHotCityVO queryVO) throws Exception;

	/**
	 * <b>根据主键查询对象信息</b>
	 * @param hotelId
	 * @return
	 */
	Hotel getHotelById(Long hotelId) throws Exception;
}
