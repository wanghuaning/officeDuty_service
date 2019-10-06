package com.local.service.impl;

import com.local.entity.sys.SYS_AREA;
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
}
