package com.local.service;

import org.nutz.dao.QueryResult;

public interface LogsService {
    QueryResult selectLogs(int pageSize, int pageNumber, String userId,String name);
    QueryResult selectLogss(int pageSize, int pageNumber, String name,String unitId);
}
