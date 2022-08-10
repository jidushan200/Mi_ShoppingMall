<script setup lang="ts">

import NavTop from "../../components/NavTop/NavTop.vue";
import {ChevronBack, ChevronForward} from '@vicons/ionicons5'
import {reactive} from "vue";
import Footer from "../../components/Footer.vue";
import {useStore} from "vuex";
import {useRouter} from "vue-router";

const router=useRouter()
const store=useStore()

function toDetail(id){
  console.log(id)
  router.push({path:'/detail',query:{id}})
}

//轮播图
const imgs = reactive([
  'https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/628a0172dc664f2740250ed9691b65ee.jpg?w=2452&h=920',
  'https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/008e480893428ae9c9c4452f67a4eec9.jpg?thumb=1&w=1533&h=575&f=webp&q=90'
])
</script>

<template>
  <NavTop/>
  <div class="tmp-div" style="margin-bottom: 30px">
    <n-carousel autoplay draggable show-arrow>
      <img
          v-for="img in imgs"
          class="carousel-img"
          :src="img"
      >
      <template #arrow="{ prev, next }">
        <div class="custom-arrow">
          <button type="button" class="curtom-arrow--left" @click="prev">
            <n-icon size="30">
              <ChevronBack/>
            </n-icon>
          </button>
          <button style="margin-left: 1131px" type="button" class="curtom-arrow--right" @click="next">
            <n-icon size="30">
              <ChevronForward/>
            </n-icon>
          </button>
        </div>
      </template>
      <template #dots="{ total, currentIndex, to }">
        <ul class="custom-dots">
          <li
              v-for="index of total"
              :key="index"
              :class="{ ['is-active']: currentIndex === index - 1 }"
              @click="to(index - 1)"
          />
        </ul>
      </template>
    </n-carousel>
  </div>
<!--  商品列表-->
  <div style="width: 100%;background-color: #f5f5f5;padding:30px 0px 30px 0px;">
    <div class="tmp-div">
      <!--   栅格布局 -->
      <n-grid :cols="5" responsive="screen" x-gap="14" y-gap="14">
        <n-grid-item v-for="value in store.state.goods">
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

.green {
  height: 108px;
  background-color: rgba(0, 128, 0, 0.24);
  display: flex;
  align-items: center;
  justify-content: center;
}

.tmp-div {
  width: 1226px;
  margin: 0 auto 0px auto;
}

.carousel-img {
  width: 100%;
  height: 462px;
  object-fit: cover;
}

.custom-arrow {
  display: flex;
  position: absolute;
  bottom: 50%;
  margin-bottom: -20px;
}

.custom-arrow button {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 41px;
  height: 69px;
  margin-right: 12px;
  color: #333333;
  border-width: 0;
  border-radius: 2px;
  background-color: rgba(255, 255, 255, 0.21);
  transition: background-color 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  cursor: pointer;
}

.custom-arrow button:hover {
  background-color: rgba(183, 178, 178, 0.9);
  color: #fcfcfc;
}

.custom-arrow button:active {
  transform: scale(0.95);
  transform-origin: center;
}

.custom-dots {
  display: flex;
  padding: 0;
  position: absolute;
  bottom: 20px;
  right: 30px;
  margin: 0 0 0 -69px;
}

.custom-dots li {
  display: inline-block;
  width: 12px;
  height: 4px;
  margin: 0 3px;
  border-radius: 4px;
  background-color: #a8a8a8;
  transition: width 0.3s, background-color 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  cursor: pointer;
}

.custom-dots li.is-active {
  width: 40px;
  background: #ff6700;
}
</style>
