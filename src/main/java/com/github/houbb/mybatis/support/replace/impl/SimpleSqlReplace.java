package com.github.houbb.mybatis.support.replace.impl;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.heaven.util.util.MapUtil;
import com.github.houbb.mybatis.constant.MapperSqlConst;
import com.github.houbb.mybatis.exception.MybatisException;
import com.github.houbb.mybatis.support.replace.ISqlReplace;
import com.github.houbb.mybatis.support.replace.SqlReplaceResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * sql 替换
 * @since 0.0.13
 */
public class SimpleSqlReplace implements ISqlReplace {

    /**
     * 执行替换
     *
     * ${orderBy}
     * @param original 原始字符串
     * @param paramMap 参数 map
     * @return 替换后的字符串
     */
    public SqlReplaceResult replace(String original,
                                    Map<String, Object> paramMap) {
        SqlReplaceResult result = new SqlReplaceResult();
        List<String> psNames = new ArrayList<>();
        List<String> replaceKeys = new ArrayList<>();

        if(MapUtil.isEmpty(paramMap)) {
            result.sql(original).psNames(psNames);
            return result;
        }

        // 从左向右遍历
        StringBuilder sqlBuffer = new StringBuilder();
        StringBuilder replaceBuffer = new StringBuilder();
        StringBuilder psBuffer = new StringBuilder();
        Stack<Character> replaceStack = new Stack<>();
        Stack<Character> psStack = new Stack<>();
        for(int i = 0; i < original.length(); i++) {
            char c = original.charAt(i);

            if(c == '$') {
                replaceStack.push(c);

                // 判断下一个字符
                c = original.charAt(i+1);
                if(c != '{') {
                    throw new MybatisException("$ must next with char {, but found " + c);
                }
                i++;

                continue;
            }
            if(c == '#') {
                psStack.push(c);
                // 判断下一个字符
                c = original.charAt(i+1);
                if(c != '{') {
                    throw new MybatisException("# must next with char {, but found " + c);
                }
                i++;

                continue;
            }


            if(c == '}') {
                if(!psStack.isEmpty()) {
                    psStack.pop();
                    // 加入 sqlBuffer
                    String psName = psBuffer.toString();
                    psNames.add(psName);
                    sqlBuffer.append(MapperSqlConst.PLACEHOLDER);
                    // 重置
                    psBuffer = new StringBuilder();
                    continue;
                }
                if(!replaceStack.isEmpty()) {
                    replaceStack.pop();

                    // 直接连接对应的字符串
                    // 后期需要添加其他的转换形式
                    String replaceKey = replaceBuffer.toString();
                    replaceKeys.add(replaceKey);
                    String replaceValue = paramMap.get(replaceKey).toString();
                    sqlBuffer.append(replaceValue);

                    // 重置
                    replaceBuffer = new StringBuilder();
                    continue;
                }

                throw new MybatisException("char } must start with ${ or #{ !");
            }

            // 判断当前字符
            if(!replaceStack.isEmpty()) {
                replaceBuffer.append(c);
            } else if(!psStack.isEmpty()) {
                psBuffer.append(c);
            } else {
                sqlBuffer.append(c);
            }

        }

        if(!psStack.isEmpty()) {
            throw new MybatisException("#{ not found } for enclosure!");
        }
        if(!replaceStack.isEmpty()) {
            throw new MybatisException("${ not found } for enclosure!");
        }

        // 如果参数列表为空，比如原来直接使用了 ? 的场景
        // 那么将 replace 的 key 移除即可。
        if(CollectionUtil.isEmpty(psNames)) {
            psNames = buildPsNames(replaceKeys, paramMap);
        }

        result.sql(sqlBuffer.toString())
                .psNames(psNames);
        return result;
    }

    /**
     * 构建对应的 psNames
     * @param replaceParams 替换的参数列表
     * @param paramMap 参数集合
     * @return 结果
     * @since 0.0.13
     */
    private List<String> buildPsNames(final List<String> replaceParams,
                                      Map<String, Object> paramMap) {

        List<String> results = new ArrayList<>();

        for(String key : paramMap.keySet()) {
            if(!replaceParams.contains(key)) {
                results.add(key);
            }
        }
        return results;
    }


}
