<script setup lang="ts">
import {computed, onMounted, reactive, ref} from "vue";
import {ElMessage, FormInstance, genFileId, UploadInstance, UploadProps, UploadRawFile} from "element-plus";
import axios from "axios";
import qs from "qs";

//商品列表
const tableData = reactive([])

function upTableData() {
  tableData.length = 0
  axios.get('/all')
      .then(res => {
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
            data.username.toLowerCase().includes(search.value.toLowerCase())
    )
)

const commit = ref(null)
const upId = ref(null)

function addUser() {
  dialogFormVisible.value = true
  commit.value = 'post'
  form.admin='1'
}

const handleUpdate = (value) => {
  // console.log(value)
  dialogFormVisible.value = true
  commit.value = 'put'
  upId.value = value.id
  form.username=value.username
  form.password=value.password
  form.admin=value.admin

}
const handleDelete = (index: number) => {
  // console.log(index)
  axios.delete('/admin/delete',{
    params:{
      id:index
    }
  }).then(res=>{
    location.reload()
  })
}

//商品对话框
const dialogFormVisible = ref(false)
const form = reactive({
  username: '',
  password: '',
  admin: ''
})

const formRef = ref<FormInstance>()


//提交表单
const submitForm = (formEl: FormInstance | undefined) => {
  dialogFormVisible.value = false
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      if (commit.value == 'post') {
        //添加
        axios.post('/enroll',qs.stringify({
          username:form.username,
          password:form.password,
          admin:form.admin
        })).then(res=>{
          commit.value=null
          location.reload()
        })
      } else if (commit.value == 'put') {
        //修改
        axios.put('/admin/put',qs.stringify({
          id:upId.value,
          username:form.username,
          password:form.password,
          admin:form.admin
        })).then(res=>{
          commit.value=null
        })
      }
      formEl.resetFields()
    } else {
      console.log('error submit!')
      return false
    }
  })
}

//重置表单
const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
  dialogFormVisible.value = false
}

const id=ref(window.sessionStorage.getItem('id'))
</script>

<template>
  <div>
    <n-button style="margin-left: 10px;margin-bottom: 10px" @click="addUser">添加用户</n-button>
  </div>

  <!--  对话框-->
  <el-dialog v-model="dialogFormVisible" title="账号" :close-on-click-modal="false">
    <el-form :model="form" ref="formRef">
      <el-form-item label="用户名" prop="name" label-width="140px">
        <el-input v-model="form.username" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="密码" prop="message" label-width="140px">
        <el-input v-model="form.password" type="password" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="是否管理员" prop="admin" label-width="140px">
        <template #default>
          <el-radio-group v-model="form.admin" :disabled="commit==='put'">
            <el-radio label="1" size="large">是</el-radio>
            <el-radio label="0" size="large">否</el-radio>
          </el-radio-group>
        </template>
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
    <el-table-column prop="username" label="名称"  align="center">
      <template #default="scope">
        <n-ellipsis :line-clamp="2">
          {{ scope.row.username }}
        </n-ellipsis>
      </template>
    </el-table-column>
    <el-table-column prop="admin" label="是否管理员" align="center">
      <template #default="scope">
          {{ scope.row.admin==='1'?'是':'否' }}
      </template>
    </el-table-column>
    <el-table-column align="center">
      <template #header>
        <el-input v-model="search" size="small" placeholder="搜索框"/>
      </template>
      <template #default="scope">
        <el-button size="small" @click="handleUpdate(scope.row)"
        >修改
        </el-button
        >
        <el-button
            size="small"
            type="danger"
            @click="handleDelete(scope.row.id)"
            :disabled="id===String(scope.row.id)"
        >删除
        </el-button
        >
      </template>
    </el-table-column>
  </el-table>
</template>

<style scoped>

</style>
