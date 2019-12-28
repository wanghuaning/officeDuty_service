package com.local.cell;

import com.local.entity.sys.SYS_People;
import com.local.entity.sys.SYS_Rank;
import com.local.entity.sys.SYS_UNIT;
import com.local.model.UnitModel;
import com.local.service.PeopleService;
import com.local.service.RankService;
import com.local.service.UnitService;
import com.local.util.DateUtil;
import com.local.util.StrUtils;
import org.nutz.lang.random.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UnitManager {
    private final static Logger logger = LoggerFactory.getLogger(UnitManager.class);

    public static void setUnitArea(SYS_UNIT unit) {
        if (!StrUtils.isBlank(unit.getAreaStrs())) {
            if (unit.getAreaStrs().length > 0) {
                StringBuffer sbf = new StringBuffer();
                for (String area : unit.getAreaStrs()) {
                    sbf.append(area);
                }
                unit.setArea(sbf.toString());
                unit.setBuildProvince(unit.getAreaStrs()[0]);
            }
            if (unit.getAreaStrs().length > 1) {
                unit.setBuildCity(unit.getAreaStrs()[1]);
            }
            if (unit.getAreaStrs().length > 2) {
                unit.setBuildCounty(unit.getAreaStrs()[2]);
            }
            unit.setAreaStrs(unit.getAreaStrs());
        }
    }

    public static List<SYS_UNIT> getUnitDataByExcel(List<Map<String, Object>> list, UnitService unitService, StringBuffer stringBuffer) throws Exception {
        List<SYS_UNIT> unitList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            SYS_UNIT unit = new SYS_UNIT();
            if (!StrUtils.isBlank(map.get("单位名称"))) {
                unit.setName(map.get("单位名称").toString());
                if (!StrUtils.isBlank(map.get("组织机构编码"))) {
                    unit.setCode(map.get("组织机构编码").toString());
                    SYS_UNIT punit = unitService.selectUnitByName(String.valueOf(map.get("上级单位")));
                    if (punit != null) {
                        unit.setParentName(punit.getName());
                    } else {
                        unit.setParentName(String.valueOf(map.get("上级单位")));
                        unit.setEnabled("2");
                    }
                    unit.setBuildProvince(StrUtils.toNullStr(map.get("所在省")));
                    unit.setBuildCity(StrUtils.toNullStr(map.get("所在市")));
                    unit.setBuildCounty(StrUtils.toNullStr(map.get("所在县（区）")));
                    unit.setArea(StrUtils.toNullStr(map.get("所在省")) + StrUtils.toNullStr(map.get("所在市")) + StrUtils.toNullStr(map.get("所在县（区）")));
                    unit.setAffiliation(StrUtils.toNullStr(map.get("隶属关系")));
                    unit.setCategory(StrUtils.toNullStr(map.get("所属序列")));
                    unit.setLevel(StrUtils.toNullStr(map.get("单位级别")));
                    unit.setOfficialNum(StrUtils.strToLong(StrUtils.toNullStr(map.get("行政编制数"))));
                    unit.setOfficialRealNum(StrUtils.strToLong(StrUtils.toNullStr(map.get("（行政)实有人数"))));
                    unit.setReferOfficialNum(StrUtils.strToLong(StrUtils.toNullStr(map.get("参照公务员法管理事业单位编制数"))));
                    unit.setReferOfficialRealNum(StrUtils.strToLong(StrUtils.toNullStr(map.get("（参公）实有人数"))));
                    unit.setReferOfficialDate(DateUtil.stringToDate(StrUtils.toNullStr(map.get("参照公务员法管理审批时间"))));
                    unit.setReferOfficialDocument(StrUtils.toNullStr(map.get("参照公务员法管理审批文号")));
                    unit.setMainHallNum(StrUtils.strToLong(StrUtils.toNullStr(map.get("乡科级正职领导职数"))));
                    unit.setDeputyHallNum(StrUtils.strToLong(StrUtils.toNullStr(map.get("乡科级副职领导职数"))));
                    unit.setRightPlaceNum(StrUtils.strToLong(StrUtils.toNullStr(map.get("县处级正职领导职数"))));
                    unit.setDeputyPlaceNum(StrUtils.strToLong(StrUtils.toNullStr(map.get("县处级副职领导职数"))));
//                    unit.setOneInspectorNum(StrUtils.strToInt(StrUtils.toNullStr(map.get("一级巡视员职数"))));
//                    unit.setTowInspectorNum(StrUtils.strToInt(StrUtils.toNullStr(map.get("二级巡视员职数"))));
                    unit.setOneTowResearcherNum(StrUtils.strToLong(StrUtils.toNullStr(map.get("一级调研员和二级调研员职数"))));
                    unit.setThreeFourResearcherNum(StrUtils.strToLong(StrUtils.toNullStr(map.get("三级级调研员和四级调研员职数"))));
                    unit.setOneTowClerkNum(StrUtils.strToLong(StrUtils.toNullStr(map.get("一级主任科员和二级主任科员职数"))));
                    unit.setThreeFourClerkNum(StrUtils.strToLong(StrUtils.toNullStr(map.get("三级主任科员和四级主任科员职数"))));
                    unit.setContact("联系人");
                    unit.setContactNumber("联系电话");
                    unit.setDetail(StrUtils.toNullStr(map.get("备注")));
                    unit.setEnabled("0");
                    SYS_UNIT unit1 = unitService.selectUnitByNameAndParent(unit.getName(), unit.getParentName());
                    if (unit1 != null) {
                        unit.setUnitOrder(unit1.getUnitOrder());
                        unitService.updateUnit(unit);
                    } else {
                        SYS_UNIT codeUnit = unitService.selectUnitByCode(unit.getCode());
                        if (codeUnit != null) {
                            stringBuffer.append("单位表：第" + list.indexOf(map) + "行;组织机构编码已存在，请检查！");
                            logger.error("单位表：第" + list.indexOf(map) + "行;组织机构编码已存在，请检查！");
                        } else {
                            SYS_UNIT unitbyname = unitService.selectUnitByName(unit.getName());
                            if (unitbyname != null) {
                                stringBuffer.append(unit.getName() + ":单位已存在，请核查！");
                                logger.error(unit.getName() + ":单位已存在，请核查！");
                            } else {
                                String uuid = UUID.randomUUID().toString();
                                unit.setId(uuid);
                                unitService.insertUnit(unit);
                                unitList.add(unit);
                            }
                        }
                    }
                } else {
                    stringBuffer.append("单位表：第" + list.indexOf(map) + "行;组织机构编码为空！");
                    logger.error("单位表：第" + list.indexOf(map) + "行;组织机构编码为空！");
                }
            } else {
                stringBuffer.append("单位表：第" + list.indexOf(map) + "行;单位名称为空！");
                logger.error("单位表：第" + list.indexOf(map) + "行;单位名称为空！");
            }
        }
        return unitList;
    }

    /**
     * 套转职级
     *
     * @param rankService
     * @param unitId
     * @param um
     */
    public static void saveTurnRank(RankService rankService, String unitId, UnitModel um) {
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
        um.setOneTowResearcherNumTurn(um.getOneResearcherNumTurn() + um.getTowResearcherNumTurn());
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
        um.setThreeFourResearcherNumTurn(um.getThreeResearcherNumTurn() + um.getFourResearcherNumTurn());
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
        um.setOneTowClerkNumTurn(um.getOneClerkNumTurn() + um.getTowClerkNumTurn());
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
        um.setThreeFourClerkNumTurn(um.getThreeClerkNumTurn() + um.getFourClerkNumTurn());
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

    /**
     * @param peopleService
     * @param unitId
     * @param um
     */
    public static void saveNowRank(PeopleService peopleService, String unitId, UnitModel um) {
        List<SYS_People> oneranks = peopleService.selectPeoplesByUnitIdAndRank(unitId, "一级调研员","在职");
        if (oneranks != null) {
            um.setOneResearcherNumNow(oneranks.size());
        }else {
            um.setOneResearcherNumNow(0);
        }
        List<SYS_People> towranks = peopleService.selectPeoplesByUnitIdAndRank(unitId, "二级调研员","在职");
        if (towranks != null) {
            um.setTowResearcherNumNow(towranks.size());
        }else {
            um.setTowResearcherNumNow(0);
        }
        um.setOneTowResearcherNumNow(um.getOneResearcherNumNow() + um.getTowResearcherNumNow());
        List<SYS_People> threeranks = peopleService.selectPeoplesByUnitIdAndRank(unitId, "三级调研员","在职");
        if (threeranks != null) {
            um.setThreeResearcherNumNow(threeranks.size());
        }else {
            um.setThreeResearcherNumNow(0);
        }
        List<SYS_People> fourranks = peopleService.selectPeoplesByUnitIdAndRank(unitId, "四级调研员","在职");
        if (fourranks != null) {
            um.setFourResearcherNumNow(fourranks.size());
        }else {
            um.setFourResearcherNumNow(0);
        }
        um.setThreeFourResearcherNumNow(um.getThreeResearcherNumNow() + um.getFourResearcherNumNow());
        List<SYS_People> oneKranks = peopleService.selectPeoplesByUnitIdAndRank(unitId, "一级主任科员","在职");
        if (oneKranks != null) {
            um.setOneClerkNumNow(oneKranks.size());
        }else {
            um.setOneClerkNumNow(0);
        }
        List<SYS_People> towKranks = peopleService.selectPeoplesByUnitIdAndRank(unitId, "二级主任科员","在职");
        if (towKranks != null) {
            um.setTowClerkNumNow(towKranks.size());
        }else {
            um.setTowClerkNumNow(0);
        }
        um.setOneTowClerkNumNow(um.getOneClerkNumNow() + um.getTowClerkNumNow());
        List<SYS_People> threeKranks = peopleService.selectPeoplesByUnitIdAndRank(unitId, "三级主任科员","在职");
        if (threeKranks != null) {
            um.setThreeClerkNumNow(threeKranks.size());
        }else {
            um.setThreeClerkNumNow(0);
        }
        List<SYS_People> fourKranks = peopleService.selectPeoplesByUnitIdAndRank(unitId, "四级主任科员","在职");
        if (fourKranks != null) {
            um.setFourClerkNumNow(fourKranks.size());
        }else {
            um.setFourClerkNumNow(0);
        }
        um.setThreeFourClerkNumNow(um.getThreeClerkNumNow() + um.getFourClerkNumNow());
        List<SYS_People> oneKYranks = peopleService.selectPeoplesByUnitIdAndRank(unitId, "一级科员","在职");
        if (oneKYranks != null) {
            um.setOneClerkNow(oneKYranks.size());
        }else {
            um.setOneClerkNow(0);
        }
        List<SYS_People> towKYranks = peopleService.selectPeoplesByUnitIdAndRank(unitId, "二级科员","在职");
        if (towKYranks != null) {
            um.setTowClerkNow(towKYranks.size());
        }else {
            um.setTowClerkNow(0);
        }
        List<SYS_People> shiranks = peopleService.selectPeoplesByUnitIdAndRank(unitId, "试用期","在职");
        if (shiranks != null) {
            um.setProbationNow(shiranks.size());
        }else {
            um.setProbationNow(0);
        }
    }

    public static void saveLaveRank(SYS_UNIT unit, UnitModel um) {
        um.setOneResearcherNumLave(unit.getOneResearcherNum() - um.getOneResearcherNumNow());
        um.setTowResearcherNumLave(unit.getTowResearcherNum() - um.getTowResearcherNumNow());
        um.setOneTowResearcherNumLave(unit.getOneTowResearcherNum() - um.getOneTowResearcherNumNow());
        um.setThreeResearcherNumLave(unit.getThreeResearcherNum() - um.getThreeResearcherNumNow());
        um.setFourResearcherNumLave(unit.getFourResearcherNum() - um.getFourResearcherNumNow());
        um.setThreeFourResearcherNumLave(unit.getThreeFourResearcherNum() - um.getThreeFourResearcherNumNow());
        um.setOneClerkNumLave(unit.getOneClerkNum() - um.getOneClerkNumNow());
        um.setTowClerkNumLave(unit.getTowClerkNum() - um.getTowClerkNumNow());
        um.setOneTowClerkNumLave(unit.getOneTowClerkNum() - um.getOneTowClerkNumNow());
        um.setThreeClerkNumLave(unit.getThreeClerkNum() - um.getThreeClerkNumNow());
        um.setFourClerkNumLave(unit.getFourClerkNum() - um.getFourClerkNumNow());
        um.setThreeFourClerkNumLave(unit.getThreeFourClerkNum() - um.getThreeFourClerkNumNow());
    }
}
