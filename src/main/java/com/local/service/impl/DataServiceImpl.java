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
    public void inserData(SYS_Data data) {
        dao.insert(data);
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "修改备份数据", type = "U")
    public void updateData(SYS_Data data) {
        dao.update(data);
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "删除备份数据", type = "U")
    public void deleteData(String id) {
        dao.delete(SYS_Data.class, id);
    }

    @Override
    public SYS_Data selectDataById(String id) {
        List<SYS_Data> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("id", id);
        list = dao.query(SYS_Data.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<SYS_Data> selectDataByUnitId(String unitId) {
        List<SYS_Data> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("unit_Id", unitId);
        list = dao.query(SYS_Data.class, cir);
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    @Override
    public SYS_Data selectDataByProcessId(String processId) {
        List<SYS_Data> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("process_Id", processId);
        list = dao.query(SYS_Data.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public SYS_Digest selectDigestById(String id) {
        List<SYS_Digest> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("id", id);
        list = dao.query(SYS_Digest.class, cir);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<SYS_Digest> selectDigestsByUnitId(String unitId) {
        List<SYS_Digest> list = new ArrayList<>();
        Criteria cir = Cnd.cri();
        cir.where().andEquals("unit_Id", unitId);
        list = dao.query(SYS_Digest.class, cir);
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "新增消化情况", type = "C")
    public void insertDigest(SYS_Digest digest) {
        dao.insert(digest);
    }

    @Override
    @Transactional//声明式事务管理
    @SLog(tag = "修改消化情况", type = "U")
    public void updateDigest(SYS_Digest digest) {
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
    private ApprovalService approvalService;
    private final static Gson gson = new Gson();

    public SYS_Data saveApprovalData(Sys_Process process, String approvalType, String unitId) {
        String dataId = unitId + process.getId();
        String dataType="上行";
        SYS_Data data = DataManager.saveData(dataId, process.getId(), "上行", unitId, dataService);
        if (!approvalType.contains("职级")) {
            List<SYS_USER> users = userService.selectUsersByUnitId(unitId);
            if (users != null) {
                DataManager.saveDataInfo(dataId, "上行", unitId, dataInfoService, "user", gson.toJson(users), gson.toJson(users));
            }
            List<SYS_Digest> digests = dataService.selectDigestsByUnitId(unitId);
            if (digests != null) {
                DataManager.saveDataInfo(dataId, "上行", unitId, dataInfoService, "digest", gson.toJson(digests), gson.toJson(digests));
            }
            List<SYS_People> peoples = peopleService.selectPeoplesByUnitId(unitId);
            if (peoples != null) {
                DataManager.saveDataInfo(dataId, "上行", unitId, dataInfoService, "people", gson.toJson(peoples),  gson.toJson(peoples));
            }
            List<SYS_Duty> duties = dutyService.selectDutysByUnitId(unitId);
            if (duties != null) {
                DataManager.saveDataInfo(dataId, "上行", unitId, dataInfoService, "duty", gson.toJson(duties), gson.toJson(duties));
            }
            List<SYS_Rank> ranks = rankService.selectRanksByUnitId(unitId);
            if (ranks != null) {
                DataManager.saveDataInfo(dataId, "上行", unitId, dataInfoService, "rank", gson.toJson(ranks), gson.toJson(ranks));
            }
            List<SYS_Reward> rewards = rewardService.selectRewardsByUnitId(unitId);
            if (rewards != null) {
                DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "reward", gson.toJson(rewards), gson.toJson(rewards));
            }
            List<SYS_Education> educations = educationService.selectEducationsByUnitId(unitId);
            if (educations != null) {
                DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "education", gson.toJson(educations), gson.toJson(educations));
            }
            List<SYS_Assessment> assessments = assessmentService.selectAssessmentsByUnitId(unitId);
            if (assessments != null) {
                DataManager.saveDataInfo(dataId, dataType, unitId, dataInfoService, "assessment", gson.toJson(assessments),  gson.toJson(assessments));
            }
        } else {
            List<Sys_Approal> approals=new ArrayList<>();
            Sys_Approal approal = approvalService.selectApproval(unitId,"0");
            if (approal != null) {
                approals.add(approal);
                DataManager.saveDataInfo(dataId, "上行", unitId, dataInfoService, "approval", gson.toJson(approals), gson.toJson(approals));
            }
        }
        SYS_UNIT unit = unitService.selectUnitById(unitId);
        if (unit != null) {
            DataManager.saveDataInfo(dataId, "上行", unitId, dataInfoService, "unit", gson.toJson(unit), gson.toJson(unit));
        }
        List<Sys_Process> processes = processService.selectApprProcess(unitId);
        if (processes != null) {
            DataManager.saveDataInfo(dataId, "上行", unitId, dataInfoService, "process", gson.toJson(processes), gson.toJson(processes));
        }
        return data;
    }
}
