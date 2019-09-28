package com.local.controller;

import com.local.entity.*;
import com.local.service.AnalysisXMLService;
import com.local.util.Result;
import com.local.util.ResultCode;
import com.local.util.ResultMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by chennan on 2019/8/15.
 */

@Api(value = "辐射安全管理系统数据", description = "辐射安全管理系统数据接口")
@RestController
@RequestMapping("/api")
public class AnalysisXMLController {

    @Autowired
    AnalysisXMLService analysisXMLService;

    /*public static List showDirectory(File file,List filelistone) {
        File[] files=file.listFiles();
        for(File a:files) {
            System.out.println(a.getAbsolutePath());
            if(a.getAbsolutePath().contains(".xml")){
                filelistone.add(a.getAbsolutePath());
            }
            if(a.isDirectory()) {
                showDirectory(a,filelistone);
            }
        }
        return filelistone;
    }*/

    public static List showDirectory(File file) {
        List filelist=new ArrayList();
        File[] files=file.listFiles();
        for(File a:files) {
            System.out.println(a.getAbsolutePath());
            if(a.getAbsolutePath().contains(".xml")){
                filelist.add(a.getAbsolutePath());
            }
            if(a.isDirectory()) {
                showDirectory(a);
            }
        }
        return filelist;
    }

