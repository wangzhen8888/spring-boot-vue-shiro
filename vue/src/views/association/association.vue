<template>
  <div class="association">
    <div class="filter-container">
 

      <el-form>
        <el-form-item>
          <el-button
            type="primary"
            icon="plus"
            @click="showCreate"
            v-if="hasPerm('association:add')"
          >新增社团</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table
      :data="list"
      v-loading.body="listLoading"
      element-loading-text="拼命加载中"
      border
      fit
      highlight-current-row
    >
      <template slot="empty">
        没有社团，请创建
        <img class="data-pic" src="#" alt>
      </template>
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"></span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="name" label="社团名称" style="width: 30px;"></el-table-column>
      
      <el-table-column align="center" prop="simple_detail" label="社团简介" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="创建时间" width="150">
        <template slot-scope="scope">
          <span>{{scope.row.create_time}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="社长姓名" width="150">
        <template slot-scope="scope">
    
          <span v-if="scope.row.user_name==''||scope.row.user_name==null">无社长</span>
          <span v-else>{{scope.row.user_name}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="社长id" width="150">
        <template slot-scope="scope">
    
          <span v-if="scope.row.user_id==''||scope.row.user_id==null">无社长</span>
          <span v-else>{{scope.row.user_id}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="管理" width="250" v-if="hasPerm('association:admin')">
        <template slot-scope="scope">
          <!-- <el-button
            type="info"
            icon="info"
            size="mini"
            v-if="hasPerm('association:delete') "
            @click="showDetail(scope.$index)"
          >详情</el-button> -->
            <el-button type="primary" icon="edit" size="mini" @click="showUpdate(scope.row)">社团管理</el-button>
          <el-button
            type="danger"
            icon="delete"
            size="mini"
            v-if="hasPerm('association:delete') "
            @click="removeAssociation(scope.$index)"
          >删除</el-button>
        
        </template>
      </el-table-column>
      <img src alt>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="listQuery.pageNum"
      :page-size="listQuery.pageRow"
      :total="totalCount"
      :page-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
    ></el-pagination>
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" :show-close="false">
      <el-form
        class="small-space"
        :model="association"
        label-position="left"
        label-width="70px"
        style="width: 400px; margin-left:80px;"
      >
        <el-form-item label="社团名称">
          <el-input type="text" v-model="association.name" :disabled="dialogFormVisible1"></el-input>
        </el-form-item>
         <el-form-item label="社团简介">
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4}"
            placeholder="请输入内容"
            v-model="association.simple_detail" 
            :disabled="dialogFormVisible1"
          ></el-input>
        </el-form-item>
        <el-form-item label="社团详情">
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4}"
            placeholder="请输入内容"
            v-model="association.details"
            :disabled="dialogFormVisible1"
          ></el-input>
        </el-form-item>
         
        <el-form-item v-show="dialogFormVisible1" label="创建时间">
           <el-input type="text" v-model="association.create_time" :disabled="dialogFormVisible1"></el-input>
          <!-- <template slot-scope="scope">
          <span>{{association.create_time}}</span>
        </template> -->
        </el-form-item>
         <el-form-item v-show="dialogFormVisible1" label="更新时间">
           <el-input type="text" v-model="association.update_time" :disabled="dialogFormVisible1"></el-input>
          <!-- <template slot-scope="scope">
          <span>{{association.create_time}}</span>
        </template> -->
        </el-form-item>
         <el-form-item v-show="dialogFormVisible1" label="社长">
           <el-input type="text" v-model="value9" :disabled="dialogFormVisible1"></el-input>
          </el-form-item>
        <el-form-item v-show="!dialogFormVisible1" label="选择社长">
       

          <el-tooltip  placement="top">
            <div slot="content">{{tesTitle}}</div>
          <el-button>{{tes}}</el-button>
          </el-tooltip>
      <el-button type="warning" style="margin-left:22px" @click="showAdminList()">选择社长</el-button>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClear">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createAssociation">创 建</el-button>
        <el-button type="primary" v-else-if="dialogStatus=='update'" @click="toUpdate">修 改</el-button>
      </div>
    </el-dialog>


    <!-- 选择社长的弹窗 -->

 <el-dialog
  title="详情"
  :visible.sync="detailDialogVisible"
  width="60%"
  :before-close="handleClose">
  <el-table
    :data="userList"
    border
      fit
      highlight-current-row
    style="width: 100%">
    <el-table-column
      label="学号"
      prop="username">
    </el-table-column>
    <el-table-column
      label="姓名"
      prop="nickname">
    </el-table-column>
     <el-table-column
      label="班级"
      prop="class">
    </el-table-column>
    <el-table-column
      align="right">
      
      <template slot-scope="scope">
        <el-button
          size="mini"
          @click="handleEdit(scope.$index, scope.row)">选择为社长</el-button>
       
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
      layout="total, sizes, prev, pager, next, jumper"
    ></el-pagination>
    <!-- <el-button @click="detailDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="detailDialogVisible = false">确 定</el-button> -->
  
</el-dialog> 
  </div>
</template>
<script>
export default {
  data() {
    return {
      tes:"无社长",
      tesTitle:"",
      associationDetail:"",
      associationName:"",
      totalCount: 0, //分页组件--数据总条数
      list: [], //表格的数据
      listLoading: false, //数据加载等待动画
      listQuery: {
        pageNum: 1, //页码
        pageRow: 10, //每页条数
        name: ""
      },
      search: '',
      dialogFormVisible: false,
      dialogFormVisible1:false,
      detailDialogVisible:false,
      association: {
        id: "",
        name: "",
        details: "",
        simple_detail: "",
        create_time:"",
        delete_status:"",
        update_time:"",
        old_user_id:"",
        user_id:"",
        is_open:"",
        user_name:""

      },
      value9:"",
      textMap: {
        detail:"详情",
        update: "编辑",
        create: "创建社团"
      },
      dialogStatus: "create",
      userList:[],
    };
  },
  created() {
    this.getList();
  },
  methods: {
   
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      },
    handleEdit(index, row) {
      console.log(row);

      
      if(undefined==row.user_id){
        this.association.old_user_id=this.association.user_id;
        this.association.user_id=row.userId;
        if(this.association.old_user_id==row.user_id){
          this.association.user_id="";
          this.association.old_user_id="";


        }
      
      }else{
        this.association.old_user_id=this.association.user_id;
       this.association.user_id=row.user_id;
       if(this.association.old_user_id==row.userId){
          this.association.user_id="";
          this.association.old_user_id="";


        }
      }

     
      this.association.user_name=row.nickname;
      this.tesTitle="学号:"+row.userId+"-"+"班级:"+row.class;
      this.tes=row.nickname;
      this.detailDialogVisible=false;
      console.log("社团信息")
      console.log(this.association)
      
      },
     
     handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
      handleClear(){
      this.dialogFormVisible = false;
      this.dialogFormVisible1=false;
      this.association.id = "";
      this.association.name = "";
      this.association.details = "";
      this.association.simple_detail = "";
      this.association.create_time="";
      this.association.delete_status="";
      this.association.update_time="";
      this.association.old_user_id="";
      this.association.user_id="";
       this.association.is_open="";



      },
      showAdminList(){
       
       this.getUserList();
       this.detailDialogVisible=true
       

      }
      ,
    changeAdmin(){
this.association.user_id=this.value9;

    },
    getList() {
      //查询社团列表
      if (!this.hasPerm("association:list")) {
        return;
      }
      this.listLoading = true;
      this.api({
        url: "/association/listAssociation",
        method: "get",
        params: this.listQuery
      }).then(data => {
        console.log(data);
        this.listLoading = false;
        this.list = data.list;
        this.totalCount = data.totalCount;
      });
    },
    showCreate() {
      // this.getUserList();
      //显示新增对话框
      this.association.id = "";
      this.association.name = "";
      this.association.details = "";
      this.association.simple_detail = "";
      this.association.create_time="";
      this.association.delete_status="";
      this.association.update_time="";
      this.association.old_user_id="";
      this.association.user_id="";
       this.association.is_open="";
      

      this.dialogStatus = "create";
      this.dialogFormVisible = true;
      this.tes="请选择社长";
      this.tesTitle="";
      console.log("执行了获取用户列表");
    },
    createAssociation() {
      console.log(this.association);
      //保存新社团
      this.api({
        url: "/association/addAssociation",
        method: "post",
        data: this.association
      }).then(() => {
        this.getList();
        this.dialogFormVisible = false;
      });
    },


    showUpdate(s) {

      console.log(s)
      this.association.id=s.id;
      console.log( "我是id"+this.association.id)
      //获取当前社团的详情
     this.api({
        url: "/association/getAssociation",
        method: "post",
        data: this.association
      }).then(res=> {
        console.log("接口结果")
        console.log(res)
      //显示修改对话框
      this.association.id = s.id;
      this.association.name = s.name;
      this.association.details = s.details;
      this.association.simple_detail = s.simple_detail;
      this.delete_status=s.delete_status;
      this.dialogStatus = "update";
      this.dialogFormVisible = true;
      this.association.old_user_id="";
      if("无社长"==s.user_id){
      this.association.user_id="";
      this.tesTitle="未选择社长";
      this.tes="未选择社长";
      }else{
      this.association.user_id=res.user_id;
      this.tesTitle="学号:"+res.user_id+"-"+"班级:"+res.class;
      this.tes=res.userName;
      }
      
      
     
     
      this.checkedUser=res.user_id;
     
      });

      // //显示修改对话框
      // this.association.id = this.list[$index].id;
      // this.association.name = this.list[$index].name;
      // this.association.details = this.list[$index].details;
      // this.association.simple_detail = this.list[$index].simple_detail;
      // this.association.delete_status=this.list[$index].delete_status;
      // this.dialogStatus = "update";
      // this.dialogFormVisible = true;
     
      // this.value9=this.list[$index].username;
      
     
     
      // this.checkedUser=this.list[$index].user_id;
     
    
    },
    //获取用户列表
    getUserList(){
      console.log("测试信息")
      console.log(this.association)
 //获取用户列表，用于选择社长
     this.api(
       {
        url: "/association/getStuList",
        method: "post",
        data:this.association
       }
     ).then(result=>{
       console.log(result.list);
       this.userList=result.list;
       console.log(result)
       this.totalCount = result.totalCount;
 
     })
    },
    updateAssociation() {
      console.log("社团信息11")
      console.log(this.association)
      //修改社团信息
      this.api({
        url: "/association/updateAssociation",
        method: "post",
        data: this.association
      }).then(result => {
        this.$message({
          type: "info",
          message: result.msg
        });
        this.getList();
        this.dialogFormVisible = false;
      });
    },
    toRemoveAssociation(asMsg) {
      console.log(asMsg)

      //删除社团
      this.api({
        url: "/association/updateAssociation",
        method: "post",
        data: asMsg
      }).then(result => {
        this.$message({
          type: "info",
          message: result.msg
        });
        this.getList();
        this.dialogFormVisible = false;
      });
    },
    handleSizeChange(val) {
      //改变每页数量
      this.listQuery.pageRow = val;
      this.handleFilter();
    },
    handleCurrentChange(val) {
      //改变页码
      this.listQuery.pageNum = val;
      this.getList();
    },
    handleFilter() {
      //查询事件
      this.listQuery.pageNum = 1;
      this.getList();
    },
    getIndex($index) {
      //表格序号
      return (this.listQuery.pageNum - 1) * this.listQuery.pageRow + $index + 1;
    },
    //更新社团信息提示框
    toUpdate() {
      this.$confirm("请确认信息？", "确认修改", {
        distinguishCancelAndClose: true,
        confirmButtonText: "保存",
        cancelButtonText: "放弃修改"
      })
        .then(() => {
          this.updateAssociation();
        })
        .catch(action => {
          this.getList();
          this.dialogFormVisible = false;
          this.dialogFormVisible1 = false;
        });
    },
    removeAssociation(index){
      this.$confirm("请确认信息？", "确认删除", {
        distinguishCancelAndClose: true,
        confirmButtonText: "删除",
        cancelButtonText: "取消"
      }).then(()=> {
        this.list[index].delete_status=2;
        let copy={};
       copy=JSON.parse(JSON.stringify(this.list[index]));
       this.toRemoveAssociation(copy);
        }) 
        .catch(action => {
          this.getList();
        });
  


    },
    showDetail($index){
      this.association.id = this.list[$index].id;
      this.association.name = this.list[$index].name;
      this.association.details = this.list[$index].details;
      this.association.simple_detail = this.list[$index].simple_detail;
      this.association.create_time=this.list[$index].create_time;
      this.association.update_time=this.list[$index].update_time;
      this.dialogStatus ="detail"
      this.dialogFormVisible=true;
      this.dialogFormVisible1=true;
     
    }
  

  }
};
</script>