package com.local.util;

import com.local.controller.UnitConttoller;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ExcelFileGenerator <T>{
    private final static Logger logger = LoggerFactory.getLogger(ExcelFileGenerator.class);
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
    private static void  setCellFormattedValue(Cell cell, Object value) {
        String textValue = null;
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Float) {
            textValue = String.valueOf((Float) value);
            cell.setCellValue(textValue);
        } else if (value instanceof Double) {
            textValue = String.valueOf((Double) value);
            cell.setCellValue(textValue);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        }
        if (value instanceof Boolean) {
            textValue = "是";
            if (!(Boolean) value) {
                textValue = "否";
            }
        } else if (value instanceof Date) {
            String pattern="yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            textValue = sdf.format((Date) value);
        } else {
            // 其它数据类型都当作字符串简单处理
            if (value != null) {
                textValue = value.toString();
            }
        }
        if (textValue != null) {
            Pattern p = Pattern.compile("^//d+(//.//d+)?$");
            Matcher matcher = p.matcher(textValue);
            if (matcher.matches()) {
                // 是数字当作double处理
                cell.setCellValue(Double.parseDouble(textValue));
            } else {
                XSSFRichTextString richString = new XSSFRichTextString(textValue);
                cell.setCellValue(richString);
            }
        }
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


    /**
     * excelz转为List
     * @param inputStream
     * @return
     */
    public static List<Integer> excelToShopIdList(InputStream inputStream) {
        List<Integer> list = new ArrayList<>();
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();
            //工作表对象
            Sheet sheet = workbook.getSheetAt(0);
            //总行数
            int rowLength = sheet.getLastRowNum() + 1;
            //工作表的列
            Row row = sheet.getRow(0);
            //总列数
            int colLength = row.getLastCellNum();
            //得到指定的单元格
            Cell cell = row.getCell(0);
            for (int i = 1; i < rowLength; i++) {
                row = sheet.getRow(i);
                for (int j = 0; j < colLength; j++) {
                    cell = row.getCell(j);
                    if (cell != null) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        String data = cell.getStringCellValue();
                        data = data.trim();
                        if (StringUtils.isNumeric(data))
                            list.add(Integer.parseInt(data));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("parse excel file error :", e);
        }
        return list;
    }

    /**
     * Excel数据返回
     * @param inputStream
     * @param index
     * @return
     */
    public static List<Map<Integer, String>> importReportExcel(InputStream inputStream, int index) {
        List<Map<Integer, String>> mapRow = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(index);
            for (Row row : sheet) {
                Map<Integer, String> mapColumn = new HashMap<Integer, String>();
                for (Cell cell : row) {
                    int type = cell.getCellType();
                    String cellContext = "";
                    switch (type) { // 判断数据类型
                        case Cell.CELL_TYPE_BLANK:// 空的
                            cellContext = "";
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:// 布尔
                            cellContext = cell.getBooleanCellValue() + "";
                            break;
                        case Cell.CELL_TYPE_ERROR:// 错误
                            cellContext = cell.getErrorCellValue() + "";
                            break;
                        case Cell.CELL_TYPE_FORMULA:// 公式
                            cellContext = cell.getCellFormula();
                            break;
                        case Cell.CELL_TYPE_NUMERIC:// 数字或日期
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                cellContext = new DataFormatter().formatRawCellContents(cell.getNumericCellValue(), 0, "yyyy-mm-dd");// 格式化日期
                            } else {
                                NumberFormat nf = NumberFormat.getInstance();
                                nf.setGroupingUsed(false);
                                cellContext = nf.format(cell.getNumericCellValue());
                            }
                            break;
                        case Cell.CELL_TYPE_STRING:// 字符串
                            cellContext = cell.getStringCellValue();
                            break;
                        default:
                            break;
                    }
                    mapColumn.put(cell.getColumnIndex(), cellContext);
                }
                mapRow.add(mapColumn);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapRow;
    }


    /**
     * 读取Excel表数据
     * @param excelInputSteam
     * @param sheetNumber 读取Excel表位置 0开始
     * @param headerNumber 表头位置 0开始
     * @param rowStart 读取开始位置 0开始
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static List<Map<String, Object>> readeExcelData(InputStream excelInputSteam,
                                                           int sheetNumber,
                                                           int headerNumber,
                                                           int rowStart) throws IOException, InvalidFormatException {
        //需要的变量以及要返回的数据
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        List<String> headers = new ArrayList<String>();
        //生成工作表
        Workbook workbook = WorkbookFactory.create(excelInputSteam);
        Sheet sheet = workbook.getSheetAt(sheetNumber);
        Row header = sheet.getRow(headerNumber);
        //最后一行数据
        int rowEnd = sheet.getLastRowNum();
        DataFormatter dataFormatter = new DataFormatter();
        //获取标题信息
        for (int i = 0; i < header.getLastCellNum(); ++i) {
            Cell cell = header.getCell(i);
            headers.add(dataFormatter.formatCellValue(cell));
        }
        //获取内容信息
        for (int i = rowStart; i <= rowEnd; ++i) {
            Row currentRow = sheet.getRow(i);
            if (Objects.isNull(currentRow)) {
                continue;
            }
            Map<String, Object> dataMap = new HashMap<>();
            for (int j = 0; j < currentRow.getLastCellNum(); ++j) {
                //将null转化为Blank
                Cell data = currentRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                if (Objects.isNull(data)) {     //感觉这个if有点多余
                    dataMap.put(headers.get(j), null);
                } else {
                    switch (data.getCellType()) {   //不同的类型分别进行存储
                        case Cell.CELL_TYPE_STRING:
                            dataMap.put(headers.get(j), data.getRichStringCellValue().getString());
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            if (DateUtil.isCellDateFormatted(data)) {
                                dataMap.put(headers.get(j), data.getDateCellValue());
                            } else {
                                dataMap.put(headers.get(j), data.getNumericCellValue());
                            }
                            break;
                        case Cell.CELL_TYPE_FORMULA:
                            dataMap.put(headers.get(j), data.getCellFormula());
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            dataMap.put(headers.get(j), data.getBooleanCellValue());
                            break;
                        default:
                            dataMap.put(headers.get(j), null);
                    }
                }
            }
            result.add(dataMap);
        }
        return result;
    }
}
