webpackJsonp([7,26],{"9diA":function(e,t){},OuLH:function(e,t){},RRDt:function(e,t,l){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=l("s3ol"),o=l("Ks07"),i=l("mHAX"),r=l("gN++"),n=["单位隶属关系","组织机构编码","单位级别","所属序列","所在政区代码","行政编制数","事业编制数（参公）","厅局级正职领导职数","厅局级副职领导职数","县处级正职领导职数","县处级副职领导职数","一级巡视员职数","二级巡视员职数","一级调研员职数","二级调研员职数","三级调研员职数","四级调研员职数","一级主任科员职数","二级主任科员职数","三级主任科员职数","四级主任科员职数","参照公务员法管理审批文号","参照公务员法管理审批时间","备注","排序","状态"],s={components:{eForm:l("V1V/").default,treeTable:a.a},mixins:[o.a,i.a],props:{defaultExpandAll:{type:Boolean,default:function(){return!0}}},data:function(){return{loading:!1,enabledTypeOptions:[{key:"0",display_name:"正常"},{key:"1",display_name:"禁用"}],delLoading:!1,downloadLoading:!1,expand:!0,excelLoading:!1,exceldialog:!1,queryInline:{name:"",enabled:""},colData:[{title:"单位隶属关系",istrue:!0},{title:"组织机构编码",istrue:!0},{title:"单位级别",istrue:!0},{title:"所属序列",istrue:!0},{title:"所在政区代码",istrue:!0},{title:"行政编制数",istrue:!0},{title:"事业编制数（参公）",istrue:!0},{title:"厅局级正职领导职数",istrue:!0},{title:"厅局级副职领导职数",istrue:!0},{title:"县处级正职领导职数",istrue:!0},{title:"县处级副职领导职数",istrue:!0},{title:"一级巡视员职数",istrue:!0},{title:"二级巡视员职数",istrue:!0},{title:"一级调研员职数",istrue:!0},{title:"二级调研员职数",istrue:!0},{title:"三级调研员职数",istrue:!0},{title:"四级调研员职数",istrue:!0},{title:"一级主任科员职数",istrue:!0},{title:"二级主任科员职数",istrue:!0},{title:"三级主任科员职数",istrue:!0},{title:"四级主任科员职数",istrue:!0},{title:"参照公务员法管理审批文号",istrue:!0},{title:"参照公务员法管理审批时间",istrue:!0},{title:"备注",istrue:!0},{title:"排序",istrue:!0},{title:"状态",istrue:!0}],colOptions:[],colSelect:[],visible:!1,checkAll:!1,cities:n,isIndeterminate:!0}},updated:function(){this.expandAll()},created:function(){var e=this;this.$nextTick(function(){e.getUnitDatas()});for(var t=0;t<this.colData.length;t++)this.colSelect.push(this.colData[t].title),"名称"!=this.colData[t].title&&this.colOptions.push(this.colData[t].title)},watch:{colOptions:function(e){var t=this.colSelect.filter(function(t){return e.indexOf(t)<0});this.expandAll(),this.colData.filter(function(e){-1!=t.indexOf(e.title)?e.istrue=!1:e.istrue=!0})}},methods:{unFoldAll:function(){for(var e=this.$el.getElementsByClassName("el-table__expand-icon"),t=0;t<e.length/2;t++)e[t].click()},expandAll:function(){for(var e=this.$el.getElementsByClassName("el-table__expand-icon"),t=0;t<e.length/2;t++)e[t].click()},getUnitDatas:function(){var e=this,t=this.queryInline,l={name:t.name,enabled:t.enabled};Object(r.h)(l).then(function(t){e.data=t.result,e.loading=!1}),this.expand=!0,this.expandAll()},add:function(){this.isAdd=!0;var e=this.$refs.form;e.getUnits(),e.dialog=!0},changeExpand:function(){this.expand=!this.expand;for(var e=this.$el.getElementsByClassName("el-table__expand-icon"),t=0;t<e.length;t++)e[t].click()},edit:function(e){this.isAdd=!1;var t=this.$refs.form;t.getUnits(),t.form={id:e.id,name:e.name,parentId:e.parentId,code:e.code,level:e.level,areaStrs:e.areaStrs,affiliation:e.affiliation,category:e.category,referOfficialDocument:e.referOfficialDocument,referOfficialDate:e.referOfficialDate,officialNum:e.officialNum,referOfficialNum:e.referOfficialNum,mainHallNum:e.mainHallNum,deputyHallNum:e.deputyHallNum,rightPlaceNum:e.rightPlaceNum,detail:e.detail,deputyPlaceNum:e.deputyPlaceNum,oneInspectorNum:e.oneInspectorNum,towInspectorNum:e.towInspectorNum,oneResearcherNum:e.oneResearcherNum,towResearcherNum:e.towResearcherNum,threeResearcherNum:e.threeResearcherNum,fourResearcherNum:e.fourResearcherNum,oneClerkNum:e.oneClerkNum,towClerkNum:e.towClerkNum,threeClerkNum:e.threeClerkNum,fourClerkNum:e.fourClerkNum,iframe:"false",enabled:e.enabled.toString()},t.dialog=!0},subDelete:function(e){var t=this;this.delLoading=!0;var l={id:e};Object(r.b)(l).then(function(l){t.delLoading=!1,t.$refs[e].doClose(),t.getUnitDatas(),t.$notify({title:"删除成功",type:"success",duration:2500})}).catch(function(l){t.delLoading=!1,t.$refs[e].doClose(),console.log(l.response.data.message)})},handleCheckAllChange:function(e){this.colOptions=e?n:[],this.isIndeterminate=!1},handlecolOptionsChange:function(e){var t=e.length;this.checkAll=t===this.cities.length,this.isIndeterminate=t>0&&t<this.cities.length},download:function(){this.$axios({method:"post",url:"http://152.136.134.221:8085/api/unit/outExcel",contentType:"application/x-www-form-urlencoded",responseType:"blob"}).then(function(e){console.log(e);var t=document.createElement("a"),l=new Blob([e.data],{type:"application/vnd.ms-excel"});t.style.display="none",t.href=URL.createObjectURL(l),t.setAttribute("download","单位信息表导出.xls"),document.body.appendChild(t),t.click(),document.body.removeChild(t)}).catch(function(e){console.log(e)})},uploadSuccess:function(e,t){if(200!=e.code)return this.excelLoading=!1,this.$notify({title:"文件导入失败"+e.msg,type:"error",duration:25e3}),t;this.excelLoading=!1,this.$notify({title:"文件导入成功"+e.msg,type:"success",duration:25e3}),this.exceldialog=!1,this.getUnitDatas()},handleError:function(e,t){var l=JSON.parse(e.message);this.$notify({title:l.message,type:"error",duration:2500})},beforeAvatarUpload:function(e){var t="xls"===e.name.split(".")[1],l="xlsx"===e.name.split(".")[1];return t||l||this.$message.error("上传w文件只能是 xls、xlsx格式!"),t||l},handleExceed:function(e){this.$message.warning("当前限制选择 1 个文件，请删除后继续上传")},submitUpload:function(){this.excelLoading=!0,this.$refs.upload.submit()},uploadUrl:function(){return"http://152.136.134.221:8085/api/unit/import"},handleRemove:function(e){console.log(e)},handlePreview:function(e){console.log(e)}}},c={render:function(){var e=this,t=e.$createElement,l=e._self._c||t;return l("div",{staticClass:"app-container"},[l("div",{staticClass:"head-container"},[l("el-input",{staticClass:"filter-item",staticStyle:{width:"200px"},attrs:{clearable:"",placeholder:"输入单位名称搜索"},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.getUnitDatas(t)}},model:{value:e.queryInline.name,callback:function(t){e.$set(e.queryInline,"name",t)},expression:"queryInline.name"}}),e._v(" "),l("el-select",{staticClass:"filter-item",staticStyle:{width:"90px"},attrs:{clearable:"",placeholder:"状态"},on:{change:e.getUnitDatas},model:{value:e.queryInline.enabled,callback:function(t){e.$set(e.queryInline,"enabled",t)},expression:"queryInline.enabled"}},e._l(e.enabledTypeOptions,function(e){return l("el-option",{key:e.key,attrs:{label:e.display_name,value:e.key}})}),1),e._v(" "),l("el-button",{staticClass:"filter-item",attrs:{size:"mini",type:"success",icon:"el-icon-search"},on:{click:e.getUnitDatas}},[e._v("搜索")]),e._v(" "),l("div",{staticStyle:{display:"inline-block",margin:"0px 2px"}},[l("el-button",{staticClass:"filter-item",attrs:{size:"mini",type:"primary",icon:"el-icon-plus"},on:{click:e.add}},[e._v("新增")])],1),e._v(" "),l("div",{staticStyle:{display:"inline-block"}},[l("el-button",{staticClass:"filter-item",attrs:{size:"mini",type:"warning",icon:"el-icon-more"},on:{click:e.changeExpand}},[e._v(e._s(e.expand?"展开":"折叠"))]),e._v(" "),l("eForm",{ref:"form",attrs:{"is-add":!0,dicts:e.dicts}})],1),e._v(" "),l("div",{staticStyle:{display:"inline-block"}},[l("el-button",{staticClass:"filter-item",attrs:{loading:e.downloadLoading,size:"mini",type:"warning",icon:"el-icon-download"},on:{click:e.download}},[e._v("导出")])],1),e._v(" "),l("div",{staticStyle:{display:"inline-block",margin:"0px 2px"}},[l("el-button",{staticClass:"filter-item",attrs:{size:"mini",type:"primary",icon:"el-icon-upload"},on:{click:function(t){e.exceldialog=!0}}},[e._v("上传Excel")])],1),e._v(" "),l("el-dialog",{attrs:{visible:e.exceldialog,"append-to-body":"",width:"500px"},on:{"update:visible":function(t){e.exceldialog=t}}},[l("el-upload",{ref:"upload",staticClass:"upload-demo",attrs:{drag:"",action:e.uploadUrl(),loading:e.excelLoading,limit:1,"on-exceed":e.handleExceed,name:"excelFile","on-preview":e.handlePreview,"on-remove":e.handleRemove,"on-error":e.handleError,"on-success":e.uploadSuccess,"auto-upload":!1,"before-upload":e.beforeAvatarUpload}},[l("i",{staticClass:"el-icon-upload"}),e._v(" "),l("div",{staticClass:"el-upload__text"},[e._v("将文件拖到此处，或"),l("em",[e._v("点击上传")])]),e._v(" "),l("div",{staticClass:"el-upload__tip",attrs:{slot:"tip"},slot:"tip"},[e._v("一次只能上传一个文件，仅限Excel格式")])]),e._v(" "),l("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[l("el-button",{attrs:{type:"primary"},on:{click:e.submitUpload}},[e._v("确认")])],1)],1),e._v(" "),l("div",{staticStyle:{display:"inline-block",margin:"0px 2px",float:"right"}},[l("el-popover",{attrs:{placement:"right",width:"200",trigger:"click"},model:{value:e.visible,callback:function(t){e.visible=t},expression:"visible"}},[l("el-checkbox",{attrs:{indeterminate:e.isIndeterminate},on:{change:e.handleCheckAllChange},model:{value:e.checkAll,callback:function(t){e.checkAll=t},expression:"checkAll"}},[e._v("全选")]),e._v(" "),l("div",{staticStyle:{margin:"15px 0"}}),e._v(" "),l("el-checkbox-group",{on:{change:e.handlecolOptionsChange},model:{value:e.colOptions,callback:function(t){e.colOptions=t},expression:"colOptions"}},e._l(e.cities,function(t){return l("el-checkbox",{key:t,attrs:{label:t}},[e._v(e._s(t))])}),1),e._v(" "),l("el-button",{attrs:{slot:"reference",type:"primary",icon:"el-icon-edit"},slot:"reference"},[e._v("显示/隐藏表头")])],1)],1)],1),e._v(" "),l("eForm",{ref:"form",attrs:{"is-add":e.isAdd,dicts:e.dicts}}),e._v(" "),l("div",[l("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],staticStyle:{width:"100%","margin-bottom":"20px"},attrs:{data:e.data,"row-key":"id",border:"","default-expand-all":""}},[l("el-table-column",{attrs:{type:"index",width:"50"}}),e._v(" "),l("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"name",label:"名称",width:"200%"}}),e._v(" "),e.colData[0].istrue?l("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"affiliation",label:"单位隶属关系"}}):e._e(),e._v(" "),e.colData[1].istrue?l("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"code",label:"组织机构编码"}}):e._e(),e._v(" "),e.colData[2].istrue?l("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"level",label:"单位级别"}}):e._e(),e._v(" "),e.colData[3].istrue?l("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"category",label:"所属序列"}}):e._e(),e._v(" "),e.colData[4].istrue?l("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"area",label:"所在政区代码"}}):e._e(),e._v(" "),e.colData[5].istrue?l("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"officialNum",label:"行政编制数"}}):e._e(),e._v(" "),e.colData[6].istrue?l("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"referOfficialNum",label:"事业编制数（参公）"}}):e._e(),e._v(" "),e.colData[7].istrue?l("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"mainHallNum",label:"厅局级正职领导职数"}}):e._e(),e._v(" "),e.colData[8].istrue?l("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"deputyHallNum",label:"厅局级副职领导职数"}}):e._e(),e._v(" "),e.colData[9].istrue?l("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"rightPlaceNum",label:"县处级正职领导职数"}}):e._e(),e._v(" "),e.colData[10].istrue?l("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"deputyPlaceNum",label:"县处级副职领导职数"}}):e._e(),e._v(" "),e.colData[11].istrue?l("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"oneInspectorNum",label:"一级巡视员职数"}}):e._e(),e._v(" "),e.colData[12].istrue?l("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"towInspectorNum",label:"二级巡视员职数"}}):e._e(),e._v(" "),e.colData[13].istrue?l("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"oneResearcherNum",label:"一级调研员职数"}}):e._e(),e._v(" "),e.colData[14].istrue?l("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"towResearcherNum",label:"二级调研员职数"}}):e._e(),e._v(" "),e.colData[15].istrue?l("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"threeResearcherNum",label:"三级调研员职数"}}):e._e(),e._v(" "),e.colData[16].istrue?l("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"fourResearcherNum",label:"四级调研员职数"}}):e._e(),e._v(" "),e.colData[17].istrue?l("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"oneClerkNum",label:"一级主任科员职数"}}):e._e(),e._v(" "),e.colData[18].istrue?l("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"towClerkNum",label:"二级主任科员职数"}}):e._e(),e._v(" "),e.colData[19].istrue?l("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"threeClerkNum",label:"三级主任科员职数"}}):e._e(),e._v(" "),e.colData[20].istrue?l("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"fourClerkNum",label:"四级主任科员职数"}}):e._e(),e._v(" "),e.colData[21].istrue?l("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"referOfficialDocument",label:"参照公务员法管理审批文号"}}):e._e(),e._v(" "),e.colData[22].istrue?l("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"referOfficialDate",label:"参照公务员法管理审批时间"}}):e._e(),e._v(" "),e.colData[23].istrue?l("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"detail",label:"备注"}}):e._e(),e._v(" "),e.colData[24].istrue?l("el-table-column",{attrs:{"show-overflow-tooltip":!0,prop:"affiliation",label:"排序"}}):e._e(),e._v(" "),e.colData[25].istrue?l("el-table-column",{attrs:{"show-overflow-tooltip":!0,label:"状态",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return e._l(e.enabledTypeOptions,function(a){return l("div",{key:a.key},[t.row.enabled.toString()===a.key?l("el-tag",{attrs:{type:t.row.enabled?"":"info"}},[e._v(e._s(a.display_name))]):e._e()],1)})}}],null,!1,1894161505)}):e._e(),e._v(" "),l("el-table-column",{attrs:{prop:"tag",fixed:"right",label:"操作",width:"130px",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[l("el-button",{attrs:{size:"mini",type:"primary",icon:"el-icon-edit"},on:{click:function(l){return e.edit(t.row)}}}),e._v(" "),l("el-popover",{ref:t.row.id,attrs:{placement:"top",width:"180"}},[l("p",[e._v("确定删除本条数据吗？")]),e._v(" "),l("div",{staticStyle:{"text-align":"right",margin:"0"}},[l("el-button",{attrs:{size:"mini",type:"text"},on:{click:function(l){e.$refs[t.row.id].doClose()}}},[e._v("取消")]),e._v(" "),l("el-button",{attrs:{loading:e.delLoading,type:"primary",size:"mini"},on:{click:function(l){return e.subDelete(t.row.id)}}},[e._v("确定")])],1),e._v(" "),l("el-button",{attrs:{slot:"reference",disabled:1===t.row.id,type:"danger",icon:"el-icon-delete",size:"mini"},slot:"reference"})],1)]}}])})],1)],1)],1)},staticRenderFns:[]};var u=l("VU/8")(s,c,!1,function(e){l("OuLH")},"data-v-56601a6c",null);t.default=u.exports},"V1V/":function(e,t,l){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=l("gN++"),o=l("GXH0"),i=l.n(o),r=l("EAjQ"),n=(l("DmJO"),{components:{Treeselect:i.a,IconSelect:r.a},props:{isAdd:{type:Boolean,required:!0},dicts:{type:Array,required:!0}},data:function(){return{loading:!1,dialog:!1,units:[],areas:[],levels:[],affiliations:[],categorys:[],form:{id:"",name:"",parentId:"d21adc3949ba59abbe56e057f20f456e",enabled:"1",level:"",areaStrs:[],affiliation:"",category:"",referOfficialDocument:"",referOfficialDate:"",officialNum:"",referOfficialNum:"",mainHallNum:"",deputyHallNum:"",rightPlaceNum:"",deputyPlaceNum:"",oneInspectorNum:"",towInspectorNum:"",oneResearcherNum:"",towResearcherNum:"",threeResearcherNum:"",fourResearcherNum:"",oneClerkNum:"",towClerkNum:"",threeClerkNum:"",fourClerkNum:"",detail:""},enabledTypeOptions:[{key:"1",display_name:"正常"},{key:"0",display_name:"禁用"}],rules:{name:[{required:!0,message:"请输入名称",trigger:"blur"}],code:[{required:!0,message:"请输入编码",trigger:"blur"}]}}},methods:{getCitys:function(){var e=this;Object(a.e)().then(function(t){e.areas=t.result})},getLevels:function(){var e=this;Object(a.g)().then(function(t){e.levels=t.result})},getAffiliations:function(){var e=this;Object(a.d)().then(function(t){e.affiliations=t.result})},getCategorys:function(){var e=this;Object(a.f)().then(function(t){e.categorys=t.result})},cancel:function(){this.resetForm()},doSubmit:function(){var e=this;this.$refs.form.validate(function(t){t&&(e.loading=!0,void 0!==e.form.parentId?(e.loading=!1,e.isAdd?e.doAdd():e.doEdit()):(e.$message({message:"上级部门不能为空",type:"warning"}),e.loading=!1))})},doAdd:function(){var e=this;Object(a.a)(this.form).then(function(t){e.resetForm(),e.$notify({title:"添加成功",type:"success",duration:2500}),e.loading=!1,e.$parent.getUnitDatas()}).catch(function(t){e.loading=!1,console.log(t.response.data.message)})},doEdit:function(){var e=this;Object(a.c)(this.form).then(function(t){e.resetForm(),e.$notify({title:"修改成功",type:"success",duration:2500}),e.loading=!1,e.$parent.getUnitDatas()}).catch(function(t){e.loading=!1,console.log(t.response.data.message)})},resetForm:function(){this.dialog=!1,this.$refs.form.resetFields(),this.form={id:"",name:"",parentId:"d21adc3949ba59abbe56e057f20f456e",enabled:"0"},this.iframe="true"},getUnits:function(){var e=this;Object(a.h)({enabled:"0"}).then(function(t){e.units=t.result})}},created:function(){this.getCitys(),this.getLevels(),this.getAffiliations(),this.getCategorys()}}),s={render:function(){var e=this,t=e.$createElement,l=e._self._c||t;return l("el-dialog",{attrs:{"append-to-body":!0,visible:e.dialog,title:e.isAdd?"新增单位":"编辑单位",width:"70%"},on:{"update:visible":function(t){e.dialog=t}}},[l("el-form",{ref:"form",staticStyle:{margin:"0px"},attrs:{model:e.form,rules:e.rules,size:"small"}},[l("el-row",[l("el-col",{attrs:{span:12}},[l("el-form-item",{attrs:{label:"上级单位","label-width":"140px"}},[l("treeselect",{attrs:{options:e.units,placeholder:"搜索：输入关键字或直接选择"},model:{value:e.form.parentId,callback:function(t){e.$set(e.form,"parentId",t)},expression:"form.parentId"}})],1)],1)],1),e._v(" "),l("el-row",[l("el-col",{attrs:{span:8}},[l("el-form-item",{attrs:{label:"机构名称",prop:"name",float:"left"}},[l("el-input",{staticStyle:{width:"50%"},model:{value:e.form.name,callback:function(t){e.$set(e.form,"name",t)},expression:"form.name"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:8}},[l("el-form-item",{attrs:{label:"组织机构编码",prop:"code",float:"right"}},[l("el-input",{staticStyle:{width:"50%"},model:{value:e.form.code,callback:function(t){e.$set(e.form,"code",t)},expression:"form.code"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:8}},[l("el-form-item",{attrs:{label:"隶属关系",prop:"affiliation"}},[l("el-select",{staticStyle:{width:"50%"},attrs:{filterable:"",placeholder:"请选择"},model:{value:e.form.affiliation,callback:function(t){e.$set(e.form,"affiliation",t)},expression:"form.affiliation"}},e._l(e.affiliations,function(e){return l("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}),1)],1)],1)],1),e._v(" "),l("el-row",[l("el-col",{attrs:{span:8}},[l("el-form-item",{attrs:{label:"所在政区",prop:"area"}},[l("el-cascader",{staticStyle:{width:"50%"},attrs:{placeholder:"搜索：指南",options:e.areas,filterable:""},model:{value:e.form.areaStrs,callback:function(t){e.$set(e.form,"areaStrs",t)},expression:"form.areaStrs"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:8}},[l("el-form-item",{attrs:{label:"机构级别",prop:"level"}},[l("el-select",{staticStyle:{width:"50%"},attrs:{filterable:"",placeholder:"请选择"},model:{value:e.form.level,callback:function(t){e.$set(e.form,"level",t)},expression:"form.level"}},e._l(e.levels,function(e){return l("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}),1)],1)],1),e._v(" "),l("el-col",{attrs:{span:8}},[l("el-form-item",{attrs:{label:"所属序列",prop:"category"}},[l("el-select",{staticStyle:{width:"50%"},attrs:{filterable:"",placeholder:"请选择"},model:{value:e.form.category,callback:function(t){e.$set(e.form,"category",t)},expression:"form.category"}},e._l(e.categorys,function(e){return l("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}),1)],1)],1)],1),e._v(" "),l("el-row",[l("el-col",{attrs:{span:12}},[l("el-form-item",{attrs:{label:"参照公务员法管理审批时间",prop:"referOfficialDate"}},[l("el-date-picker",{staticStyle:{width:"50%"},attrs:{type:"date",placeholder:"选择日期"},model:{value:e.form.referOfficialDate,callback:function(t){e.$set(e.form,"referOfficialDate",t)},expression:"form.referOfficialDate"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:12}},[l("el-form-item",{attrs:{label:"参照公务员法管理审批文号",prop:"referOfficialDocument"}},[l("el-input",{staticStyle:{width:"50%"},model:{value:e.form.referOfficialDocument,callback:function(t){e.$set(e.form,"referOfficialDocument",t)},expression:"form.referOfficialDocument"}})],1)],1)],1),e._v(" "),l("br"),e._v(" "),l("fieldset",{staticStyle:{width:"30%","border-color":"#b5b8c8",float:"left"}},[l("legend",{staticStyle:{"text-align":"center"}},[l("span",[e._v("编制数"),l("br"),e._v("实有人数")])]),e._v(" "),l("el-form-item",{attrs:{label:"行政编制数",prop:"officialNum"}},[l("el-input",{staticStyle:{width:"50%"},model:{value:e.form.officialNum,callback:function(t){e.$set(e.form,"officialNum",t)},expression:"form.officialNum"}})],1),e._v(" "),l("el-form-item",{attrs:{label:"事业编制数（参公）",prop:"referOfficialNum"}},[l("el-input",{staticStyle:{width:"20%"},model:{value:e.form.referOfficialNum,callback:function(t){e.$set(e.form,"referOfficialNum",t)},expression:"form.referOfficialNum"}})],1)],1),e._v(" "),l("fieldset",{staticStyle:{width:"67%","border-color":"#b5b8c8",float:"left"}},[l("legend",{staticStyle:{"text-align":"center"}},[l("span",[e._v("领导"),l("br"),e._v("职数")])]),e._v(" "),l("el-row",[l("el-col",{attrs:{span:8}},[l("el-form-item",{attrs:{"label-width":"140px",label:"厅局级正职领导职数",prop:"mainHallNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.mainHallNum,callback:function(t){e.$set(e.form,"mainHallNum",t)},expression:"form.mainHallNum"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:8}},[l("el-form-item",{attrs:{"label-width":"140px",label:"厅局级副职领导职数",prop:"deputyHallNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.deputyHallNum,callback:function(t){e.$set(e.form,"deputyHallNum",t)},expression:"form.deputyHallNum"}})],1)],1)],1),e._v(" "),l("el-row",[l("el-col",{attrs:{span:8}},[l("el-form-item",{attrs:{"label-width":"140px",label:"县处级正职领导职数",prop:"rightPlaceNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.rightPlaceNum,callback:function(t){e.$set(e.form,"rightPlaceNum",t)},expression:"form.rightPlaceNum"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:8}},[l("el-form-item",{attrs:{"label-width":"140px",label:"县处级副职领导职数",prop:"deputyPlaceNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.deputyPlaceNum,callback:function(t){e.$set(e.form,"deputyPlaceNum",t)},expression:"form.deputyPlaceNum"}})],1)],1)],1)],1),e._v(" "),l("fieldset",{staticStyle:{width:"100%","border-color":"#b5b8c8"}},[l("legend",{staticStyle:{"text-align":"center"}},[l("span",[e._v("职级数")])]),e._v(" "),l("el-row",[l("el-col",{attrs:{span:6}},[l("el-form-item",{attrs:{"label-width":"120px",label:"一级巡视员职数",prop:"oneInspectorNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.oneInspectorNum,callback:function(t){e.$set(e.form,"oneInspectorNum",t)},expression:"form.oneInspectorNum"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:6}},[l("el-form-item",{attrs:{label:"二级巡视员职数","label-width":"120px",prop:"towInspectorNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.towInspectorNum,callback:function(t){e.$set(e.form,"towInspectorNum",t)},expression:"form.towInspectorNum"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:6}},[l("el-form-item",{attrs:{label:"一级调研员职数","label-width":"120px",prop:"oneResearcherNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.oneResearcherNum,callback:function(t){e.$set(e.form,"oneResearcherNum",t)},expression:"form.oneResearcherNum"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:6}},[l("el-form-item",{attrs:{"label-width":"120px",label:"二级调研员职数",prop:"towResearcherNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.towResearcherNum,callback:function(t){e.$set(e.form,"towResearcherNum",t)},expression:"form.towResearcherNum"}})],1)],1)],1),e._v(" "),l("el-row",[l("el-col",{attrs:{span:6}},[l("el-form-item",{attrs:{"label-width":"120px",label:"三级调研员职数",prop:"threeResearcherNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.threeResearcherNum,callback:function(t){e.$set(e.form,"threeResearcherNum",t)},expression:"form.threeResearcherNum"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:6}},[l("el-form-item",{attrs:{"label-width":"120px",label:"四级调研员职数",prop:"fourResearcherNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.fourResearcherNum,callback:function(t){e.$set(e.form,"fourResearcherNum",t)},expression:"form.fourResearcherNum"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:6}},[l("el-form-item",{attrs:{"label-width":"140px",label:"一级主任科员职数",prop:"oneClerkNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.oneClerkNum,callback:function(t){e.$set(e.form,"oneClerkNum",t)},expression:"form.oneClerkNum"}})],1)],1)],1),e._v(" "),l("el-row",[l("el-col",{attrs:{span:6}},[l("el-form-item",{attrs:{"label-width":"140px",label:"二级主任科员职数",prop:"towClerkNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.towClerkNum,callback:function(t){e.$set(e.form,"towClerkNum",t)},expression:"form.towClerkNum"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:6}},[l("el-form-item",{attrs:{"label-width":"140px",label:"三级主任科员职数",prop:"threeClerkNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.threeClerkNum,callback:function(t){e.$set(e.form,"threeClerkNum",t)},expression:"form.threeClerkNum"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:6}},[l("el-form-item",{attrs:{"label-width":"140px",label:"四级主任科员职数",prop:"fourClerkNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.fourClerkNum,callback:function(t){e.$set(e.form,"fourClerkNum",t)},expression:"form.fourClerkNum"}})],1)],1)],1)],1),e._v(" "),l("el-form-item",{attrs:{label:"备注",prop:"detail"}},[l("el-input",{staticStyle:{width:"80%"},attrs:{type:"textarea"},model:{value:e.form.detail,callback:function(t){e.$set(e.form,"detail",t)},expression:"form.detail"}})],1)],1),e._v(" "),l("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[l("el-button",{attrs:{type:"text"},on:{click:e.cancel}},[e._v("取消")]),e._v(" "),l("el-button",{attrs:{loading:e.loading,type:"primary"},on:{click:e.doSubmit}},[e._v("确认")])],1)],1)},staticRenderFns:[]};var c=l("VU/8")(n,s,!1,function(e){l("9diA")},"data-v-38f3c769",null);t.default=c.exports}});