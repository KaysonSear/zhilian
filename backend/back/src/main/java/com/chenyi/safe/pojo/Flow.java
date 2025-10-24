package com.chenyi.safe.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 
 * @TableName flow
 */

@Schema(description = "流量信息")
@TableName(value ="flow")
@Data
public class Flow implements Serializable {
    /**
     * 流量id
     */
    @TableId
    private String flowId;

    /**
     * 源ip
     */
    private String srcIp;

    /**
     * 源端口
     */
    private Integer srcPort;

    /**
     * 目标ip
     */
    private String dstIp;

    /**
     * 目标端口
     */
    private Integer dstPort;

    /**
     * 协议
     */
    private Integer protocol;

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * 流量持续时间
     */
    private Integer flowDuration;

    /**
     * 正向总包数,即流出
     */
    private Integer totalFwdPacket;

    /**
     * 反向总包数
     */
    private Integer totalBwdPackets;

    /**
     * 正向数据包的总大小
     */
    private Double totalLengthOfFwdPacket;

    /**
     * 反向数据包的总大小
     */
    private Double totalLengthOfBwdPacket;

    /**
     * 前向数据包的最大长度
     */
    private Double fwdPacketLengthMax;

    /**
     * 前向数据包的最小长度
     */
    private Double fwdPacketLengthMin;

    /**
     * 前向数据包的平均长度
     */
    private Double fwdPacketLengthMean;

    /**
     * 前向数据包长度的标准偏差
     */
    private Double fwdPacketLengthStd;

    /**
     * 反向数据包的最大长度
     */
    private Double bwdPacketLengthMax;

    /**
     * 反向数据包的最大长度
     */
    private Double bwdPacketLengthMin;

    /**
     * 反向数据包的平均长度
     */
    private Double bwdPacketLengthMean;

    /**
     * 反向数据包长度的标准偏差
     */
    private Double bwdPacketLengthStd;

    /**
     * 流字节率，即每秒传输的数据包数
     */
    private Double flowBytesPerS;

    /**
     * 流包速率，即每秒传输的包数
     */
    private Double flowPacketsPerS;

    /**
     * 某个数据流的平均流间到达时间
     */
    private Double flowIatMean;

    /**
     * 某个数据流的流间到达时间（Flow Inter-Arrival Time）的标准偏差
     */
    private Double flowIatStd;

    /**
     * 最大流间到达时间（
     */
    private Double flowIatMax;

    /**
     * 表示某个数据流的最小流间到达时间
     */
    private Double flowIatMin;

    /**
     * 前向数据流的总流间到达时间
     */
    private Double fwdIatTotal;

    /**
     * 前向数据流的平均流间到达时间
     */
    private Double fwdIatMean;

    /**
     * 前向数据流的流间到达时间标准偏差
     */
    private Double fwdIatStd;

    /**
     * 前向数据流的最大流间到达时间
     */
    private Double fwdIatMax;

    /**
     * 前向数据流的最小流间到达时间
     */
    private Double fwdIatMin;

    /**
     * 反向数据流的总流间到达时间
     */
    private Double bwdIatTotal;

    /**
     * 反向数据流的平均流间到达时间
     */
    private Double bwdIatMean;

    /**
     * 反向数据流的流间到达时间标准偏差
     */
    private Double bwdIatStd;

    /**
     * 反向数据流的最大流间到达时间
     */
    private Double bwdIatMax;

    /**
     * 反向数据流的最小流间到达时间
     */
    private Double bwdIatMin;

    /**
     * 前向数据流的PSH标志计数
     */
    private Integer fwdPshFlags;

    /**
     * 反向数据流的PSH标志计数
     */
    private Integer bwdPshFlags;

    /**
     * 前向数据流的URG标志计数
     */
    private Integer fwdUrgFlags;

    /**
     * 反向数据流的URG标志计数
     */
    private Integer bwdUrgFlags;

