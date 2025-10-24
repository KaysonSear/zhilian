package com.chenyi.safe.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chenyi.safe.common.Result;
import com.chenyi.safe.mapper.AttackTypeMapper;
import com.chenyi.safe.mapper.MyFlowMapper;
import com.chenyi.safe.pojo.MyFlow;
import com.chenyi.safe.pojo.SystemInfo;
import com.chenyi.safe.pojo.dto.InboundTraffic;
import com.chenyi.safe.pojo.vo.*;
import com.chenyi.safe.service.MyFlowService;
import com.chenyi.safe.service.RedisService;
import com.chenyi.safe.utils.RedisUtils;
import com.chenyi.safe.utils.SystemEvaluator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：ChenYi
 * @Package：com.chenyi.safe.controller
 * @Project：back
 * @name：MyFlowController
 * @Date：2024/6/24 下午4:25
 * @Filename：MyFlowController
 */

@Tag(name = "大一统")
@CrossOrigin
@RestController
@RequestMapping("/myFlow")
public class MyFlowController {

    private static final Logger log = LoggerFactory.getLogger(MyFlowController.class);

    @Autowired
    private MyFlowService myFlowService;

    @Autowired
    private MyFlowMapper myFlowMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RedisService redisService;

    @Autowired
    private SystemEvaluator systemEvaluator;

    @Autowired
    private AttackTypeMapper attackTypeMapper;

    /******************************* 流量数据分析 **********************************************/

    @Operation(summary = "协议出现次数")
    @GetMapping("/getProtocolCounts")
    public Result<?> getProtocolCounts() {
        List<Object> csvFlow = redisService.getCSVFlow();
        List<Map<String, String>> result = new ArrayList<>();
        // 使用 Map 存储 protocol 和 count
        Map<Integer, Integer> protocolCounts = new HashMap<>();
        // 遍历获取到的数据列表
        if (!csvFlow.isEmpty() && csvFlow.get(0) instanceof List) {
            List<MyFlow> firstFlowList = (List<MyFlow>) csvFlow.get(0);
            for (MyFlow myFlow : firstFlowList) {
                int protocol = myFlow.getProtocol();
                // 如果 Map 中已经包含该 protocol，则数量加一；否则初始化为 1
                protocolCounts.put(protocol, protocolCounts.getOrDefault(protocol, 0) + 1);
            }
            // 构造结果列表
            for (Map.Entry<Integer, Integer> entry : protocolCounts.entrySet()) {
                Map<String, String> protocolCountMap = new HashMap<>();
                protocolCountMap.put("protocol", String.valueOf(entry.getKey()));
                protocolCountMap.put("count", String.valueOf(entry.getValue()));
                result.add(protocolCountMap);
            }
            // 日志输出
            for (Map<String, String> protocolCountMap : result) {
                log.info("Protocol: {}, Count: {}", protocolCountMap.get("protocol"), protocolCountMap.get("count"));
            }
        }
        return Result.success(result); // myFlowService.getProtocolCounts()
    }

    @Operation(summary = "24h内流量数据")
    @GetMapping("/fetchDataByDayGroups")
    public Result<?> fetchDataByDayGroups() {
        List<Object> csvFlow = redisService.getCSVFlow();
        List<TimePeriodCount> result = new ArrayList<>();

        if (!csvFlow.isEmpty() && csvFlow.get(0) instanceof List) {
            List<MyFlow> firstFlowList = (List<MyFlow>) csvFlow.get(0);

            // 使用 Map 存储 timestamp 和 count
            Map<String, Integer> timePeriodCounts = new HashMap<>();

            // 遍历获取到的 MyFlow 列表
            for (MyFlow myFlow : firstFlowList) {
                String timestamp = myFlow.getTimestamp(); // 假设获取 timestamp 字段的方法是 getTimestamp()

                // 如果 Map 中已经包含该 timestamp，则数量加一；否则初始化为 1
                timePeriodCounts.put(timestamp, timePeriodCounts.getOrDefault(timestamp, 0) + 1);
            }

            // 构造结果列表
            for (Map.Entry<String, Integer> entry : timePeriodCounts.entrySet()) {
                TimePeriodCount timePeriodCount = new TimePeriodCount();
                timePeriodCount.setTimePeriod(entry.getKey());
                timePeriodCount.setCount(entry.getValue());
                result.add(timePeriodCount);
            }
        }

        // 打印输出结果
        for (TimePeriodCount timePeriodCount : result) {
            log.info("Time Period: {}, Count: {}", timePeriodCount.getTimePeriod(), timePeriodCount.getCount());
        }

        return Result.success(result); // myFlowMapper.getTimePeriodCounts()
    }

