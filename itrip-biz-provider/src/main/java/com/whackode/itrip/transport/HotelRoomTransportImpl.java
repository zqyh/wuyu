package com.whackode.itrip.transport;

import com.whackode.itrip.pojo.entity.HotelRoom;
import com.whackode.itrip.pojo.vo.SearchHotelRoomVO;
import com.whackode.itrip.pojo.vo.ValidateRoomStoreVO;
import com.whackode.itrip.service.HotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b>爱旅行-酒店房间模块传输层接口实现类</b>
 * @author Arthur
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController("hotelRoomTransport")
@RequestMapping("/hotelroom/trans")
public class HotelRoomTransportImpl implements HotelRoomTransport {
	@Autowired
	private HotelRoomService hotelRoomService;

	/**
	 * <b>查询酒店房间列表-此刻可以预定的房间列表</b>
	 * @param searchHotelRoomVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/queryhotelroombyhotel")
	public List<HotelRoom> queryHotelRoomByHotel(@RequestBody SearchHotelRoomVO searchHotelRoomVO) throws Exception {
		return hotelRoomService.queryHotelRoomByHotel(searchHotelRoomVO);
	}

	/**
	 * <b>根据房间主键查询房间信息</b>
	 * @param roomId
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/id")
	public HotelRoom queryHotelRoomById(@RequestParam Long roomId) throws Exception {
		return hotelRoomService.getHotelRoomById(roomId);
	}

	/**
	 * <b>根据查询条件获得可用房间数量</b>
	 * @param validateRoomStoreVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/store")
	public int queryHotelRoomStoreByDate(@RequestBody ValidateRoomStoreVO validateRoomStoreVO) throws Exception {
		return hotelRoomService.getHotelRoomStoreByDate(validateRoomStoreVO);
	}
}
