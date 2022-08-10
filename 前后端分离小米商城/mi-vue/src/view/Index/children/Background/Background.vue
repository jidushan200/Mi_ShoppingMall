<script setup lang="ts">

import NavTop from "../../../../components/NavTop/NavTop.vue";
import Footer from "../../../../components/Footer.vue";
import {h, onMounted, onUpdated, reactive, ref, watch} from "vue";
import {RouterLink, RouterView, useRoute, useRouter} from "vue-router";
import {MenuOption} from "naive-ui";
import {useStore} from "vuex";

// const message = useMessage()
const store=useStore()
const route=useRoute()

const menuOptions = reactive<MenuOption[]>([
  {
    label: () =>
        h(
            RouterLink,
            {
              to: '/bg/orders'
            },
            {default: () => '我的订单'}
        ),
    key: '我的订单',
  },
  {
    label: '个人中心',
    key: '个人中心',
    children: [
      {
        label: () =>
            h(
                RouterLink,
                {
                  to: '/bg/personInfo'
                },
                {default: () => '个人信息'}
            ),
        key: '个人信息',
      },
      {
        label: () =>
            h(
                RouterLink,
                {
                  to: '/bg/address'
                },
                {default: () => '收货地址'}
            ),
        key: '收货地址',
      },
      {
        label: () =>
            h(
                RouterLink,
                {
                  to: '/bg/upPwd',
                },
                {default: () => '修改密码'}
            ),
        key: '修改密码',
      }
    ]
  }
])

</script>

<template>
  <NavTop/>
  <div class="mainDiv">
    <div>
      <n-breadcrumb style="padding: 10px 0px 10px 0px">
        <n-breadcrumb-item @click="$router.push('/')">
          首页
        </n-breadcrumb-item>
        <n-breadcrumb-item>
          <span style="color: #757575">{{ route.name }}</span>
        </n-breadcrumb-item>
      </n-breadcrumb>
    </div>
    <div>
      <n-space>
        <!--        导航菜单-->
        <div style="width: 234px;background-color: #fff">
          <n-menu style="width: 100%" :value="route.name" default-expand-all :options="menuOptions"
                  />
        </div>
        <div style="width: 970px;background-color:#fff;padding-top: 30px;padding-bottom: 30px">
          <router-view :key="$route.fullPath"/>
        </div>
      </n-space>
    </div>
  </div>
  <Footer/>
</template>

<style scoped>
.mainDiv {
  display: block;
  width: 100%;
  background-color: #f5f5f5;
  padding-bottom: 30px;
}

.mainDiv div {
  width: 1226px;
  margin: auto
}

.n-breadcrumb-item {
  font-size: 12px;
  color: red;
}

span {
  color: #757575;
}

h3 {
  font-weight: 400;
}
</style>
