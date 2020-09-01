package com.whackode.itrip.controller;

import com.whackode.itrip.base.controller.BaseController;
import com.whackode.itrip.base.pojo.vo.ResponseDto;
import com.whackode.itrip.pojo.entity.User;
import com.whackode.itrip.pojo.entity.UserLinkUser;
import com.whackode.itrip.transport.UserLinkUserTransport;
import com.whackode.itrip.util.RedisUtils;
import com.whackode.itrip.util.ValidationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userLinkUserController")
@RequestMapping("/biz/api/userinfo")
public class UserLinkUserController extends BaseController {
	@Autowired
	private UserLinkUserTransport userLinkUserTransport;
	@Autowired
	ValidationToken validationToken;
	@Autowired
	private RedisUtils redisUtils;

	/**
	 * <b>根据当前登陆用户，获得联系人</b>
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/queryuserlinkuser")
	public ResponseDto<Object> queryUserLinkUser() throws Exception {
		String tokenString = request.getHeader("token");
		User currentUser = validationToken.getCurrentUser(tokenString);


		// 封装查询对象
		UserLinkUser query = new UserLinkUser();
		query.setUserCode(currentUser.getUserCode());
		return ResponseDto.success(userLinkUserTransport.queryUserLinkUserListByQuery(query));
	}
}
