package com.chenyi.safe.pojo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @Author：ChenYi
 * @Package：com.chenyi.safe.pojo
 * @Project：back
 * @name：FlowChain
 * @Date：2024/6/19 下午6:32
 * @Filename：FlowChain
 */

@Data
public class FlowChain {

    @NotNull(message = "流量id不可为空")
    @NotBlank(message = "流量id不可为空")
    private String id;

    @NotNull(message = "源IP不可为空")
    @NotBlank(message = "源IP不可为空")
    private String srcIp;

    @NotNull(message = "目标IP不可为空")
    @NotBlank(message = "目标IP不可为空")
    private String dstIp;

    @NotNull(message = "源端口不可为空")
    @NotBlank(message = "源端口不可为空")
    private String srcPort;

    @NotNull(message = "目标端口不可为空")
    @NotBlank(message = "目标端口不可为空")
    private String dstPort;

    @NotNull(message = "协议不可为空")
    @NotBlank(message = "协议不可为空")
    private String protocol;
}
