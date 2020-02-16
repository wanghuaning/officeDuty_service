webpackJsonp([44],{"3YB/":function(t,e){},sAes:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=a("Ks07"),l=a("R2SV"),i={name:"retire",mixins:[o.a],data:function(){return{loading:!1,query:{states:this.$route.query.params},states:"全部",downloadLoading:!1,date1:"",date2:"",date3:"",date4:"",enabledTypeOptions:[{key:"全部",display_name:"全部"},{key:"1",display_name:this.date1},{key:"2",display_name:this.date2},{key:"3",display_name:this.date3},{key:"4",display_name:this.date4}]}},methods:{beforeInit:function(){this.url="api/people/retireInfo";var t=localStorage.getItem("checkedUnit"),e=localStorage.getItem("childUnit"),a=this.query,o=a.states;return void 0===a.states?(o="全部",a.states="全部"):-1!==a.states.indexOf(".")&&(o=a.states.split(".")[1]),this.params={page:this.page,size:this.size,isChild:t,childUnit:e,states:o},!0},getRowClass:function(t){t.row,t.column,t.rowIndex,t.columnIndex},formatter:function(t,e,a,o){if(null!=t[e.property])return Object(l.a)(t[e.property])},getDate:function(){var t=(new Date).getFullYear(),e=(new Date).getMonth()+1,a=e+1,o=e+2,l=e+3;a>12&&(a-=12,t+=1),o>12&&(o-=12,t+=1),l>12&&(l-=12,t+=1),this.date1=t+"."+e,this.date2=t+"."+a,this.date3=t+"."+o,this.date4=t+"."+l,this.enabledTypeOptions=[{key:"全部",display_name:"全部"},{key:"1",display_name:this.date1},{key:"2",display_name:this.date2},{key:"3",display_name:this.date3},{key:"4",display_name:this.date4}]},downloadPeople:function(){var t=localStorage.getItem("checkedUnit"),e=localStorage.getItem("unitId"),a=localStorage.getItem("childUnit"),o=this.query,l=o.states;-1!==o.states.indexOf(".")&&(l=o.states.split(".")[1]),this.downloadLoading=!0;var i=this,n={isChild:t,childUnit:a,states:l,unitId:e};this.$axios({method:"post",url:"http://152.136.134.221:8085/api/people/outRetirExcel",params:n,contentType:"application/x-www-form-urlencoded",responseType:"blob"}).then(function(t){console.log(t);var e=document.createElement("a"),a=new Blob([t.data],{type:"application/vnd.ms-excel"});e.style.display="none",e.href=URL.createObjectURL(a),e.setAttribute("download","到期退休人员信息表导出.xls"),document.body.appendChild(e),e.click(),document.body.removeChild(e),i.downloadLoading=!1}).catch(function(t){console.log(t)})}},created:function(){var t=this;this.getDate(),this.$nextTick(function(){t.init()})}},n={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("el-select",{staticClass:"filter-item",staticStyle:{width:"10%","margin-left":"50px"},attrs:{placeholder:"请选择时间"},on:{change:t.toQuery},model:{value:t.query.states,callback:function(e){t.$set(t.query,"states",e)},expression:"query.states"}},t._l(t.enabledTypeOptions,function(t){return a("el-option",{key:t.key,attrs:{label:t.display_name,value:t.key}})}),1),t._v(" "),a("div",{staticStyle:{display:"inline-block"}},[a("el-button",{staticClass:"filter-item",attrs:{loading:t.downloadLoading,size:"mini",type:"warning",icon:"el-icon-download"},on:{click:t.downloadPeople}},[t._v("导出")])],1),t._v(" "),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],staticStyle:{width:"100%","font-size":"10px"},attrs:{"row-style":t.getRowClass,"header-row-style":t.getRowClass,"header-cell-style":{"text-align":"center",background:"#ADD8E6",color:"#030303"},"cell-style":{padding:"0px"},data:t.data,size:"small","row-key":"id","element-loading-text":"请稍后","highlight-current-row":"","element-loading-spinner":"el-icon-loading",border:""}},[a("el-table-column",{attrs:{type:"index",width:"60",label:"序号"}}),t._v(" "),a("el-table-column",{attrs:{sortable:"","show-overflow-tooltip":!0,width:"90px",label:"姓名",prop:"name"}}),t._v(" "),a("el-table-column",{attrs:{sortable:"","show-overflow-tooltip":!0,label:"单位",prop:"unitName"}}),t._v(" "),a("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"idcard",width:"180px",label:"身份证号"}}),t._v(" "),a("el-table-column",{attrs:{sortable:"","show-overflow-tooltip":!0,formatter:t.formatter,width:"120px",prop:"birthday",label:"出生年月"}}),t._v(" "),a("el-table-column",{attrs:{sortable:"","show-overflow-tooltip":!0,prop:"age",width:"120px",label:"年龄"}}),t._v(" "),a("el-table-column",{attrs:{"show-overflow-tooltip":!0,formatter:t.formatter,width:"120px",prop:"retireDate",label:"退休时间"}}),t._v(" "),a("el-table-column",{attrs:{"show-overflow-tooltip":!0,width:"60px",prop:"sex",label:"性别"}}),t._v(" "),a("el-table-column",{attrs:{sortable:"","show-overflow-tooltip":!0,width:"80px",prop:"nationality",label:"民族"}}),t._v(" "),a("el-table-column",{attrs:{"show-overflow-tooltip":!0,formatter:t.formatter,width:"120px",prop:"workday",label:"参加工作时间"}}),t._v(" "),a("el-table-column",{attrs:{sortable:"","show-overflow-tooltip":!0,prop:"position",width:"120px",label:"职务层次"}}),t._v(" "),a("el-table-column",{attrs:{sortable:"","show-overflow-tooltip":!0,prop:"positionLevel",width:"120px",label:"现任职级"}}),t._v(" "),a("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"politicalStatus",width:"100px",label:"编制类型"}})],1),t._v(" "),a("el-pagination",{staticStyle:{"margin-top":"2px",float:"right"},attrs:{total:t.total,"current-page":t.page+1,layout:"total, prev, pager, next, jumper, sizes"},on:{"size-change":t.sizeChange,"current-change":t.pageChange}})],1)},staticRenderFns:[]};var s=a("VU/8")(i,n,!1,function(t){a("3YB/")},"data-v-28e24210",null);e.default=s.exports}});