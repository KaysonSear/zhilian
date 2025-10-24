package com.chenyi.safe.pojo.dto;

import lombok.Data;

/**
 * safe
 * 2024/6/14 下午11:08
 * 流入流量大小
 *
 * @author chenyi
 * @since
 **/

@Data
public class InboundTraffic {
    private String dstIp;
    private Double totalLengthOfBwdPacket;
}
