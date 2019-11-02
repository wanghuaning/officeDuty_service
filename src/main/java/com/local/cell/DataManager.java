package com.local.cell;

import com.local.entity.sys.*;
import com.local.model.ApproalModel;
import com.local.model.RankModel;
import com.local.model.ReimbursementModel;
import com.local.service.*;
import com.local.util.DateUtil;
import com.local.util.ExcelFileGenerator;
import org.apache.poi.ss.formula.functions.Rank;
import org.apache.poi.ss.usermodel.Workbook;
import org.nutz.lang.random.R;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataManager {

    public static List<RankModel> filingList(UnitService unitService, String unitName, HttpServletResponse response,
                                   PeopleService peopleService, RankService rankService)throws Exception{
        SYS_UNIT unit=unitService.selectUnitByName(unitName);
        List<SYS_People> peoples=peopleService.selectPeoplesByUnitId(unit.getId(),"0");
        List<RankModel> rankModels=new ArrayList<>();
        if (peoples!=null){
            int order=0;
            for (SYS_People people:peoples){
                SYS_Rank ranks=rankService.selectNotAproRanksByPid(people.getId());
                if (ranks!=null){
                    order+=1;
                    SYS_Rank rank=rankService.selectAprodRanksByPid(people.getId());
                    RankModel rankModel=new RankModel();
                    rankModel.setName(people.getName());
                    rankModel.setIdcard(people.getIdcard());
                    rankModel.setSex(people.getSex());
                    rankModel.setNationality(people.getNationality());
                    rankModel.setBirthday(DateUtil.parseDateYMD(people.getBirthday()));
                    rankModel.setEducation(people.getEducation());
                    rankModel.setWorkday(DateUtil.parseDateYMD(people.getWorkday()));
                    rankModel.setDemocracy(ranks.getDemocracy());
                    if (rank!=null){
                        if (rank.getCreateTime()!=null){
                            rankModel.setNowRank(rank.getName()+"\n"+DateUtil.dateToString(rank.getCreateTime()));
                        }else {
                            rankModel.setNowRank(rank.getName());
                        }
                    }
                    rankModel.setNewRank(ranks.getName());
                    if (ranks.getCreateTime()!=null){
                        rankModel.setNewRankTime(DateUtil.parseDateYMD(ranks.getCreateTime()));
                    }
                    rankModel.setDetail(ranks.getDetail());
                    rankModel.setOrder(order);
                    rankModels.add(rankModel);
                }
            }
            if (rankModels.size()>0){
                String[] arr={"order","name","idcard","sex","nationality","birthday","education","workday","democracy",
                        "nowRank","newRank","newRankTime","detail"};
                ClassPathResource resource=new ClassPathResource("exportExcel/filingListExport.xls");
                String path=resource.getFile().getPath();
                String[] arr1=new String[]{unit.getName(),unit.getContactNumber(),unit.getContact()};
                Workbook temp= ExcelFileGenerator.getTeplet(path);
                ExcelFileGenerator excelFileGenerator=new ExcelFileGenerator();
                excelFileGenerator.setExcleNAME(response,"公务员晋升职级人员备案名册.xls");
                String name=unit.getName()+"公务员晋升职级人员备案名册";
                excelFileGenerator.createTitleExcel(temp.getSheet("备案名册"),name);
                excelFileGenerator.createExcelFileFixedRow(temp.getSheet("备案名册"),1,new int[]{2,6,11},arr1);
                excelFileGenerator.createExcelFile(temp.getSheet("备案名册"),6,rankModels,arr);
                excelFileGenerator.createExcelFileFixedMergeRow(temp.getSheet("备案名册"),rankModels.size()+5,new int[]{0},new String[]{"填表要求：备注需注明军转干部、实名制管理干部、领导职务干部"},rankModels.size()+5,rankModels.size()+5,0,12);
                temp.write(response.getOutputStream());
                temp.close();
                return rankModels;
            }else {
                return null;
            }
        }else {
            return null;
        }
    }
    public static ReimbursementModel exportPeopleData(HttpServletResponse response, PeopleService peopleService, String peopleId,
                                                      RankService rankService, EducationService educationService,
                                                      AssessmentService assessmentService,UnitService unitService)throws Exception{
        SYS_People people=peopleService.selectPeopleById(peopleId);
        if (people!=null){
            ReimbursementModel reimbursementModel=new ReimbursementModel();
            reimbursementModel.setName(people.getName());
            reimbursementModel.setSex(people.getSex());
            int startYear=DateUtil.getYear(people.getBirthday());
            int endYear=DateUtil.getYear(new Date());
            int startMonth=DateUtil.getMonth(people.getBirthday());
            int endMonth=DateUtil.getMonth(new Date());
            int year=0;
            if (endMonth>startMonth){
                year=endYear-startYear;
            }else {
                year=endYear-startYear-1;
            }
            String years=DateUtil.dateToString(people.getBirthday())+"\n("+year+")";
            reimbursementModel.setYears(years);
            reimbursementModel.setBirthplace(people.getBirthplace());
            reimbursementModel.setNationality(people.getNationality());
            reimbursementModel.setParty(people.getParty());
            SYS_Rank rank=rankService.selectAprodRanksByPid(peopleId);
            SYS_UNIT unit=unitService.selectUnitById(people.getUnitId());
            if (rank!=null){
                String unitAndDuty=unit.getName()+rank.getName();
                reimbursementModel.setUnitAndDuty(unitAndDuty);
                reimbursementModel.setWorkday(DateUtil.dateToString(DateUtil.parseDateYMD(people.getWorkday())));
                reimbursementModel.setDutyAndRank(rank.getName());
                reimbursementModel.setDutyAndRankTime(DateUtil.dateToString(rank.getCreateTime()));
                reimbursementModel.setDeposeRank(rank.getName());
            }
            SYS_Education education=educationService.selectEducationByPidAndSchoolOrderByTime(peopleId,"全日制教育");
            SYS_Education education1=educationService.selectEducationByPidAndSchoolOrderByTime(peopleId,"在职教育");
            if (education!=null){
                reimbursementModel.setFullTimeEducation(education.getName());
                reimbursementModel.setFullTimeSchool(education.getSchool()+"\n"+education.getProfession());
            }
            if (education1!=null){
                reimbursementModel.setWorkEducation(education1.getName());
                reimbursementModel.setWorkSchool(education1.getSchool()+"\n"+education1.getProfession());
            }
            List<SYS_Assessment> assessment=assessmentService.selectKaoHeByPidAndResult(peopleId,"优秀");
            if (assessment!=null){
                reimbursementModel.setSuperYears(String.valueOf(assessment.size()));
            }else{
                reimbursementModel.setSuperYears("0");
            }
            List<SYS_Assessment> assessment1=assessmentService.selectKaoHeByPidAndResult(peopleId,"优秀");
            if (assessment!=null){
                reimbursementModel.setSuperYears(String.valueOf(assessment.size()));
            }else{
                reimbursementModel.setSuperYears("0");
            }
            SYS_Rank rank1=rankService.selectNotAproRanksByPid(peopleId);
            if (rank1!=null){
                reimbursementModel.setIntendedRank(rank1.getName());
            }
                ClassPathResource resource=new ClassPathResource("exportExcel/intendeAndDepose.xls");
                String path=resource.getFile().getPath();
                Workbook temp= ExcelFileGenerator.getTeplet(path);
                ExcelFileGenerator excelFileGenerator=new ExcelFileGenerator();
                excelFileGenerator.setExcleNAME(response,"公务员职级任免审批表.xls");
                excelFileGenerator.createReimbursementExcel(temp.getSheet("任免审批表"),reimbursementModel);
                temp.write(response.getOutputStream());
                temp.close();
                return reimbursementModel;
        }else {
            return null;
        }
    }

    public static ApproalModel approvalExport(UnitService unitService, String unitName, HttpServletResponse response,
                               PeopleService peopleService, RankService rankService)throws Exception{
        SYS_UNIT unit=unitService.selectUnitByName(unitName);
        List<SYS_People> peoples=peopleService.selectPeoplesByUnitId(unit.getId(),"0");
        ApproalModel approalModel=new ApproalModel();
        approalModel.setUnitName(unitName);
        approalModel.setUnitType("");
        approalModel.setLevel(unit.getLevel());
        if (unit.getOfficialNum()>0){
            approalModel.setOfficialNum(String.valueOf(unit.getOfficialNum()));
        }
        int researcherTotal=0;
        if (unit.getOneTowResearcherNum()>0){
            approalModel.setOneTowResearcherNum(String.valueOf(unit.getOneTowResearcherNum()));
            researcherTotal+=unit.getOneTowResearcherNum();
        }
        if (unit.getThreeFourResearcherNum()>0){
            approalModel.setThreeFourResearcherNum(String.valueOf(unit.getThreeFourResearcherNum()));
            researcherTotal+=unit.getThreeFourResearcherNum();
        }
        approalModel.setResearcherTotal(String.valueOf(researcherTotal));
        if (unit.getOneResearcherNum()>0){
            approalModel.setOneResearcherNum(String.valueOf(unit.getOneResearcherNum()));
        }
        if (unit.getTowResearcherNum()>0){
            approalModel.setTowResearcherNum(String.valueOf(unit.getTowResearcherNum()));
        }
        if (unit.getThreeResearcherNum()>0){
            approalModel.setThreeResearcherNum(String.valueOf(unit.getThreeResearcherNum()));
        }
        if (unit.getFourResearcherNum()>0){
            approalModel.setFourResearcherNum(String.valueOf(unit.getFourResearcherNum()));
        }
        int clerkTotal=0;
        if (unit.getOneTowClerkNum()>0){
            approalModel.setOneTowClerkNum(String.valueOf(unit.getOneTowClerkNum()));
            clerkTotal+=unit.getOneTowClerkNum();
        }
        if (unit.getThreeFourClerkNum()>0){
            approalModel.setThreeFourClerkNum(String.valueOf(unit.getThreeFourClerkNum()));
            clerkTotal+=unit.getThreeFourClerkNum();
        }
        approalModel.setClerkTotal(String.valueOf(clerkTotal));
        if (unit.getOneClerkNum()>0){
            approalModel.setOneClerkNum(String.valueOf(unit.getOneClerkNum()));
        }
        if (unit.getTowClerkNum()>0){
            approalModel.setTowClerkNum(String.valueOf(unit.getTowClerkNum()));
        }
        if (unit.getThreeClerkNum()>0){
            approalModel.setThreeClerkNum(String.valueOf(unit.getThreeClerkNum()));
        }
        if (unit.getFourClerkNum()>0){
            approalModel.setFourClerkNum(String.valueOf(unit.getFourClerkNum()));
        }
        int oneClerkUserNum=0;//一级主任科员职数使用
        int towClerkUserNum=0;//二级主任科员职数使用
        int threeClerkUserNum=0;//三级主任科员职数使用
        int fourClerkUserNum=0;//四级主任科员职数使用
        int userTotal=0;//使用合计
        for (SYS_People people:peoples){
            SYS_Rank rank=rankService.selectAprodRanksByPid(people.getId());
            if (rank!=null){
                if ("一级主任科员".equals(rank.getName())){
                    oneClerkUserNum+=1;
                }else if ("二级主任科员".equals(rank.getName())){
                    towClerkUserNum+=1;
                }else if ("三级主任科员".equals(rank.getName())){
                    threeClerkUserNum+=1;
                }else if ("四级主任科员".equals(rank.getName())){
                    fourClerkUserNum+=1;
                }
            }
        }
        userTotal=oneClerkUserNum+towClerkUserNum+threeClerkUserNum+fourClerkUserNum;
        approalModel.setUserTotal(String.valueOf(userTotal));
       if (oneClerkUserNum>0){
           approalModel.setOneClerkUserNum(String.valueOf(oneClerkUserNum));
       }
        if (towClerkUserNum>0){
            approalModel.setTowClerkUserNum(String.valueOf(towClerkUserNum));
        }
        if (threeClerkUserNum>0){
            approalModel.setThreeClerkUserNum(String.valueOf(threeClerkUserNum));
        }
        if (fourClerkUserNum>0){
            approalModel.setFourClerkUserNum(String.valueOf(fourClerkUserNum));
        }

        int vacancyTotal=0;
        if (unit.getOneClerkNum()>oneClerkUserNum){
            approalModel.setOneClerkVacancyNum(String.valueOf(unit.getOneClerkNum()-oneClerkUserNum));
            vacancyTotal+=unit.getOneClerkNum()-oneClerkUserNum;
        }
        if (unit.getTowClerkNum()>towClerkUserNum){
            approalModel.setTowClerkVacancyNum(String.valueOf(unit.getTowClerkNum()-towClerkUserNum));
            vacancyTotal+=unit.getTowClerkNum()-towClerkUserNum;
        }
        if (unit.getThreeClerkNum()>threeClerkUserNum){
            approalModel.setThreeClerkVacancyNum(String.valueOf(unit.getThreeClerkNum()-threeClerkUserNum));
            vacancyTotal+=unit.getThreeClerkNum()-threeClerkUserNum;
        }
        if (unit.getFourClerkNum()>fourClerkUserNum){
            approalModel.setFourClerkVacancyNum(String.valueOf(unit.getFourClerkNum()-fourClerkUserNum));
            vacancyTotal+=unit.getFourClerkNum()-fourClerkUserNum;
        }
        approalModel.setVacancyTotal(String.valueOf(vacancyTotal));
        ClassPathResource resource=new ClassPathResource("exportExcel/approveRank.xls");
        String path=resource.getFile().getPath();
        Workbook temp= ExcelFileGenerator.getTeplet(path);
        ExcelFileGenerator excelFileGenerator=new ExcelFileGenerator();
        excelFileGenerator.setExcleNAME(response,"公务员职级职数使用审批表.xls");
        excelFileGenerator.createApprovalExcel(temp.getSheet("职数使用审批表"),approalModel);
        temp.write(response.getOutputStream());
        temp.close();
        return approalModel;
    }
}
