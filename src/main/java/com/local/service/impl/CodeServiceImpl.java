package com.local.service.impl;

import com.local.entity.sys.SYS_AREA;
import com.local.service.CodeService;
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
      if (list.size()>0){
          return list;
      }else {
          return null;
      }
  }
}
