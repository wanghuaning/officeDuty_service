package com.local.service;

import com.local.entity.sys.SYS_People;
import org.nutz.dao.QueryResult;

import java.util.List;

public interface PeopleService {

    void insertPeoples(SYS_People people);
    void updatePeople(SYS_People people);
    void deletePeople(String id);

    QueryResult selectPeoples(int pageSize, int pageNumber,String unitId,String name,String idcard,String politicalStatus,String enabled);

    SYS_People selectPeopleById(String id);//根据Id查询

    SYS_People selectPeopleByIdcardAndNotId(String idcard,String id);//查询身份证是否重复,处修改此条外

    SYS_People selectPeopleByIdcardAndUnitId(String idcard,String uid);//查询身份证

    List<SYS_People> selectPeoplesByUnitId(String unitId,String isChild);//根据单位ID查询，是否包含下级单位的 人员
}
