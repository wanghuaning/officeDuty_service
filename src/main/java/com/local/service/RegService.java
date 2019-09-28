package com.local.service;

import com.local.entity.REG_FactorMeasure;
import com.local.entity.REG_InfluencingFactor;
import com.local.entity.REG_Registration;
import org.nutz.dao.QueryResult;

import java.util.List;

public interface RegService {
    //查询单个备案数据
    REG_Registration selectRegById(String id);
    //模糊查询备案信息
    QueryResult selectRegistration(int pageSize, int pageNumber);
    //条件查询备案信息
    QueryResult selectRegistrationByParam(int pageNumber,int pageSize,String city,String block,String proname,String buildunit,String recordnum);

    List<REG_InfluencingFactor> selcetFactorByPId(String pid);//根据备案ID查询

    /**
     * 措施
     */
    List<REG_FactorMeasure> selectMeasureListByPolluteId(String pid);//根据备案查询措施
}

