package com.chenyi.safe.pojo.vo;

import lombok.Data;

/**
 * back
 * 2024/6/25 下午10:25
 * 24小时流量统计
 *
 * @author chenyi
 * @since
 **/

@Data
public class TimePeriodCount {
    private String timePeriod;
    private int count;
}
