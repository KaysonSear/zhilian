package com.chenyi.safe.service;

import com.chenyi.safe.pojo.Rule;

import java.util.List;

/**
 * safe
 * 2024/6/17 上午1:36
 * 规则库
 *
 * @author chenyi
 * @since
 **/
public interface RuleService {

    List<Rule> getRulesByKeyword(String keyword);
}
