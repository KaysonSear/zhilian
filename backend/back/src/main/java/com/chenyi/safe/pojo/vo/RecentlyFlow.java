package com.chenyi.safe.pojo.vo;

import lombok.Data;

/**
 * @Author：ChenYi
 * @Package：com.chenyi.safe.pojo.vo
 * @Project：back
 * @name：RecentlyFlow
 * @Date：2024/6/23 下午1:45
 * @Filename：RecentlyFlow
 */

@Data
public class RecentlyFlow {
    private String timestamp;
    private String label;
    private Double packetLengthMean;
}
