package com.chenyi.safe.service;

import com.chenyi.safe.pojo.Flow;
import com.chenyi.safe.pojo.dto.InboundTraffic;
import com.chenyi.safe.pojo.dto.IpRatioDTO;
import com.chenyi.safe.pojo.dto.OutboundTraffic;
import com.chenyi.safe.pojo.vo.*;

import java.util.List;

/**
 * @Author：ChenYi
 * @Package：com.chenyi.safe.service
 * @Project：network-security-java-backend
 * @name：TimeAndFlow
 * @Date：2024/6/14 下午9:41
 * @Filename：TimeAndFlow
 */
public interface TimeAndFlowService {

    List<com.chenyi.safe.pojo.vo.TimeAndFlow> getTimeAndSize();

    IpRatioDTO getIpRatios();

    List<OutboundTraffic> getOutboundTraffic();

    List<InboundTraffic> getInboundTraffic();

    List<ProtocolTraffic> getProtocolTraffic();

    List<ProtocolTrafficPercentage> getProtocolTrafficPercentage();

    List<Flow> getByKeyFlows(String[] keyword);

    List<LabelCount> getLabelCounts();

    List<List<RecentlyFlow>> groupFlowsByDay(List<RecentlyFlow> flows);

    List<ProtocolCount> getProtocolCounts();
}
