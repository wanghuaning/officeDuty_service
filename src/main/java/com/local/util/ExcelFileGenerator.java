package com.local.util;

import com.local.entity.sys.Sys_Approal;
import com.local.model.CompleteModel;
import com.local.model.RegModel;
import com.local.model.ReimbursementModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.java2d.pipe.Region;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ExcelFileGenerator<T> {
    private final static Logger logger = LoggerFactory.getLogger(ExcelFileGenerator.class);
    private static Map<String, byte[]> templetes = new HashMap<>();

    public static Workbook getTeplet(String path) throws IOException, InvalidFormatException {
        if (!templetes.containsKey(path)) {
            templetes.put(path, fileToByteArray(path));
        }
        return WorkbookFactory.create(new ByteArrayInputStream(templetes.get(path)));
    }

    public static byte[] fileToByteArray(String path) throws IOException {
        File file = new File(path);
        int fileSize = (int) file.length();
        byte[] dataArr = new byte[fileSize];
        FileInputStream data = new FileInputStream(path);
        int readCount = (int) data.read(dataArr);
        int totleCount = 0;
        while (readCount > 0) {
            totleCount += totleCount;
            readCount = data.read(dataArr, totleCount, fileSize - readCount);
        }
        return dataArr;
    }

    /**
     * 设置Excel表名
     *
     * @param response
     * @param fileName
     * @throws UnsupportedEncodingException
     */
    public static void setExcleNAME(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        response.addHeader("content-disposition", "attachment; filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    /**
     * @param sheet
     * @return
     * @throws Exception
     */
    public int createExcelFileFixedRow(Sheet sheet, int rowIndex, int[] colIndex, String[] columns) throws Exception {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        Font font = sheet.getWorkbook().createFont();
        font.setBold(false);
        font.setFontHeightInPoints((short) 12);//设置行高像素
        font.setFontName("仿宋");
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        Row row = sheet.getRow(rowIndex);
        for (int i = 0; i < columns.length; i++) {
            Cell c = null;
            if (row.getCell(i) != null) {
                c = row.getCell(colIndex[i]);
            } else {
                row.createCell(colIndex[i]);
            }
            c.setCellStyle(cellStyle);
            setCellFormattedValue(c, columns[i]);
        }
        return rowIndex;
    }

    /**
     * mergeRow 合并日期占两行(4个参数，分别为起始行，结束行，起始列，结束列)
     * // 行和列都是从0开始计数，且起始结束都会合并
     * // 这里是合并excel中日期的两行为一行
     *
     * @param sheet
     * @return
     * @throws Exception
     */
    public int createExcelFileFixedMergeRow(Sheet sheet, int rowIndex, int[] colIndex, String[] columns, int cell1, int cell2, int row1, int row2) throws Exception {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN); //左边框
        cellStyle.setBorderTop(BorderStyle.THIN); //上边框
        cellStyle.setBorderRight(BorderStyle.THIN); //右边框
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font font = sheet.getWorkbook().createFont();
        font.setBold(false);
        font.setFontHeightInPoints((short) 12);//设置行高像素
        font.setFontName("仿宋");
        cellStyle.setFont(font);
        //合并单元格
        CellRangeAddress address = new CellRangeAddress(cell1, cell2, row1, row2);
        sheet.addMergedRegion(address);
        //添加合并边框边框
        RegionUtil.setBorderTop(1, address, sheet);
        RegionUtil.setBorderBottom(1, address, sheet);
        RegionUtil.setBorderLeft(1, address, sheet);
        RegionUtil.setBorderRight(1, address, sheet);
        cellStyle.setWrapText(true);
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        row.setHeightInPoints(25);
        for (int i = 0; i < columns.length; i++) {
            Cell c = null;
            if (row.getCell(colIndex[i]) != null) {
                c = row.getCell(colIndex[i]);
            } else {
                c = row.createCell(colIndex[i]);
            }
            c.setCellStyle(cellStyle);
            setCellFormattedValue(c, columns[i]);
        }
        return rowIndex;
    }

    /**
     * mergeRow 合并日期占两行(4个参数，分别为起始行，结束行，起始列，结束列)
     * // 行和列都是从0开始计数，且起始结束都会合并
     * // 这里是合并excel中日期的两行为一行
     *
     * @param sheet
     * @return
     * @throws Exception
     */
    public int createExcelFileFixedMergeAreaRow(Sheet sheet, int rowIndex, int[] colIndex, String[] columns, int cell1, int cell2, int row1, int row2, HorizontalAlignment area) throws Exception {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN); //左边框
        cellStyle.setBorderTop(BorderStyle.THIN); //上边框
        cellStyle.setBorderRight(BorderStyle.THIN); //右边框
        cellStyle.setAlignment(area);//HorizontalAlignment.CENTER;
        Font font = sheet.getWorkbook().createFont();
        font.setBold(false);
        font.setFontHeightInPoints((short) 12);//设置行高像素
        font.setFontName("仿宋");
        cellStyle.setFont(font);
        //合并单元格
        CellRangeAddress address = new CellRangeAddress(cell1, cell2, row1, row2);
        sheet.addMergedRegion(address);
        //添加合并边框边框
        RegionUtil.setBorderTop(1, address, sheet);
        RegionUtil.setBorderBottom(1, address, sheet);
        RegionUtil.setBorderLeft(1, address, sheet);
        RegionUtil.setBorderRight(1, address, sheet);
        cellStyle.setWrapText(true);
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        row.setHeightInPoints(25);
        for (int i = 0; i < columns.length; i++) {
            Cell c = null;
            if (row.getCell(colIndex[i]) != null) {
                c = row.getCell(colIndex[i]);
            } else {
//                System.out.println(colIndex[i]);
                c = row.createCell(colIndex[i]);
            }
            c.setCellStyle(cellStyle);
            setCellFormattedValue(c, columns[i]);
        }
        return rowIndex;
    }

    public int createExcelFile(Sheet sheet, int dataStartIndex, List<T> data, String[] columns) throws Exception {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN); //左边框
        cellStyle.setBorderTop(BorderStyle.THIN); //上边框
        cellStyle.setBorderRight(BorderStyle.THIN); //右边框
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font font = sheet.getWorkbook().createFont();
        font.setBold(false);
        font.setFontHeightInPoints((short) 12);//设置行高像素
        font.setFontName("仿宋");
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        int rowIndex = dataStartIndex;
        if (data.size() > 0) {
            Class classType = data.get(0).getClass();
            for (Object d : data) {
                Row row = sheet.createRow(rowIndex);
                boolean isChanged = false;
                for (int i = 0; i < columns.length; i++) {
                    Cell c = row.createCell(i);
                    c.setCellStyle(cellStyle);
                    if (columns[i] != null && columns[i].length() > 0) {
                        String normalName = columns[i].substring(0, 1).toUpperCase() + columns[i].substring(1);
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

    public static int createExcelFileOld(Sheet sheet, int dataStartIndex, List<CompleteModel> data, String[] columns) throws Exception {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.DOUBLE); //下边框
        cellStyle.setBorderLeft(BorderStyle.THICK); //左边框
        cellStyle.setBorderTop(BorderStyle.MEDIUM); //上边框
        cellStyle.setBorderRight(BorderStyle.DASH_DOT); //右边框
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font font = sheet.getWorkbook().createFont();
        font.setBold(false);
        font.setFontHeightInPoints((short) 12);//设置行高像素
        font.setFontName("仿宋");
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        int rowIndex = dataStartIndex;
        if (data.size() > 0) {
            Class classType = data.get(0).getClass();
            for (Object d : data) {
                Row row = sheet.createRow(rowIndex);
                boolean isChanged = false;
                for (int i = 0; i < columns.length; i++) {
                    Cell c = row.createCell(i);
                    c.setCellStyle(cellStyle);
                    if (columns[i] != null && columns[i].length() > 0) {
                        String normalName = columns[i].substring(0, 1).toUpperCase() + columns[i].substring(1);
                        Object rs = classType.getMethod("get" + normalName).invoke(d);
                        if (rs != null) {
                            isChanged = true;
                            setCellFormattedValueOld(c, rs);
                        }
                    }
                }
                if (isChanged) rowIndex += 1;
            }
        }
        return rowIndex;
    }

    private static void setCellFormattedValueOld(Cell c, Object rs) {
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
                c.setCellValue(Double.valueOf(value));
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
     * 设置单元格的值，并判断是否数字
     */
    private static void setCellFormattedValue(Cell cell, Object value) {
        String textValue = null;
        if (value instanceof Integer) {
            cell.setCellValue(StrUtils.strToInt(String.valueOf(value)));
        } else if (value instanceof Float) {
            textValue = String.valueOf((Float) value);
            cell.setCellValue(textValue);
        } else if (value instanceof Double) {
            cell.setCellValue(StrUtils.strToDouble(String.valueOf(value)));
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        }
        if (value instanceof Boolean) {
            textValue = "是";
            if (!(Boolean) value) {
                textValue = "否";
            }
        } else if (value instanceof Date) {
            String pattern = "yyyy-MM-dd";
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
                cell.setCellType(CellType.STRING);
                XSSFRichTextString richString = new XSSFRichTextString(textValue);
                cell.setCellValue(richString);
            }
        }
    }

    /**
     * 创建Excel表压缩文件包
     * * @param tableName  压缩包名字
     *
     * @param userAgent  处理firefox乱码
     * @param sheetIndex 名字所在表坐标
     * @param rowIndex   获取名字row的坐标
     * @param colIndex   获取名字col坐标
     */
    public static void creatExcelZip(List<Workbook> workBookList, String tableName, String excleName, HttpServletResponse response,
                                     String userAgent, int sheetIndex, int rowIndex, int colIndex) throws UnsupportedEncodingException, IOException {
        String fileName = tableName + ".zip";
        String zipFileName = "";
        if (userAgent.toLowerCase().indexOf("firefox") > 0) {
            zipFileName = new java.lang.String(fileName.getBytes("UTF-8"), "ISO8859-1");
        } else {
            zipFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
        }
        String zipName = zipFileName;
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=" + zipName);
        ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
        int i = 0;
        for (Workbook workBook : workBookList) {
            String name = workBook.getSheetName(0);
            if (rowIndex == 0 && colIndex == 0) {
                zipOut.putNextEntry(new ZipEntry(excleName + ".xls"));
            } else {
                Sheet sheet = workBook.getSheetAt(sheetIndex);
                name = sheet.getRow(rowIndex).getCell(colIndex).getStringCellValue().trim();
//                zipOut.putNextEntry(new ZipEntry(excleName + i + ".xls"));
                zipOut.putNextEntry(new ZipEntry(excleName + i + ".xls"));
            }
            i += 1;
            workBook.write(zipOut);
            workBook.close();
            System.out.println(name + "=>" + workBook.getSheetName(0));
            System.out.println("压缩好第" + i + "个");
        }
        zipOut.close();
    }


    /**
     * excelz转为List
     *
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
     *
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
     *
     * @param excelInputSteam
     * @param sheetNumber     读取Excel表位置 0开始
     * @param headerNumber    表头位置 0开始
     * @param rowStart        读取开始位置 0开始
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
            headers.add(dataFormatter.formatCellValue(cell).replaceAll("\uFEFF", ""));
        }
        //获取内容信息
        for (int i = rowStart; i <= rowEnd; ++i) {
            Row currentRow = sheet.getRow(i);
            if (Objects.isNull(currentRow)) {
                continue;
            }
            boolean sd = false;
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
                            sd = true;
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            sd = true;
                            if (DateUtil.isCellDateFormatted(data)) {
                                dataMap.put(headers.get(j), data.getDateCellValue());
                            } else {
                                DecimalFormat df = new DecimalFormat("0");
                                dataMap.put(headers.get(j), df.format(data.getNumericCellValue()));
                            }
                            break;
                        case Cell.CELL_TYPE_FORMULA:
                            sd = true;
                            dataMap.put(headers.get(j), data.getCellFormula());
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            sd = true;
                            dataMap.put(headers.get(j), data.getBooleanCellValue());
                            break;
                        default:
                            dataMap.put(headers.get(j), null);
                    }
                }
            }
            if (sd) {
                result.add(dataMap);
            }
        }
        return result;
    }

    /**
     * 或Excel表头
     *
     * @param excelInputSteam
     * @param sheetNumber     数据表序号 0开始
     * @param headerNumber    表头开始位置 0开始
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static List<String> readeExcelHeader(InputStream excelInputSteam,
                                                int sheetNumber,
                                                int headerNumber) throws IOException, InvalidFormatException {
        //要返回的数据
        List<String> headers = new ArrayList<String>();
        //生成工作表
        Workbook workbook = WorkbookFactory.create(excelInputSteam);
        Sheet sheet = workbook.getSheetAt(sheetNumber);
        Row header = sheet.getRow(headerNumber);
        DataFormatter dataFormatter = new DataFormatter();
        for (int i = 0; i < header.getLastCellNum(); i++) {
            //获取单元格
            Cell cell = header.getCell(i);
            headers.add(dataFormatter.formatCellValue(cell));
        }
        return headers;
    }

    /**
     * 或Excel表头
     *
     * @param excelInputSteam
     * @param sheetname       数据表sheet名
     * @param headerNumber    表头开始位置 0开始
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static List<String> readeExcelHeaderBySheetName(InputStream excelInputSteam,
                                                           String sheetname,
                                                           int headerNumber) throws IOException, InvalidFormatException {
        //要返回的数据
        List<String> headers = new ArrayList<String>();
        //生成工作表
        Workbook workbook = WorkbookFactory.create(excelInputSteam);
        Sheet sheet = workbook.getSheet(sheetname);
        Row header = sheet.getRow(headerNumber);
        DataFormatter dataFormatter = new DataFormatter();
        for (int i = 0; i < header.getLastCellNum(); i++) {
            //获取单元格
            Cell cell = header.getCell(i);
            headers.add(dataFormatter.formatCellValue(cell));
        }
        return headers;
    }

    /**
     * 读取Excel表数据
     *
     * @param excelInputSteam
     * @param sheetName       读取Excel表名
     * @param headerNumber    表头位置 0开始
     * @param rowStart        读取开始位置 0开始
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static List<Map<String, Object>> readeExcelDataBySheetName(InputStream excelInputSteam,
                                                                      String sheetName,
                                                                      int headerNumber,
                                                                      int rowStart) throws IOException, InvalidFormatException {
        //需要的变量以及要返回的数据
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        List<String> headers = new ArrayList<String>();
        //生成工作表
        Workbook workbook = WorkbookFactory.create(excelInputSteam);
        Sheet sheet = workbook.getSheet(sheetName);
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

    /**
     * row:行
     *
     * @param sheet
     * @param y
     * @param x
     * @param value
     * @return
     */
    public static Cell setValue(Sheet sheet, int x, int y, String value) {
        Row row = sheet.getRow(x);
        Cell c = null;
        if (row.getCell(y) != null) {
            c = row.getCell(y);
        } else {
            row.createCell(y);
            c = row.getCell(y);
        }
        if (value != null) {
            c.setCellValue(value);
        } else {
            c.setCellValue("");
        }
//        setCellFormattedValue(c, value);
        return c;
    }

    /**
     * 市级单位科级公务员职务职级任免与升降审核备案表
     *
     * @param sheet
     * @param data
     * @throws Exception
     */
    public void createRegReimbursementExcel(Sheet sheet, RegModel data) throws Exception {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font font = sheet.getWorkbook().createFont();
        font.setBold(false);
        font.setFontHeightInPoints((short) 12);//设置行高像素
        font.setFontName("仿宋");
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        setValue(sheet, 7, 0, data.getPeopleNums());
        setValue(sheet, 7, 1, data.getHdzhengke());
        setValue(sheet, 7, 2, data.getHdfuke());
        setValue(sheet, 7, 3, data.getXianyouzhengke());
        setValue(sheet, 7, 4, data.getXianyoufuke());
        setValue(sheet, 7, 5, data.getXianyouganbu());
        setValue(sheet, 7, 6, data.getHezhunoneTowClerkNum());
        setValue(sheet, 7, 8, data.getHezhunthreeFourClerkNum());
        setValue(sheet, 7, 10, data.getXianyouoneTowClerkNum());
        setValue(sheet, 7, 11, data.getXianyouoneClerkNum());
        setValue(sheet, 7, 12, data.getXianyoutowClerkNum());
        setValue(sheet, 7, 13, data.getXianyouOneTowJunZhuanNum());
        setValue(sheet, 7, 14, data.getXianyouthreeFourClerkNum());
        setValue(sheet, 7, 15, data.getXianyouThreeClerkNum());
        setValue(sheet, 7, 16, data.getXianyouFourClerkNum());
        setValue(sheet, 7, 17, data.getXianyouThreeFourJunZhuanNum());
        setValue(sheet, 7, 18, data.getNijinshengzhengke());
        setValue(sheet, 7, 19, data.getNijinshengfuke());
        setValue(sheet, 7, 20, data.getNijinshengganbu());
        setValue(sheet, 7, 21, data.getNijinshengoneClerkNum());
        setValue(sheet, 7, 22, data.getNijinshengtowClerkNum());
        setValue(sheet, 7, 23, data.getNijinshengThreeClerkNum());
        setValue(sheet, 7, 24, data.getNijinshengFourClerkNum());
        setValue(sheet, 7, 25, data.getNijinshengJianZhioneClerkNum());
        setValue(sheet, 7, 26, data.getNijinshengJiantowClerkNum());
        setValue(sheet, 7, 27, data.getNijinshengJianThreeClerkNum());
        setValue(sheet, 7, 28, data.getZhengkeGaitowClerkNum());
        setValue(sheet, 7, 29, data.getFukeGaiFourClerkNum());
        setValue(sheet, 7, 30, data.getTiaozhengzhengke());
        setValue(sheet, 7, 31, data.getTiaozhengfuke());
        setValue(sheet, 7, 32, data.getTiaozhengganbu());
        setValue(sheet, 7, 33, data.getTiaozhengoneTowClerkNum());
        setValue(sheet, 7, 34, data.getTiaozhengoneClerkNum());
        setValue(sheet, 7, 35, data.getTiaozhengtowClerkNum());
        setValue(sheet, 7, 36, data.getTiaozhengOneTowJunZhuanNum());
        setValue(sheet, 7, 37, data.getTiaozhenghreeFourClerkNum());
        setValue(sheet, 7, 38, data.getTiaozhengThreeClerkNum());
        setValue(sheet, 7, 39, data.getTiaozhengFourClerkNum());
        setValue(sheet, 7, 40, data.getTiaozhengThreeFourJunZhuanNum());
    }

    public static int getIndex(int[] arr, int value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                return i;
            }
        }
        return -1;//如果未找到返回-1
    }

    public int createRankApprovalExcelFile(Sheet sheet, int dataStartIndex, List<T> data, String[] columns) throws Exception {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN); //左边框
        cellStyle.setBorderTop(BorderStyle.THIN); //上边框
        cellStyle.setBorderRight(BorderStyle.THIN); //右边框
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font font = sheet.getWorkbook().createFont();
        font.setBold(false);
        font.setFontHeightInPoints((short) 9);//设置行高像素
        font.setFontName("仿宋");
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        int[] colarr = {0, 2, 4, 6, 8, 10, 12, 14, 17, 20, 23, 25, 27, 29, 31, 33, 35, 38};
        int rowIndex = dataStartIndex;
        if (data.size() > 0) {
            Class classType = data.get(0).getClass();
            for (Object d : data) {
                Row row = sheet.createRow(rowIndex);
                row.setHeightInPoints(24);//目的是想把行高设置成20px
                boolean isChanged = false;
                for (int i : colarr) {
                    //合并单元格
                    CellRangeAddress address = new CellRangeAddress(rowIndex, rowIndex, i, i + 1);
                    Cell c = row.createCell(i + 1);
                    c.setCellStyle(cellStyle);
                    if (14 == i || i == 17 | i == 20 || i == 35 || i == 38) {
                        address = new CellRangeAddress(rowIndex, rowIndex, i, i + 2);
                        c = row.createCell(i + 2);
                        c.setCellStyle(cellStyle);
                    }
                    c = row.createCell(i);
                    c.setCellStyle(cellStyle);
                    sheet.addMergedRegion(address);
                    cellStyle.setWrapText(true);
                    int j = getIndex(colarr, i);
                    if (columns[j] != null && columns[j].length() > 0) {
                        String normalName = columns[j].substring(0, 1).toUpperCase() + columns[j].substring(1);
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

    public void createReimbursementExcel(Sheet sheet, ReimbursementModel data) throws Exception {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font font = sheet.getWorkbook().createFont();
        font.setBold(false);
        font.setFontHeightInPoints((short) 12);//设置行高像素
        font.setFontName("仿宋");
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        setValue(sheet, 1, 2, data.getName());
        setValue(sheet, 1, 8, data.getSex());
        setValue(sheet, 1, 12, data.getYears());
        setValue(sheet, 3, 2, data.getBirthplace());
        setValue(sheet, 3, 8, data.getNationality());
        setValue(sheet, 3, 12, data.getParty());
        setValue(sheet, 4, 4, data.getUnitAndDuty());
        setValue(sheet, 4, 12, data.getWorkday());
        setValue(sheet, 6, 5, data.getFullTimeEducation());
        setValue(sheet, 6, 11, data.getFullTimeSchool());
        setValue(sheet, 8, 5, data.getWorkEducation());
        setValue(sheet, 8, 11, data.getWorkSchool());
        setValue(sheet, 10, 3, data.getDutyAndRank());
        setValue(sheet, 10, 11, data.getDutyAndRankTime());
        setValue(sheet, 10, 14, data.getLevel());
        setValue(sheet, 13, 0, data.getSuperYears());
        setValue(sheet, 13, 4, data.getCompetentYears());
        setValue(sheet, 13, 6, data.getNotCompetentYears());
        setValue(sheet, 11, 11, data.getIntendedRank());
        setValue(sheet, 14, 4, data.getConvertYears());
        setValue(sheet, 13, 11, data.getDeposeRank());
    }

    /**
     * 干部任免审批表
     *
     * @param sheet
     * @param data
     * @throws Exception
     */
    public void createDutyReimbursementExcel(Sheet sheet, ReimbursementModel data) throws Exception {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font font = sheet.getWorkbook().createFont();
        font.setBold(false);
        font.setFontHeightInPoints((short) 12);//设置行高像素
        font.setFontName("仿宋");
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        setValue(sheet, 1, 1, data.getName());
        setValue(sheet, 1, 3, data.getSex());
        setValue(sheet, 1, 5, data.getYears());
        setValue(sheet, 3, 1, data.getNationality());
        setValue(sheet, 3, 3, data.getBirthplace());
        setValue(sheet, 4, 1, data.getPartyTime());
        setValue(sheet, 4, 3, data.getWorkday());
        setValue(sheet, 8, 2, data.getFullTimeEducation());
        setValue(sheet, 8, 5, data.getFullTimeSchool());
        setValue(sheet, 10, 2, data.getWorkEducation());
        setValue(sheet, 10, 5, data.getWorkSchool());
        setValue(sheet, 12, 2, data.getNowDuty());
        setValue(sheet, 13, 2, data.getNiRenDuty());
        setValue(sheet, 14, 2, data.getNiMianDuty());
    }

    /**
     * 标题
     *
     * @param sheet
     * @param data
     * @throws Exception
     */
    public void createTitleExcel(Sheet sheet, String data) throws Exception {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);//设置行高像素
        font.setFontName("仿宋");
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        setValue(sheet, 0, 0, data);
    }

    /**
     * 职级职数使用审批表
     *
     * @param sheet
     * @param data
     * @throws Exception
     */
    public void createApprovalExcel(Sheet sheet, Sys_Approal data) throws Exception {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font font = sheet.getWorkbook().createFont();
        font.setBold(false);
        font.setFontHeightInPoints((short) 12);//设置行高像素
        font.setFontName("仿宋");
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        setValue(sheet, 1, 1, data.getUnitName());
        setValue(sheet, 1, 5, data.getUnitType());
        setValue(sheet, 1, 7, data.getLevel());
        setValue(sheet, 1, 9, data.getOfficialNum());
        setValue(sheet, 5, 0, data.getResearcherTotal());
        setValue(sheet, 5, 1, data.getOneTowResearcherNum());
        setValue(sheet, 5, 3, data.getThreeFourResearcherNum());
        setValue(sheet, 6, 1, data.getOneResearcherNum());
        setValue(sheet, 6, 2, data.getTowResearcherNum());
        setValue(sheet, 6, 3, data.getThreeResearcherNum());
        setValue(sheet, 6, 4, data.getFourResearcherNum());
        setValue(sheet, 5, 5, data.getClerkTotal());
        setValue(sheet, 5, 6, data.getOneTowClerkNum());
        setValue(sheet, 5, 8, data.getThreeFourClerkNum());
        setValue(sheet, 6, 6, data.getOneClerkNum());
        setValue(sheet, 6, 7, data.getTowClerkNum());
        setValue(sheet, 6, 8, data.getThreeClerkNum());
        setValue(sheet, 6, 9, data.getFourClerkNum());
        setValue(sheet, 10, 0, data.getResearcherUserTotal());
        if (StrUtils.strToLong(data.getOneResearcherJunNum()) > 0) {
            String str = data.getOneResearcherUserNum() + "\n军转(" + data.getOneResearcherJunNum() + ")";
            setValue(sheet, 10, 1, str);
        } else {
            setValue(sheet, 10, 1, data.getOneResearcherUserNum());
        }
        if (StrUtils.strToLong(data.getTowResearcherJunNum()) > 0) {
            String str = data.getTowResearcherUserNum() + "\n军转(" + data.getTowResearcherJunNum() + ")";
            setValue(sheet, 10, 2, str);
        } else {
            setValue(sheet, 10, 2, data.getTowResearcherUserNum());
        }
        if (StrUtils.strToLong(data.getThreeResearcherJunNum()) > 0) {
            String str = data.getThreeResearcherUserNum() + "\n军转(" + data.getThreeResearcherJunNum() + ")";
            setValue(sheet, 10, 3, str);
        } else {
            setValue(sheet, 10, 3, data.getThreeResearcherUserNum());
        }
        if (StrUtils.strToLong(data.getFourResearcherJunNum()) > 0) {
            String str = data.getFourResearcherUserNum() + "\n军转(" + data.getThreeResearcherJunNum() + ")";
            setValue(sheet, 10, 4, str);
        } else {
            setValue(sheet, 10, 4, data.getFourResearcherUserNum());
        }
        setValue(sheet, 10, 5, data.getUserTotal());
        if (StrUtils.strToLong(data.getOneClerkJunNum()) > 0) {
            String str = data.getOneClerkUserNum() + "\n军转(" + data.getOneClerkJunNum() + ")";
            setValue(sheet, 10, 6, str);
        } else {
            setValue(sheet, 10, 6, data.getOneClerkUserNum());
        }
        if (StrUtils.strToLong(data.getTowClerkJunNum()) > 0) {
            String str = data.getTowClerkUserNum() + "\n军转(" + data.getTowClerkJunNum() + ")";
            setValue(sheet, 10, 7, str);
        } else {
            setValue(sheet, 10, 7, data.getTowClerkUserNum());
        }
        if (StrUtils.strToLong(data.getThreeClerkJunNum()) > 0) {
            String str = data.getThreeClerkUserNum() + "\n军转(" + data.getThreeClerkJunNum() + ")";
            setValue(sheet, 10, 8, str);
        } else {
            setValue(sheet, 10, 8, data.getThreeClerkUserNum());
        }
        if (StrUtils.strToLong(data.getFourClerkJunNum()) > 0) {
            String str = data.getFourClerkUserNum() + "\n军转(" + data.getFourClerkJunNum() + ")";
            setValue(sheet, 10, 9, str);
        } else {
            setValue(sheet, 10, 9, data.getFourClerkUserNum());
        }
        setValue(sheet, 14, 0, data.getVacancyResearcherTotal());
        setValue(sheet, 14, 1, data.getOneResearcherVacancyNum());
        setValue(sheet, 14, 2, data.getTowResearcherVacancyNum());
        setValue(sheet, 14, 3, data.getThreeResearcherVacancyNum());
        setValue(sheet, 14, 4, data.getFourResearcherVacancyNum());
        setValue(sheet, 14, 5, data.getVacancyTotal());
        setValue(sheet, 14, 6, data.getOneClerkVacancyNum());
        setValue(sheet, 14, 7, data.getTowClerkVacancyNum());
        setValue(sheet, 14, 8, data.getThreeClerkVacancyNum());
        setValue(sheet, 14, 9, data.getFourClerkVacancyNum());
        setValue(sheet, 18, 1, data.getOneResearcherDraftingNum());
        setValue(sheet, 18, 2, data.getTowResearcherDraftingNum());
        setValue(sheet, 18, 3, data.getThreeResearcherDraftingNum());
        setValue(sheet, 18, 4, data.getFourResearcherDraftingNum());
        setValue(sheet, 18, 5, data.getDrafting());
        setValue(sheet, 18, 6, data.getOneClerkDraftingNum());
        setValue(sheet, 18, 7, data.getTowClerkDraftingNum());
        setValue(sheet, 18, 8, data.getThreeClerkDraftingNum());
        setValue(sheet, 18, 9, data.getFourClerkDraftingNum());
    }

    /**
     * row:行
     *
     * @param sheet
     * @param y
     * @param x
     * @param value
     * @return
     */
    public static Cell setValueStyle(Sheet sheet, int x, int y, String value, CellStyle cellStyle) {
        Row row = sheet.getRow(x);
        Cell c = null;
        if (row.getCell(y) != null) {
            c = row.getCell(y);
        } else {
            row.createCell(y);
            c = row.getCell(y);
        }
        if (value != null) {
            c.setCellValue(value);
        } else {
            c.setCellValue("");
        }
        c.setCellStyle(cellStyle);
//        setCellFormattedValue(c, value);
        return c;
    }

    /**
     * 设置表头
     *
     * @param sheet
     * @param data
     * @throws Exception
     */
    public void createExcelHeader(Sheet sheet, String[] data) throws Exception {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN); //左边框
        cellStyle.setBorderTop(BorderStyle.THIN); //上边框
        cellStyle.setBorderRight(BorderStyle.THIN); //右边框
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font font = sheet.getWorkbook().createFont();
        font.setBold(false);
        font.setFontHeightInPoints((short) 15);//设置行高像素
        font.setFontName("黑体");
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        for (int i = 3; i < data.length + 3; i++) {
            setValueStyle(sheet, 0, i, data[i - 3], cellStyle);
        }
    }

    /**
     * author h
     * 创建excel的压缩文件包
     *
     * @param tableName  压缩包名字
     * @param userAgent  处理firefox乱码
     * @param sheetIndex 名字所在表坐标
     * @param rowIndex   获取名字row的坐标
     * @param colIndex   获取名字col坐标
     */
    public void creatExcelZip(List<Workbook> workBookList, String tableName, HttpServletResponse response, String userAgent, int sheetIndex, int rowIndex, int colIndex) throws Exception {
        String fileName = tableName + ".zip";
        String zipFileName = "";
        if (userAgent.toLowerCase().indexOf("firefox") > 0) {
            zipFileName = new java.lang.String(fileName.getBytes("UTF-8"), "ISO8859-1");
        } else {
            zipFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
        }
        String zipName = zipFileName;
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=" + zipName);
        ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
        int i = 0;
        for (Workbook workBook : workBookList) {
            String name = workBook.getSheetName(0);
            if (rowIndex == 0 && colIndex == 0) {
                zipOut.putNextEntry(new ZipEntry(i + ".xls"));
            } else {
                Sheet sheet = workBook.getSheetAt(0);
                name = sheet.getRow(rowIndex).getCell(colIndex).getStringCellValue().trim();
                zipOut.putNextEntry(new ZipEntry(name + ".xls"));
            }
            i += 1;
            workBook.write(zipOut);
            workBook.close();
            //       println("压缩好第"+i+"个")
        }
        zipOut.close();
    }
}
