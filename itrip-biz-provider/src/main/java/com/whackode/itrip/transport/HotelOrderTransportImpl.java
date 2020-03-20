package com.whackode.itrip.transport;

import com.whackode.itrip.pojo.entity.HotelOrder;
import com.whackode.itrip.service.HotelOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("hotelOrderTransport")
@RequestMapping("/hotelorder/trans")
public class HotelOrderTransportImpl implements HotelOrderTransport {
	@Autowired
	private HotelOrderService hotelOrderService;

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
}
