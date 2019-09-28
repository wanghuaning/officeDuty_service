package com.local.service.impl;

import com.local.entity.*;
import com.local.entity.sys.SYS_AREA;
import com.local.entity.sys.SYS_USER;
import com.local.service.AnalysisXMLService;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnalysisXMLServiceImpl implements AnalysisXMLService {

    @Autowired
    Dao dao;

    //获取xml数据到本地数据库
    @Override
    /*public void getXMLData(List<TTLRCOMPANY> ttlrcompanylist, List<TTLRSITE> ttlrsitelist, List<TTLRWSDEPT> ttlrwsdeptlist, List<TTLRRADIALSETACTIVERANGE> ttlrlist,
                           List<TTLRRADIALSETRECORD> ttlrrecordlist, List<TTLRMONITORINSTRUMENT> ttlrinstrumentlist, List<TTLRPROTECTGOODS> ttlrgoodslist,
                           List<TTLRRADIALMANAGEPERSON> ttlrmanagepersonlist, List<TTLRRADIALWORKPERSON> ttlrworkpersonlist, List<TTLRTRAINRECORD> ttlrtrainrecordlist,
                           List<IMATTACHMENT> imattachmentlist, List<TTLRPERSONDOSAGE> ttlrpersondosagelist, List<TTSYSATTACHMANAGE> ttsysattachmanagelist,
                           List<TTREQWASTENUCLIDETAKEBACK> ttreqwastenuclidetakebacklist, List<TTREQWASTETAKEBACKDETAIL> ttreqwastetakebackdetaillist,
                           List<TTREQNUCLIDETRANSFER> ttreqnuclidetransferllist, List<TTREQNUCLIDETRANSFERDETAIL> ttreqnuclidetransferdetaillist,
                           List<TTLRNUCLIDEACTIVERANGE> ttlrnuclideactiverangelist, List<TTLCRCOMPANY> ttlcrcompanylist, List<TTREQNUCLIDEIMPORT> ttreqnuclideimportlist,
                           List<TTREQNUCLIDEIMPORTDETAIL> ttreqnuclideimportdetaillist, List<TTLMRCOMPANY> ttlmrcompanylist, List<TTLRNONSEALACTIVERANGE> ttlrnonsealactiverangelist) {*/

    public void getXMLData(List<TTLRCOMPANY> ttlrcompanylist) {
        for (int i = 0; i <ttlrcompanylist.size() ; i++) {
            TTLRCOMPANY temp=new TTLRCOMPANY();
            temp=ttlrcompanylist.get(i);
            LRADResource lradr=new LRADResource();
            lradr.setNAME(temp.getNAME());
            lradr.setUNIFY_SOCIETY_CODE(temp.getUNIFY_SOCIETY_CODE());
            lradr.setLEGAL_PERSON(temp.getLEGAL_PERSON());
            lradr.setPASS_NO(temp.getPASS_NO());
            lradr.setPASS_RESULT(temp.getBE_PASS());
            //按断id是否存在
            Criteria criteria = Cnd.cri();
            criteria.where().andEquals("ID", temp.getCOMPANY_ID());
            List<LRADResource> tp = dao.query(LRADResource.class, criteria);
            if (tp.size() > 0) {
                lradr.setID(temp.getCOMPANY_ID());
                lradr.setOPERSTATUS("1");
                dao.update(lradr);
            } else {
                lradr.setOPERSTATUS("0");
                lradr.setID(temp.getCOMPANY_ID());
                dao.fastInsert(lradr);
            }
        }

        /*for (int i = 0; i <ttlrsitelist.size() ; i++) {
            TTLRSITE temp2=new TTLRSITE();
            temp2=ttlrsitelist.get(i);
            dao.fastInsert(temp2);
        }

        for (int i = 0; i <ttlrwsdeptlist.size() ; i++) {
            TTLRWSDEPT temp3=new TTLRWSDEPT();
            temp3=ttlrwsdeptlist.get(i);
            dao.fastInsert(temp3);
        }

        for (int i = 0; i <ttlrlist.size() ; i++) {
            TTLRRADIALSETACTIVERANGE temp4=new TTLRRADIALSETACTIVERANGE();
            temp4=ttlrlist.get(i);
            dao.fastInsert(temp4);
        }

        for (int i = 0; i <ttlrrecordlist.size() ; i++) {
            TTLRRADIALSETRECORD temp5=new TTLRRADIALSETRECORD();
            temp5=ttlrrecordlist.get(i);
            dao.fastInsert(temp5);
        }

        for (int i = 0; i <ttlrinstrumentlist.size() ; i++) {
            TTLRMONITORINSTRUMENT temp6=new TTLRMONITORINSTRUMENT();
            temp6=ttlrinstrumentlist.get(i);
            dao.fastInsert(temp6);
        }

        for (int i = 0; i <ttlrgoodslist.size() ; i++) {
            TTLRPROTECTGOODS temp7=new TTLRPROTECTGOODS();
            temp7=ttlrgoodslist.get(i);
            dao.fastInsert(temp7);
        }


        for (int i = 0; i <ttlrmanagepersonlist.size() ; i++) {
            TTLRRADIALMANAGEPERSON temp8=new TTLRRADIALMANAGEPERSON();
            temp8=ttlrmanagepersonlist.get(i);
            dao.fastInsert(temp8);
        }


        for (int i = 0; i <ttlrworkpersonlist.size() ; i++) {
            TTLRRADIALWORKPERSON temp9=new TTLRRADIALWORKPERSON();
            temp9=ttlrworkpersonlist.get(i);
            dao.fastInsert(temp9);
        }

        for (int i = 0; i <ttlrtrainrecordlist.size() ; i++) {
            TTLRTRAINRECORD temp10=new TTLRTRAINRECORD();
            temp10=ttlrtrainrecordlist.get(i);
            dao.fastInsert(temp10);
        }

        for (int i = 0; i <imattachmentlist.size() ; i++) {
            IMATTACHMENT temp11=new IMATTACHMENT();
            temp11=imattachmentlist.get(i);
            dao.fastInsert(temp11);
        }

        for (int i = 0; i <ttlrpersondosagelist.size() ; i++) {
            TTLRPERSONDOSAGE temp12=new TTLRPERSONDOSAGE();
            temp12=ttlrpersondosagelist.get(i);
            dao.fastInsert(temp12);
        }

        for (int i = 0; i <ttsysattachmanagelist.size() ; i++) {
            TTSYSATTACHMANAGE temp13=new TTSYSATTACHMANAGE();
            temp13=ttsysattachmanagelist.get(i);
            dao.fastInsert(temp13);
        }

        for (int i = 0; i <ttreqwastenuclidetakebacklist.size() ; i++) {
            TTREQWASTENUCLIDETAKEBACK temp14=new TTREQWASTENUCLIDETAKEBACK();
            temp14=ttreqwastenuclidetakebacklist.get(i);
            dao.fastInsert(temp14);
        }

        for (int i = 0; i <ttreqwastetakebackdetaillist.size() ; i++) {
            TTREQWASTETAKEBACKDETAIL temp15=new TTREQWASTETAKEBACKDETAIL();
            temp15=ttreqwastetakebackdetaillist.get(i);
            dao.fastInsert(temp15);
        }

        for (int i = 0; i <ttreqnuclidetransferllist.size() ; i++) {
            TTREQNUCLIDETRANSFER temp16=new TTREQNUCLIDETRANSFER();
            temp16=ttreqnuclidetransferllist.get(i);
            dao.fastInsert(temp16);
        }

        for (int i = 0; i <ttreqnuclidetransferdetaillist.size() ; i++) {
            TTREQNUCLIDETRANSFERDETAIL temp17=new TTREQNUCLIDETRANSFERDETAIL();
            temp17=ttreqnuclidetransferdetaillist.get(i);
            dao.fastInsert(temp17);
        }


        for (int i = 0; i <ttlrnuclideactiverangelist.size() ; i++) {
            TTLRNUCLIDEACTIVERANGE temp18=new TTLRNUCLIDEACTIVERANGE();
            temp18=ttlrnuclideactiverangelist.get(i);
            dao.fastInsert(temp18);
        }

        for (int i = 0; i <ttlcrcompanylist.size() ; i++) {
            TTLCRCOMPANY temp19=new TTLCRCOMPANY();
            temp19=ttlcrcompanylist.get(i);
            dao.fastInsert(temp19);
        }

        for (int i = 0; i <ttreqnuclideimportlist.size() ; i++) {
            TTREQNUCLIDEIMPORT temp20=new TTREQNUCLIDEIMPORT();
            temp20=ttreqnuclideimportlist.get(i);
            dao.fastInsert(temp20);
        }

        for (int i = 0; i <ttreqnuclideimportdetaillist.size() ; i++) {
            TTREQNUCLIDEIMPORTDETAIL temp21=new TTREQNUCLIDEIMPORTDETAIL();
            temp21=ttreqnuclideimportdetaillist.get(i);
            dao.fastInsert(temp21);
        }

        for (int i = 0; i <ttlmrcompanylist.size() ; i++) {
            TTLMRCOMPANY temp22=new TTLMRCOMPANY();
            temp22=ttlmrcompanylist.get(i);
            dao.fastInsert(temp22);
        }

        for (int i = 0; i <ttlrnonsealactiverangelist.size() ; i++) {
            TTLRNONSEALACTIVERANGE temp23=new TTLRNONSEALACTIVERANGE();
            temp23=ttlrnonsealactiverangelist.get(i);
            dao.fastInsert(temp23);
        }*/


    }




}
