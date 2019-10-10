package com.local.cell;

import com.local.entity.sys.SYS_UNIT;
import com.local.service.UnitService;
import com.local.util.StrUtils;
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

    public static List<SYS_UNIT> getUnitDataByExcel(List<Map<String, Object>> list, UnitService unitService, StringBuffer stringBuffer) {
        List<SYS_UNIT> unitList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            SYS_UNIT unit = new SYS_UNIT();
            if (!StrUtils.isBlank(map.get("单位名称"))) {
                unit.setName(map.get("单位名称").toString());
                if (!StrUtils.isBlank(map.get("组织机构编码"))) {
                    unit.setCode(map.get("组织机构编码").toString());
                    unit.setSimpleName(String.valueOf(map.get("简称")));
                    SYS_UNIT punit = unitService.selectUnitByName(String.valueOf(map.get("上级单位")));
                    if (punit != null) {
                        unit.setParentName(punit.getName());
                    } else {
                        unit.setParentName(String.valueOf(map.get("上级单位")));
                        unit.setEnabled("2");
                    }
                    unit.setBuildProvince(String.valueOf(map.get("所在省")));
                    unit.setBuildCity(String.valueOf(map.get("所在市")));
                    unit.setBuildCounty(String.valueOf(map.get("所在县")));
                    unit.setArea(String.valueOf(map.get("所在省")) + String.valueOf(map.get("所在市")) + String.valueOf(map.get("所在县")));
                    unit.setAffiliation(String.valueOf(map.get("隶属关系")));
                    unit.setCategory(String.valueOf(map.get("机构类别")));
                    unit.setLevel(String.valueOf(map.get("机构级别")));
                    unit.setStandingLeaderNum(StrUtils.strToInt(String.valueOf(map.get("正职领导数"))));
                    unit.setVoceLeaderNum(StrUtils.strToInt(String.valueOf(map.get("副职领导数"))));
                    unit.setStandingNotLeaderNum(StrUtils.strToInt(String.valueOf(map.get("正职非领导数"))));
                    unit.setVoceNotLeaderNum(StrUtils.strToInt(String.valueOf(map.get("副职非领导数"))));
                    unit.setOfficialNum(StrUtils.strToInt(String.valueOf(map.get("行政编制数"))));
                    unit.setReferOfficialNum(StrUtils.strToInt(String.valueOf(map.get("参照公务员法管理事业单位编制数"))));
                    unit.setEnterpriseNum(StrUtils.strToInt(String.valueOf(map.get("其他事业编制数"))));
                    unit.setWorkerNum(StrUtils.strToInt(String.valueOf(map.get("工勤编制数"))));
                    unit.setOtherNum(StrUtils.strToInt(String.valueOf(map.get("其他编制数"))));
                    unit.setInternalLeaderStanding(StrUtils.strToInt(String.valueOf(map.get("内设机构应配领导正职"))));
                    unit.setInternalLeaderVoce(StrUtils.strToInt(String.valueOf(map.get("内设机构应配领导副职"))));
                    unit.setInternalNotLeaderStanding(StrUtils.strToInt(String.valueOf(map.get("内设机构应配正职非领导"))));
                    unit.setInternalNotLeaderVoce(StrUtils.strToInt(String.valueOf(map.get("内设机构应配副职非领导"))));
                    unit.setDetail(String.valueOf(map.get("备注")));
                    unit.setEnabled("0");
                    SYS_UNIT unit1 = unitService.selectUnitByNameAndParent(unit.getName(), unit.getParentName());
                    if (unit1 != null) {
                        unit.setUnitOrder(unit1.getUnitOrder());
                        unitService.updateUnit(unit);
                    } else {
                        SYS_UNIT codeUnit = unitService.selectUnitByCode(unit.getCode());
                        if (codeUnit!=null){
                            stringBuffer.append("第" + list.indexOf(map) + "行;组织机构编码已存在，请检查！");
                            logger.error("第" + list.indexOf(map) + "行;组织机构编码已存在，请检查！");
                        }else {
                            String uuid = UUID.randomUUID().toString();
                            unit.setId(uuid);
                            unitService.insertUnit(unit);
                            unitList.add(unit);
                        }
                    }
                } else {
                    stringBuffer.append("第" + list.indexOf(map) + "行;组织机构编码为空！");
                    logger.error("第" + list.indexOf(map) + "行;组织机构编码为空！");
                }
            } else {
                stringBuffer.append("第" + list.indexOf(map) + "行;单位名称为空！");
                logger.error("第" + list.indexOf(map) + "行;单位名称为空！");
            }
        }
        return unitList;
    }
}
