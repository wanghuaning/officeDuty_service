webpackJsonp([33],{JH2q:function(e,t){},jzet:function(e,t,s){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var o=s("IcnI"),r=s("vMJZ"),a={props:["userId"],data:function(){var e=this;return{loading:!1,dialog:!1,title:"修改密码",form:{oldPass:"",newPass:"",confirmPass:""},rules:{oldPass:[{required:!0,message:"请输入旧密码",trigger:"blur"}],newPass:[{required:!0,message:"请输入新密码",trigger:"blur"},{min:3,max:20,message:"长度在 3 到 20 个字符",trigger:"blur"}],confirmPass:[{required:!0,validator:function(t,s,o){s?e.form.newPass!==s?o(new Error("两次输入的密码不一致")):o():o(new Error("请再次输入密码"))},trigger:"blur"}]}}},methods:{cancel:function(){this.resetForm()},doSubmit:function(){var e=this;this.$refs.form.validate(function(t){if(!t)return!1;e.loading=!0;var s={oldPass:e.form.oldPass,newPass:e.form.newPass};Object(r.g)(s).then(function(t){e.resetForm(),e.$notify({title:"密码修改成功，请重新登录",type:"success",duration:1500}),setTimeout(function(){o.a.dispatch("LogOut").then(function(){location.reload()})},1500)}).catch(function(t){e.loading=!1,console.log(t.response.data.message)})})},resetForm:function(){this.dialog=!1,this.$refs.form.resetFields(),this.form={oldPass:"",newPass:"",confirmPass:""}}}},i={render:function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("div",{staticStyle:{display:"inline-block"}},[s("el-dialog",{attrs:{visible:e.dialog,"close-on-click-modal":!1,title:e.title,"append-to-body":"",width:"500px"},on:{"update:visible":function(t){e.dialog=t},close:e.cancel}},[s("el-form",{ref:"form",attrs:{model:e.form,rules:e.rules,size:"small","label-width":"88px"}},[s("el-form-item",{attrs:{label:"旧密码",prop:"oldPass"}},[s("el-input",{staticStyle:{width:"370px"},attrs:{type:"password","auto-complete":"on"},model:{value:e.form.oldPass,callback:function(t){e.$set(e.form,"oldPass",t)},expression:"form.oldPass"}})],1),e._v(" "),s("el-form-item",{attrs:{label:"新密码",prop:"newPass"}},[s("el-input",{staticStyle:{width:"370px"},attrs:{type:"password","auto-complete":"on"},model:{value:e.form.newPass,callback:function(t){e.$set(e.form,"newPass",t)},expression:"form.newPass"}})],1),e._v(" "),s("el-form-item",{attrs:{label:"确认密码",prop:"confirmPass"}},[s("el-input",{staticStyle:{width:"370px"},attrs:{type:"password","auto-complete":"on"},model:{value:e.form.confirmPass,callback:function(t){e.$set(e.form,"confirmPass",t)},expression:"form.confirmPass"}})],1)],1),e._v(" "),s("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[s("el-button",{attrs:{type:"text"},on:{click:e.cancel}},[e._v("取消")]),e._v(" "),s("el-button",{attrs:{loading:e.loading,type:"primary"},on:{click:e.doSubmit}},[e._v("确认")])],1)],1)],1)},staticRenderFns:[]};var l=s("VU/8")(a,i,!1,function(e){s("JH2q")},"data-v-fe7578b4",null);t.default=l.exports}});