package com.whackode.itrip.service;

import com.whackode.itrip.pojo.entity.ItripImage;

import java.util.List;

/**
 * <b>爱旅行-图片业务层接口</b>
 * @author Arthur
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ItripImageService {
	/**
	 * <b>根据条件查询列表</b>
	 * @param query
	 * @return
	 * @throws Exception
	 */
	List<ItripImage> getImageListByQuery(ItripImage query) throws Exception;
}
