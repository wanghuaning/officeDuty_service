package com.local.service.impl;
import com.local.common.redis.util.RedisUtil;
import com.local.entity.*;
import com.local.model.RecordDetailModel;
import com.local.service.PushRecordService;
import com.local.util.Result;
import com.local.util.ResultCode;
import com.local.util.ResultMsg;
import com.local.util.StrUtils;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.commons.lang.StringEscapeUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.Sqls;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PushRecordServiceImpl implements PushRecordService {
    @Autowired
    private Dao dao;
    @Autowired
    private RedisUtil redisUtil;
    /**
     * 条件查询备案表推送记录信息
     */
    @Override
    public QueryResult selectPushRecordPage(int pageSize, int pageNumber,String dataType,String batchNumber){
        Pager pager=new Pager();
        pager.setPageNumber(pageNumber);
        pager.setPageSize(pageSize);
        Criteria cri= Cnd.cri();
        if (!StrUtils.isBlank(dataType)){//推送或者获取
            cri.where().andLike("Push_Receive","%"+dataType.trim()+"%");
        }
        if (!StrUtils.isBlank(batchNumber)){//批次号
            cri.where().andLike("Batch_Number","%"+batchNumber.trim()+"%");
        }
        cri.where().andEquals("Data_Table","备案表");
        cri.getOrderBy().desc("End_Date");
        List<REG_PushRecord> pushRecords=dao.query(REG_PushRecord.class,cri,pager);
        for (REG_PushRecord rp:pushRecords){
            List<REG_PushRecord> childList=dao.query(REG_PushRecord.class,Cnd.where("Parent_Id","=",rp.getId()));
            if(!StrUtils.isBlank(childList) && childList.size()>0){
                rp.setChildren(childList);
            }
        }
        if (StrUtils.isBlank(pager)){
            pager=new Pager();
        }
        pager.setRecordCount(dao.count(REG_PushRecord.class,cri));
        QueryResult queryResult=new QueryResult(pushRecords,pager);
        return queryResult;
    }
    /**
     * 查询推送记录详情信息
     */
    @Override
    public QueryResult selectPushRecordPageByParam( int pageSize, int pageNumber,String dataTable,String dataType,String batchNumber) {
        Pager pager = dao.createPager(pageNumber, pageSize);
        Criteria cri = Cnd.cri();
        if (dataType.equals("推送")) {
            cri.where().andEquals("Push_Batch", batchNumber);
        } else if(dataType.equals("获取")){
            cri.where().andEquals("Batch_Number", batchNumber);
        }
        if (StrUtils.isBlank(pager)) {
            pager = new Pager();
        }
        if (dataTable.equals("备案表")) {
            List<RecordDetailModel> pushRecords=selectRegPushRecordByParam(cri,pager);
            pager.setRecordCount(dao.count(REG_Registration.class,cri));
            return new QueryResult(pushRecords, pager);
        } else if (dataTable.equals("措施表")) {
            List<RecordDetailModel> pushRecords = selectMeasurePushRecordByParam(cri,pager);
            pager.setRecordCount(dao.count(REG_FactorMeasure.class,cri));
            return new QueryResult(pushRecords, pager);
        } else if (dataTable.equals("污染类型表")) {
            List<RecordDetailModel> pushRecords = selectFactorPushRecordByParam(cri,pager);
            pager.setRecordCount(dao.count(REG_InfluencingFactor.class,cri));
            return new QueryResult(pushRecords, pager);
        } else{//查询用户表
            List<RecordDetailModel> pushRecords = selectUserPushRecordByParam(cri,pager);
            pager.setRecordCount(dao.count(REG_User.class,cri));
            return new QueryResult(pushRecords, pager);
        }
    }
    /**
     * 查询某表推送记录详情信息
     */
    public List<RecordDetailModel> selectRegPushRecordByParam(Criteria cri,Pager pager){
        List<REG_Registration> pushRecords = dao.query(REG_Registration.class, cri, pager);
        List<RecordDetailModel> RecordsDetail  = new ArrayList<>();
        for (REG_Registration record : pushRecords) {
            RecordDetailModel reg_record = new RecordDetailModel();
            reg_record.setName(record.getProjectName());
            reg_record.setId(record.getId());
            reg_record.setParentId(record.getRecordNumber());
            reg_record.setUpdateTime(record.getUpdateTime());
            RecordsDetail.add(reg_record);
        }
        return RecordsDetail;
    }
    public List<RecordDetailModel> selectFactorPushRecordByParam(Criteria cri,Pager pager){
        List<REG_InfluencingFactor> pushRecords = dao.query(REG_InfluencingFactor.class, cri, pager);
        List<RecordDetailModel> RecordsDetail  = new ArrayList<>();
        for (REG_InfluencingFactor record : pushRecords) {
            RecordDetailModel reg_record = new RecordDetailModel();
            reg_record.setName(record.getFactor());
            reg_record.setId(record.getId());
            reg_record.setParentId(record.getRegistrationId());
            reg_record.setUpdateTime(record.getUpdateTime());
            RecordsDetail.add(reg_record);
        }
        return RecordsDetail;
    }
    public List<RecordDetailModel> selectMeasurePushRecordByParam(Criteria cri,Pager pager){
        List<REG_FactorMeasure> pushRecords = dao.query(REG_FactorMeasure.class, cri, pager);
        List<RecordDetailModel> RecordsDetail  = new ArrayList<>();
        for (REG_FactorMeasure record : pushRecords) {
            RecordDetailModel reg_record = new RecordDetailModel();
            reg_record.setName(record.getYnFlag());
            reg_record.setId(record.getId());
            reg_record.setParentId(record.getFactorId());
            reg_record.setUpdateTime(record.getUpdateDate());
            RecordsDetail.add(reg_record);
        }
        return RecordsDetail;
    }
    public List<RecordDetailModel> selectUserPushRecordByParam(Criteria cri,Pager pager){
        List<REG_User> pushRecords = dao.query(REG_User.class, cri, pager);
        List<RecordDetailModel> RecordsDetail  = new ArrayList<>();
        for (REG_User record : pushRecords) {
            RecordDetailModel reg_record = new RecordDetailModel();
            reg_record.setName(record.getName());
            reg_record.setId(record.getId());
            reg_record.setParentId(record.getId());
            reg_record.setUpdateTime(record.getUpdateDate());
            RecordsDetail.add(reg_record);
        }
        return RecordsDetail;
    }
}
