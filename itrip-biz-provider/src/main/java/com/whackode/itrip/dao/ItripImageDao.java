package com.whackode.itrip.dao;

import com.whackode.itrip.pojo.entity.ItripImage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b>爱旅行-图片信息数据持久层接口</b>
 * @author Arthur
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface ItripImageDao {
	/**
	 * <b>根据查询条件查询信息列表</b>
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public List<ItripImage> findListByQuery(ItripImage query) throws Exception;
}
