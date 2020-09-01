package com.whackode.itrip.util.solr;


import com.whackode.itrip.util.EmptyUtils;
import com.whackode.itrip.util.Page;
import com.whackode.itrip.util.constant.Constants;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import java.util.List;

/**
 * 通用的封装的查询solr的基础查询类
 */
public class BaseQuery<T> {
    /***
     * 使用SolrQuery 查询分页数据
     */
    public Page<T> queryPage(HttpSolrClient httpSolrClient,SolrQuery query, Integer pageNo, Integer pageSize, Class clazz) throws Exception {
        //设置起始页数
        Integer rows= EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Integer currPage=(EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO - 1 : pageNo - 1);
        Integer start=currPage*rows;
        query.setStart(start);
        //一页显示多少条
        query.setRows(rows);
        QueryResponse queryResponse = httpSolrClient.query(query);
        SolrDocumentList docs = queryResponse.getResults();
        Page<T> page = new Page(currPage+1, query.getRows(), new Long(docs.getNumFound()).intValue());
        List<T> list = queryResponse.getBeans(clazz);
        page.setRows(list);
        return page;
    }

    /***
     * 使用SolrQuery 查询列表数据
     */
    public List<T> queryList(HttpSolrClient httpSolrClient,SolrQuery query, Integer pageSize, Class clazz) throws Exception {
        //设置起始页数
        query.setStart(0);
        //一页显示多少条
        query.setRows(EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize);
        QueryResponse queryResponse = httpSolrClient.query(query);
        List<T> list = queryResponse.getBeans(clazz);
        return list;
    }
}
