<template>
  <div class="app-container">
    <div class="filter-container">
   
      <el-form :inline="true" :model="listQuery" ref="searchForm" class="demo-form-inline">
  <el-form-item label="操作人" prop="user_name">
    <el-input v-model="listQuery.user_name" placeholder="请输入操作人姓名" ></el-input>
  </el-form-item>
<el-form-item label="货物名称" prop="goods_name">
    <el-input v-model="listQuery.goods_name" placeholder="请输入货物名称" ></el-input>
  </el-form-item>
   <el-form-item label="货物编码" prop="goods_id">
    <el-input v-model="listQuery.goods_id" placeholder="请输入货物编码" ></el-input>
  </el-form-item>
  <el-form-item label="盘点日期" prop="dateTime">
    <div class="block">
  
    <el-date-picker
      v-model="dateTime1"
      type="datetimerange"
      range-separator="至"
      start-placeholder="开始日期"
      end-placeholder="结束日期"
      @change="selectTime"
      value-format="yyyy.MM.dd HH.mm.ss"
      >
    </el-date-picker>
  </div>
  </el-form-item>
   <el-form-item >
     <el-button type="primary" @click="onReset">重置</el-button>
    <el-button type="primary" @click="onSearch">查询</el-button>
    <el-button type="primary" icon="plus" @click="showCreate" v-if="hasPerm('inventoryAdmin:add')">开始盘点
          </el-button>
          <!-- <el-button type="primary" icon="plus" @click="importExcel" v-if="hasPerm('goods:add')">导出
          </el-button> -->
  
  </el-form-item>
  
 
</el-form>
    </div>
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row>
      <el-table-column align="center" label="序号" width="60">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="goods_name" prop="goods_name" label="货物名称" width="80"></el-table-column>
      <el-table-column align="goods_name" prop="goods_id" label="货物编号" width="80"></el-table-column>
       <el-table-column align="goods_name" prop="goods_price" label="商品单价" width="80"></el-table-column>
      <el-table-column align="goods_name" prop="user_name" label="操作人" width="80"></el-table-column>
      <el-table-column align="goods_name" prop="reduce_num" label="出库数量/收入" style="width: 80px;">
        <template slot-scope="scope">
          <el-tag type="success">数量：{{scope.row.reduce_num}}</el-tag><el-tag type="success">收入：{{scope.row.reduce_num*scope.row.goods_price}}</el-tag>
         
        </template>
      </el-table-column>
      <el-table-column align="goods_name" prop="add_num" label="入库数量/支出" style="width: 80px;">
             <template slot-scope="scope">
          <el-tag type="danger">数量：{{scope.row.add_num}}</el-tag><el-tag type="danger">支出：{{scope.row.add_num*scope.row.goods_price}}</el-tag>
         
        </template>
      </el-table-column>
      <el-table-column align="goods_name" prop="scrap_num" label="报废数量" style="width: 80px;">
         <template slot-scope="scope">
          <el-tag type="warning">数量：{{scope.row.scrap_num}}</el-tag><el-tag type="warning">支出：{{scope.row.scrap_num*scope.row.goods_price}}</el-tag>
         
        </template>
      </el-table-column>
       <el-table-column align="center" label="盘点开始日期" width="170">
        <template slot-scope="scope">
          <span>{{scope.row.start_time}}</span>
        </template>
      </el-table-column>
       <el-table-column align="center" label="盘点结束日期" width="170">
        <template slot-scope="scope">
          <span>{{scope.row.end_time}}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="创建时间" width="150">
        <template slot-scope="scope">
          <span>{{scope.row.createTime}}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column align="center" label="管理" width="200" v-if="hasPerm('inventoryAdmin:update')">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)">修改</el-button>
        </template>
      </el-table-column> -->
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
    <!-- 新增盘点记录弹窗 -->
    <el-dialog title="新增盘点记录" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="inventoryadmin"  label-position="left" label-width="80px"
               style='width: 600px; margin-left:50px;'>
        <el-form-item label="选择货物" prop="goods_name">
          <el-tag type="info" v-if="inventoryadmin.goods_name!=''" >{{inventoryadmin.goods_name}}</el-tag>
          <el-button type="warning" size="mini" @click="selectGoods">选择货物</el-button>
        </el-form-item>
         </el-form-item>
        <el-form-item label="货物单价" prop="goods_price">
          <el-tag type="success" v-if="inventoryadmin.goods_price!=''">{{inventoryadmin.goods_price}}元</el-tag>
        </el-form-item>
        <el-form-item label="选择日期" prop="">
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
        <el-form-item label="出库数量">
          <el-input type="text" disabled v-model="inventoryadmin.reduce_num"  >
          </el-input>
        </el-form-item>
        <el-form-item label="收入资金">
           <el-tag type="success" v-if="inventoryadmin.goods_price!=''&&inventoryadmin.reduce_num!=''">{{inventoryadmin.goods_price*inventoryadmin.reduce_num}}元</el-tag>
        </el-form-item>
         <el-form-item label="入库数量">
          <el-input type="text" disabled v-model="inventoryadmin.add_num">
          </el-input>
        </el-form-item>
        <el-form-item label="支出资金">
           <el-tag type="success" v-if="inventoryadmin.goods_price!=''&&inventoryadmin.add_num!=''">{{inventoryadmin.goods_price*inventoryadmin.add_num}}元</el-tag>
   
        </el-form-item>
         <el-form-item label="报废数量">
          <el-input type="text" disabled v-model="inventoryadmin.scrap_num">
          </el-input>
        </el-form-item>
         <el-form-item label="报废资金">
          <el-tag type="success" v-if="inventoryadmin.goods_price!=''&&inventoryadmin.scrap_num!=''">{{inventoryadmin.goods_price*inventoryadmin.scrap_num}}元</el-tag>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createInventoryadmin">创 建</el-button>
        <el-button type="primary" v-else @click="updateArticle">修 改</el-button>
      </div>
    </el-dialog>

  <!-- 选择货物弹窗 -->
    <el-dialog title="选择货物" :visible.sync="dialogGoodsVisible">
      <el-table :data="goodsList" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row>
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="goods_name" label="货物名称" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="goods_price" label="货物价格" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="goods_num" label="库存数量" style="width: 60px;"></el-table-column>
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
      <el-table-column align="center" label="管理" width="250" v-if="hasPerm('goods:update')">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" icon="edit" @click="changeGoods(scope.row)">选择</el-button>
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
    </el-dialog> 
  </div>
