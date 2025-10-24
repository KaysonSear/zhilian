package com.chenyi.safe.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * back
 * 2024/6/20 下午11:12
 * IP出现数量
 *
 * @author chenyi
 * @since
 **/

@Data
@AllArgsConstructor
public class IpCount {
    private String ip;
    private Long count;

    // Getters and setters

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}