package com.whackode.itrip.transport;

import com.whackode.itrip.pojo.entity.HotelRoom;
import com.whackode.itrip.pojo.vo.SearchHotelRoomVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * <b>爱旅行-酒店房间模块传输层接口</b>
 * @author Arthur
 * @version 1.0.0
 * @since 1.0.0
 */
@FeignClient(name = "itrip-biz-provider")
@RequestMapping("/hotelroom/trans")
public interface HotelRoomTransport {
	/**
	 * <b>查询酒店房间列表-此刻可以预定的房间列表</b>
	 * @param searchHotelRoomVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/queryhotelroombyhotel")
	List<HotelRoom> queryHotelRoomByHotel(@RequestBody SearchHotelRoomVO searchHotelRoomVO) throws Exception;
}
