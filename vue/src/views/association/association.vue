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
        此时间段没有社团开放申请
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
          <span>{{scope.row.createTime}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="管理" width="250" v-if="hasPerm('association:admin')">
        <template slot-scope="scope">
          <el-button
            type="info"
            icon="info"
            size="mini"
            v-if="hasPerm('association:delete') "
            @click="showDetail(scope.$index)"
          >详情</el-button>
          <el-button
            type="danger"
            icon="delete"
            size="mini"
            v-if="hasPerm('association:delete') "
            @click="removeAssociation(scope.$index)"
          >删除</el-button>
          <el-button type="primary" icon="edit" size="mini" @click="showUpdate(scope.$index)">修改</el-button>
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
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        class="small-space"
        :model="association"
        label-position="left"
        label-width="70px"
        style="width: 300px; margin-left:80px;"
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createAssociation">创 建</el-button>
        <el-button type="primary" v-else @click="toUpdate">修 改</el-button>
      </div>
    </el-dialog>
   
  </div>
</template>
<script>
export default {
  data() {
    return {
      totalCount: 0, //分页组件--数据总条数
      list: [], //表格的数据
      listLoading: false, //数据加载等待动画
      listQuery: {
        pageNum: 1, //页码
        pageRow: 10, //每页条数
        name: ""
      },
      dialogFormVisible: false,
      dialogFormVisible1:false,
      association: {
        id: "",
        name: "",
        details: "",
        simple_detail: ""
      },
      textMap: {
        update: "编辑",
        create: "创建社团"
      },
      dialogStatus: "create"
    };
  },
  created() {
    this.getList();
  },
  methods: {
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
      //显示新增对话框
      this.association.name = "";
      this.association.details = "";
      this.association.simple_detail = "";
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
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
    showUpdate($index) {
      //显示修改对话框
      this.association.id = this.list[$index].id;
      this.association.name = this.list[$index].name;
      this.association.details = this.list[$index].details;
      this.association.simple_detail = this.list[$index].simple_detail;
      this.dialogStatus = "update";
      this.dialogFormVisible = true;
    },
    updateAssociation() {
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
        });
    },
    removeAssociation(index){
      this.$confirm("请确认信息？", "确认删除", {
        distinguishCancelAndClose: true,
        confirmButtonText: "删除",
        cancelButtonText: "取消"
      }).then(()=> {
        this.list[index].delete_status=2;
        this.association=JSON.parse(JSON.stringify(this.list[index]));
        console.log(this.association)
         this.updateAssociation();
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
      this.dialogFormVisible=true
this.dialogFormVisible1=true
    }
  

  }
};
</script>