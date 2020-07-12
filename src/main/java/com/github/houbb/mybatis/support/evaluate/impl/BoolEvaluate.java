package com.github.houbb.mybatis.support.evaluate.impl;

import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassTypeUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.heaven.util.lang.reflect.ReflectFieldUtil;
import com.github.houbb.heaven.util.util.MapUtil;
import com.github.houbb.mybatis.support.evaluate.IEvaluate;
import org.apache.commons.jexl3.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * bool 的处理
 *
 * https://github.com/apache/commons-jexl
 *
 * @since 0.0.16
 */
public class BoolEvaluate implements IEvaluate {

    /**
     * 参数集合
     * @since 0.0.16
     */
    private final Map<String, Object> paramMap;

    /**
     * 上下文
     * @since 0.0.16
     */
    private final JexlContext jexlContext;

    public BoolEvaluate(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
        this.jexlContext = initJexlContext();
    }

    @Override
    public Object evaluate(String expression) {
        // Create or retrieve an engine
        JexlEngine jexl = new JexlBuilder().create();
        // Create an expression
        JexlExpression e = jexl.createExpression(expression);

        // Now evaluate the expression, getting the result
        return e.evaluate(jexlContext);
    }

    /**
     * 1. 直接将参数全部放进来
     * 2. 将第一个参数的信息全部放入
     * @return 上下文
     * @since 0.0.16
     */
    private JexlContext initJexlContext() {
        JexlContext jexlContext = new MapContext();

        for(Map.Entry<String, Object> entry : paramMap.entrySet()) {
            jexlContext.set(entry.getKey(), entry.getValue());
        }

        // 第一个参数
        Map.Entry<String, Object> firstEntry = MapUtil.getFirstEntry(paramMap);
        if(ObjectUtil.isNull(firstEntry)) {
            return jexlContext;
        }

        Object value = firstEntry.getValue();
        if(ObjectUtil.isNotNull(firstEntry)
            && ObjectUtil.isNotNull(value)
            && ClassTypeUtil.isBean(value.getClass())) {
            Class<?> clazz = value.getClass();

            List<Field> fieldList = ClassUtil.getAllFieldList(clazz);
            for(Field field : fieldList) {
                String fieldName = field.getName();

                // 不包含，则放入
                if(!paramMap.containsKey(fieldName)) {
                    Object fieldValue = ReflectFieldUtil.getValue(field, value);
                    jexlContext.set(fieldName, fieldValue);
                }
            }
        }

        return jexlContext;
    }

}
