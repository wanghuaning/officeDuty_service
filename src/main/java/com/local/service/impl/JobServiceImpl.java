package com.local.service.impl;

import com.local.entity.elsys.ElSysDept;
import com.local.entity.elsys.ElSysJob;
import com.local.entity.elsys.ElSysRole;
import com.local.service.DeptService;
import com.local.service.JobService;
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
import java.util.Optional;


@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private Dao dao;

    @Autowired
    private DeptService deptService;

    @Override
    public ElSysJob findById(Long id){
        Criteria cri= Cnd.cri();
        cri.where().andEquals("ID",id);
        ElSysJob job = dao.fetch(ElSysJob.class,cri);
        return job;
    }

    @Override
    public void create(ElSysJob job){
        dao.insert(job);
    }

    @Override
    public void update(ElSysJob job){

    }

    @Override
    public void delete(Long id){

    }

    @Override
    public QueryResult selectAllByParam(String name, int enabled, int pageSize, int pageNumber){
        Pager pager=new Pager();
        pager.setPageNumber(pageNumber+1);
        pager.setPageSize(pageSize);
        List<ElSysJob> resultList = new ArrayList<>();
        Criteria cri= Cnd.cri();
        if (!StrUtils.isBlank(name)){
            cri.where().andLike("NAME",name);
        }
        if (!StrUtils.isBlank(enabled) && enabled != -1){
            cri.where().andEquals("ENABLED",enabled);
        }
        cri.getOrderBy().asc("SORT");
        List<ElSysJob> jobs = dao.query(ElSysJob.class,cri,pager);
        for(ElSysJob j:jobs){
            j.setDept(deptService.findById(j.getDeptId()));
            if (j.getDept().getPid() != 0){
                ElSysDept dept = deptService.findById(j.getDept().getPid());
                j.setDeptSuperiorName(dept.getName());
            }
            resultList.add(j);
        }
        if (StrUtils.isBlank(pager)){
            pager=new Pager();
        }
        pager.setRecordCount(dao.count(ElSysJob.class,cri));
        QueryResult queryResult=new QueryResult(resultList,pager);
        return queryResult;
    }

}
