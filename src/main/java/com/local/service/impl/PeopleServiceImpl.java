package com.local.service.impl;

import com.local.entity.elsys.ElSysRole;
import com.local.entity.sys.SYS_People;
import com.local.service.PeopleService;
import com.local.util.StrUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PeopleServiceImpl implements PeopleService {

    @Autowired
    private Dao dao;
    @Override
    public QueryResult selectPeoples(int pageSize, int pageNumber,String unitId,String name,String idcard,String politicalStatus,String enabled){
        Pager pager=new Pager();
        pager.setPageNumber(pageNumber+1);
        pager.setPageSize(pageSize);
        List<SYS_People> peopleList=new ArrayList<>();
        Criteria cri= Cnd.cri();
        if (!StrUtils.isBlank(name)){//市
            cri.where().andLike("name","%"+name+"%");
        }
        if (!StrUtils.isBlank(idcard)){
            cri.where().andLike("idcard","%"+idcard+"%");
        }
        if (!StrUtils.isBlank(politicalStatus)){
            if ("office".equals(politicalStatus)){
                cri.where().andEquals("political_Status","行政编制");
            }else if ("enterprise".equals(politicalStatus)){
                cri.where().andEquals("political_Status","事业编制（参公）");
            }else if ("other".equals(politicalStatus)){
                cri.where().andEquals("political_Status","其他");
            }
        }
        if (!StrUtils.isBlank(enabled)){
            if ("enabled".equals(enabled)){
                cri.where().andEquals("enabled","0");
            }else if ("notEnabled".equals(enabled)){
                cri.where().andEquals("enabled","1");
            }
        }
        cri.where().andEquals("unit_Id",unitId);
        cri.getOrderBy().asc("people_Order");
        peopleList = dao.query(SYS_People.class,cri,pager);
        if (StrUtils.isBlank(pager)){
            pager=new Pager();
        }
        pager.setRecordCount(dao.count(SYS_People.class,cri));
        QueryResult queryResult=new QueryResult(peopleList,pager);
        return queryResult;
    }

}
