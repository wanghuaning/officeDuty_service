package com.local.service.impl;

import com.local.common.slog.annotation.SLog;
import com.local.entity.elsys.ElSysDept;
import com.local.entity.sys.SYS_UNIT;
import com.local.service.UnitService;
import com.local.util.StrUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private Dao dao;

    @Override
    public List<SYS_UNIT> selectUnitsByParam(String name, String enabled) {
        Criteria cri = Cnd.cri();
        if (!StrUtils.isBlank(name)) {//部门名称不为空
            cri.where().andLike("name", "%" + name.trim() + "%");
            if (!StrUtils.isBlank(enabled)) {//状态不为空，以当前满足上面条件的所有节点继续往下查找
                cri.where().andEquals("enabled", enabled);
            }
        }else {
            cri.where().andEquals("parent_Id", null);
            if (!StrUtils.isBlank(enabled)) {//状态不为空，以当前满足上面条件的所有节点继续往下查找
                cri.where().andEquals("enabled", enabled);
            }
        }
        List<SYS_UNIT> units = dao.query(SYS_UNIT.class, cri);
        getUnits(units, enabled);
        return units;
    }

    /**
     * 是否存在子单位
     *
     * @param pid
     * @return
     */
    public int countUnit(String pid) {
        int num = dao.count(SYS_UNIT.class, Cnd.where("parent_Id", "=", pid));
        return num;
    }

    public void getUnits(List<SYS_UNIT> units, String enabled) {
        for (SYS_UNIT unit : units) {
//            unit.setLabel(dept.getName());
            if (countUnit(unit.getId()) > 0) {
                List<SYS_UNIT> unitList = dao.query(SYS_UNIT.class, Cnd.where("parent_Id", "=", unit.getId()).and("enabled", "=", "0"));
                if (!StrUtils.isBlank(unitList) && unitList.size() > 0) {
                    unit.setChildren(unitList);
                    unit.setHasChildren(true);
                    getUnits(unitList, enabled);
                } else {
                    unit.setChildren(new ArrayList<>());
                    unit.setHasChildren(false);
                }
            } else {
                unit.setChildren(new ArrayList<>());
            }
        }
    }

    /**
     * 插入单位
     *
     * @param unit
     */
    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "插入单位", type = "C")
    public void insertUnit(SYS_UNIT unit) {
        dao.insert(unit);
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "修改单位", type = "U")
    public void updateUnit(SYS_UNIT unit){
    dao.update(unit);
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "删除单位", type = "D")
    public void deleteUnit(String id){
    dao.delete(SYS_UNIT.class,id);
    }
    /**
     * 根据名称查询单位
     *
     * @param name
     * @return
     */
    @Override
    public SYS_UNIT selectUnitByName(String name) {
        Criteria cri = Cnd.cri();
        cri.where().andEquals("name", name);
        List<SYS_UNIT> units = dao.query(SYS_UNIT.class, cri);
        if (units.size() > 0) {
            return units.get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据名称查询单位
     *
     * @param code
     * @return
     */
    @Override
    public SYS_UNIT selectUnitByCode(String code) {
        Criteria cri = Cnd.cri();
        cri.where().andEquals("code", code);
        List<SYS_UNIT> units = dao.query(SYS_UNIT.class, cri);
        if (units.size() > 0) {
            return units.get(0);
        } else {
            return null;
        }
    }
    /**
     * 根据ID查询单位
     *
     * @param id
     * @return
     */
    @Override
    public SYS_UNIT selectUnitById(String id){
        Criteria cri = Cnd.cri();
        cri.where().andEquals("id", id);
        List<SYS_UNIT> units = dao.query(SYS_UNIT.class, cri);
        if (units.size() > 0) {
            return units.get(0);
        } else {
            return null;
        }
    }
}
