package com.chenyi.safe.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author：ChenYi
 * @Package：com.chenyi.safe.pojo.vo
 * @Project：network-security-java-backend
 * @name：TimeAndSize
 * @Date：2024/6/14 下午9:34
 * @Filename：TimeAndSize
 */

@Data
@AllArgsConstructor
public class TimeAndFlow {

    private String timestamp;
    private Double packetLengthMean;
}
