webpackJsonp([31],{mywa:function(t,i){},qWGQ:function(t,i,e){"use strict";Object.defineProperty(i,"__esModule",{value:!0});var s=e("QrwQ"),a=e("GXH0"),n=e.n(a),r=e("EAjQ"),d=(e("DmJO"),e("mBDO")),h=e("nG6b"),c=e("v9OR"),m=e("gUlz"),o={components:{Treeselect:n.a,IconSelect:r.a,position:s.default,rank:d.default,education:h.default,reward:c.default,assessment:m.default},props:["pid"],data:function(){return{activeName:"first",position:"",rank:"",education:"",reward:"",assessment:"",title:""}},watch:{pid:function(t,i){if(this.title=this.pid.items[1],"first"===this.activeName)this.position=this.pid.items[0],this.$refs.position.init();else if("second"===this.activeName){this.rank=this.pid.items[0],this.$refs.rank.init()}else if("third"===this.activeName){this.education=this.pid.items[0],this.$refs.education.init()}else if("fourth"===this.activeName){this.reward=this.pid.items[0],this.$refs.reward.init()}else if("five"===this.activeName){this.assessment=this.pid.items[0],this.$refs.assessment.init()}}},created:function(){console.log(this.pid),this.title=this.pid.items[1],"first"===this.activeName?this.position=this.pid.items[0]:"second"===this.activeName?this.rank=this.pid.items[0]:"third"===this.activeName?this.education=this.pid.items[0]:"fourth"===this.activeName?this.reward=this.pid.items[0]:"five"===this.activeName&&(this.assessment=this.pid.items[0])},methods:{tabclick:function(){this.title=this.pid.items[1],"first"===this.activeName?this.position=this.pid.items[0]:"second"===this.activeName?this.rank=this.pid.items[0]:"third"===this.activeName?this.education=this.pid.items[0]:"fourth"===this.activeName?this.reward=this.pid.items[0]:"five"===this.activeName&&(this.assessment=this.pid.items[0])}}},l={render:function(){var t=this,i=t.$createElement,e=t._self._c||i;return e("el-tabs",{staticStyle:{margin:"0px"},attrs:{type:"card"},on:{"tab-click":t.tabclick},model:{value:t.activeName,callback:function(i){t.activeName=i},expression:"activeName"}},[e("el-tab-pane",{attrs:{label:"职务信息",name:"first"}},[e("position",{ref:"position",attrs:{position:t.position}})],1),t._v(" "),e("el-tab-pane",{attrs:{label:"职级信息",name:"second"}},[e("rank",{ref:"rank",attrs:{rank:t.rank}})],1),t._v(" "),e("el-tab-pane",{attrs:{label:"学历学位",name:"third"}},[e("education",{ref:"education",attrs:{education:t.education}})],1),t._v(" "),e("el-tab-pane",{attrs:{label:"奖惩信息",name:"fourth"}},[e("reward",{ref:"reward",attrs:{reward:t.reward}})],1),t._v(" "),e("el-tab-pane",{attrs:{label:"考核信息",name:"five"}},[e("assessment",{ref:"assessment",attrs:{assessment:t.assessment}})],1),t._v(" "),e("el-tab-pane",{attrs:{label:t.title,name:"six",disabled:""}},[t._v(t._s(t.pid.items[1]))])],1)},staticRenderFns:[]};var p=e("VU/8")(o,l,!1,function(t){e("mywa")},"data-v-40f11213",null);i.default=p.exports}});