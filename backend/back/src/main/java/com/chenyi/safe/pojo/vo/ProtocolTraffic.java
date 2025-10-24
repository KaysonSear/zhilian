package com.chenyi.safe.pojo.vo;

import lombok.Data;

/**
 * safe
 * 2024/6/14 下午11:33
 * 协议与流量大小
 *
 * @author chenyi
 * @since
 **/

@Data
public class ProtocolTraffic {
    private Integer protocol;
    private Double packetLengthMean;
}
