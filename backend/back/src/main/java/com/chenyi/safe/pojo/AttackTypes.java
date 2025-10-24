package com.chenyi.safe.pojo;

import lombok.Data;

/**
 * @Author：ChenYi
 * @Package：com.chenyi.safe.pojo
 * @Project：back
 * @name：AttackType
 * @Date：2024/6/25 下午5:07
 * @Filename：AttackType
 */

@Data
public class AttackTypes {

    private Integer  id;
    private String label;
    private String overview;
    private String techniques;
    private String impact;
    private String defenses;
    private String conclusion;

}
