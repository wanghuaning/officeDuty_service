package com.local.service.impl;

import com.google.gson.Gson;
import com.local.cell.DataManager;
import com.local.common.slog.annotation.SLog;
import com.local.entity.sys.*;
import com.local.service.*;
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
import java.util.List;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private Dao dao;

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "新增备份数据", type = "C")
    public void inserData(SYS_Data data){
        dao.insert(data);
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "修改备份数据", type = "U")
    public void updateData(SYS_Data data){
        dao.update(data);
    }
    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "删除备份数据", type = "U")
    public void deleteData(String id){
        dao.delete(SYS_Data.class,id);
    }

    @Override
    public SYS_Data selectDataById(String id){
        List<SYS_Data> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andEquals("id",id);
        list=dao.query(SYS_Data.class,cir);
        if (list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
    }
    @Override
    public List<SYS_Data> selectDataByUnitId(String unitId){
        List<SYS_Data> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andEquals("unit_Id",unitId);
        list=dao.query(SYS_Data.class,cir);
        if (list.size()>0){
            return list;
        }else {
            return null;
        }
    }
    @Override
    public SYS_Data selectDataByProcessId(String processId){
        List<SYS_Data> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andEquals("process_Id",processId);
        list=dao.query(SYS_Data.class,cir);
        if (list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
    }
    @Override
    public SYS_Digest selectDigestById(String id){
        List<SYS_Digest> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andEquals("id",id);
        list=dao.query(SYS_Digest.class,cir);
        if (list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
    }

    @Override
    public List<SYS_Digest> selectDigestsByUnitId(String unitId){
        List<SYS_Digest> list=new ArrayList<>();
        Criteria cir= Cnd.cri();
        cir.where().andEquals("unit_Id",unitId);
        list=dao.query(SYS_Digest.class,cir);
        if (list.size()>0){
            return list;
        }else {
            return null;
        }
    }
    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "新增消化情况", type = "C")
   public void insertDigest(SYS_Digest digest){
        dao.insert(digest);
   }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "修改消化情况", type = "U")
   public void updateDigest(SYS_Digest digest){
        dao.update(digest);
   }

    @Autowired
    private UnitService unitService;
    @Autowired
    private PeopleService peopleService;
    @Autowired
    private RankService rankService;
    @Autowired
    private EducationService educationService;
    @Autowired
    private AssessmentService assessmentService;
    @Autowired
    private DutyService dutyService;
    @Autowired
    private RewardService rewardService;
    @Autowired
    private DataInfoService dataInfoService;
    @Autowired
    private DataService dataService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private final static Gson gson = new Gson();
    private ApprovalService approvalService;
    public SYS_Data saveApprovalData(Sys_Process process, String approvalType, String unitId){
        String dataId=unitId+process.getId();
       SYS_Data data = DataManager.saveData(dataId, process.getId(), "上行", unitId, dataService);
       if (!approvalType.contains("职级")){

       }else {
       }
        SYS_UNIT unit = unitService.selectUnitById(unitId);
        List<SYS_UNIT> unitList = new ArrayList<>();
        if (unit!=null){
            unitList.add(unit);
            DataManager.saveDataInfo(dataId, "上行", unitId, dataInfoService, "unit", gson.toJson(unitList), gson.toJson(unitList));
        }
       return data;
   }
}
