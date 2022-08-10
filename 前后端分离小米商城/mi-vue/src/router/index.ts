import {createRouter, createWebHashHistory} from "vue-router";
import * as path from "path";
import { ElMessage } from "element-plus"

const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: '/',
            name: 'index',
            component: () => import('../view/Index/Index.vue')
        },
        {
            path: '/detail',
            name: 'detail',
            component: () => import('../view/Index/children/GoodDetail/GoodDetail.vue')
        },
        {
            path: '/bg',
            name: 'bg',
            component: () => import('../view/Index/children/Background/Background.vue'),
            children: [
                {
                    path: 'orders',
                    name: '我的订单',
                    component: () => import('../view/Index/children/Background/children/Orders.vue')
                }, {
                    path: 'personInfo',
                    name: '个人信息',
                    component: () => import('../view/Index/children/Background/children/PersonInfo.vue')
                }, {
                    path: 'address',
                    name: '收货地址',
                    component: () => import('../view/Index/children/Background/children/Address.vue')
                }, {
                    path: 'upPwd',
                    name: '修改密码',
                    component: () => import('../view/Index/children/Background/children/UpPwd.vue')
                }
            ]
        },
        {
            path: '/searchRes',
            name: 'searchRes',
            component: () => import('../view/Index/children/SearchRes.vue')
        }
    ]
})

router.beforeEach((to, from, next) => {
    if (to.path == '/' || to.path == '/searchRes') {
        // 登录或者注册才可以往下进行
        next();
    } else {
        // 获取 token
        const token = window.sessionStorage.getItem('userId');
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
