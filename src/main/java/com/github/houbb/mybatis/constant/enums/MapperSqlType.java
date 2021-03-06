package com.github.houbb.mybatis.constant.enums;

/**
 * sql 的类型
 * @since 0.0.12
 */
public enum MapperSqlType {

    /**
     * 纯文本
     * @since 0.0.12
     */
    TEXT,

    /**
     * 包含的信息
     * @since 0.0.12
     */
    INCLUDE,

    /**
     * 是否满足条件
     * @since 0.0.16
     */
    IF,

    /**
     * 循环遍历
     * @since 0.0.17
     */
    FOREACH,

    ;
}
