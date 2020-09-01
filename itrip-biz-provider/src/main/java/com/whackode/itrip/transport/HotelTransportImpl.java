package com.whackode.itrip.transport;

import com.whackode.itrip.pojo.entity.Hotel;
import com.whackode.itrip.pojo.vo.SearchHotCityVO;
import com.whackode.itrip.pojo.vo.SearchHotelVO;
import com.whackode.itrip.service.HotelService;
import com.whackode.itrip.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b>爱旅行-酒店模块传输层接口实现类</b>
 * @author Arthur
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController("hotelTransport")
@RequestMapping("/hotel/trans")
public class HotelTransportImpl implements HotelTransport {
	@Autowired
	private HotelService hotelService;

	/**
	 * <b>根据热门城市查询酒店</b>
	 * @param queryVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/searchItripHotelListByHotCity")
	public List<SearchHotelVO> searchItripHotelListByHotCity(@RequestBody SearchHotCityVO queryVO)
			throws Exception {
		return hotelService.searchItripHotelListByHotCity(queryVO);
	}

	/**
	 * <b>根据主键查询对象信息</b>
	 * @param hotelId
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/id")
	public Hotel getHotelById(@RequestParam Long hotelId) throws Exception {
		return hotelService.getHotelById(hotelId);
	}

	@PostMapping(value = "/searchItripHotelPage")
	@Override
	public Page<SearchHotelVO> searchItripHotelPage(@RequestBody SearchHotelVO vo) throws Exception {
		return hotelService.searchItripHotelPage(vo);
	}
}
