package com.whackode.itrip.dao;

import com.whackode.itrip.pojo.entity.UserLinkUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b>爱旅行-用户联系人信息数据持久层接口</b>
 * @author Arthur
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface UserLinkUserDao {
	/**
	 * <b>根据查询获得列表信息</b>
	 * @param query
	 * @return
	 * @throws Exception
	 */
	List<UserLinkUser> findUserLinkUserListByQuery(UserLinkUser query) throws Exception;
}
