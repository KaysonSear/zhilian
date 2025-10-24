package com.chenyi.safe.service;

import com.chenyi.safe.pojo.vo.GeoIPInfo;

import java.util.List;
import java.util.Map;

/**
 * safe
 * 2024/6/14 上午3:08
 * 地址相关的操作
 *
 * @author chenyi
 * @since
 **/

public interface GeoIPService {

    Map<String, Integer> findMostFrequentIP();

    List<GeoIPInfo> getAddress();
}
