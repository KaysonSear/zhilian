package com.chenyi.safe.utils;

/**
 * back
 * 2024/6/19 下午8:06
 * 系统评分
 *
 * @author chenyi
 * @since
 **/

import com.chenyi.safe.pojo.SystemInfo;
import org.springframework.stereotype.Component;

@Component
public class SystemEvaluator {

    public int evaluateSystem(SystemInfo systemInfo) {
        int totalScore = 0;

        // Evaluate CPU usage
        double cpuUsage = systemInfo.getCpu().getPercent();
        totalScore += evaluateCpuUsage(cpuUsage);

        // Evaluate memory usage
        double memoryUsage = systemInfo.getMemory().getPercent();
        totalScore += evaluateMemoryUsage(memoryUsage);

        // Evaluate disk usage
        double diskUsage = systemInfo.getDisk().getPercent();
        totalScore += evaluateDiskUsage(diskUsage);

        // Evaluate network traffic
        long bytesSent = systemInfo.getNetwork().getBytesSent();
        long bytesRecv = systemInfo.getNetwork().getBytesRecv();
        totalScore += evaluateNetworkTraffic(bytesSent, bytesRecv);

        return totalScore;
    }

    private int evaluateCpuUsage(double cpuUsage) {
        if (cpuUsage < 20) {
            return 5;
        } else if (cpuUsage < 40) {
            return 4;
        } else if (cpuUsage < 60) {
            return 3;
        } else if (cpuUsage < 80) {
            return 2;
        } else {
            return 1;
        }
    }

    private int evaluateMemoryUsage(double memoryUsage) {
        if (memoryUsage < 20) {
            return 5;
        } else if (memoryUsage < 40) {
            return 4;
        } else if (memoryUsage < 60) {
            return 3;
        } else if (memoryUsage < 80) {
            return 2;
        } else {
            return 1;
        }
    }

    private int evaluateDiskUsage(double diskUsage) {
        if (diskUsage < 20) {
            return 5;
        } else if (diskUsage < 40) {
            return 4;
        } else if (diskUsage < 60) {
            return 3;
        } else if (diskUsage < 80) {
            return 2;
        } else {
            return 1;
        }
    }

    private int evaluateNetworkTraffic(long bytesSent, long bytesRecv) {
        long totalTraffic = bytesSent + bytesRecv;
        long lowTrafficThreshold = 1024 * 1024 * 100; // 100 MB

        if (totalTraffic < lowTrafficThreshold) {
            return 5;
        } else if (totalTraffic < lowTrafficThreshold * 2) {
            return 4;
        } else if (totalTraffic < lowTrafficThreshold * 4) {
            return 3;
        } else if (totalTraffic < lowTrafficThreshold * 8) {
            return 2;
        } else {
            return 1;
        }
    }
}
