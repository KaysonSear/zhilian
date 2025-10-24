package com.chenyi.safe.service;

import com.chenyi.safe.pojo.CSV;
import com.chenyi.safe.pojo.MyFlow;

import java.util.List;
import java.util.Map;

/**
 * back
 * 2024/6/19 上午9:29
 * Redis相关
 *
 * @author chenyi
 * @since
 **/
public interface RedisService {
    public void setSystem(Map<String, Object> map);

    public void setCSVFlow(List<MyFlow> myFlows);

    public List<Object> getCSVFlow();

    public void setCsvCount(CSV csv);

    public CSV getCSVCount();
}
