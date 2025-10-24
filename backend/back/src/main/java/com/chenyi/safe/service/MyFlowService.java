package com.chenyi.safe.service;

import com.chenyi.safe.pojo.Rule;
import com.chenyi.safe.pojo.vo.GeoIPInfo;
import com.chenyi.safe.pojo.vo.ProtocolCount;
import com.chenyi.safe.pojo.vo.RecentlyFlow;

import java.util.List;

/**
 * @Author：ChenYi
 * @Package：com.chenyi.safe.service
 * @Project：back
 * @name：MyFlowService
 * @Date：2024/6/24 下午4:31
 * @Filename：MyFlowService
 */
public interface MyFlowService {

    List<ProtocolCount> getProtocolCounts();

    List<List<RecentlyFlow>> groupFlowsByDay(List<RecentlyFlow> flows);

    List<GeoIPInfo> getAddress();
}
