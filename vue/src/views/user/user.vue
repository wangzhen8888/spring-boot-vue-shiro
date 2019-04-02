<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="6">
        <div class="filter-container" >
          <el-form>
            <el-form-item>
              <el-button
                type="primary"
                icon="plus"
                v-if="hasPerm('user:add')"
                @click="showCreate"
              >添加新用户</el-button>
              <el-button
                type="primary"
                icon="plus"
                v-if="hasPerm('user:add')"
                @click="getList()"
              >查询</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-col>
      <el-col :span="6">
        <el-container  style="height: 120px;width:600px; ">
  <el-aside style="width:120px;"><div><el-tag style="text-align: center;" type="primary">选择学生账号文件</el-tag></div></el-aside>
  <el-main style="width:300px"><div style="float:left">
        <el-upload
          class="upload-demo"
          ref="upload"
          :action="doUpload()"
          :limit="1"
          :file-list="fileList"
          :before-upload="beforeUpload"
        >
        
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          <a href="./static/moban.xlsx" rel="external nofollow" download="模板">
            <el-button size="small" type="success">下载模板</el-button>
          </a>


          <div slot="tip" class="el-upload__tip">只能上传excel文件，且不超过5MB</div>
          <div slot="tip" class="el-upload-list__item-name">{{fileName}}</div>
        </el-upload>
        </div>
       <!-- <el-button @click="visible = false">取消</el-button> -->
       <div>
        <el-button style="float:left" type="primary" @click="submitUpload()">开始上传</el-button>
        </div></el-main>
</el-container>
        
      </el-col>
      <el-col :span="6">
         <span slot="footer" class="dialog-footer"></span>
        
      </el-col>
      <el-col :span="6">
        <div class="grid-content bg-purple"></div>
      </el-col>
    </el-row>

    <el-table
      :data="list"
      v-loading.body="listLoading"
      element-loading-text="拼命加载中"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="序号" width="35">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"></span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="姓名" prop="nickname" width="80"></el-table-column>
      <el-table-column align="center" label="用户名" prop="username" width="150"></el-table-column>
      <el-table-column align="center" label="角色类型" width="120px">
        <template slot-scope="scope">
          <el-tag type="success" v-text="scope.row.roleName" v-if="scope.row.roleId===1"></el-tag>
          <el-tag type="primary" v-text="scope.row.roleName" v-else></el-tag>
        </template>
      </el-table-column>
       <el-table-column align="center" label="班级" prop="class" width="170"></el-table-column>
      <el-table-column align="center" label="创建时间" prop="createTime" width="170"></el-table-column>
      <el-table-column align="center" label="最近修改时间" prop="updateTime" width="170"></el-table-column>
      <el-table-column align="center" label="管理" width="220" v-if="hasPerm('user:update')">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)">修改</el-button>
          <el-button
            type="danger"
            icon="delete"
            v-if="scope.row.userId!=userId&&hasPerm('user:delete') "
            @click="removeUser(scope.$index)"
          >删除</el-button>
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
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        class="small-space"
        :model="tempUser"
        label-position="left"  
        label-width="80px"
        style="width: 300px; margin-left:50px;"
      >
        <el-form-item label="用户名" required v-if="dialogStatus=='create'">
          <el-input type="text" v-model="tempUser.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" v-if="dialogStatus=='create'" required>
          <el-input type="password" v-model="tempUser.password"></el-input>
        </el-form-item>
        <el-form-item label="新密码" v-else>
          <el-input type="password" v-model="tempUser.password" placeholder="不填则表示不修改"></el-input>
        </el-form-item>
        <el-form-item label="角色" required>
          <el-select v-model="tempUser.roleId" placeholder="请选择">
            <el-option
              v-for="item in roles"
              :key="item.roleId"
              :label="item.roleName"
              :value="item.roleId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="昵称" required>
          <el-input type="text" v-model="tempUser.nickname"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createUser">创 建</el-button>
        <el-button type="primary" v-else @click="updateUser">修 改</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { mapGetters } from "vuex";

