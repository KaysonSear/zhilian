package com.chenyi.safe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenyi.safe.pojo.Flow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * safe
 * 2024/6/14 上午12:31
 * 地址相关的操作
 *
 * @author chenyi
 * @since
 **/

@Mapper
public interface GeoIPMapper extends BaseMapper<Flow> {

    /**
     * 查询源 IP 和目标 IP 出现次数最多的 IP
     * @return IP 和出现次数的映射
     */
    @Select("SELECT src_ip AS ip, COUNT(*) AS count FROM flow GROUP BY src_ip " +
            "UNION " +
            "SELECT dst_ip AS ip, COUNT(*) AS count FROM flow GROUP BY dst_ip " +
            "ORDER BY count DESC LIMIT 1")
    Map<String, Integer> findMostFrequentIP();


    /**
     * 获取源IP和目标IP，同时若有重复的IP就去掉，保证原子性
     * @return 不重复的IP列表
     */
    @Select("SELECT DISTINCT ip FROM (" +
            "SELECT src_ip AS ip FROM flow " +
            "UNION " +
            "SELECT dst_ip AS ip FROM flow" +
            ") AS distinct_ips")
    List<String> findDistinctIPs();


}
