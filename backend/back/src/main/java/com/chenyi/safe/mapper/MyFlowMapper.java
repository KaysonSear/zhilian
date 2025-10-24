package com.chenyi.safe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenyi.safe.pojo.MyFlow;
import com.chenyi.safe.pojo.dto.InboundTraffic;
import com.chenyi.safe.pojo.dto.OutboundTraffic;
import com.chenyi.safe.pojo.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author：ChenYi
 * @Package：com.chenyi.safe.mapper
 * @Project：back
 * @name：MyFlowMapper
 * @Date：2024/6/24 下午3:47
 * @Filename：MyFlowMapper
 */
@Mapper
public interface MyFlowMapper extends BaseMapper<MyFlow> {

    /**
     * 协议出现次数
     */
    @Select("SELECT protocol, COUNT(*) AS count FROM my_flow GROUP BY protocol")
    List<ProtocolCount> getProtocolCounts();

    /**
     * 24h流量统计
     * @return
     */
    @Select("SELECT *, packet_length_mean AS packetLengthMean FROM  my_flow")
    List<RecentlyFlow> selectCustomFlows();

    /**
     * 不同类型攻击出现的次数
     */
    @Select("SELECT label, COUNT(*) AS count FROM my_flow WHERE label != 'BENIGN' GROUP BY label")
    List<LabelCount> getLabelCounts();

    /**
     * 统计各个IP出现次数
     */
    @Select("SELECT ip, COUNT(*) AS count " +
            "FROM ( " +
            "    SELECT dst_ip AS ip FROM my_flow " +
            "    UNION ALL " +
            "    SELECT src_ip AS ip FROM my_flow " +
            ") AS combined " +
            "GROUP BY ip " +
            "ORDER BY count DESC")
    List<IpCount> getIpCount();

    /**
     * 获取每种协议类型和对应的流量包百分比
     */
    @Select("SELECT protocol, (SUM(packet_length_mean) * 100.0 / (SELECT SUM(packet_length_mean) FROM my_flow)) AS packetLengthMean FROM my_flow GROUP BY protocol")
    List<ProtocolTrafficPercentage> getProtocolTrafficPercentage();

    /**
     * 获取每种协议类型和对应的数据包平均长度
     */
    @Select("SELECT protocol, AVG(packet_length_mean) AS packetLengthMean FROM my_flow GROUP BY protocol")
    List<ProtocolTraffic> getProtocolTraffic();

    /**
     * 时间与流量图
     * @return 时间与流量大小的关系
     */
    @Select("SELECT timestamp, packet_length_mean AS packetLengthMean FROM my_flow")
    List<TimeAndFlow> getTimeAndSize();

    /**
     * 查询源 IP 和目标 IP 出现次数最多的 IP
     * @return IP 和出现次数的映射
     */
    @Select("SELECT src_ip AS ip, COUNT(*) AS count FROM my_flow GROUP BY src_ip " +
            "UNION " +
            "SELECT dst_ip AS ip, COUNT(*) AS count FROM my_flow GROUP BY dst_ip " +
            "ORDER BY count DESC LIMIT 1")
    Map<String, Integer> findMostFrequentIP();


    /**
     * 获取源IP和目标IP，同时若有重复的IP就去掉，保证原子性
     * @return 不重复的IP列表
     */
    @Select("SELECT DISTINCT ip FROM (" +
            "SELECT src_ip AS ip FROM my_flow " +
            "UNION " +
            "SELECT dst_ip AS ip FROM my_flow" +
            ") AS distinct_ips")
    List<String> findDistinctIPs();

    /**
     * 统计每个时间段出现的次数
     * @return 时间段和对应的记录数
     */
    @Select("SELECT timestamp AS timePeriod, " +
            "       COUNT(*) AS count " +
            "FROM my_flow " +
            "GROUP BY timestamp")
    List<TimePeriodCount> getTimePeriodCounts();


}
