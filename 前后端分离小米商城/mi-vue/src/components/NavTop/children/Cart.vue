<script setup lang="ts">

import {onMounted, reactive, ref} from "vue";
import {useStore} from "vuex";
import {Add, Remove} from '@vicons/ionicons5'
import axios from "axios";
import qs from "qs";

const store = useStore()

const img = reactive([])

function updateCart(){
  axios.get("/cart/get", {
    params: {
      userId: window.sessionStorage.getItem("userId")
    }
  }).then(res => {
    store.commit('cart',res.data)
  })
}

onMounted(() => {
  // console.log(store.state.goods.find(item=>item.goods.id===1).goods.indexImg)
})

function handleClose(value) {
  axios.delete('/cart/delete',{
    params:{
      id:value.id
    }
  }).then(res=>{
    if (res.data>0){
      updateCart()
    }
  })
}

function handleChange(value,goodsNum){
  console.log(value)
  console.log(goodsNum)
  axios.put('/cart/put',qs.stringify({
    id:value.id,
    userId:value.userId,
    goodsId:value.goodsId,
    goodsNum:value.goodsNum,
    note:value.note
  })).then(res=>{
    if (res.data>0){
      updateCart()
    }
  })
}


</script>

<template>
  <n-card v-for="(value,index) in store.state.cartList" :closable="true" @close="handleClose(value)" :hoverable="true">
    <table>
      <tr>
        <td>
          <n-image
              width="100"
              :src="`${'http://localhost:8080'+store.state.goods.find(item=>item.goods.id===value.goodsId).goods.indexImg}`"
          />
        </td>
        <td>
          <n-ellipsis style="width: 159px" :line-clamp="2">
            {{ store.state.goods.find(item=>item.goods.id===value.goodsId).goods.name }}
            {{value.note}}
          </n-ellipsis>
        </td>
        <td style="text-align: center">
          <el-input-number style="width: 100px" v-model="value.goodsNum" :min="1" @change="handleChange(value,value.goodsNum)" />
        </td>
      </tr>
    </table>
  </n-card>
</template>

<style scoped>

</style>
