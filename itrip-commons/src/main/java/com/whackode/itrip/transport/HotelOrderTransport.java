package com.whackode.itrip.transport;

import com.whackode.itrip.pojo.entity.HotelOrder;
import com.whackode.itrip.pojo.vo.ListHotelOrderVO;
import com.whackode.itrip.util.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@FeignClient(name = "itrip-biz-provider")
@RequestMapping("/hotelorder/trans")
public interface HotelOrderTransport {
	@PostMapping(value = "/list")
	List<HotelOrder> getHotelListByQuery(@RequestBody HotelOrder query) throws Exception;

	@PostMapping(value = "/save")
	boolean save(@RequestBody HotelOrder hotelOrder) throws Exception;

	@PostMapping(value = "/update")
	boolean update(@RequestBody HotelOrder hotelOrder) throws Exception;

	@PostMapping(value = "/id")
	HotelOrder getHotelOrderById(@RequestParam Long orderId) throws Exception;

	@PostMapping(value = "/no")
	HotelOrder getHotelOrderByNo(@RequestParam String orderNo) throws Exception;

	@PostMapping(value = "/personalorderlist")
	public Page<ListHotelOrderVO> getPersonalOrderList(@RequestParam Map<String, Object> param,@RequestParam Integer pageNo, @RequestParam Integer pageSize) throws Exception;

	@GetMapping(value="/orderPayAmount")
	public BigDecimal getOrderPayAmount(@RequestParam int count, @RequestParam Long roomId) throws Exception;
}
