package com.local.service;

import com.local.entity.elsys.ElSysDept;
import org.nutz.dao.QueryResult;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeptService {

    List<ElSysDept> selectAllDepts();
    List<ElSysDept> selectDeptsByParam(String name,String enabled);
    ElSysDept findById(long id);

}
