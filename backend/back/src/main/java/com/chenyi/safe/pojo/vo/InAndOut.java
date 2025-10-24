package com.chenyi.safe.pojo.vo;

import lombok.Data;

/**
 * safe
 * 2024/6/14 下午10:29
 * 流入流出流量
 *
 * @author chenyi
 * @since
 **/

@Data
public class InAndOut {
    private String srcIp;
    private String dstIp;
}
