package com.local.service;

import org.nutz.dao.QueryResult;

public interface MonitorService {

    QueryResult selectLogss(int pageSize, int pageNumber, String name);
}
