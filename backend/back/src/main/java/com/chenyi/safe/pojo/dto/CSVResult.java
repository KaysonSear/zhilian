package com.chenyi.safe.pojo.dto;

import com.chenyi.safe.pojo.CSV;
import lombok.Data;

import java.util.List;

/**
 * @Author：ChenYi
 * @Package：com.chenyi.safe.pojo.dto
 * @Project：back
 * @name：CSVResult
 * @Date：2024/6/24 下午4:18
 * @Filename：CSVResult
 */

@Data
public class CSVResult {
    private CSV csvCount;
    private List<Object> csvFlow;
}
