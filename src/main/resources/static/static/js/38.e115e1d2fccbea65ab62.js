webpackJsonp([38],{"53Oj":function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var l=a("Ks07"),o=a("R2SV"),i=a("lbHh"),r=a.n(i),n={name:"retire",mixins:[l.a],data:function(){return{loading:!1,query:{sex:"全部",party:this.$route.query.party,age:this.$route.query.sex,duty:this.$route.query.duty},downloadLoading:!1,date1:"",date2:"",date3:"",date4:"",sexs:[{key:"全部",display_name:"全部"},{key:"男",display_name:"男"},{key:"女",display_name:"女"}],partys:[{key:"全部",display_name:"全部"},{key:"党员",display_name:"党员"},{key:"非党员",display_name:"非党员"}],ages:[{key:"全部",display_name:"全部"},{key:"20",display_name:"20-29"},{key:"30",display_name:"30-39"},{key:"40",display_name:"40-49"},{key:"50",display_name:"50-59"},{key:"60",display_name:"60以上"}],dutys:[{key:"全部",display_name:"全部"},{key:"县处级正职",display_name:"县处级正职"},{key:"县处级副职",display_name:"县处级副职"},{key:"乡科级正职",display_name:"乡科级正职"},{key:"乡科级副职",display_name:"乡科级副职"},{key:"其他",display_name:"其他"}]}},methods:{beforeInit:function(){this.url="api/people/peopleDetailInfo";var e=r.a.get("unitId"),t=localStorage.getItem("checkedUnit"),a=localStorage.getItem("childUnit"),l=this.query,o=l.sex,i=l.party,n=l.age,s=l.duty;return void 0===l.sex&&(o="全部",l.sex="全部"),void 0===l.party&&(i="全部",l.party="全部"),void 0===l.age?(n="全部",l.age="全部"):-1!==l.age.indexOf("-")?n=l.age.split("-")[0]:-1!==l.age.indexOf("60")&&(n=60),void 0===l.duty&&(s="全部",l.duty="全部"),this.params={page:this.page,size:this.size,isChild:t,childUnit:a,sex:o,party:i,age:n,duty:s,unitId:e},!0},getRowClass:function(e){e.row,e.column,e.rowIndex,e.columnIndex},formatter:function(e,t,a,l){if(null!=e[t.property])return Object(o.a)(e[t.property])},downloadPeopleDetail:function(){var e=localStorage.getItem("checkedUnit"),t=localStorage.getItem("unitId"),a=localStorage.getItem("childUnit"),l=this.query,o=l.sex,i=l.party,r=l.age,n=l.duty;void 0===l.sex?(o="全部",l.sex="全部"):-1!==l.sex.indexOf("-")?o=l.sex.split(".")[0]:-1!==l.sex.indexOf("60")&&(o=60),void 0===l.party?(i="全部",l.party="全部"):-1!==l.party.indexOf(".")&&(i=l.party.split(".")[1]),void 0===l.age?(r="全部",l.age="全部"):-1!==l.age.indexOf(".")&&(r=l.age.split(".")[1]),void 0===l.duty?(n="全部",l.duty="全部"):-1!==l.duty.indexOf(".")&&(n=l.duty.split(".")[1]),this.downloadLoading=!0;var s=this,p={isChild:e,childUnit:a,sex:o,age:r,party:i,duty:n,unitId:t};this.$axios({method:"post",url:"http://152.136.134.221:8085/api/people/outPeopleDetailExcel",params:p,contentType:"application/x-www-form-urlencoded",responseType:"blob"}).then(function(e){console.log(e);var t=document.createElement("a"),a=new Blob([e.data],{type:"application/vnd.ms-excel"});t.style.display="none",t.href=URL.createObjectURL(a),t.setAttribute("download","人员详情信息表导出.xls"),document.body.appendChild(t),t.click(),document.body.removeChild(t),s.downloadLoading=!1}).catch(function(e){console.log(e)})}},created:function(){var e=this;this.$nextTick(function(){e.init()})}},s={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("el-form",{staticClass:"demo-form-inline",staticStyle:{margin:"0px","margin-bottom":"2px"},attrs:{inline:!0,size:"formInline"}},[a("el-form-item",{attrs:{prop:"states",label:"性别："}},[a("el-select",{staticClass:"filter-item",attrs:{placeholder:"请选择性别"},on:{change:e.toQuery},model:{value:e.query.sex,callback:function(t){e.$set(e.query,"sex",t)},expression:"query.sex"}},e._l(e.sexs,function(e){return a("el-option",{key:e.key,attrs:{label:e.display_name,value:e.key}})}),1)],1),e._v(" "),a("el-form-item",{attrs:{prop:"states",label:"年龄："}},[a("el-select",{staticClass:"filter-item",attrs:{placeholder:"请选择年龄"},on:{change:e.toQuery},model:{value:e.query.age,callback:function(t){e.$set(e.query,"age",t)},expression:"query.age"}},e._l(e.ages,function(e){return a("el-option",{key:e.key,attrs:{label:e.display_name,value:e.key}})}),1)],1),e._v(" "),a("el-form-item",{attrs:{prop:"states",label:"党员："}},[a("el-select",{staticClass:"filter-item",attrs:{placeholder:"请选择党员"},on:{change:e.toQuery},model:{value:e.query.party,callback:function(t){e.$set(e.query,"party",t)},expression:"query.party"}},e._l(e.partys,function(e){return a("el-option",{key:e.key,attrs:{label:e.display_name,value:e.key}})}),1)],1),e._v(" "),a("el-form-item",{attrs:{prop:"states",label:"职务："}},[a("el-select",{staticClass:"filter-item",attrs:{placeholder:"请选择职务"},on:{change:e.toQuery},model:{value:e.query.duty,callback:function(t){e.$set(e.query,"duty",t)},expression:"query.duty"}},e._l(e.dutys,function(e){return a("el-option",{key:e.key,attrs:{label:e.display_name,value:e.key}})}),1)],1),e._v(" "),a("div",{staticStyle:{display:"inline-block"}},[a("el-button",{staticClass:"filter-item",attrs:{loading:e.downloadLoading,size:"mini",type:"warning",icon:"el-icon-download"},on:{click:e.downloadPeopleDetail}},[e._v("导出")])],1)],1),e._v(" "),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],staticStyle:{width:"100%","font-size":"10px","margin-top":"2px"},attrs:{"row-style":e.getRowClass,"header-row-style":e.getRowClass,"header-cell-style":{"text-align":"center",background:"#ADD8E6",color:"#030303"},"cell-style":{padding:"0px"},data:e.data,size:"small","row-key":"id","element-loading-text":"请稍后","highlight-current-row":"","element-loading-spinner":"el-icon-loading",border:""}},[a("el-table-column",{attrs:{type:"index",label:"序号",width:"60"}}),e._v(" "),a("el-table-column",{attrs:{sortable:"","show-overflow-tooltip":!0,width:"100px",label:"姓名",prop:"name"}}),e._v(" "),a("el-table-column",{attrs:{sortable:"","show-overflow-tooltip":!0,label:"单位",prop:"unitName"}}),e._v(" "),a("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"idcard",width:"180px",label:"身份证号"}}),e._v(" "),a("el-table-column",{attrs:{sortable:"","show-overflow-tooltip":!0,formatter:e.formatter,width:"120px",prop:"birthday",label:"出生年月"}}),e._v(" "),a("el-table-column",{attrs:{sortable:"","show-overflow-tooltip":!0,width:"80px",prop:"age",label:"年龄"}}),e._v(" "),a("el-table-column",{attrs:{"show-overflow-tooltip":!0,width:"60px",prop:"sex",label:"性别"}}),e._v(" "),a("el-table-column",{attrs:{sortable:"","show-overflow-tooltip":!0,width:"80px",prop:"nationality",label:"民族"}}),e._v(" "),a("el-table-column",{attrs:{"show-overflow-tooltip":!0,formatter:e.formatter,width:"120px",prop:"workday",label:"参加工作时间"}}),e._v(" "),a("el-table-column",{attrs:{sortable:"","show-overflow-tooltip":!0,prop:"party",width:"110px",label:"政治面貌"}}),e._v(" "),a("el-table-column",{attrs:{sortable:"","show-overflow-tooltip":!0,prop:"position",width:"110px",label:"职务层次"}}),e._v(" "),a("el-table-column",{attrs:{sortable:"","show-overflow-tooltip":!0,prop:"positionLevel",width:"120px",label:"现任职级"}}),e._v(" "),a("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"politicalStatus",width:"120px",label:"编制类型"}})],1),e._v(" "),a("el-pagination",{staticStyle:{"margin-top":"2px",float:"right"},attrs:{total:e.total,"current-page":e.page+1,layout:"total, prev, pager, next, jumper, sizes"},on:{"size-change":e.sizeChange,"current-change":e.pageChange}})],1)},staticRenderFns:[]};var p=a("VU/8")(n,s,!1,function(e){a("mpzo")},"data-v-6244aaa2",null);t.default=p.exports},mpzo:function(e,t){}});