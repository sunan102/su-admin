import {constantRouterMap} from './router'
import router from './router'
import store from './store'
import NProgress from 'nprogress' // Progress 进度条
import 'nprogress/nprogress.css'// Progress 进度条样式
import {Message} from 'element-ui'
import {getToken} from '@/utils/auth' // 验权
import Layout from '@/views/layout/Layout'

const whiteList = ['/login'] // 不重定向白名单
router.beforeEach((to, from, next) => {
    NProgress.start()
    if (getToken()) {
        if (to.path === '/login') {
            next({path: '/'})
            NProgress.done() // if current page is dashboard will not trigger	afterEach hook, so manually handle it
        } else {
            if (store.getters.roles.length === 0) {
                store.dispatch('GetInfo').then(res => { // 拉取用户信息
                    let dr = createUserRouterMap(res.data.menuList, "0");
                    dr.push({
                        path: '*',
                        redirect: '/404'
                    });
                    router.options.routes = constantRouterMap.concat(dr);
                    router.addRoutes(dr);
                    next({...to})
                }).catch((err) => {
                    store.dispatch('FedLogOut').then(() => {
                        Message.error(err || 'Verification failed, please login again')
                        next({path: '/'})
                    })
                })
            } else {
                next()
            }
        }
    } else {
        if (whiteList.indexOf(to.path) !== -1) {
            next()
        } else {
            next('/login')
            NProgress.done()
        }
    }
})

router.afterEach(() => {
    NProgress.done() // 结束Progress
})

function createUserRouterMap(routerList, pid) { //创建用户路由
    let childrenArr = [];
    if (routerList && routerList.length > 0) {
        for (let i = 0; i < routerList.length; i++) {
            if (routerList[i].pid == pid) {
                let children = createUserRouterMap(routerList, routerList[i].adminMenuId);
                let router_ele = {
                    path: routerList[i].url,
                    component: Layout,
                    name: routerList[i].vueName,
                    title: routerList[i].menuName,
                    icon: routerList[i].icon
                };
                if (children && children.length > 0) {
                    router_ele.children = children;
                }
                let component = routerList[i].page;
                router_ele.component = routerList[i].pid == "0" ? Layout : resolve => require([`@/views/${component}`], resolve);
                childrenArr.push(router_ele);
            }
        }
    }
    childrenArr = childrenArr.sort(function (a, b) {
        return a.num - b.num;
    })
    return childrenArr;
}