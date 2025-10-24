package com.chenyi.safe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chenyi.safe.mapper.FlowMapper;
import com.chenyi.safe.mapper.GeoIPMapper;
import com.chenyi.safe.pojo.Flow;
import com.chenyi.safe.pojo.dto.InboundTraffic;
import com.chenyi.safe.pojo.dto.IpRatioDTO;
import com.chenyi.safe.pojo.dto.OutboundTraffic;
import com.chenyi.safe.pojo.vo.*;
import com.chenyi.safe.service.TimeAndFlowService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author：ChenYi
 * @Package：com.chenyi.safe.service.impl
 * @Project：network-security-java-backend
 * @name：TimeAndFlowImpl
 * @Date：2024/6/14 下午9:42
 * @Filename：TimeAndFlowImpl
 */

@Service
public class TimeAndFlowImpl implements TimeAndFlowService {

    @Resource
    private FlowMapper flowMapper;

    @Resource
    private GeoIPMapper geoIPMapper;

    @Override
    public List<LabelCount> getLabelCounts() {
        return flowMapper.getLabelCounts();
    }

    @Override
    public List<ProtocolCount> getProtocolCounts() {
        return flowMapper.getProtocolCounts();
    }

    @Override
    public List<List<RecentlyFlow>> groupFlowsByDay(List<RecentlyFlow> flows) {
        Map<String, List<RecentlyFlow>> dayGroups = new LinkedHashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // 按天分组数据
        for (RecentlyFlow flow : flows) {
            try {
                Date date = dateFormat.parse(flow.getTimestamp());
                String dayKey = dateFormat.format(date);

                // 将数据添加到对应的天的分组中
                dayGroups.computeIfAbsent(dayKey, k -> new ArrayList<>()).add(flow);
            } catch (ParseException e) {
                // 如果有解析异常，记录日志并继续处理下一个数据
                System.err.println("Failed to parse timestamp: " + flow.getTimestamp());
                e.printStackTrace();
            }
        }

        // 将 dayGroups 中的值转换为列表返回
        return new ArrayList<>(dayGroups.values());
    }

    @Override
    public List<Flow> getByKeyFlows(String[] keyword) {
        for (String key : keyword) {
            QueryWrapper<Flow> query = Wrappers.query();
            query.and(w -> w.like("srcIp",key))
                    .or().like("dstIp",key)
                    .or().like("srcPort",key)
                    .or().like("dstPort",key)
                    .or().like("protocol",key);
            List<Flow> flows = flowMapper.selectList(query);
            return flows;

        }
        return null;
    }

    @Override
    public List<ProtocolTrafficPercentage> getProtocolTrafficPercentage() {
        return flowMapper.getProtocolTrafficPercentage();
    }

    @Override
    public List<ProtocolTraffic> getProtocolTraffic() {
        return flowMapper.getProtocolTraffic();
    }

    @Override
    public List<InboundTraffic> getInboundTraffic() {

        return flowMapper.getInboundTraffic();
    }

    @Override
    public List<OutboundTraffic> getOutboundTraffic() {

        return flowMapper.getOutboundTraffic();
    }

    @Override
    public IpRatioDTO getIpRatios() {

        Map<String, Integer> localIP = geoIPMapper.findMostFrequentIP();
        Double srcIpRatio = flowMapper.getSrcIpRatio(String.valueOf(localIP.get("ip")));
        Double dstIpRatio = flowMapper.getDstIpRatio(String.valueOf(localIP.get("ip")));
        IpRatioDTO ipRatioDTO = new IpRatioDTO();
        ipRatioDTO.setSrcIpRatio(srcIpRatio);
        ipRatioDTO.setDstIpRatio(dstIpRatio);
        return ipRatioDTO;
    }

    @Override
    public List<TimeAndFlow> getTimeAndSize() {

        return flowMapper.getTimeAndSize();
    }
}
