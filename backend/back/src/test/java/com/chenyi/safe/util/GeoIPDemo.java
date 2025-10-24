package com.chenyi.safe.util;

/**
 * safe
 * 2024/6/13 下午10:37
 * 测试geoip的功能
 *
 * @author chenyi
 * @since
 **/
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

public class GeoIPDemo {
    public static void main(String[] args) {
        try {
            // 初始化GeoIP数据库
            File database = new File("D:\\code\\Software Cup\\network-security-java-backend\\src\\main\\java\\com\\chenyi\\safe\\data\\GeoIP\\GeoLite2-City.mmdb");
            DatabaseReader reader = new DatabaseReader.Builder(database).build();

            // 查询IP地址的地理位置信息
            InetAddress ipAddress = InetAddress.getByName("120.46.216.189"); // 替换为要查询的IP地址
            CityResponse response = reader.city(ipAddress);

            // 提取地理位置信息
            String cityName = response.getCountry().getName() + response.getCity().getName();
            double longitude = response.getLocation().getLongitude();
            double latitude = response.getLocation().getLatitude();

            // 输出地理位置信息
            System.out.println("City: " + cityName);
            System.out.println("Longitude: " + longitude);
            System.out.println("Latitude: " + latitude);
        } catch (IOException | GeoIp2Exception e) {
            e.printStackTrace();
        }
    }
}
