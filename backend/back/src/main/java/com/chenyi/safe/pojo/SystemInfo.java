package com.chenyi.safe.pojo;

import lombok.Data;

/**
 * back
 * 2024/6/17 下午10:03
 * 系统状态
 *
 * @author chenyi
 * @since
 **/

@Data
public class SystemInfo {

    @Data
    public static class CPUInfo {
        private double user;
        private double system;
        private double idle;
        private double percent;

        // Constructors, getters, and setters
    }

    @Data
    public static class MemoryInfo {
        private long total;
        private long available;
        private double percent;
        private long used;
        private long free;

        // Constructors, getters, and setters
    }

    @Data
    public static class DiskInfo {
        private long total;
        private long used;
        private long free;
        private double percent;

        // Constructors, getters, and setters
    }

    @Data
    public static class NetworkInfo {
        private long bytesSent;
        private long bytesRecv;
        private long packetsSent;
        private long packetsRecv;

        // Constructors, getters, and setters
    }

    private CPUInfo cpu;
    private MemoryInfo memory;
    private DiskInfo disk;
    private NetworkInfo network;

    // Constructors, getters, and setters for SystemInfo
}
