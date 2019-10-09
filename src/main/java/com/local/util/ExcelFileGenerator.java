package com.local.util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ExcelFileGenerator <T>{
    private static Map<String, byte[]> templetes=new HashMap<>();
    public static Workbook getTeplet(String path) throws IOException, InvalidFormatException {
        if (!templetes.containsKey(path)){
            templetes.put(path,fileToByteArray(path));
        }
        return WorkbookFactory.create(new ByteArrayInputStream(templetes.get(path)));
    }
    public static byte[] fileToByteArray(String path) throws IOException {
        File file=new File(path);
        int fileSize=(int) file.length();
        byte[] dataArr=new byte[fileSize];
        FileInputStream data=new FileInputStream(path);
        int readCount= (int) data.read(dataArr);
        int totleCount=0;
        while (readCount>0){
            totleCount+=totleCount;
            readCount=data.read(dataArr,totleCount,fileSize-readCount);
        }
        return dataArr;
    }

    /**
     * 设置Excel表名
     * @param response
     * @param fileName
     * @throws UnsupportedEncodingException
     */
    public static void setExcleNAME(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        response.addHeader("content-disposition", "attachment; filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public int  createExcelFile(Sheet sheet, int dataStartIndex, List<T> data, String[] columns)throws Exception{
        CellStyle cellStyle=sheet.getWorkbook().createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.DOUBLE ); //下边框
        cellStyle.setBorderLeft(BorderStyle.THICK); //左边框
        cellStyle.setBorderTop(BorderStyle.MEDIUM); //上边框
        cellStyle.setBorderRight(BorderStyle.DASH_DOT); //右边框
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font font=sheet.getWorkbook().createFont();
        font.setBold(false);
        font.setFontHeightInPoints((short) 12);//设置行高像素
        font.setFontName("仿宋");
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        int rowIndex=dataStartIndex;
        if(data.size()>0){
            Class classType=data.get(0).getClass();
            for(Object d:data){
                Row row=sheet.createRow(rowIndex);
                boolean isChanged = false;
                for (int i=0;i <columns.length;i++) {
                    Cell c = row.createCell(i);
                    c.setCellStyle(cellStyle);
                    if (columns[i] != null && columns[i].length() > 0) {
                        String normalName = columns[i].substring(0,1).toUpperCase() + columns[i].substring(1);
                        Object rs = classType.getMethod("get" + normalName).invoke(d);
                        if (rs != null) {
                            isChanged = true;
                            setCellFormattedValue(c, rs);
                        }
                    }
                }
                if (isChanged) rowIndex += 1;
            }
        }
        return rowIndex;
    }
    /**
     * 设置单元格的值，并判断是否数字
     *
     */
    private static void  setCellFormattedValue(Cell c, Object rs) {
        if (rs == null) {
            c.setCellType(CellType.BLANK);
        } else {
            String v = (String) rs;
//            Boolean strResult =StrUtils.isNumeric(v);
//            if (strResult) {
//                c.setCellType(CellType.NUMERIC);
//                Double d=Double.valueOf(v);
//                c.setCellValue(d);
//            } else {
            if (v.contains("color")) {
                int index1 = v.indexOf(">");
                int index2 = v.indexOf("<", 1); //跳过第一个，从索引1开始搜索
                String value = v.substring(index1 + 1, index2);
                c.setCellType(CellType.NUMERIC);
                c.setCellValue( Double.valueOf(value));
            }
//                else if (v.length() < 9 && (v.matches("^\\d+$") || v.matches("^\\d+\\.+\\d+$") || v.matches("^((\\d+\\.?+\\d+)[Ee]{1}(\\d+))$"))) {
//                    c.setCellType(CellType.NUMERIC);
//                    c.setCellValue(Double.valueOf(v));
//                }
            else {
                c.setCellType(CellType.STRING);
                c.setCellValue(v);
            }
        }
//        }
    }
    /**
     * 创建Excel表压缩文件包
     * * @param tableName  压缩包名字
     * @param userAgent  处理firefox乱码
     * @param sheetIndex 名字所在表坐标
     * @param rowIndex   获取名字row的坐标
     * @param colIndex   获取名字col坐标
     */
 public static void creatExcelZip(List<Workbook> workBookList, String tableName, String excleName, HttpServletResponse response,
                                  String userAgent, int sheetIndex, int rowIndex, int colIndex) throws UnsupportedEncodingException ,IOException{
     String fileName = tableName + ".zip";
     String zipFileName = "";
     if (userAgent.toLowerCase().indexOf("firefox") > 0) {
         zipFileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
     } else {
         zipFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
     }
     String zipName = zipFileName;
     response.setContentType("APPLICATION/OCTET-STREAM");
     response.setHeader("Content-Disposition", "attachment; filename=" + zipName);
     ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
     int i = 0;
     for (Workbook workBook: workBookList) {
         String name = workBook.getSheetName(0);
         if (rowIndex == 0 && colIndex == 0) {
             zipOut.putNextEntry(new ZipEntry(excleName + ".xls"));
         } else {
             Sheet sheet = workBook.getSheetAt(0);
              name = sheet.getRow(rowIndex).getCell(colIndex).getStringCellValue().trim();
             zipOut.putNextEntry(new ZipEntry(excleName+i + ".xls"));
         }
         i += 1;
         workBook.write(zipOut);
         workBook.close();
         System.out.println("压缩好第"+i+"个");
     }
     zipOut.close();
 }
}
