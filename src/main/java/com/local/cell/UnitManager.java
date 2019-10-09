package com.local.cell;

import com.local.entity.sys.SYS_UNIT;
import com.local.util.StrUtils;

public class UnitManager {

    public static void setUnitArea(SYS_UNIT unit){
        if (!StrUtils.isBlank(unit.getAreaStrs())) {
            if (unit.getAreaStrs().length > 0) {
                StringBuffer sbf=new StringBuffer();
                for (String area:unit.getAreaStrs()){
                    System.out.println(area);
                    sbf.append(area);
                }
                unit.setArea(sbf.toString());
                unit.setBuildProvince(unit.getAreaStrs()[0]);
            }
            if (unit.getAreaStrs().length>1){
                unit.setBuildCity(unit.getAreaStrs()[1]);
            }
            if(unit.getAreaStrs().length>2){
                unit.setBuildCounty(unit.getAreaStrs()[2]);
            }
            unit.setAreaStrs(unit.getAreaStrs());
        }
    }
}
