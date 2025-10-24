// src/store/index.js

import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

const store = new Vuex.Store({
    state: {
        HostNum: 1,
    },
    mutations: {
        // 定义 mutation 来修改 HostNum 的值
        setHostNum(state, newValue) {
            state.HostNum = newValue;
        }
    },
    actions: {
        // 定义 action 来调用 mutation 修改 HostNum 的值
        updateHostNum({ commit }, newValue) {
            commit('setHostNum', newValue);
        }
    },
    getters: {
        // 如果需要定义 getters，可以在这里添加
    },
});

export default store;
