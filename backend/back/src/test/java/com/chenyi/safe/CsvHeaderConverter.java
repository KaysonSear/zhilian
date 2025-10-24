package com.chenyi.safe;

/**
 * back
 * 2024/6/21 下午2:09
 * CSV转mysql
 *
 * @author chenyi
 * @since
 **/
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class CsvHeaderConverter {

    public static void main(String[] args) {
        String inputFilePath = "E:\\other\\流量数据\\unnormal_flow.csv";
        String outputFilePath = "E:\\other\\流量数据\\new_flow.csv";
        convertCsvHeadersToSnakeCase(inputFilePath, outputFilePath);
    }

    public static void convertCsvHeadersToSnakeCase(String inputFilePath, String outputFilePath) {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(inputFilePath));
             BufferedWriter bw = Files.newBufferedWriter(Paths.get(outputFilePath))) {

            String headerLine = br.readLine();
            if (headerLine != null) {
                String[] headers = headerLine.split(",");
                String[] newHeaders = Arrays.stream(headers)
                        .map(CsvHeaderConverter::toSnakeCase)
                        .toArray(String[]::new);

                bw.write(String.join(",", newHeaders));
                bw.newLine();

                String line;
                while ((line = br.readLine()) != null) {
                    bw.write(line);
                    bw.newLine();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String toSnakeCase(String input) {
        String result = input.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
        return result.replaceAll("[^a-zA-Z0-9]", "_").replaceAll("_+", "_");
    }
}
