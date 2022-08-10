<script setup lang="ts">

import NavTop from "../../../components/NavTop/NavTop.vue";
import Footer from "../../../components/Footer.vue";
import {onMounted, onUpdated, reactive, ref, watch} from "vue";
import {useRoute, useRouter} from "vue-router";
import {useStore} from "vuex";
import axios from "axios";
import qs from "qs";
const route=useRoute()
const router=useRouter()
const store=useStore()

function toDetail(id){
  router.push({path:'/detail',query:{id}})
}
</script>

<template>
  <NavTop/>
  <div>
    <div style="width: 100%;background-color: #f5f5f5;padding:30px 0px 30px 0px;">
      <div class="tmp-div">
        <n-grid :cols="5" responsive="screen" x-gap="14" y-gap="14">
          <n-grid-item v-for="value in store.state.searchList">
            <n-card @click="toDetail(value.goods.id)">
              <template #cover>
                <img style="width: 160px;height:160px;margin: 15px auto auto auto"
                     :src="'http://localhost:8080'+value.goods.indexImg">
              </template>
              <h3 style="margin-bottom: 0">{{value.goods.name}}</h3>
              <p style="font-size: 12px;margin: 0px;color: #b0b0b0">
                <n-ellipsis style="max-width: 130px">
                  {{value.goods.message}}
                </n-ellipsis>
              </p>
              <p style="color: #ff893a">{{value.goods.price}}元</p>
            </n-card>
          </n-grid-item>
        </n-grid>
      </div>
      </div>
    </div>
  <Footer/>
</template>

<style scoped>
.n-card {
  text-align: center;
  width: 234px;
  height: 300px
}
.n-card:hover{
  cursor: pointer;
  transition: 0.3s;
  margin-top: -3px;
  margin-bottom: 3px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
}
.tmp-div {
  width: 1226px;
  margin: 0 auto 0px auto;
}
</style>
