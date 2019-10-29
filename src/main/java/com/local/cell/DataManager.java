package com.local.cell;

import com.local.entity.sys.SYS_People;
import com.local.entity.sys.SYS_Rank;
import com.local.entity.sys.SYS_UNIT;
import com.local.model.RankModel;
import com.local.service.PeopleService;
import com.local.service.RankService;
import com.local.service.UnitService;
import com.local.util.ExcelFileGenerator;
import org.apache.poi.ss.formula.functions.Rank;
import org.apache.poi.ss.usermodel.Workbook;
import org.nutz.lang.random.R;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
                    rankModel.setBirthday(people.getBirthday());
                    rankModel.setEducation(people.getEducation());
                    rankModel.setWorkday(people.getWorkday());
                    rankModel.setDemocracy(ranks.getDemocracy());
                    if (rank!=null){
                        rankModel.setNowRank(rank.getName());
                    }
                    rankModel.setNewRank(ranks.getName());
                    rankModel.setNewRankTime(ranks.getCreateTime());
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
}
