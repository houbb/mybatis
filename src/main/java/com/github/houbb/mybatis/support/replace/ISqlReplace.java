package com.github.houbb.mybatis.support.replace;

import java.util.Map;

/**
 * sql 替换
 * @since 0.0.13
 */
public interface ISqlReplace {

    /**
     *
     * 执行替换
     * @param original 原始字符串
     * @param paramMap 参数 map
     * @return 替换后的字符串
     */
    SqlReplaceResult replace(String original,
                   Map<String, Object> paramMap);

}
