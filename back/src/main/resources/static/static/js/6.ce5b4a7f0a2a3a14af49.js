webpackJsonp([6],{"Vg+r":function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=a("hRKE"),s=a.n(i),o={data:function(){return{totalCount:0,formInline:{user:"",region:""},dateTime:[],list:[],listLoading:!1,listQuery:{pageNum:1,pageRow:10,goods_name:"",orderType:"",goods_id:"",company_name:"",start_time:"",end_time:""},order:{id:"",goods_id:"",goods_num:"",orderType:"",marks:""},dialogStatus:"create",dialogFormVisible:!1,dialogFormVisible1:!1,textMap:{update:"编辑",create:"创建文章"},tempOrder:{id:"",user_name:"",company_name:"",goods_id:"",goods_num:"",orderType:"",marks:""}}},created:function(){this.getList()},methods:{showOrder:function(e){this.dialogFormVisible1=!0,this.tempOrder.user_name=e.user_name,this.tempOrder.company_name=e.company_name},importExcel:function(){this.api({url:"/excel/getUser",method:"post",params:this.listQuery,responseType:"blob"}).then(function(e){console.log(void 0===e?"undefined":s()(e));var t=document.createElement("a"),a=window.URL.createObjectURL(e);t.href=a,t.download="用户统计信息.xls",t.click(),window.URL.revokeObjectURL(a)})},selectDateTime:function(){this.listQuery.start_time=this.dateTime[0],this.listQuery.end_time=this.dateTime[1]},onSearch:function(){console.log(this.listQuery),this.getList()},onReset:function(){this.dateTime=[],this.selectDateTime(),this.$refs.searchForm.resetFields(),this.getList()},getList:function(){var e=this;this.hasPerm("order:list")&&(this.listLoading=!0,this.api({url:"/order/listOrder",method:"get",params:this.listQuery}).then(function(t){e.listLoading=!1,e.list=t.list,e.totalCount=t.totalCount}))},handleSizeChange:function(e){this.listQuery.pageRow=e,this.handleFilter()},handleCurrentChange:function(e){this.listQuery.pageNum=e,this.getList()},getIndex:function(e){return(this.listQuery.pageNum-1)*this.listQuery.pageRow+e+1},showCreate:function(){this.tempArticle.content="",this.dialogStatus="create",this.dialogFormVisible=!0},showUpdate:function(e){console.log(this.list),this.order.id=this.list[e].id,this.order.marks=this.list[e].marks,this.order.goods_num=this.list[e].goods_num,this.order.orderType=1,this.order.goods_id=this.list[e].goods_id,this.dialogStatus="update",this.dialogFormVisible=!0,console.log(this.order)},createArticle:function(){var e=this;this.api({url:"/article/addArticle",method:"post",data:this.tempArticle}).then(function(){e.getList(),e.dialogFormVisible=!1})},handleFilter:function(){this.listQuery.pageNum=1,this.getList()},updateOrder:function(){var e=this;this.api({url:"/order/updateOrder",method:"post",data:this.order}).then(function(t){console.log(t),e.$message({message:t.msg,type:"warning"}),e.getList(),e.dialogFormVisible=!1})}}},l={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"app-container"},[a("div",{staticClass:"filter-container"},[a("el-form",{ref:"searchForm",staticClass:"demo-form-inline",attrs:{inline:!0,model:e.listQuery}},[a("el-form-item",{attrs:{label:"单位名称",prop:"company_name"}},[a("el-input",{attrs:{placeholder:"单位名称"},model:{value:e.listQuery.company_name,callback:function(t){e.$set(e.listQuery,"company_name",t)},expression:"listQuery.company_name"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"货物名称",prop:"goods_name"}},[a("el-input",{attrs:{placeholder:"货物名称"},model:{value:e.listQuery.goods_name,callback:function(t){e.$set(e.listQuery,"goods_name",t)},expression:"listQuery.goods_name"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"货物编码",prop:"goods_id"}},[a("el-input",{attrs:{placeholder:"货物编码"},model:{value:e.listQuery.goods_id,callback:function(t){e.$set(e.listQuery,"goods_id",t)},expression:"listQuery.goods_id"}})],1),e._v(" "),a("el-form-item",{attrs:{label:"订单类型",prop:"orderType"}},[a("el-select",{attrs:{placeholder:"订单类型"},model:{value:e.listQuery.orderType,callback:function(t){e.$set(e.listQuery,"orderType",t)},expression:"listQuery.orderType"}},[a("el-option",{attrs:{label:"入库",value:"0"}}),e._v(" "),a("el-option",{attrs:{label:"出库",value:"1"}}),e._v(" "),a("el-option",{attrs:{label:"报废",value:"2"}}),e._v(" "),a("el-option",{attrs:{label:"缺货",value:"3"}})],1)],1),e._v(" "),a("el-form-item",[a("el-button",{attrs:{type:"primary"},on:{click:e.onReset}},[e._v("重置")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:e.onSearch}},[e._v("查询")]),e._v(" "),e.hasPerm("goods:add")?a("el-button",{attrs:{type:"primary",icon:"plus"},on:{click:e.importExcel}},[e._v("导出\n          ")]):e._e()],1),e._v(" "),a("el-form-item",{attrs:{label:"订单日期",prop:"dateTime"}},[a("div",{staticClass:"block"},[a("el-date-picker",{attrs:{type:"datetimerange","range-separator":"至","start-placeholder":"开始日期","end-placeholder":"结束日期","value-format":"yyyy.MM.dd HH.mm.ss"},on:{change:e.selectDateTime},model:{value:e.dateTime,callback:function(t){e.dateTime=t},expression:"dateTime"}})],1)])],1)],1),e._v(" "),a("el-table",{directives:[{name:"loading",rawName:"v-loading.body",value:e.listLoading,expression:"listLoading",modifiers:{body:!0}}],attrs:{data:e.list,"element-loading-text":"拼命加载中",border:"",fit:"","highlight-current-row":""}},[a("el-table-column",{attrs:{align:"center",label:"序号",width:"80"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("span",{domProps:{textContent:e._s(e.getIndex(t.$index))}})]}}])}),e._v(" "),a("el-table-column",{staticStyle:{width:"60px"},attrs:{align:"center",prop:"goods_name",label:"货物名称"}}),e._v(" "),a("el-table-column",{staticStyle:{width:"60px"},attrs:{align:"center",prop:"goods_id",label:"货物编号"}}),e._v(" "),a("el-table-column",{staticStyle:{width:"60px"},attrs:{align:"center",prop:"goods_num",label:"货物数量"}}),e._v(" "),a("el-table-column",{staticStyle:{width:"60px"},attrs:{align:"center",prop:"company_name",label:"单位/个人"}}),e._v(" "),a("el-table-column",{staticStyle:{width:"60px"},attrs:{align:"center",prop:"company_phone",label:"联系方式"}}),e._v(" "),a("el-table-column",{staticStyle:{width:"60px"},attrs:{align:"center",prop:"user_name",label:"操作人"}}),e._v(" "),a("el-table-column",{staticStyle:{width:"60px"},attrs:{align:"center",prop:"marks",label:"备注"}}),e._v(" "),a("el-table-column",{staticStyle:{width:"60px"},attrs:{align:"center",prop:"goods_type",label:"订单类型"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-tag",{directives:[{name:"show",rawName:"v-show",value:0==t.row.order_type,expression:"scope.row.order_type==0"}],attrs:{type:"success"}},[e._v("入库")]),e._v(" "),a("el-tag",{directives:[{name:"show",rawName:"v-show",value:1==t.row.order_type,expression:"scope.row.order_type==1"}],attrs:{type:"success"}},[e._v("出库")]),e._v(" "),a("el-tag",{directives:[{name:"show",rawName:"v-show",value:2==t.row.order_type,expression:"scope.row.order_type==2"}],attrs:{type:"success"}},[e._v("报废")]),e._v(" "),a("el-tag",{directives:[{name:"show",rawName:"v-show",value:3==t.row.order_type,expression:"scope.row.order_type==3"}],attrs:{type:"success"}},[e._v("缺货")])]}}])}),e._v(" "),a("el-table-column",{staticStyle:{width:"60px"},attrs:{align:"center",prop:"goods_type",label:"订单凭证"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{type:"success",size:"mini",icon:"edit"},on:{click:function(a){e.showOrder(t.row)}}},[e._v("查看凭证")])]}}])}),e._v(" "),a("el-table-column",{attrs:{align:"center",label:"创建时间",width:"170"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("span",[e._v(e._s(t.row.createTime))])]}}])}),e._v(" "),a("el-table-column",{attrs:{align:"center",label:"更新时间",width:"170"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("span",[e._v(e._s(t.row.updateTime))])]}}])}),e._v(" "),a("el-table-column",{staticStyle:{width:"60px"},attrs:{align:"center",prop:"marks",label:"备注"}}),e._v(" "),e.hasPerm("order:update")?a("el-table-column",{attrs:{align:"center",label:"管理",width:"200"},scopedSlots:e._u([{key:"default",fn:function(t){return[3==t.row.order_type?a("el-button",{attrs:{type:"warning",size:"mini",icon:"edit"},on:{click:function(a){e.showUpdate(t.$index)}}},[e._v("待补货")]):a("el-button",{attrs:{type:"success",size:"mini",icon:"edit",disabled:""}},[e._v("已处理")])]}}])}):e._e()],1),e._v(" "),a("el-pagination",{attrs:{"current-page":e.listQuery.pageNum,"page-size":e.listQuery.pageRow,total:e.totalCount,"page-sizes":[10,20,50,100],layout:"total, sizes, prev, pager, next, jumper"},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}}),e._v(" "),a("el-dialog",{attrs:{title:e.textMap[e.dialogStatus],visible:e.dialogFormVisible},on:{"update:visible":function(t){e.dialogFormVisible=t}}},[a("el-form",{staticClass:"small-space",staticStyle:{width:"300px","margin-left":"50px"},attrs:{model:e.order,"label-position":"left","label-width":"60px"}},[a("el-form-item",{attrs:{label:""}},[a("el-tag",{attrs:{type:"danger"}},[e._v("请确保货物库存充足，否则将补货失败")])],1),e._v(" "),a("el-form-item",{attrs:{label:"备注"}},[a("el-input",{attrs:{type:"text"},model:{value:e.order.marks,callback:function(t){e.$set(e.order,"marks",t)},expression:"order.marks"}})],1)],1),e._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.dialogFormVisible=!1}}},[e._v("取 消")]),e._v(" "),a("el-button",{attrs:{type:"primary"},on:{click:e.updateOrder}},[e._v("补发")])],1)],1),e._v(" "),a("el-dialog",{attrs:{title:"订单凭证",visible:e.dialogFormVisible1,width:"300px"},on:{"update:visible":function(t){e.dialogFormVisible1=t}}},[a("el-form",{staticClass:"small-space",staticStyle:{width:"300px","margin-left":"10px"},attrs:{model:e.tempOrder,"label-position":"left","label-width":"60px"}},[a("el-form-item",{attrs:{label:""}},[a("div",{},[a("el-tag",{attrs:{type:"danger"}},[e._v("仓库管理员")]),e._v(":  "+e._s(this.tempOrder.user_name)+" "),a("br"),e._v(" 确认结果:    "),a("el-tag",{attrs:{type:"success"}},[e._v("已确认")]),a("br")],1),e._v(" "),a("div",[a("el-tag",{attrs:{type:"danger"}},[e._v("单位/公司")]),e._v(":  "+e._s(this.tempOrder.company_name)+" "),a("br"),e._v("确认结果:    "),a("el-tag",{attrs:{type:"success"}},[e._v("已确认")]),a("br")],1)])],1),e._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(t){e.dialogFormVisible1=!1}}},[e._v("确认")])],1)],1)],1)},staticRenderFns:[]},r=a("/Xao")(o,l,!1,null,null,null);t.default=r.exports}});