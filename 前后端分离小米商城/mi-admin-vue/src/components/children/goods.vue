<script setup lang="ts">
import {computed, onMounted, reactive, ref} from "vue";
import {FormInstance, genFileId, UploadInstance, UploadProps, UploadRawFile} from "element-plus";
import axios from "axios";
import qs from "qs";

//商品列表
const tableData = reactive([])

function upTableData() {
  tableData.length = 0
  axios.get('/admin/goods/all')
      .then(res => {
        tableData.push(...res.data)
      })
}

onMounted(() => {
  upTableData()
})

const search = ref('')
const filterTableData = computed(() =>
    tableData.filter(
        (data) =>
            !search.value ||
            data.goods.name.toLowerCase().includes(search.value.toLowerCase()) ||
            data.goods.message.toLowerCase().includes(search.value.toLowerCase()) ||
            data.goods.classify.toLowerCase().includes(search.value.toLowerCase())
    )
)

const commit = ref(null)
const upId = ref(null)

function addGoods() {
  dialogFormVisible.value = true
  commit.value = 'post'
}

const handleUpdate = (value) => {
  // console.log(value)
  dialogFormVisible.value = true
  commit.value = 'put'
  upId.value = value.goods.id
  form.name = value.goods.name
  form.price = value.goods.price
  form.message = value.goods.message
  form.classify = value.goods.classify
  form.config.length = 0
  for (let i = 0; i < value.goodsConfigList.length; i++) {
    form.config.push({
      key: value.goodsConfigList[i].id,
      value: value.goodsConfigList[i].config
    })
  }
  form.color.length = 0
  for (let i = 0; i < value.goodsColorList.length; i++) {
    form.color.push({
      key: value.goodsColorList[i].id,
      value: value.goodsColorList[i].color
    })
  }
}
const handleDelete = (index: number) => {
  // console.log(index)
  axios.delete('/admin/goods/del',{
    params:{
      id:index
    }
  }).then(res=>{
    console.log(res)
    location.reload()
  })
}

//商品对话框
const dialogFormVisible = ref(false)
const form = reactive({
  name: '',
  message: '',
  price: '',
  classify: '',
  config: [{
    key: 1,
    value: ''
  }],
  color: [
    {
      key: 1,
      value: ''
    }
  ],
})

const formRef = ref<FormInstance>()

const removeConfig = (item) => {
  const index = form.config.indexOf(item)
  if (index !== -1) {
    form.config.splice(index, 1)
  }
}

const addConfig = () => {
  form.config.push({
    key: Date.now(),
    value: '',
  })
}

const removeColor = (item) => {
  const index = form.color.indexOf(item)
  if (index !== -1) {
    form.color.splice(index, 1)
  }
}

const addColor = () => {
  form.color.push({
    key: Date.now(),
    value: '',
  })
}

const upload1 = ref<UploadInstance>()
const action1 = ref(null)
//阻止表单提交
const handleExceed1: UploadProps['onExceed'] = (files) => {
  upload1.value!.clearFiles()
  const file = files[0] as UploadRawFile
  file.uid = genFileId()
  upload1.value!.handleStart(file)
}

const upload2 = ref<UploadInstance>()
const action2 = ref(null)
//阻止表单提交
const handleExceed2: UploadProps['onExceed'] = (files) => {
  upload2.value!.clearFiles()
  const file = files[0] as UploadRawFile
  file.uid = genFileId()
  upload2.value!.handleStart(file)
}


