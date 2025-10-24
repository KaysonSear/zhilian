package com.chenyi.safe.controller;

import com.chenyi.safe.common.Result;
import com.chenyi.safe.mapper.RuleMapper;
import com.chenyi.safe.pojo.Rule;
import com.chenyi.safe.service.RuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

/**
 * safe
 * 2024/6/15 上午1:33
 * 规则库相关
 *
 * @author chenyi
 * @since
 **/

@Tag(name = "规则相关")
@Log
@CrossOrigin
@RestController
@RequestMapping("/rule")
public class RuleController {

    @Resource
    private RuleMapper ruleMapper;

    @Resource
    private RuleService ruleService;

    @Operation(summary = "获取全部规则")
    @GetMapping("/getAllRule")
    public Result<?> getAllRule() {
        List<Rule> rules = ruleMapper.selectList(null);
        return Result.success(rules);
    }

    @Operation(summary = "添加规则")
    @PostMapping("/insertRule")
    public Result<?> insertRule(@Valid @RequestBody Rule rule, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return Result.error("400", errorMessage);
        }

        int insert = ruleMapper.insert(rule);
        if (insert > 0) {
            return Result.success("添加规则成功");
        }
        return Result.error("500", "请重新尝试");
    }

    @Operation(summary = "关键字查询")
    @PostMapping("/getRuleByKeyword")
    public Result<?> getRuleByKeyword(@RequestParam String keyword) {
        if (keyword != null || keyword.length() > 0) {
            List<Rule> rulesByKeyword = ruleService.getRulesByKeyword(keyword);
            return Result.success(rulesByKeyword);
        }
        return Result.error("401","请检查输入");
    }
}
