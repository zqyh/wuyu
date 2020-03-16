package com.whackode.itrip.service;

import com.whackode.itrip.pojo.entity.HotelRoom;
import com.whackode.itrip.pojo.vo.SearchHotelRoomVO;

import java.util.List;

/**
 * <b>爱旅行-酒店房间模块业务层接口</b>
 * @author Arthur
 * @version 1.0.0
 * @since 1.0.0
 */
public interface HotelRoomService {
	/**
	 * <b>查询酒店房间列表-此刻可以预定的房间列表</b>
	 * @param searchHotelRoomVO
	 * @return
	 * @throws Exception
	 */
	List<HotelRoom> queryHotelRoomByHotel(SearchHotelRoomVO searchHotelRoomVO) throws Exception;
}
