webpackJsonp([25],{"/YGe":function(e,t){},"GW/u":function(e,t,o){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r=o("CV50"),n=o("iVC1"),i=o("GXH0"),a=o.n(i),s=(o("DmJO"),{components:{Treeselect:a.a},props:{isAdd:{type:Boolean,required:!0},dicts:{type:Array,required:!0}},data:function(){return{loading:!1,dialog:!1,depts:[],deptId:null,form:{id:"",name:"",sort:999,enabled:"1",createTime:"",deptId:""},rules:{name:[{required:!0,message:"请输入名称",trigger:"blur"}],sort:[{required:!0,message:"请输入序号",trigger:"blur",type:"number"}]}}},methods:{cancel:function(){this.resetForm()},doSubmit:function(){var e=this;this.form.deptId=this.deptId,this.$refs.form.validate(function(t){t&&(null===e.deptId||void 0===e.deptId?e.$message({message:"所属部门不能为空",type:"warning"}):(e.loading=!0,e.isAdd?e.doAdd():e.doEdit()))})},doAdd:function(){var e=this;Object(n.a)(this.form).then(function(t){e.resetForm(),e.$notify({title:"添加成功",type:"success",duration:2500}),e.loading=!1,e.$parent.init()}).catch(function(t){e.loading=!1,console.log(t.response.data.message)})},doEdit:function(){var e=this;Object(n.c)(this.form).then(function(t){e.resetForm(),e.$notify({title:"修改成功",type:"success",duration:2500}),e.loading=!1,e.$parent.init()}).catch(function(t){e.loading=!1,console.log(t.response.data.message)})},resetForm:function(){this.dialog=!1,this.$refs.form.resetFields(),this.deptId=null,this.form={id:"",name:"",sort:999,enabled:"true",createTime:"",deptId:""}},getDepts:function(){var e=this;Object(r.d)().then(function(t){e.depts=t.result})}}}),l={render:function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("el-dialog",{attrs:{"append-to-body":!0,visible:e.dialog,title:e.isAdd?"新增岗位":"编辑岗位",width:"500px"},on:{"update:visible":function(t){e.dialog=t}}},[o("el-form",{ref:"form",attrs:{model:e.form,rules:e.rules,size:"small","label-width":"80px"}},[o("el-form-item",{attrs:{label:"名称",prop:"name"}},[o("el-input",{staticStyle:{width:"370px"},model:{value:e.form.name,callback:function(t){e.$set(e.form,"name",t)},expression:"form.name"}})],1),e._v(" "),o("el-form-item",{attrs:{label:"排序",prop:"sort"}},[o("el-input-number",{staticStyle:{width:"370px"},attrs:{min:0,max:999,"controls-position":"right"},model:{value:e.form.sort,callback:function(t){e.$set(e.form,"sort",e._n(t))},expression:"form.sort"}})],1),e._v(" "),0!==e.form.pid?o("el-form-item",{attrs:{label:"状态",prop:"enabled"}},[o("el-radio",{key:1,attrs:{label:1},model:{value:e.form.enabled,callback:function(t){e.$set(e.form,"enabled",t)},expression:"form.enabled"}},[e._v("正常")]),e._v(" "),o("el-radio",{key:0,attrs:{label:0},model:{value:e.form.enabled,callback:function(t){e.$set(e.form,"enabled",t)},expression:"form.enabled"}},[e._v("停用")])],1):e._e(),e._v(" "),o("el-form-item",{attrs:{label:"所属部门"}},[o("treeselect",{staticStyle:{width:"370px"},attrs:{options:e.depts,placeholder:"选择部门"},model:{value:e.deptId,callback:function(t){e.deptId=t},expression:"deptId"}})],1)],1),e._v(" "),o("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[o("el-button",{attrs:{type:"text"},on:{click:e.cancel}},[e._v("取消")]),e._v(" "),o("el-button",{attrs:{loading:e.loading,type:"primary"},on:{click:e.doSubmit}},[e._v("确认")])],1)],1)},staticRenderFns:[]};var d=o("VU/8")(s,l,!1,function(e){o("/YGe")},"data-v-cd196bdc",null);t.default=d.exports},iVC1:function(e,t,o){"use strict";t.a=function(e){return Object(r.a)({url:"api/job",method:"post",data:e})},t.b=function(e){return Object(r.a)({url:"api/job/"+e,method:"delete"})},t.c=function(e){return Object(r.a)({url:"api/job",method:"put",data:e})};var r=o("vLgD")}});