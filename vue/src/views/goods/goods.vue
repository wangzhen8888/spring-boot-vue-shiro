<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="primary" icon="plus" @click="showCreate" v-if="hasPerm('goods:add')">添加
          </el-button>
        </el-form-item>
        <!-- <el-form-item>
          <el-button type="primary" icon="plus" @click="importExcel" v-if="hasPerm('goods:add')">导出出入库记录
          </el-button>
        </el-form-item> -->
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
          <el-button size="mini" type="primary" icon="edit" @click="showUpdate(scope.$index)">修改</el-button>
          <el-button size="mini" type="primary" icon="edit" @click="deleteGoods(scope.row)">删除</el-button>
          <el-button size="mini" type="primary" icon="edit" @click="adminGoods(scope.row)">出入库</el-button>
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
      <el-form class="small-space" :model="tempGoods" label-position="left" :rules="rules" ref="goodsForm" label-width="80px"
               style='width: 300px; margin-left:50px;'>
        <el-form-item label="货物名称" prop="goods_name">
          <el-input type="text" v-model="tempGoods.goods_name">
          </el-input>
        </el-form-item>
         <el-form-item label="货物数量" prop="goods_num">
          <el-input type="text" v-model="tempGoods.goods_num">
          </el-input>
        </el-form-item>
        <el-form-item label="货物价格" prop="goods_price">
          <el-input type="text" v-model="tempGoods.goods_price">
          </el-input>
        </el-form-item>
     
       
         
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleColse">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createGoods">创 建</el-button>
        <el-button type="primary" v-else @click="updateGoods">修 改</el-button>
      </div>
    </el-dialog>
      <!-- 出入库弹窗 -->
     <el-dialog title="商品出入库管理" :visible.sync="dialogGoodsVisible">
      <el-form class="small-space" :model="goodsOrder" label-position="left"  ref="goodsAdmin"  label-width="120px"
               style='width: 600px; margin-left:50px;'>
        <el-form-item label="货物名称" prop="goods_name">
          <el-input type="text" :disabled="disFlag" v-model="goodsOrder.goods_name">
          </el-input>
        </el-form-item>
         <el-form-item label="货物总数量" prop="goods_totalNum">
          <el-input type="text" :disabled="disFlag" v-model="goodsOrder.goods_totalNum">
          </el-input>
        </el-form-item>
        <el-form-item label="货物价格" prop="goods_price">
          <el-input type="text" :disabled="disFlag" v-model="goodsOrder.goods_price">
          </el-input>
        </el-form-item>
         <el-form-item label="订单类型" prop="order_type">
          <template>
          <el-select v-model="orderType" placeholder="请选择" @change="selectOrderType" >
           <el-option
           v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
          </el-option>
         </el-select>
        </template>
          </el-input>
        </el-form-item>
         <el-form-item label="货物数量" prop="goods_num">
          <el-input type="text"  v-model="goodsOrder.goods_num">
          </el-input>
        </el-form-item>
      
        <el-form-item label="公司名称" prop="company_name">
          <el-input type="text"  v-model="goodsOrder.company_name">
          </el-input>
        </el-form-item>
        <el-form-item label="公司电话" prop="company_phone">
          <el-input type="text"  v-model="goodsOrder.company_phone">
          </el-input>
        </el-form-item>
          <el-form-item label="备注" prop="marks">
          <el-input type="textarea"  v-model="goodsOrder.marks">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleColse1">取 消</el-button>
        <el-button  type="success" @click="createOrder">创 建</el-button>
     
      </div>
    </el-dialog>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        totalCount: 0, //分页组件--数据总条数
        disFlag:true,


        
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        flag:"",
        listQuery: {
          pageNum: 1,//页码
          pageRow: 10,//每页条数
          name: ''
        },
        dialogStatus: 'create',
        dialogFormVisible: false,
        dialogGoodsVisible:false,
        textMap: {
          update: '编辑',
          create: '创建货物'


        },
        tempGoods: {
          id: "",
         goods_name: "",
         goods_num:"",
         goods_price:"",
         goods_type:"",
         create_time:"",
         update_time:"",
         delete_status:""
        },
        goodsOrder:{
          user_id:"",
          user_name:"",
          goods_id:"",
          goods_name:"",
          goods_price:"",
          goods_totalNum:"",
          goods_num:"",
         
          company_name:"",
          company_phone:"",
          order_type:"",
          marks:""






        },
        options:[
         {
          value: '0',
          label: '入库'
        }, {
          value: '1',
          label: '出库'
        }, {
          value: '2',
          label: '报废'
        }, {
          value: '3',
          label: '缺货'
        }
        ],
        orderType:"",
        rules:{
          goods_num:[{
     validator:(rule,value,callback)=>{
         if(value != ""){
             if((/^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/).test(value) == false){
                 callback(new Error("请填写大于0的数字"));
             }else{
                 callback();
             }
         }else{
             callback();
         }

     },
     trigger:'change'
 }]
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
      selectOrderType(){
       if(this.orderType==0){
         this.flag=false;
       }else{
         this.flag=true;
       }
       
        this.goodsOrder.order_type=this.orderType;
        console.log(this.goodsOrder.order_type)

      },
      getList() {
        //查询列表
        if (!this.hasPerm('goods:list')) {
          return
        }
        this.listLoading = true;
        this.api({
          url: "/goods/listGoods",
          method: "get",
          params: this.listQuery
        }).then(data => {
          console.log(data);
          this.listLoading = false;
          this.list = data.list;
          this.totalCount = data.totalCount;
        })
      },
      handleColse(){

        this.dialogFormVisible = false;
        this.$refs.goodsForm.resetFields();
 
      

      },
       handleColse1(){

        this.dialogGoodsVisible = false;
        this.orderType="";
        this.$refs.goodsAdmin.resetFields();
 
      

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
      
        this.tempGoods.id = "";
        this.tempGoods.goods_name = "";
        this.tempGoods.goods_num = "";
        this.tempGoods.goods_price = "";
        this.dialogStatus = "create"
        this.dialogFormVisible = true
      },
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
      deleteGoods(index) {
        this.tempGoods.id=index.id;
        this.$confirm('此操作删除该货物, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.api({
          url: "/goods/deleteGoods",
          method: "post",
          data: this.tempGoods
        }).then(() => {
          this.getList();
        this.$message({
            type: 'success',
            message: '删除成功!'
          });
        })
         
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
      },
    
      showUpdate($index) {
        //显示修改对话框
        this.tempGoods.id = this.list[$index].id;
        this.tempGoods.goods_name = this.list[$index].goods_name;
        this.tempGoods.goods_num = this.list[$index].goods_num;
        this.tempGoods.goods_price = this.list[$index].goods_price;
 
      
        this.dialogStatus = "update"
        this.dialogFormVisible = true
      },
      createGoods() {
        //保存新货物
        this.api({
          url: "/goods/addGoods",
          method: "post",
          data: this.tempGoods
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false
        })
      },
      createOrder(){
 //保存新的盘点记录
        this.api({
          url: "/order/addOrder",
          method: "post",
          data: this.goodsOrder
        }).then(res => {
          this.$message({
            type: 'success',
            message: res.msg
          });
          this.getList();
          this.dialogGoodsVisible = false;
          this.orderType="";
        this.$refs.goodsAdmin.resetFields();
        })
      },
       handleFilter() {
        //查询事件
        this.listQuery.pageNum = 1
        this.getList()
      },
      updateGoods() {
        //修改文章
        this.api({
          url: "/goods/updateGoods",
          method: "post",
          data: this.tempGoods
        }).then(() => {
        
          this.getList();
          this.dialogFormVisible = false
        })
      },
      adminGoods(index){
this.order_type="";
     this.goodsOrder.user_name=this.user.nickname;
     this.goodsOrder.user_id=this.user.userId;
     this.goodsOrder.goods_name=index.goods_name;
     this.goodsOrder.goods_price=index.goods_price;
     this.goodsOrder.goods_totalNum=index.goods_num;
     this.goodsOrder.goods_id=index.id;
    
     this.dialogGoodsVisible=true;
      }
    }
  }
</script>
