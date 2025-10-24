package com.chenyi.safe.service.impl;

import com.chenyi.safe.pojo.CSV;
import com.chenyi.safe.pojo.MyFlow;
import com.chenyi.safe.service.RedisService;
import com.chenyi.safe.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * back
 * 2024/6/19 上午9:30
 * Redis实现
 *
 * @author chenyi
 * @since
 **/

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public CSV getCSVCount() {
        return (CSV) redisUtils.get("count");
    }

    @Override
    public void setCsvCount(CSV csv) {
        redisUtils.set("count",csv);
    }

    @Override
    public List<Object> getCSVFlow() {
        List<Object> csv = redisUtils.lGet("csv", 0, -1);
        return csv;
    }

    @Override
    public void setCSVFlow(List<MyFlow> myFlows) {
        redisUtils.deleteKey("csv");
        redisUtils.lSet("csv",myFlows);
    }

    @Override
    public void setSystem(Map<String, Object> map) {
        // 检查并获取 CPU 信息
        Map<String, Object> cpuMap = (Map<String, Object>) map.get("CPU");
        if (cpuMap != null) {
            redisUtils.hmset("cpuInfo", cpuMap);
        } else {
            System.out.println("CPU map is null");
        }

        // 检查并获取内存信息
        Map<String, Object> memoryMap = (Map<String, Object>) map.get("Memory");
        if (memoryMap != null) {
            redisUtils.hmset("memoryInfo", memoryMap);
        } else {
            System.out.println("Memory map is null");
        }

        // 检查并获取网络信息
        Map<String, Object> networkMap = (Map<String, Object>) map.get("Network");
        if (networkMap != null) {
            redisUtils.hmset("networkInfo", networkMap);
        } else {
            System.out.println("Network map is null");
        }

        // 检查并获取磁盘信息
        Map<String, Object> diskMap = (Map<String, Object>) map.get("Disk");
        if (diskMap != null) {
            redisUtils.hmset("diskInfo", diskMap);
        } else {
            System.out.println("Disk map is null");
        }
    }
}
