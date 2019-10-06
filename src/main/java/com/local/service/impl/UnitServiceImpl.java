package com.local.service.impl;

import com.local.entity.elsys.ElSysDept;
import com.local.entity.sys.SYS_UNIT;
import com.local.service.UnitService;
import com.local.util.StrUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
//            if (!StrUtils.isBlank(name)){//部门名称不为空
//                System.out.println("1111111111");
//                cri.where().andLike("NAME","%"+name.trim()+"%");
//            }else {//如果部门名称为空
//                if (enabled.equals("1")){//状态为正常，那么就初始化根节点
//                    cri.where().andEquals("parent_Id","0");
//                }
//            }
        if (!StrUtils.isBlank(enabled)) {//状态不为空，以当前满足上面条件的所有节点继续往下查找
            cri.where().andEquals("ENABLED", enabled);
            List<SYS_UNIT> units = dao.query(SYS_UNIT.class, cri);
            getUnits(units, enabled);
            return units;
        } else {
            cri.where().andEquals("parent_Id", null);
            List<SYS_UNIT> units = dao.query(SYS_UNIT.class, cri);
            getUnits(units, enabled);
            return units;
        }
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
                }else {
                    unit.setHasChildren(false);
                }
            }
        }
    }
}
