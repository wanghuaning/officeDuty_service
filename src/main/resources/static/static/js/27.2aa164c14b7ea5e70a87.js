webpackJsonp([27],{FRr9:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var o=r("vMJZ"),l=r("GXH0"),s=r.n(l),a=(r("DmJO"),{components:{Treeselect:s.a},props:{isAdd:{type:Boolean,required:!0},peoples:{type:Array,required:!0}},data:function(){var e=this;return{dialog:!1,loading:!1,isManager:!1,people:"",form:{userAccount:"",newPass:"",userPassword:"",enabled:"1",peopleId:[],unitId:"",permission:""},peopleId:"",dicts:[{value:"0",label:"激活"},{value:"1",label:"锁定"}],permissions:[{value:"0",label:"普通用户"},{value:"1",label:"管理员"}],level:3,rules:{userAccount:[{required:!0,message:"请输入用户名",trigger:"blur"},{min:2,max:20,message:"长度在 2 到 20 个字符",trigger:"blur"}],userPassword:[{required:!0,validator:function(t,r,o){r?e.form.newPass!==r?o(new Error("两次输入的密码不一致")):o():o(new Error("请再次输入密码"))},trigger:"blur"}]}}},created:function(){navigator.userAgent.indexOf("Chrome")>=0?this.style="width: 184px":this.style="width: 172px"},methods:{cancel:function(){this.resetForm()},doSubmit:function(){var e=this;this.$refs.form.validate(function(t){t&&(e.loading=!0,e.isAdd?e.doAdd():e.doEdit())})},doAdd:function(){var e=this;Object(o.a)(this.form).then(function(t){e.resetForm(),e.$notify({title:"添加成功",type:"success",duration:2500}),e.loading=!1,e.$parent.$parent.init()}).catch(function(t){e.loading=!1,console.log(t.response.data.message)})},doEdit:function(){var e=this;Object(o.c)(this.form).then(function(t){e.resetForm(),e.$notify({title:"修改成功",type:"success",duration:2500}),e.loading=!1,e.$parent.$parent.init()}).catch(function(t){e.loading=!1,console.log(t.response.data.message)})},resetForm:function(){this.dialog=!1,this.$refs.form.resetFields(),this.form={username:"",enabled:"false"}}}}),n={render:function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",[r("el-dialog",{attrs:{visible:e.dialog,title:e.isAdd?"新增用户":"编辑用户","append-to-body":"",width:"570px"},on:{"update:visible":function(t){e.dialog=t}}},[r("el-form",{ref:"form",attrs:{inline:!0,model:e.form,rules:e.rules,size:"small","label-width":"88px"}},[r("el-form-item",{attrs:{label:"用户名",prop:"userAccount"}},[r("el-input",{model:{value:e.form.userAccount,callback:function(t){e.$set(e.form,"userAccount",t)},expression:"form.userAccount"}})],1),e._v(" "),r("div",{attrs:{hidden:"true"}},[r("el-form-item",{attrs:{label:"单位Id",prop:"unitId",hidden:"true"}},[r("el-input",{model:{value:e.form.unitId,callback:function(t){e.$set(e.form,"unitId",t)},expression:"form.unitId"}})],1)],1),e._v(" "),r("el-form-item",{attrs:{label:"新密码",prop:"newPass"}},[r("el-input",{attrs:{type:"password"},model:{value:e.form.newPass,callback:function(t){e.$set(e.form,"newPass",t)},expression:"form.newPass"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"确认密码",prop:"userPassword"}},[r("el-input",{attrs:{type:"password"},model:{value:e.form.userPassword,callback:function(t){e.$set(e.form,"userPassword",t)},expression:"form.userPassword"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"受理人",prop:"nationality"}},[r("el-select",{attrs:{filterable:"",placeholder:"选择人员"},model:{value:e.form.peopleId,callback:function(t){e.$set(e.form,"peopleId",t)},expression:"form.peopleId"}},e._l(e.peoples,function(e){return r("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}),1)],1),e._v(" "),e.isManager?r("el-form-item",{attrs:{label:"权限",prop:"nationality"}},[r("el-select",{attrs:{filterable:"",placeholder:"请选择"},model:{value:e.form.roles,callback:function(t){e.$set(e.form,"roles",t)},expression:"form.roles"}},e._l(e.permissions,function(e){return r("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}),1)],1):e._e(),e._v(" "),r("el-form-item",{attrs:{label:"状态",prop:"enabled"}},e._l(e.dicts,function(t){return r("el-radio",{key:t.value,attrs:{label:t.value},model:{value:e.form.enabled,callback:function(t){e.$set(e.form,"enabled",t)},expression:"form.enabled"}},[e._v(e._s(t.label))])}),1)],1),e._v(" "),r("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[r("el-button",{attrs:{type:"text"},on:{click:e.cancel}},[e._v("取消")]),e._v(" "),r("el-button",{attrs:{loading:e.loading,type:"primary"},on:{click:e.doSubmit}},[e._v("确认")])],1)],1)],1)},staticRenderFns:[]};var i=r("VU/8")(a,n,!1,function(e){r("HIVu")},"data-v-c1353240",null);t.default=i.exports},HIVu:function(e,t){}});