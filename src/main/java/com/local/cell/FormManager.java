package com.local.cell;

import com.local.entity.sys.SYS_People;
import com.local.entity.sys.SYS_Rank;
import com.local.entity.sys.SYS_UNIT;
import com.local.entity.sys.Sys_Approal;
import com.local.model.FormRankModel;
import com.local.model.UnitModel;
import com.local.service.ApprovalService;
import com.local.service.RankService;
import com.local.util.StrUtils;

import java.util.List;

public class FormManager {
    public static FormRankModel getApprovalDataCell(FormRankModel approalModel, SYS_UNIT unit, List<SYS_People> peoples, RankService rankService,ApprovalService approvalService) {
        int researcherTotal = 0;
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
        long oneResearcherUserNum = 0;//一级调研员职数使用
        long towResearcherUserNum = 0;//二级调研员职数使用
        long threeResearcherUserNum = 0;//三级调研员职数使用
        long fourResearcherUserNum = 0;//四级调研员职数使用
        long oneClerkUserNum = 0;//一级主任科员职数使用
        long towClerkUserNum = 0;//二级主任科员职数使用
        long threeClerkUserNum = 0;//三级主任科员职数使用
        long fourClerkUserNum = 0;//四级主任科员职数使用
        long oneClerk=0l;//核准一级科员
        long towClerk=0l;//核准二级科员
        long probation=0l;//核准试用期
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
        getTurnRank(rankService, unit.getId(), approalModel);
        getNiRank( unit.getId(), approvalService, approalModel);
        return approalModel;
    }

    public static void getTurnRank(RankService rankService, String unitId, FormRankModel um) {
        List<SYS_Rank> oneranks = rankService.selectRanksFlagByUnitId(unitId, "是", "一级调研员");
        List<SYS_Rank> towranks = rankService.selectRanksFlagByUnitId(unitId, "是", "二级调研员");
        if (oneranks != null) {
            um.setOneResearcherNumTurn(oneranks.size());
        }else {
            um.setOneResearcherNumTurn(0);
        }
        if (towranks != null) {
            um.setTowResearcherNumTurn(towranks.size());
        }else {
            um.setTowResearcherNumTurn(0);
        }
        List<SYS_Rank> threeranks = rankService.selectRanksFlagByUnitId(unitId, "是", "三级调研员");
        if (threeranks != null) {
            um.setThreeResearcherNumTurn(threeranks.size());
        }else {
            um.setThreeResearcherNumTurn(0);
        }
        List<SYS_Rank> fourranks = rankService.selectRanksFlagByUnitId(unitId, "是", "四级调研员");
        if (fourranks != null) {
            um.setFourResearcherNumTurn(fourranks.size());
        }else {
            um.setFourResearcherNumTurn(0);
        }
        List<SYS_Rank> oneKranks = rankService.selectRanksFlagByUnitId(unitId, "是", "一级主任科员");
        if (oneKranks != null) {
            um.setOneClerkNumTurn(oneKranks.size());
        }else {
            um.setOneClerkNumTurn(0);
        }
        List<SYS_Rank> towKranks = rankService.selectRanksFlagByUnitId(unitId, "是", "二级主任科员");
        if (towKranks != null) {
            um.setTowClerkNumTurn(towKranks.size());
        }else {
            um.setTowClerkNumTurn(0);
        }
        List<SYS_Rank> threeKranks = rankService.selectRanksFlagByUnitId(unitId, "是", "三级主任科员");
        if (threeKranks != null) {
            um.setThreeClerkNumTurn(threeKranks.size());
        }else {
            um.setThreeClerkNumTurn(0);
        }
        List<SYS_Rank> fourKranks = rankService.selectRanksFlagByUnitId(unitId, "是", "四级主任科员");
        if (fourKranks != null) {
            um.setFourClerkNumTurn(fourKranks.size());
        }else {
            um.setFourClerkNumTurn(0);
        }
        List<SYS_Rank> oneKYranks = rankService.selectRanksFlagByUnitId(unitId, "是", "一级科员");
        if (oneKYranks != null) {
            um.setOneClerkTurn(oneKYranks.size());
        }else {
            um.setOneClerkTurn(0);
        }
        List<SYS_Rank> towKYranks = rankService.selectRanksFlagByUnitId(unitId, "是", "二级科员");
        if (towKYranks != null) {
            um.setTowClerkTurn(towKYranks.size());
        }else {
            um.setTowClerkTurn(0);
        }
        List<SYS_Rank> shiranks = rankService.selectRanksFlagByUnitId(unitId, "是", "试用期");
        if (shiranks != null) {
            um.setProbationTurn(shiranks.size());
        }else {
            um.setProbationTurn(0);
        }
    }

    public static void getNiRank(String unitId, ApprovalService approvalService,FormRankModel um){
        Sys_Approal approal=approvalService.selectApproval(unitId,"0");
        if (approal!=null){
            um.setOneResearcherDraftingNum(StrUtils.strToLong(approal.getOneResearcherDraftingNum()));
            um.setTowResearcherDraftingNum(StrUtils.strToLong(approal.getTowResearcherDraftingNum()));
            um.setThreeResearcherDraftingNum(StrUtils.strToLong(approal.getThreeResearcherDraftingNum()));
            um.setFourResearcherDraftingNum(StrUtils.strToLong(approal.getFourResearcherDraftingNum()));
            um.setOneClerkDraftingNum(StrUtils.strToLong(approal.getOneClerkDraftingNum()));
            um.setTowClerkDraftingNum(StrUtils.strToLong(approal.getTowClerkDraftingNum()));
            um.setThreeClerkDraftingNum(StrUtils.strToLong(approal.getThreeClerkDraftingNum()));
            um.setFourClerkDraftingNum(StrUtils.strToLong(approal.getFourClerkDraftingNum()));
        }
    }
}
