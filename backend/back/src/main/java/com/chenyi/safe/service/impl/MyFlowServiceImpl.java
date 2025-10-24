package com.chenyi.safe.service.impl;

import com.chenyi.safe.mapper.MyFlowMapper;
import com.chenyi.safe.pojo.MyFlow;
import com.chenyi.safe.pojo.vo.GeoIPInfo;
import com.chenyi.safe.pojo.vo.ProtocolCount;
import com.chenyi.safe.pojo.vo.RecentlyFlow;
import com.chenyi.safe.service.MyFlowService;
import com.chenyi.safe.service.RedisService;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author：ChenYi
 * @Package：com.chenyi.safe.service.impl
 * @Project：back
 * @name：MyFlowServicenImpl
 * @Date：2024/6/24 下午4:31
 * @Filename：MyFlowServiceImpl
 */

@Service
public class MyFlowServiceImpl implements MyFlowService {

    private static final Logger log = LoggerFactory.getLogger(MyFlowServiceImpl.class);

    @Resource
    private MyFlowMapper myFlowMapper;

    @Resource
    private RedisService redisService;

    @Override
    public List<GeoIPInfo> getAddress() {
        List<Object> csvFlow = redisService.getCSVFlow(); // Assuming this retrieves your flow data

        Set<String> distinctIPs = new HashSet<>();

        // Count occurrences of each IP
        Map<String, Integer> ipCountMap = new HashMap<>();

        if (!csvFlow.isEmpty() && csvFlow.get(0) instanceof List) {
            List<MyFlow> flowList = (List<MyFlow>) csvFlow.get(0);

            for (MyFlow flow : flowList) {
                String srcIp = flow.getSrcIp();
                String dstIp = flow.getDstIp();

                // Increment count for srcIp
                ipCountMap.put(srcIp, ipCountMap.getOrDefault(srcIp, 0) + 1);
                // Increment count for dstIp
                ipCountMap.put(dstIp, ipCountMap.getOrDefault(dstIp, 0) + 1);

                // Add IPs to distinctIPs set
                distinctIPs.add(srcIp);
                distinctIPs.add(dstIp);
            }
        }

        // Find the IP with the highest count
        String mostFrequentIP = Collections.max(ipCountMap.entrySet(), Map.Entry.comparingByValue()).getKey();

        List<GeoIPInfo> addresses = new ArrayList<>();

        // Initialize GeoIP database
        File database = new File("E:\\other\\code\\ids\\back\\src\\main\\java\\com\\chenyi\\safe\\data\\GeoIP\\GeoLite2-City.mmdb");

        try (DatabaseReader reader = new DatabaseReader.Builder(database).build()) {
            for (String ip : distinctIPs) {
                if (!ip.equals(mostFrequentIP)) {
                    try {
                        InetAddress ipAddress = InetAddress.getByName(ip);
                        CityResponse response = reader.city(ipAddress);

                        String cityName = response.getCountry().getName() + " " + response.getCity().getName();
                        double longitude = response.getLocation().getLongitude();
                        double latitude = response.getLocation().getLatitude();

                        GeoIPInfo geoIPInfo = new GeoIPInfo(ip, cityName, longitude, latitude);
                        addresses.add(geoIPInfo);
                        log.info("地址信息：{}", geoIPInfo);
                    } catch (AddressNotFoundException e) {
                        log.warn("IP地址 {} 不在数据库中。", ip);
                    } catch (GeoIp2Exception | IOException e) {
                        log.error("处理IP地址 {} 时发生错误：{}", ip, e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            log.error("初始化GeoIP数据库时发生错误：{}", e.getMessage());
        }

        return addresses;
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
    public List<ProtocolCount> getProtocolCounts() {

        return myFlowMapper.getProtocolCounts();
    }
}
