package com.chenyi.safe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenyi.safe.pojo.Rule;
import org.apache.ibatis.annotations.Mapper;

/**
 * safe
 * 2024/6/15 上午1:33
 * 规则库
 *
 * @author chenyi
 * @since
 **/

@Mapper
public interface RuleMapper extends BaseMapper<Rule> {
}
