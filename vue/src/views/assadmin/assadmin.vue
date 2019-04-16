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
      <el-table-column align="center" label="管理" width="200" v-if="hasPerm('article:update')">
        <template slot-scope="scope">
          <el-button type="primary" v-show="scope.row.is_accept==1" icon="edit" :disabled="true" @click="agreeUser(scope.$index)">已审批</el-button>
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
      <el-button type="primary">创建活动</el-button>
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
      <el-table-column align="center" label="管理" width="200" v-if="hasPerm('article:update')">
        <template slot-scope="scope">
          <el-button type="primary" v-show="scope.row.is_accept==1" icon="edit" :disabled="true" @click="agreeUser(scope.$index)">已审批</el-button>
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
    <el-tab-pane label="考勤管理" name="third">考勤管理</el-tab-pane>
  
  </el-tabs>
   
    <!-- <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="tempArticle" label-position="left" label-width="60px"
               style='width: 300px; margin-left:50px;'>
        <el-form-item label="文章">
          <el-input type="text" v-model="tempArticle.content">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createArticle">创 建</el-button>
        <el-button type="primary" v-else @click="updateArticle">修 改</el-button>
      </div>
    </el-dialog> -->
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
        dialogStatus: 'create',
        dialogFormVisible: false,
        textMap: {
          update: '编辑',
          create: '创建文章'


        },
        assUser: {
          user_id: "",
          assocation_id: "",
          is_accept:""
        }
      }
    },
    computed:{
      user(){
        return this.$store.state.user.userId
      }

    },
    created() {
      this.listQuery.userId=this.user;
      this.getAssUserList();
    },
    methods: {

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
        agreeUser(index) {
          this.assUser.user_id=index.user_id;
          this.assUser.assocation_id=index.assocation_id;
          this.assUser.is_accept=1;
          console.log(this.assUser)
        this.$confirm('即将审批当前社员入团,请谨慎操作,是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          //审批社员
          this.api({
          url: "/assAdmin/agreeAssUser",
          method: "post",
          params: this.assUser
        }).then((res) => {
          this.getList();
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
