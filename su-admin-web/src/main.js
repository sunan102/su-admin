import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import "normalize.css/normalize.css";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import '@/styles/index.scss' // global css
import "./assets/iconfont/iconfont.css"

import '@/permission' // permission control

Vue.use(ElementUI);
Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
