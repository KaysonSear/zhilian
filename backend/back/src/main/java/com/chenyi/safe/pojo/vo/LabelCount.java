package com.chenyi.safe.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author：ChenYi
 * @Package：com.chenyi.safe.pojo.vo
 * @Project：back
 * @name：LabelCount
 * @Date：2024/6/22 下午9:39
 * @Filename：LabelCount
 */

@Data
@AllArgsConstructor
public class LabelCount {
    private String label;
    private Long count;
}
