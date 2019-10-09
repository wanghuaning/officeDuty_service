package com.local.cell;

import com.local.entity.sys.SYS_UNIT;
import com.local.util.StrUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public class UnitManager {

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

    /**
     * 导入校验（excel文件）
     *
     * @param file
     */

    public static void checkFile(MultipartFile file) throws IOException {
        //判断文件是否存在
        if (null == file) {
            // logger.error("文件不存在！");
            throw new FileNotFoundException("文件不存在！");
        }
        //获得文件名
        String fileName = file.getOriginalFilename();
        //判断文件是否是excel文件
        if (!fileName.endsWith("xls") && !fileName.endsWith("xlsx")) {
            // logger.error(fileName + "不是excel文件");
            throw new IOException(fileName + "不是excel文件");
        }
    }
}
