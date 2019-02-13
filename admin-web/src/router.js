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
  },
  {path: '*', redirect: '/404', hidden: true}
]
export default new Router({
  mode: 'history',
  // mode: 'hash',
  base: process.env.BASE_URL,
  scrollBehavior: () => ({y: 0}),
  routes: constantRouterMap
})
