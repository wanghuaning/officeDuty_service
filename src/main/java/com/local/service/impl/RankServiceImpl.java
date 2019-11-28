package com.local.service.impl;

import com.local.common.slog.annotation.SLog;
import com.local.entity.sys.SYS_Rank;
import com.local.entity.sys.SYS_Rank;
import com.local.entity.sys.SYS_Rank;
import com.local.entity.sys.SYS_UNIT;
import com.local.service.RankService;
import com.local.util.StrUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RankServiceImpl implements RankService {

    @Autowired
    private Dao dao;

    @Override
    public QueryResult selectRanks(int pageSize, int pageNumber, String pid) {
        Pager pager = new Pager();
        pager.setPageNumber(pageNumber + 1);
        pager.setPageSize(pageSize);
        List<SYS_Rank> peopleList = new ArrayList<>();
        Criteria cri = Cnd.cri();
        if (!StrUtils.isBlank(pid)) {
            cri.where().andEquals("people_Id", pid);
            cri.getOrderBy().desc("create_Time");
            peopleList = dao.query(SYS_Rank.class, cri, pager);
            if (StrUtils.isBlank(pager)) {
                pager = new Pager();
            }
            pager.setRecordCount(dao.count(SYS_Rank.class, cri));
        }
        QueryResult queryResult = new QueryResult(peopleList, pager);
        return queryResult;
    }

    @Override
    public SYS_Rank selectRankByPidOrderByTime(String pid) {
        List<SYS_Rank> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("people_Id", pid);
        cir.getOrderBy().desc("create_Time");
        list = dao.query(SYS_Rank.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public SYS_Rank selectEnableRankByPidOrderByTime(String pid) {
        List<SYS_Rank> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("people_Id", pid).andEquals("status", "在任");
        cir.getOrderBy().desc("create_Time");
        list = dao.query(SYS_Rank.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public SYS_Rank selectNotEnableRankByPidOrderByTime(String pid) {
        List<SYS_Rank> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("people_Id", pid).andEquals("status", "已免").andEquals("serve_Approval_Time", null);
        cir.getOrderBy().desc("create_Time");
        list = dao.query(SYS_Rank.class, cir);
        if (list.size() > 0) {//dao.query(SYS_Rank.class, Cnd.where("people_Id", "=", pid).and(new Static("create_Time > to_date('"+DateUtil.dateToString(list.get(0).getCreateTime())+"')")));
            List<SYS_Rank> nowRank=dao.query(SYS_Rank.class, Cnd.where("people_Id", "=", pid).and("create_Time",">",list.get(0).getCreateTime()));
            if (nowRank!=null){
                return null;
            }else {
                return list.get(0);
            }
        } else {
            return null;
        }
    }

    @Override
    public SYS_Rank selectRankByNameAndTime(String name, String peopleId, Date createTime) {
        List<SYS_Rank> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("name", name).andEquals("people_Id", peopleId).andEquals("create_Time", createTime);
        list = dao.query(SYS_Rank.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<SYS_Rank> selectRanksByPeopleId(String pid) {
        List<SYS_Rank> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("people_Id", pid);
        list = dao.query(SYS_Rank.class, cir);
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    @Override
    public SYS_Rank selectNotAproRanksByPid(String pid) {
        List<SYS_Rank> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("people_Id", pid).andEquals("approval_Time", null);
        cir.getOrderBy().desc("create_Time");
        list = dao.query(SYS_Rank.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public SYS_Rank selectTurnRankById(String pid) {
        List<SYS_Rank> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("people_Id", pid).andEquals("flag", "是");
        list = dao.query(SYS_Rank.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public SYS_Rank selectTurnNotSelfRankById(String pid, String rid) {
        List<SYS_Rank> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("people_Id", pid).andEquals("flag", "是").andNotEquals("id", rid);
        list = dao.query(SYS_Rank.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public SYS_Rank selectNowRankByPidOrderByTime(String pid) {
        List<SYS_Rank> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("people_Id", pid).andNotEquals("approval_Time", null).andEquals("serve_Approval_Time", null);
        cir.getOrderBy().desc("create_Time");
        list = dao.query(SYS_Rank.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public SYS_Rank selectAprodRanksByPid(String pid) {
        List<SYS_Rank> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("people_Id", pid).andNotEquals("approval_Time", null);
        cir.getOrderBy().desc("create_Time");
        list = dao.query(SYS_Rank.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public SYS_Rank selectRankById(String id) {
        List<SYS_Rank> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("id", id);
        list = dao.query(SYS_Rank.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * //1:套转 0：晋升
     *
     * @param unitId
     * @param flag
     * @return
     */
    @Override
    public List<SYS_Rank> selectRanksFlagByUnitId(String unitId, String flag, String name) {
        Criteria cri = Cnd.cri();
        cri.where().andEquals("unit_Id", unitId).andEquals("flag", flag).andEquals("name", name);
        List<SYS_Rank> peoples = new ArrayList<>();
        List<SYS_Rank> list = dao.query(SYS_Rank.class, cri);
        if (!StrUtils.isBlank(list) && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    /**
     * ;//根据单位ID查询，是否包含下级单位的 职级1:包含
     *
     * @param unitId
     * @param isChild
     * @return
     */
    @Override
    public List<SYS_Rank> selectRanksByUnitId(String unitId, String isChild) {
        Criteria cri = Cnd.cri();
        cri.where().andEquals("unit_Id", unitId);
        List<SYS_Rank> peoples = new ArrayList<>();
        List<SYS_Rank> list = dao.query(SYS_Rank.class, cri);
        if ("1".equals(isChild)) {//包含下级单位
            Criteria criteria = Cnd.cri();
            criteria.where().andEquals("parent_Id", unitId);
            List<SYS_UNIT> units = dao.query(SYS_UNIT.class, criteria);
            getUnits(units, list);
        }
        if (!StrUtils.isBlank(list) && list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    public void getUnits(List<SYS_UNIT> units, List<SYS_Rank> peoples) {
        if (!StrUtils.isBlank(units) && units.size() > 0) {
//            List<SYS_People> peopleList=new ArrayList<>();
            for (SYS_UNIT unit : units) {
                Criteria cri = Cnd.cri();
                cri.where().andEquals("unit_Id", unit.getId());
                List<SYS_Rank> list = dao.query(SYS_Rank.class, cri);
                if (!StrUtils.isBlank(list) && list.size() > 0) {
                    peoples.addAll(list);
                }
                List<SYS_UNIT> cunits = dao.query(SYS_UNIT.class, Cnd.where("parent_Id", "=", unit.getId()));
                if (!StrUtils.isBlank(cunits) && cunits.size() > 0) {
                    getUnits(cunits, peoples);
                }
            }
        }
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "新增职级", type = "C")
    public void insertRank(SYS_Rank duty) {
        dao.insert(duty);
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "修改职级", type = "U")
    public void updateRank(SYS_Rank duty) {
        dao.update(duty);
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "删除职级", type = "D")
    public void deleteRank(String id) {
        dao.delete(SYS_Rank.class, id);
    }
}