    /**
     * 前向数据流的头部长度
     */
    private Integer fwdHeaderLength;

    /**
     * 反向数据流的头部长度
     */
    private Integer bwdHeaderLength;

    /**
     * 前向数据包的速率
     */
    private Double fwdPacketsPerS;

    /**
     * 反向数据包的速率
     */
    private Double bwdPacketsPerS;

    /**
     * 数据包长度的最小值
     */
    private Double packetLengthMin;

    /**
     * 数据包长度的最大值
     */
    private Double packetLengthMax;

    /**
     * 数据包长度的平均值
     */
    private Double packetLengthMean;

    /**
     * 数据包长度的标准偏差
     */
    private Double packetLengthStd;

    /**
     * 数据包长度的方差
     */
    private Double packetLengthVariance;

    /**
     * FIN标志计数
     */
    private Integer finFlagCount;

    /**
     * SYN标志计数
     */
    private Integer synFlagCount;

    /**
     * RST标志计数
     */
    private Integer rstFlagCount;

    /**
     * PSH标志计数
     */
    private Integer pshFlagCount;

    /**
     * ACK标志计数
     */
    private Integer ackFlagCount;

    /**
     * URG标志计数
     */
    private Integer urgFlagCount;

    /**
     * CWR标志计数
     */
    private Integer cwrFlagCount;

    /**
     * ECE标志计数
     */
    private Integer eceFlagCount;

    /**
     * 下行与上行流量的比率
     */
    private Double downUpRatio;

    /**
     * 平均数据包大小
     */
    private Double averagePacketSize;

    /**
     * 前向数据流的段大小平均值
     */
    private Double fwdSegmentSizeAvg;

    /**
     * 反向数据流的段大小平均值
     */
    private Double bwdSegmentSizeAvg;

    /**
     * 前向数据流的字节批量平均值
     */
    private Integer fwdBytesBulkAvg;

    /**
     * 前向数据流的数据包批量平均值
     */
    private Integer fwdPacketBulkAvg;

    /**
     * 前向数据流的批量速率平均值
     */
    private Integer fwdBulkRateAvg;

    /**
     * 反向数据流的字节批量平均值
     */
    private Integer bwdBytesBulkAvg;

    /**
     * 反向数据流的数据包批量平均值
     */
    private Integer bwdPacketBulkAvg;

    /**
     * 反向数据流的批量速率平均值
     */
    private Integer bwdBulkRateAvg;

    /**
     * 子流前向数据包数量
     */
    private Integer subflowFwdPackets;

    /**
     * 子流前向数据字节数
     */
    private Integer subflowFwdBytes;

    /**
     * 子流反向数据包数量
     */
    private Integer subflowBwdPackets;

    /**
     * 子流反向数据字节数
     */
    private Integer subflowBwdBytes;

    /**
     * 前向初始化窗口字节数
     */
    private Integer fwdInitWinBytes;

    /**
     * 反向初始化窗口字节数
     */
    private Integer bwdInitWinBytes;

    /**
     * 前向活动数据包数量
     */
    private Integer fwdActDataPkts;

    /**
     * 前向数据流的最小段大小
     */
    private Integer fwdSegSizeMin;

    /**
     * 活动时间的平均值
     */
    private Integer activeMean;

    /**
     * 活动时间的标准偏差
     */
    private Integer activeStd;

    /**
     * 活动时间的最大值
     */
    private Integer activeMax;

    /**
     * 活动时间的最小值
     */
    private Integer activeMin;

    /**
     * 空闲时间的平均值
     */
    private Integer idleMean;

    /**
     * 空闲时间的标准偏差
     */
    private Integer idleStd;

    /**
     * 空闲时间的最大值
     */
    private Integer idleMax;

    /**
     * 空闲时间的最小值
     */
    private Integer idleMin;

    /**
     * 数据流的标签
     */
    private String label;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}