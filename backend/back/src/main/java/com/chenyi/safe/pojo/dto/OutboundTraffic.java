package com.chenyi.safe.pojo.dto;

import lombok.Data;

/**
 * safe
 * 2024/6/14 下午11:04
 * 流出IP总流量
 *
 * @author chenyi
 * @since
 **/

@Data
public class OutboundTraffic {
    private String srcIp;
    private Double totalLengthOfFwdPacket;
}
