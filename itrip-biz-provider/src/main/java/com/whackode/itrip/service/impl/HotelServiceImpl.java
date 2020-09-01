package com.whackode.itrip.service.impl;

import com.whackode.itrip.dao.HotelDao;
import com.whackode.itrip.pojo.entity.Hotel;
import com.whackode.itrip.pojo.vo.SearchHotCityVO;
import com.whackode.itrip.pojo.vo.SearchHotelVO;
import com.whackode.itrip.service.HotelService;
import com.whackode.itrip.util.EmptyUtils;
import com.whackode.itrip.util.Page;
import com.whackode.itrip.util.solr.BaseQuery;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <b>爱旅行-酒店模块业务层接口实现类</b>
 * @author Arthur
 * @version 1.0.0
 * @since 1.0.0
 */
@Service("hotelService")
@Transactional
public class HotelServiceImpl implements HotelService {
	@Autowired
	private SolrClient solrClient;
	@Autowired
	private HotelDao hotelDao;

	private BaseQuery<SearchHotelVO> itripHotelVOBaseQuery= new BaseQuery<SearchHotelVO>();

	/**
	 * <b>根据热门城市查询酒店</b>
	 * @param queryVO
	 * @return
	 * @throws Exception
	 */
	public List<SearchHotelVO> searchItripHotelListByHotCity(SearchHotCityVO queryVO) throws Exception {
		// 对于Spring Boot注入的SolrClient就是HttpSolrClient对象，进行强制类型转换
		HttpSolrClient httpSolrClient = (HttpSolrClient) solrClient;
		httpSolrClient.setParser(new XMLResponseParser());
		// 创建Solr的查询参数
		SolrQuery solrQuery = new SolrQuery("*:*");
		solrQuery.setQuery("cityId:" + queryVO.getCityId());
		//solrQuery.setRows(queryVO.getCount());
		// 使用SolrClient进行查询，QueryResponse
		//QueryResponse queryResponsey = solrClient.query(solrQuery);
		// 通过使用QueryResponse提取结果
		//return queryResponsey.getBeans(SearchHotelVO.class);

		List<SearchHotelVO> hotelVOList =itripHotelVOBaseQuery.queryList(httpSolrClient,solrQuery,queryVO.getCount(),SearchHotelVO.class);

		return hotelVOList;
	}

	/**
	 * <b>根据主键查询对象信息</b>
	 * @param hotelId
	 * @return
	 */
	public Hotel getHotelById(Long hotelId) throws Exception {
		// 创建查询对象
		Hotel query = new Hotel();
		query.setId(hotelId);
		// 进行列表查询
		List<Hotel> hotelList = hotelDao.findListByQuery(query);

		if (hotelList != null && hotelList.size() > 0) {
			return hotelList.get(0);
		}
		return new Hotel();
	}

	@Override
	public Page<SearchHotelVO> searchItripHotelPage(SearchHotelVO vo) throws Exception {
		// 对于Spring Boot注入的SolrClient就是HttpSolrClient对象，进行强制类型转换
		HttpSolrClient httpSolrClient = (HttpSolrClient) solrClient;
		httpSolrClient.setParser(new XMLResponseParser());

		SolrQuery query = new SolrQuery("*:*");
		StringBuffer tempQuery = new StringBuffer();
		int tempFlag = 0;

		if (EmptyUtils.isNotEmpty(vo)) {
			if (EmptyUtils.isNotEmpty(vo.getDestination())) {
				tempQuery.append(" destination :" + vo.getDestination());
				tempFlag = 1;
			}
			if (EmptyUtils.isNotEmpty(vo.getHotelLevel())) {
				query.addFilterQuery("hotelLevel:" + vo.getHotelLevel() + "");
			}

			if (EmptyUtils.isNotEmpty(vo.getKeywords())) {
				if (tempFlag == 1) {
					tempQuery.append(" AND keyword :" + vo.getKeywords());
				} else {
					tempQuery.append(" keyword :" + vo.getKeywords());
				}
			}

			if (EmptyUtils.isNotEmpty(vo.getFeatureIds())) {
				StringBuffer buffer = new StringBuffer("(");
				int flag = 0;
				String featureIdArray[] = vo.getFeatureIds().split(",");
				for (String featureId : featureIdArray) {
					if (flag == 0) {
						buffer.append(" featureIds:" + "*," + featureId + ",*");
					} else {
						buffer.append(" OR featureIds:" + "*," + featureId + ",*");
					}
					flag++;
				}
				buffer.append(")");
				query.addFilterQuery(buffer.toString());
			}

			if (EmptyUtils.isNotEmpty(vo.getTradeAreaIds())) {
				StringBuffer buffer = new StringBuffer("(");
				int flag = 0;
				String tradeAreaIdArray[] = vo.getTradeAreaIds().split(",");
				for (String tradeAreaId : tradeAreaIdArray) {
					if (flag == 0) {
						buffer.append(" tradingAreaIds:" + "*," + tradeAreaId + ",*");
					} else {
						buffer.append(" OR tradingAreaIds:" + "*," + tradeAreaId + ",*");
					}
					flag++;
				}
				buffer.append(")");
				query.addFilterQuery(buffer.toString());
			}

			if (EmptyUtils.isNotEmpty(vo.getMaxPrice())) {
				query.addFilterQuery("minPrice:" + "[* TO " + vo.getMaxPrice() + "]");
			}
			if (EmptyUtils.isNotEmpty(vo.getMinPrice())) {
				query.addFilterQuery("minPrice:" + "[" + vo.getMinPrice() + " TO *]");
			}

			if (EmptyUtils.isNotEmpty(vo.getAscSort())) {
				query.addSort(vo.getAscSort(), SolrQuery.ORDER.asc);
			}

			if (EmptyUtils.isNotEmpty(vo.getDescSort())) {
				query.addSort(vo.getDescSort(), SolrQuery.ORDER.desc);
			}
		}

		if (EmptyUtils.isNotEmpty(tempQuery.toString())) {
			query.setQuery(tempQuery.toString());
		}

		Page<SearchHotelVO> page = itripHotelVOBaseQuery.queryPage(httpSolrClient,query, vo.getPageNo(), vo.getPageSize(), SearchHotelVO.class);
		return page;
	}
}
