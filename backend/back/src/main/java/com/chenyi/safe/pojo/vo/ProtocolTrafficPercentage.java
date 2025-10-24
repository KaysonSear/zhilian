package com.chenyi.safe.pojo.vo;

import lombok.Data;

/**
 * safe
 * 2024/6/14 下午11:40
 * 协议流量比
 *
 * @author chenyi
 * @since
 **/
@Data
public class ProtocolTrafficPercentage {
    private Integer protocol;
    private Double packetLengthMean;
}
