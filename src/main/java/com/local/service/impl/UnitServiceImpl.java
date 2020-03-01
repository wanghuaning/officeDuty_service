package com.local.service.impl;

import com.local.common.slog.annotation.SLog;
import com.local.entity.sys.SYS_UNIT;
import com.local.service.UnitService;
import com.local.util.StrUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.util.cri.SimpleCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private Dao dao;

    private static List<SYS_UNIT> cunits=new ArrayList<>();
    private static List<SYS_UNIT> punits=new ArrayList<>();
    private static List<SYS_UNIT> allunits=new ArrayList<>();

    @Override
    public List<SYS_UNIT> selectAllParentUnits(SYS_UNIT unit){
        Criteria criteria=Cnd.cri();
        punits=new ArrayList<>();
        List<SYS_UNIT> unitList=new ArrayList<>();
        if (!StrUtils.isBlank(unit.getParentId())){
            criteria.where().andEquals("Id",unit.getParentId());
            unitList=dao.query(SYS_UNIT.class,criteria);
            if (unitList.size()>0){
                getAllParentUnits(unitList.get(0));
            }
            return  punits;
        }else {
            return new ArrayList<SYS_UNIT>();
        }
    }
    public void getAllParentUnits(SYS_UNIT unit){
            if (!StrUtils.isBlank(unit.getParentId())) {
                punits.add(unit);
                List<SYS_UNIT> cunitList = dao.query(SYS_UNIT.class, Cnd.where("Id", "=", unit.getParentId()));
                if (!StrUtils.isBlank(cunitList) && cunitList.size() > 0) {
                    getAllChildUnits(cunitList);
                }
            }
    }
    private static StringBuffer stringBuffer=new StringBuffer();
    @Override
    public String  selectUnitAndChildUnits(String parentId){
        stringBuffer=new StringBuffer();
        Criteria criteria=Cnd.cri();
        List<SYS_UNIT> unitList=new ArrayList<>();
        if (!StrUtils.isBlank(parentId)){
            criteria.where().andEquals("parent_Id",parentId);
            unitList=dao.query(SYS_UNIT.class,criteria);
            getUnitAndChildUnits(unitList);
            return  stringBuffer.toString();
        }else {
            return null;
        }
    }
    public void getUnitAndChildUnits(List<SYS_UNIT> unitList){
        for (SYS_UNIT unit : unitList)  {
            stringBuffer.append(unit.getId()+";");
            if (countUnit(unit.getId()) > 0) {
                List<SYS_UNIT> cunitList = dao.query(SYS_UNIT.class, Cnd.where("parent_Id", "=", unit.getId()));
                if (!StrUtils.isBlank(cunitList) && cunitList.size() > 0) {
                    getUnitAndChildUnits(cunitList);
                }
            }
        }
    }
    @Override
    public List<SYS_UNIT> selectAllChildUnits(String parentId){
        Criteria criteria=Cnd.cri();
        cunits=new ArrayList<>();
        List<SYS_UNIT> unitList=new ArrayList<>();
        if (!StrUtils.isBlank(parentId)){
            criteria.where().andEquals("parent_Id",parentId);
            unitList=dao.query(SYS_UNIT.class,criteria);
//            cunits.addAll(unitList);
            getAllChildUnits(unitList);
            return  cunits;
        }else {
            return new ArrayList<SYS_UNIT>();
        }
    }

    public void getAllChildUnits(List<SYS_UNIT> unitList){
        for (SYS_UNIT unit : unitList)  {
//            System.out.println(unit.getName());
            cunits.add(unit);
            if (countUnit(unit.getId()) > 0) {
                List<SYS_UNIT> cunitList = dao.query(SYS_UNIT.class, Cnd.where("parent_Id", "=", unit.getId()));
                if (!StrUtils.isBlank(cunitList) && cunitList.size() > 0) {
                    getAllChildUnits(cunitList);
                }
            }
        }
    }
    @Override
    public List<SYS_UNIT> selectUnitsByParam(String name, String enabled,String parentId) {
        Criteria cri = Cnd.cri();
        allunits=new ArrayList<SYS_UNIT>();
//        if (!StrUtils.isBlank(name)) {//部门名称不为空
//            cri.where().andLike("name", "%" + name.trim() + "%");
//        }
//        if (!StrUtils.isBlank(enabled)) {//状态不为空，以当前满足上面条件的所有节点继续往下查找
//            cri.where().andEquals("enabled", enabled);
//        }
        cri.where().andEquals("id", parentId);
        List<SYS_UNIT> units = dao.query(SYS_UNIT.class, cri);
        allunits=units;
        if (units.size()>0){
            List<SYS_UNIT> unitList = dao.query(SYS_UNIT.class, Cnd.where("parent_Id", "=", parentId).and("enabled", "=", "0"));
            if (!StrUtils.isBlank(name)){
                unitList = dao.query(SYS_UNIT.class, Cnd.where("parent_Id", "=", parentId).and("enabled", "=", "0").and("name","like", "%" + name.trim() + "%"));
            }
            getAllUnit(unitList, enabled, name);
        }
//        getUnits(units, enabled, name);
        return allunits;
    }
    public void getAllUnit(List<SYS_UNIT> units, String enabled, String name) {
        for (SYS_UNIT unit: units) {
            allunits.add(unit);
            List<String> aras = new ArrayList<>();
//            System.out.println(unit.getName());
            if (countUnit(unit.getId()) > 0) {
                List<SYS_UNIT> unitList = dao.query(SYS_UNIT.class, Cnd.where("parent_Id", "=", unit.getId()).and("enabled", "=", "0"));
                if (!StrUtils.isBlank(name)){
                    unitList = dao.query(SYS_UNIT.class, Cnd.where("parent_Id", "=", unit.getId()).and("enabled", "=", "0").and("name","like", "%" + name.trim() + "%"));
                }
                if (!StrUtils.isBlank(unitList) && unitList.size() > 0) {
//                    unit.setChildren(unitList);
                    unit.setHasChildren(true);
                    getAllUnit(unitList, enabled, name);
                } else {
                    unit.setChildren(new ArrayList<SYS_UNIT>());
                    unit.setHasChildren(false);
                }
            } else {
                unit.setChildren(new ArrayList<SYS_UNIT>());
            }
            if (unit.getBuildCounty() != null) {
                String[] arr = {unit.getBuildProvince(), unit.getBuildCity(), unit.getBuildCounty()};
                unit.setAreaStrs(arr);
            } else if (unit.getBuildCity() != null) {
                String[] arr = {unit.getBuildProvince(), unit.getBuildCity()};
                unit.setAreaStrs(arr);
            } else {
                String[] arr = {unit.getBuildProvince()};
                unit.setAreaStrs(arr);
            }
        }
    }
    @Override
    public Object buildTree(List<SYS_UNIT> unitList,SYS_UNIT punit){
        Set<SYS_UNIT> trees = new LinkedHashSet<>();
        Set<SYS_UNIT> depts= new LinkedHashSet<>();
        List<String> deptNames = unitList.stream().map(SYS_UNIT::getName).collect(Collectors.toList());
        Boolean isChild;
        for (SYS_UNIT deptDTO : unitList) {
            isChild = false;
//            if (deptDTO.getId().contains(punit.getId())){
//                trees.add(punit);
//            }
            for (SYS_UNIT it : unitList) {
                if (deptDTO.getId().equals(it.getParentId())) {
                    if (deptDTO.getChildren() == null) {
                        deptDTO.setChildren(new ArrayList<SYS_UNIT>());
                    }
                    deptDTO.getChildren().add(it);
                }
                if (it.getId().equals(deptDTO.getParentId())){
                    isChild = true;
                }
            }
            String name="";
            List<SYS_UNIT> unit = dao.query(SYS_UNIT.class, Cnd.where("id", "=", deptDTO.getId()));
            if (unit.size()>0){
                name=unit.get(0).getName();
            }
            if(!isChild)
                depts.add(deptDTO);
            else if(!deptNames.contains(name))
                depts.add(deptDTO);
        }

        if (CollectionUtils.isEmpty(trees)) {
            trees = depts;
        }
        Integer totalElements = unitList!=null?unitList.size():0;
        Map map = new HashMap();
//        map.put("totalElements",totalElements);
        map.put("content",CollectionUtils.isEmpty(trees)?unitList:trees);
        return CollectionUtils.isEmpty(trees)?unitList:trees;
    }
    public static List removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
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

    public void getUnits(List<SYS_UNIT> units, String enabled, String name) {
//        for (SYS_UNIT unit : units)  {
        for (int i = 0; i < units.size(); i++) {
            List<String> aras=new ArrayList<>();
            SYS_UNIT unit = units.get(i);
            if (countUnit(unit.getId()) > 0) {
                List<SYS_UNIT> unitList = dao.query(SYS_UNIT.class, Cnd.where("parent_Id", "=", unit.getId()).and("enabled", "=", "0"));
                if (!StrUtils.isBlank(unitList) && unitList.size() > 0) {
                    if (!StrUtils.isBlank(name)) {//部门名称不为空
//                        for (SYS_UNIT unit1:unitList){
                        for (int j = 0; j < unitList.size(); j++) {
                            SYS_UNIT unit1 = unitList.get(j);
                            if (units.contains(unit1)) {//**需要在实体类对象中复写equals()方法 equals()去除 unit.setChildren(new ArrayList<>());判断
                                units.remove(units.indexOf(unit1));
                                i--;//确认删除 必要步骤**
                            }
                        }
                    }
                    unit.setChildren(unitList);
                    unit.setHasChildren(true);
                    getUnits(unitList, enabled, name);
                } else {
//                    unit.setChildren(new ArrayList<SYS_UNIT>());
                    unit.setHasChildren(false);
                }
            } else {
//                unit.setChildren(new ArrayList<SYS_UNIT>());
            }
            if (unit.getBuildCounty()!=null){
                String[] arr={unit.getBuildProvince(),unit.getBuildCity(),unit.getBuildCounty()};
                unit.setAreaStrs(arr);
            }else if (unit.getBuildCity()!=null){
                String[] arr={unit.getBuildProvince(),unit.getBuildCity()};
                unit.setAreaStrs(arr);
            }else{
                String[] arr={unit.getBuildProvince()};
                unit.setAreaStrs(arr);
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
    public void updateUnit(SYS_UNIT unit) {
        dao.update(unit);
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "删除单位", type = "D")
    public void deleteUnit(String id) {
        dao.delete(SYS_UNIT.class, id);
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

    @Override
    public SYS_UNIT selectUnitByNameNotId(String name,String id){
        Criteria cri = Cnd.cri();
        cri.where().andEquals("name", name).andNotEquals("id",id);
        List<SYS_UNIT> units = dao.query(SYS_UNIT.class, cri);
        if (units.size() > 0) {
            return units.get(0);
        } else {
            return null;
        }
    }
    @Override
    public SYS_UNIT selectLikeUnitByName(String name){
        Criteria cri = Cnd.cri();
        cri.where().andLike("name", "%"+name+"%");
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

    @Override
    public SYS_UNIT selectUnitByCodeNotId(String code,String id){
        Criteria cri = Cnd.cri();
        cri.where().andEquals("code", code).andNotEquals("id",id);
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
    public SYS_UNIT selectUnitById(String id) {
        Criteria cri = Cnd.cri();
        cri.where().andEquals("id", id);
        List<SYS_UNIT> units = dao.query(SYS_UNIT.class, cri);
        if (units.size() > 0) {
            return units.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<SYS_UNIT> selectUnitAll(){
        Criteria cri = Cnd.cri();
        cri.where().andNotEquals("name", "'单位'");
        List<SYS_UNIT> units = dao.query(SYS_UNIT.class, cri);
        if (units.size() > 0) {
            return units;
        } else {
            return null;
        }
    }
    @Override
    public SYS_UNIT selectUnitByNameAndParent(String name,String pname){
        Criteria cri = Cnd.cri();
        cri.where().andEquals("name", name).andEquals("parent_Name",pname);
        List<SYS_UNIT> units = dao.query(SYS_UNIT.class, cri);
        if (units.size() > 0) {
            return units.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<SYS_UNIT> selectAllUnits(String[] units){
        Criteria cri = Cnd.cri();
        cri.where().andInStrArray("id", units);
        List<SYS_UNIT> unitList = dao.query(SYS_UNIT.class, cri);
        if (unitList.size() > 0) {
            return unitList;
        } else {
            return null;
        }
    }
}
