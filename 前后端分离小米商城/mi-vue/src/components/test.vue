<template>
  <el-button :plain="true" @click="open">Show message</el-button>
</template>

<script lang="ts" setup>
import { ElMessage } from 'element-plus'
// import 'element-plus/es/components/form/style/css'
// import 'element-plus/es/components/message/style/css'

const open = () => {
  ElMessage({
    message: 'this is a message.',
    grouping: true,
    type: 'success',
  })
}
</script>
