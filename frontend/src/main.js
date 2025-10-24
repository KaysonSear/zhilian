import Vue from 'vue'
import App from './App.vue'
import dataV from '@jiaminghi/data-view'
import router from './router/router';
import store from './store/index';
Vue.config.productionTip = false
import echarts from 'echarts';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);
Vue.prototype.$echarts = echarts;
Vue.use(dataV)
new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
