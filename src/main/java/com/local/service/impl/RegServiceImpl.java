package com.local.service.impl;

import com.local.common.slog.annotation.SLog;
import com.local.entity.REG_FactorMeasure;
import com.local.entity.REG_InfluencingFactor;
import com.local.entity.REG_Registration;
import com.local.service.RegService;
import com.local.util.StrUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegServiceImpl implements RegService {
    @Autowired
    private Dao dao;
    /**
     * 模糊查询备案信息
     */
    @Override
    @SLog(tag = "备案查询")
    public QueryResult selectRegistration(int pageSize, int pageNumber){
        Pager pager=new Pager();
        pager.setPageNumber(pageNumber);
        pager.setPageSize(pageSize);
        Criteria cri= Cnd.cri();
        cri.getOrderBy().asc("Update_Time");
        List<REG_Registration> registrations=dao.query(REG_Registration.class,cri,pager);
        if (StrUtils.isBlank(pager)){
            pager=new Pager();
        }
        pager.setRecordCount(dao.count(REG_Registration.class,cri));
        QueryResult queryResult=new QueryResult(registrations,pager);
        return queryResult;
    }
    /**
     * 条件查询备案信息
     */
    public QueryResult selectRegistrationByParam(int pageNumber,int pageSize,String city,String block,
                                                 String proname,String buildunit,String recordnum){
        Pager pager=new Pager();
        pager.setPageSize(pageSize);
        pager.setPageNumber(pageNumber);
        Criteria cri=Cnd.cri();
        if (!StrUtils.isBlank(city)){//市
            cri.where().andLike("Build_City_Name","%"+city+"%");
        }
        if (!StrUtils.isBlank(block)){
            cri.where().andLike("Build_County_Name","%"+block+"%");
        }
        if (!StrUtils.isBlank(proname)){
            cri.where().andLike("Project_Name","%"+proname+"%");
        }
        if (!StrUtils.isBlank(buildunit)){
            cri.where().andLike("Construction_Company_Name","%"+buildunit+"%");
        }
        if (!StrUtils.isBlank(recordnum)){
            cri.where().andLike("Record_Number","%"+recordnum+"%");
        }
        cri.getOrderBy().orderBy("Announcement_Date","desc NULLS LAST");
        List<REG_Registration> registrations=dao.query(REG_Registration.class,cri,pager);
        if (StrUtils.isBlank(pager)){
            pager=new Pager();
        }
        pager.setRecordCount(dao.count(REG_Registration.class,cri));
        QueryResult queryResult=new QueryResult(registrations,pager);
        return queryResult;
    }
    @Override
    public REG_Registration selectRegById(String id){
        List<REG_Registration> list=new ArrayList<>();
        Criteria criteria=Cnd.cri();
        criteria.where().andEquals("id",id);
        list=dao.query(REG_Registration.class,criteria);
        if (list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
    }
    /**
     * 根据ID查询
     * @param pid
     * @return
     */
    @Override
    public List<REG_InfluencingFactor> selcetFactorByPId(String pid){
        List<REG_InfluencingFactor> list=new ArrayList<>();
        Criteria criteria=Cnd.cri();
        criteria.where().andEquals("Registration_Id",pid);
        criteria.getOrderBy().asc("Factor_Sort");
        list=dao.query(REG_InfluencingFactor.class,criteria);
        if (list.size()>0){
            return list;
        }else {
            return null;
        }
    }
    /**
     *查询措施列表
     * @param pid 备案ID
     * @return
     */
    @Override
    public List<REG_FactorMeasure> selectMeasureListByPolluteId(String pid){
        List<REG_FactorMeasure> list=new ArrayList<>();
        Criteria criteria=Cnd.cri();
        criteria.where().andEquals("Factor_Id",pid);
        criteria.where().andEquals("Delete_Flag",0);
        criteria.getOrderBy().asc("Measure_Sort");
        list=dao.query(REG_FactorMeasure.class,criteria);
        if (list.size()>0){
            return list;
        }else {
            return null;
        }
    }
}
