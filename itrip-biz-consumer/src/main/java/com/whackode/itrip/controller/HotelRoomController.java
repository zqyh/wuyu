package com.whackode.itrip.controller;

import com.whackode.itrip.base.controller.BaseController;
import com.whackode.itrip.base.pojo.vo.ResponseDto;
import com.whackode.itrip.pojo.entity.HotelRoom;
import com.whackode.itrip.pojo.vo.SearchHotelRoomVO;
import com.whackode.itrip.transport.HotelRoomTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	/**
	 * <b>查询酒店房间列表-此刻可以预定的房间列表</b>
	 * @param searchHotelRoomVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/queryhotelroombyhotel")
	public ResponseDto<Object> queryHotelRoombyHotel(@RequestBody SearchHotelRoomVO searchHotelRoomVO) throws Exception {
		// 查询可用酒店房间列表
		List<HotelRoom> hotelRoomList = hotelRoomTransport.queryHotelRoomByHotel(searchHotelRoomVO);
		return ResponseDto.success(hotelRoomList);
	}
}
