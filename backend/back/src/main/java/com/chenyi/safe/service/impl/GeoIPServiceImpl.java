package com.chenyi.safe.service.impl;

import com.chenyi.safe.mapper.GeoIPMapper;
import com.chenyi.safe.pojo.MyFlow;
import com.chenyi.safe.pojo.vo.GeoIPInfo;
import com.chenyi.safe.service.GeoIPService;
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
import java.util.*;

/**
 * safe
 * 2024/6/14 上午10:50
 * 实现地址相关接口
 *
 * @author chenyi
 * @since
 **/

@Service
public class GeoIPServiceImpl implements GeoIPService {

    private static final Logger log = LoggerFactory.getLogger(GeoIPServiceImpl.class);
    @Resource
    private GeoIPMapper geoIPMapper;
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
    public Map<String, Integer> findMostFrequentIP() {
        Map<String, Integer> mostFrequentIP = geoIPMapper.findMostFrequentIP();
        log.info("本机IP:{}", mostFrequentIP);
        return mostFrequentIP;
    }
}