    @Operation(summary = "统计各个攻击类型出现次数")
    @GetMapping("/getLabelCounts")
    public Result<?> getLabelCounts() {
        List<Object> csvFlow = redisService.getCSVFlow();

        // 如果需要返回的是类似 SQL 查询结果的对象列表，可以定义一个 LabelCount 类
        List<LabelCount> labelCounts = new ArrayList<>();

        // 使用 Map 存储 label 和 count
        Map<String, Long> labelCountsMap = new HashMap<>();

        // 遍历获取到的数据列表
        if (!csvFlow.isEmpty() && csvFlow.get(0) instanceof List) {
            List<MyFlow> firstFlowList = (List<MyFlow>) csvFlow.get(0);

            for (MyFlow myFlow : firstFlowList) {
                if (!"BENIGN".equals(myFlow.getLabel())) {
                    String label = myFlow.getLabel();
                    // 如果 Map 中已经包含该 label，则数量加一；否则初始化为 1
                    labelCountsMap.put(label, labelCountsMap.getOrDefault(label, Long.valueOf(0)) + 1);
                }
            }

            // 将 Map 转换为 LabelCount 对象列表（如果使用 LabelCount 类）
            for (Map.Entry<String, Long> entry : labelCountsMap.entrySet()) {
                LabelCount labelCount = new LabelCount(entry.getKey(), entry.getValue());
                labelCounts.add(labelCount);
            }
        }

        for (LabelCount labelCount : labelCounts) {
            log.info("Label: {}, Count: {}", labelCount.getLabel(), labelCount.getCount());
        }

        return Result.success(labelCounts); // myFlowMapper.getLabelCounts()
    }

    @Operation(summary = "统计各个IP出现个数")
    @GetMapping("/getIPCount")
    public Result<?> getIPCount() {
        List<Object> csvFlow = redisService.getCSVFlow();

        List<MyFlow> firstFlowList = new ArrayList<>();
        if (!csvFlow.isEmpty() && csvFlow.get(0) instanceof List) {
            firstFlowList = (List<MyFlow>) csvFlow.get(0);
        }

        // 统计 IP 地址出现次数
        Map<String, Long> ipCounts = new HashMap<>();
        for (MyFlow myFlow : firstFlowList) {
            String dstIp = myFlow.getDstIp();
            String srcIp = myFlow.getSrcIp();

            // 统计 dstIp
            if (dstIp != null) {
                ipCounts.put(dstIp, ipCounts.getOrDefault(dstIp, Long.valueOf(0)) + 1);
            }

            // 统计 srcIp
            if (srcIp != null) {
                ipCounts.put(srcIp, ipCounts.getOrDefault(srcIp, Long.valueOf(0)) + 1);
            }
        }

        // 构造 IpCount 对象列表，并排序
        List<IpCount> result = new ArrayList<>();
        for (Map.Entry<String, Long> entry : ipCounts.entrySet()) {
            result.add(new IpCount(entry.getKey(), entry.getValue()));
        }
        // 按照 count 排序（降序）
        result.sort((o1, o2) -> Math.toIntExact(o2.getCount() - o1.getCount()));

        // 打印输出结果
        for (IpCount ipCount : result) {
            log.info("IP: {}, Count: {}", ipCount.getIp(), ipCount.getCount());
        }

        return Result.success(result);
    }

//    @Operation(summary = "协议与流量占比")
//    @GetMapping("/getProtocolTrafficPercentage")
//    public Result<?> getProtocolTrafficPercentage() {
//        List<ProtocolTrafficPercentage> protocolTrafficPercentage = myFlowMapper.getProtocolTrafficPercentage();
//        return Result.success(protocolTrafficPercentage);
//    }

