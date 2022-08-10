<script setup lang="ts">
import {computed, onMounted, reactive, ref} from "vue";
import {ElMessage, FormInstance, genFileId, UploadInstance, UploadProps, UploadRawFile} from "element-plus";
import axios from "axios";
import qs from "qs";

//商品列表
const tableData = reactive([])

function upTableData() {
  tableData.length = 0
  axios.get('/address/getAll')
      .then(res => {
        tableData.push(...res.data)
      })
}

onMounted(() => {
  upTableData()
  console.log(tableData)
})

const search = ref('')
const filterTableData = computed(() =>
    tableData.filter(
        (data) =>
            !search.value ||
            data.userName.toLowerCase().includes(search.value.toLowerCase()) ||
            data.consinee.toLowerCase().includes(search.value.toLowerCase())
    )
)

</script>

<template>
  <el-table :data="filterTableData" table-layout="fixed">
    <el-table-column prop="id" label="编号" align="center"/>
    <el-table-column prop="userName" label="用户名"  align="center"/>
    <el-table-column prop="consignee" label="收货人" align="center"/>
    <el-table-column prop="phone" label="电话" align="center"/>
    <el-table-column label="地址" align="center">
      <template #default="scope">
        {{scope.row.region+scope.row.detailAddress}}
      </template>
    </el-table-column>
    <el-table-column prop="label" label="标签" align="center"/>
    <el-table-column align="center">
      <template #header>
        <el-input v-model="search" size="small" placeholder="搜索框"/>
      </template>
    </el-table-column>
  </el-table>
</template>

<style scoped>

</style>
