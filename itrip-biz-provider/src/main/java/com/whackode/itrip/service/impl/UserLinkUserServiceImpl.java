package com.whackode.itrip.service.impl;

import com.whackode.itrip.dao.UserLinkUserDao;
import com.whackode.itrip.pojo.entity.UserLinkUser;
import com.whackode.itrip.service.UserLinkUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("userLinkUserService")
@Transactional
public class UserLinkUserServiceImpl implements UserLinkUserService {
	@Autowired
	private UserLinkUserDao userLinkUserDao;

	/**
	 * <b>根据查询信息查询联系人列表</b>
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public List<UserLinkUser> getUserLinkUserListByQuery(UserLinkUser query) throws Exception {
		List<UserLinkUser> userLinkUserList = userLinkUserDao.findUserLinkUserListByQuery(query);

		if (userLinkUserList != null) {
			return userLinkUserList;
		}

		return new ArrayList<UserLinkUser>();
	}
}
