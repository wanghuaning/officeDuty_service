package com.local.common.filter;

import java.io.*;

public class FileUtil {
    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取json文件
     * @param excelInputSteam
     * @return
     */
    public static String readJsonFile(InputStream excelInputSteam){
        String jsonStr="";
        try {
            Reader reader=new InputStreamReader(excelInputSteam,"UTF-8");
            int ch=0;
            StringBuffer stringBuffer=new StringBuffer();
            while ((ch=reader.read())!=-1){
                stringBuffer.append((char) ch);
            }
            reader.close();
            jsonStr=stringBuffer.toString();
            return jsonStr;
        }catch (FileNotFoundException e){
            e.printStackTrace();
            return null;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
