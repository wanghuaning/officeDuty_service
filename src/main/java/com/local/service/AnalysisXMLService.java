package com.local.service;

import com.local.entity.*;

import java.util.List;

public interface AnalysisXMLService {

    //获取xml数据到本地数据库
    /*void getXMLData(List<TTLRCOMPANY> ttlrcompanylist, List<TTLRSITE> ttlrsitelist, List<TTLRWSDEPT> ttlrwsdeptlist, List<TTLRRADIALSETACTIVERANGE> ttlrlist,
                    List<TTLRRADIALSETRECORD> ttlrrecordlist, List<TTLRMONITORINSTRUMENT> ttlrinstrumentlist, List<TTLRPROTECTGOODS> ttlrgoodslist, List<TTLRRADIALMANAGEPERSON> ttlrmanagepersonlist,
                    List<TTLRRADIALWORKPERSON> ttlrworkpersonlist, List<TTLRTRAINRECORD> ttlrtrainrecordlist, List<IMATTACHMENT> imattachmentlist,  List<TTLRPERSONDOSAGE> ttlrpersondosagelist,
                    List<TTSYSATTACHMANAGE> ttsysattachmanagelist, List<TTREQWASTENUCLIDETAKEBACK> ttreqwastenuclidetakebacklist,
                    List<TTREQWASTETAKEBACKDETAIL> ttreqwastetakebackdetaillist, List<TTREQNUCLIDETRANSFER> ttreqnuclidetransferllist, List<TTREQNUCLIDETRANSFERDETAIL> ttreqnuclidetransferdetaillist,
                    List<TTLRNUCLIDEACTIVERANGE> ttlrnuclideactiverangelist, List<TTLCRCOMPANY> ttlcrcompanylist, List<TTREQNUCLIDEIMPORT> ttreqnuclideimportlist,
                    List<TTREQNUCLIDEIMPORTDETAIL> ttreqnuclideimportdetaillist, List<TTLMRCOMPANY> ttlmrcompanylist, List<TTLRNONSEALACTIVERANGE> ttlrnonsealactiverangelist);*/
    void getXMLData(List<TTLRCOMPANY> ttlrcompanylist);


}