    @Operation(summary = "协议与流量")
    @GetMapping("/getProtocolTraffic")
    public Result<?> getProtocolTraffic() {
        List<Object> csvFlow = redisService.getCSVFlow();
        List<Map<String, Object>> protocolTrafficList = new ArrayList<>();

        if (!csvFlow.isEmpty() && csvFlow.get(0) instanceof List) {
            List<MyFlow> firstFlowList = (List<MyFlow>) csvFlow.get(0);

            Map<Integer, Double> sumLengthMap = new HashMap<>();
            Map<Integer, Integer> countMap = new HashMap<>();

            for (MyFlow myFlow : firstFlowList) {
                int protocol = myFlow.getProtocol();
                double packetLengthMean = myFlow.getPacketLengthMean();

                if (!sumLengthMap.containsKey(protocol)) {
                    sumLengthMap.put(protocol, 0.0);
                    countMap.put(protocol, 0);
                }

                sumLengthMap.put(protocol, sumLengthMap.get(protocol) + packetLengthMean);
                countMap.put(protocol, countMap.get(protocol) + 1);
            }

            for (Map.Entry<Integer, Double> entry : sumLengthMap.entrySet()) {
                int protocol = entry.getKey();
                double sumLength = entry.getValue();
                int count = countMap.get(protocol);
                double avgPacketLengthMean = sumLength / count;

                Map<String, Object> protocolTrafficMap = new HashMap<>();
                protocolTrafficMap.put("protocol", protocol);
                protocolTrafficMap.put("packetLengthMean", avgPacketLengthMean);
                protocolTrafficList.add(protocolTrafficMap);
            }
        }

        for (Map<String, Object> map : protocolTrafficList) {
            log.info("Protocol: {}, Avg Packet Length Mean: {}", map.get("protocol"), map.get("packetLengthMean"));
        }

        return Result.success(protocolTrafficList);
    }

    @Operation(summary = "获取时间与流量大小的关系")
    @GetMapping("/getTimeAndFlow")
    public Result<?> getTimeAndFlow() {
        List<Object> csvFlow = redisService.getCSVFlow();

        List<TimeAndFlow> timeAndFlowList = new ArrayList<>();

        // 遍历获取到的数据列表
        if (!csvFlow.isEmpty() && csvFlow.get(0) instanceof List) {
            List<MyFlow> firstFlowList = (List<MyFlow>) csvFlow.get(0);

            for (MyFlow myFlow : firstFlowList) {
                // 提取 timestamp 和 packetLengthMean 属性
                String timestamp = myFlow.getTimestamp();
                double packetLengthMean = myFlow.getPacketLengthMean();

                // 创建 TimeAndFlow 对象并添加到列表中
                TimeAndFlow timeAndFlow = new TimeAndFlow(timestamp, packetLengthMean);
                timeAndFlowList.add(timeAndFlow);
            }

            // 打印输出结果（可选）
            for (TimeAndFlow timeAndFlow : timeAndFlowList) {
                log.info(timeAndFlow.toString());
            }
        }

        // 返回处理后的结果列表
        return Result.success(timeAndFlowList);
    }

    @Operation(summary = "查看全部流量记录")
    @GetMapping("/getAllFlow")
    public Result<?> getAllFlow() {
        List<Object> csvFlow = redisService.getCSVFlow();

        // 遍历获取到的数据列表
        List<MyFlow> firstFlowList = null;
        if (!csvFlow.isEmpty() && csvFlow.get(0) instanceof List) {
            firstFlowList = (List<MyFlow>) csvFlow.get(0);

        }

        return Result.success(firstFlowList);
    }


    /********************************* 系统数据分析 ******************************************/

    @Operation(summary = "获取CPU信息")
    @GetMapping("/getCpu")
    public Result<?> getCpu() {
        return Result.success(redisUtils.hmget("cpuInfo"));
    }

    @Operation(summary = "获取Memory内存信息")
    @GetMapping("/getMemory")
    public Result<?> getMemory() {
        return Result.success(redisUtils.hmget("memoryInfo"));
    }

