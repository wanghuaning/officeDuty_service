package com.local.service;

import org.nutz.dao.QueryResult;

public interface PeopleService {

    QueryResult selectPeoples(int pageSize, int pageNumber,String unitId,String name,String idcard,String politicalStatus,String enabled);
}
