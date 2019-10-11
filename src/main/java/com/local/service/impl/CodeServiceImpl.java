package com.local.service.impl;

import com.local.entity.sys.SYS_AREA;
import com.local.entity.sys.SYS_CODE;
import com.local.entity.sys.SYS_UNIT;
import com.local.service.CodeService;
import com.local.util.StrUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CodeServiceImpl implements CodeService {
    @Autowired
    Dao dao;

    @Override
  public List<SYS_AREA> selectAreaCodeByUpCode(String upCode){
      List<SYS_AREA> list=new ArrayList<>();
      Criteria criteria= Cnd.cri();
      criteria.where().andEquals("Up_Code",upCode);
      list=dao.query(SYS_AREA.class,criteria);
      getAreas(list);
      if (list.size()>0){
          return list;
      }else {
          return null;
      }
  }

  @Override
  public SYS_AREA selectAreaByCode(String code){
        List<SYS_AREA> list=new ArrayList<>();
        Criteria criteria= Cnd.cri();
        criteria.where().andEquals("Code",code);
        list=dao.query(SYS_AREA.class,criteria);
        getAreas(list);
        if (list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
  }
    /**
     * 是否存在子区域
     *
     * @param pid
     * @return
     */
    public int countArea(String pid) {
        int num = dao.count(SYS_AREA.class, Cnd.where("Up_Code", "=", pid));
        return num;
    }

    public void getAreas(List<SYS_AREA> areas) {
        for (SYS_AREA area : areas) {
//            unit.setLabel(dept.getName());
            if (countArea(area.getCode()) > 0) {
                List<SYS_AREA> areaList = dao.query(SYS_AREA.class, Cnd.where("Up_Code", "=", area.getCode()));
                if (!StrUtils.isBlank(areaList) && areaList.size() > 0) {
                    area.setChildren(areaList);
                    getAreas(areaList);
                }
            }
        }
    }
    /**
     * 机构级别
     */
    @Override
    public List<SYS_CODE> selectLevels(){
        List<SYS_CODE> list=new ArrayList<>();
        Criteria criteria= Cnd.cri();
        criteria.where().andEquals("parent_id","1");
        list=dao.query(SYS_CODE.class,criteria);
        if (list.size()>0){
            return list;
        }else {
            return null;
        }
    }
    /**
     * 机构类别
     */
    @Override
    public List<SYS_CODE> selectCategorys(){
        List<SYS_CODE> list=new ArrayList<>();
        Criteria criteria= Cnd.cri();
        criteria.where().andEquals("parent_id","20");
        list=dao.query(SYS_CODE.class,criteria);
        if (list.size()>0){
            return list;
        }else {
            return null;
        }
    }

    /**
     * 隶属关系
     * @return
     */
    @Override
    public List<SYS_CODE> selectAffiliations(){
        List<SYS_CODE> list=new ArrayList<>();
        Criteria criteria= Cnd.cri();
        criteria.where().andEquals("parent_id","480");
        list=dao.query(SYS_CODE.class,criteria);
        if (list.size()>0){
            return list;
        }else {
            return null;
        }
    }

    @Override
    public SYS_CODE selectCodeById(String id){
        List<SYS_CODE> list=new ArrayList<>();
        Criteria criteria= Cnd.cri();
        criteria.where().andEquals("id",id);
        list=dao.query(SYS_CODE.class,criteria);
        if (list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
    }

    /**
     * 民族
     */
    @Override
    public List<SYS_CODE> selectNationality(){
        List<SYS_CODE> list=new ArrayList<>();
        Criteria criteria= Cnd.cri();
        criteria.where().andEquals("parent_id","50");
        list=dao.query(SYS_CODE.class,criteria);
        if (list.size()>0){
            return list;
        }else {
            return null;
        }
    }

    /**
     * 政治面貌
     */
    public List<SYS_CODE> selectParty(){
        List<SYS_CODE> list=new ArrayList<>();
        Criteria criteria= Cnd.cri();
        criteria.where().andEquals("parent_id","120");
        list=dao.query(SYS_CODE.class,criteria);
        if (list.size()>0){
            return list;
        }else {
            return null;
        }
    }

    /**
     * 现职务层次
     * @return
     */
    public List<SYS_CODE> selectPosition(){
        List<SYS_CODE> list=new ArrayList<>();
        Criteria criteria= Cnd.cri();
        criteria.where().andEquals("parent_id","140");
        list=dao.query(SYS_CODE.class,criteria);
        if (list.size()>0){
            return list;
        }else {
            return null;
        }
    }

    //现职级
    public List<SYS_CODE> selectPositionLevel(){
        List<SYS_CODE> list=new ArrayList<>();
        Criteria criteria= Cnd.cri();
        criteria.where().andEquals("parent_id","165");
        list=dao.query(SYS_CODE.class,criteria);
        if (list.size()>0){
            return list;
        }else {
            return null;
        }
    }
    //编制类型
    public List<SYS_CODE> selectPoliticalStatus(){
        List<SYS_CODE> list=new ArrayList<>();
        Criteria criteria= Cnd.cri();
        criteria.where().andEquals("parent_id","190");
        list=dao.query(SYS_CODE.class,criteria);
        if (list.size()>0){
            return list;
        }else {
            return null;
        }
    }
}
