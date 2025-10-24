package com.chenyi.safe.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 
 * @TableName rule_ids
 */

@Schema(description = "规则库")
@TableName(value ="rule")
@Data
public class Rule implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 匹配规则
     */
    @NotBlank(message = "匹配规则不能为空")
    @NotNull(message = "规则描述不能为空")
    private String pattern;

    /**
     * 描述
     */
    @NotBlank(message = "规则描述不能为空")
    @NotNull(message = "匹配规则不能为空")
    private String description;

    private int status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}