package com.whackode.itrip.service;

import com.whackode.itrip.pojo.entity.AreaDic;

import java.util.List;

/**
 * <b>爱旅行-区域字典信息业务层接口</b>
 * @author Arthur
 * @version 1.0.0
 * @since 1.0.0
 */
public interface AreaDicService {
	/**
	 * <b>根据查询获得信息列表</b>
	 * @param query
	 * @return
	 * @throws Exception
	 */
	List<AreaDic> getListByQuery(AreaDic query) throws Exception;
}
