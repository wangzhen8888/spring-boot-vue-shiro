webpackJsonp([5],{"+L4n":function(e,t,s){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r=s("4YfN"),a=s.n(r),i=s("9rMa"),l={data:function(){return{totalCount:0,list:[],listLoading:!1,listQuery:{pageNum:1,pageRow:50},roles:[],dialogStatus:"create",dialogFormVisible:!1,textMap:{update:"编辑",create:"新建用户"},tempUser:{username:"",password:"",nickname:"",roleId:"",userId:""}}},created:function(){this.getList(),(this.hasPerm("user:add")||this.hasPerm("user:update"))&&this.getAllRoles()},computed:a()({},Object(i.b)(["userId"])),methods:{getAllRoles:function(){var e=this;this.api({url:"/user/getAllRoles",method:"get"}).then(function(t){e.roles=t.list})},getList:function(){var e=this;this.listLoading=!0,this.api({url:"/user/list",method:"get",params:this.listQuery}).then(function(t){e.listLoading=!1,e.list=t.list,e.totalCount=t.totalCount})},handleSizeChange:function(e){this.listQuery.pageRow=e,this.handleFilter()},handleCurrentChange:function(e){this.listQuery.pageNum=e,this.getList()},handleFilter:function(){this.listQuery.pageNum=1,this.getList()},getIndex:function(e){return(this.listQuery.pageNum-1)*this.listQuery.pageRow+e+1},showCreate:function(){this.tempUser.username="",this.tempUser.password="",this.tempUser.nickname="",this.tempUser.roleId="",this.tempUser.userId="",this.dialogStatus="create",this.dialogFormVisible=!0},showUpdate:function(e){var t=this.list[e];this.tempUser.username=t.username,this.tempUser.nickname=t.nickname,this.tempUser.roleId=t.roleId,this.tempUser.userId=t.userId,this.tempUser.deleteStatus="1",this.tempUser.password="",this.dialogStatus="update",this.dialogFormVisible=!0},createUser:function(){var e=this;this.api({url:"/user/addUser",method:"post",data:this.tempUser}).then(function(){e.getList(),e.dialogFormVisible=!1})},updateUser:function(){var e=this,t=this;this.api({url:"/user/updateUser",method:"post",data:this.tempUser}).then(function(){var s="修改成功";e.dialogFormVisible=!1,e.userId===e.tempUser.userId&&(s="修改成功,部分信息重新登录后生效"),e.$message({message:s,type:"success",duration:1e3,onClose:function(){t.getList()}})})},removeUser:function(e){var t=this;this.$confirm("确定删除此用户?","提示",{confirmButtonText:"确定",showCancelButton:!1,type:"warning"}).then(function(){var s=t.list[e];s.deleteStatus="2",t.api({url:"/user/updateUser",method:"post",data:s}).then(function(){t.getList()}).catch(function(){t.$message.error("删除失败")})})}}},n={render:function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("div",{staticClass:"app-container"},[s("div",{staticClass:"filter-container"},[s("el-form",[s("el-form-item",[e.hasPerm("user:add")?s("el-button",{attrs:{type:"primary",icon:"plus"},on:{click:e.showCreate}},[e._v("添加新用户\n        ")]):e._e()],1)],1)],1),e._v(" "),s("el-table",{directives:[{name:"loading",rawName:"v-loading.body",value:e.listLoading,expression:"listLoading",modifiers:{body:!0}}],attrs:{data:e.list,"element-loading-text":"拼命加载中",border:"",fit:"","highlight-current-row":""}},[s("el-table-column",{attrs:{align:"center",label:"序号",width:"80"},scopedSlots:e._u([{key:"default",fn:function(t){return[s("span",{domProps:{textContent:e._s(e.getIndex(t.$index))}})]}}])}),e._v(" "),s("el-table-column",{staticStyle:{width:"60px"},attrs:{align:"center",label:"昵称",prop:"nickname"}}),e._v(" "),s("el-table-column",{staticStyle:{width:"60px"},attrs:{align:"center",label:"用户名",prop:"username"}}),e._v(" "),s("el-table-column",{attrs:{align:"center",label:"角色",width:"100"},scopedSlots:e._u([{key:"default",fn:function(t){return[1===t.row.roleId?s("el-tag",{attrs:{type:"success"},domProps:{textContent:e._s(t.row.roleName)}}):s("el-tag",{attrs:{type:"primary"},domProps:{textContent:e._s(t.row.roleName)}})]}}])}),e._v(" "),s("el-table-column",{attrs:{align:"center",label:"创建时间",prop:"createTime",width:"170"}}),e._v(" "),s("el-table-column",{attrs:{align:"center",label:"最近修改时间",prop:"updateTime",width:"170"}}),e._v(" "),e.hasPerm("user:update")?s("el-table-column",{attrs:{align:"center",label:"管理",width:"220"},scopedSlots:e._u([{key:"default",fn:function(t){return[s("el-button",{attrs:{type:"primary",icon:"edit"},on:{click:function(s){e.showUpdate(t.$index)}}},[e._v("修改")]),e._v(" "),t.row.userId!=e.userId&&e.hasPerm("user:delete")?s("el-button",{attrs:{type:"danger",icon:"delete"},on:{click:function(s){e.removeUser(t.$index)}}},[e._v("删除\n        ")]):e._e()]}}])}):e._e()],1),e._v(" "),s("el-pagination",{attrs:{"current-page":e.listQuery.pageNum,"page-size":e.listQuery.pageRow,total:e.totalCount,"page-sizes":[10,20,50,100],layout:"total, sizes, prev, pager, next, jumper"},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}}),e._v(" "),s("el-dialog",{attrs:{title:e.textMap[e.dialogStatus],visible:e.dialogFormVisible},on:{"update:visible":function(t){e.dialogFormVisible=t}}},[s("el-form",{staticClass:"small-space",staticStyle:{width:"300px","margin-left":"50px"},attrs:{model:e.tempUser,"label-position":"left","label-width":"80px"}},["create"==e.dialogStatus?s("el-form-item",{attrs:{label:"用户名",required:""}},[s("el-input",{attrs:{type:"text"},model:{value:e.tempUser.username,callback:function(t){e.$set(e.tempUser,"username",t)},expression:"tempUser.username"}})],1):e._e(),e._v(" "),"create"==e.dialogStatus?s("el-form-item",{attrs:{label:"密码",required:""}},[s("el-input",{attrs:{type:"password"},model:{value:e.tempUser.password,callback:function(t){e.$set(e.tempUser,"password",t)},expression:"tempUser.password"}})],1):s("el-form-item",{attrs:{label:"新密码"}},[s("el-input",{attrs:{type:"password",placeholder:"不填则表示不修改"},model:{value:e.tempUser.password,callback:function(t){e.$set(e.tempUser,"password",t)},expression:"tempUser.password"}})],1),e._v(" "),s("el-form-item",{attrs:{label:"角色",required:""}},[s("el-select",{attrs:{placeholder:"请选择"},model:{value:e.tempUser.roleId,callback:function(t){e.$set(e.tempUser,"roleId",t)},expression:"tempUser.roleId"}},e._l(e.roles,function(e){return s("el-option",{key:e.roleId,attrs:{label:e.roleName,value:e.roleId}})}))],1),e._v(" "),s("el-form-item",{attrs:{label:"昵称",required:""}},[s("el-input",{attrs:{type:"text"},model:{value:e.tempUser.nickname,callback:function(t){e.$set(e.tempUser,"nickname",t)},expression:"tempUser.nickname"}})],1)],1),e._v(" "),s("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[s("el-button",{on:{click:function(t){e.dialogFormVisible=!1}}},[e._v("取 消")]),e._v(" "),"create"==e.dialogStatus?s("el-button",{attrs:{type:"success"},on:{click:e.createUser}},[e._v("创 建")]):s("el-button",{attrs:{type:"primary"},on:{click:e.updateUser}},[e._v("修 改")])],1)],1)],1)},staticRenderFns:[]},o=s("/Xao")(l,n,!1,null,null,null);t.default=o.exports}});