export default {
  data() {
    return {
      fileList: [],
      fileName: "",
      totalCount: 0, //分页组件--数据总条数
      list: [], //表格的数据
      listLoading: false, //数据加载等待动画
      listQuery: {
        stuNum:"",  
        pageNum: 1, //页码
        pageRow: 50 //每页条数
      },
      roles: [], //角色列表
      dialogStatus: "create",
      dialogFormVisible: false,
      textMap: {
        update: "编辑",
        create: "新建用户"
      },
      tempUser: {
        username: "",
        password: "",
        nickname: "",
        roleId: "",
        userId: ""
      }
    };
  },
  created() {
    this.getList();
    if (this.hasPerm("user:add") || this.hasPerm("user:update")) {
      this.getAllRoles();
    }
  },
  computed: {
    ...mapGetters(["userId"])
  },
  methods: {
    doUpload() {
      return "http:cccc.com";
    },
    beforeUpload(file) {
      // debugger
      console.log(file, "文件");
      this.files = file;
      const extension = file.name.split(".")[1] === "xls";
      const extension2 = file.name.split(".")[1] === "xlsx";
      const isLt2M = file.size / 1024 / 1024 < 5;
      if (!extension && !extension2) {
        this.$message.warning("上传模板只能是 xls、xlsx格式!");
        return;
      }
      if (!isLt2M) {
        this.$message.warning("上传模板大小不能超过 5MB!");
        return;
      }
      this.fileName = file.name;
      return false; // 返回false不会自动上传
    },
    submitUpload() {
      // debugger
      console.log("上传" + this.filesName);
      if (this.fileName == "") {
        this.$message.warning("请选择要上传的文件！");
        return false;
      }
      let file = new FormData();
      file.append("file", this.files, this.fileName); //filename是键，file是值，就是要传的文件，test.zip是要传的文件名
      let requestConfig = {
        headers: {
          "Content-Type": "multipart/form-data"
        }
      };
  
      this.api.post(`/user/upload`, file, requestConfig).then(res => {
       
          this.$message({
            message: "操作成功",
            type: "success",
             duration: 1500,
            onClose: () => {
              console.log("执行了上传");
              this.visible = false;
              this.$emit("refreshDataList");
              this.$refs.upload.clearFiles();
              this.fileName=""
            }
          });

      });
    },
    getAllRoles() {
      this.api({
        url: "/user/getAllRoles",
        method: "get"
      }).then(data => {
        this.roles = data.list;
      });
    },
    getList() {
      console.log(this.listQuery)
      //查询列表
      this.listLoading = true;
      this.api({
        url: "/user/list",
        method: "get",
        params: this.listQuery
      }).then(data => {
        this.listLoading = false;
        console.log(data.list);
        this.list = data.list;
        this.totalCount = data.totalCount;
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
    showCreate() {
      //显示新增对话框
      this.tempUser.username = "";
      this.tempUser.password = "";
      this.tempUser.nickname = "";
      this.tempUser.roleId = "";
      this.tempUser.userId = "";
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
    },
    showUpdate($index) {
      let user = this.list[$index];
      this.tempUser.username = user.username;
      this.tempUser.nickname = user.nickname;
      this.tempUser.roleId = user.roleId;
      this.tempUser.userId = user.userId;
      this.tempUser.deleteStatus = "1";
      this.tempUser.password = "";
      this.dialogStatus = "update";
      this.dialogFormVisible = true;
    },
    createUser() {
      //添加新用户
      this.api({
        url: "/user/addUser",
        method: "post",
        data: this.tempUser
      }).then(() => {
        this.getList();
        this.dialogFormVisible = false;
      });
    },
    updateUser() {
      //修改用户信息
      let _vue = this;
      this.api({
        url: "/user/updateUser",
        method: "post",
        data: this.tempUser
      }).then(() => {
        let msg = "修改成功";
        this.dialogFormVisible = false;
        if (this.userId === this.tempUser.userId) {
          msg = "修改成功,部分信息重新登录后生效";
        }
        this.$message({
          message: msg,
          type: "success",
          duration: 1 * 1000,
          onClose: () => {
            _vue.getList();
          }
        });
      });
    },
    removeUser($index) {
      let _vue = this;
      this.$confirm("确定删除此用户?", "提示", {
        confirmButtonText: "确定",
        showCancelButton: false,
        type: "warning"
      }).then(() => {
        let user = _vue.list[$index];
        user.deleteStatus = "2";
        _vue
          .api({
            url: "/user/updateUser",
            method: "post",
            data: user
          })
          .then(() => {
            _vue.getList();
          })
          .catch(() => {
            _vue.$message.error("删除失败");
          });
      });
    }
  }
};
</script>
<style scoped>
.el-aside {
    
    color: #333;
    text-align: center;
    line-height: 70px;
  }
</style>

