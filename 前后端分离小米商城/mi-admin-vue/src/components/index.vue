<script setup lang="ts">
import {h, defineComponent, ref, Component, reactive, onMounted} from 'vue'
import {MenuOption, NIcon, useMessage} from 'naive-ui'
import {RouterLink} from 'vue-router'
import {
  LaptopOutline as WorkIcon,
  LogOutOutline as HomeIcon
} from '@vicons/ionicons5'
import {
  ReorderFour,
  Cart,
  Bag,
  Location,
  Person
} from '@vicons/ionicons5'
import router from "../router";

const message = useMessage()

onMounted(() => {
  if (window.sessionStorage.getItem('id') != null) {
    message.success('欢迎您,管理员' + window.sessionStorage.getItem('username'))
  }
})

function renderIcon(icon: Component) {
  return () => h(NIcon, null, {default: () => h(icon)})
}

const menuOptions = reactive([
  {
    label: () =>
        h(
            RouterLink,
            {
              to: {
                path: '/children/userAdmin'
              }
            },
            {default: () => '账号管理'}
        ),
    key: '账号管理',
    icon: renderIcon(Person),
  },
  {
    label: () =>
        h(
            RouterLink,
            {
              to: {
                path: '/children/addressAdmin'
              }
            },
            {default: () => '地址管理'}
        ),
    key: '地址管理',
    icon: renderIcon(Location),
  },
  {
    label: () =>
        h(
            RouterLink,
            {
              to: {
                path: '/children/goodsAdmin'
              }
            },
            {default: () => '商品管理'}
        ),
    key: '商品管理',
    icon: renderIcon(Bag),
  },
  {
    label: () =>
        h(
            RouterLink,
            {
              to: {
                path: '/children/orderAdmin'
              }
            },
            {default: () => '订单管理'}
        ),
    key: '订单管理',
    icon: renderIcon(ReorderFour)
  },
  {
    label: () =>
        h(
            RouterLink,
            {
              to: {
                path: '/children/cartAdmin'
              }
            },
            {default: () => '购物车'}
        ),
    key: '购物车',
    icon: renderIcon(Cart)
  }
])

function outLogin(){
  window.sessionStorage.removeItem('id')
  window.sessionStorage.removeItem('username')
  router.push('/')
}
</script>

<template>
  <div style="float:left;width:15%;height: 100vh;position: fixed">
    <n-menu
        :options="menuOptions"
        :value="$route.name"
    />
  </div>
  <div style="float:left;width:15%;height: 100vh"/>
  <div style="overflow:hidden">
    <n-space vertical justify="end" style="">
      <div style="text-align: end;margin-right: 20px">
        <label style="line-height: 40px;cursor:pointer;" @click="outLogin">退出登录</label>
      </div>
      <div style="width:84%;top: 40px;bottom: 0;position: absolute">
        <router-view/>
      </div>
    </n-space>
  </div>


</template>

<style scoped>

</style>
