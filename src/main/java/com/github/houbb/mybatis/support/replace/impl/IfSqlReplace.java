package com.github.houbb.mybatis.support.replace.impl;

import com.github.houbb.mybatis.constant.enums.MapperSqlType;
import com.github.houbb.mybatis.mapper.MapperSqlItem;
import com.github.houbb.mybatis.support.evaluate.IEvaluate;
import com.github.houbb.mybatis.support.evaluate.impl.BoolEvaluate;
import com.github.houbb.mybatis.support.replace.ISqlReplace;
import com.github.houbb.mybatis.support.replace.SqlReplaceResult;

import java.util.List;
import java.util.Map;

/**
 * if 条件的处理
 *
 * 条件：and or not
 * 比较 == != gt; lt;
 * @since 0.0.16
 */
public class IfSqlReplace implements ISqlReplace {

    @Override
    public SqlReplaceResult replace(SqlReplaceResult sqlReplaceResult) {
        //1. 基本信息
        Map<String, Object> paramMap = sqlReplaceResult.paramMap();
        List<MapperSqlItem> sqlItemList = sqlReplaceResult.dynamicSqlItems();

        //2. 遍历处理
        IEvaluate evaluate = new BoolEvaluate(paramMap);
        for(MapperSqlItem sqlItem : sqlItemList) {
            if(MapperSqlType.IF.equals(sqlItem.getType())) {
                String expression = sqlItem.getTestCondition();

                boolean isMatch = (boolean) evaluate.evaluate(expression);
                if(isMatch) {
                    sqlItem.setReadyForSql(true);
                }
            }
        }

        //3. 返回
        return sqlReplaceResult;
    }

}
