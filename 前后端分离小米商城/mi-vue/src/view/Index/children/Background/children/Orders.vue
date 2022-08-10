<script setup lang="ts">
import {computed, onMounted, reactive, ref} from "vue";
import axios from "axios";

const pagination = reactive({
  page: 2,
  pageSize: 5,
  showSizePicker: true,
  pageSizes: [
    {
      label: '3 / 页',
      value: 3
    }, {
      label: '5 / 页',
      value: 5
    }, {
      label: '7 / 页',
      value: 7
    }
  ],
  onChange: (page: number) => {
    pagination.page = page
  },
  onUpdatePageSize: (pageSize: number) => {
    pagination.pageSize = pageSize
    pagination.page = 1
  }
})

//排序
const sortStatesRef = ref([])
const sortKeyMapOrderRef = computed(() =>
    sortStatesRef.value.reduce((result, {columnKey, order}) => {
      result[columnKey] = order
      return result
    }, {})
)
//数据类型
type orderForm = {
  id: string
  goodName: string
  price: number
  consignee: string
  address: string
  creationTime: string
  goodsNum: number
  note: string
}
//表单头
const columns = reactive(computed(() => [
  {
    title: 'Id',
    key: 'id',
    fixed:'left',
    width:'40'
  },
  {
    title: 'GoodName',
    key: 'goodName',
    fixed: 'left',
    width: '160'
  },
  {
    title: 'Price',
    key: 'price',
    sortOrder: sortKeyMapOrderRef.value.price || false,
    sorter(rowA, rowB) {
      return rowA.price - rowB.price
    }
  },
  {
    title: 'Consignee',
    key: 'consignee'
  },
  {
    title: 'Address',
    key: 'address'
  },
  {
    title: 'CreationTime',
    key: 'creationTime'
  },
  {
    title: 'GoodsNum',
    key: 'goodsNum',
    sortOrder: sortKeyMapOrderRef.value.goodsNum || false,
    sorter(rowA, rowB) {
      return rowA.goodsNum - rowB.goodsNum
    }
  },
  {
    title: 'Note',
    key: 'note'
  }
]))

//点击排序
function handleUpdateSorter(sorters) {
  console.log(sorters)
  sortStatesRef.value = [].concat(sorters)
  console.log(sortStatesRef)
}

//数据
const data = reactive<orderForm[]>([])

onMounted(()=>{
  axios.get('/order/get',{
    params:{
      userId:window.sessionStorage.getItem('userId')
    }
  })
      .then(res=>{
        // console.log(res.data)
        data.length=0
        for (let i = 0; i < res.data.length; i++) {
          for (let j = 0; j <res.data[i].ex1List.length; j++) {
            data.push({
              id: res.data[i].ex1List[j].id,
              goodName: res.data[i].ex1List[j].goods.name,
              price: res.data[i].ex1List[j].goods.price,
              consignee: res.data[i].consignee,
              address: res.data[i].address,
              creationTime: res.data[i].creationTime,
              goodsNum: res.data[i].ex1List[j].goodsNum,
              note:res.data[i].ex1List[j].note
            })
          }
        }
      })
})
</script>

<template>
  <div style="width: 100%;margin-left:20px">
    <h1>我的订单</h1>
    <n-scrollbar x-scrollable>
      <n-data-table style="width: 96%" :columns="columns" :data="data" :pagination="pagination"
                    @update:sorter="handleUpdateSorter"/>
    </n-scrollbar>
  </div>
</template>

<style scoped>

</style>
