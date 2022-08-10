<script lang="ts" setup>

import NavTop from "../../../../components/NavTop/NavTop.vue";
import Footer from "../../../../components/Footer.vue";
import {useRoute, useRouter} from "vue-router";
import {onMounted, reactive, ref} from "vue";
import {useStore} from "vuex";
import axios from "axios";
import qs from "qs";
import {useMessage} from "naive-ui";
// import {useMessage} from "naive-ui";

const route = useRoute()
const store = useStore()
const message=useMessage()
//获取商品id
const goodId = ref(route.query.id)

const good = reactive(store.state.goods.find(item => item.goods.id == goodId.value))

//配置
const config = ref<String>('')

function checkConfig() {
  console.log(config.value)
}

//颜色
const color = ref<String>('')

function checkColor() {
  console.log(color.value)
}

//加入购物车
function putCart(){
  if (window.sessionStorage.getItem("userId")!=null){
    axios.post('/cart/add',qs.stringify({
      userId:window.sessionStorage.getItem("userId"),
      goodsId:goodId.value,
      goodsNum:1,
      note:config.value+' '+color.value
    })).then(res=>{
      if (res.data){
        message.success("加入购物车成功")
        axios.get("/cart/get", {
          params: {
            userId: window.sessionStorage.getItem("userId")
          }
        }).then(res => {
          store.commit('cart',res.data)
        })
      }else {
        message.error("加入购物车失败")
      }
    })
  }else {
    message.error("请先登录")
  }
}
</script>

<template>
  <NavTop/>
  <div class="main">
    <n-space justify="space-between">
      <div style="width: 606px">
        <img :src="'http://localhost:8080'+good.goods.detailImg" style="width: 560px">
      </div>
      <div style="width: 580px">
        <h1>{{ good.goods.name }}</h1>
        <p style="color: #b0b0b0">{{ good.goods.message }}</p>
        <h2 style="color: #ff6700">{{ good.goods.price }}元</h2>
        <span v-if="good.goodsConfigList.length>0">
          <p>选择配置</p>
          <div>
            <n-radio-group v-model:value="config" :default-value="config" size="medium">
                <n-grid :x-gap="12" :y-gap="8" :cols="2">
                  <n-grid-item v-for="item in good.goodsConfigList" style="width: 270px;margin-bottom: 3px">
                    <n-radio-button
                        @change="checkConfig"
                        :value="item.config"
                    >
                      {{ item.config }}
                    </n-radio-button>
                  </n-grid-item>
                </n-grid>
            </n-radio-group>
          </div>
        </span>
        <span v-show="good.goodsColorList.length>0">
          <p>选择颜色</p>
          <div>
            <n-radio-group v-model:value="color" :default-value="color" size="medium">
                <n-grid :x-gap="12" :y-gap="8" :cols="2">
                  <n-grid-item v-for="item in good.goodsColorList" style="width: 270px;margin-bottom: 3px">
                    <n-radio-button
                        @change="checkColor"
                        :value="item.color"
                    >
                      {{ item.color }}
                    </n-radio-button>
                  </n-grid-item>
                </n-grid>
            </n-radio-group>
          </div>
        </span>
        <span style="width: 95% ;height: 86px;margin: 20px 0 60px 0;display: block">
          <n-space justify="space-between">
            <div>
              <p style="font-size: 14px;color: #616161">
                {{ good.goods.name }}&nbsp;&nbsp;{{ config }}&nbsp;&nbsp;{{ color }}
              </p>
              <p style="font-size: 24px;color: #ff6700">
                总计:{{ good.goods.price }}元
              </p>
            </div>
            <div style="line-height: 130px;">
              <button @click="putCart">加入购物车</button>
            </div>
          </n-space>
        </span>
      </div>
    </n-space>
  </div>
  <Footer/>
</template>

<style scoped>
span p {
  font-size: 18px;
}

.main {
  width: 1210px;
  margin: 10px auto 20px auto;
  border-right: 1px rgba(124, 121, 121, 0.7) solid;
  border-bottom: 1px rgba(124, 121, 121, 0.6) solid;
  box-shadow: 1px 1px 3px rgba(166, 149, 149, 0.53);
  border-radius: 4px;
}

.n-radio-button {
  width: 100%;
  height: 42px;
  line-height: 42px;
  font-size: 16px;
}

span {
  width: 100%;
}

button {
  width: 240px;
  height: 54px;
  border: 0;
  background-color: #ff6700;
  font-size: 16px;
  color: #fcfcfc;
}
button:hover{
  transition: 0.3s;
  background-color: #d35100;
  cursor: pointer;
}
</style>
