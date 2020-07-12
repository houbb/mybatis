package com.github.houbb.mybatis.support.replace.impl;

import com.github.houbb.heaven.support.pipeline.Pipeline;
import com.github.houbb.heaven.support.pipeline.impl.DefaultPipeline;
import com.github.houbb.mybatis.support.replace.ISqlReplace;
import com.github.houbb.mybatis.support.replace.SqlReplaceResult;

import java.util.List;

/**
 * sql 替换责任链
 * @since 0.0.15
 */
public abstract class SqlReplaceChains implements ISqlReplace {

    /**
     * 初始化责任链
     * @param pipeline 流水线
     * @since 0.0.16
     */
    protected abstract void init(Pipeline<ISqlReplace> pipeline);

    @Override
    public SqlReplaceResult replace(SqlReplaceResult sqlReplaceResult) {
        Pipeline<ISqlReplace> pipeline = new DefaultPipeline<>();
        init(pipeline);

        List<ISqlReplace> list = pipeline.list();
        for(ISqlReplace replace : list) {
            sqlReplaceResult = replace.replace(sqlReplaceResult);
        }

        return sqlReplaceResult;
    }

}
