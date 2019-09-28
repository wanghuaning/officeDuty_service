package com.local.service;

import com.local.entity.REG_PushRecord;
import org.nutz.dao.QueryResult;

import java.util.List;

public interface PushRecordService {
    QueryResult selectPushRecordPage(int pageSize, int pageNumber,String dataType,String batchNumber);
    QueryResult selectPushRecordPageByParam( int pageSize, int pageNumber,String dataTable,String dataType,String batchNumber);

}
