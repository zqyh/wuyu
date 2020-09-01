package com.whackode.itrip.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.whackode.itrip.base.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("tradeController")
@RequestMapping("/trade/api")
public class TradeController extends BaseController {

	@GetMapping(value = "/prepay/{tradeNo}")
	public void testTrade(@PathVariable("tradeNo") String tradeNo) throws Exception {
		AlipayClient alipayClient =  new DefaultAlipayClient(
				"https://openapi.alipaydev.com/gateway.do" ,
				"2016101800719590",
				"MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCJ4DX03IK2ShB6mF5yNeCxx2cT02Dr/UK3+QDNqdZfPTRNxmSAf8L0Ewguuziu0KN8JUBhJwz17JS0QlI7Cagx7Yl5zsH+Kjj0v+BrOA145OjrwVjgzGH74R0iHBeK5fLsRQcSVlp7wFUXPiWKnePnOjnVXoJTb70A4thBYE9KFIoWym7+xcPf0aOgFl0ZZqbgLfwGAOcOom7ctQDzfcXT2AZhGxgIVc+Joz4BqiLaPoO7rSVTfTBhm9PcuX/eJp77KNXUMM9q0Qifw7yI2iTkGnsThsOu9y9M3iSHZnevtXJ6Dpq56nBoGio+LQdHQArxeNwfJG23SgDi58ugvJrFAgMBAAECggEAXfHO8gQacHjw14fgurNpYdk7q++Yl9PeVRquaHQz5HsAm4n1yvTC8qnRJn5dNghljN6ZClmgZCUKgfS6sQ14dGSlCsFNv5AndOUU1hnAXEKlTpZUp/+7N/QAdNBtXjAmoMS798utJas8j3TCAbxpoJE0/OTDqnR1dRgcQBeOjyt/xYrUNAvwNZQ6lYlfpBDOy6OHWDkZ33l2uyHZE1JYHmIISOJdPZBd4eocKxh5q22dfuOVl6US9KMyg9IgGz9zdK8SuhVQrdWT3aV5qRtr5nTfxmd0anq/K2mA0OCPrkbE6t6ALlTcd90DO0vqk9uCIHCvl2RS/p+Z1sDpebarwQKBgQDoU11L2CqMTxzUctJNiXSngznmuRCWYiCvtbGcCSxVSnGDatlokvGMM768ou9t1gJriGo67QxoNyB9EyY/FEOyF1hK+9C6HMrXfx8IHD4oG1cRvxdtxXloRXHZ6L5M8lgeQWu2umrVp0NLWCoCSytcttmTgEJL915r71KdchlwUQKBgQCX7POaQFQpqneX09zJ01OYHLEywGa85Sat6Qstnv9mbmjLjrBopCUtIW/pDZJQmcS+Mo5IU2F6PbhnhAbFLkJxIOmGExi44QqaKFirGWzi6aGI5iO0CMNWBbdsvsOub3nBzQLjAdlC9NbUfGIjtVHg9Y3Ttdr1NLBmKeuG8iY6NQKBgB/7ifZs9b6HDZL5k5X0/EgrOodWpr/tFk7aFm9CnAXJz8KlrPRGdlY0sWdKjudx9LdU+jrOc0zNef1961/3rDISIKeB7wQP/qSX7W+yY6Qlz2C0C8eizcna9YdoEPqKyjKwEuH+28I52nu+0Vmre3ax/VXe7EGE9kypj5f5IyNhAoGAHDpxtNp5CNjcqcA6yOFUKfJC1mw7aqOlQTEI26wsRksDe7cCfh7prW4GDiZwZjt7+98G4tdjSERJjCpS27ZRsBVubnQD5u4hey099GSf/7VrRzWDssLiEIy1XSZA05tw4xqFi8pfJPq7I/YMghgBo6vcc1bZ3S+yCvmFAqjhg3UCgYATufPX9/UD6/toSoccIjdon5p4pHiDmlW2X3vNfhSK4OqhB7SA1HRYEqY6Gl37DByERoJ0Qn4ckpJ21HZUwV9HlKT6/JHp3KgxAaUpSChentE6MoCiYJgeUqzO1EnicH0JGOWn0mLMnvOpZxGwmK6bF9mWAwR+1ILH/Bf7/cpriQ==",
				"json", "UTF-8",
				"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAieA19NyCtkoQephecjXgscdnE9Ng6/1Ct/kAzanWXz00TcZkgH/C9BMILrs4rtCjfCVAYScM9eyUtEJSOwmoMe2Jec7B/io49L/gazgNeOTo68FY4Mxh++EdIhwXiuXy7EUHElZae8BVFz4lip3j5zo51V6CU2+9AOLYQWBPShSKFspu/sXD39GjoBZdGWam4C38BgDnDqJu3LUA833F09gGYRsYCFXPiaM+Aaoi2j6Du60lU30wYZvT3Ll/3iae+yjV1DDPatEIn8O8iNok5Bp7E4bDrvcvTN4kh2Z3r7Vyeg6auepwaBoqPi0HR0AK8XjcHyRtt0oA4ufLoLyaxQIDAQAB",
				"RSA2");  //获得初始化的AlipayClient
		AlipayTradePagePayRequest alipayRequest =  new  AlipayTradePagePayRequest(); //创建API对应的request
		alipayRequest.setReturnUrl( "http://localhost/itrip" );
		alipayRequest.setNotifyUrl( "http://domain.com/CallBack/notify_url.jsp" ); //在公共参数中设置回跳和通知地址
		alipayRequest.setBizContent( "{"  +
				"    \"out_trade_no\":\"20150320010101001\","  +
				"    \"product_code\":\"FAST_INSTANT_TRADE_PAY\","  +
				"    \"total_amount\":88.88,"  +
				"    \"subject\":\"Iphone6 16G\","  +
				"    \"body\":\"Iphone6 16G\","  +
				"    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\","  +
				"    \"extend_params\":{"  +
				"    \"sys_service_provider_id\":\"2088511833207846\""  +
				"    }" +
				"  }" ); //填充业务参数
		String form= "" ;
		try  {
			form = alipayClient.pageExecute(alipayRequest).getBody();  //调用SDK生成表单
		}  catch  (AlipayApiException e) {
			e.printStackTrace();
		}
		response.setContentType( "text/html;charset=UTF-8");
		response.getWriter().write(form); //直接将完整的表单html输出到页面
		response.getWriter().flush();
		response.getWriter().close();
	}
}
