<script setup lang="ts">
import Login from './children/Login.vue'
import {onMounted, reactive, ref} from 'vue'
import {DropdownOption, NIcon, NModal, useMessage} from 'naive-ui'
import Cart from './children/Cart.vue'
import {useStore} from "vuex";
import router from "../../router";
import axios from "axios";
import qs from "qs";

const showModal = ref(false)
const message = useMessage()
const cartShow = ref(false)
const store = useStore()
//从会话里获取用户名
const info = ref<string | null>(window.sessionStorage.getItem('loginInfo'))

function updateCart() {
  axios.get("/cart/get", {
    params: {
      userId: window.sessionStorage.getItem("userId")
    }
  }).then(res => {
    store.commit('cart', res.data)
  })
}

//用户选项
const userOptions = reactive([
  {
    label: '个人中心',
    key: 'Personal Center'
  },
  {
    label: '退出登录',
    key: 'logout'
  }
])

//搜索
const searchValue = ref()
const searchOptions = reactive([
  {
    label: '手机',
    key: '1'
  },
  {
    label: '电视',
    key: '2'
  },
  {
    label: '笔记本',
    key: '3'
  },
  {
    label: '平板',
    key: '4'
  }
  ,
  {
    label: '家电',
    key: '5'
  },
  {
    label: '路由器',
    key: '6'
  }])

function searchSelect(key: string | number) {
  let a = searchOptions.find(item => item.key == key)
  searchValue.value = a.label
  console.log(searchValue.value)
}

//用户操作选项
function handleSelect(key: string | number, option: DropdownOption) {
  if (option.key == userOptions[1].key) {
    window.sessionStorage.removeItem('loginInfo')
    window.sessionStorage.removeItem('userId')
    info.value = null
    store.state.userId = null
    router.push('/')
  }
  if (option.key == userOptions[0].key) {
    router.replace('/bg/personInfo')
  }
}

//导航菜单
const navItems = reactive([
  '手机', '电视', '笔记本', '平板', '家电', '路由器'
])

function getSearchRes(value) {
  axios.get('/admin/goods/search', {
    params: {
      search: value
    }
  }).then(res => {
    // console.log(res)
    store.commit("search", res.data)
  })
}

function clickNavItem(value) {
  // console.log(value)
  getSearchRes(value)
  router.push('/searchRes')
}

//点击logo回到首页
function clickLogo() {
  router.push('/')
}

//我的订单
function toOrders() {
  router.push('/bg/orders')
}

const settleModal = ref<boolean>(false)

//清空购物车
function clear() {
  axios.delete('/cart/clear', {
    params: {
      userId: window.sessionStorage.getItem("userId")
    }
  }).then(res => {
    updateCart()
  })
}

//地址列表
const addressList = reactive([])

function getAdd() {
  axios.get('/address/get', {
    params: {
      userId: window.sessionStorage.getItem("userId")
    }
  }).then(res => {
    addressList.length = 0
    addressList.push(...res.data)
  })
}

function settle() {
  settleModal.value = true
  getAdd()
  // console.log(addressList)
}

//提交结算对话
function submitCallback() {
  if (selectValue.value == null) {
    message.warning("请先选择地址")
  } else {
    axios.post('/order/add', qs.stringify({
      userId: selectValue.value.userId,
      consignee: selectValue.value.consignee,
      phone: selectValue.value.phone,
      region: selectValue.value.region,
      detailAddress: selectValue.value.detailAddress
    })).then(res => {
      console.log(res.data)
      if (res.data > 0) {
        message.success("购买成功")
        updateCart()
      }
    })
  }
}

const selectValue = ref(null)

//选择地址
function selectAddress(address) {
  selectValue.value = address
  // console.log(selectValue.value)
}


//初始化获取所有商品信息
onMounted(() => {

  axios.get('/admin/goods/all')
      .then(res => {
        store.commit('goods', res.data)
        console.log(store.state.goods)
      })


  if (window.sessionStorage.getItem("userId") != null && window.sessionStorage.getItem("userId") != '') {
    axios.get("/cart/get", {
      params: {
        userId: window.sessionStorage.getItem("userId")
      }
    }).then(res => {
      store.commit('cart', res.data)
    })
  }

  // console.log(store.state.goods)
})

</script>