    @Operation(summary = "获取Disk磁盘信息")
    @GetMapping("/getDisk")
    public Result<?> getDisk() {
        return Result.success(redisUtils.hmget("diskInfo"));
    }

    @Operation(summary = "获取Network网络信息")
    @GetMapping("/getNet")
    public Result<?> getNet() {
        return Result.success(redisUtils.hmget("networkInfo"));
    }

    @Operation(summary = "评估系统状态")
    @GetMapping("/evaluate")
    public Result<?> evaluateSystem() {
        SystemInfo.CPUInfo cpuInfo = getCpuInfoFromResult(getCpu());
        SystemInfo.MemoryInfo memoryInfo = getMemoryInfoFromResult(getMemory());
        SystemInfo.DiskInfo diskInfo = getDiskInfoFromResult(getDisk());
        SystemInfo.NetworkInfo networkInfo = getNetworkInfoFromResult(getNet());

        SystemInfo systemInfo = new SystemInfo();
        systemInfo.setCpu(cpuInfo);
        systemInfo.setMemory(memoryInfo);
        systemInfo.setDisk(diskInfo);
        systemInfo.setNetwork(networkInfo);

        int totalScore = systemEvaluator.evaluateSystem(systemInfo);
        return Result.success("System score: " + totalScore + "/20");
    }

    private SystemInfo.CPUInfo getCpuInfoFromResult(Result<?> result) {
        Map<String, Object> data = (Map<String, Object>) result.getData();
        SystemInfo.CPUInfo cpuInfo = new SystemInfo.CPUInfo();
        cpuInfo.setUser((double) data.get("user"));
        cpuInfo.setSystem((double) data.get("system"));
        cpuInfo.setIdle((double) data.get("idle"));
        cpuInfo.setPercent((double) data.get("percent"));
        return cpuInfo;
    }

    private SystemInfo.MemoryInfo getMemoryInfoFromResult(Result<?> result) {
        Map<String, Object> data = (Map<String, Object>) result.getData();
        SystemInfo.MemoryInfo memoryInfo = new SystemInfo.MemoryInfo();
        memoryInfo.setTotal((Long) data.get("total"));
        memoryInfo.setUsed((Long) Long.valueOf(String.valueOf(data.get("used"))));
        memoryInfo.setFree((Long) data.get("free"));
        memoryInfo.setPercent((double) data.get("percent"));
        return memoryInfo;
    }

    private SystemInfo.DiskInfo getDiskInfoFromResult(Result<?> result) {
        Map<String, Object> data = (Map<String, Object>) result.getData();
        SystemInfo.DiskInfo diskInfo = new SystemInfo.DiskInfo();
        diskInfo.setTotal((long) data.get("total"));
        diskInfo.setUsed((long) data.get("used"));
        diskInfo.setFree((long) data.get("free"));
        diskInfo.setPercent((double) data.get("percent"));
        return diskInfo;
    }

    private SystemInfo.NetworkInfo getNetworkInfoFromResult(Result<?> result) {
        Map<String, Object> data = (Map<String, Object>) result.getData();
        SystemInfo.NetworkInfo networkInfo = new SystemInfo.NetworkInfo();
        networkInfo.setBytesSent((Long) Long.valueOf(String.valueOf(data.get("bytes_sent"))));
        networkInfo.setBytesRecv((Long)Long.valueOf(String.valueOf(data.get("bytes_recv"))));
        networkInfo.setPacketsSent((Long) Long.valueOf(String.valueOf(data.get("packets_sent"))));
        networkInfo.setPacketsRecv((Long) Long.valueOf(String.valueOf(data.get("packets_recv"))));
        return networkInfo;
    }


    /********************************* 地理位置数据分析 ******************************************/

    @Operation(summary = "获取地址")
    @GetMapping("/getAddress")
    public Result<?> getAddress() {
        return Result.success(myFlowService.getAddress());
    }



    /********************************* 安全报告分析 ******************************************/

    @Operation(summary = "获取指定安全报告")
    @GetMapping("/getAttackType")
    public Result<?> getAttackType(@RequestParam @NotBlank(message = "label参数不能为空") String label) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("label",label);
        return Result.success(attackTypeMapper.selectList(queryWrapper));
    }

}
