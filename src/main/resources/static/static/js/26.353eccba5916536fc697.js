webpackJsonp([26],{"9diA":function(e,t){},"V1V/":function(e,t,l){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r=l("gN++"),a=l("GXH0"),o=l.n(a),i=l("EAjQ"),s=(l("DmJO"),{components:{Treeselect:o.a,IconSelect:i.a},props:{isAdd:{type:Boolean,required:!0},dicts:{type:Array,required:!0}},data:function(){return{loading:!1,dialog:!1,units:[],areas:[],levels:[],affiliations:[],categorys:[],form:{id:"",name:"",parentId:"d21adc3949ba59abbe56e057f20f456e",enabled:"1",level:"",areaStrs:[],affiliation:"",category:"",referOfficialDocument:"",referOfficialDate:"",officialNum:"",referOfficialNum:"",mainHallNum:"",deputyHallNum:"",rightPlaceNum:"",deputyPlaceNum:"",oneInspectorNum:"",towInspectorNum:"",oneResearcherNum:"",towResearcherNum:"",threeResearcherNum:"",fourResearcherNum:"",oneClerkNum:"",towClerkNum:"",threeClerkNum:"",fourClerkNum:"",detail:""},enabledTypeOptions:[{key:"1",display_name:"正常"},{key:"0",display_name:"禁用"}],rules:{name:[{required:!0,message:"请输入名称",trigger:"blur"}],code:[{required:!0,message:"请输入编码",trigger:"blur"}]}}},methods:{getCitys:function(){var e=this;Object(r.e)().then(function(t){e.areas=t.result})},getLevels:function(){var e=this;Object(r.g)().then(function(t){e.levels=t.result})},getAffiliations:function(){var e=this;Object(r.d)().then(function(t){e.affiliations=t.result})},getCategorys:function(){var e=this;Object(r.f)().then(function(t){e.categorys=t.result})},cancel:function(){this.resetForm()},doSubmit:function(){var e=this;this.$refs.form.validate(function(t){t&&(e.loading=!0,void 0!==e.form.parentId?(e.loading=!1,e.isAdd?e.doAdd():e.doEdit()):(e.$message({message:"上级部门不能为空",type:"warning"}),e.loading=!1))})},doAdd:function(){var e=this;Object(r.a)(this.form).then(function(t){e.resetForm(),e.$notify({title:"添加成功",type:"success",duration:2500}),e.loading=!1,e.$parent.getUnitDatas()}).catch(function(t){e.loading=!1,console.log(t.response.data.message)})},doEdit:function(){var e=this;Object(r.c)(this.form).then(function(t){e.resetForm(),e.$notify({title:"修改成功",type:"success",duration:2500}),e.loading=!1,e.$parent.getUnitDatas()}).catch(function(t){e.loading=!1,console.log(t.response.data.message)})},resetForm:function(){this.dialog=!1,this.$refs.form.resetFields(),this.form={id:"",name:"",parentId:"d21adc3949ba59abbe56e057f20f456e",enabled:"0"},this.iframe="true"},getUnits:function(){var e=this;Object(r.h)({enabled:"0"}).then(function(t){e.units=t.result})}},created:function(){this.getCitys(),this.getLevels(),this.getAffiliations(),this.getCategorys()}}),n={render:function(){var e=this,t=e.$createElement,l=e._self._c||t;return l("el-dialog",{attrs:{"append-to-body":!0,visible:e.dialog,title:e.isAdd?"新增单位":"编辑单位",width:"70%"},on:{"update:visible":function(t){e.dialog=t}}},[l("el-form",{ref:"form",staticStyle:{margin:"0px"},attrs:{model:e.form,rules:e.rules,size:"small"}},[l("el-row",[l("el-col",{attrs:{span:12}},[l("el-form-item",{attrs:{label:"上级单位","label-width":"140px"}},[l("treeselect",{attrs:{options:e.units,placeholder:"搜索：输入关键字或直接选择"},model:{value:e.form.parentId,callback:function(t){e.$set(e.form,"parentId",t)},expression:"form.parentId"}})],1)],1)],1),e._v(" "),l("el-row",[l("el-col",{attrs:{span:8}},[l("el-form-item",{attrs:{label:"机构名称",prop:"name",float:"left"}},[l("el-input",{staticStyle:{width:"50%"},model:{value:e.form.name,callback:function(t){e.$set(e.form,"name",t)},expression:"form.name"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:8}},[l("el-form-item",{attrs:{label:"组织机构编码",prop:"code",float:"right"}},[l("el-input",{staticStyle:{width:"50%"},model:{value:e.form.code,callback:function(t){e.$set(e.form,"code",t)},expression:"form.code"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:8}},[l("el-form-item",{attrs:{label:"隶属关系",prop:"affiliation"}},[l("el-select",{staticStyle:{width:"50%"},attrs:{filterable:"",placeholder:"请选择"},model:{value:e.form.affiliation,callback:function(t){e.$set(e.form,"affiliation",t)},expression:"form.affiliation"}},e._l(e.affiliations,function(e){return l("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}),1)],1)],1)],1),e._v(" "),l("el-row",[l("el-col",{attrs:{span:8}},[l("el-form-item",{attrs:{label:"所在政区",prop:"area"}},[l("el-cascader",{staticStyle:{width:"50%"},attrs:{placeholder:"搜索：指南",options:e.areas,filterable:""},model:{value:e.form.areaStrs,callback:function(t){e.$set(e.form,"areaStrs",t)},expression:"form.areaStrs"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:8}},[l("el-form-item",{attrs:{label:"机构级别",prop:"level"}},[l("el-select",{staticStyle:{width:"50%"},attrs:{filterable:"",placeholder:"请选择"},model:{value:e.form.level,callback:function(t){e.$set(e.form,"level",t)},expression:"form.level"}},e._l(e.levels,function(e){return l("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}),1)],1)],1),e._v(" "),l("el-col",{attrs:{span:8}},[l("el-form-item",{attrs:{label:"所属序列",prop:"category"}},[l("el-select",{staticStyle:{width:"50%"},attrs:{filterable:"",placeholder:"请选择"},model:{value:e.form.category,callback:function(t){e.$set(e.form,"category",t)},expression:"form.category"}},e._l(e.categorys,function(e){return l("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}),1)],1)],1)],1),e._v(" "),l("el-row",[l("el-col",{attrs:{span:12}},[l("el-form-item",{attrs:{label:"参照公务员法管理审批时间",prop:"referOfficialDate"}},[l("el-date-picker",{staticStyle:{width:"50%"},attrs:{type:"date",placeholder:"选择日期"},model:{value:e.form.referOfficialDate,callback:function(t){e.$set(e.form,"referOfficialDate",t)},expression:"form.referOfficialDate"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:12}},[l("el-form-item",{attrs:{label:"参照公务员法管理审批文号",prop:"referOfficialDocument"}},[l("el-input",{staticStyle:{width:"50%"},model:{value:e.form.referOfficialDocument,callback:function(t){e.$set(e.form,"referOfficialDocument",t)},expression:"form.referOfficialDocument"}})],1)],1)],1),e._v(" "),l("br"),e._v(" "),l("fieldset",{staticStyle:{width:"30%","border-color":"#b5b8c8",float:"left"}},[l("legend",{staticStyle:{"text-align":"center"}},[l("span",[e._v("编制数"),l("br"),e._v("实有人数")])]),e._v(" "),l("el-form-item",{attrs:{label:"行政编制数",prop:"officialNum"}},[l("el-input",{staticStyle:{width:"50%"},model:{value:e.form.officialNum,callback:function(t){e.$set(e.form,"officialNum",t)},expression:"form.officialNum"}})],1),e._v(" "),l("el-form-item",{attrs:{label:"事业编制数（参公）",prop:"referOfficialNum"}},[l("el-input",{staticStyle:{width:"20%"},model:{value:e.form.referOfficialNum,callback:function(t){e.$set(e.form,"referOfficialNum",t)},expression:"form.referOfficialNum"}})],1)],1),e._v(" "),l("fieldset",{staticStyle:{width:"67%","border-color":"#b5b8c8",float:"left"}},[l("legend",{staticStyle:{"text-align":"center"}},[l("span",[e._v("领导"),l("br"),e._v("职数")])]),e._v(" "),l("el-row",[l("el-col",{attrs:{span:8}},[l("el-form-item",{attrs:{"label-width":"140px",label:"厅局级正职领导职数",prop:"mainHallNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.mainHallNum,callback:function(t){e.$set(e.form,"mainHallNum",t)},expression:"form.mainHallNum"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:8}},[l("el-form-item",{attrs:{"label-width":"140px",label:"厅局级副职领导职数",prop:"deputyHallNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.deputyHallNum,callback:function(t){e.$set(e.form,"deputyHallNum",t)},expression:"form.deputyHallNum"}})],1)],1)],1),e._v(" "),l("el-row",[l("el-col",{attrs:{span:8}},[l("el-form-item",{attrs:{"label-width":"140px",label:"县处级正职领导职数",prop:"rightPlaceNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.rightPlaceNum,callback:function(t){e.$set(e.form,"rightPlaceNum",t)},expression:"form.rightPlaceNum"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:8}},[l("el-form-item",{attrs:{"label-width":"140px",label:"县处级副职领导职数",prop:"deputyPlaceNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.deputyPlaceNum,callback:function(t){e.$set(e.form,"deputyPlaceNum",t)},expression:"form.deputyPlaceNum"}})],1)],1)],1)],1),e._v(" "),l("fieldset",{staticStyle:{width:"100%","border-color":"#b5b8c8"}},[l("legend",{staticStyle:{"text-align":"center"}},[l("span",[e._v("职级数")])]),e._v(" "),l("el-row",[l("el-col",{attrs:{span:6}},[l("el-form-item",{attrs:{"label-width":"120px",label:"一级巡视员职数",prop:"oneInspectorNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.oneInspectorNum,callback:function(t){e.$set(e.form,"oneInspectorNum",t)},expression:"form.oneInspectorNum"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:6}},[l("el-form-item",{attrs:{label:"二级巡视员职数","label-width":"120px",prop:"towInspectorNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.towInspectorNum,callback:function(t){e.$set(e.form,"towInspectorNum",t)},expression:"form.towInspectorNum"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:6}},[l("el-form-item",{attrs:{label:"一级调研员职数","label-width":"120px",prop:"oneResearcherNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.oneResearcherNum,callback:function(t){e.$set(e.form,"oneResearcherNum",t)},expression:"form.oneResearcherNum"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:6}},[l("el-form-item",{attrs:{"label-width":"120px",label:"二级调研员职数",prop:"towResearcherNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.towResearcherNum,callback:function(t){e.$set(e.form,"towResearcherNum",t)},expression:"form.towResearcherNum"}})],1)],1)],1),e._v(" "),l("el-row",[l("el-col",{attrs:{span:6}},[l("el-form-item",{attrs:{"label-width":"120px",label:"三级调研员职数",prop:"threeResearcherNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.threeResearcherNum,callback:function(t){e.$set(e.form,"threeResearcherNum",t)},expression:"form.threeResearcherNum"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:6}},[l("el-form-item",{attrs:{"label-width":"120px",label:"四级调研员职数",prop:"fourResearcherNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.fourResearcherNum,callback:function(t){e.$set(e.form,"fourResearcherNum",t)},expression:"form.fourResearcherNum"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:6}},[l("el-form-item",{attrs:{"label-width":"140px",label:"一级主任科员职数",prop:"oneClerkNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.oneClerkNum,callback:function(t){e.$set(e.form,"oneClerkNum",t)},expression:"form.oneClerkNum"}})],1)],1)],1),e._v(" "),l("el-row",[l("el-col",{attrs:{span:6}},[l("el-form-item",{attrs:{"label-width":"140px",label:"二级主任科员职数",prop:"towClerkNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.towClerkNum,callback:function(t){e.$set(e.form,"towClerkNum",t)},expression:"form.towClerkNum"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:6}},[l("el-form-item",{attrs:{"label-width":"140px",label:"三级主任科员职数",prop:"threeClerkNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.threeClerkNum,callback:function(t){e.$set(e.form,"threeClerkNum",t)},expression:"form.threeClerkNum"}})],1)],1),e._v(" "),l("el-col",{attrs:{span:6}},[l("el-form-item",{attrs:{"label-width":"140px",label:"四级主任科员职数",prop:"fourClerkNum"}},[l("el-input",{staticStyle:{width:"100%"},model:{value:e.form.fourClerkNum,callback:function(t){e.$set(e.form,"fourClerkNum",t)},expression:"form.fourClerkNum"}})],1)],1)],1)],1),e._v(" "),l("el-form-item",{attrs:{label:"备注",prop:"detail"}},[l("el-input",{staticStyle:{width:"80%"},attrs:{type:"textarea"},model:{value:e.form.detail,callback:function(t){e.$set(e.form,"detail",t)},expression:"form.detail"}})],1)],1),e._v(" "),l("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[l("el-button",{attrs:{type:"text"},on:{click:e.cancel}},[e._v("取消")]),e._v(" "),l("el-button",{attrs:{loading:e.loading,type:"primary"},on:{click:e.doSubmit}},[e._v("确认")])],1)],1)},staticRenderFns:[]};var c=l("VU/8")(s,n,!1,function(e){l("9diA")},"data-v-38f3c769",null);t.default=c.exports}});