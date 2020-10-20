<template>
  <div class="container">
    <div style="margin-top: 15px;">
      <el-input placeholder="请输入内容" v-model="input3" class="input-with-select">
        <p  slot="prepend" style="margin: 0;padding: 0">sxy</p>
        <el-button slot="append" >发表</el-button>
      </el-input>
    </div>
  </div>
</template>

<script>
  export default {
    name:"Register"
  }
</script>

<style scoped>

</style>
