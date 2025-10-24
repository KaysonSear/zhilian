// src/utils/request.js

import axios from 'axios';

const service = axios.create({
    baseURL: 'http://127.0.0.1:9090', // 设置基础URL
    // baseURL: 'http://10.86.51.37:9090', // 设置基础URL
    // baseURL: 'http://10.86.51.247:9090', // 设置基础URL

    timeout: 5000, // 请求超时时间
});

// 请求拦截器
service.interceptors.request.use(
    config => {
        // 在请求发送之前可以做一些处理，例如添加请求头信息等
        return config;
    },
    error => {
        // 请求错误时的处理逻辑
        console.error('Request error:', error);
        return Promise.reject(error);
    }
);

// 响应拦截器
service.interceptors.response.use(
    response => {
        // 响应成功时的处理逻辑
        if (response.status === 200) {
            return response.data; // 返回响应数据
        } else {
            return Promise.reject('Unexpected response status');
        }
    },
    error => {
        // 响应错误时的处理逻辑
        console.error('Response error:', error);
        return Promise.reject(error);
    }
);

export default service;
