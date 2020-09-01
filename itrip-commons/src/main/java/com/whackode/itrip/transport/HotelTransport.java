package com.whackode.itrip.transport;

import com.whackode.itrip.pojo.entity.Hotel;
import com.whackode.itrip.pojo.vo.SearchHotCityVO;
import com.whackode.itrip.pojo.vo.SearchHotelVO;
import com.whackode.itrip.util.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <b>爱旅行-酒店模块传输层接口</b>
 * @author Arthur
 * @version 1.0.0
 * @since 1.0.0
 */
@FeignClient(name = "itrip-biz-provider")
@RequestMapping("/hotel/trans")
public interface HotelTransport {

	/**
	 * <b>根据热门城市查询酒店</b>
	 * @param queryVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/searchItripHotelListByHotCity")
	List<SearchHotelVO> searchItripHotelListByHotCity(@RequestBody SearchHotCityVO queryVO) throws Exception;

	/**
	 * <b>根据主键查询对象信息</b>
	 * @param hotelId
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/id")
	Hotel getHotelById(@RequestParam Long hotelId) throws Exception;

	@PostMapping(value = "/searchItripHotelPage")
	public Page<SearchHotelVO> searchItripHotelPage(@RequestBody SearchHotelVO vo)throws Exception;
}
