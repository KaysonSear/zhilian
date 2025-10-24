package com.chenyi.safe.pojo.dto;

import lombok.Data;

/**
 * safe
 * 2024/6/14 下午10:41
 * 统一计算本机IP在源IP和目标IP中的占比
 *
 * @author chenyi
 * @since
 **/

@Data
public class IpRatioDTO {
    private Double srcIpRatio;
    private Double dstIpRatio;
}
