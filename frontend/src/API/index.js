// src/API/index.js
import request from '../utils/Request';
export const flowtest = (data) => {
    return request.get(`/API/flow/getAll`, data);
}
/**
 * HIDS主机检测接口
 */
export const evaluate = (data) => {
    return request.get(`/myFlow/evaluate`, data);
};

/**
 * 规则增删改查接口
 */
// 删除上链规则
export const deleteFlow = (data) => {
    return request.get(`/block/deleteFlow?flowId=${data}`, data);
};
//获取区块链上的规则
export const getAllRules_block = (data) => {
    return request.get('/block/getAllRules', data);
};
/**
   告警中心
 */
export const getAttackType = (data) => {
    return request.get(`/myFlow/getAttackType?label=${data}`, data);
};
export const getLabelCounts = (data) => {
    return request.get('/myFlow/getLabelCounts', data);
};
export const getProtocolCounts = (data) => {
    return request.get('/myFlow/getProtocolCounts', data);
};
export const getIPCount = (data) => {
    return request.get('/myFlow/getIPCount', data);
};
/**
 流量分析
 */
export const getAllFlow = (data) => {
    return request.get('/myFlow/getAllFlow', data);
};
/**
 主机检测
 */
export const getDisk = (data) => {
    return request.get('/myFlow/getDisk', data);
};
export const getMemory = (data) => {
    return request.get('/myFlow/getMemory', data);
};
export const getCpu = (data) => {
    return request.get('/myFlow/getCpu', data);
};
/**
 规则检测
 */
export const getAllRule = (data) => {
    return request.get('/rule/getAllRule', data);
};
/**
 首页
 */
export const fetchDataByDayGroups = (data) => {
    return request.get('/myFlow/fetchDataByDayGroups', data);
};
export const getAddress = (data) => {
    return request.get('/myFlow/getAddress', data);
};
export const events = (data) => {
    return request.get('/events', data);
};
/**
 * 文件上传  进行AI模型或者规则匹配
 */
export const uploadFile = (file,type) => {
    const formData = new FormData();
    formData.append('file', file);

    return request.post(`/file/upload?type=${type}`, formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    });
};

/**
 * 获取最近一次上传的分析结果
 */
export const getCSV = (data) => {
    return request.get('/file/getCSV', data);
};
