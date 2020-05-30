package com.local.model;

import com.local.entity.sys.SYS_Rank;
import lombok.Data;

import java.util.List;

@Data
public class RankCountModel {
    private String title;
    private int rowIndex;
    private String name;
    private String bianzhishu;
    private String taogai;
    private String taogaijunzhuan;
    private String taogaichaozhi;
    private String beforerank;
    private String beforejunrank;
    private String beforechaozhirank;
    private String jinshengrank;
    private String jinshengjunrank;
    private String jinshengshimingrank;
    private String quedingzhuanzheng;
    private String quedingjun;
    private String quedingdiaoru;
    private String quedingmianzhiwu;
    private String jiangdichufen;
    private String jiangdidiaodong;
    private String jianshaomiaozhi;
    private String jianshaodiaozou;
    private String afterrank;
    private String afterjunrank;
    private String afterchaozhirank;
    private List<SYS_Rank> nameList;
    private List<SYS_Rank> bianzhishuList;
    private List<SYS_Rank> taogaiList;
    private List<SYS_Rank> taogaijunzhuanList;
    private List<SYS_Rank> taogaichaozhiList;
    private List<SYS_Rank> beforerankList;
    private List<SYS_Rank> beforejunrankList;
    private List<SYS_Rank> beforechaozhirankList;
    private List<SYS_Rank> jinshengrankList;
    private List<SYS_Rank> jinshengjunrankList;
    private List<SYS_Rank> jinshengshimingrankList;
    private List<SYS_Rank> quedingzhuanzhengList;
    private List<SYS_Rank> quedingjunList;
    private List<SYS_Rank> quedingdiaoruList;
    private List<SYS_Rank> quedingmianzhiwuList;
    private List<SYS_Rank> jiangdichufenList;
    private List<SYS_Rank> jiangdidiaodongList;
    private List<SYS_Rank> jianshaomiaozhiList;
    private List<SYS_Rank> jianshaodiaozouList;
    private List<SYS_Rank> afterrankList;
    private List<SYS_Rank> afterjunrankList;
    private List<SYS_Rank> afterchaozhirankList;
}
