<script setup lang="ts">

import {ref} from "vue";
import axios from "axios";
import qs from "qs";
import {useMessage} from "naive-ui";

const message=useMessage()

const oldPwd = ref(null)

const newPwd = ref(null)

const reNewPwd = ref(null)

//修改密码
function updatePwd(){
  if (newPwd.value==reNewPwd.value){
    axios.put('/updatePwd',qs.stringify({
      userId:window.sessionStorage.getItem("userId"),
      oldPwd:oldPwd.value,
      password:newPwd.value
    })).then(res=>{
      if (res.data){
        message.success("修改成功")
        reset()
      }else {
        message.error("修改失败")
      }
    })
  }
}

//重置
function reset(){
  oldPwd.value=null
  newPwd.value=null
  reNewPwd.value=null
}

</script>

<template>
  <div style="width: 100%;margin-left:20px">
    <h1>修改密码</h1>
    <n-space vertical>
      <div style="line-height: 55px">
        <n-space style="margin-top: 8px">
          <div class="div-align">
            旧密码
          </div>
          <div>
            <n-input style="width: 360px" type="password" size="large" placeholder="旧密码" v-model:value="oldPwd"/>
          </div>
        </n-space>
        <n-space style="margin-top: 8px">
          <div class="div-align">
            新密码
          </div>
          <div>
            <n-input style="width: 360px" type="password" size="large" placeholder="新密码" v-model:value="newPwd"/>
          </div>
        </n-space>
        <n-space style="margin-top: 8px">
          <div class="div-align">
            重复新密码
          </div>
          <div>
            <n-input style="width: 360px" type="password" size="large" placeholder="重复新密码" v-model:value="reNewPwd"/>
          </div>
        </n-space>

        <n-space style="margin-top: 8px">
          <div style="padding-left: 150px">
            <n-button style="width: 100px;margin-right: 40px" @click="updatePwd">修改</n-button>
            <n-button style="width: 100px" @click="reset">重置</n-button>
          </div>
        </n-space>
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
