<style scoped>
  h2{
    text-align: center;
    padding: 30px;
    font-size: 18px;
  }
  #myChart{
    width: 100%;
    height: 500px;
    border: 1px solid red;
    margin: 0 auto;
  }
</style>
<template>
  <div>
    <h2>最近一个月资金出入记录</h2>
    <div id="myChart">
 
    </div>
  </div>
</template>
 
<script>

  export default {

    data() {
      return {
        list:{}
      }
    },
       created() {
  
    },
    mounted() {
       this.getList();
  

    },
    methods: {
     
     SetEchart(cs){
       console.log(cs)
        // 基于准备好的dom，初始化echarts实例
        let myChart = this.$echarts.init(document.getElementById('myChart'))
        // 绘制图表
        
     
      var option={
    title: {
        text: '最近资金记录'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data:['支出','收入']
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    toolbox: {
        feature: {
            saveAsImage: {}
        }
    },
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: ""
    },
    yAxis: {
        type: 'value'
    },
    series: [
        {
            name:'支出',
            type:'line',
            stack: '总量',
            data:""
        },
        {
            name:'收入',
            type:'line',
            stack: '总量',
            data:""
        },
       
    ]
}
console.log(cs)
option.xAxis.data=this.list.dateTime;
option.series[0].data=this.list.dels;
option.series[1].data=this.list.adds;
  myChart.setOption(option);
  
        window.addEventListener('resize',function() {myChart.resize()});
    },
      getList() {
        //查询列表
        
     
        this.api({
          url: "/inventoryAdmin/selectOrderList",
          method: "get",
          params: this.listQuery
        }).then(data => {
          
          this.list = data;
          console.log(this.list)
          this.SetEchart(data)
          
        })
      }


    },
    watch: {},
 
  }
</script>
