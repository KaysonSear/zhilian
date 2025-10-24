package com.chenyi.safe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenyi.safe.pojo.Flow;
import com.chenyi.safe.pojo.dto.InboundTraffic;
import com.chenyi.safe.pojo.dto.OutboundTraffic;
import com.chenyi.safe.pojo.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author：ChenYi
 * @Package：com.chenyi.safe.mapper
 * @Project：network-security-java-backend
 * @name：FlowMapper
 * @Date：2024/6/14 下午9:33
 * @Filename：FlowMapper
 */

@Mapper
public interface FlowMapper extends BaseMapper<Flow> {

    /**
     * 时间与流量图
     * @return 时间与流量大小的关系
     */
    @Select("SELECT timestamp, packet_length_mean AS packetLengthMean FROM flow")
    List<TimeAndFlow> getTimeAndSize();

    /**
     * 计算本机IP在源IP中的占比
     */
    @Select("SELECT (COUNT(*) * 1.0 / (SELECT COUNT(*) FROM flow)) AS srcIpRatio FROM flow WHERE src_ip = #{localIP}")
    Double getSrcIpRatio(@Param("localIP") String localIP);

    /**
     * 计算本机IP在目标IP中的占比
     */
    @Select("SELECT (COUNT(*) * 1.0 / (SELECT COUNT(*) FROM flow)) AS dstIpRatio FROM flow WHERE dst_ip = #{localIP}")
    Double getDstIpRatio(@Param("localIP") String localIP);

    /**
     * 获取全部流出IP和流出流量大小
     */
    @Select("SELECT src_ip AS srcIp, SUM(total_length_of_fwd_packet) AS totalLengthOfFwdPacket FROM flow GROUP BY src_ip")
    List<OutboundTraffic> getOutboundTraffic();

    /**
     * 获取全部流出IP和流出流量大小
     */
    @Select("SELECT dst_ip  AS dstIp, SUM(total_length_of_bwd_packet) AS totalLengthOfBwdPacket FROM flow GROUP BY dst_ip")
    List<InboundTraffic> getInboundTraffic();

    /**
     * 获取每种协议类型和对应的数据包平均长度
     */
    @Select("SELECT protocol, AVG(packet_length_mean) AS packetLengthMean FROM flow GROUP BY protocol")
    List<ProtocolTraffic> getProtocolTraffic();

    /**
     * 获取每种协议类型和对应的流量包百分比
     */
    @Select("SELECT protocol, (SUM(packet_length_mean) * 100.0 / (SELECT SUM(packet_length_mean) FROM flow)) AS packetLengthMean FROM flow GROUP BY protocol")
    List<ProtocolTrafficPercentage> getProtocolTrafficPercentage();

    /**
     * 统计各个IP出现次数
     */
    @Select("SELECT ip, COUNT(*) AS count " +
            "FROM ( " +
            "    SELECT dst_ip AS ip FROM flow " +
            "    UNION ALL " +
            "    SELECT src_ip AS ip FROM flow " +
            ") AS combined " +
            "GROUP BY ip " +
            "ORDER BY count DESC")
    List<IpCount> getIpCount();


    /**
     * 不同类型攻击出现的次数
     */
    @Select("SELECT label, COUNT(*) AS count FROM flow WHERE label != '0' GROUP BY label")
    List<LabelCount> getLabelCounts();

    /**
     * 24h流量统计
     * @return
     */
    @Select("SELECT timestamp, label, packet_length_mean AS packetLengthMean FROM flow")
    List<RecentlyFlow> selectCustomFlows();

    /**
     * 协议出现次数
     */
    @Select("SELECT protocol, COUNT(*) AS count FROM flow GROUP BY protocol")
    List<ProtocolCount> getProtocolCounts();

}