</template>
<script>
  export default {
    data() {
      return {
        totalCount: 0, //分页组件--数据总条数
         goodsList:[],
         dateTime:[],
         dateTime1:[],
        
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          pageNum: 1,//页码
          pageRow: 10,//每页条数
          goods_id:"",
          goods_name:"",
          user_name:"",
          start_time:"",
          end_time:""

          
        },
      
        listGoodsQuery:{
           pageNum: 1,//页码
          pageRow: 10,//每页条数
          name: ''
        },
        inventoryadmin:{
          goods_id:"",
          goods_name:"",
          add_num:"",
          reduce_num:"",
          goods_price:"",
          start_time:"",
          end_time:"",
          scrap_num:"",
          user_name:"",
          user_id:"",
      


        },
        dialogStatus: 'create',
        dialogFormVisible: false,
        dialogGoodsVisible:false,
        
        temp: {
          id: "",
          content: "未选择"
        }
      }
    },
    created() {
      this.getList();
    },
    computed: {
      user(){

        return this.$store.state.user
      }
    },
    methods: {
      importExcel(){
       this.api({
          url: "/excel/getUser",
          method: "get",
          data: this.tempGoods,
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
       onSearch() {
         console.log(this.listQuery)
        this.getList();
    
      },
      onReset() {
       this.dateTime1=[]
       this.selectTime()
      this.$refs.searchForm.resetFields();
      this.getList();
      },
      selectTime(){
       this.listQuery.start_time=this.dateTime1[0]
       this.listQuery.end_time=this.dateTime1[1]
       console.log(this.listQuery)
      },
      selectDateTime(){
        this.inventoryadmin.start_time=this.dateTime[0];
        this.inventoryadmin.end_time=this.dateTime[1];
          console.log( this.inventoryadmin)
          //查询列表
        if (!this.hasPerm('order:list')) {
          return
        }
        this.listLoading = true;
        this.api({
          url: "/order/orderMsg",
          method: "get",
          params: this.inventoryadmin
        }).then(data => {
          this.inventoryadmin.reduce_num=data.reduce_num;
          this.inventoryadmin.scrap_num=data.scrap_num;
          this.inventoryadmin.add_num=data.add_num;
          console.log(data);
           this.listLoading = false;
        })

       


      },
      changeGoods(index){
        this.inventoryadmin.goods_id=index.id;
        this.inventoryadmin.goods_name=index.goods_name;
        this.inventoryadmin.goods_price=index.goods_price;
        this.dialogGoodsVisible=false;

   
      console.log(this.inventoryadmin)
      },
      selectGoods(){
        this.dialogGoodsVisible=true;
       this.getGoodsList();
      },
      getGoodsList() {
        //查询列表
        if (!this.hasPerm('goods:list')) {
          return
        }
        this.listLoading = true;
        this.api({
          url: "/goods/listGoods",
          method: "get",
          params: this.listGoodsQuery
        }).then(data => {
          console.log(data);
          this.listLoading = false;
          this.goodsList = data.list;
          this.totalCount = data.totalCount;
        })
      },
      getList() {
        //查询列表
        if (!this.hasPerm('inventoryAdmin:list')) {
          return
        }
        this.listLoading = true;
        this.api({
          url: "/inventoryAdmin/listInventoryAdmin",
          method: "get",
          params: this.listQuery
        }).then(data => {
          console.log(data.list)
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
        
        this.inventoryadmin.goods_id = "";
        this.inventoryadmin.goods_name = "";
        this.inventoryadmin.add_num = "";
        this.inventoryadmin.goods_price = "";
        this.inventoryadmin.start_time = "";
        this.inventoryadmin.end_time = "";
        this.inventoryadmin.scrap_num = "";
        this.inventoryadmin.user_name = this.user.nickname;
        this.inventoryadmin.user_id = this.user.userId;
        this.inventoryadmin.reduce_num = "";
        
        this.dateTime=[];
        this.dialogStatus = "create"
        this.dialogFormVisible = true
      },
      showUpdate($index) {
        //显示修改对话框
        this.temp.id = this.list[$index].id;
        this.temp.content = this.list[$index].content;
        this.dialogStatus = "update"
        this.dialogFormVisible = true
      },
      createInventoryadmin() {
        //保存盘点记录
        this.api({
          url: "/inventoryAdmin/addInventoryAdmin",
          method: "post",
          data: this.inventoryadmin
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
          data: this.temp
        }).then(() => {
        
          this.getList();
          this.dialogFormVisible = false
        })
      },
    }
  }
</script>
