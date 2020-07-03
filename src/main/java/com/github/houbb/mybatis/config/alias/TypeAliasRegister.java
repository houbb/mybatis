package com.github.houbb.mybatis.config.alias;

/**
 * @author binbin.hou
 * @since 0.0.5
 */
public interface TypeAliasRegister {

    /**
     * 注册
     * @param alias 别称
     * @param typeName 类型名称
     * @return 结果
     * @since 0.0.5
     */
    TypeAliasRegister register(final String alias,
                               final String typeName);

    /**
     * 获取名称的全称
     * 1. 默认返回别称本身
     * @param alias 别称
     * @return 结果
     * @since 0.0.5
     */
    String getTypeOrDefault(final String alias);

}
