package com.chenyi.safe;

/**
 * safe
 * 2024/6/15 上午1:20
 * 测试
 *
 * @author chenyi
 * @since
 **/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class test {

//    private static final String DB_URL = "jdbc:mysql://localhost:3306/safe"; // 替换为你的数据库URL
//    private static final String DB_USERNAME = "root"; // 替换为你的数据库用户名
//    private static final String DB_PASSWORD = "123456"; // 替换为你的数据库密码
//    private static final String FILE_PATH = "E:\\WARN.txt"; // 替换为你的文件路径
//
//    public static void main(String[] args) {
//        try {
//            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
//            readAndInsertData(connection, FILE_PATH);
//            connection.close();
//        } catch (SQLException | IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void readAndInsertData(Connection connection, String filePath) throws IOException, SQLException {
//        String sql = "INSERT INTO rule_ids (pattern, description) VALUES (?, ?)";
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] parts = line.split(" : ");
//                if (parts.length == 2) {
//                    String pattern = parts[0].trim();
//                    String description = parts[1].trim();
//                    insertData(preparedStatement, pattern, description);
//                }
//            }
//        }
//    }
//
//    private static void insertData(PreparedStatement preparedStatement, String pattern, String description) throws SQLException {
//        preparedStatement.setString(1, pattern);
//        preparedStatement.setString(2, description);
//        preparedStatement.executeUpdate();
//        UUID uuid = UUID.randomUUID();
//        System.out.println(uuid);

//    }
public static void main(String[] args) {
    String dateTimeStr1 = "13/06/2024 09:12";
    String dateTimeStr2 = "5/7/2017 10:43";

    // 定义日期时间格式化器
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");

    // 解析日期时间字符串为 LocalDateTime 对象
    LocalDateTime dateTime1 = LocalDateTime.parse(dateTimeStr1, formatter);
    LocalDateTime dateTime2 = LocalDateTime.parse(dateTimeStr2, formatter);

    // 比较日期时间先后顺序
    if (dateTime1.isBefore(dateTime2)) {
        System.out.println(dateTimeStr1 + " 在 " + dateTimeStr2 + " 之前");
    } else if (dateTime1.isAfter(dateTime2)) {
        System.out.println(dateTimeStr1 + " 在 " + dateTimeStr2 + " 之后");
    } else {
        System.out.println(dateTimeStr1 + " 和 " + dateTimeStr2 + " 相同");
    }
}
}
