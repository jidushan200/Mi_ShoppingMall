<script setup lang="ts">
import {AddCircle} from '@vicons/ionicons5'
import {CascaderOption, useMessage} from 'naive-ui';
import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import qs from "qs";

const showModal = ref(false)
const showDialog = ref(false)
const message=useMessage()

//地址列表
const addressList=reactive([])

function getAdd(){
  axios.get('/address/get',{
    params:{
      userId:window.sessionStorage.getItem("userId")
    }
  }).then(res=>{
    addressList.length=0
    addressList.push(...res.data)
  })
}
onMounted(()=>{
  getAdd()
})

//姓名
const name = ref(null)
//手机号
const phone = ref(null)
//地区
const region = ref(null)
const options = reactive<CascaderOption[]>([
  {
    value: '北京',
    label: '北京',
    children: [
      {
        value: '北京市东城区',
        label: '东城区'
      }, {
        value: '北京市西城区',
        label: '西城区'
      }, {
        value: '北京市朝阳区',
        label: '朝阳区'
      }, {
        value: '北京市丰台区',
        label: '丰台区'
      }, {
        value: '北京市石景山区',
        label: '石景山区'
      }, {
        value: '北京市海淀区',
        label: '海淀区'
      }, {
        value: '北京市门头沟区',
        label: '门头沟区'
      }, {
        value: '北京市房山区',
        label: '房山区'
      }, {
        value: '北京市通州区',
        label: '通州区'
      }, {
        value: '北京市顺义区',
        label: '顺义区'
      }, {
        value: '北京市昌平区',
        label: '昌平区'
      }, {
        value: '北京市大兴区',
        label: '大兴区'
      }, {
        value: '北京市怀柔区',
        label: '怀柔区'
      }, {
        value: '北京市平谷区',
        label: '平谷区'
      }, {
        value: '北京市密云区',
        label: '密云区'
      }, {
        value: '北京市延庆区',
        label: '延庆区'
      }
    ]
  }, {
    value: '浙江',
    label: '浙江',
    children: [
      {
        value: '浙江省杭州市',
        label: '杭州市'
      }, {
        value: '浙江省绍兴市',
        label: '绍兴市'
      }, {
        value: '浙江省金华市',
        label: '金华市'
      }
    ]
  }, {
    value: '上海',
    label: '上海'
  }, {
    value: '江苏',
    label: '江苏'
  }
])
//详细地址
const detailAddress = ref(null)
//标签
const label = ref(null)

//添加新地址
function insertAddress() {
  axios.post('/address/insert', qs.stringify({
    consignee: name.value,
    region: region.value,
    detailAddress: detailAddress.value,
    label: label.value,
    phone:phone.value,
    userId: window.sessionStorage.getItem("userId")
  })).then(res=>{
    if (res.data){
      message.success("添加成功")
      getAdd()
    }else {
      message.error("添加失败")
    }
  })
  handleClose()
}

function handleClose() {
  showModal.value = false
  name.value=null
  phone.value = null
  region.value = null
  detailAddress.value = null
  label.value = null
}

//是否显示修改、删除按钮
const isShow = ref(false)

//点击删除
const delId=ref(null)
function delAddress(index:number){
  showDialog.value=true
  delId.value=index
}
//确认删除
function handleNegativeClick() {
  axios.delete('/address/delete',{
    params:{
      id:addressList[delId.value].id
    }
  }).then(res=>{
    getAdd()
  })
  showDialog.value=false
  delId.value=null
}

//点击修改
const upId=ref(null)
function upAdd(index:number){
  showModal.value = true
  name.value=addressList[index].consignee
  region.value=addressList[index].region
  detailAddress.value=addressList[index].detailAddress
  label.value=addressList[index].label
  phone.value=addressList[index].phone
  upId.value=index
}
function update(){
  axios.put('/address/update', qs.stringify({
    consignee: name.value,
    region: region.value,
    detailAddress: detailAddress.value,
    label: label.value,
    phone:phone.value,
    id:addressList[upId.value].id,
    userId:window.sessionStorage.getItem("userId")
  })).then(res=> {
    if (res.data) {
      console.log(res)
      message.success("修改成功")
      getAdd()
    } else {
      message.error("修改失败")
    }
  })
  upId.value=null
  handleClose()
}
</script>

