import Vue from 'vue';
import Router from 'vue-router';
import Index from '@/pages/Index/index.vue'
import Flow from '@/pages/Flow/Flow.vue'
import Host from '@/pages/Host/Host.vue'
import Rule from '@/pages/Rule/Rule.vue'
import SetUp from '@/pages/SetUp/SetUp.vue'
import Warning from '@/pages/Warning/Warning.vue'
import Login from '@/pages/Login/Login.vue'
import Load from '@/pages/Loading/Load.vue'
import Test from '@/pages/Loading/Test.vue'
import Collect from '@/pages/Collect/Collect.vue'
import computer from "@/pages/NewBig/computer.vue";
import datas from "@/pages/NewBig/datas.vue";
import chat from "@/pages/NewBig/chat.vue";
import liuliang from "@/pages/NewBig/liuliang.vue";
import HostComputer from "@/pages/NewBig/HostComputer.vue";
import Flow2 from "@/pages/NewBig/Flow2.vue";
import MyChat from "@/pages/NewBig/MyChat.vue";
import controlled from "@/pages/NewBig/controlled.vue";
Vue.use(Router);

const routes = [
    {
        path: '/datas',
        name: 'datas',
        component: datas
    },
    {
        path: '/computer',
        name: 'computer',
        component: computer
    },
    {
        path: '/HostComputer',
        name: 'HostComputer',
        component: HostComputer
    },
    {
        path: '/chat',
        name: 'chat',
        component: chat
    },
    {
        path: '/liuliang',
        name: 'liuliang',
        component: liuliang
    },
    {
        path: '/Flow2',
        name: 'Flow2',
        component: Flow2
    },
    {
        path: '/MyChat',
        name: 'MyChat',
        component: MyChat
    },
    {
        path: '/controlled',
        name: 'controlled',
        component: controlled
    },
    // 默认跳转到登录页面
    {
        path: '/',
        redirect: '/Login'
    },
    // 登录页面
    {
        path: '/Login',
        name: 'Login',
        component: Login
    },
    {
        path: '/Collect',
        name: 'Collect',
        component: Collect
    },
    {
        path: '/Load',
        name: 'Load',
        component: Load
    },
    {
        path: '/Test',
        name: 'Test',
        component: Test
    },
    // 首页界面
    {
        path: '/Index',
        name: 'Index',
        component: Index
    },
    // 流量分析
    {
        path: '/Flow',
        name: 'Flow',
        component: Flow
    },
    // 规则管理
    {
        path: '/Host',
        name: 'Host',
        component: Host
    },
    // 主机配置
    {
        path: '/Rule',
        name: 'Rule',
        component: Rule
    },
    // 设置中心
    {
        path: '/SetUp',
        name: 'SetUp',
        component: SetUp
    },
    // 告警中心
    {
        path: '/Warning',
        name: 'Warning',
        component: Warning
    },

];

const router = new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
});

export default router;
