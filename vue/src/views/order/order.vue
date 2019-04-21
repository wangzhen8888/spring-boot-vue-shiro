<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true" :model="listQuery" ref="searchForm" class="demo-form-inline">
  <el-form-item label="单位名称" prop="company_name">
    <el-input v-model="listQuery.company_name" placeholder="单位名称" ></el-input>
  </el-form-item>
<el-form-item label="货物名称" prop="goods_name">
    <el-input v-model="listQuery.goods_name" placeholder="货物名称" ></el-input>
  </el-form-item>
   <el-form-item label="货物编码" prop="goods_id">
    <el-input v-model="listQuery.goods_id" placeholder="货物编码" ></el-input>
  </el-form-item>
  <el-form-item label="订单类型" prop="orderType">
    <el-select v-model="listQuery.orderType" placeholder="订单类型">
      <el-option label="入库" value="0"></el-option>
      <el-option label="出库" value="1"></el-option>
        <el-option label="报废" value="2"></el-option>
          <el-option label="缺货" value="3"></el-option>
    </el-select>
  </el-form-item>
   
   <el-form-item >
     <el-button type="primary" @click="onReset">重置</el-button>
    <el-button type="primary" @click="onSearch">查询</el-button>

          <el-button type="primary" icon="plus" @click="importExcel" v-if="hasPerm('goods:add')">导出
          </el-button>
  
  </el-form-item>
  <el-form-item label="订单日期" prop="dateTime">
    <div class="block">
  
    <el-date-picker
      v-model="dateTime"
      type="datetimerange"
      range-separator="至"
      start-placeholder="开始日期"
      end-placeholder="结束日期"
      @change="selectDateTime"
      value-format="yyyy.MM.dd HH.mm.ss"
      >
    </el-date-picker>
  </div>
  </el-form-item>
 
</el-form>
    </div>
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row>
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="goods_name" label="货物名称" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="goods_id" label="货物编号" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="goods_num" label="货物数量" style="width: 60px;"></el-table-column>
       <el-table-column align="center" prop="company_name" label="单位/个人" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="company_phone" label="联系方式" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="user_name" label="操作人" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="marks" label="备注" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="goods_type" label="类型" style="width: 60px;">
     
       <template slot-scope="scope">
       <el-tag type="success" v-show="scope.row.order_type==0">入库</el-tag>
         <el-tag type="success" v-show="scope.row.order_type==1">出库</el-tag>
          <el-tag type="success" v-show="scope.row.order_type==2">报废</el-tag>
           <el-tag type="success" v-show="scope.row.order_type==3">缺货</el-tag>
        </template>

</el-table-column>
      <el-table-column align="center" label="创建时间" width="170">
        <template slot-scope="scope">
          <span>{{scope.row.createTime}}</span>
        </template>
      </el-table-column>
       <el-table-column align="center" label="更新时间" width="170">
        <template slot-scope="scope">
          <span>{{scope.row.updateTime}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="marks" label="备注" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="管理" width="200" v-if="hasPerm('order:update')">
        <template slot-scope="scope">
          <el-button type="warning" icon="edit" v-if="scope.row.order_type==3" @click="showUpdate(scope.$index)">待补货</el-button>
          <el-button type="primary" icon="edit" disabled v-else @click="showUpdate(scope.$index)">已处理</el-button>
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
    </el-pagination>
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="tempArticle" label-position="left" label-width="60px"
               style='width: 300px; margin-left:50px;'>
               <el-form-item label="">
          <el-tag type="danger">请确保货物库存充足，否则将补货失败</el-tag>
          </el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input type="text" v-model="order.marks">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary"  @click="updateOrder">补发</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        totalCount: 0, //分页组件--数据总条数
         formInline: {
          user: '',
          region: ''
        },
       dateTime:[],

        
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          pageNum: 1,//页码
          pageRow: 10,//每页条数
          goods_name: '',
          orderType:"",
          goods_id:"",
          company_name:"",
          start_time:"",
          end_time:""
        },
        order:{
          id:"",
          goods_id:"",
          goods_num:"",
          orderType:"",
          marks:""
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
      this.getList();
    },
    methods: {
      importExcel(){
       this.api({
          url: "/excel/getUser",
          method: "post",
          params: this.listQuery,
          responseType: 'blob', //二进制流
        }).then(response  => {
          console.log(typeof response);
      var a = document.createElement('a');
      var url = window.URL.createObjectURL(response);
      a.href = url;
      a.download = '用户统计信息.xls';
      a.click();
      window.URL.revokeObjectURL(url);
        })
      },
      selectDateTime(){
       this.listQuery.start_time=this.dateTime[0]
       this.listQuery.end_time=this.dateTime[1]
      },
       onSearch() {
         console.log(this.listQuery)
        this.getList();
    
      },
      onReset() {
       this.dateTime=[]
       this.selectDateTime()
      this.$refs.searchForm.resetFields();
      this.getList();
      },
      getList() {
        //查询列表
        if (!this.hasPerm('order:list')) {
          return
        }
        this.listLoading = true;
        this.api({
          url: "/order/listOrder",
          method: "get",
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
      showUpdate($index) {
        console.log(this.list)
        //显示修改对话框
        this.order.id = this.list[$index].id;
        this.order.marks=this.list[$index].marks;
        this.order.goods_num=this.list[$index].goods_num;
        this.order.orderType=1;
        this.order.goods_id=this.list[$index].goods_id;
        this.dialogStatus = "update"
        this.dialogFormVisible = true
        console.log(this.order)
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
      updateOrder() {
        //修改文章
        this.api({
          url: "/order/updateOrder",
          method: "post",
          data: this.order
        }).then(res => {
        console.log(res)
        this.$message({
          message: res.msg,
          type: 'warning'
        });
          this.getList();
          this.dialogFormVisible = false
        })
      },
    }
  }
</script>
