webpackJsonp([23],{MHZ4:function(e,t){},qWGQ:function(e,t,s){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=s("QrwQ"),i=s("GXH0"),n=s.n(i),r=s("EAjQ"),l=(s("DmJO"),s("mBDO")),o=s("nG6b"),d=s("v9OR"),c=s("gUlz"),f={components:{Treeselect:n.a,IconSelect:r.a,position:a.default,rank:l.default,education:o.default,reward:d.default,assessment:c.default},props:["pid"],data:function(){return{activeName:"first",position:"",rank:"",education:"",reward:"",assessment:"",title:""}},updated:function(){if(this.title=this.pid.items[1],"first"===this.activeName)this.position=this.pid.items[0],this.$refs.position.init();else if("second"===this.activeName){this.rank=this.pid.items[0],this.$refs.rank.init()}else if("third"===this.activeName){this.education=this.pid.items[0],this.$refs.education.init()}else if("fourth"===this.activeName){this.reward=this.pid.items[0],this.$refs.reward.init()}else if("five"===this.activeName){this.assessment=this.pid.items[0],this.$refs.assessment.init()}},created:function(){console.log(this.pid)}},m={render:function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("el-tabs",{staticStyle:{margin:"0px"},attrs:{type:"card"},model:{value:e.activeName,callback:function(t){e.activeName=t},expression:"activeName"}},[s("el-tab-pane",{attrs:{label:"职务信息",name:"first"}},[s("position",{ref:"position",attrs:{position:e.position}})],1),e._v(" "),s("el-tab-pane",{attrs:{label:"职级信息",name:"second"}},[s("rank",{ref:"rank",attrs:{rank:e.rank}})],1),e._v(" "),s("el-tab-pane",{attrs:{label:"学历学位",name:"third"}},[s("education",{ref:"education",attrs:{education:e.education}})],1),e._v(" "),s("el-tab-pane",{attrs:{label:"奖惩信息",name:"fourth"}},[s("reward",{ref:"reward",attrs:{reward:e.reward}})],1),e._v(" "),s("el-tab-pane",{attrs:{label:"考核信息",name:"five"}},[s("assessment",{ref:"assessment",attrs:{assessment:e.assessment}})],1),e._v(" "),s("el-tab-pane",{attrs:{label:e.title,name:"six",disabled:""}},[e._v(e._s(e.pid.items[1]))])],1)},staticRenderFns:[]};var p=s("VU/8")(f,m,!1,function(e){s("MHZ4")},"data-v-57109b92",null);t.default=p.exports}});