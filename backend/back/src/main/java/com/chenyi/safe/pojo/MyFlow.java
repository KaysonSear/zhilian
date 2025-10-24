package com.chenyi.safe.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author：ChenYi
 * @Package：com.chenyi.safe.pojo
 * @Project：back
 * @name：MyFlow
 * @Date：2024/6/24 下午3:45
 * @Filename：MyFlow
 */

@Data
public class MyFlow {

    @TableId(type = IdType.AUTO)
    private Integer  id;
    private double confidence;
    private String dstIp;
    private int dstPort;
    private String label;
    private double packetLengthMean;
    private int protocol;
    private String srcIp;
    private int srcPort;
    private String timestamp;
    private String ruleOrAi;

}