<template>
  <div style="width: 100%;margin-left:20px">
    <h1>收货地址</h1>
    <div style="width: 860px;margin-left:25px;display: block">
      <n-grid :x-gap="16" :y-gap="16" :cols="3">
        <n-grid-item>
          <div class="light-add" @click="showModal = true">
            <n-space style="height: 100%" vertical align="center" justify="center">
              <n-icon size="40" color="#e0e0e0">
                <AddCircle/>
              </n-icon>
              <span>添加新地址</span>
            </n-space>
          </div>
        </n-grid-item>
        <n-grid-item v-for="(item,index) in addressList">
          <div class="light-add" @mouseenter="isShow=true" @mouseleave="isShow=false">
            <n-space vertical :size="-2" style="padding-top: 23px;padding-left: 25px">
              <span style="font-size: 18px;display:inline-block;margin-bottom: 11px">{{ item.consignee }}</span>
              <span style="font-size: 14px;color: #a8a8a8">{{ item.phone }}</span>
              <span style="font-size: 14px;color: #a8a8a8">{{ item.region }}</span>
              <span style="font-size: 14px;color: #a8a8a8">{{ item.detailAddress }}</span>
              <span style="font-size: 14px;color: #a8a8a8">{{ item.label }}</span>
            </n-space>
            <span style="width: 88%;display: inline-block;text-align: end;margin-bottom: 20px" v-show="isShow">
              <label style="margin-right: 20px;cursor: pointer;color: #ff6700" @click="upAdd(index)">修改</label>
              <label style="cursor: pointer;color: #ff6700" @click="delAddress(index)">删除</label>
            </span>
          </div>
        </n-grid-item>
      </n-grid>
    </div>

    <!--    地址卡片-->
    <n-modal v-model:show="showModal" :mask-closable="false">
      <n-card style="width: 664px;height: 435px" title="收货地址" closable
              @close="handleClose">
        <n-input style="width: 300px" v-model:value="name" type="text" placeholder="姓名"/>
        <n-input style="width: 300px;margin-left: 12px" v-model:value="phone" type="text" placeholder="手机号"/>
        <n-cascader
            style="margin-top: 16px"
            v-model:value="region"
            placeholder="选择/省/市/区"
            expand-trigger="hover"
            :options="options"
            check-strategy="child"
            separator=" "
        />
        <n-input
            style="margin-top: 16px"
            v-model:value="detailAddress"
            type="textarea"
            placeholder="详细地址"
        />
        <n-input style="margin-top: 16px" type="text" v-model:value="label" placeholder="标签(家、公司等)"/>
        <div style="width: 100%;height: 81px;background-color: #f5f5f5;margin-top: 25px">
          <n-space style="padding-top: 15px" justify="center">
            <n-button v-show="upId==null" style="width: 160px;height: 40px" type="primary" color="#ff6700" @click="insertAddress">
              确定
            </n-button>
            <n-button v-show="upId!=null" style="width: 160px;height: 40px" type="primary" color="#ff6700" @click="update">
              确定
            </n-button>
            <n-button style="width: 160px;height: 40px" ghost color="#ff6700" @click="handleClose">
              取消
            </n-button>
          </n-space>
        </div>
      </n-card>
    </n-modal>

    <!--    对话框-->
    <n-modal v-model:show="showDialog" transform-origin="center">
      <n-dialog
          title="确认"
          content="你确定删除该地址吗？"
          negative-text="确认"
          positive-text="取消"
          @negative-click="handleNegativeClick"
          @positive-click="showDialog=false"
          @close="showDialog=false"
          :negative-button-props="{type:'primary'}"
          :positive-button-props="{type:'default'}"
      />
    </n-modal>
  </div>

</template>

<style scoped>
.light-add {
  height: 180px;
  border: 1px #cccccc solid;
}

.light-add:hover {
  cursor: pointer;
}

</style>
