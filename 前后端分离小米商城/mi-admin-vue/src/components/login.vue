<script setup lang="ts">

import {ref} from "vue";
import axios from "axios";
import router from "../router";
import {useMessage} from "naive-ui";

const username=ref(null)
const password=ref(null)
const message=useMessage()

function login(){
  axios.get('/admin/login',{
    params:{
      username:username.value,
      password:password.value,
      admin:'1'
    }
  }).then(res=>{
    // console.log(res)
    if (res.data.id>0){
      window.sessionStorage.setItem('id',res.data.id)
      window.sessionStorage.setItem('username',res.data.username)
      router.push('/children/userAdmin')
    }else {
      message.error("登录失败")
    }
  })
}

function reset(){
  username.value=null
  password.value=null
}

</script>

<template>
  <div class="continent">
    <n-space vertical>
      <div>
        <h2>&nbsp;登录</h2>
      </div>
      <div>
        <n-space justify="center">
          <div style="width: 50px;text-align: end">用户名:</div>
          <n-input type="text" v-model:value="username" placeholder="用户名" clearable/>
        </n-space>
      </div>
      <div>
        <n-space justify="center">
          <div style="width: 50px;text-align: end">密码:</div>
          <n-input type="password" v-model:value="password" placeholder="密码" clearable/>
        </n-space>
      </div>
      <div>
        <n-space  justify="center">
          <n-button @click="login">登录</n-button>
          <n-button @click="reset">重置</n-button>
        </n-space>
      </div>
    </n-space>
  </div>
</template>

<style scoped>
.continent {
  position: fixed;
  top: 50%;
  left: 50%;
  width: 300px;
  height: 220px;
  -webkit-transform: translateX(-50%) translateY(-50%);
  border: 1px #757575 solid;
  border-radius: 8px;
}
</style>
