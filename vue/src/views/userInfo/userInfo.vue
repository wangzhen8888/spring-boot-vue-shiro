<template>
  <div class="app-container">
    <div class="filter-container" style="width:600px">
      <el-form :label-position="labelPosition" label-width="80px" :model="formLabelAlign">
  <el-form-item label="学号">
    <el-input disabled v-model="formLabelAlign.id"></el-input>
  </el-form-item>
  <el-form-item label="姓名">
    <el-input  disabled v-model="formLabelAlign.user_name"></el-input>
  </el-form-item>
  <el-form-item label="班级">
    <el-input disabled v-model="formLabelAlign.class"></el-input>
  </el-form-item>
   <el-form-item label="手机号">
    <el-input v-model="formLabelAlign.phone"></el-input>
  </el-form-item>
  <el-form-item label="密码">
    <el-input v-model="formLabelAlign.password"></el-input>
  </el-form-item>
   <el-form-item>
    <el-button type="primary" @click="updateUserInfo" :disabled="!hasPerm('userInfo:update')">修改</el-button>
  </el-form-item>
</el-form>
  </div>
</div>
</template>
<script>
  export default {
    data() {
      return {

        labelPosition: 'left',
        formLabelAlign: {
          id:"",
          user_name: '',
          class: '',
          phone: '',
          password:""
        },
        totalCount: 0, //分页组件--数据总条数


        
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          pageNum: 1,//页码
          pageRow: 10,//每页条数
          user_id: ''
        },
        dialogStatus: 'create',
        dialogFormVisible: false,
        textMap: {
          update: '编辑',
          create: '创建文章'


        },
        tempArticle: {
          id: "",
          content: ""
        }
      }
    },
    created() {
      this.listQuery.user_id=this.user.userId
     console.log(this.listQuery);
     this.getUserInfo()
    },
    computed:{
      user(){
        return this.$store.state.user
      }

    },
    methods: {
      getUserInfo(){
        
        //获取角色基本信息
        this.api({
          url: "/user/getNewUserInfo",
          method: "post",
          params: this.listQuery
        }).then((res) => {
          this.formLabelAlign.id=res.id;
         this.formLabelAlign.user_name=res.nickname;
         this.formLabelAlign.phone=res.phone;
           this.formLabelAlign.class=res.class;
           this.formLabelAlign.password=res.password;

          console.log(this.formLabelAlign)
          
        })
      },
      updateUserInfo(){
        if(this.formLabelAlign.password==""||null==this.formLabelAlign.password){
          this.$message({
          message: '密码不能为空',
          type: 'warning'
        });
    
        return
        }
      //更新个人基本信息
        this.api({
          url: "/user/updateNewUser",
          method: "post",
          params: this.formLabelAlign
        }).then((res) => {
         this.getUserInfo();
          console.log(this.formLabelAlign)
           this.$message({
          message: '修改成功',
          type: 'warning'
        });
        })


      },
     
      handleSizeChange(val) {
        //改变每页数量
        this.listQuery.pageRow = val
        this.handleFilter();
      },
      handleCurrentChange(val) {
        //改变页码
        this.listQuery.pageNum = val
        this.getList();
      },
      getIndex($index) {
        //表格序号
        return (this.listQuery.pageNum - 1) * this.listQuery.pageRow + $index + 1
      },
      showCreate() {
        //显示新增对话框
        this.tempArticle.content = "";
        this.dialogStatus = "create"
        this.dialogFormVisible = true
      },
      showUpdate($index) {
        //显示修改对话框
        this.tempArticle.id = this.list[$index].id;
        this.tempArticle.content = this.list[$index].content;
        this.dialogStatus = "update"
        this.dialogFormVisible = true
      },
      createArticle() {
        //保存新文章
        this.api({
          url: "/article/addArticle",
          method: "post",
          data: this.tempArticle
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false
        })
      },
       handleFilter() {
        //查询事件
        this.listQuery.pageNum = 1
        this.getList()
      },
      updateArticle() {
        //修改文章
        this.api({
          url: "/article/updateArticle",
          method: "post",
          data: this.tempArticle
        }).then(() => {
        
          this.getList();
          this.dialogFormVisible = false
        })
      },
    }
  }
</script>
