import Vue from 'vue'
import Router from 'vue-router'
import Layout from './views/layout/Layout'

Vue.use(Router)

export const constantRouterMap = [
    {path: '/login', component: () => import('@/views/login/index'), hidden: true},
    {path: '/404', component: () => import('@/views/404'), hidden: true},
    {
        path: '/',
        component: Layout,
        redirect: '/dashboard',
        name: 'Dashboard',
        hidden: true,
        children: [{
            path: 'dashboard',
            name: '_dashboard',
            component: () => import('@/views/dashboard/index')
        }]
    }
    // {
    //     path: '/accounts',
    //     component: Layout,
    //     redirect: '/accounts/user',
    //     name: 'accounts',
    //     title: '账户及权限',
    //     icon: 'icon-caidanyingyong1',
    //     children: [
    //         {
    //             path: '/accounts/user',
    //             name: 'user',
    //             component: () => import('@/views/admin/user/index'),
    //             title: '账号管理',
    //             icon: 'icon-zhanghao'
    //         },
    //         {
    //             path: '/accounts/menu',
    //             name: 'menu',
    //             component: () => import('@/views/admin/menu/index'),
    //             title: '菜单管理',
    //             icon: 'icon-zhanghao'
    //         }
    //     ]
    // }
    // {path: '*', redirect: '/404', hidden: true}
]

export default new Router({
    mode: 'history',
    // mode: 'hash',
    base: process.env.BASE_URL,
    scrollBehavior: () => ({y: 0}),
    routes: constantRouterMap
})
