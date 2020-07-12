package com.github.houbb.mybatis.support.replace;

import com.github.houbb.mybatis.mapper.MapperSqlItem;

import java.util.List;
import java.util.Map;

/**
 * sql 替换
 * @since 0.0.13
 */
public interface ISqlReplace {

    /**
     *
     * 执行替换
     * @param sqlReplaceResult 原始的替换信息
     * @return 替换后的字符串
     * @since 0.0.13
     */
    SqlReplaceResult replace(SqlReplaceResult sqlReplaceResult);

}