//提交表单
const submitForm = (formEl: FormInstance | undefined) => {
  dialogFormVisible.value = false
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      console.log('submit!')
      console.log(form)
      if (commit.value == 'post') {

        //添加
        axios.post('/admin/goods/insert', {
          name: form.name,
          message: form.message,
          price: form.price,
          classify: form.classify,
          config: form.config,
          color: form.color
        }).then(res => {
          console.log(res)
          action1.value = "api/admin/goods/indexImg?goodsId=" + res.data
          action2.value = "api/admin/goods/detailImg?goodsId=" + res.data
          upload1.value!.submit()
          upload2.value!.submit()
          upload1.value!.clearFiles()
          upload2.value!.clearFiles()
          location.reload()
        })
      } else if (commit.value == 'put') {

        //修改
        action1.value = "api/admin/goods/indexImg?goodsId=" + upId.value
        action2.value = "api/admin/goods/detailImg?goodsId=" + upId.value
        const con = reactive([])
        for (let i = 0; i < form.config.length; i++) {
          con.push(form.config[i].value)
        }
        const col = reactive([])
        for (let i = 0; i < form.color.length; i++) {
          col.push(form.color[i].value)
        }
        // console.log(con.values())
        // console.log(col)
        axios.put('/admin/goods/update', {
          id: upId.value,
          name: form.name,
          message: form.message,
          price: form.price,
          classify: form.classify,
          config: con,
          color: col
        }).then(res => {
          upload1.value!.submit()
          upload2.value!.submit()
          upload1.value!.clearFiles()
          upload2.value!.clearFiles()
          location.reload()
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
</script>

<template>
  <div>
    <n-button style="margin-left: 10px;margin-bottom: 10px" @click="addGoods">添加商品</n-button>
  </div>

  <!--  对话框-->
  <el-dialog v-model="dialogFormVisible" title="商品" :close-on-click-modal="false">
    <el-form :model="form" ref="formRef">
      <el-form-item label="名称" prop="name" label-width="140px">
        <el-input v-model="form.name" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="信息" prop="message" label-width="140px">
        <el-input v-model="form.message" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="价格" prop="price" label-width="140px"
                    :rules="[
        { type: 'number', message: 'price must be a number' },
      ]"
      >
        <el-input v-model.number="form.price" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="分类" prop="classify" label-width="140px">
        <el-select v-model="form.classify" placeholder="请选择">
          <el-option label="手机" value="手机"/>
          <el-option label="电视" value="电视"/>
          <el-option label="笔记本" value="笔记本"/>
          <el-option label="平板" value="平板"/>
          <el-option label="家电" value="家电"/>
          <el-option label="路由器" value="路由器"/>
        </el-select>
      </el-form-item>
      <!--      配置-->
      <el-form-item
          v-for="(item, index) in form.config"
          :key="item.key"
          :label="'config' + index"
          :prop="'config.' + index + '.value'"
          label-width="140px"
      >
        <el-input v-model="item.value" style="float: left;width: 90%"/>
        <el-button style="width: 10%" @click.prevent="removeConfig(item)">
          Delete
        </el-button>
      </el-form-item>
      <el-form-item label-width="140px">
        <el-button @click.prevent="addConfig">
          addConfig
        </el-button>
      </el-form-item>
      <!--      颜色-->
      <el-form-item
          v-for="(item, index) in form.color"
          :key="item.key"
          :label="'color' + index"
          :prop="'color.' + index + '.value'"
          label-width="140px"
      >
        <el-input v-model="item.value" style="float: left;width: 90%"/>
        <el-button style="width: 10%" @click.prevent="removeColor(item)">
          Delete
        </el-button>
      </el-form-item>
      <el-form-item label-width="140px">
        <el-button @click.prevent="addColor">
          addColor
        </el-button>
      </el-form-item>
      <el-form-item label="预览图" label-width="140px">
        <el-upload
            ref="upload1"
            name="indexImg"
            :action="action1"
            :limit="1"
            :on-exceed="handleExceed1"
            :auto-upload="false"
        >
          <template #trigger>
            <el-button type="primary">选择文件</el-button>
          </template>
        </el-upload>
      </el-form-item>
      <el-form-item label="详情图" label-width="140px">
        <el-upload
            ref="upload2"
            name="detailImg"
            :action="action2"
            :limit="1"
            :on-exceed="handleExceed2"
            :auto-upload="false"
        >
          <template #trigger>
            <el-button type="primary">选择文件</el-button>
          </template>
        </el-upload>
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
    <el-table-column prop="goods.id" label="编号" align="center"/>
    <el-table-column prop="goods.name" label="名称"  align="center">
      <template #default="scope">
        <n-ellipsis :line-clamp="2">
          {{ scope.row.goods.name }}
        </n-ellipsis>
      </template>
    </el-table-column>
    <el-table-column prop="goods.message" label="信息" align="center">
      <template #default="scope">
        <n-ellipsis :line-clamp="2">
          <div style="width: 148px">{{ scope.row.goods.message }}</div>
        </n-ellipsis>
      </template>
    </el-table-column>
    <el-table-column prop="goods.price" label="单价" align="center"/>
    <el-table-column prop="goods.classify" label="分类" align="center"/>
    <el-table-column prop="goods.indexImg" label="预览图" align="center">
      <template #default="scope">
        <el-image style="width: 100px; height: 100px" :src="'http://localhost:8080'+scope.row.goods.indexImg" fit="contain" />
      </template>
    </el-table-column>
    <el-table-column prop="goods.detailImg" label="详情图" align="center">
      <template #default="scope">
        <el-image style="width: 100px; height: 100px" :src="'http://localhost:8080'+scope.row.goods.detailImg" fit="contain" />
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
            @click="handleDelete(scope.row.goods.id)"
        >删除
        </el-button
        >
      </template>
    </el-table-column>
  </el-table>
</template>

<style scoped>

</style>
