webpackJsonp([20],{PSIO:function(t,e,o){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var l=o("Foau"),a={name:"index",data:function(){return{completeData:[],loading:!1,downloadLoading:!1,query:{name:""}}},created:function(){this.getCompleteData()},methods:{getRowClass:function(t){t.row,t.column,t.rowIndex,t.columnIndex},getCompleteData:function(){var t=this;this.loading=!0,Object(l.c)().then(function(e){t.completeData=e.result,t.$notify({title:e.msg,type:"success",duration:2500}),t.loading=!1}).catch(function(t){console.log(t.response.data.message)})},toCompleteQuery:function(){var t=this;this.loading=!0;var e={name:this.query.name};Object(l.c)(e).then(function(e){t.completeData=e.result,t.$notify({title:e.msg,type:"success",duration:2500}),t.loading=!1}).catch(function(t){console.log(t.response.data.message)})},downloadComplete:function(){var t={childUnit:localStorage.getItem("childUnit")};this.$axios({method:"post",url:"http://localhost:8085/api/data/outComplete",params:t,contentType:"application/x-www-form-urlencoded",responseType:"blob"}).then(function(t){console.log(t);var e=document.createElement("a"),o=new Blob([t.data],{type:"application/vnd.ms-excel"});e.style.display="none",e.href=URL.createObjectURL(o),e.setAttribute("download","完成职级晋升情况统计表.xls"),document.body.appendChild(e),e.click(),document.body.removeChild(e)}).catch(function(t){console.log(t)})}}},n={render:function(){var t=this,e=t.$createElement,o=t._self._c||e;return o("div",[o("div",{staticClass:"head-container",staticStyle:{"padding-top":"10px"}},[o("el-input",{staticClass:"filter-item",staticStyle:{width:"10%"},attrs:{clearable:"",placeholder:"单位名称"},model:{value:t.query.name,callback:function(e){t.$set(t.query,"name",e)},expression:"query.name"}}),t._v(" "),o("el-button",{staticClass:"filter-item",attrs:{size:"mini",type:"success",icon:"el-icon-search"},on:{click:t.toCompleteQuery}},[t._v("搜索")]),t._v(" "),o("div",{staticStyle:{display:"inline-block"}},[o("el-button",{staticClass:"filter-item",attrs:{loading:t.downloadLoading,size:"mini",type:"warning",icon:"el-icon-download"},on:{click:t.downloadComplete}},[t._v("导出")])],1),t._v(" "),o("span",{staticStyle:{"text-align":"center","margin-left":"30%"}},[t._v("完成职级晋升情况统计表")])],1),t._v(" "),o("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],staticStyle:{width:"100%","font-size":"10px"},attrs:{"row-style":t.getRowClass,"header-row-style":t.getRowClass,"header-cell-style":{"text-align":"center",background:"#ADD8E6",color:"#030303"},"cell-style":{padding:"0px"},data:t.completeData,size:"small","row-key":"id","element-loading-text":"请稍后","highlight-current-row":"","element-loading-spinner":"el-icon-loading",border:""}},[o("el-table-column",{attrs:{type:"index",label:"序号",width:"60"}}),t._v(" "),o("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"name",label:"套改单位"}}),t._v(" "),o("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"firstBatch",label:"完成第一批晋升"}}),t._v(" "),o("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"secondBatch",label:"完成第二批晋升"}}),t._v(" "),o("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"thirdBatch",label:"完成第三批晋升"}}),t._v(" "),o("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"ganBuNum",label:"超职级职数干部数"}}),t._v(" "),o("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"fourBatch",label:"完成第四批晋升"}}),t._v(" "),o("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"fiveBatch",label:"完成第五批晋升"}}),t._v(" "),o("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"sexBatch",label:"完成第六批晋升"}}),t._v(" "),o("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"total",label:"合计"}})],1)],1)},staticRenderFns:[]};var i=o("VU/8")(a,n,!1,function(t){o("oU3j")},"data-v-5128db36",null);e.default=i.exports},oU3j:function(t,e){}});