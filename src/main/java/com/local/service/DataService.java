package com.local.service;

import com.local.entity.sys.SYS_Data;
import com.local.entity.sys.SYS_Digest;
import org.nutz.dao.QueryResult;

public interface DataService {
    void inserData(SYS_Data data);
    void updateData(SYS_Data data);
    SYS_Data selectDataById(String id);

    SYS_Digest selectDigestById(String id);
    void insertDigest(SYS_Digest digest);
    void updateDigest(SYS_Digest digest);
    QueryResult selectDigests(int pageSize, int pageNumber,String unitName);


}
