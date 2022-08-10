<script setup lang="ts">
import {computed, onMounted, reactive, ref} from "vue";
import {ElMessage, FormInstance, genFileId, UploadInstance, UploadProps, UploadRawFile} from "element-plus";
import axios from "axios";
import qs from "qs";

//商品列表
const tableData = reactive([])

function upTableData() {
  tableData.length = 0
  axios.get('/cart/getAll')
      .then(res => {
        // console.log(res.data)
        tableData.push(...res.data)
      })
}

onMounted(() => {
  upTableData()
  // console.log(tableData)
})

const search = ref('')
const filterTableData = computed(() =>
    tableData.filter(
        (data) =>
            !search.value ||
            data.goodsName.toLowerCase().includes(search.value.toLowerCase())
    )
)

</script>

<template>

  <el-table :data="filterTableData" table-layout="fixed">
    <el-table-column prop="goodsId" label="编号" align="center"/>
    <el-table-column prop="userName" label="用户名"  align="center"/>
    <el-table-column prop="goodsName" label="商品名"  align="center">
      <template #default="scope">
        <n-ellipsis :line-clamp="2">
          {{ scope.row.goodsName }}
        </n-ellipsis>
      </template>
    </el-table-column>
    <el-table-column prop="goodsNum" label="数量" align="center"/>
    <el-table-column prop="note" label="备注" align="center"/>

    <el-table-column align="center">
      <template #header>
        <el-input v-model="search" size="small" placeholder="搜索框"/>
      </template>
    </el-table-column>
  </el-table>
</template>

<style scoped>

</style>
