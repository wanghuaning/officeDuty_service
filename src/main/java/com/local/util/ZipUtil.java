package com.local.util;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ZipUtil {
    private static final Log log = LogFactory.getLog(ZipUtil.class);


    /**
     * 解压缩
     *
     * @param zipfile File 需要解压缩的文件
     * @param descDir String 解压后的目标目录
     */
    public static void unZipFiles(File zipfile, String descDir) {
        try {
            // Open the ZIP file
            ZipFile zf = new ZipFile(zipfile);
            for (Enumeration entries = zf.entries(); entries.hasMoreElements(); ) {
                // Get the entry name
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String zipEntryName = entry.getName();
                InputStream in = zf.getInputStream(entry);
                // System.out.println(zipEntryName);
                OutputStream out = new FileOutputStream(descDir + zipEntryName);
                byte[] buf1 = new byte[1024];
                int len;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
                // Close the file and stream
                in.close();
                out.close();
            }
        } catch (IOException e) {
            log.error("ZipUtil unZipFiles exception:" + e);
        }
    }

    /**
     * 压缩文件
     *
     * @param srcfile File[] 需要压缩的文件列表
     */
    public static void zipFiles(List<File> srcfile, HttpServletResponse response, String zipNameStr,
                                HttpServletRequest request) throws UnsupportedEncodingException, IOException{
        String fileName = zipNameStr + ".zip";
        String zipFileName = "";
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.toLowerCase().indexOf("firefox") > 0) {
            zipFileName = new java.lang.String(fileName.getBytes("UTF-8"), "ISO8859-1");
        } else {
            zipFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
        }
        String zipName = zipFileName;
        byte[] buf = new byte[1024];
        try {
            // Create the ZIP file
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", "attachment; filename=" + zipName);
            ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());
//            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
            // Compress the files
            for (int i = 0; i < srcfile.size(); i++) {
                File file = srcfile.get(i);
                FileInputStream in = new FileInputStream(file);
                // Add ZIP entry to output stream.
                zipOut.putNextEntry(new ZipEntry(file.getName()));
                // Transfer bytes from the file to the ZIP file
                int len;
                while ((len = in.read(buf)) > 0) {
                    zipOut.write(buf, 0, len);
                }
                // Complete the entry
                zipOut.closeEntry();
                in.close();
            }
            // Complete the ZIP file
            zipOut.close();
        } catch (IOException e) {
            log.error("ZipUtil zipFiles exception:" + e);
        }
    }

    public static void getFile(String path){
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
    }
   public static boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delFile(f);
            }
        }
        return file.delete();
    }


    /**
     * 定时删除日志
     *
     * @param path 路径
     * @param day 最近几天
     */
    public static void deleteLogFileMyself(String path, int day) {
        File file = new File(path);
        Calendar calendar = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date currentDate = new Date();
            calendar.setTime(currentDate);
            calendar.add(Calendar.DATE, -day); //得到前几天
            Date date = calendar.getTime();
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {//xxxx_2019-09-05_2325
                    String[] arr=f.getName().split("_");
                    if (arr.length>2){
                        Date logdate = df.parse(arr[1]);
                        //若是文件夹且在设定日期之前
                        if (f.exists() && logdate.before(date)) {
                            f.delete();
                        }
                    }
                } }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main
     *
     * @param args
    public static void main(String[] args) {
        List<File> srcfile = new ArrayList<File>();
        srcfile.add(new File("D:\\公务员\\压缩\\1.xls"));
        srcfile.add(new File("D:\\公务员\\压缩\\2.xls"));
        srcfile.add(new File("D:\\公务员\\压缩\\3.xls"));
        srcfile.add(new File("D:\\公务员\\压缩\\4.xls"));
        File zipfile = new File("D:\\公务员\\压缩\\edm.zip");
//        ZipUtil.zipFiles(srcfile, zipfile);
    }
    */
}