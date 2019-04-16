<template>
  <div class="app-container">
 
   <el-tabs v-model="activeName2" type="card" @tab-click="handleClick">
    <el-tab-pane label="社员审批" name="first">
       <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row>
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="nickname" label="姓名" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="user_id" label="学号" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="class" label="班级" style="width: 60px;"></el-table-column>
      <el-table-column align="center"  label="入团状态" style="width: 60px;">
           <template slot-scope="scope">
               <el-tag  v-show="scope.row.is_accept==1">已入团</el-tag>
               <el-tag  v-show="scope.row.is_accept==2" type="success">待审批</el-tag>
            </template>
  </el-table-column>
      <el-table-column align="center" label="申请时间" width="170">
        <template slot-scope="scope">
          <span>{{scope.row.create_time}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="管理" width="200" v-if="hasPerm('assAdmin:admin')">
        <template slot-scope="scope">
          <el-button type="primary" v-show="scope.row.is_accept==1" icon="edit"  @click="agreeUser(scope.row)">踢出社团</el-button>
          <el-button type="primary" v-show="scope.row.is_accept==2" icon="edit" @click="agreeUser(scope.row)">审批</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="listQuery.pageNum"
      :page-size="listQuery.pageRow"
      :total="totalCount"
      :page-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination></el-tab-pane>
    <el-tab-pane label="活动管理" name="second"> 
      <el-button type="primary" @click="showCreate()" >创建活动</el-button>
      <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row>
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="name" label="活动名称" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="details" label="活动简介" style="width: 60px;"></el-table-column>
   
      <el-table-column align="center"  label="活动状态" style="width: 60px;">
           <template slot-scope="scope">
               <el-tag  v-show="scope.row.is_open==1">开放中</el-tag>
               <el-tag  v-show="scope.row.is_open==2" type="success">关闭中</el-tag>
            </template>
  </el-table-column>
      <el-table-column align="center" label="活动开始时间" width="170">
        <template slot-scope="scope">
          <span>{{scope.row.start_time}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="活动结束时间" width="170">
        <template slot-scope="scope">
          <span>{{scope.row.end_time}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="管理" width="200" v-if="hasPerm('assAdmin:admin')">
        <template slot-scope="scope">
          <el-button type="primary"  icon="edit"  @click="showUpdateActivity(scope.row)">活动管理</el-button>
       
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="listQuery.pageNum"
      :page-size="listQuery.pageRow"
      :total="totalCount"
      :page-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination></el-tab-pane>
    <el-tab-pane label="考勤管理" name="third">考勤管理</el-tab-pane>
  
  </el-tabs>
   
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form class="small-space"  :model="tempActivity" ref="tempActivity" label-position="left" label-width="100px"
               style='width: 300px; margin-left:50px;'>
        <el-form-item label="活动名称" prop="name" :rules="[
      { required: true, message: '请输入活动名称', trigger: 'blur' },
    ]">
          <el-input type="text" v-model="tempActivity.name">
          </el-input>
        </el-form-item>
        <el-form-item label="活动介绍" prop="details" :rules="[
      { required: true, message: '请输入活动介绍', trigger: 'blur' },
    ]">
          <el-input type="text" v-model="tempActivity.details">
          </el-input>
        </el-form-item>
         <el-form-item label="活动时间" :rules="[
      { required: true, message: '请选择活动时间', trigger: 'blur' },
    ]">
         <div class="block">
    <span class="demonstration">选择活动开始结束时间</span>
    <el-date-picker
     @change="changeTime"
      v-model="dateTime"
      type="datetimerange"
      range-separator="至"
      value-format="yyyy-MM-dd HH:mm"
      start-placeholder="开始日期"
      end-placeholder="结束日期">
      
    </el-date-picker>
  </div>
        </el-form-item>
            <el-form-item label="活动状态" prop="name" :rules="[
      { required: true, message: '请选择活动状态', trigger: 'blur' },
    ]">
         <el-select v-model="tempActivity.is_open" placeholder="请选择">
        <el-option
       v-for="item in options"
      :key="item.value"
      :label="item.label"
      :value="item.value">
    </el-option>
  </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createActivity">创 建</el-button>
        <el-button type="primary" v-else @click="updateActivity">修 改</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        totalCount: 0, //分页组件--数据总条数


        activeName2: 'first',
        list: [],//表格的数据
        listLoadinglistLoading: false,//数据加载等待动画
        listQuery: {
          pageNum: 1,//页码
          pageRow: 10,//每页条数
          userId: ''
        },
        options:[
          {
          value: 1,
          label: '开放'},
          { value: 2,
          label: '关闭'}],
       rules:{
            name: [
            { required: true, message: '请输入活动名称', trigger: 'blur' },
            { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ],
          details: [
            { required: true, message: '请填写活动形式', trigger: 'blur' }
          ]
       },
        dialogStatus: 'create',
        dialogFormVisible: false,
        textMap: {
          update: '编辑活动',
          create: '创建活动'


        },
        tempActivity:{
          id:"",
          association_id:"",
          userId:"",
          name:"",
          details:"",
          start_time:"",
          end_time:"",
          is_open:""

        },
        assUser: {
          user_id: "",
          association_id: "",
          is_accept:""
        },
        dateTime:[]
      }
    },
    computed:{
      user(){
        return this.$store.state.user.userId
      }

    },
    created() {
      this.listQuery.userId=this.user;
      console.log(this.listQuery)
      this.getAssUserList();
    },
    methods: {
      changeTime(){
       this.tempActivity.start_time = this.dateTime[0];
        this.tempActivity.end_time = this.dateTime[1];
      },

      handleClick(tab, event) {
     if("first"==tab.name){
      this.getAssUserList();
     }
     if("second"==tab.name){
    this.getActivityList();
     }
      },
      getAssUserList() {
        //查询列表
        if (!this.hasPerm('assAdmin:list')) {
          return
        }
        this.listLoading = true;
        this.api({
          url: "/assAdmin/listAssUser",
          method: "post",
          params: this.listQuery
        }).then(data => {
          this.listLoading = false;
          this.list = data.list;
          this.totalCount = data.totalCount;
        })
      },
        getActivityList() {
        //查询列表
        if (!this.hasPerm('assAdmin:list')) {
          return
        }
        this.listLoading = true;
        this.api({
          url: "/assAdmin/listActivity",
          method: "post",
          params: this.listQuery
        }).then(data => {
          this.listLoading = false;
          this.list = data.list;
          this.totalCount = data.totalCount;
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
        this.getAssUserList()
      },
      handleClose(){
       this.dialogFormVisible = false;
         this.dateTime=[];
          this.tempActivity.is_open=""

      },
      getIndex($index) {
        //表格序号
        return (this.listQuery.pageNum - 1) * this.listQuery.pageRow + $index + 1
      },
      showCreate() {
        //显示新增对话框
        this.tempActivity.name = "";
        this.tempActivity.userId = this.user;
        this.tempActivity.details = "";
        this.dialogStatus = "create"
        this.dialogFormVisible = true
      },
        agreeUser(index) {
          this.assUser.user_id=index.user_id;
          this.assUser.association_id=index.association_id;
          if(index.is_accept==1){
            this.assUser.is_accept=2;

          }
          if(index.is_accept==2){
            this.assUser.is_accept=1;

          }
          
          console.log(this.assUser)
        this.$confirm('请谨慎操作,是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          //管理社员
          this.api({
          url: "/assAdmin/agreeAssUser",
          method: "post",
          params: this.assUser
        }).then((res) => {
        this.getAssUserList()
        console.log(res)
        })

          this.$message({
            type: 'success',
            message: '审批成功!'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消审批'
          });          
        });

      },
      createActivity() {
        console.log(this.tempActivity);
        if(this.tempActivity.name==""||this.tempActivity.details==""||this.tempActivity.start_time==""||this.tempActivity.end_time==""){
          this.$message({
          message: '请输入必选数据',
          type: 'warning'
        });
    
        return
        }
      
     
     
     
        console.log(this.tempActivity)
        //创建活动
        this.api({
          url: "/assAdmin/createActivity",
          method: "post",
          params: this.tempActivity
        }).then(() => {
          this.getActivityList();
          this.dialogFormVisible = false
          this.dateTime=[];
          this.tempActivity.is_open=""
        })
      },
      showUpdateActivity(index){
     
         //显示更新对话框
          this.tempActivity.id=index.id;
          this.tempActivity.association_id=index.association_id;
        this.tempActivity.name = index.name;
        this.tempActivity.details = index.details;
        this.tempActivity.start_time = index.start_time;
        this.tempActivity.end_time = index.end_time;
        this.dateTime=[index.start_time,index.end_time]
        this.dialogStatus = "update"
        this.tempActivity.is_open=index.is_open;
        this.dialogFormVisible = true
       
         console.log(this.tempActivity)

      },
      updateActivity(){

      //更新活动
        this.api({
          url: "/assAdmin/updateActivity",
          method: "post",
          params: this.tempActivity
        }).then(() => {
          this.getActivityList();
          this.dialogFormVisible = false
        })
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
<style scoped>

  .el-row {
    margin-bottom: 20px;
    
  }
  .el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
</style>
