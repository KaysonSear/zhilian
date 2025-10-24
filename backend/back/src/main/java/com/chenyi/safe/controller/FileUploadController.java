package com.chenyi.safe.controller;

import com.chenyi.safe.common.Result;
import com.chenyi.safe.mapper.MyFlowMapper;
import com.chenyi.safe.pojo.CSV;
import com.chenyi.safe.pojo.MyFlow;
import com.chenyi.safe.pojo.dto.CSVResult;
import com.chenyi.safe.service.RedisService;
import com.chenyi.safe.utils.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.chenyi.safe.utils.JsonUtils.MAPPER;

/**
 * 上传CSV文件分析
 */
@Tag(name = "CSV文件")
@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileUploadController {

    private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);

    @Value("${upload.path}")
    private String uploadPath;

    @Resource
    private MyFlowMapper myFlowMapper;

    @Resource
    private RedisService redisService;

    /**
     * 上传CSV文件并调用Python接口处理
     */
    @Operation(summary = "上传流量文件")
    @PostMapping("/upload")
    public Result<?> handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("type") String type) {
        if (file.isEmpty()) {
            return Result.error("404", "请重新选择文件");
        }

        try {
            // 保存文件到指定路径
            String fileName = file.getOriginalFilename();
            String filePath = uploadPath + File.separator + fileName;
            File dest = new File(filePath);
            file.transferTo(dest);
            if (type.equals("1")) {
                List<Object> objects = sendFileToPython(dest);
                return Result.success(objects);
            }
            if (type.equals("0")) {
                List<Object> objects = sendPcapToPython(dest);
                return Result.success(objects);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return Result.error("500", "上传失败");
        }
        return Result.success("暂无");
    }

    @Operation(summary = "获取最近一次上传的分析结果")
    @GetMapping("/getCSV")
    public Result<?> getCSV() {
        CSV csvCount = redisService.getCSVCount();
        List<Object> csvFlow = redisService.getCSVFlow();
        CSVResult csvResult = new CSVResult();
        csvResult.setCsvFlow(csvFlow);
        csvResult.setCsvCount(csvCount);
        return Result.success(csvResult);
    }

    /**
     * 发送文件到Python接口
     */
    private List<Object> sendFileToPython(File file) throws IOException {
        String pythonEndpoint = "http://192.168.31.210:5000/ipPredict"; // 替换成实际的Python接口地址

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(pythonEndpoint);

        // 构建文件实体
        HttpEntity entity = MultipartEntityBuilder.create()
                .addBinaryBody("file", file, ContentType.MULTIPART_FORM_DATA, file.getName())
                .build();

        httpPost.setEntity(entity);

        // 发送请求并处理响应
        CloseableHttpResponse response = client.execute(httpPost);
        try {
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String responseString = EntityUtils.toString(responseEntity);
                System.out.println(responseString);
                JsonNode jsonNode = JsonUtils.stringToJsonNode(responseString);
                CSV predictionCounts = MAPPER.treeToValue(jsonNode.get("prediction_counts"), CSV.class);
                // 将 predictions 映射为实体类列表
                List<MyFlow> predictions = MAPPER.readValue(
                        jsonNode.get("predictions").traverse(), // 使用 traverse 方法处理数组
                        MAPPER.getTypeFactory().constructCollectionType(List.class, MyFlow.class)
                );
                log.info("存储数据库>>>");
                for (MyFlow prediction : predictions) {
                    myFlowMapper.insert(prediction);
                }
                log.info("存储到redis>>>");
                redisService.setCSVFlow(predictions);
                redisService.setCsvCount(predictionCounts);
                return redisService.getCSVFlow();
            }
        } finally {
            response.close();
            client.close();
        }
        return null;
    }

    // 分析pcap
    private List<Object> sendPcapToPython(File pcapFile) throws IOException {
        String pythonEndpoint = "http://192.168.32.136:5000/upload"; // Flask接收pcap文件的端点

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(pythonEndpoint);

        // 构建文件实体
        HttpEntity entity = MultipartEntityBuilder.create()
                .addBinaryBody("file", pcapFile, ContentType.create("application/octet-stream"), pcapFile.getName())
                .build();

        httpPost.setEntity(entity);

        // 发送请求并处理响应
        CloseableHttpResponse response = client.execute(httpPost);
        try {
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String responseString = EntityUtils.toString(responseEntity);
                // 根据需要处理响应
                JsonNode jsonNode = JsonUtils.stringToJsonNode(responseString);
                List<MyFlow> predictions = MAPPER.readValue(
                        jsonNode.get("warnings").traverse(), // 使用 traverse 方法处理数组
                        MAPPER.getTypeFactory().constructCollectionType(List.class, MyFlow.class)
                );
                log.info("存储数据库>>>");
                for (MyFlow prediction : predictions) {
                    myFlowMapper.insert(prediction);
                }
                log.info("存储到redis>>>");
                redisService.setCSVFlow(predictions);
                // 返回相关数据
                return redisService.getCSVFlow();
            }
        } finally {
            response.close();
            client.close();
        }
        return null;
    }

}
