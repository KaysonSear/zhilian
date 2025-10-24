package com.chenyi.safe.utils;

import com.chenyi.safe.pojo.SystemInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * back
 * 2024/6/19 上午12:06
 * 系统状态相关工具类
 *
 * @author chenyi
 * @since
 **/
public class SystemUtils {


    /**
     * 把多层 Map 转为一层
     * @param prefix
     * @param map 入参
     * @return Map<String,Object>
     * @author Chenyi
     * @date 2024/6/19 上午12:02
     **/
    public static Map<String, Object> flattenMap(String prefix, Map<?, ?> map) {
        Map<String, Object> flattenedMap = new HashMap<>();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Map) {
                Map<String, Object> nestedMap = flattenMap(prefix + entry.getKey() + ".", (Map<?, ?>) value);
                flattenedMap.putAll(nestedMap);
            } else {
                flattenedMap.put(prefix + entry.getKey(), value);
            }
        }
        return flattenedMap;
    }

}
