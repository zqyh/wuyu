package com.whackode.itrip.controller;

import com.whackode.itrip.base.controller.BaseController;
import com.whackode.itrip.base.pojo.vo.ResponseDto;
import com.whackode.itrip.pojo.dto.Dto;
import com.whackode.itrip.pojo.vo.SearchHotCityVO;
import com.whackode.itrip.pojo.vo.SearchHotelVO;
import com.whackode.itrip.transport.HotelTransport;
import com.whackode.itrip.util.DtoUtil;
import com.whackode.itrip.util.EmptyUtils;
import com.whackode.itrip.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <b>爱旅行-搜索模块控制器</b>
 * @author Arthur
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController("searchController")
@RequestMapping("/search/api")
public class SearchController extends BaseController {
	@Autowired
	private HotelTransport hotelTransport;

	/**
	 * <b>根据热门城市查询酒店</b>
	 * @param queryVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/hotellist/searchItripHotelListByHotCity")
	public ResponseDto<Object> searchItripHotelListByHotCity(@RequestBody SearchHotCityVO queryVO)
			throws Exception {
		List<SearchHotelVO> hotelList = hotelTransport.searchItripHotelListByHotCity(queryVO);
		return ResponseDto.success(hotelList);
	}

	@PostMapping(value = "/hotellist/searchItripHotelPage", produces = "application/json")
	public Dto<Page<SearchHotelVO>> searchItripHotelPage(@RequestBody SearchHotelVO vo) {
		Page page = null;
		if (EmptyUtils.isEmpty(vo) || EmptyUtils.isEmpty(vo.getDestination())) {
			return DtoUtil.returnFail("目的地不能为空", "20002");
		}
		try {
			page = hotelTransport.searchItripHotelPage(vo);
		} catch (Exception e) {
			e.printStackTrace();
			return DtoUtil.returnFail("系统异常,获取失败", "20001");
		}
		return DtoUtil.returnDataSuccess(page);
	}
}
