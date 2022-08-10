<script setup lang="ts">
import {ref} from "vue";
import {UploadFileInfo, UploadInst, useMessage} from "naive-ui";
import axios from "axios";
import qs from "qs"

//昵称
const name = ref(null)

//性别
const sex = ref(null)

//ID
const id = ref(null)

//头像
const avatar = ref(null)

axios.get('/user/getUser',{
  params:{
    id:window.sessionStorage.getItem("userId")
  }
}).then((res)=>{
  if (res.data!=null){
    name.value=res.data.nickname
    sex.value=res.data.sex
    id.value=res.data.id
    avatar.value=res.data.headImg
  }
})

//文件上传组件
const upload = ref<UploadInst | null>(null)

//文件格式判断
const message = useMessage()
const beforeUpload = ref(async (data: {
  file: UploadFileInfo
  fileList: UploadFileInfo[]
}) => {
  if (data.file.file?.type == 'image/png' || data.file.file?.type == 'image/jpg' || data.file.file?.type == 'image/jpeg') {
    return true
  }
  message.error('只能上传png、jpg、jpeg格式的图片文件，请重新上传')
  return false
})

//头像上传路径
const path = ref("api/user/headImgUpload?userId=" + window.sessionStorage.getItem("userId"))

//保存
function save() {
  axios.put("/user/save", qs.stringify({
    id: window.sessionStorage.getItem("userId"),
    nickname: name.value,
    sex: sex.value
  })).then((res) => {

  })
  //文件上传提交
  upload.value?.submit()
  location.reload()
}

</script>

<template>
  <div style="width: 100%;margin-left:20px">
    <h1>个人信息</h1>
    <n-space vertical>
      <div style="line-height: 55px">
        <n-space>
          <div class="div-align">
            头像
          </div>
          <div style="width: 360px;">
            <n-space justify="space-between" style="height: 60px">
              <n-avatar
                  round
                  :size="60"
                  :src="avatar"
                  fallback-src="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg"
              />
              <n-upload
                  ref="upload"
                  :action="path"
                  name="headerImg"
                  @before-upload="beforeUpload"
                  :default-upload="false"
              >
                <n-button>上传头像</n-button>
              </n-upload>
            </n-space>
          </div>
        </n-space>

        <n-space style="margin-top: 8px">
          <div class="div-align">
            昵称
          </div>
          <div>
            <n-input style="width: 360px" type="text" size="large" placeholder="请设置名称" v-model:value="name"/>
          </div>
        </n-space>

        <n-space style="margin-top: 8px">
          <div class="div-align">
            性别
          </div>
          <div>
            <n-radio-group v-model:value="sex" name="sexGroup">
              <n-space>
                <n-radio value="男">
                  男
                </n-radio>
                <n-radio value="女">
                  女
                </n-radio>
              </n-space>
            </n-radio-group>
          </div>
        </n-space>

        <n-space style="margin-top: 8px">
          <div class="div-align">
            ID
          </div>
          <div>
            <n-text>{{ id }}</n-text>
          </div>
        </n-space>

        <n-space style="margin-top: 8px">
          <div class="div-align">
            国家/地区
          </div>
          <div>
            <n-text>中国</n-text>
          </div>
        </n-space>
        <n-button style="width: 360px;height: 60px;margin-left: 131px" @click="save">保存</n-button>
      </div>
    </n-space>
  </div>
</template>

<style scoped>
.div-align {
  width: 120px;
  text-align: end
}
</style>
