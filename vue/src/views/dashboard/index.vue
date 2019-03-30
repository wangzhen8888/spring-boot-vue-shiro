
<template>
  <div class="dashboard-container" :style="{backgroundImage: 'url(' + imgUrl + ')',backgroundSize: '100% 100%',
    height: '100%',
    position: 'fixed',
    width: '100%',
    margin:'0 auto' }" >
   <!-- <img :src="imgUrl" style="width:100%;height:100%" > -->
   
   
    
  </div>
</template>
<script>

  export default {
    name: 'dashboard',
    data() {
      return {
        imgUrl:require('@/assets/timgs.png')
      }
    },
  }
</script>


