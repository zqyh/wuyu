package com.whackode.itrip.controller;

import com.whackode.itrip.base.controller.BaseController;
import com.whackode.itrip.base.enums.UserActivatedEnum;
import com.whackode.itrip.base.enums.UserTypeEnum;
import com.whackode.itrip.base.pojo.vo.ResponseDto;
import com.whackode.itrip.pojo.entity.User;
import com.whackode.itrip.pojo.vo.UserVO;
import com.whackode.itrip.transport.UserTransport;
import com.whackode.itrip.util.JWTUtil;
import com.whackode.itrip.util.MD5Util;
import com.whackode.itrip.util.RegValidationUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b>爱旅行-认证模块控制器</b>
 * @author Arthur
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController("authController")
@RequestMapping("/auth/api")
public class AuthController extends BaseController {
	@Autowired
	private UserTransport userTransport;

	/**
	 * <b>用户名注册验证-电子邮件</b>
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/ckusr")
	public ResponseDto<Object> checkUserEmailForRegistry(String name) throws Exception {
		// 校验用户所提交的电子邮件是否有效（是否为空，以及是不是一个电子邮件格式）
		if (RegValidationUtil.validateEmail(name)) {
			// 校验通过之后，通过注册中心找到对应的生产者进行数据库校验
			// 封装查询对象
			User query = new User();
			query.setUserCode(name);
			// 进行查询
			List<User> userList = userTransport.getListByQuery(query);
			// 进行结果判断
			if (userList == null || userList.size() == 0) {
				// 此时用户注册时所填写的邮箱地址可用
				return ResponseDto.success();
			}
		}
		return ResponseDto.failure("该邮箱地址已被注册");
	}

	/**
	 * <b>使用电子邮件注册用户信息</b>
	 * @param userVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/doregister")
	public ResponseDto<Object> registryUser(@RequestBody UserVO userVO) throws Exception {
		// 校验用户所给定信息是否有效
		if (RegValidationUtil.validateEmail(userVO.getUserCode())
				&& userVO.getUserPassword() != null && !"".equals(userVO.getUserPassword())) {
			// 进程唯一性校验
			User query = new User();
			query.setUserCode(userVO.getUserCode());
			List<User> userList = userTransport.getListByQuery(query);
			if (userList == null || userList.size() <= 0) {
				// 对于密码进行MD5加密
				userVO.setUserPassword(MD5Util.encrypt(userVO.getUserPassword()));
				// 将用户注册UserVO转换成User对象
				User user = new User();
				BeanUtils.copyProperties(userVO, user);
				// 当调用该方法的时候，用户属于自主注册
				user.setUserType(UserTypeEnum.USER_TYPE_REG.getCode());
				// 将激活状态设置为未激活
				user.setActivated(UserActivatedEnum.USER_ACTIVATED_NO.getCode());
				// 使用传输层，远程调用生产者进行用户信息注册工作
				boolean flag = userTransport.saveUser(user);
				if (flag) {
					// 注册成功
					return ResponseDto.success();
				}
			}
		}
		return ResponseDto.failure("注册失败");
	}

	/**
	 * <b>使用手机号码注册用户信息</b>
	 * @param userVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/registerbyphone")
	public ResponseDto<Object> registryByCellphone(@RequestBody UserVO userVO) throws Exception {
		// 校验用户所给定信息是否有效
		if (RegValidationUtil.validateCellphone(userVO.getUserCode())
				&& userVO.getUserPassword() != null && !"".equals(userVO.getUserPassword())) {
			// 进行唯一性校验
			User query = new User();
			query.setUserCode(userVO.getUserCode());
			List<User> userList = userTransport.getListByQuery(query);
			if (userList == null || userList.size() == 0) {
				// 此时的手机号码未进行注册
				// 对于密码进行MD5加密
				userVO.setUserPassword(MD5Util.encrypt(userVO.getUserPassword()));
				// 将用户注册UserVO转换成User对象
				User user = new User();
				BeanUtils.copyProperties(userVO, user);
				// 当调用该方法的时候，用户属于自主注册
				user.setUserType(UserTypeEnum.USER_TYPE_REG.getCode());
				// 将激活状态设置为未激活
				user.setActivated(UserActivatedEnum.USER_ACTIVATED_NO.getCode());
				// 使用传输层，远程调用生产者进行用户信息注册工作
				boolean flag = userTransport.saveUser(user);
				if (flag) {
					// 注册成功
					return ResponseDto.success();
				}
			}
			return ResponseDto.failure("该手机号码已经注册");
		}
		return ResponseDto.failure("注册失败");
	}

	/**
	 * <b>激活注册用户-邮箱</b>
	 * @param user
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@PutMapping(value = "/activate")
	public ResponseDto<Object> activeUser(String user, String code) throws Exception {
		// 校验用户所给定的user和code有效
		if (user != null && !"".equals(user.trim()) && code != null && !"".equals(code)) {
			// 通过user在Redis中查询相应的code
			String activeCode = userTransport.getActiveCodeByUserCode(user);
			// 比较两个code是否相同
			if (code.equals(activeCode)) {
				// 修改用户的激活状态
				User updateUser = new User();
				updateUser.setUserCode(user);
				updateUser.setActivated(UserActivatedEnum.USER_ACTIVATED_YES.getCode());
				// 在数据库中更新用户数据
				userTransport.updateUser(updateUser);
				return ResponseDto.success();
			}
			return ResponseDto.failure("激活码不正确");
		}
		return ResponseDto.failure("激活失败");
	}

	/**
	 * <b>激活注册用户-手机号码</b>
	 * @param user
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@PutMapping(value = "/validatephone")
	public ResponseDto<Object> activeUserByCellphone(String user, String code) throws Exception {
		// 校验用户所给定的user和code有效
		if (user != null && !"".equals(user.trim()) && code != null && !"".equals(code)) {
			// 通过user在Redis中查询相应的code
			String activeCode = userTransport.getActiveCodeByUserCode(user);
			// 比较两个code是否相同
			if (code.equals(activeCode)) {
				// 修改用户的激活状态
				User updateUser = new User();
				updateUser.setUserCode(user);
				updateUser.setActivated(UserActivatedEnum.USER_ACTIVATED_YES.getCode());
				// 在数据库中更新用户数据
				userTransport.updateUser(updateUser);
				return ResponseDto.success();
			}
			return ResponseDto.failure("激活码不正确");
		}
		return ResponseDto.failure("激活失败");
	}

	/**
	 * <b>使用cellphone/email和password登陆系统</b>
	 * @param name
	 * @param password
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/dologin")
	public ResponseDto<Object> loginUser(String name, String password) throws Exception {
		if (name != null && !"".equals(name.trim())
				&& password != null && !"".equals(password.trim())) {
			// 通过登陆用户名查找相关信息，在比较密码是否相同
			User query = new User();
			query.setUserCode(name);
			// 查找获得相应结果
			List<User> userList = userTransport.getListByQuery(query);
			if (userList != null && userList.size() > 0) {
				User user = userList.get(0);
				// 比较密码是否相同
				if (user.getUserPassword().equals(MD5Util.encrypt(password))) {
					if (user.getActivated() == UserActivatedEnum.USER_ACTIVATED_YES.getCode()) {
						// 登陆成功，按照相应的技术，生成一个Token令牌，以Cookie形式交给浏览器，
						// 每当浏览器在访问其他服务器的时候，都会携带该信息，如果需要校验该用户是否登陆，
						// 只需要校验该Token是否是按照系统规则生成的即可。
						// 在Java当中，Token技术使用了JWT（Java Web Token）来完成
						// 使用当前登陆用户的id生成Token信息
						String token = JWTUtil.createToken(user.getId());
						// 将Token随着相应交给浏览器
						response.setHeader("token", token);
						return ResponseDto.success(token);
					} else {
						return ResponseDto.failure("该用户未激活");
					}
				} else {
					return ResponseDto.failure("登陆密码错误");
				}
			} else {
				return ResponseDto.failure("该用户未注册");
			}
		} else {
			return ResponseDto.failure("请填写登陆信息");
		}
	}
}
