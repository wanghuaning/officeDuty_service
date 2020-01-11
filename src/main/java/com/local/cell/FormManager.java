package com.local.cell;

import com.local.entity.sys.SYS_People;
import com.local.entity.sys.SYS_Rank;
import com.local.entity.sys.SYS_UNIT;
import com.local.entity.sys.Sys_Approal;
import com.local.model.FormRankModel;
import com.local.model.RetireModel;
import com.local.service.ApprovalService;
import com.local.service.RankService;
import com.local.service.UnitService;
import com.local.util.StrUtils;

import java.util.List;

public class FormManager {
    public static FormRankModel getApprovalDataCell(FormRankModel approalModel, String[] arr, List<SYS_People> peoples, RankService rankService, ApprovalService approvalService, UnitService unitService) {
        long oneResearcherUserNum = 0,towResearcherUserNum = 0,threeResearcherUserNum = 0,fourResearcherUserNum = 0,oneClerkUserNum = 0,towClerkUserNum = 0;
        long threeClerkUserNum = 0,fourClerkUserNum = 0,oneClerk=0l,towClerk=0l,probation=0l;
        int oneResearcherNumTurn=0,towResearcherNumTurn=0,threeResearcherNumTurn=0,fourResearcherNumTurn=0,oneClerkNumTurn=0,towClerkNumTurn=0;
        int threeClerkNumTurn=0,fourClerkNumTurn=0,oneClerkTurn=0,towClerkTurn=0,probationTurn=0;
        long oneResearcherDraftingNum=0l,towResearcherDraftingNum=0l,threeResearcherDraftingNum=0l,fourResearcherDraftingNum=0l,oneClerkDraftingNum=0l;
        long towClerkDraftingNum=0l,threeClerkDraftingNum=0l,fourClerkDraftingNum=0l;
        for (int i=0;i<arr.length;i++){
            SYS_UNIT unit=unitService.selectUnitById(arr[i]);
            if (unit.getOneResearcherNum() > 0) {
                approalModel.setOneResearcherNum(unit.getOneResearcherNum());
            }
            if (unit.getTowResearcherNum() > 0) {
                approalModel.setTowResearcherNum(unit.getTowResearcherNum());
            }
            if (unit.getThreeResearcherNum() > 0) {
                approalModel.setThreeResearcherNum(unit.getThreeResearcherNum());
            }
            if (unit.getFourResearcherNum() > 0) {
                approalModel.setFourResearcherNum(unit.getFourResearcherNum());
            }
            if (unit.getOneClerkNum() > 0) {
                approalModel.setOneClerkNum(unit.getOneClerkNum());
            }
            if (unit.getTowClerkNum() > 0) {
                approalModel.setTowClerkNum(unit.getTowClerkNum());
            }
            if (unit.getThreeClerkNum() > 0) {
                approalModel.setThreeClerkNum(unit.getThreeClerkNum());
            }
            if (unit.getFourClerkNum() > 0) {
                approalModel.setFourClerkNum(unit.getFourClerkNum());
            }
            //剩余
            if (unit.getOneClerkNum() > oneClerkUserNum) {
                approalModel.setOneClerkNumLave(unit.getOneClerkNum() - oneClerkUserNum);
            }
            if (unit.getTowClerkNum() > towClerkUserNum) {
                approalModel.setTowClerkNumLave(unit.getTowClerkNum() - towClerkUserNum);
            }
            if (unit.getThreeClerkNum() > threeClerkUserNum) {
                approalModel.setThreeClerkNumLave(unit.getThreeClerkNum() - threeClerkUserNum);
            }
            if (unit.getFourClerkNum() > fourClerkUserNum) {
                approalModel.setFourClerkNumLave(unit.getFourClerkNum() - fourClerkUserNum);
            }
            if (unit.getOneResearcherNum() > oneResearcherUserNum) {
                approalModel.setOneResearcherNumLave(unit.getOneResearcherNum() - oneResearcherUserNum);
            }
            if (unit.getTowResearcherNum() > towResearcherUserNum) {
                approalModel.setTowResearcherNumLave(unit.getTowResearcherNum() - towResearcherUserNum);
            }
            if (unit.getThreeResearcherNum() > threeResearcherUserNum) {
                approalModel.setThreeResearcherNumLave(unit.getThreeResearcherNum() - threeResearcherUserNum);
            }
            if (unit.getFourResearcherNum() > fourResearcherUserNum) {
                approalModel.setFourResearcherNumLave(unit.getFourResearcherNum() - fourResearcherUserNum);
            }
            //套转信息
            String unitId=arr[i];
            List<SYS_Rank> oneranks = rankService.selectRanksFlagByUnitId(unitId, "是", "一级调研员");
            List<SYS_Rank> towranks = rankService.selectRanksFlagByUnitId(unitId, "是", "二级调研员");
            if (oneranks != null) {
                oneResearcherNumTurn+=oneranks.size();
            }
            if (towranks != null) {
                towResearcherNumTurn+=towranks.size();
            }
            List<SYS_Rank> threeranks = rankService.selectRanksFlagByUnitId(unitId, "是", "三级调研员");
            if (threeranks != null) {
                threeResearcherNumTurn+=threeranks.size();
            }
            List<SYS_Rank> fourranks = rankService.selectRanksFlagByUnitId(unitId, "是", "四级调研员");
            if (fourranks != null) {
                fourResearcherNumTurn+=fourranks.size();
            }
            List<SYS_Rank> oneKranks = rankService.selectRanksFlagByUnitId(unitId, "是", "一级主任科员");
            if (oneKranks != null) {
                oneClerkNumTurn+=oneKranks.size();
            }
            List<SYS_Rank> towKranks = rankService.selectRanksFlagByUnitId(unitId, "是", "二级主任科员");
            if (towKranks != null) {
                towClerkNumTurn+=towKranks.size();
            }
            List<SYS_Rank> threeKranks = rankService.selectRanksFlagByUnitId(unitId, "是", "三级主任科员");
            if (threeKranks != null) {
                threeClerkNumTurn+=threeKranks.size();
            }
            List<SYS_Rank> fourKranks = rankService.selectRanksFlagByUnitId(unitId, "是", "四级主任科员");
            if (fourKranks != null) {
                fourClerkNumTurn+=fourKranks.size();
            }
            List<SYS_Rank> oneKYranks = rankService.selectRanksFlagByUnitId(unitId, "是", "一级科员");
            if (oneKYranks != null) {
                oneClerkTurn+=oneKYranks.size();
            }
            List<SYS_Rank> towKYranks = rankService.selectRanksFlagByUnitId(unitId, "是", "二级科员");
            if (towKYranks != null) {
                towClerkTurn+=towKYranks.size();
            }
            List<SYS_Rank> shiranks = rankService.selectRanksFlagByUnitId(unitId, "是", "试用期");
            if (shiranks != null) {
                probationTurn+=shiranks.size();
            }
            //拟定使用
            Sys_Approal approal=approvalService.selectApproval(unitId,"0");
            if (approal!=null){
                oneResearcherDraftingNum+=StrUtils.strToLong(approal.getOneResearcherDraftingNum());
                towResearcherDraftingNum+=StrUtils.strToLong(approal.getTowResearcherDraftingNum());
                threeResearcherDraftingNum+=StrUtils.strToLong(approal.getThreeResearcherDraftingNum());
                fourResearcherDraftingNum+=StrUtils.strToLong(approal.getFourResearcherDraftingNum());
                oneClerkDraftingNum+=StrUtils.strToLong(approal.getOneClerkDraftingNum());
                towClerkDraftingNum+=StrUtils.strToLong(approal.getTowResearcherDraftingNum());
                threeClerkDraftingNum+=StrUtils.strToLong(approal.getThreeResearcherDraftingNum());
                fourClerkDraftingNum+=StrUtils.strToLong(approal.getFourResearcherDraftingNum());
            }
        }
        for (SYS_People people : peoples) {
            SYS_Rank rank = rankService.selectAprodRanksByPid(people.getId());
            if (rank != null) {
                if ("一级主任科员".equals(rank.getName())) {
                    oneClerkUserNum += 1;
                } else if ("二级主任科员".equals(rank.getName())) {
                    towClerkUserNum += 1;
                } else if ("三级主任科员".equals(rank.getName())) {
                    threeClerkUserNum += 1;
                } else if ("四级主任科员".equals(rank.getName())) {
                    fourClerkUserNum += 1;
                }else if ("一级调研员".equals(rank.getName())) {
                    oneResearcherUserNum += 1;
                } else if ("二级调研员".equals(rank.getName())) {
                    towResearcherUserNum += 1;
                } else if ("三级调研员".equals(rank.getName())) {
                    threeResearcherUserNum += 1;
                } else if ("四级调研员".equals(rank.getName())) {
                    fourResearcherUserNum += 1;
                } else if ("一级科员".equals(rank.getName())) {
                    oneClerk += 1;
                } else if ("二级科员".equals(rank.getName())) {
                    towClerk += 1;
                } else if ("试用期".equals(rank.getName())) {
                    probation += 1;
                }
            }
        }
        approalModel.setOneResearcherNumNow(oneResearcherUserNum);
        approalModel.setTowResearcherNumNow(towResearcherUserNum);
        approalModel.setThreeResearcherNumNow(threeResearcherUserNum);
        approalModel.setFourResearcherNumNow(fourResearcherUserNum);
        approalModel.setOneClerkNumNow(oneClerkUserNum);
        approalModel.setTowClerkNumNow(towClerkUserNum);
        approalModel.setThreeClerkNumNow(fourClerkUserNum);
        approalModel.setFourClerkNumNow(oneResearcherUserNum);
        approalModel.setOneClerkNow(oneClerk);
        approalModel.setTowClerkNow(towClerk);
        approalModel.setProbationNow(probation);
        approalModel.setOneResearcherNumTurn(oneResearcherNumTurn);
        approalModel.setTowResearcherNumTurn(towResearcherNumTurn);
        approalModel.setThreeResearcherNumTurn(threeResearcherNumTurn);
        approalModel.setFourResearcherNumTurn(fourResearcherNumTurn);
        approalModel.setOneClerkNumTurn(oneClerkNumTurn);
        approalModel.setTowClerkNumTurn(towClerkNumTurn);
        approalModel.setThreeClerkNumTurn(threeClerkNumTurn);
        approalModel.setFourClerkNumTurn(fourClerkNumTurn);
        approalModel.setOneClerkTurn(oneClerkTurn);
        approalModel.setTowClerkTurn(towClerkTurn);
        approalModel.setProbationTurn(probationTurn);
        approalModel.setOneResearcherDraftingNum(oneResearcherDraftingNum);
        approalModel.setTowResearcherDraftingNum(towResearcherDraftingNum);
        approalModel.setThreeResearcherDraftingNum(threeResearcherDraftingNum);
        approalModel.setFourResearcherDraftingNum(fourResearcherDraftingNum);
        approalModel.setOneClerkDraftingNum(oneClerkDraftingNum);
        approalModel.setTowClerkDraftingNum(towClerkDraftingNum);
        approalModel.setThreeClerkDraftingNum(threeClerkDraftingNum);
        approalModel.setFourClerkDraftingNum(fourClerkDraftingNum);
        return approalModel;
    }


}
