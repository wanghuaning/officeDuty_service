webpackJsonp([48],{"7HbZ":function(e,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var c={name:"dheader",data:function(){return{currentTime:"",getTime:new Date,millisecond:!1,checked:!0}},created:function(){var e=this,t=localStorage.getItem("checkedUnit");this.$nextTick(function(){e.checked="true"==t}),setInterval(this.getTimes,1e3)},destroyed:function(){window.clearInterval(this._cache_timeout_id_)},methods:{getTimes:function(){var e="",t=new Date;this.getTime=this.millisecond?new Date(this.millisecond):this.getTime,e+=this.getTime.getFullYear()+"年",e+=this.setZero(this.getTime.getMonth()+1,"月","1"),e+=this.setZero(this.getTime.getDate()+"日","   "),e+=this.setZero(["星期天","星期一","星期二","星期三","星期四","星期五","星期六"][t.getDay()]," "),e+=this.setZero(this.getTime.getHours(),":"),e+=this.setZero(this.getTime.getMinutes(),":"),e+=this.setZero(this.getTime.getSeconds(),""),this.currentTime=e,this.millisecond=this.millisecond?this.millisecond:new Date(this.getTime).getTime(),this.millisecond+=1e3},setZero:function(e,t){var i="";return i=e<10?"0"+e:e,i+=t},checkedUnit:function(){this.checked?localStorage.setItem("checkedUnit","true"):localStorage.removeItem("checkedUnit"),setTimeout(function(){location.reload()},2e3)}}},s={render:function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",{staticClass:"header"},[i("div",{staticClass:"bg-header"},[i("el-checkbox",{staticStyle:{color:"#000000",margin:"0px","padding-right":"0px"},on:{change:e.checkedUnit},model:{value:e.checked,callback:function(t){e.checked=t},expression:"checked"}},[e._v("包含下级单位")]),e._v(" "),i("div",{staticStyle:{float:"right",color:"black"}},[e._v(e._s(e.currentTime))]),e._v(" "),i("div",{staticClass:"t-title"},[e._v("\n      公务员职务职级实名制管理信息系统\n    ")])],1)])},staticRenderFns:[]};var n=i("VU/8")(c,s,!1,function(e){i("uEEW")},"data-v-056dc8b0",null);t.default=n.exports},uEEW:function(e,t){}});