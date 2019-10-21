package com.local.service;

import org.nutz.dao.QueryResult;

public interface LogsService {
    QueryResult selectPeoples(int pageSize, int pageNumber, String userId,String name);
}
