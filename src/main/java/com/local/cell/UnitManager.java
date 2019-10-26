package com.local.cell;

import com.local.entity.sys.SYS_UNIT;
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

    public static List<SYS_UNIT> getUnitDataByExcel(List<Map<String, Object>> list, UnitService unitService, StringBuffer stringBuffer)throws Exception {
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
                    unit.setBuildCounty(StrUtils.toNullStr(map.get("所在县")));
                    unit.setArea(StrUtils.toNullStr(map.get("所在省")) + StrUtils.toNullStr(map.get("所在市")) + StrUtils.toNullStr(map.get("所在县")));
                    unit.setAffiliation(StrUtils.toNullStr(map.get("隶属关系")));
                    unit.setCategory(StrUtils.toNullStr(map.get("所属序列")));
                    unit.setLevel(StrUtils.toNullStr(map.get("单位级别")));
                    unit.setOfficialNum(StrUtils.strToInt(StrUtils.toNullStr(map.get("行政编制数"))));
                    unit.setReferOfficialNum(StrUtils.strToInt(StrUtils.toNullStr(map.get("事业编制数（参公）"))));
                    unit.setReferOfficialDate(DateUtil.stringToDate(StrUtils.toNullStr(map.get("参照公务员法管理审批时间"))));
                    unit.setReferOfficialDocument(StrUtils.toNullStr(map.get("参照公务员法管理审批文号")));
                    unit.setMainHallNum(StrUtils.strToInt(StrUtils.toNullStr(map.get("乡科级正职领导职数"))));
                    unit.setDeputyHallNum(StrUtils.strToInt(StrUtils.toNullStr(map.get("乡科级副职领导职数"))));
                    unit.setRightPlaceNum(StrUtils.strToInt(StrUtils.toNullStr(map.get("县处级正职领导职数"))));
                    unit.setDeputyPlaceNum(StrUtils.strToInt(StrUtils.toNullStr(map.get("县处级副职领导职数"))));
                    unit.setOneInspectorNum(StrUtils.strToInt(StrUtils.toNullStr(map.get("一级巡视员职数"))));
                    unit.setTowInspectorNum(StrUtils.strToInt(StrUtils.toNullStr(map.get("二级巡视员职数"))));
                    unit.setOneTowResearcherNum(StrUtils.strToInt(StrUtils.toNullStr(map.get("一级和二级调研员职数"))));
                    unit.setThreeFourResearcherNum(StrUtils.strToInt(StrUtils.toNullStr(map.get("三级和四级调研员职数"))));
                    unit.setOneTowClerkNum(StrUtils.strToInt(StrUtils.toNullStr(map.get("一级和二级主任科员职数"))));
                    unit.setThreeFourClerkNum(StrUtils.strToInt(StrUtils.toNullStr(map.get("三级和四级主任科员职数"))));
                    unit.setDetail(StrUtils.toNullStr(map.get("备注")));
                    unit.setEnabled("0");
                    SYS_UNIT unit1 = unitService.selectUnitByNameAndParent(unit.getName(), unit.getParentName());
                    if (unit1 != null) {
                        unit.setUnitOrder(unit1.getUnitOrder());
                        unitService.updateUnit(unit);
                    } else {
                        SYS_UNIT codeUnit = unitService.selectUnitByCode(unit.getCode());
                        if (codeUnit!=null){
                            stringBuffer.append("单位表：第" + list.indexOf(map) + "行;组织机构编码已存在，请检查！");
                            logger.error("单位表：第" + list.indexOf(map) + "行;组织机构编码已存在，请检查！");
                        }else {
                            String uuid = UUID.randomUUID().toString();
                            unit.setId(uuid);
                            unitService.insertUnit(unit);
                            unitList.add(unit);
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
}
