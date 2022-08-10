import {createStore} from 'vuex'
import {computed, reactive, ref} from "vue";
import axios from "axios";
import createVuexAlong from 'vuex-along'
import qs from "qs";

export default createStore({
    state: {
        cartList: reactive([]),
        goods: reactive([]),
        searchList: reactive([])
    },
    mutations: {
        goods: function (state, goods) {
            this.state.goods.length=0
            this.state.goods.push(...goods)
        },
        cart: function (state, cart) {
            this.state.cartList.length = 0
            this.state.cartList.push(...cart)
        },
        search: function (state, search) {
            this.state.searchList.length = 0
            this.state.searchList.push(...search)
        }
    },
    actions: {},
    getters: {
        getCartSum(state) {
            let sum = 0
            for (let i = 0; i < state.cartList.length; i++) {
                const goods = state.goods.find(item => item.goods.id === state.cartList[i].goodsId).goods;
                sum += goods.price * state.cartList[i].goodsNum
            }
            return sum
        }
    },
    modules: {},
    plugins: [createVuexAlong({
        name: "vuex-along-01", // 设置保存的集合名字，避免同站点下的多项目数据冲突
        // local: {
        //     list: ["goods"],
        //     isFilter: true // 过滤模块 ma 数据， 将其他的存入 localStorage
        // },
        session: {
            list: ["goods", "cartList", "searchList"] // 保存 goods 到 sessionStorage
        }
    })]
})