    @ApiOperation(value = "解析辐射安全管理系统数据接口", notes = "解析辐射安全管理系统数据接口", httpMethod = "POST", tags = {"解析辐射安全管理系统数据接口"})
    @PostMapping("/analysisXML")
    @ResponseBody
    public String AnalysisXMLTobase(String url) {
        long lasting =System.currentTimeMillis();

        try {
            File f=new File(url);
           /* File f=new File("E:/国家核技术利用辐射安全管理系统/数据包XML-20190805/云南省/下行");*/
            //List filelistone=new ArrayList();
            //List filelist=showDirectory(f,filelistone);
            List filelist=showDirectory(f);
            //1.创建DocumentBuilderFactory对象
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            //2.创建DocumentBuilder对象
            DocumentBuilder builder=factory.newDocumentBuilder();
            List<TTLRCOMPANY> ttlrcompanylist = new ArrayList<TTLRCOMPANY>();
            for(int a=0;a<filelist.size();a++){
            File file=new File(filelist.get(a).toString());
            Document doc = builder.parse(file);
            NodeList nl = doc.getElementsByTagName("TT_LR_COMPANY");
            for (int i = 0; i < nl.getLength(); i++) {
                // 获取一个节点
                Node node = nl.item(i);
                //获取所有子节点数据
                NodeList childNodes=node.getChildNodes();
                TTLRCOMPANY ttlrcompany =new TTLRCOMPANY();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Node childNode=childNodes.item(j);
                    if(childNode.getNodeName().contains("#text")){
                        continue;
                    }else{
                        if(childNode.getNodeName().contains("LONGITUDE")){
                            ttlrcompany.setLONGITUDE(Integer.parseInt(childNode.getTextContent()));
                        }
                        if(childNode.getNodeName().contains("LONGITUDE_HOUR")){
                            ttlrcompany.setLONGITUDE_HOUR(Integer.parseInt(childNode.getTextContent()));
                        }
                        if(childNode.getNodeName().contains("LONGITUDE_SECOND")){
                            ttlrcompany.setLONGITUDE_SECOND(Integer.parseInt(childNode.getTextContent()));
                        }
                        if(childNode.getNodeName().contains("LATITUDE")){
                            ttlrcompany.setLATITUDE(Integer.parseInt(childNode.getTextContent()));
                        }
                        if(childNode.getNodeName().contains("LATITUDE_HOUR")){
                            ttlrcompany.setLATITUDE_HOUR(Integer.parseInt(childNode.getTextContent()));
                        }
                        if(childNode.getNodeName().contains("LATITUDE_SECOND")){
                            ttlrcompany.setLATITUDE_SECOND(Integer.parseInt(childNode.getTextContent()));
                        }
                        if(childNode.getNodeName().contains("NUCLIDE_SALE_ACTIVE")){
                            ttlrcompany.setNUCLIDE_SALE_ACTIVE(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("NUCLIDE_USE_ACTIVE")){
                            ttlrcompany.setNUCLIDE_USE_ACTIVE(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("RADIAL_PRODUCE_ACTIVE")){
                            ttlrcompany.setRADIAL_PRODUCE_ACTIVE(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("RADIAL_SALE_ACTIVE")){
                            ttlrcompany.setRADIAL_SALE_ACTIVE(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("RADIAL_SALE_AND_PRODUCE")){
                            ttlrcompany.setRADIAL_SALE_AND_PRODUCE(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("NON_SEAL_ACTIVE")){
                            ttlrcompany.setNON_SEAL_ACTIVE(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("PASS_NO")){
                            ttlrcompany.setPASS_NO(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("REQUEST_NO")){
                            ttlrcompany.setREQUEST_NO(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().equals("YWID")){
                            ttlrcompany.setYWID(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("RESPONSE_NO")){
                            ttlrcompany.setRESPONSE_NO(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("NON_SEAL_TYPE")){
                            ttlrcompany.setNON_SEAL_TYPE(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("USE_AND_COLLECTION")){
                            ttlrcompany.setUSE_AND_COLLECTION(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("UNIFY_SOCIETY_CODE")){
                            ttlrcompany.setUNIFY_SOCIETY_CODE(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("REMARK")){
                            ttlrcompany.setREMARK(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("LICENSE_CONDITION")){
                            ttlrcompany.setLICENSE_CONDITION(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("NOPASS_REASON")){
                            ttlrcompany.setNOPASS_REASON(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("INSERT_DATE")){
                            Timestamp ts = new Timestamp(format.parse(childNode.getTextContent()).getTime());
                            ttlrcompany.setINSERT_DATE(ts);
                        }
                        if(childNode.getNodeName().contains("REGIST_ADDR_POST")){
                            ttlrcompany.setREGIST_ADDR_POST(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("ACCEPT_PERSON")){
                            ttlrcompany.setACCEPT_PERSON(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("MODIFY_DATE")){
                            Timestamp te = new Timestamp(format.parse(childNode.getTextContent()).getTime());
                            ttlrcompany.setMODIFY_DATE(te);
                        }
                        if(childNode.getNodeName().contains("CONTACT_ADDR_POST")){
                            ttlrcompany.setCONTACT_ADDR_POST(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("DO_DATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            ttlrcompany.setDO_DATE(sdf.parse(childNode.getTextContent()));
                        }
                        if(childNode.getNodeName().contains("ACCEPT_DATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            ttlrcompany.setACCEPT_DATE(sdf.parse(childNode.getTextContent()));
                        }
                        if(childNode.getNodeName().contains("SUBMIT_DATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            ttlrcompany.setSUBMIT_DATE(sdf.parse(childNode.getTextContent()));
                        }
                        if(childNode.getNodeName().contains("LICENSE_NO")){
                            ttlrcompany.setLICENSE_NO(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("RADIAL_ORGCONTACT_MOBILE")){
                            ttlrcompany.setRADIAL_ORGCONTACT_MOBILE(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("INSTANCEID")){
                            ttlrcompany.setINSTANCEID(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("INDUSTRY_TYPE")){
                            ttlrcompany.setINDUSTRY_TYPE(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("LICENSE_ISSUE_ORGAN")){
                            ttlrcompany.setLICENSE_ISSUE_ORGAN(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("RADIAL_ORGCONTACT_EMAIL")){
                            ttlrcompany.setRADIAL_ORGCONTACT_EMAIL(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().equals("NAME")){
                            ttlrcompany.setNAME(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("LICENSE_START_DATE")){
                            Timestamp ta = new Timestamp(format.parse(childNode.getTextContent()).getTime());
                            ttlrcompany.setLICENSE_START_DATE(ta);
                        }
                        if(childNode.getNodeName().contains("RESPONSE_STATUS")){
                            ttlrcompany.setRESPONSE_STATUS(Integer.parseInt(childNode.getTextContent()));
                        }
                        if(childNode.getNodeName().contains("CONTACT_ADDR")){
                            ttlrcompany.setCONTACT_ADDR(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("COMPANY_TYPE")){
                            ttlrcompany.setCOMPANY_TYPE(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("REGIST_ADDR")){
                            ttlrcompany.setREGIST_ADDR(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("CITY")){
                            ttlrcompany.setCITY(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("INSERT_USER")){
                            ttlrcompany.setINSERT_USER(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("REGION_CODE_1")){
                            ttlrcompany.setREGION_CODE_1(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("REGION_CODE_2")){
                            ttlrcompany.setREGION_CODE_2(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("RADIAL_ORGCONTACT_FAX")){
                            ttlrcompany.setRADIAL_ORGCONTACT_FAX(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("RADIAL_ORGCONTACT_PERSON")){
                            ttlrcompany.setRADIAL_ORGCONTACT_PERSON(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("RADIAL_USE_ACTIVE")){
                            ttlrcompany.setRADIAL_USE_ACTIVE(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("ORGANIZATION_CODE")){
                            ttlrcompany.setORGANIZATION_CODE(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("IS_APPLY")){
                            ttlrcompany.setIS_APPLY(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("STATUS")){
                            ttlrcompany.setSTATUS(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("CONTACT_PHONE")){
                            ttlrcompany.setCONTACT_PHONE(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("BE_PASS")){
                            ttlrcompany.setBE_PASS(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("LEGAL_PERSON_ID")){
                            ttlrcompany.setLEGAL_PERSON_ID(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("RADIAL_ORGCONTACT_PHONE")){
                            ttlrcompany.setRADIAL_ORGCONTACT_PHONE(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("CONTACT_PERSON")){
                            ttlrcompany.setCONTACT_PHONE(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("LEGAL_PERSON")){
                            ttlrcompany.setLEGAL_PERSON(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("KIND")){
                            ttlrcompany.setKIND(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("RADIAL_ORGNAME")){
                            ttlrcompany.setRADIAL_ORGNAME(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("DISTRICT")){
                            ttlrcompany.setDISTRICT(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("COMPANY_ID")){
                            ttlrcompany.setCOMPANY_ID(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("LICENSE_END_DATE")){
                            Timestamp tb = new Timestamp(format.parse(childNode.getTextContent()).getTime());
                            ttlrcompany.setLICENSE_END_DATE(tb);
                        }
                        if(childNode.getNodeName().contains("LICENCE_ISSUING_AUTHORITY")){
                            ttlrcompany.setLICENCE_ISSUING_AUTHORITY(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("LEGAL_PERSON_PHONE")){
                            ttlrcompany.setLEGAL_PERSON_PHONE(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("PROVINCE")){
                            ttlrcompany.setPROVINCE(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("DO_PERSON")){
                            ttlrcompany.setDO_PERSON(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("LEGAL_PERSON_TYPE")){
                            ttlrcompany.setLEGAL_PERSON_TYPE(Integer.parseInt(childNode.getTextContent()));
                        }
                        if(childNode.getNodeName().contains("MODIFY_USER")){
                            ttlrcompany.setMODIFY_USER(childNode.getTextContent());
                        }
                        if(childNode.getNodeName().contains("COMPANY_CODE_TYPE")){
                            ttlrcompany.setCOMPANY_CODE_TYPE(Integer.parseInt(childNode.getTextContent()));
                        }
                        if(childNode.getNodeName().contains("NUCLIDE_PRODUCE_ACTIVE")){
                            ttlrcompany.setNUCLIDE_PRODUCE_ACTIVE(childNode.getTextContent());
                        }

                    }


                    //System.out.println(childNode.getNodeName()+":"+childNode.getTextContent());
                }
                ttlrcompanylist.add(ttlrcompany);
            }

            /*List<TTLRSITE> ttlrsitelist = new ArrayList<TTLRSITE>();
            NodeList nl2 = doc.getElementsByTagName("TT_LR_SITE");
            for (int i = 0; i < nl2.getLength(); i++) {
                // 获取一个节点
                Node node2 = nl2.item(i);
                //获取所有子节点数据
                NodeList childNodes2=node2.getChildNodes();
                TTLRSITE ttlrsite = new TTLRSITE();
                for (int j = 0; j < childNodes2.getLength(); j++) {
                    Node childNode2=childNodes2.item(j);
                    DateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                    if(childNode2.getNodeName().contains("#text")){
                        continue;
                    }else{
                        if(childNode2.getNodeName().contains("SITE_CODE")){
                            ttlrsite.setSITE_CODE(childNode2.getTextContent());
                        }
                        if(childNode2.getNodeName().contains("NAME")){
                            ttlrsite.setNAME(childNode2.getTextContent());
                        }
                        if(childNode2.getNodeName().contains("YWID")){
                            ttlrsite.setYWID(childNode2.getTextContent());
                        }
                        if(childNode2.getNodeName().contains("REQUEIR_YWID")){
                            ttlrsite.setREQUEIR_YWID(childNode2.getTextContent());
                        }
                        if(childNode2.getNodeName().contains("ADDRESS")){
                            ttlrsite.setADDRESS(childNode2.getTextContent());
                        }
                        if(childNode2.getNodeName().contains("COMPANY_ID")){
                            ttlrsite.setCOMPANY_ID(childNode2.getTextContent());
                        }
                        if(childNode2.getNodeName().contains("WSDEPT_ID")){
                            ttlrsite.setWSDEPT_ID(childNode2.getTextContent());
                        }
                        if(childNode2.getNodeName().contains("SITE_ID")){
                            ttlrsite.setSITE_ID(childNode2.getTextContent());
                        }
                        if(childNode2.getNodeName().contains("DEPARTMENT")){
                            ttlrsite.setDEPARTMENT(childNode2.getTextContent());
                        }
                        if(childNode2.getNodeName().contains("UPDATEUSER")){
                            ttlrsite.setUPDATEUSER(childNode2.getTextContent());
                        }
                        if(childNode2.getNodeName().contains("PRINCIPAL")){
                            ttlrsite.setPRINCIPAL(childNode2.getTextContent());
                        }
                        if(childNode2.getNodeName().contains("UPDATETIME")){
                            Timestamp tb = new Timestamp(format.parse(childNode2.getTextContent()).getTime());
                            ttlrsite.setUPDATETIME(tb);
                        }

                    }
                }
                ttlrsitelist.add(ttlrsite);
            }

            List<TTLRWSDEPT> ttlrwsdeptlist = new ArrayList<TTLRWSDEPT>();
            NodeList nl3 = doc.getElementsByTagName("TT_LR_WSDEPT");
            for (int i = 0; i < nl3.getLength(); i++) {
                // 获取一个节点
                Node node3 = nl3.item(i);
                //获取所有子节点数据
                NodeList childNodes3=node3.getChildNodes();
                TTLRWSDEPT ttlrwsdept = new TTLRWSDEPT();
                for (int j = 0; j < childNodes3.getLength(); j++) {
                    Node childNode3=childNodes3.item(j);
                    DateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                    if(childNode3.getNodeName().contains("#text")){
                        continue;
                    }else{
                        if(childNode3.getNodeName().contains("DETAIL_ADDR")){
                            ttlrwsdept.setDETAIL_ADDR(childNode3.getTextContent());
                        }
                        if(childNode3.getNodeName().contains("CREATEPERSON")){
                            ttlrwsdept.setCREATEPERSON(childNode3.getTextContent());
                        }
                        if(childNode3.getNodeName().contains("COUNTRY")){
                            ttlrwsdept.setCOUNTRY(childNode3.getTextContent());
                        }
                        if(childNode3.getNodeName().contains("COMPANY_ID")){
                            ttlrwsdept.setCOMPANY_ID(childNode3.getTextContent());
                        }
                        if(childNode3.getNodeName().contains("DEPTNAME")){
                            ttlrwsdept.setDEPTNAME(childNode3.getTextContent());
                        }
                        if(childNode3.getNodeName().contains("PRINCIPAL")){
                            ttlrwsdept.setPRINCIPAL(childNode3.getTextContent());
                        }
                        if(childNode3.getNodeName().contains("PROVINCE")){
                            ttlrwsdept.setPROVINCE(childNode3.getTextContent());
                        }
                        if(childNode3.getNodeName().contains("ID")){
                            ttlrwsdept.setID(childNode3.getTextContent());
                        }
                        if(childNode3.getNodeName().contains("CITY")){
                            ttlrwsdept.setCITY(childNode3.getTextContent());
                        }
                        if(childNode3.getNodeName().contains("REGION_CODE")){
                            ttlrwsdept.setREGION_CODE(childNode3.getTextContent());
                        }
                        if(childNode3.getNodeName().contains("UPDATETIME")){
                            Timestamp tb = new Timestamp(format.parse(childNode3.getTextContent()).getTime());
                            ttlrwsdept.setUPDATETIME(tb);
                        }

                    }
                }
                ttlrwsdeptlist.add(ttlrwsdept);
            }

            List<TTLRRADIALSETACTIVERANGE> ttlrlist = new ArrayList<TTLRRADIALSETACTIVERANGE>();
            NodeList nl4 = doc.getElementsByTagName("TT_LR_RADIALSET_ACTIVERANGE");
            for (int i = 0; i < nl4.getLength(); i++) {
                // 获取一个节点
                Node node4 = nl4.item(i);
                //获取所有子节点数据
                NodeList childNodes4=node4.getChildNodes();
                TTLRRADIALSETACTIVERANGE ttlr = new TTLRRADIALSETACTIVERANGE();
                for (int j = 0; j < childNodes4.getLength(); j++) {
                    Node childNode4=childNodes4.item(j);
                    DateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                    if(childNode4.getNodeName().contains("#text")){
                        continue;
                    }else{
                        if(childNode4.getNodeName().contains("SET_TYPE")){
                            ttlr.setSET_TYPE(childNode4.getTextContent());
                        }
                        if(childNode4.getNodeName().contains("NAME")){
                            ttlr.setNAME(childNode4.getTextContent());
                        }
                        if(childNode4.getNodeName().contains("COMPANY_ID")){
                            ttlr.setCOMPANY_ID(childNode4.getTextContent());
                        }
                        if(childNode4.getNodeName().contains("YWID")){
                            ttlr.setYWID(childNode4.getTextContent());
                        }
                        if(childNode4.getNodeName().contains("REQUEIR_YWID")){
                            ttlr.setREQUEIR_YWID(childNode4.getTextContent());
                        }
                        if(childNode4.getNodeName().contains("ACTIVE_RANGE_ID")){
                            ttlr.setACTIVE_RANGE_ID(childNode4.getTextContent());
                        }
                        if(childNode4.getNodeName().contains("SET_COUNT")){
                            ttlr.setSET_COUNT(Integer.parseInt(childNode4.getTextContent()));
                        }
                        if(childNode4.getNodeName().contains("ACTIVE_TYPE")){
                            ttlr.setACTIVE_TYPE(childNode4.getTextContent());
                        }
                        if(childNode4.getNodeName().contains("SET_NAME")){
                            ttlr.setSET_NAME(childNode4.getTextContent());
                        }
                        if(childNode4.getNodeName().contains("UPDATEUSER")){
                            ttlr.setUPDATEUSER(childNode4.getTextContent());
                        }
                        if(childNode4.getNodeName().contains("UPDATETIME")){
                            Timestamp tb = new Timestamp(format.parse(childNode4.getTextContent()).getTime());
                            ttlr.setUPDATETIME(tb);
                        }

                    }
                }
                ttlrlist.add(ttlr);
            }

            List<TTLRRADIALSETRECORD> ttlrrecordlist = new ArrayList<TTLRRADIALSETRECORD>();
            NodeList nl5 = doc.getElementsByTagName("TT_LR_RADIALSET_RECORD");
            for (int i = 0; i < nl5.getLength(); i++) {
                // 获取一个节点
                Node node5 = nl5.item(i);
                //获取所有子节点数据
                NodeList childNodes5=node5.getChildNodes();
                TTLRRADIALSETRECORD ttlrrecord = new TTLRRADIALSETRECORD();
                for (int j = 0; j < childNodes5.getLength(); j++) {
                    Node childNode5=childNodes5.item(j);
                    DateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                    if(childNode5.getNodeName().contains("#text")){
                        continue;
                    }else{
                        if(childNode5.getNodeName().contains("YWID")){
                            ttlrrecord.setYWID(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("REQUEIR_YWID")){
                            ttlrrecord.setREQUEIR_YWID(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("INSERT_DATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            ttlrrecord.setINSERT_DATE(sdf.parse(childNode5.getTextContent()));
                        }
                        if(childNode5.getNodeName().contains("DEST_AUDITDATE")){
                            Timestamp tb = new Timestamp(format.parse(childNode5.getTextContent()).getTime());
                            ttlrrecord.setDEST_AUDITDATE(tb);
                        }
                        if(childNode5.getNodeName().contains("REMARK")){
                            ttlrrecord.setREMARK(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("SOURCE")){
                            ttlrrecord.setSOURCE(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("SRC_AUDITPERSON")){
                            ttlrrecord.setSRC_AUDITPERSON(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("SRC_AUDITDATE")){
                            ttlrrecord.setSRC_AUDITDATE(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("DESTINATION")){
                            ttlrrecord.setDESTINATION(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("DEST_AUDITPERSON")){
                            ttlrrecord.setDEST_AUDITPERSON(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("PARTICLE_ENERGY")){
                            ttlrrecord.setPARTICLE_ENERGY(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("SITE_DETAIL")){
                            ttlrrecord.setSITE_DETAIL(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("USE_TYPE_OTHER")){
                            ttlrrecord.setUSE_TYPE_OTHER(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("SPEC_AND_MODEL")){
                            ttlrrecord.setSPEC_AND_MODEL(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("STATUS")){
                            ttlrrecord.setSTATUS(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("SITE")){
                            ttlrrecord.setSITE(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("FLAG")){
                            ttlrrecord.setFLAG(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("POWER")){
                            ttlrrecord.setPOWER(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("RECORD_ID")){
                            ttlrrecord.setRECORD_ID(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("USE_TYPE")){
                            ttlrrecord.setUSE_TYPE(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("BE_HISTORY")){
                            ttlrrecord.setBE_HISTORY(Integer.parseInt(childNode5.getTextContent()));
                        }
                        if(childNode5.getNodeName().contains("SITE_CODE")){
                            ttlrrecord.setSITE_CODE(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("VOLTAGE")){
                            ttlrrecord.setVOLTAGE(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("AMPEREMETER")){
                            ttlrrecord.setAMPEREMETER(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("SET_NAME_INFO")){
                            ttlrrecord.setSET_NAME_INFO(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("COMPANY_ID")){
                            ttlrrecord.setCOMPANY_ID(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("USER_TYPE")){
                            ttlrrecord.setUSER_TYPE(Integer.parseInt(childNode5.getTextContent()));
                        }
                        if(childNode5.getNodeName().contains("SET_TYPE")){
                            ttlrrecord.setSET_TYPE(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("INSERT_USER")){
                            ttlrrecord.setINSERT_USER(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("SET_NAME")){
                            ttlrrecord.setSET_NAME(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("UPDATEUSER")){
                            ttlrrecord.setUPDATEUSER(childNode5.getTextContent());
                        }
                        if(childNode5.getNodeName().contains("UPDATETIME")){
                            Timestamp tb = new Timestamp(format.parse(childNode5.getTextContent()).getTime());
                            ttlrrecord.setUPDATETIME(tb);
                        }

                    }

                }
                ttlrrecordlist.add(ttlrrecord);
            }

            List<TTLRMONITORINSTRUMENT> ttlrinstrumentlist = new ArrayList<TTLRMONITORINSTRUMENT>();
            NodeList nl6 = doc.getElementsByTagName("TT_LR_MONITOR_INSTRUMENT");
            for (int i = 0; i < nl6.getLength(); i++) {
                // 获取一个节点
                Node node6 = nl6.item(i);
                //获取所有子节点数据
                NodeList childNodes6=node6.getChildNodes();
                TTLRMONITORINSTRUMENT ttlrinstrument = new TTLRMONITORINSTRUMENT();
                for (int j = 0; j < childNodes6.getLength(); j++) {
                    Node childNode6=childNodes6.item(j);
                    if(childNode6.getNodeName().contains("#text")){
                        continue;
                    }else{
                        if(childNode6.getNodeName().contains("REQUEIR_YWID")){
                            ttlrinstrument.setREQUEIR_YWID(childNode6.getTextContent());
                        }
                        if(childNode6.getNodeName().contains("REMARK")){
                            ttlrinstrument.setREMARK(childNode6.getTextContent());
                        }
                        if(childNode6.getNodeName().contains("STYLE")){
                            ttlrinstrument.setSTYLE(childNode6.getTextContent());
                        }
                        if(childNode6.getNodeName().contains("NAME")){
                            ttlrinstrument.setNAME(childNode6.getTextContent());
                        }
                        if(childNode6.getNodeName().contains("COMPANY_ID")){
                            ttlrinstrument.setCOMPANY_ID(childNode6.getTextContent());
                        }
                        if(childNode6.getNodeName().contains("YWID")){
                            ttlrinstrument.setYWID(childNode6.getTextContent());
                        }
                        if(childNode6.getNodeName().contains("AMOUNT")){
                            ttlrinstrument.setAMOUNT(Integer.parseInt(childNode6.getTextContent()));
                        }
                        if(childNode6.getNodeName().contains("STATUS")){
                            ttlrinstrument.setSTATUS(childNode6.getTextContent());
                        }
                        if(childNode6.getNodeName().contains("MONITOR_INSTRUMENT_ID")){
                            ttlrinstrument.setMONITOR_INSTRUMENT_ID(childNode6.getTextContent());
                        }
                        if(childNode6.getNodeName().contains("BUY_DATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            ttlrinstrument.setBUY_DATE(sdf.parse(childNode6.getTextContent()));
                        }

                    }

                }
                ttlrinstrumentlist.add(ttlrinstrument);
            }

            List<TTLRPROTECTGOODS> ttlrgoodslist = new ArrayList<TTLRPROTECTGOODS>();
            NodeList nl7 = doc.getElementsByTagName("TT_LR_PROTECT_GOODS");
            for (int i = 0; i < nl7.getLength(); i++) {
                // 获取一个节点
                Node node7 = nl7.item(i);
                //获取所有子节点数据
                NodeList childNodes7=node7.getChildNodes();
                TTLRPROTECTGOODS ttlrgoods = new TTLRPROTECTGOODS();
                for (int j = 0; j < childNodes7.getLength(); j++) {
                    Node childNode7=childNodes7.item(j);
                    DateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                    if(childNode7.getNodeName().contains("#text")){
                        continue;
                    }else{
                        if(childNode7.getNodeName().contains("YWID")){
                            ttlrgoods.setYWID(childNode7.getTextContent());
                        }
                        if(childNode7.getNodeName().contains("REQUEIR_YWID")){
                            ttlrgoods.setREQUEIR_YWID(childNode7.getTextContent());
                        }
                        if(childNode7.getNodeName().contains("QIAN_WEI_BO")){
                            ttlrgoods.setQIAN_WEI_BO(Integer.parseInt(childNode7.getTextContent()));
                        }
                        if(childNode7.getNodeName().contains("QIAN_YI")){
                            ttlrgoods.setQIAN_YI(Integer.parseInt(childNode7.getTextContent()));
                        }
                        if(childNode7.getNodeName().contains("COMPANY_ID")){
                            ttlrgoods.setCOMPANY_ID(childNode7.getTextContent());
                        }
                        if(childNode7.getNodeName().contains("UPDATEUSER")){
                            ttlrgoods.setUPDATEUSER(childNode7.getTextContent());
                        }
                        if(childNode7.getNodeName().contains("OTHERS")){
                            ttlrgoods.setOTHERS(childNode7.getTextContent());
                        }
                        if(childNode7.getNodeName().contains("QIAN_MAO")){
                            ttlrgoods.setQIAN_MAO(Integer.parseInt(childNode7.getTextContent()));
                        }
                        if(childNode7.getNodeName().contains("QIAN_YAN_JING")){
                            ttlrgoods.setQIAN_YAN_JING(Integer.parseInt(childNode7.getTextContent()));
                        }
                        if(childNode7.getNodeName().contains("QIAN_PING_FENG")){
                            ttlrgoods.setQIAN_PING_FENG(Integer.parseInt(childNode7.getTextContent()));
                        }
                        if(childNode7.getNodeName().contains("PROTECT_GOODS_ID")){
                            ttlrgoods.setPROTECT_GOODS_ID(childNode7.getTextContent());
                        }
                        if(childNode7.getNodeName().contains("QIAN_WEI_QUN")){
                            ttlrgoods.setQIAN_WEI_QUN(Integer.parseInt(childNode7.getTextContent()));
                        }
                        if(childNode7.getNodeName().contains("QIAN_SHOU_TAO")){
                            ttlrgoods.setQIAN_SHOU_TAO(Integer.parseInt(childNode7.getTextContent()));
                        }
                        if(childNode7.getNodeName().contains("UPDATETIME")){
                            Timestamp tb = new Timestamp(format.parse(childNode7.getTextContent()).getTime());
                            ttlrgoods.setUPDATETIME(tb);
                        }
                        if(childNode7.getNodeName().contains("GEREN_JILIANG_JI")){
                            ttlrgoods.setGEREN_JILIANG_JI(Integer.parseInt(childNode7.getTextContent()));
                        }
                        if(childNode7.getNodeName().contains("GEREN_JILIANG")){
                            ttlrgoods.setGEREN_JILIANG(Integer.parseInt(childNode7.getTextContent()));
                        }

                    }

                }
                ttlrgoodslist.add(ttlrgoods);
            }

            List<TTLRRADIALMANAGEPERSON> ttlrmanagepersonlist = new ArrayList<TTLRRADIALMANAGEPERSON>();
            NodeList nl8 = doc.getElementsByTagName("TT_LR_RADIAL_MANAGE_PERSON");
            for (int i = 0; i < nl8.getLength(); i++) {
                // 获取一个节点
                Node node8 = nl8.item(i);
                //获取所有子节点数据
                NodeList childNodes8=node8.getChildNodes();
                TTLRRADIALMANAGEPERSON ttlrmanageperson = new TTLRRADIALMANAGEPERSON();
                for (int j = 0; j < childNodes8.getLength(); j++) {
                    Node childNode8=childNodes8.item(j);
                    DateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                    if(childNode8.getNodeName().contains("#text")){
                        continue;
                    }else{
                        if(childNode8.getNodeName().contains("YWID")){
                            ttlrmanageperson.setYWID(childNode8.getTextContent());
                        }
                        if(childNode8.getNodeName().contains("REQUEIR_YWID")){
                            ttlrmanageperson.setREQUEIR_YWID(childNode8.getTextContent());
                        }
                        if(childNode8.getNodeName().contains("CARD")){
                            ttlrmanageperson.setCARD(childNode8.getTextContent());
                        }
                        if(childNode8.getNodeName().contains("COMPANY_ID")){
                            ttlrmanageperson.setCOMPANY_ID(childNode8.getTextContent());
                        }
                        if(childNode8.getNodeName().contains("PERSON_NAME")){
                            ttlrmanageperson.setPERSON_NAME(childNode8.getTextContent());
                        }
                        if(childNode8.getNodeName().contains("PERSON_JOB")){
                            ttlrmanageperson.setPERSON_JOB(childNode8.getTextContent());
                        }
                        if(childNode8.getNodeName().contains("MAJOR")){
                            ttlrmanageperson.setMAJOR(childNode8.getTextContent());
                        }
                        if(childNode8.getNodeName().contains("FULL_OR_PART")){
                            ttlrmanageperson.setFULL_OR_PART(childNode8.getTextContent());
                        }
                        if(childNode8.getNodeName().contains("SEX")){
                            ttlrmanageperson.setSEX(childNode8.getTextContent());
                        }
                        if(childNode8.getNodeName().contains("MANAGE_PERSON_ID")){
                            ttlrmanageperson.setMANAGE_PERSON_ID(childNode8.getTextContent());
                        }
                        if(childNode8.getNodeName().contains("NUCLEUS_ENGINEER")){
                            ttlrmanageperson.setNUCLEUS_ENGINEER(childNode8.getTextContent());
                        }
                        if(childNode8.getNodeName().contains("DUTY")){
                            ttlrmanageperson.setDUTY(childNode8.getTextContent());
                        }
                        if(childNode8.getNodeName().contains("DEPARTMENT")){
                            ttlrmanageperson.setDEPARTMENT(childNode8.getTextContent());
                        }
                        if(childNode8.getNodeName().contains("UPDATEUSER")){
                            ttlrmanageperson.setUPDATEUSER(childNode8.getTextContent());
                        }
                        if(childNode8.getNodeName().contains("UPDATETIME")){
                            Timestamp tb = new Timestamp(format.parse(childNode8.getTextContent()).getTime());
                            ttlrmanageperson.setUPDATETIME(tb);
                        }

                    }

                }
                ttlrmanagepersonlist.add(ttlrmanageperson);
            }

            List<TTLRRADIALWORKPERSON> ttlrworkpersonlist = new ArrayList<TTLRRADIALWORKPERSON>();
            NodeList nl9 = doc.getElementsByTagName("TT_LR_RADIAL_WORK_PERSON");
            for (int i = 0; i < nl9.getLength(); i++) {
                // 获取一个节点
                Node node9 = nl9.item(i);
                //获取所有子节点数据
                NodeList childNodes9=node9.getChildNodes();
                TTLRRADIALWORKPERSON ttlrworkperson = new TTLRRADIALWORKPERSON();
                for (int j = 0; j < childNodes9.getLength(); j++) {
                    Node childNode9=childNodes9.item(j);
                    if(childNode9.getNodeName().contains("#text")){
                        continue;
                    }else{
                        if(childNode9.getNodeName().contains("YWID")){
                            ttlrworkperson.setYWID(childNode9.getTextContent());
                        }
                        if(childNode9.getNodeName().contains("REQUEIR_YWID")){
                            ttlrworkperson.setREQUEIR_YWID(childNode9.getTextContent());
                        }
                        if(childNode9.getNodeName().contains("REMARK")){
                            ttlrworkperson.setREMARK(childNode9.getTextContent());
                        }
                        if(childNode9.getNodeName().contains("BIRTHDAY")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            ttlrworkperson.setBIRTHDAY(sdf.parse(childNode9.getTextContent()));
                        }
                        if(childNode9.getNodeName().contains("WORK_PERSON_ID")){
                            ttlrworkperson.setWORK_PERSON_ID(childNode9.getTextContent());
                        }
                        if(childNode9.getNodeName().contains("WORK_STATION")){
                            ttlrworkperson.setWORK_STATION(childNode9.getTextContent());
                        }
                        if(childNode9.getNodeName().contains("NAME")){
                            ttlrworkperson.setNAME(childNode9.getTextContent());
                        }
                        if(childNode9.getNodeName().contains("COMPANY_ID")){
                            ttlrworkperson.setCOMPANY_ID(childNode9.getTextContent());
                        }
                        if(childNode9.getNodeName().contains("PERSON_ID")){
                            ttlrworkperson.setPERSON_ID(childNode9.getTextContent());
                        }
                        if(childNode9.getNodeName().contains("SEX")){
                            ttlrworkperson.setSEX(childNode9.getTextContent());
                        }
                        if(childNode9.getNodeName().contains("GRADE_SCHOOL")){
                            ttlrworkperson.setGRADE_SCHOOL(childNode9.getTextContent());
                        }
                        if(childNode9.getNodeName().contains("DEGREE")){
                            ttlrworkperson.setDEGREE(childNode9.getTextContent());
                        }
                        if(childNode9.getNodeName().contains("DEPARTMENT")){
                            ttlrworkperson.setDEPARTMENT(childNode9.getTextContent());
                        }
                        if(childNode9.getNodeName().contains("PERSON_ID_TYPE")){
                            ttlrworkperson.setPERSON_ID_TYPE(childNode9.getTextContent());
                        }

                    }

                }
                ttlrworkpersonlist.add(ttlrworkperson);
            }

            List<TTLRTRAINRECORD> ttlrtrainrecordlist = new ArrayList<TTLRTRAINRECORD>();
            NodeList nl10 = doc.getElementsByTagName("TT_LR_TRAIN_RECORD");
            for (int i = 0; i < nl10.getLength(); i++) {
                // 获取一个节点
                Node node10 = nl10.item(i);
                //获取所有子节点数据
                NodeList childNodes10=node10.getChildNodes();
                TTLRTRAINRECORD ttlrtrainrecord = new TTLRTRAINRECORD();
                for (int j = 0; j < childNodes10.getLength(); j++) {
                    Node childNode10=childNodes10.item(j);
                    if(childNode10.getNodeName().contains("#text")){
                        continue;
                    }else{
                        if(childNode10.getNodeName().contains("YWID")){
                            ttlrtrainrecord.setYWID(childNode10.getTextContent());
                        }
                        if(childNode10.getNodeName().contains("REQUEIR_YWID")){
                            ttlrtrainrecord.setREQUEIR_YWID(childNode10.getTextContent());
                        }
                        if(childNode10.getNodeName().contains("_CONTENT")){
                            ttlrtrainrecord.set_CONTENT(childNode10.getTextContent());
                        }
                        if(childNode10.getNodeName().contains("TRAINDATETIME")){
                            ttlrtrainrecord.setTRAINDATETIME(childNode10.getTextContent());
                        }
                        if(childNode10.getNodeName().contains("TRAIN_ID")){
                            ttlrtrainrecord.setTRAIN_ID(childNode10.getTextContent());
                        }
                        if(childNode10.getNodeName().contains("TRAIN_LEVEL")){
                            ttlrtrainrecord.setTRAIN_LEVEL(childNode10.getTextContent());
                        }
                        if(childNode10.getNodeName().contains("WORK_PERSON_ID")){
                            ttlrtrainrecord.setWORK_PERSON_ID(childNode10.getTextContent());
                        }
                        if(childNode10.getNodeName().contains("TRAIN_NO")){
                            ttlrtrainrecord.setTRAIN_NO(childNode10.getTextContent());
                        }
                        if(childNode10.getNodeName().contains("TRAIN_NATURE")){
                            ttlrtrainrecord.setTRAIN_NATURE(childNode10.getTextContent());
                        }
                        if(childNode10.getNodeName().contains("END_TRAIN_DATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            ttlrtrainrecord.setEND_TRAIN_DATE(sdf.parse(childNode10.getTextContent()));
                        }
                        if(childNode10.getNodeName().contains("TRAIN_ORG")){
                            ttlrtrainrecord.setTRAIN_ORG(childNode10.getTextContent());
                        }
                        if(childNode10.getNodeName().contains("PERIOD")){
                            ttlrtrainrecord.setPERIOD(childNode10.getTextContent());
                        }
                        if(childNode10.getNodeName().contains("BEGIN_TRAIN_DATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            ttlrtrainrecord.setBEGIN_TRAIN_DATE(sdf.parse(childNode10.getTextContent()));
                        }

                    }

                }
                ttlrtrainrecordlist.add(ttlrtrainrecord);
            }

            List<IMATTACHMENT> imattachmentlist = new ArrayList<IMATTACHMENT>();
            NodeList nl11 = doc.getElementsByTagName("IM_ATTACHMENT");
            for (int i = 0; i < nl11.getLength(); i++) {
                // 获取一个节点
                Node node11 = nl11.item(i);
                //获取所有子节点数据
                NodeList childNodes11=node11.getChildNodes();
                IMATTACHMENT imattachment = new IMATTACHMENT();
                for (int j = 0; j < childNodes11.getLength(); j++) {
                    Node childNode11=childNodes11.item(j);
                    if(childNode11.getNodeName().contains("#text")){
                        continue;
                    }else{
                        if(childNode11.getNodeName().contains("UPLOAD_DATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            imattachment.setUPLOAD_DATE(sdf.parse(childNode11.getTextContent()));
                        }
                        if(childNode11.getNodeName().contains("UPLOAD_PERSON")){
                            imattachment.setUPLOAD_PERSON(childNode11.getTextContent());
                        }
                        if(childNode11.getNodeName().contains("EXTENDNAME")){
                            imattachment.setEXTENDNAME(childNode11.getTextContent());
                        }
                        if(childNode11.getNodeName().contains("SAVEFILE")){
                            imattachment.setSAVEFILE(childNode11.getTextContent());
                        }
                        if(childNode11.getNodeName().contains("FILE_TYPE")){
                            imattachment.setFILE_TYPE(childNode11.getTextContent());
                        }
                        if(childNode11.getNodeName().contains("FILESIZE")){
                            imattachment.setFILESIZE(Integer.parseInt(childNode11.getTextContent()));
                        }
                        if(childNode11.getNodeName().contains("SNO")){
                            imattachment.setSNO(childNode11.getTextContent());
                        }
                        if(childNode11.getNodeName().contains("ID")){
                            imattachment.setID(childNode11.getTextContent());
                        }
                        if(childNode11.getNodeName().contains("FILENAME")){
                            imattachment.setFILENAME(childNode11.getTextContent());
                        }

                    }

                }
                imattachmentlist.add(imattachment);
            }

            List<TTLRPERSONDOSAGE> ttlrpersondosagelist = new ArrayList<TTLRPERSONDOSAGE>();
            NodeList nl12 = doc.getElementsByTagName("TT_LR_PERSON_DOSAGE");
            for (int i = 0; i < nl12.getLength(); i++) {
                // 获取一个节点
                Node node12 = nl12.item(i);
                //获取所有子节点数据
                NodeList childNodes12=node12.getChildNodes();
                TTLRPERSONDOSAGE ttlrpersondosage = new TTLRPERSONDOSAGE();
                for (int j = 0; j < childNodes12.getLength(); j++) {
                    Node childNode12=childNodes12.item(j);
                    DateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                    if(childNode12.getNodeName().contains("#text")){
                        continue;
                    }else{
                        if(childNode12.getNodeName().contains("YWID")){
                            ttlrpersondosage.setYWID(childNode12.getTextContent());
                        }
                        if(childNode12.getNodeName().contains("REQUEIR_YWID")){
                            ttlrpersondosage.setREQUEIR_YWID(childNode12.getTextContent());
                        }
                        if(childNode12.getNodeName().contains("DOSAGE_SEVEN")){
                            ttlrpersondosage.setDOSAGE_SEVEN(childNode12.getTextContent());
                        }
                        if(childNode12.getNodeName().contains("DOSAGE_THREE")){
                            ttlrpersondosage.setDOSAGE_THREE(childNode12.getTextContent());
                        }
                        if(childNode12.getNodeName().contains("IN_DOSAGE")){
                            ttlrpersondosage.setIN_DOSAGE(childNode12.getTextContent());
                        }
                        if(childNode12.getNodeName().contains("TOTAL_DOSAGE")){
                            ttlrpersondosage.setTOTAL_DOSAGE(childNode12.getTextContent());
                        }
                        if(childNode12.getNodeName().contains("DOSAGE_TEN")){
                            ttlrpersondosage.setDOSAGE_TEN(childNode12.getTextContent());
                        }
                        if(childNode12.getNodeName().contains("WORK_PERSON_ID")){
                            ttlrpersondosage.setWORK_PERSON_ID(childNode12.getTextContent());
                        }
                        if(childNode12.getNodeName().contains("PERSON_DOSAGE_ID")){
                            ttlrpersondosage.setPERSON_DOSAGE_ID(childNode12.getTextContent());
                        }
                        if(childNode12.getNodeName().contains("WORK_COMPANY")){
                            ttlrpersondosage.setWORK_COMPANY(childNode12.getTextContent());
                        }
                        if(childNode12.getNodeName().contains("MONITOR_YEAR")){
                            ttlrpersondosage.setMONITOR_YEAR(Integer.parseInt(childNode12.getTextContent()));
                        }
                        if(childNode12.getNodeName().contains("UPDATEUSER")){
                            ttlrpersondosage.setUPDATEUSER(childNode12.getTextContent());
                        }
                        if(childNode12.getNodeName().contains("UPDATETIME")){
                            Timestamp tb = new Timestamp(format.parse(childNode12.getTextContent()).getTime());
                            ttlrpersondosage.setUPDATETIME(tb);
                        }

                    }

                }
                ttlrpersondosagelist.add(ttlrpersondosage);
            }

            List<TTSYSATTACHMANAGE> ttsysattachmanagelist = new ArrayList<TTSYSATTACHMANAGE>();
            NodeList nl13 = doc.getElementsByTagName("TT_SYS_ATTACH_MANAGE");
            for (int i = 0; i < nl13.getLength(); i++) {
                // 获取一个节点
                Node node13 = nl13.item(i);
                //获取所有子节点数据
                NodeList childNodes13=node13.getChildNodes();
                TTSYSATTACHMANAGE ttsysattachmanage = new TTSYSATTACHMANAGE();
                for (int j = 0; j < childNodes13.getLength(); j++) {
                    Node childNode13=childNodes13.item(j);
                    if(childNode13.getNodeName().contains("#text")){
                        continue;
                    }else{
                        if(childNode13.getNodeName().contains("UP_DATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            ttsysattachmanage.setUP_DATE(sdf.parse(childNode13.getTextContent()));
                        }
                        if(childNode13.getNodeName().contains("FILE_NAME")){
                            ttsysattachmanage.setFILE_NAME(childNode13.getTextContent());
                        }
                        if(childNode13.getNodeName().contains("FILE_YEAR")){
                            ttsysattachmanage.setFILE_YEAR(childNode13.getTextContent());
                        }
                        if(childNode13.getNodeName().contains("FILE_URL")){
                            ttsysattachmanage.setFILE_URL(childNode13.getTextContent());
                        }
                        if(childNode13.getNodeName().contains("ATTACH_TYPE_CODE")){
                            ttsysattachmanage.setATTACH_TYPE_CODE(childNode13.getTextContent());
                        }
                        if(childNode13.getNodeName().contains("FILE_TYPE")){
                            ttsysattachmanage.setFILE_TYPE(childNode13.getTextContent());
                        }
                        if(childNode13.getNodeName().contains("UP_COMPANY_LICENSE_NO")){
                            ttsysattachmanage.setUP_COMPANY_LICENSE_NO(childNode13.getTextContent());
                        }
                        if(childNode13.getNodeName().contains("UP_COMPANY_INSTANCE_ID")){
                            ttsysattachmanage.setUP_COMPANY_INSTANCE_ID(childNode13.getTextContent());
                        }
                        if(childNode13.getNodeName().contains("ID")){
                            ttsysattachmanage.setID(childNode13.getTextContent());
                        }
                        if(childNode13.getNodeName().contains("UP_COMPANY")){
                            ttsysattachmanage.setUP_COMPANY(childNode13.getTextContent());
                        }

                    }

                }
                ttsysattachmanagelist.add(ttsysattachmanage);
            }

            List<TTREQWASTENUCLIDETAKEBACK> ttreqwastenuclidetakebacklist = new ArrayList<TTREQWASTENUCLIDETAKEBACK>();
            NodeList nl14 = doc.getElementsByTagName("TT_REQ_WASTE_NUCLIDE_TAKEBACK");
            for (int i = 0; i < nl14.getLength(); i++) {
                // 获取一个节点
                Node node14 = nl14.item(i);
                //获取所有子节点数据
                NodeList childNodes14=node14.getChildNodes();
                TTREQWASTENUCLIDETAKEBACK ttreqwastenuclidetakeback = new TTREQWASTENUCLIDETAKEBACK();
                for (int j = 0; j < childNodes14.getLength(); j++) {
                    Node childNode14=childNodes14.item(j);
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    if(childNode14.getNodeName().contains("#text")){
                        continue;
                    }else{
                        if(childNode14.getNodeName().contains("YWID")){
                            ttreqwastenuclidetakeback.setYWID(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("DELIVERY_COMPANY_POST")){
                            ttreqwastenuclidetakeback.setDELIVERY_COMPANY_POST(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("ATTACHMENT")){
                            ttreqwastenuclidetakeback.setATTACHMENT(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("ATTACHMENT_OTHERS")){
                            ttreqwastenuclidetakeback.setATTACHMENT_OTHERS(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("STORE_DATE")){
                            Timestamp tb = new Timestamp(format.parse(childNode14.getTextContent()).getTime());
                            ttreqwastenuclidetakeback.setSTORE_DATE(tb);
                        }
                        if(childNode14.getNodeName().contains("STORE_FACILITY")){
                            ttreqwastenuclidetakeback.setSTORE_FACILITY(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("ZONE_NO")){
                            ttreqwastenuclidetakeback.setZONE_NO(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("PIT_NO")){
                            ttreqwastenuclidetakeback.setPIT_NO(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("COVER_NO")){
                            ttreqwastenuclidetakeback.setCOVER_NO(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("SHELF_NO")){
                            ttreqwastenuclidetakeback.setSHELF_NO(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("BARREL_NO")){
                            ttreqwastenuclidetakeback.setBARREL_NO(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("CONTAINTER_NO")){
                            ttreqwastenuclidetakeback.setCONTAINTER_NO(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("METER_DOSE_RATE")){
                            ttreqwastenuclidetakeback.setMETER_DOSE_RATE(Integer.parseInt(childNode14.getTextContent()));
                        }
                        if(childNode14.getNodeName().contains("A_POLLUTE_RATE")){
                            ttreqwastenuclidetakeback.setA_POLLUTE_RATE(Integer.parseInt(childNode14.getTextContent()));
                        }
                        if(childNode14.getNodeName().contains("B_POLLUTE_RATE")){
                            ttreqwastenuclidetakeback.setB_POLLUTE_RATE(Integer.parseInt(childNode14.getTextContent()));
                        }
                        if(childNode14.getNodeName().contains("PACK_VOLUME")){
                            ttreqwastenuclidetakeback.setPACK_VOLUME(Integer.parseInt(childNode14.getTextContent()));
                        }
                        if(childNode14.getNodeName().contains("WEIGHT")){
                            ttreqwastenuclidetakeback.setWEIGHT(Integer.parseInt(childNode14.getTextContent()));
                        }
                        if(childNode14.getNodeName().contains("MODIFY_USER")){
                            ttreqwastenuclidetakeback.setMODIFY_USER(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("MODIFY_DATE")){
                            Timestamp tb = new Timestamp(format.parse(childNode14.getTextContent()).getTime());
                            ttreqwastenuclidetakeback.setMODIFY_DATE(tb);
                        }
                        if(childNode14.getNodeName().contains("SURFACE_POWER")){
                            ttreqwastenuclidetakeback.setSURFACE_POWER(Integer.parseInt(childNode14.getTextContent()));
                        }
                        if(childNode14.getNodeName().contains("METER_POWER")){
                            ttreqwastenuclidetakeback.setMETER_POWER(Integer.parseInt(childNode14.getTextContent()));
                        }
                        if(childNode14.getNodeName().contains("A_POWER")){
                            ttreqwastenuclidetakeback.setA_POWER(Integer.parseInt(childNode14.getTextContent()));
                        }
                        if(childNode14.getNodeName().contains("B_POWER")){
                            ttreqwastenuclidetakeback.setB_POWER(Integer.parseInt(childNode14.getTextContent()));
                        }
                        if(childNode14.getNodeName().contains("RETREAT_REASON")){
                            ttreqwastenuclidetakeback.setRETREAT_REASON(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("RECEIVECOMPANY_BE_RECORD")){
                            ttreqwastenuclidetakeback.setRECEIVECOMPANY_BE_RECORD(Integer.parseInt(childNode14.getTextContent()));
                        }
                        if(childNode14.getNodeName().contains("REVOKE_DECLARE")){
                            ttreqwastenuclidetakeback.setREVOKE_DECLARE(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("REVOKE_REASON")){
                            ttreqwastenuclidetakeback.setREVOKE_REASON(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("OUT_DATE")){
                            ttreqwastenuclidetakeback.setOUT_DATE(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("IN_DATE")){
                            ttreqwastenuclidetakeback.setIN_DATE(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("OUT_PERSON")){
                            ttreqwastenuclidetakeback.setOUT_PERSON(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("IN_PERSON")){
                            ttreqwastenuclidetakeback.setIN_PERSON(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("OLD_MARK")){
                            ttreqwastenuclidetakeback.setOLD_MARK(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("RECEIVECOMPANY_CONTACTADDR")){
                            ttreqwastenuclidetakeback.setRECEIVECOMPANY_CONTACTADDR(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("INSERT_DATE")){
                            Timestamp te = new Timestamp(format.parse(childNode14.getTextContent()).getTime());
                            ttreqwastenuclidetakeback.setINSERT_DATE(te);
                        }
                        if(childNode14.getNodeName().contains("RECEIVECOMPANY_POST")){
                            ttreqwastenuclidetakeback.setRECEIVECOMPANY_POST(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("DELIVERY_COMPANY_NAME")){
                            ttreqwastenuclidetakeback.setDELIVERY_COMPANY_NAME(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("DELIVERY_PERSON")){
                            ttreqwastenuclidetakeback.setDELIVERY_PERSON(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("DELIVERY_COMPANY_DOPERSON")){
                            ttreqwastenuclidetakeback.setDELIVERY_COMPANY_DOPERSON(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("RECEIVECOMPANY_PHONEORFAX")){
                            ttreqwastenuclidetakeback.setRECEIVECOMPANY_PHONEORFAX(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("REQUEST_NO")){
                            ttreqwastenuclidetakeback.setREQUEST_NO(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("RESPONSE_STATUS")){
                            ttreqwastenuclidetakeback.setRESPONSE_STATUS(Integer.parseInt(childNode14.getTextContent()));
                        }
                        if(childNode14.getNodeName().contains("SURFACE_DOSE_RATE")){
                            ttreqwastenuclidetakeback.setSURFACE_DOSE_RATE(Integer.parseInt(childNode14.getTextContent()));
                        }
                        if(childNode14.getNodeName().contains("RECEIVE_COMPANY_INSTANCEID")){
                            ttreqwastenuclidetakeback.setRECEIVE_COMPANY_INSTANCEID(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("INSERT_USER")){
                            ttreqwastenuclidetakeback.setINSERT_USER(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("REGION_CODE_1")){
                            ttreqwastenuclidetakeback.setREGION_CODE_1(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("RECEIVECOMPANY_DOPERSON")){
                            ttreqwastenuclidetakeback.setRECEIVECOMPANY_DOPERSON(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("REGION_CODE_2")){
                            ttreqwastenuclidetakeback.setREGION_CODE_2(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("MEASURE_PERSON")){
                            ttreqwastenuclidetakeback.setMEASURE_PERSON(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("DELIVERY_COMPANY_PHONEORFAX")){
                            ttreqwastenuclidetakeback.setDELIVERY_COMPANY_PHONEORFAX(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("DELIVERY_COMPANY_INSTANCEID")){
                            ttreqwastenuclidetakeback.setDELIVERY_COMPANY_INSTANCEID(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("TAKEBACK_DATE")){
                            Timestamp ta = new Timestamp(format.parse(childNode14.getTextContent()).getTime());
                            ttreqwastenuclidetakeback.setTAKEBACK_DATE(ta);
                        }
                        if(childNode14.getNodeName().contains("SURFACE_POWER")){
                            ttreqwastenuclidetakeback.setSURFACE_POWER(Integer.parseInt(childNode14.getTextContent()));
                        }
                        if(childNode14.getNodeName().contains("DELIVERY_COMPANY_LICENSENO")){
                            ttreqwastenuclidetakeback.setDELIVERY_COMPANY_LICENSENO(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("STORE_PERSON")){
                            ttreqwastenuclidetakeback.setSTORE_PERSON(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("TAKEBACK_ID")){
                            ttreqwastenuclidetakeback.setTAKEBACK_ID(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("DELIVERY_COMPANY_CONTACTADDR")){
                            ttreqwastenuclidetakeback.setDELIVERY_COMPANY_CONTACTADDR(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("RESPONSE_NO")){
                            ttreqwastenuclidetakeback.setRESPONSE_NO(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("RECEIVECOMPANY_LICENSENO")){
                            ttreqwastenuclidetakeback.setRECEIVECOMPANY_LICENSENO(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("RECEIVE_COMPANY_NAME")){
                            ttreqwastenuclidetakeback.setRECEIVE_COMPANY_NAME(childNode14.getTextContent());
                        }
                        if(childNode14.getNodeName().contains("PASS_NO")){
                            ttreqwastenuclidetakeback.setPASS_NO(childNode14.getTextContent());
                        }

                    }

                }
                ttreqwastenuclidetakebacklist.add(ttreqwastenuclidetakeback);
            }


            List<TTREQWASTETAKEBACKDETAIL> ttreqwastetakebackdetaillist = new ArrayList<TTREQWASTETAKEBACKDETAIL>();
            NodeList nl15 = doc.getElementsByTagName("TT_REQ_WASTE_TAKEBACK_DETAIL");
            for (int i = 0; i < nl15.getLength(); i++) {
                // 获取一个节点
                Node node15 = nl15.item(i);
                //获取所有子节点数据
                NodeList childNodes15=node15.getChildNodes();
                TTREQWASTETAKEBACKDETAIL ttreqwastetakebackdetail = new TTREQWASTETAKEBACKDETAIL();
                for (int j = 0; j < childNodes15.getLength(); j++) {
                    Node childNode15=childNodes15.item(j);
                    if(childNode15.getNodeName().contains("#text")){
                        continue;
                    }else{
                        if(childNode15.getNodeName().contains("YWID")){
                            ttreqwastetakebackdetail.setYWID(childNode15.getTextContent());
                        }
                        if(childNode15.getNodeName().contains("REQUEIR_YWID")){
                            ttreqwastetakebackdetail.setREQUEIR_YWID(childNode15.getTextContent());
                        }
                        if(childNode15.getNodeName().contains("TAB")){
                            ttreqwastetakebackdetail.setTAB(childNode15.getTextContent());
                        }
                        if(childNode15.getNodeName().contains("REMARK")){
                            ttreqwastetakebackdetail.setREMARK(childNode15.getTextContent());
                        }
                        if(childNode15.getNodeName().contains("STATUS")){
                            ttreqwastetakebackdetail.setSTATUS(childNode15.getTextContent());
                        }
                        if(childNode15.getNodeName().contains("INPERSON")){
                            ttreqwastetakebackdetail.setINPERSON(childNode15.getTextContent());
                        }
                        if(childNode15.getNodeName().contains("OUTPERSON")){
                            ttreqwastetakebackdetail.setOUTPERSON(childNode15.getTextContent());
                        }
                        if(childNode15.getNodeName().contains("INDATE")){
                            ttreqwastetakebackdetail.setINDATE(childNode15.getTextContent());
                        }
                        if(childNode15.getNodeName().contains("OUTDATE")){
                            ttreqwastetakebackdetail.setOUTDATE(childNode15.getTextContent());
                        }
                        if(childNode15.getNodeName().contains("OTHER_USE_TYPE")){
                            ttreqwastetakebackdetail.setOTHER_USE_TYPE(childNode15.getTextContent());
                        }
                        if(childNode15.getNodeName().contains("RECORD_TYPE")){
                            ttreqwastetakebackdetail.setRECORD_TYPE(Integer.parseInt(childNode15.getTextContent()));
                        }
                        if(childNode15.getNodeName().contains("TAKEBACK_ID")){
                            ttreqwastetakebackdetail.setTAKEBACK_ID(childNode15.getTextContent());
                        }
                        if(childNode15.getNodeName().contains("NUCLIDE")){
                            ttreqwastetakebackdetail.setNUCLIDE(childNode15.getTextContent());
                        }
                        if(childNode15.getNodeName().contains("DETAIL_ID")){
                            ttreqwastetakebackdetail.setDETAIL_ID(childNode15.getTextContent());
                        }
                        if(childNode15.getNodeName().contains("LEAVEFACTORY_ACTIVITY")){
                            ttreqwastetakebackdetail.setLEAVEFACTORY_ACTIVITY(Integer.parseInt(childNode15.getTextContent()));
                        }
                        if(childNode15.getNodeName().contains("CATEGORY")){
                            ttreqwastetakebackdetail.setCATEGORY(childNode15.getTextContent());
                        }
                        if(childNode15.getNodeName().contains("POWER")){
                            ttreqwastetakebackdetail.setPOWER(Integer.parseInt(childNode15.getTextContent()));
                        }
                        if(childNode15.getNodeName().contains("RECORD_ID")){
                            ttreqwastetakebackdetail.setRECORD_ID(childNode15.getTextContent());
                        }
                        if(childNode15.getNodeName().contains("USE_TYPE")){
                            ttreqwastetakebackdetail.setUSE_TYPE(childNode15.getTextContent());
                        }
                        if(childNode15.getNodeName().contains("LEAVEFACTORY_DATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            ttreqwastetakebackdetail.setLEAVEFACTORY_DATE(sdf.parse(childNode15.getTextContent()));
                        }
                        if(childNode15.getNodeName().contains("CODE")){
                            ttreqwastetakebackdetail.setCODE(childNode15.getTextContent());
                        }
                        if(childNode15.getNodeName().contains("RECORD_ID")){
                            ttreqwastetakebackdetail.setRECORD_ID(childNode15.getTextContent());
                        }

                    }

                }
                ttreqwastetakebackdetaillist.add(ttreqwastetakebackdetail);
            }

            List<TTREQNUCLIDETRANSFER> ttreqnuclidetransferllist = new ArrayList<TTREQNUCLIDETRANSFER>();
            NodeList nl16 = doc.getElementsByTagName("TT_REQ_NUCLIDE_TRANSFER");
            for (int i = 0; i < nl16.getLength(); i++) {
                // 获取一个节点
                Node node16 = nl16.item(i);
                //获取所有子节点数据
                NodeList childNodes16=node16.getChildNodes();
                TTREQNUCLIDETRANSFER ttreqnuclidetransfer = new TTREQNUCLIDETRANSFER();
                for (int j = 0; j < childNodes16.getLength(); j++) {
                    Node childNode16=childNodes16.item(j);
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    if(childNode16.getNodeName().contains("#text")){
                        continue;
                    }else{
                        if(childNode16.getNodeName().contains("YWID")){
                            ttreqnuclidetransfer.setYWID(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("RESPONSE_NO")){
                            ttreqnuclidetransfer.setRESPONSE_NO(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("TRANSFER_REASON_OTHERS")){
                            ttreqnuclidetransfer.setTRANSFER_REASON_OTHERS(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("ATTACHMENT")){
                            ttreqnuclidetransfer.setATTACHMENT(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("ATTACHMENT_OTHERS")){
                            ttreqnuclidetransfer.setATTACHMENT_OTHERS(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("REQUEST_SOURCE")){
                            ttreqnuclidetransfer.setREQUEST_SOURCE(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("REQUEST_SOURCE_ID")){
                            ttreqnuclidetransfer.setREQUEST_SOURCE_ID(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("BACKUP_STATUS")){
                            ttreqnuclidetransfer.setBACKUP_STATUS(Integer.parseInt(childNode16.getTextContent()));
                        }
                        if(childNode16.getNodeName().contains("NOT_PASS_REASON")){
                            ttreqnuclidetransfer.setNOT_PASS_REASON(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("RETREAT_REASON")){
                            ttreqnuclidetransfer.setRETREAT_REASON(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("USER_ID")){
                            ttreqnuclidetransfer.setUSER_ID(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("REVOKE_DECLARE")){
                            ttreqnuclidetransfer.setREVOKE_DECLARE(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("REVOKE_REASON")){
                            ttreqnuclidetransfer.setREVOKE_REASON(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("OLD_MARK")){
                            ttreqnuclidetransfer.setOLD_MARK(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("INSERT_DATE")){
                            Timestamp ta = new Timestamp(format.parse(childNode16.getTextContent()).getTime());
                            ttreqnuclidetransfer.setINSERT_DATE(ta);
                        }
                        if(childNode16.getNodeName().contains("INCOMPANY_CONTACTADDR")){
                            ttreqnuclidetransfer.setINCOMPANY_CONTACTADDR(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("OUTCOMPANY_LICENSENO")){
                            ttreqnuclidetransfer.setOUTCOMPANY_LICENSENO(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("TRANSFER_ID")){
                            ttreqnuclidetransfer.setTRANSFER_ID(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("MODIFY_DATE")){
                            Timestamp tb = new Timestamp(format.parse(childNode16.getTextContent()).getTime());
                            ttreqnuclidetransfer.setMODIFY_DATE(tb);
                        }
                        if(childNode16.getNodeName().contains("OUT_COMPANY")){
                            ttreqnuclidetransfer.setOUT_COMPANY(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("BE_PASS")){
                            ttreqnuclidetransfer.setBE_PASS(Integer.parseInt(childNode16.getTextContent()));
                        }
                        if(childNode16.getNodeName().contains("INCONPANY_PHONEORFAX")){
                            ttreqnuclidetransfer.setINCONPANY_PHONEORFAX(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("DO_DATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            ttreqnuclidetransfer.setDO_DATE(sdf.parse(childNode16.getTextContent()));
                        }
                        if(childNode16.getNodeName().contains("INCOMPANY_LICENSENO")){
                            ttreqnuclidetransfer.setINCOMPANY_LICENSENO(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("INCOMPANY_DOPERSON")){
                            ttreqnuclidetransfer.setINCOMPANY_DOPERSON(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("INCOMPANY_POST")){
                            ttreqnuclidetransfer.setINCOMPANY_POST(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("INCOMPANY_NAME")){
                            ttreqnuclidetransfer.setINCOMPANY_NAME(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("OUTCOMPANY_CONTACTADDR")){
                            ttreqnuclidetransfer.setOUTCOMPANY_CONTACTADDR(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("OUT_COMPANY_INSTANCEID")){
                            ttreqnuclidetransfer.setOUT_COMPANY_INSTANCEID(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("OUTCOMPANY_POST")){
                            ttreqnuclidetransfer.setOUTCOMPANY_POST(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("PASS_NO")){
                            ttreqnuclidetransfer.setPASS_NO(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("REQUEST_NO")){
                            ttreqnuclidetransfer.setREQUEST_NO(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("IN_COMPANY_INSTANCEID")){
                            ttreqnuclidetransfer.setIN_COMPANY_INSTANCEID(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("RESPONSE_STATUS")){
                            ttreqnuclidetransfer.setRESPONSE_STATUS(Integer.parseInt(childNode16.getTextContent()));
                        }
                        if(childNode16.getNodeName().contains("OUTCOMPANY_DOPERSON")){
                            ttreqnuclidetransfer.setOUTCOMPANY_DOPERSON(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("TRANSFER_REASON")){
                            ttreqnuclidetransfer.setTRANSFER_REASON(Integer.parseInt(childNode16.getTextContent()));
                        }
                        if(childNode16.getNodeName().contains("OUTCOMPANY_PHONEORFAX")){
                            ttreqnuclidetransfer.setOUTCOMPANY_PHONEORFAX(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("DO_PERSON")){
                            ttreqnuclidetransfer.setDO_PERSON(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("INSERT_USER")){
                            ttreqnuclidetransfer.setINSERT_USER(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("REGION_CODE_1")){
                            ttreqnuclidetransfer.setREGION_CODE_1(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("REGION_CODE_2")){
                            ttreqnuclidetransfer.setREGION_CODE_2(childNode16.getTextContent());
                        }
                        if(childNode16.getNodeName().contains("MODIFY_USER")){
                            ttreqnuclidetransfer.setMODIFY_USER(childNode16.getTextContent());
                        }

                    }

                }
                ttreqnuclidetransferllist.add(ttreqnuclidetransfer);
            }

            List<TTREQNUCLIDETRANSFERDETAIL> ttreqnuclidetransferdetaillist = new ArrayList<TTREQNUCLIDETRANSFERDETAIL>();
            NodeList nl17 = doc.getElementsByTagName("TT_REQ_NUCLIDE_TRANSFER_DETAIL");
            for (int i = 0; i < nl17.getLength(); i++) {
                // 获取一个节点
                Node node17 = nl17.item(i);
                //获取所有子节点数据
                NodeList childNodes17=node17.getChildNodes();
                TTREQNUCLIDETRANSFERDETAIL ttreqnuclidetransferdetail = new TTREQNUCLIDETRANSFERDETAIL();
                for (int j = 0; j < childNodes17.getLength(); j++) {
                    Node childNode17=childNodes17.item(j);
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    if(childNode17.getNodeName().contains("#text")){
                        continue;
                    }else{
                        if(childNode17.getNodeName().contains("YWID")){
                            ttreqnuclidetransferdetail.setYWID(childNode17.getTextContent());
                        }
                        if(childNode17.getNodeName().contains("REQUEIR_YWID")){
                            ttreqnuclidetransferdetail.setREQUEIR_YWID(childNode17.getTextContent());
                        }
                        if(childNode17.getNodeName().contains("BE_BACKUP")){
                            ttreqnuclidetransferdetail.setBE_BACKUP(Integer.parseInt(childNode17.getTextContent()));
                        }
                        if(childNode17.getNodeName().contains("BACK_UPDATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                            ttreqnuclidetransferdetail.setBACK_UPDATE(sdf.parse(childNode17.getTextContent()));
                        }
                        if(childNode17.getNodeName().contains("ADDRESS")){
                            ttreqnuclidetransferdetail.setADDRESS(childNode17.getTextContent());
                        }
                        if(childNode17.getNodeName().contains("USE_TYPE_OTHER")){
                            ttreqnuclidetransferdetail.setUSE_TYPE_OTHER(childNode17.getTextContent());
                        }
                        if(childNode17.getNodeName().contains("OUTPERSON")){
                            ttreqnuclidetransferdetail.setOUTPERSON(childNode17.getTextContent());
                        }
                        if(childNode17.getNodeName().contains("OUTDATE")){
                            ttreqnuclidetransferdetail.setOUTDATE(childNode17.getTextContent());
                        }
                        if(childNode17.getNodeName().contains("SITE_CODE")){
                            ttreqnuclidetransferdetail.setSITE_CODE(childNode17.getTextContent());
                        }
                        if(childNode17.getNodeName().contains("RECORD_TYPE")){
                            ttreqnuclidetransferdetail.setRECORD_TYPE(Integer.parseInt(childNode17.getTextContent()));
                        }
                        if(childNode17.getNodeName().contains("TRANSFER_ID")){
                            ttreqnuclidetransferdetail.setTRANSFER_ID(childNode17.getTextContent());
                        }
                        if(childNode17.getNodeName().contains("DETAIL_ID")){
                            ttreqnuclidetransferdetail.setDETAIL_ID(childNode17.getTextContent());
                        }
                        if(childNode17.getNodeName().contains("STATUS")){
                            ttreqnuclidetransferdetail.setSTATUS(Integer.parseInt(childNode17.getTextContent()));
                        }
                        if(childNode17.getNodeName().contains("SITE")){
                            ttreqnuclidetransferdetail.setSITE(childNode17.getTextContent());
                        }
                        if(childNode17.getNodeName().contains("TAB")){
                            ttreqnuclidetransferdetail.setTAB(childNode17.getTextContent());
                        }
                        if(childNode17.getNodeName().contains("POWER")){
                            ttreqnuclidetransferdetail.setPOWER(Integer.parseInt(childNode17.getTextContent()));
                        }
                        if(childNode17.getNodeName().contains("CATEGORY")){
                            ttreqnuclidetransferdetail.setCATEGORY(childNode17.getTextContent());
                        }
                        if(childNode17.getNodeName().contains("RECORD_ID")){
                            ttreqnuclidetransferdetail.setRECORD_ID(childNode17.getTextContent());
                        }
                        if(childNode17.getNodeName().contains("LEAVE_FACTORY_DATE")){
                            Timestamp tb = new Timestamp(format.parse(childNode17.getTextContent()).getTime());
                            ttreqnuclidetransferdetail.setLEAVE_FACTORY_DATE(tb);
                        }
                        if(childNode17.getNodeName().contains("USE_TYPE")){
                            ttreqnuclidetransferdetail.setUSE_TYPE(childNode17.getTextContent());
                        }
                        if(childNode17.getNodeName().contains("BATCH")){
                            ttreqnuclidetransferdetail.setBATCH(Integer.parseInt(childNode17.getTextContent()));
                        }
                        if(childNode17.getNodeName().contains("CODE")){
                            ttreqnuclidetransferdetail.setCODE(childNode17.getTextContent());
                        }
                        if(childNode17.getNodeName().contains("LEAVE_FACTORY_ACTIVITY")){
                            ttreqnuclidetransferdetail.setLEAVE_FACTORY_ACTIVITY(Integer.parseInt(childNode17.getTextContent()));
                        }
                        if(childNode17.getNodeName().contains("NUCLIDE")){
                            ttreqnuclidetransferdetail.setNUCLIDE(childNode17.getTextContent());
                        }
                        if(childNode17.getNodeName().contains("INDATE")){
                            ttreqnuclidetransferdetail.setINDATE(childNode17.getTextContent());
                        }
                        if(childNode17.getNodeName().contains("BE_REPLENISH_INFO")){
                            ttreqnuclidetransferdetail.setBE_REPLENISH_INFO(Integer.parseInt(childNode17.getTextContent()));
                        }
                        if(childNode17.getNodeName().contains("INPERSON")){
                            ttreqnuclidetransferdetail.setINPERSON(childNode17.getTextContent());
                        }

                    }

                }
                ttreqnuclidetransferdetaillist.add(ttreqnuclidetransferdetail);
            }

            List<TTLRNUCLIDEACTIVERANGE> ttlrnuclideactiverangelist = new ArrayList<TTLRNUCLIDEACTIVERANGE>();
            NodeList nl18 = doc.getElementsByTagName("TT_LR_NUCLIDE_ACTIVE_RANGE");
            for (int i = 0; i < nl18.getLength(); i++) {
                // 获取一个节点
                Node node18 = nl18.item(i);
                //获取所有子节点数据
                NodeList childNodes18=node18.getChildNodes();
                TTLRNUCLIDEACTIVERANGE ttlrnuclideactiverange = new TTLRNUCLIDEACTIVERANGE();
                for (int j = 0; j < childNodes18.getLength(); j++) {
                    Node childNode18=childNodes18.item(j);
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if(childNode18.getNodeName().contains("#text")){
                        continue;
                    }else{
                        if(childNode18.getNodeName().contains("YWID")){
                            ttlrnuclideactiverange.setYWID(childNode18.getTextContent());
                        }
                        if(childNode18.getNodeName().contains("REQUEIR_YWID")){
                            ttlrnuclideactiverange.setREQUEIR_YWID(childNode18.getTextContent());
                        }
                        if(childNode18.getNodeName().contains("NUCLIDE_NAME")){
                            ttlrnuclideactiverange.setNUCLIDE_NAME(childNode18.getTextContent());
                        }
                        if(childNode18.getNodeName().contains("NAME")){
                            ttlrnuclideactiverange.setNAME(childNode18.getTextContent());
                        }
                        if(childNode18.getNodeName().contains("NUCLIDE_CATEGORY")){
                            ttlrnuclideactiverange.setNUCLIDE_CATEGORY(childNode18.getTextContent());
                        }
                        if(childNode18.getNodeName().contains("TOTAL_ACTIVITY")){
                            ttlrnuclideactiverange.setTOTAL_ACTIVITY(childNode18.getTextContent());
                        }
                        if(childNode18.getNodeName().contains("COMPANY_ID")){
                            ttlrnuclideactiverange.setCOMPANY_ID(childNode18.getTextContent());
                        }
                        if(childNode18.getNodeName().contains("ACTIVE_RANGE_ID")){
                            ttlrnuclideactiverange.setACTIVE_RANGE_ID(childNode18.getTextContent());
                        }
                        if(childNode18.getNodeName().contains("ACTIVE_TYPE")){
                            ttlrnuclideactiverange.setACTIVE_TYPE(childNode18.getTextContent());
                        }
                        if(childNode18.getNodeName().contains("UPDATEUSER")){
                            ttlrnuclideactiverange.setUPDATEUSER(childNode18.getTextContent());
                        }
                        if(childNode18.getNodeName().contains("UPDATETIME")){
                            Timestamp tb = new Timestamp(format.parse(childNode18.getTextContent()).getTime());
                            ttlrnuclideactiverange.setUPDATETIME(tb);
                        }

                    }

                }
                ttlrnuclideactiverangelist.add(ttlrnuclideactiverange);
            }

            List<TTLCRCOMPANY> ttlcrcompanylist = new ArrayList<TTLCRCOMPANY>();
            NodeList nl19 = doc.getElementsByTagName("TT_LCR_COMPANY");
            for (int i = 0; i < nl19.getLength(); i++) {
                // 获取一个节点
                Node node19 = nl19.item(i);
                //获取所有子节点数据
                NodeList childNodes19=node19.getChildNodes();
                TTLCRCOMPANY ttlcrcompany = new TTLCRCOMPANY();
                for (int j = 0; j < childNodes19.getLength(); j++) {
                    Node childNode19=childNodes19.item(j);
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    if(childNode19.getNodeName().contains("#text")){
                        continue;
                    }else{
                        if(childNode19.getNodeName().contains("YWID")){
                            ttlcrcompany.setYWID(childNode19.getTextContent());
                        }
                        if(childNode19.getNodeName().contains("REQUEST_NO")){
                            ttlcrcompany.setREQUEST_NO(childNode19.getTextContent());
                        }
                        if(childNode19.getNodeName().contains("NOT_PASS_REASON")){
                            ttlcrcompany.setNOT_PASS_REASON(childNode19.getTextContent());
                        }
                        if(childNode19.getNodeName().contains("DO_PERSON")){
                            ttlcrcompany.setDO_PERSON(childNode19.getTextContent());
                        }
                        if(childNode19.getNodeName().contains("DO_DATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
                            ttlcrcompany.setDO_DATE(sdf.parse(childNode19.getTextContent()));
                        }
                        if(childNode19.getNodeName().contains("LCR_DATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                            ttlcrcompany.setLCR_DATE(sdf.parse(childNode19.getTextContent()));
                        }
                        if(childNode19.getNodeName().contains("CERTIFICATE_DATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                            ttlcrcompany.setCERTIFICATE_DATE(sdf.parse(childNode19.getTextContent()));
                        }
                        if(childNode19.getNodeName().contains("INSERT_DATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                            ttlcrcompany.setINSERT_DATE(sdf.parse(childNode19.getTextContent()));
                        }
                        if(childNode19.getNodeName().contains("COMPANY_DOPERSON_PHONE")){
                            ttlcrcompany.setCOMPANY_DOPERSON_PHONE(childNode19.getTextContent());
                        }
                        if(childNode19.getNodeName().contains("MODIFY_DATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                            ttlcrcompany.setMODIFY_DATE(sdf.parse(childNode19.getTextContent()));
                        }
                        if(childNode19.getNodeName().contains("LEGAL_PERSON_ID")){
                            ttlcrcompany.setLEGAL_PERSON_ID(childNode19.getTextContent());
                        }
                        if(childNode19.getNodeName().contains("LICENSE_NO")){
                            ttlcrcompany.setLICENSE_NO(childNode19.getTextContent());
                        }
                        if(childNode19.getNodeName().contains("LEGAL_PERSON")){
                            ttlcrcompany.setLEGAL_PERSON(childNode19.getTextContent());
                        }
                        if(childNode19.getNodeName().contains("SUBMIT_DATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                            ttlcrcompany.setSUBMIT_DATE(sdf.parse(childNode19.getTextContent()));
                        }
                        if(childNode19.getNodeName().contains("INSTANCEID")){
                            ttlcrcompany.setINSTANCEID(childNode19.getTextContent());
                        }
                        if(childNode19.getNodeName().contains("LICENSE_ENDDATE")){
                            Timestamp tb = new Timestamp(format.parse(childNode19.getTextContent()).getTime());
                            ttlcrcompany.setLICENSE_ENDDATE(tb);
                        }
                        if(childNode19.getNodeName().contains("PASS_NO")){
                            ttlcrcompany.setPASS_NO(childNode19.getTextContent());
                        }
                        if(childNode19.getNodeName().contains("LICENSE_ISSUE_ORGAN")){
                            ttlcrcompany.setLICENSE_ISSUE_ORGAN(childNode19.getTextContent());
                        }
                        if(childNode19.getNodeName().contains("LCR_COMPANY_ID")){
                            ttlcrcompany.setLCR_COMPANY_ID(childNode19.getTextContent());
                        }
                        if(childNode19.getNodeName().contains("NAME")){
                            ttlcrcompany.setNAME(childNode19.getTextContent());
                        }
                        if(childNode19.getNodeName().contains("COMPANY_ID")){
                            ttlcrcompany.setCOMPANY_ID(childNode19.getTextContent());
                        }
                        if(childNode19.getNodeName().contains("RESPONSE_STATUS")){
                            ttlcrcompany.setRESPONSE_STATUS(Integer.parseInt(childNode19.getTextContent()));
                        }
                        if(childNode19.getNodeName().contains("SUBMIT_USER")){
                            ttlcrcompany.setSUBMIT_USER(childNode19.getTextContent());
                        }
                        if(childNode19.getNodeName().contains("ADDRESS")){
                            ttlcrcompany.setADDRESS(childNode19.getTextContent());
                        }
                        if(childNode19.getNodeName().contains("TYPE_AND_RANGE")){
                            ttlcrcompany.setTYPE_AND_RANGE(childNode19.getTextContent());
                        }
                        if(childNode19.getNodeName().contains("RESPONSE_NO")){
                            ttlcrcompany.setRESPONSE_NO(childNode19.getTextContent());
                        }
                        if(childNode19.getNodeName().contains("COMPANY_DOPERSON")){
                            ttlcrcompany.setCOMPANY_DOPERSON(childNode19.getTextContent());
                        }
                        if(childNode19.getNodeName().contains("LEGAL_PERSON_PHONE")){
                            ttlcrcompany.setLEGAL_PERSON_PHONE(childNode19.getTextContent());
                        }
                        if(childNode19.getNodeName().contains("LICENSE_STARTDATE")){
                            Timestamp tc = new Timestamp(format.parse(childNode19.getTextContent()).getTime());
                            ttlcrcompany.setLICENSE_STARTDATE(tc);
                        }
                        if(childNode19.getNodeName().contains("INSERT_USER")){
                            ttlcrcompany.setINSERT_USER(childNode19.getTextContent());
                        }
                        if(childNode19.getNodeName().contains("LEGAL_PERSON_TYPE")){
                            ttlcrcompany.setLEGAL_PERSON_TYPE(Integer.parseInt(childNode19.getTextContent()));
                        }
                        if(childNode19.getNodeName().contains("MODIFY_USER")){
                            ttlcrcompany.setMODIFY_USER(childNode19.getTextContent());
                        }

                    }

                }
                ttlcrcompanylist.add(ttlcrcompany);
            }

            List<TTREQNUCLIDEIMPORT> ttreqnuclideimportlist = new ArrayList<TTREQNUCLIDEIMPORT>();
            NodeList nl20 = doc.getElementsByTagName("TT_REQ_NUCLIDE_IMPORT");
            for (int i = 0; i < nl20.getLength(); i++) {
                // 获取一个节点
                Node node20 = nl20.item(i);
                //获取所有子节点数据
                NodeList childNodes20=node20.getChildNodes();
                TTREQNUCLIDEIMPORT ttreqnuclideimport = new TTREQNUCLIDEIMPORT();
                for (int j = 0; j < childNodes20.getLength(); j++) {
                    Node childNode20=childNodes20.item(j);
                    DateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                    if(childNode20.getNodeName().contains("#text")){
                        continue;
                    }else{
                        if(childNode20.getNodeName().contains("YWID")){
                            ttreqnuclideimport.setYWID(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("REQUEST_NO")){
                            ttreqnuclideimport.setREQUEST_NO(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("NUCLIDE_MANUFACTURE")){
                            ttreqnuclideimport.setNUCLIDE_MANUFACTURE(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("IMPORT_REASON_OTHERS")){
                            ttreqnuclideimport.setIMPORT_REASON_OTHERS(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("ATTACHMENT")){
                            ttreqnuclideimport.setATTACHMENT(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("ATTACHMENT_OTHERS")){
                            ttreqnuclideimport.setATTACHMENT_OTHERS(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("INSERT_DATE")){
                            Timestamp td = new Timestamp(format.parse(childNode20.getTextContent()).getTime());
                            ttreqnuclideimport.setINSERT_DATE(td);
                        }
                        if(childNode20.getNodeName().contains("NOT_PASS_REASON")){
                            ttreqnuclideimport.setNOT_PASS_REASON(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("DO_DATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
                            ttreqnuclideimport.setDO_DATE(sdf.parse(childNode20.getTextContent()));
                        }
                        if(childNode20.getNodeName().contains("REQUEST_SOURCE")){
                            ttreqnuclideimport.setREQUEST_SOURCE(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("REQUEST_SOURCE_ID")){
                            ttreqnuclideimport.setREQUEST_SOURCE_ID(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("DO_PERSON")){
                            ttreqnuclideimport.setDO_PERSON(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("BE_PASS")){
                            ttreqnuclideimport.setBE_PASS(Integer.parseInt(childNode20.getTextContent()));
                        }
                        if(childNode20.getNodeName().contains("RETREAT_REASON")){
                            ttreqnuclideimport.setRETREAT_REASON(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("GOODS_CODE")){
                            ttreqnuclideimport.setGOODS_CODE(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("USER_ID")){
                            ttreqnuclideimport.setUSER_ID(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("REVOKE_DECLARE")){
                            ttreqnuclideimport.setREVOKE_DECLARE(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("REVOKE_REASON")){
                            ttreqnuclideimport.setREVOKE_REASON(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("IMPORT_IS_RECORD")){
                            ttreqnuclideimport.setIMPORT_IS_RECORD(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("OLD_MARK")){
                            ttreqnuclideimport.setOLD_MARK(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("IMPORTCOMPANY_DOPERSON")){
                            ttreqnuclideimport.setIMPORTCOMPANY_DOPERSON(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("IMPORT_COMPANY_INSTANCEID")){
                            ttreqnuclideimport.setIMPORT_COMPANY_INSTANCEID(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("IMPORTCOMPANY_PHONEORFAX")){
                            ttreqnuclideimport.setIMPORTCOMPANY_PHONEORFAX(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("IMPORTCOMPANY_NAME")){
                            ttreqnuclideimport.setIMPORTCOMPANY_NAME(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("MODIFY_DATE")){
                            Timestamp tc = new Timestamp(format.parse(childNode20.getTextContent()).getTime());
                            ttreqnuclideimport.setMODIFY_DATE(tc);
                        }
                        if(childNode20.getNodeName().contains("NUCLIDE_EXPORTCOUNTRY")){
                            ttreqnuclideimport.setNUCLIDE_EXPORTCOUNTRY(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("IMPORT_REASON")){
                            ttreqnuclideimport.setIMPORT_REASON(Integer.parseInt(childNode20.getTextContent()));
                        }
                        if(childNode20.getNodeName().contains("USECOMPANY_NAME")){
                            ttreqnuclideimport.setUSECOMPANY_NAME(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("USECOMPANY_DOPERSON")){
                            ttreqnuclideimport.setUSECOMPANY_DOPERSON(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("IMPORT_ID")){
                            ttreqnuclideimport.setIMPORT_ID(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("IMPORTCOMPANY_POST")){
                            ttreqnuclideimport.setIMPORTCOMPANY_POST(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("PASS_NO")){
                            ttreqnuclideimport.setPASS_NO(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("IMPORTCOMPANY_CONTACTADDR")){
                            ttreqnuclideimport.setIMPORTCOMPANY_CONTACTADDR(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("RESPONSE_STATUS")){
                            ttreqnuclideimport.setRESPONSE_STATUS(Integer.parseInt(childNode20.getTextContent()));
                        }
                        if(childNode20.getNodeName().contains("USE_COMPANY_INSTANCEID")){
                            ttreqnuclideimport.setUSE_COMPANY_INSTANCEID(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("IMPORTCOMPANY_LICENSENO")){
                            ttreqnuclideimport.setIMPORTCOMPANY_LICENSENO(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("RESPONSE_NO")){
                            ttreqnuclideimport.setRESPONSE_NO(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("USECPMPANY_CONTACTADDR")){
                            ttreqnuclideimport.setUSECPMPANY_CONTACTADDR(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("USECOMPANY_LICENSENO")){
                            ttreqnuclideimport.setUSECOMPANY_LICENSENO(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("USECOMPANY_POST")){
                            ttreqnuclideimport.setUSECOMPANY_POST(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("USECOMPANY_PHONEORFAX")){
                            ttreqnuclideimport.setUSECOMPANY_PHONEORFAX(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("NUCLIDE_PRODUCE_COUNTRY")){
                            ttreqnuclideimport.setNUCLIDE_PRODUCE_COUNTRY(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("INSERT_USER")){
                            ttreqnuclideimport.setINSERT_USER(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("REGION_CODE_1")){
                            ttreqnuclideimport.setREGION_CODE_1(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("REGION_CODE_2")){
                            ttreqnuclideimport.setREGION_CODE_2(childNode20.getTextContent());
                        }
                        if(childNode20.getNodeName().contains("MODIFY_USER")){
                            ttreqnuclideimport.setMODIFY_USER(childNode20.getTextContent());
                        }

                    }

                }
                ttreqnuclideimportlist.add(ttreqnuclideimport);
            }

            List<TTREQNUCLIDEIMPORTDETAIL> ttreqnuclideimportdetaillist = new ArrayList<TTREQNUCLIDEIMPORTDETAIL>();
            NodeList nl21 = doc.getElementsByTagName("TT_REQ_NUCLIDE_IMPORT_DETAIL");
            for (int i = 0; i < nl21.getLength(); i++) {
                // 获取一个节点
                Node node21 = nl21.item(i);
                //获取所有子节点数据
                NodeList childNodes21=node21.getChildNodes();
                TTREQNUCLIDEIMPORTDETAIL ttreqnuclideimportdetail = new TTREQNUCLIDEIMPORTDETAIL();
                for (int j = 0; j < childNodes21.getLength(); j++) {
                    Node childNode21=childNodes21.item(j);
                    if(childNode21.getNodeName().contains("#text")){
                        continue;
                    }else{
                        if(childNode21.getNodeName().contains("YWID")){
                            ttreqnuclideimportdetail.setYWID(childNode21.getTextContent());
                        }
                        if(childNode21.getNodeName().contains("REQUEIR_YWID")){
                            ttreqnuclideimportdetail.setREQUEIR_YWID(childNode21.getTextContent());
                        }
                        if(childNode21.getNodeName().contains("CODE")){
                            ttreqnuclideimportdetail.setCODE(childNode21.getTextContent());
                        }
                        if(childNode21.getNodeName().contains("BE_BACKUP")){
                            ttreqnuclideimportdetail.setBE_BACKUP(Integer.parseInt(childNode21.getTextContent()));
                        }
                        if(childNode21.getNodeName().contains("BACK_UPDATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                            ttreqnuclideimportdetail.setBACK_UPDATE(sdf.parse(childNode21.getTextContent()));
                        }
                        if(childNode21.getNodeName().contains("RECORD_TYPE")){
                            ttreqnuclideimportdetail.setRECORD_TYPE(Integer.parseInt(childNode21.getTextContent()));
                        }
                        if(childNode21.getNodeName().contains("RECORD_ID")){
                            ttreqnuclideimportdetail.setRECORD_ID(childNode21.getTextContent());
                        }
                        if(childNode21.getNodeName().contains("STATUS")){
                            ttreqnuclideimportdetail.setSTATUS(Integer.parseInt(childNode21.getTextContent()));
                        }
                        if(childNode21.getNodeName().contains("OTHER_USE_TYPE")){
                            ttreqnuclideimportdetail.setOTHER_USE_TYPE(childNode21.getTextContent());
                        }
                        if(childNode21.getNodeName().contains("NUCLIDE_PRODUCE_COUNTRY")){
                            ttreqnuclideimportdetail.setNUCLIDE_PRODUCE_COUNTRY(childNode21.getTextContent());
                        }
                        if(childNode21.getNodeName().contains("INPERSON")){
                            ttreqnuclideimportdetail.setINPERSON(childNode21.getTextContent());
                        }
                        if(childNode21.getNodeName().contains("OUTPERSON")){
                            ttreqnuclideimportdetail.setOUTPERSON(childNode21.getTextContent());
                        }
                        if(childNode21.getNodeName().contains("INDATE")){
                            ttreqnuclideimportdetail.setINDATE(childNode21.getTextContent());
                        }
                        if(childNode21.getNodeName().contains("OUTDATE")){
                            ttreqnuclideimportdetail.setOUTDATE(childNode21.getTextContent());
                        }
                        if(childNode21.getNodeName().contains("DETAIL_ID")){
                            ttreqnuclideimportdetail.setDETAIL_ID(childNode21.getTextContent());
                        }
                        if(childNode21.getNodeName().contains("NEW_WEIGHT")){
                            ttreqnuclideimportdetail.setNEW_WEIGHT(Integer.parseInt(childNode21.getTextContent()));
                        }
                        if(childNode21.getNodeName().contains("NUCLIDE_MANUFACTURE")){
                            ttreqnuclideimportdetail.setNUCLIDE_MANUFACTURE(childNode21.getTextContent());
                        }
                        if(childNode21.getNodeName().contains("SITE")){
                            ttreqnuclideimportdetail.setSITE(childNode21.getTextContent());
                        }
                        if(childNode21.getNodeName().contains("TAB")){
                            ttreqnuclideimportdetail.setTAB(childNode21.getTextContent());
                        }
                        if(childNode21.getNodeName().contains("CATEGORY")){
                            ttreqnuclideimportdetail.setCATEGORY(childNode21.getTextContent());
                        }
                        if(childNode21.getNodeName().contains("POWER")){
                            ttreqnuclideimportdetail.setPOWER(Integer.parseInt(childNode21.getTextContent()));
                        }
                        if(childNode21.getNodeName().contains("LEAVE_FACTORY_DATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                            ttreqnuclideimportdetail.setLEAVE_FACTORY_DATE(sdf.parse(childNode21.getTextContent()));
                        }
                        if(childNode21.getNodeName().contains("SEND_DATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                            ttreqnuclideimportdetail.setSEND_DATE(sdf.parse(childNode21.getTextContent()));
                        }
                        if(childNode21.getNodeName().contains("USE_TYPE")){
                            ttreqnuclideimportdetail.setUSE_TYPE(childNode21.getTextContent());
                        }
                        if(childNode21.getNodeName().contains("IMPORT_ID")){
                            ttreqnuclideimportdetail.setIMPORT_ID(childNode21.getTextContent());
                        }
                        if(childNode21.getNodeName().contains("SITE_CODE")){
                            ttreqnuclideimportdetail.setSITE_CODE(childNode21.getTextContent());
                        }
                        if(childNode21.getNodeName().contains("LEAVE_FACTORY_ACTIVITY")){
                            ttreqnuclideimportdetail.setLEAVE_FACTORY_ACTIVITY(Integer.parseInt(childNode21.getTextContent()));
                        }
                        if(childNode21.getNodeName().contains("NUCLIDE")){
                            ttreqnuclideimportdetail.setNUCLIDE(childNode21.getTextContent());
                        }
                        if(childNode21.getNodeName().contains("ADDRESS")){
                            ttreqnuclideimportdetail.setADDRESS(childNode21.getTextContent());
                        }
                        if(childNode21.getNodeName().contains("GROSS_WEIGHT")){
                            ttreqnuclideimportdetail.setGROSS_WEIGHT(Integer.parseInt(childNode21.getTextContent()));
                        }
                        if(childNode21.getNodeName().contains("HALF_PEROD")){
                            ttreqnuclideimportdetail.setHALF_PEROD(Integer.parseInt(childNode21.getTextContent()));
                        }

                    }

                }
                ttreqnuclideimportdetaillist.add(ttreqnuclideimportdetail);
            }

            List<TTLMRCOMPANY> ttlmrcompanylist = new ArrayList<TTLMRCOMPANY>();
            NodeList nl22 = doc.getElementsByTagName("TT_LMR_COMPANY");
            for (int i = 0; i < nl22.getLength(); i++) {
                // 获取一个节点
                Node node22 = nl22.item(i);
                //获取所有子节点数据
                NodeList childNodes22=node22.getChildNodes();
                TTLMRCOMPANY ttlmrcompany = new TTLMRCOMPANY();
                for (int j = 0; j < childNodes22.getLength(); j++) {
                    Node childNode22=childNodes22.item(j);
                    if(childNode22.getNodeName().contains("#text")){
                        continue;
                    }else{
                        if(childNode22.getNodeName().contains("YWID")){
                            ttlmrcompany.setYWID(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("REQUEST_NO")){
                            ttlmrcompany.setREQUEST_NO(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("PASS_NO")){
                            ttlmrcompany.setPASS_NO(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("NOT_PASS_REASON")){
                            ttlmrcompany.setNOT_PASS_REASON(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("MODIFY_USER")){
                            ttlmrcompany.setMODIFY_USER(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("MODIFY_DATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                            ttlmrcompany.setMODIFY_DATE(sdf.parse(childNode22.getTextContent()));
                        }
                        if(childNode22.getNodeName().contains("CERTIFICATE_DATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                            ttlmrcompany.setCERTIFICATE_DATE(sdf.parse(childNode22.getTextContent()));
                        }
                        if(childNode22.getNodeName().contains("INSERT_DATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                            ttlmrcompany.setINSERT_DATE(sdf.parse(childNode22.getTextContent()));
                        }
                        if(childNode22.getNodeName().contains("NEW_LEGAL")){
                            ttlmrcompany.setNEW_LEGAL(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("LICENSE_EDNDATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
                            ttlmrcompany.setLICENSE_EDNDATE(sdf.parse(childNode22.getTextContent()));
                        }
                        if(childNode22.getNodeName().contains("COMPANY_DOPERSON_PHONE")){
                            ttlmrcompany.setCOMPANY_DOPERSON_PHONE(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("NEW_NAME")){
                            ttlmrcompany.setNEW_NAME(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("OLD_LEGAL_PERSONID_TYPE")){
                            ttlmrcompany.setOLD_LEGAL_PERSONID_TYPE(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("DO_DATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
                            ttlmrcompany.setDO_DATE(sdf.parse(childNode22.getTextContent()));
                        }
                        if(childNode22.getNodeName().contains("LICENSE_NO")){
                            ttlmrcompany.setLICENSE_NO(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("NEW_LEGAL_PERSONID_TYPE")){
                            ttlmrcompany.setNEW_LEGAL_PERSONID_TYPE(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("OLD_LEGAL_PERSONID")){
                            ttlmrcompany.setOLD_LEGAL_PERSONID(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("SUBMIT_DATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                            ttlmrcompany.setSUBMIT_DATE(sdf.parse(childNode22.getTextContent()));
                        }
                        if(childNode22.getNodeName().contains("INSTANCEID")){
                            ttlmrcompany.setINSTANCEID(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("NEW_ADDRESS")){
                            ttlmrcompany.setNEW_ADDRESS(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("LICENSE_ISSUE_ORGAN")){
                            ttlmrcompany.setLICENSE_ISSUE_ORGAN(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("COMPANY_ID")){
                            ttlmrcompany.setCOMPANY_ID(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("OLD_NAME")){
                            ttlmrcompany.setOLD_NAME(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("RESPONSE_STATUS")){
                            ttlmrcompany.setRESPONSE_STATUS(Integer.parseInt(childNode22.getTextContent()));
                        }
                        if(childNode22.getNodeName().contains("SUBMIT_USER")){
                            ttlmrcompany.setSUBMIT_USER(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("RESPONSE_NO")){
                            ttlmrcompany.setRESPONSE_NO(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("LMR_COMPANY_ID")){
                            ttlmrcompany.setLMR_COMPANY_ID(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("NEW_LEGAL_PERSONID")){
                            ttlmrcompany.setNEW_LEGAL_PERSONID(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("TYPE_AND_RANGE")){
                            ttlmrcompany.setTYPE_AND_RANGE(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("OLD_ADDRESS")){
                            ttlmrcompany.setOLD_ADDRESS(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("COMPANY_DOPERSON")){
                            ttlmrcompany.setCOMPANY_DOPERSON(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("OLD_LEGAL_PERSON")){
                            ttlmrcompany.setOLD_LEGAL_PERSON(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("LICENSE_STARTDATE")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
                            ttlmrcompany.setLICENSE_STARTDATE(sdf.parse(childNode22.getTextContent()));
                        }
                        if(childNode22.getNodeName().contains("DO_PERSON")){
                            ttlmrcompany.setDO_PERSON(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("INSERT_USER")){
                            ttlmrcompany.setINSERT_USER(childNode22.getTextContent());
                        }
                        if(childNode22.getNodeName().contains("REGION_CODE_1")){
                            ttlmrcompany.setREGION_CODE_1(childNode22.getTextContent());
                        }

                    }

                }
                ttlmrcompanylist.add(ttlmrcompany);
            }

            List<TTLRNONSEALACTIVERANGE> ttlrnonsealactiverangelist = new ArrayList<TTLRNONSEALACTIVERANGE>();
            NodeList nl23 = doc.getElementsByTagName("TT_LR_NONSEAL_ACTIVE_RANGE");
            for (int i = 0; i < nl23.getLength(); i++) {
                // 获取一个节点
                Node node23 = nl23.item(i);
                //获取所有子节点数据
                NodeList childNodes23=node23.getChildNodes();
                TTLRNONSEALACTIVERANGE ttlrnonsealactiverange = new TTLRNONSEALACTIVERANGE();
                for (int j = 0; j < childNodes23.getLength(); j++) {
                    Node childNode23=childNodes23.item(j);
                    DateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                    if(childNode23.getNodeName().contains("#text")){
                        continue;
                    }else{
                        if(childNode23.getNodeName().contains("YWID")){
                            ttlrnonsealactiverange.setYWID(childNode23.getTextContent());
                        }
                        if(childNode23.getNodeName().contains("REQUEIR_YWID")){
                            ttlrnonsealactiverange.setREQUEIR_YWID(childNode23.getTextContent());
                        }
                        if(childNode23.getNodeName().contains("ANAME")){
                            ttlrnonsealactiverange.setANAME(childNode23.getTextContent());
                        }
                        if(childNode23.getNodeName().contains("ALEVEL")){
                            ttlrnonsealactiverange.setALEVEL(childNode23.getTextContent());
                        }
                        if(childNode23.getNodeName().contains("SITE_CODE")){
                            ttlrnonsealactiverange.setSITE_CODE(childNode23.getTextContent());
                        }
                        if(childNode23.getNodeName().contains("COMPANY_ID")){
                            ttlrnonsealactiverange.setCOMPANY_ID(childNode23.getTextContent());
                        }
                        if(childNode23.getNodeName().contains("YEARMAX_COUNT")){
                            ttlrnonsealactiverange.setYEARMAX_COUNT(childNode23.getTextContent());
                        }
                        if(childNode23.getNodeName().contains("ACTIVE_TYPE")){
                            ttlrnonsealactiverange.setACTIVE_TYPE(childNode23.getTextContent());
                        }
                        if(childNode23.getNodeName().contains("NUCLIDE_NAME")){
                            ttlrnonsealactiverange.setNUCLIDE_NAME(childNode23.getTextContent());
                        }
                        if(childNode23.getNodeName().contains("DAYMAX_COUNT")){
                            ttlrnonsealactiverange.setDAYMAX_COUNT(childNode23.getTextContent());
                        }
                        if(childNode23.getNodeName().contains("ACTIVE_RANGE_ID")){
                            ttlrnonsealactiverange.setACTIVE_RANGE_ID(childNode23.getTextContent());
                        }
                        if(childNode23.getNodeName().contains("UPDATETIME")){
                            Timestamp tb = new Timestamp(format.parse(childNode23.getTextContent()).getTime());
                            ttlrnonsealactiverange.setUPDATETIME(tb);
                        }

                    }

                }
                ttlrnonsealactiverangelist.add(ttlrnonsealactiverange);
            }*/
                analysisXMLService.getXMLData(ttlrcompanylist);
            }

            /*analysisXMLService.getXMLData(ttlrcompanylist,ttlrsitelist,ttlrwsdeptlist,ttlrlist,ttlrrecordlist,ttlrinstrumentlist,ttlrgoodslist,ttlrmanagepersonlist,
                    ttlrworkpersonlist,ttlrtrainrecordlist,imattachmentlist,ttlrpersondosagelist,ttsysattachmanagelist,ttreqwastenuclidetakebacklist,
                    ttreqwastetakebackdetaillist,ttreqnuclidetransferllist,ttreqnuclidetransferdetaillist,ttlrnuclideactiverangelist,ttlcrcompanylist,
                    ttreqnuclideimportlist,ttreqnuclideimportdetaillist,ttlmrcompanylist,ttlrnonsealactiverangelist);*/
            return new Result(ResultCode.SUCCESS.toString(), ResultMsg.ADD_SUCCESS,null,null).getJson();
        }catch (Exception e){
            return new Result(ResultCode.ERROR.toString(),ResultMsg.ADD_ERROR,null,null).getJson();
        }
    }


}
