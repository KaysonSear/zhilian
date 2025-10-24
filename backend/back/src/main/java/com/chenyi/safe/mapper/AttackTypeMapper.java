package com.chenyi.safe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenyi.safe.pojo.AttackTypes;
import org.apache.ibatis.annotations.Mapper;

/**
 * back
 * 2024/6/25 下午8:31
 * 流量安全报告
 *
 * @author chenyi
 * @since
 **/

@Mapper
public interface AttackTypeMapper  extends BaseMapper<AttackTypes> {
}
