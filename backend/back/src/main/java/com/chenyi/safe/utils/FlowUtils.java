package com.chenyi.safe.utils;

import java.text.SimpleDateFormat;
import java.util.*;


/**
* back 
* 2024/7/2 上午12:23
* 流量的工具类
*
*
* @author chenyi
* @since 
**/
public class FlowUtils {

    private String getTimePeriodFromTimestamp(String timestamp) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = sdf.parse(timestamp);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int hour = calendar.get(Calendar.HOUR_OF_DAY); // 获取小时数
            return String.valueOf(hour);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
