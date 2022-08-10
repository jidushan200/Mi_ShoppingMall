import {createRouter, createWebHashHistory} from "vue-router";
import * as path from "path";
import {ElMessage} from "element-plus";

const router = createRouter({
    history:createWebHashHistory(),
    routes:[
        {
            path: '/',
            name:'login',
            component:()=>import('../components/login.vue')
        },
        {
            path: '/children',
            name: 'index',
            component: () => import('../components/index.vue'),
            children:[
                {
                    path: 'userAdmin',
                    name: '账号管理',
                    component: () => import('../components/children/user.vue'),
                },{
                    path: 'addressAdmin',
                    name: '地址管理',
                    component: () => import('../components/children/address.vue'),
                },{
                    path: 'goodsAdmin',
                    name: '商品管理',
                    component: () => import('../components/children/goods.vue'),
                },{
                    path: 'orderAdmin',
                    name: '订单管理',
                    component: () => import('../components/children/order.vue'),
                },{
                    path: 'cartAdmin',
                    name: '购物车',
                    component: () => import('../components/children/cart.vue'),
                }
            ]
        }
    ]
})

router.beforeEach((to, from, next) => {
    if (to.path == '/') {
        // 登录或者注册才可以往下进行
        next();
    } else {
        // 获取 token
        const token = window.sessionStorage.getItem('id');
        // token 不存在
        if (token === null || token === '') {
            ElMessage.error("请先登录")
            next('/');
        } else {
            next();
        }
    }
});
export default router
