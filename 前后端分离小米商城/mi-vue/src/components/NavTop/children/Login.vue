<script setup lang="ts">
import {reactive, ref} from 'vue'
import {
  FormInst,
  FormItemInst,
  FormItemRule,
  FormRules,
  useMessage
} from 'naive-ui'
import {useStore} from "vuex";
import router from "../../../router";
import axios from "axios";
import qs from "qs"

interface ModelType {
  username: string | null
  password: string | null
  reenteredPassword: string | null
}

const store = useStore()
const formRef = ref<FormInst | null>(null)
const rPasswordFormItemRef = ref<FormItemInst | null>(null)
const message = useMessage()
const modelRef = ref<ModelType>({
  username: null,
  password: null,
  reenteredPassword: null
})

function validatePasswordStartWith(
    rule: FormItemRule,
    value: string
): boolean {
  return (
      !!modelRef.value.password &&
      modelRef.value.password.startsWith(value) &&
      modelRef.value.password.length >= value.length
  )
}

function validatePasswordSame(rule: FormItemRule, value: string): boolean {
  return value === modelRef.value.password
}

//检查用户名是否重复
let available = ref(true)

async function checkUsername(value) {
  await axios.get('/checkAgain', {
    params: {
      username: value
    }
  })
      .then((res) => {
        // console.log(res)
        if (res.data) {
          available.value = true
        } else {
          available.value = false
        }
      })
      .catch((err) => {
        console.log(err)
      })
}


//用户名格式验证
const rules: FormRules = {
  username: [
    {
      required: true,
      validator(rule: FormItemRule, value: string) {
        return new Promise<void>(async (resolve, reject) => {
          if (!value) {
            reject(Error('需要用户名'))
          } else if (/\W/.test(value)) {
            reject(Error('用户名格式错误'))
          }
          await checkUsername(value)
          if (!available.value) {
            reject(Error('用户名不可用'))
          }
          resolve()
        })
      },
      trigger: ['input', 'blur']
    }
  ],
  password: [
    {
      required: true,
      message: '请输入密码'
    }
  ],
  reenteredPassword: [
    {
      required: true,
      message: '请再次输入密码',
      trigger: ['input', 'blur']
    },
    {
      validator: validatePasswordStartWith,
      message: '两次密码输入不一致',
      trigger: 'input'
    },
    {
      validator: validatePasswordSame,
      message: '两次密码输入不一致',
      trigger: ['blur', 'password-input']
    }
  ]
}

function handlePasswordInput() {
  if (modelRef.value.reenteredPassword) {
    rPasswordFormItemRef.value?.validate({trigger: 'password-input'})
  }
}

function handleValidateButtonClick(e: MouseEvent) {
  e.preventDefault()
  formRef.value?.validate((errors) => {
    if (!errors) {
      axios.post('/enroll', qs.stringify({
        username: modelRef.value.username,
        password: modelRef.value.password
      })).then((res) => {
        if (res.data) {
          message.success('注册成功')
        }
      })
    } else {
      console.log(errors)
      message.error('注册失败')
    }
  })
}

const loginModel = ref({
  loginName: null,
  loginPwd: null
})

function login(e: MouseEvent) {
  console.log(loginModel.value)
  axios.get('/login', {
    params: {
      username: loginModel.value.loginName,
      password: loginModel.value.loginPwd
    }
  }).then((res) => {
    window.sessionStorage.setItem("userId", res.data.userId)
    window.sessionStorage.setItem("loginInfo", res.data.userName)
    location.reload()

  }).catch(error=>{
    message.error("登录失败")
  })
}
</script>

<template>
  <div>
    <n-card style="width: 350px">
      <n-tabs
          class="card-tabs"
          default-value="signin"
          size="large"
          animated
          style="margin: 0 -4px"
          pane-style="padding-left: 4px; padding-right: 4px; box-sizing: border-box;"
      >
        <n-tab-pane name="signin" tab="登录">
          <n-form>
            <n-form-item-row label="用户名">
              <n-input v-model:value="loginModel.loginName"/>
            </n-form-item-row>
            <n-form-item-row label="密码">
              <n-input type="password" v-model:value="loginModel.loginPwd"/>
            </n-form-item-row>
          </n-form>
          <n-button type="primary" block secondary strong @click="login">
            登录
          </n-button>
        </n-tab-pane>
        <n-tab-pane name="signup" tab="注册">
          <n-form ref="formRef" :model="modelRef" :rules="rules">
            <n-form-item-row path="username" label="用户名">
              <n-input v-model:value="modelRef.username" @keydown.enter.prevent/>
            </n-form-item-row>
            <n-form-item-row path="password" label="密码">
              <n-input v-model:value="modelRef.password"
                       type="password"
                       @input="handlePasswordInput"
                       @keydown.enter.prevent/>
            </n-form-item-row>
            <n-form-item-row ref="rPasswordFormItemRef"
                             first
                             path="reenteredPassword"
                             label="重复密码">
              <n-input v-model:value="modelRef.reenteredPassword"
                       :disabled="!modelRef.password"
                       type="password"
                       @keydown.enter.prevent/>
            </n-form-item-row>
          </n-form>
          <n-button type="primary" block secondary strong
                    :disabled="modelRef.username===null||modelRef.username===''||
                    modelRef.password===''||modelRef.password===null||
                    modelRef.reenteredPassword===''||modelRef.reenteredPassword===null"
                    @click="handleValidateButtonClick">
            注册
          </n-button>
        </n-tab-pane>
      </n-tabs>
    </n-card>
  </div>
</template>

<style scoped>
.card-tabs .n-tabs-nav--bar-type {
  padding-left: 4px;
}
</style>
