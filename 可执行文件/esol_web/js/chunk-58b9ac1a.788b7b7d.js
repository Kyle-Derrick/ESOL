(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-58b9ac1a"],{"39be":function(t,s,a){"use strict";a.r(s);var n=function(){var t=this,s=t.$createElement,a=t._self._c||s;return a("div",{staticClass:"list-div"},[a("div",{staticClass:"list"},t._l(t.listData,(function(s,n){return a("div",{key:n,staticClass:"list-item"},[a("div",{staticClass:"list-title"},[a("span",[t._v(t._s(t.title)+" ("+t._s(n+1)+")")])]),a("div",{staticClass:"list-content"},[a("van-row",{attrs:{type:"flex",justify:"space-between"}},[a("van-col",{attrs:{span:"6"}},[a("div",{staticClass:"subhead"},[a("span",[t._v("答题时间")])]),a("div",{staticClass:"content"},[a("span",[t._v(t._s(s.resultDate))])])]),a("van-col",{attrs:{span:"6"}},[a("div",{staticClass:"subhead"},[a("span",[t._v("答题时长")])]),a("div",{staticClass:"content"},[a("span",[t._v(t._s(s.duration)+"分钟")])])]),a("van-col",{attrs:{span:"6"}},[a("div",{staticClass:"subhead"},[a("span",[t._v("最终得分")])]),a("div",{staticClass:"content"},[a("span",[t._v(t._s(s.sorce)+"分")])])])],1),a("van-row",{attrs:{type:"flex",justify:"space-between"}},[a("van-col",{attrs:{span:"6"}},[a("div",{staticClass:"subhead"},[a("span",[t._v("正确率")])]),a("div",{staticClass:"content"},[a("span",[t._v(t._s(parseInt(s.sucessNum/(s.sucessNum+s.errorNum)*100))+"%")])])]),a("van-col",{attrs:{span:"6"}},[a("div",{staticClass:"subhead"},[a("span",[t._v("错误数")])]),a("div",{staticClass:"content"},[a("span",[t._v(t._s(s.errorNum))])])]),a("van-col",{attrs:{span:"6"}},[a("a",{staticClass:"text-btn",attrs:{href:"javascript:;"},on:{click:function(a){return t.toAnswer(s.resultId)}}},[a("span",[t._v("查看详情")])])])],1)],1)])})),0)])},e=[],i=(a("ac1f"),a("5319"),{name:"examResultHistory",data:function(){return{listData:[],title:""}},mounted:function(){this.$store.state.nav.title="历史考试答卷",this.listData=this.$route.params.data,this.title=this.$route.params.title},methods:{toAnswer:function(t){var s=this;this.$get(this,"/exam/result/"+t,(function(t){s.$router.replace({name:"answer",params:t.data})}))}}}),c=i,r=(a("b9a6"),a("2877")),l=Object(r["a"])(c,n,e,!1,null,"d556157a",null);s["default"]=l.exports},b9a6:function(t,s,a){"use strict";var n=a("e277"),e=a.n(n);e.a},e277:function(t,s,a){}}]);