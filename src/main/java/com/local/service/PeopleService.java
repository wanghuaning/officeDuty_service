package com.local.service;

import com.local.entity.sys.SYS_Detail;
import com.local.entity.sys.SYS_People;
import com.local.entity.sys.SYS_Pwxk;
import org.nutz.dao.QueryResult;

import java.util.List;

public interface PeopleService {

    void insertPeoples(SYS_People people);
    void updatePeople(SYS_People people);
    void deletePeople(String id);

    QueryResult selectPeoples(int pageSize, int pageNumber,List<String> unitIds,String name,String idcard,String politicalStatus,String states,String detail,String[] position,String[] rank);

    SYS_People selectPeopleById(String id);//根据Id查询

    SYS_People selectPeopleByIdcardAndUnitIdAndName(String idcard,String name,String unitId);

    SYS_People selectPeopleByIdcardAndNotId(String idcard,String id);//查询身份证是否重复,处修改此条外

    SYS_People selectPeopleByIdcardAndUnitId(String idcard,String uid);//查询身份证

    List<SYS_People> selectPeoplesByUnitId(String unitId,String isChild,String states);//根据单位ID查询，是否包含下级单位的 人员1:包含

    List<SYS_People> selectPeoplesByUnitId(String unitId);
    List<SYS_People> selectPeoplesByUnitIdAndLikeName(String unitId,String name);//根据姓名模糊查询

    List<SYS_People> selectPeoplesByUnitIdAndRank(String unitId,String rank,String states);

    List<SYS_People> selectPeoplesByUnitIdAndDuty(String unitId,String duty,String states);
    List<SYS_People> selectPeoplesByUnitIdsAndDuty(String[] units,String duty,String states);
    List<SYS_People> selectPeoplesByUnitIdsAndAllDuty(String[] units,String states);
    List<SYS_People> selectPeoplesByUnitIdAndRealName(String unitId,String states);//单列实名制
    List<SYS_People> selectPeoplesByUnitIdAndJunZhuan(String unitId,String states,String detail);//军转
    List<SYS_People> selectPeoplesByPids(List<String> peopleIds);
    List<SYS_People> selectPeoplesByPidsArr(String[] peopleIds);
    List<SYS_People> selectPeoplesByUnitIds(String[] units,String states);
    List<SYS_People> selectPeoplesByUnitIds(String[] units);
    List<SYS_People> selectPeoplesByUnitIds(List<String> units);
    List<SYS_People> selectPeoplesByUnitIdsAndPager(int pageSize, int pageNumber,String[] units);
    List<SYS_People> selectIncumbentPeoplesByUnitId(String [] units,String states);//在职人员等查询
    List<SYS_People> selectLevelPeoplesByUnitId(String [] units);//离职人员查询
    List<SYS_People> selectTrunPeoplesByUnitId(String[] units,String states);// 套转人员查询

    List<SYS_People> selectSexPeoplesByUnitId(String unitId,String sex,String states);// 性别查询

    List<SYS_People> selectPartyPeoplesByUnitId(String unitId,String party,String states);// 党员查询
    List<SYS_People> selectPartyPeoplesByUnitIds(String[] units ,String party,String states);// 党员查询

    QueryResult selectPeopleDetailInfo(int pageSize, int pageNumber,String[] arr,String sex,String party,String age,String duty,String name,String unitName);

    List<SYS_People> selectPeopleDetailInfos(String[] arr,String sex,String party,String age,String duty);
    List<SYS_People> selectNotPeopleChinaName();// 未同步拼音

    List<SYS_People> selectPeoplesByUnitIdAndPoliticalStatus(String unitId,String politicalStatus);//根据编制类型查询

    List<SYS_Pwxk> selectpwxuke();
    QueryResult selectPeopleDetails(int pageSize, int pageNumber,String peopleId);
    void insertPeopleDetail(SYS_Detail detail);
}
