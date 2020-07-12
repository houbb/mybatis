package com.github.houbb.mybatis.support.replace.impl;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.mybatis.constant.enums.MapperSqlType;
import com.github.houbb.mybatis.exception.MybatisException;
import com.github.houbb.mybatis.mapper.MapperClass;
import com.github.houbb.mybatis.mapper.MapperMethod;
import com.github.houbb.mybatis.mapper.MapperSqlItem;
import com.github.houbb.mybatis.mapper.MapperSqlTemplate;
import com.github.houbb.mybatis.support.replace.ISqlReplace;
import com.github.houbb.mybatis.support.replace.SqlReplaceResult;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * if 条件的处理
 * @since 0.0.16
 */
public class IncludeRefSqlReplace implements ISqlReplace {

    @Override
    public SqlReplaceResult replace(SqlReplaceResult sqlReplaceResult) {
        //1. 基本信息
        MapperMethod mapperMethod = sqlReplaceResult.mapperMethod();
        List<MapperSqlItem> sqlItems = mapperMethod.getSqlItemList();
        List<MapperSqlTemplate> sqlTemplates = mapperMethod.getRefClass().getSqlTemplateList();

        //2. 循环处理
        for(MapperSqlItem sqlItem : sqlItems) {
            if(MapperSqlType.INCLUDE.equals(sqlItem.getType())) {
                String refSql = getRefIdSql(sqlTemplates, sqlItem.getRefId());
                sqlItem.setSql(refSql);
                sqlItem.setReadyForSql(true);
            }
        }

        return sqlReplaceResult;
    }

    /**
     * 获取引用的 sql
     * @param sqlTemplates sql 模板列表
     * @param refId 引用的 id 标识
     * @return 结果
     * @since 0.0.11
     */
    private String getRefIdSql(final List<MapperSqlTemplate> sqlTemplates,
                               final String refId) {
        ArgUtil.notEmpty(refId, "refId");

        for(MapperSqlTemplate sqlTemplate : sqlTemplates) {
            String id = sqlTemplate.getId();
            if(refId.equals(id)) {
                return sqlTemplate.getSql();
            }
        }

        throw new MybatisException("Not found sql template for refId: " + refId);
    }


}