<template>
  <div>
    <n-space vertical size="large">
      <!--    顶部菜单-->
      <div class="n-space-div">
        <n-space justify="end" style="width: 1226px;margin: auto">
          <label v-if="info==null" @click="showModal = true">登录/注册</label>
          <label v-if="info!==null">
            <n-dropdown :options="userOptions" @select="handleSelect">
              {{ info }},欢迎您
            </n-dropdown>
          </label>
          <label v-if="info!==null" @click="toOrders">我的订单</label>
          <label @click="cartShow = true">
            <n-icon>
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                <path
                    d="M7 18c-1.1 0-1.99.9-1.99 2S5.9 22 7 22s2-.9 2-2s-.9-2-2-2zM1 2v2h2l3.6 7.59l-1.35 2.45c-.16.28-.25.61-.25.96c0 1.1.9 2 2 2h12v-2H7.42c-.14 0-.25-.11-.25-.25l.03-.12l.9-1.63h7.45c.75 0 1.41-.41 1.75-1.03l3.58-6.49A1.003 1.003 0 0 0 20 4H5.21l-.94-2H1zm16 16c-1.1 0-1.99.9-1.99 2s.89 2 1.99 2s2-.9 2-2s-.9-2-2-2z"
                    fill="currentColor"></path>
              </svg>
            </n-icon>
            购物车({{ info != null ? store.state.cartList.length : 0 }})
          </label>
        </n-space>
      </div>
      <!--      导航菜单-->
      <div>
        <n-space justify="start" style="width: 1226px;margin: auto">
          <n-image preview-disabled style="cursor: pointer" @click="clickLogo" width="55"
                   src="/images/logo.svg"></n-image>
          <div style="width: 1150px">
            <n-space justify="end">
              <!--              分类-->
              <ul>
                <li v-for="value in navItems" @click="clickNavItem(value)">{{ value }}</li>
                <li>服务中心</li>
                <li>社区</li>
              </ul>

              <!--              搜索框-->
              <n-input-group>
                <n-dropdown
                    width="253"
                    trigger="hover"
                    placement="bottom-start"
                    :options="searchOptions"
                    @select="searchSelect"
                >
                  <n-input :style="{ width: '253px',height: '49px',lineHeight:'49px' }" v-model:value="searchValue"/>
                </n-dropdown>
                <n-button @click="clickNavItem(searchValue)" style="height: 49px;width: 80px" type="primary" ghost>
                  搜索
                </n-button>
              </n-input-group>
            </n-space>
          </div>
        </n-space>
      </div>
    </n-space>
  </div>
  <!-- 模态框、抽屉等-->
  <div>
    <n-modal v-model:show="showModal" transform-origin="center" style="margin: auto">
      <Login/>
    </n-modal>
    <n-drawer v-model:show="cartShow" :width="450">
      <n-drawer-content title="购物车" closable>
        <n-scrollbar style="max-height: 85%" trigger="none">
          <n-empty v-if="info==null" description="你什么也找不到">
            <template #extra>
              <n-button @click="showModal=true" size="small">
                先登录吧
              </n-button>
            </template>
          </n-empty>
          <Cart v-if="info!=null"/>
        </n-scrollbar>
        <div style="height: 15%">
          <n-space justify="space-between" size="large">
            <div style="text-align: center">
              共{{ info != null ? store.state.cartList.length : 0 }}件商品
              <div style="color: #ff6700">
                {{ info != null ? store.getters.getCartSum : 0 }}元
              </div>
            </div>
            <div>
              <n-button style="width: 100px;margin-right: 10px" :disabled="info==null" @click="settle">结算
              </n-button>
              <n-button style="width: 100px" :disabled="info==null" @click="clear">清空</n-button>
            </div>

            <!--            结算页面-->
            <n-modal
                v-model:show="settleModal"
                preset="dialog"
                title="选择地址"
                positive-text="取消"
                negative-text="确认"
                @negative-click="submitCallback"
                :positive-button-props="{type:'default'}"
                transform-origin="center"
            >
              <n-scrollbar style="max-height: 160px">
                <n-grid :x-gap="12" :y-gap="12" :cols="2">
                  <n-grid-item v-for="item in addressList">
                    <div style="height: 130px">
                      <n-card :class="selectValue==item?'selected':'not-selected'"
                              style="width: 100%;height: 100%;cursor: pointer"
                              @click="selectAddress(item)">
                        <div>{{ item.consignee }}</div>
                        <div>
                          <n-ellipsis style="max-width: 100%">
                            {{ item.phone }}
                          </n-ellipsis>
                        </div>
                        <div>
                          <n-ellipsis style="max-width: 100%">
                            {{ item.region }}
                          </n-ellipsis>
                        </div>
                        <div>{{ item.detailAddress }}</div>
                        <div>{{ item.label }}</div>
                      </n-card>
                    </div>
                  </n-grid-item>
                </n-grid>
              </n-scrollbar>
            </n-modal>
          </n-space>
        </div>
      </n-drawer-content>
    </n-drawer>
  </div>
</template>
<style scoped>
.not-selected {
  border: 1px #797979 solid;
}

.selected {
  border: 1px #ff6700 solid;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
}

.n-space label:hover {
  color: #fcfcfc;
  cursor: pointer;
}

.n-space label {
  color: #b0b0b0;
  font-size: 12px;
  padding: 5px;
}

.n-space-div {
  background-color: #333;
  height: 40px;
  line-height: 35px;
}

ul {
  list-style-type: none;
  display: inline;
  margin: 70px;
}

ul li {
  display: inline-block;
  margin: 12px;
  padding-left: 5px;
  font-size: 18px;
}

ul li:hover {
  color: #ff6700;
  cursor: pointer;
}
</style>
