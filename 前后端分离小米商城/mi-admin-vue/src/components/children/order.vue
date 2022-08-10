<script setup lang="ts">
import {computed, onMounted, reactive, ref} from "vue";
import {ElMessage, FormInstance, genFileId, UploadInstance, UploadProps, UploadRawFile} from "element-plus";
import axios from "axios";
import qs from "qs";

//商品列表
const tableData = reactive([])

function upTableData() {
  tableData.length = 0
  axios.get('/order/admin/getAll')
      .then(res => {
        for (let i = 0; i < res.data.length; i++) {
          for (let j = 0; j <res.data[i].ex1List.length; j++) {
            tableData.push({
              id: res.data[i].ex1List[j].id,
              order:res.data[i].id,
              goodName: res.data[i].ex1List[j].goods.name,
              price: res.data[i].ex1List[j].goods.price,
              consignee: res.data[i].consignee,
              address: res.data[i].address,
              creationTime: res.data[i].creationTime,
              goodsNum: res.data[i].ex1List[j].goodsNum,
              note:res.data[i].ex1List[j].note
            })
          }
        }
        // console.log(tableData)
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
            data.goodName.toLowerCase().includes(search.value.toLowerCase())
    )
)

const handleUpdate = (value) => {
  // console.log(value)
  dialogFormVisible.value = true
  form.id=value.order
  form.consignee=value.consignee
  form.address=value.address

}
const handleDelete = (value) => {
  // console.log(value)
  axios.delete('/order/del',{
    params:{
      id:value.id,
      orderId:value.order
    }
  }).then(res=>{
    location.reload()
  })
}

//商品对话框
const dialogFormVisible = ref(false)
const form = reactive({
  id:'',
  consignee: '',
  address: ''
})

const formRef = ref<FormInstance>()


//提交表单
const submitForm = (formEl: FormInstance | undefined) => {
  dialogFormVisible.value = false
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      //修改
      axios.put('/order/put',qs.stringify({
        id:form.id,
        consignee:form.consignee,
        address:form.address
      })).then(res=>{
        location.reload()
      })
    } else {
      // console.log('error submit!')
      return false
    }
  })
  formEl.resetFields()
}

//重置表单
const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
  dialogFormVisible.value = false
}

</script>

<template>

  <!--  对话框-->
  <el-dialog v-model="dialogFormVisible" title="订单" :close-on-click-modal="false">
    <el-form :model="form" ref="formRef">
      <el-form-item label="收货人" prop="name" label-width="140px">
        <el-input v-model="form.consignee" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="地址" prop="message" label-width="140px">
        <el-input v-model="form.address" autocomplete="off"/>
      </el-form-item>

    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="resetForm(formRef)">取消</el-button>
        <el-button type="primary" @click="submitForm(formRef)"
        >确认</el-button
        >
      </span>
    </template>
  </el-dialog>


  <el-table :data="filterTableData" table-layout="fixed">
    <el-table-column prop="id" label="编号" align="center"/>
    <el-table-column prop="goodName" label="名称"  align="center">
      <template #default="scope">
        <n-ellipsis :line-clamp="2">
          {{ scope.row.goodName }}
        </n-ellipsis>
      </template>
    </el-table-column>
    <el-table-column prop="price" label="单价" align="center"/>
    <el-table-column prop="goodsNum" label="数量" align="center"/>
    <el-table-column prop="consignee" label="收货人" align="center"/>
    <el-table-column prop="address" label="地址" align="center"/>
    <el-table-column prop="creationTime" label="创建时间" align="center"/>
    <el-table-column prop="note" label="备注" align="center"/>

    <el-table-column align="center">
      <template #header>
        <el-input v-model="search" size="small" placeholder="搜索框"/>
      </template>
      <template #default="scope">
        <el-button
            size="small"
            type="primary"
            @click="handleUpdate(scope.row)"
        >修改
        </el-button>
        <el-button
            size="small"
            type="danger"
            @click="handleDelete(scope.row)"
        >删除
        </el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<style scoped>

</style>
