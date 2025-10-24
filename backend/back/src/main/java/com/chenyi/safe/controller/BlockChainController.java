package com.chenyi.safe.controller;

import com.chenyi.safe.common.Result;
import com.chenyi.safe.mapper.RuleMapper;
import com.chenyi.safe.pojo.Flow;
import com.chenyi.safe.pojo.FlowChain;
import com.chenyi.safe.pojo.Rule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Network;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * @Author：ChenYi
 * @Package：com.chenyi.safe.controller
 * @Project：back
 * @name：BlockChainController
 * @Date：2024/6/19 下午4:42
 * @Filename：BlockChainController
 */

@Tag(name = "区块链")
@Log
@CrossOrigin
@RestController
@RequestMapping("/block")
public class BlockChainController {

    @Resource
    private Contract contract;

    @Resource
    private Network network;

    @Resource
    private RuleMapper ruleMapper;

    @Operation(summary = "添加规则")
    @PostMapping("/insertRule")
    @ApiResponse(responseCode = "400",description = "请检查传入参数")
    @ApiResponse(responseCode = "500",description = "服务器出错了")
    public Result<?> insertRule(@Valid @RequestBody Rule rule, @Valid  @RequestParam @NotNull(message = "组织ID不可为空") String userId, BindingResult bindingResult) throws ContractException, InterruptedException, TimeoutException {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return Result.error("400", errorMessage);
        }

        // 更新状态
        Rule oldRule = ruleMapper.selectById(rule.getId());
        oldRule.setStatus(1);
        int i = ruleMapper.updateById(oldRule);
        if (i != 1) {
            return Result.error("500","请稍后重试");
        }

        byte[] bytes = contract.submitTransaction("insertRule", rule.getPattern(), rule.getDescription(), userId, String.valueOf(rule.getId()));
        System.out.println("bytes = " + new String(bytes, StandardCharsets.UTF_8));
        String result = new String(bytes, StandardCharsets.UTF_8);
        System.out.println(">>>>>>>>>>>");
        System.out.println(result);
        if ("200".equals(result)) {
            return Result.success("上链成功");
        }
        return Result.error("500","请稍后重试");
    }


    @Operation(summary = "获取上链全部规则")
    @GetMapping("/getAllRules")
    @ApiResponse(responseCode = "500",description = "服务器出错了")
    @ApiResponse(responseCode = "404",description = "没有找到结果")
    public Result<?> getAllRules() throws ContractException {

        byte[] bytes = contract.evaluateTransaction("getAllRules");
        String result = new String(bytes, StandardCharsets.UTF_8);
        if ("200".equals(result)) {
            return Result.success(result);
        }
        if ("404".equals(result)) {
            return Result.success("没有上链规则");
        }
        return Result.error("500","请稍后重试");
    }

    @Operation(summary = "取消上链规则")
    @DeleteMapping("/deleteRule")
    public Result<?> deleteRule(@Valid @RequestParam @NotNull(message = "规则ID不可为空") String ruleId) throws ContractException {

        byte[] bytes = contract.evaluateTransaction("deleteRule", ruleId);
        String result = new String(bytes, StandardCharsets.UTF_8);
        if ("200".equals(result)) {
            return Result.success("取消成功");
        }
        return Result.error("500","请稍后重试");
    }

    @Operation(summary = "上链流量")
    @PostMapping("/insertFlow")
    public Result<?> insertFlow(@Valid @RequestBody FlowChain flow, BindingResult bindingResult) throws ContractException, InterruptedException, TimeoutException {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return Result.error("400", errorMessage);
        }

        byte[] bytes = contract.submitTransaction("insertFlow", flow.getId(), flow.getSrcIp(), flow.getDstIp(), flow.getSrcPort(), flow.getDstPort(), flow.getProtocol());
        String result = new String(bytes, StandardCharsets.UTF_8);
        if ("200".equals(result)) {
            return Result.success("流量上链成功");
        }

        return Result.error("500","请稍后重试");
    }

    @Operation(summary = "取消流量上链")
    @DeleteMapping("/deleteFlow")
    public Result<?> deleteFlow(@Valid @RequestParam @NotNull(message = "流量id不可为空") String flowId,BindingResult bindingResult) throws ContractException {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return Result.error("400", errorMessage);
        }

        byte[] bytes = contract.evaluateTransaction("deleteFlow", flowId);
        String result = new String(bytes, StandardCharsets.UTF_8);
        if ("200".equals(result)) {
            return Result.success("流量取消上链成功");
        }

        return Result.error("500","请稍后重试");
    }
}
