<template>
  <div class="shetuan">

<div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="primary" icon="plus" @click="showCreate" v-if="hasPerm('shetuan:add')">添加
          </el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        class="small-space"
        :model="association"
        label-position="left"
        label-width="60px"
        style="width: 300px; margin-left:50px;"
      >
        <el-form-item label="社团名称">
          <el-input type="text" v-model="association.name"></el-input>
           
        </el-form-item>
         <el-form-item label="社团详情">
        <el-input type="text" v-model="association.details"></el-input>
         </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createAssociation">创 建</el-button>
        <el-button type="primary" v-else @click="updateArticle">修 改</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      dialogFormVisible: true,
      association: {
        id: "",
        name: "",
        details: ""
      },
        textMap: {
          update: '编辑',
          create: '创建文章'
        },
        dialogStatus: 'create',
    };
  },
  methods: {
    showCreate() {
      //显示新增对话框
      this.association.name = "";
       this.association.details = "";
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
    },
    createAssociation() {
        console.log(this.association)
      //保存新文章
      this.api({
        url: "/association/addAssociation",
        method: "post",
        data: this.association
      }).then(() => {
        this.getList();
        this.dialogFormVisible = false;
      });
    }
  }
};
</script>