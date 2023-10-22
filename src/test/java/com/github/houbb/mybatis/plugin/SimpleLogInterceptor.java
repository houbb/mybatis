package com.github.houbb.mybatis.plugin;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;

import java.util.Arrays;

public class SimpleLogInterceptor extends AbstractInterceptor {

    private static final Log LOG = LogFactory.getLog(SimpleLogInterceptor.class);

    @Override
    public void before(Invocation invocation) {
        LOG.info("----param: {}", Arrays.toString(invocation.getArgs()));
    }

    @Override
    public void after(Invocation invocation, Object result) {
        LOG.info("----result: {}", result);
    }

}
