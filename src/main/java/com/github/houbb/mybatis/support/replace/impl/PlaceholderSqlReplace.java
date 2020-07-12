package com.github.houbb.mybatis.support.replace.impl;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.heaven.util.util.MapUtil;
import com.github.houbb.mybatis.constant.MapperSqlConst;
import com.github.houbb.mybatis.exception.MybatisException;
import com.github.houbb.mybatis.mapper.MapperSqlItem;
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
public class PlaceholderSqlReplace implements ISqlReplace {

    @Override
    public SqlReplaceResult replace(SqlReplaceResult sqlReplaceResult) {
        List<String> psNames = new ArrayList<>();

        // 基本属性
        Map<String, Object> paramMap = sqlReplaceResult.paramMap();
        List<MapperSqlItem> sqlItemList = sqlReplaceResult.dynamicSqlItems();

        if(MapUtil.isEmpty(paramMap)) {
            return sqlReplaceResult;
        }

        // 从左向右遍历
        StringBuilder sqlBuffer = new StringBuilder();
        StringBuilder replaceBuffer = new StringBuilder();
        StringBuilder psBuffer = new StringBuilder();
        Stack<Character> replaceStack = new Stack<>();
        Stack<Character> psStack = new Stack<>();

        for(MapperSqlItem sqlItem : sqlItemList) {
            if(sqlItem.isReadyForSql()) {
                // 获取对应的文本
                String original = sqlItem.getSql();

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

                // 设置处理后的值
                sqlItem.setSql(sqlBuffer.toString());
                sqlBuffer = new StringBuilder();
            }
        }

        sqlReplaceResult.psNames(psNames);

        return sqlReplaceResult;
    }

}
