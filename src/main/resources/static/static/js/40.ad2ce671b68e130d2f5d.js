webpackJsonp([40],{soNn:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var a=n("QmdQ"),r={name:"notice",data:function(){return{items:[]}},created:function(){var t=this;this.$nextTick(function(){t.getNotice()})},methods:{getNotice:function(){var t=this;Object(a.e)().then(function(e){t.items=e.result})}}},i={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("el-carousel",{staticStyle:{color:"#000000"},attrs:{height:"140px",interval:5e3,arrow:"always"}},t._l(t.items,function(e){return n("el-carousel-item",{key:e.name,staticStyle:{"text-align":"center"}},[n("span",[t._v(t._s(e.name))]),n("br"),t._v(" "),n("span",[t._v(t._s(e.param))])])}),1)},staticRenderFns:[]};var c=n("VU/8")(r,i,!1,function(t){n("tdp9")},"data-v-5b77508b",null);e.default=c.exports},tdp9:function(t,e){}});