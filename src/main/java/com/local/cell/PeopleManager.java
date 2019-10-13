package com.local.cell;

import com.local.entity.sys.SYS_People;
import com.local.entity.sys.SYS_UNIT;
import com.local.service.PeopleService;
import com.local.service.UnitService;
import com.local.util.DateUtil;
import com.local.util.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PeopleManager {
    private final static Logger logger= LoggerFactory.getLogger(PeopleManager.class);
    public static List<SYS_People> getPeopleDataByExcel(List<Map<String, Object>> list, PeopleService service, StringBuffer stringBuffer,
                                                        UnitService unitService,String fullImport)throws Exception {
        List<SYS_People> peopleList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            SYS_UNIT unit = unitService.selectUnitByName(String.valueOf(map.get("单位")));
            if (unit != null) {
                SYS_People people = new SYS_People();
                people.setUnitId(unit.getId());
                people.setUnitName(unit.getName());
                if (!StrUtils.isBlank(map.get("姓名"))) {
                    people.setName(map.get("姓名").toString());
                    if (!StrUtils.isBlank(map.get("身份证号"))) {
                        people.setIdcard(map.get("身份证号").toString());
                        String birthdayStr=String.valueOf(map.get("出生年月"));
                        if (!StrUtils.isBlank(birthdayStr)){
                            people.setBirthday(DateUtil.stringToDate(birthdayStr));
                        }
                        people.setSex(String.valueOf(map.get("性别")));
                        people.setBirthplace(String.valueOf(map.get("籍贯")));
                        people.setNationality(String.valueOf(map.get("民族 ")));
                        String workdayStr=String.valueOf(map.get("参加工作时间"));
                        if (!StrUtils.isBlank(workdayStr)){
                            people.setWorkday(DateUtil.stringToDate(workdayStr));
                        }
                        people.setParty(String.valueOf(map.get("政治面貌")));
                        String partyTimeStr=String.valueOf(map.get("入党时间"));
                        if (!StrUtils.isBlank(partyTimeStr)){
                            people.setPartyTime(DateUtil.stringToDate(partyTimeStr));
                        }
                        people.setSecondParty(String.valueOf(map.get("第二党派")));
                        people.setThirdParty(String.valueOf(map.get("第三党派")));
                        people.setPosition(String.valueOf(map.get("现职务层次")));
                        String positionTimeStr=String.valueOf(map.get("任现职务层次时间"));
                        if (!StrUtils.isBlank(positionTimeStr)){
                            people.setPositionTime(DateUtil.stringToDate(positionTimeStr));
                        }
                        people.setPositionLevel(String.valueOf(map.get("现职级")));
                        String positionLevelTimeStr=String.valueOf(map.get("任现职级时间"));
                        if (!StrUtils.isBlank(positionLevelTimeStr)){
                            people.setPositionLevelTime(DateUtil.stringToDate(positionLevelTimeStr));
                        }
                        people.setBaseWorker(String.valueOf(map.get("是否具有两年以上基层工作经历")));
                        people.setPoliticalStatus(String.valueOf(map.get("编制类型")));
                        String createTimeStr=String.valueOf(map.get("公务员登记时间"));
                        if (!StrUtils.isBlank(createTimeStr)){
                            people.setPositionLevelTime(DateUtil.stringToDate(createTimeStr));
                        }
                        String enableStr=String.valueOf(map.get("现职"));
                        if ("否".equals(enableStr)){
                            people.setEnabled("1");
                        }else {
                            people.setEnabled("0");
                        }
                        people.setDetail(String.valueOf(map.get("备注")));
                        SYS_People people1 =service.selectPeopleByIdcardAndUnitId(people.getIdcard(), unit.getId());
                        if (people1 != null) {
                            if ("1".equals(fullImport)){
                                people.setId(people1.getId());
                                people.setPeopleOrder(people1.getPeopleOrder());
                                service.updatePeople(people);
                            }else {
                                stringBuffer.append("第" + list.indexOf(map) + "行;身份证号重复，请检查！");
                                logger.error("第" + list.indexOf(map) + "行;身份证号重复，请检查！");
                            }
                        } else {
                                String uuid = UUID.randomUUID().toString();
                                people.setId(uuid);
                                service.insertPeoples(people);
                                peopleList.add(people);
                        }
                    } else {
                        stringBuffer.append("第" + list.indexOf(map) + "行;身份证号为空！");
                        logger.error("第" + list.indexOf(map) + "行;身份证号为空！");
                    }
                } else {
                    stringBuffer.append("第" + list.indexOf(map) + "行;姓名为空！");
                    logger.error("第" + list.indexOf(map) + "行;姓名为空！");
                }
                people.setUnitId(unit.getId());
            } else {
                logger.error("第" + list.indexOf(map) + "行;单位不存在！");
                stringBuffer.append("第" + list.indexOf(map) + "行;单位不存在！");
            }
        }
        return peopleList;
    }
}
