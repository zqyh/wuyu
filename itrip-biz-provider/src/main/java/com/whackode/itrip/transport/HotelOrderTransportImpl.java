package com.whackode.itrip.transport;

import com.whackode.itrip.pojo.entity.HotelOrder;
import com.whackode.itrip.pojo.vo.ListHotelOrderVO;
import com.whackode.itrip.service.HotelOrderService;
import com.whackode.itrip.util.Page;
import com.whackode.itrip.util.ValidationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController("hotelOrderTransport")
@RequestMapping("/hotelorder/trans")
public class HotelOrderTransportImpl implements HotelOrderTransport {
	@Autowired
	private HotelOrderService hotelOrderService;
	@Autowired
	private ValidationToken validationToken;

	@PostMapping(value = "/list")
	public List<HotelOrder> getHotelListByQuery(@RequestBody HotelOrder query) throws Exception {
		return hotelOrderService.getListByQuery(query);
	}

	@PostMapping(value = "/save")
	public boolean save(@RequestBody HotelOrder hotelOrder) throws Exception {
		return hotelOrderService.save(hotelOrder);
	}

	@PostMapping(value = "/update")
	public boolean update(@RequestBody HotelOrder hotelOrder) throws Exception {
		return hotelOrderService.update(hotelOrder);
	}

	@PostMapping(value = "/id")
	public HotelOrder getHotelOrderById(@RequestParam Long orderId) throws Exception {
		return hotelOrderService.getHotelOrderById(orderId);
	}

	@PostMapping(value = "/no")
	public HotelOrder getHotelOrderByNo(@RequestParam String orderNo) throws Exception {
		return hotelOrderService.getHotelOrderByNo(orderNo);
	}

	@PostMapping(value = "/personalorderlist")
	public Page<ListHotelOrderVO> getPersonalOrderList(@RequestParam Map<String, Object> param,@RequestParam Integer pageNo, @RequestParam Integer pageSize) throws Exception {
		return hotelOrderService.queryOrderPageByMap(param,pageNo,pageSize);
	}

	@GetMapping(value="/orderPayAmount")
	public BigDecimal getOrderPayAmount(int count, Long roomId) throws Exception{
		return hotelOrderService.getOrderPayAmount(count,roomId);
	}
}
