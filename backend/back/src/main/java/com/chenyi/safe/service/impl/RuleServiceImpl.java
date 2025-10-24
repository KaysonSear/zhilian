package com.chenyi.safe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chenyi.safe.mapper.RuleMapper;
import com.chenyi.safe.pojo.Rule;
import com.chenyi.safe.service.RuleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * safe
 * 2024/6/17 上午1:37
 * 规则库
 *
 * @author chenyi
 * @since
 **/

@Service
public class RuleServiceImpl implements RuleService {

    @Resource
    private RuleMapper ruleMapper;

    /**
     * 关键字查询
     * @param keyword 入参
     * @return List<Rule>
     * @author Chenyi
     * @date 2024/6/17 上午1:39
     **/
    @Override
    public List<Rule> getRulesByKeyword(String keyword) {
        QueryWrapper<Rule> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("description", keyword);
        return ruleMapper.selectList(queryWrapper);
    }
}
