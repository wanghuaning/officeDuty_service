package com.local.service.impl;

import com.local.common.slog.annotation.SLog;
import com.local.entity.sys.SYS_People;
import com.local.entity.sys.SYS_UNIT;
import com.local.entity.sys.SYS_USER;
import com.local.service.PeopleService;
import com.local.util.DateUtil;
import com.local.util.StrUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.util.cri.SimpleCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PeopleServiceImpl implements PeopleService {

    @Autowired
    private Dao dao;

    @Override
    public QueryResult selectPeoples(int pageSize, int pageNumber, String unitId, String name, String idcard, String politicalStatus, String states) {
        Pager pager = new Pager();
        pager.setPageNumber(pageNumber + 1);
        pager.setPageSize(pageSize);
        List<SYS_People> peopleList = new ArrayList<>();
        Criteria cri = Cnd.cri();
        if (!StrUtils.isBlank(name)) {//市
            cri.where().andLike("name", "%" + name + "%");
        }
        if (!StrUtils.isBlank(idcard)) {
            cri.where().andLike("idcard", "%" + idcard + "%");
        }
        if (!StrUtils.isBlank(politicalStatus)) {
            if ("office".equals(politicalStatus)) {
                cri.where().andEquals("political_Status", "行政编制");
            } else if ("enterprise".equals(politicalStatus)) {
                cri.where().andEquals("political_Status", "事业编制（参公）");
            } else if ("other".equals(politicalStatus)) {
                cri.where().andEquals("political_Status", "其他");
            }
        }
        if (!StrUtils.isBlank(states) && !"全部".equals(states)) {
                cri.where().andEquals("states", states);
        }
        cri.where().andEquals("unit_Id", unitId);
        cri.getOrderBy().asc("people_Order");
        peopleList = dao.query(SYS_People.class, cri, pager);
        if (StrUtils.isBlank(pager)) {
            pager = new Pager();
        }
        pager.setRecordCount(dao.count(SYS_People.class, cri));
        QueryResult queryResult = new QueryResult(peopleList, pager);
        return queryResult;
    }
    @Override
    public QueryResult selectPeopleDetailInfo(int pageSize, int pageNumber,String[] arr,String sex,String party,String age,String duty){
        Pager pager = new Pager();
        pager.setPageNumber(pageNumber + 1);
        pager.setPageSize(pageSize);
        List<SYS_People> peopleList = new ArrayList<>();
        Criteria cri = Cnd.cri();
        cri.where().andInStrArray("unit_Id",arr);
        if (!StrUtils.isBlank(sex) && !"全部".equals(sex)) {//市
            cri.where().andLike("sex", "%" + sex + "%");
        }
        if (!StrUtils.isBlank(party) && !"全部".equals(party)) {
            if ("党员".equals(party)){
                cri.where().andLike("party", "%" + party + "%");
            }else {
                cri.where().andNotLike("party", "%" + party + "%");
            }
        }//.and("create_Time",">",list.get(0).getCreateTime()))
        if (!StrUtils.isBlank(age) && !"全部".equals(age)) {
            Date startDate= DateUtil.addYears(new Date(),-Integer.parseInt(age));
            Date endDate= DateUtil.addYears(new Date(),-(Integer.parseInt(age)+9));
            if ("60".equals(age)){
                endDate= DateUtil.addYears(new Date(),-(Integer.parseInt(age)+200));
            }
            cri.where().and("birthday",">=", endDate).and("birthday","<=",startDate);
        }
        if (!StrUtils.isBlank(duty) && !"全部".equals(duty)) {
            if ("其他".equals(duty)){
                cri.where().andNotEquals("position","县处级正职").andNotEquals("position","县处级副职").andNotEquals("position","乡科级正职").andNotEquals("position","乡科级副职");
            }else {
                cri.where().andEquals("position", duty);
            }
        }
        cri.getOrderBy().asc("people_Order");
        peopleList = dao.query(SYS_People.class, cri, pager);
        if (StrUtils.isBlank(pager)) {
            pager = new Pager();
        }
        pager.setRecordCount(dao.count(SYS_People.class, cri));
        QueryResult queryResult = new QueryResult(peopleList, pager);
        return queryResult;
    }

    public List<SYS_People> selectPeopleDetailInfos(String[] arr,String sex,String party,String age,String duty){
        List<SYS_People> peopleList = new ArrayList<>();
        Criteria cri = Cnd.cri();
        cri.where().andInStrArray("unit_Id",arr);
        if (!StrUtils.isBlank(sex) && !"全部".equals(sex)) {//市
            cri.where().andLike("sex", "%" + sex + "%");
        }
        if (!StrUtils.isBlank(party) && !"全部".equals(party)) {
            if ("党员".equals(party)){
                cri.where().andLike("party", "%" + party + "%");
            }else {
                cri.where().andNotLike("party", "%" + party + "%");
            }
        }//.and("create_Time",">",list.get(0).getCreateTime()))
        if (!StrUtils.isBlank(age) && !"全部".equals(age)) {
            Date startDate= DateUtil.addYears(new Date(),-Integer.parseInt(age));
            Date endDate= DateUtil.addYears(new Date(),-(Integer.parseInt(age)+9));
            if ("60".equals(age)){
                endDate= DateUtil.addYears(new Date(),-(Integer.parseInt(age)+200));
            }
            cri.where().and("birthday",">=", endDate).and("birthday","<=",startDate);
        }
        if (!StrUtils.isBlank(duty) && !"全部".equals(duty)) {
            if ("其他".equals(duty)){
                cri.where().andNotEquals("position","县处级正职").andNotEquals("position","县处级副职").andNotEquals("position","乡科级正职").andNotEquals("position","乡科级副职");
            }else {
                cri.where().andEquals("position", duty);
            }
        }
        cri.getOrderBy().asc("people_Order");
        peopleList = dao.query(SYS_People.class, cri);
        if (!StrUtils.isBlank(peopleList) && peopleList.size()>0) {
            return peopleList;
        }else{
            return null;
        }
    }
    @Override
    public SYS_People selectPeopleById(String id) {
        List<SYS_People> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("id", id);
        list = dao.query(SYS_People.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public SYS_People selectPeopleByIdcardAndNotId(String idcard, String id) {//查询身份证是否重复,处修改此条外
        List<SYS_People> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("idcard", idcard).andNotEquals("id", id);
        list = dao.query(SYS_People.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "修改人员", type = "U")
    public void updatePeople(SYS_People people) {
        dao.update(people);
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "新增人员", type = "C")
    public void insertPeoples(SYS_People people) {
        dao.insert(people);
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "删除人员", type = "D")
    public void deletePeople(String id) {
        dao.delete(SYS_People.class, id);
    }

    @Override
    public SYS_People selectPeopleByIdcardAndUnitId(String idcard, String uid) {
        List<SYS_People> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("idcard", idcard).andEquals("unit_Id", uid);
        list = dao.query(SYS_People.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<SYS_People> selectPeoplesByUnitIdAndRank(String unitId, String rank,String states) {
        Criteria cri = Cnd.cri();
        cri.where().andEquals("unitId", unitId).andEquals("position_Level", rank).andEquals("states",states);
        List<SYS_People> peoples = new ArrayList<>();
        List<SYS_People> list = dao.query(SYS_People.class, cri);
        if (!StrUtils.isBlank(list) && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    /**
     * 人员状态查询
     *
     * @param states
     * @return
     */
    @Override
    public List<SYS_People> selectIncumbentPeoplesByUnitId(String[] units, String states) {
        Criteria cri = Cnd.cri();
        cri.where().andInStrArray("unitId", units).andEquals("states", states);
        List<SYS_People> peoples = new ArrayList<>();
        List<SYS_People> list = dao.query(SYS_People.class, cri);
        if (!StrUtils.isBlank(list) && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }
    @Override
    public List<SYS_People> selectLevelPeoplesByUnitId(String [] units){
        Criteria cri = Cnd.cri();
        cri.where().andInStrArray("unitId", units).andNotEquals("states", "在职");
        List<SYS_People> peoples = new ArrayList<>();
        List<SYS_People> list = dao.query(SYS_People.class, cri);
        if (!StrUtils.isBlank(list) && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    // 套转人员查询
    @Override
    public List<SYS_People> selectTrunPeoplesByUnitId(String[] units,String states) {
        Criteria cri = Cnd.cri();
        cri.where().andInStrArray("unitId", units).andNotEquals("turn_Rank", null).andEquals("states",states);
        List<SYS_People> peoples = new ArrayList<>();
        List<SYS_People> list = dao.query(SYS_People.class, cri);
        if (!StrUtils.isBlank(list) && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    @Override
    public List<SYS_People> selectSexPeoplesByUnitId(String unitId, String sex,String states) {
        Criteria cri = Cnd.cri();
        cri.where().andEquals("unitId", unitId).andNotEquals("sex", sex).andEquals("states",states);
        List<SYS_People> peoples = new ArrayList<>();
        List<SYS_People> list = dao.query(SYS_People.class, cri);
        if (!StrUtils.isBlank(list) && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    @Override
    public List<SYS_People> selectPartyPeoplesByUnitId(String unitId, String party,String states) {
        Criteria cri = Cnd.cri();
        cri.where().andEquals("unitId", unitId).andEquals("party", party).andEquals("states",states);
        List<SYS_People> peoples = new ArrayList<>();
        List<SYS_People> list = dao.query(SYS_People.class, cri);
        if (!StrUtils.isBlank(list) && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }
    @Override
    public List<SYS_People> selectPartyPeoplesByUnitIds(String[] units, String party,String states) {
        Criteria cri = Cnd.cri();
        cri.where().andInStrArray("unitId", units).andEquals("party", party).andEquals("states",states);
        List<SYS_People> peoples = new ArrayList<>();
        List<SYS_People> list = dao.query(SYS_People.class, cri);
        if (!StrUtils.isBlank(list) && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    @Override
    public List<SYS_People> selectPeoplesByUnitIdAndDuty(String unitId, String duty,String states) {
        Criteria cri = Cnd.cri();
        cri.where().andEquals("unitId", unitId).andEquals("position", duty).andEquals("states",states);
        List<SYS_People> peoples = new ArrayList<>();
        List<SYS_People> list = dao.query(SYS_People.class, cri);
        if (!StrUtils.isBlank(list) && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }
    @Override
    public List<SYS_People> selectPeoplesByUnitIdsAndDuty(String[] units, String duty,String states) {
        Criteria cri = Cnd.cri();
        cri.where().andInStrArray("unitId", units).andEquals("position", duty).andEquals("states",states);
        List<SYS_People> peoples = new ArrayList<>();
        List<SYS_People> list = dao.query(SYS_People.class, cri);
        if (!StrUtils.isBlank(list) && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    @Override
    public List<SYS_People> selectPeoplesByUnitIdAndRealName(String unitId,String states) {
        Criteria cri = Cnd.cri();
        cri.where().andEquals("unitId", unitId).andNotEquals("real_Name", "").andNotEquals("real_Name", null).andEquals("states",states);
        List<SYS_People> peoples = new ArrayList<>();
        List<SYS_People> list = dao.query(SYS_People.class, cri);
        if (!StrUtils.isBlank(list) && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    /**
     * //根据单位ID查询，是否包含下级单位的 人员
     *
     * @param unitId
     * @param isChild
     * @return
     */
    @Override
    public List<SYS_People> selectPeoplesByUnitId(String unitId, String isChild,String states) {
        Criteria cri = Cnd.cri();
        if ("全部".equals(states)){
            cri.where().andEquals("unitId", unitId);
        }else {
            cri.where().andEquals("unitId", unitId).andEquals("states",states);
        }
        List<SYS_People> peoples = new ArrayList<>();
        List<SYS_People> list = dao.query(SYS_People.class, cri);
        if ("1".equals(isChild)) {//包含下级单位
            Criteria criteria = Cnd.cri();
            criteria.where().andEquals("parent_Id", unitId);
            List<SYS_UNIT> units = dao.query(SYS_UNIT.class, criteria);
            getUnits(units, list,states);
        }
        if (!StrUtils.isBlank(list) && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    @Override
    public List<SYS_People> selectPeoplesByUnitIds(String[] units,String states) {
        Criteria cri = Cnd.cri();
        cri.where().andInStrArray("unitId", units).andEquals("states",states);
        List<SYS_People> peoples = new ArrayList<>();
        List<SYS_People> list = dao.query(SYS_People.class, cri);
        if (!StrUtils.isBlank(list) && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    @Override
    public List<SYS_People> selectPeoplesByUnitIds(String[] units){
        Criteria cri = Cnd.cri();
        cri.where().andInStrArray("unitId", units);
        List<SYS_People> peoples = new ArrayList<>();
        List<SYS_People> list = dao.query(SYS_People.class, cri);
        if (!StrUtils.isBlank(list) && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }
    @Override
    public List<SYS_People> selectPeoplesByUnitIdsAndPager(int pageSize, int pageNumber,String[] units){
        Pager pager = new Pager();
        pager.setPageNumber(pageNumber + 1);
        pager.setPageSize(pageSize);
        Criteria cri = Cnd.cri();
        cri.where().andInStrArray("unitId", units);
        List<SYS_People> peoples = new ArrayList<>();
        cri.getOrderBy().asc("unitId");
        List<SYS_People> list = dao.query(SYS_People.class, cri,pager);
        if (!StrUtils.isBlank(list) && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }
    public void getUnits(List<SYS_UNIT> units, List<SYS_People> peoples,String states) {
        if (!StrUtils.isBlank(units) && units.size() > 0) {
//            List<SYS_People> peopleList=new ArrayList<>();
            for (SYS_UNIT unit : units) {
                Criteria cri = Cnd.cri();
                if ("全部".equals(states)){
                    cri.where().andEquals("unitId", unit.getId());
                }else {
                    cri.where().andEquals("unitId", unit.getId()).andEquals("states",states);
                }
                List<SYS_People> list = dao.query(SYS_People.class, cri);
                if (!StrUtils.isBlank(list) && list.size() > 0) {
                    peoples.addAll(list);
                }
                List<SYS_UNIT> cunits = dao.query(SYS_UNIT.class, Cnd.where("parent_Id", "=", unit.getId()));
                if (!StrUtils.isBlank(cunits) && cunits.size() > 0) {
                    getUnits(cunits, peoples,states);
                }
            }
        }
    }

}
