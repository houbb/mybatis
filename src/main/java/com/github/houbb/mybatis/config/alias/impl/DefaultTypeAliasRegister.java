package com.github.houbb.mybatis.config.alias.impl;

import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.mybatis.config.alias.TypeAliasRegister;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author binbin.hou
 * @since 0.0.5
 */
public class DefaultTypeAliasRegister implements TypeAliasRegister {

    /**
     * 类名别称集合
     * @since 0.0.5
     */
    private static final Map<String, String> TYPE_ALIAS_MAP = new ConcurrentHashMap<>();

    static {
        TYPE_ALIAS_MAP.put("collection", Collection.class.getName());
        TYPE_ALIAS_MAP.put("object", Object.class.getName());
        TYPE_ALIAS_MAP.put("_byte", byte.class.getName());
        TYPE_ALIAS_MAP.put("long", Long.class.getName());
        TYPE_ALIAS_MAP.put("date", Date.class.getName());
        TYPE_ALIAS_MAP.put("float", Float.class.getName());
        TYPE_ALIAS_MAP.put("short", Short.class.getName());
        TYPE_ALIAS_MAP.put("_long", long.class.getName());
        TYPE_ALIAS_MAP.put("byte", Byte.class.getName());
        TYPE_ALIAS_MAP.put("_float", float.class.getName());
        TYPE_ALIAS_MAP.put("_short", short.class.getName());
        TYPE_ALIAS_MAP.put("map", Map.class.getName());
        TYPE_ALIAS_MAP.put("boolean", Boolean.class.getName());
        TYPE_ALIAS_MAP.put("bigdecimal", BigDecimal.class.getName());
        TYPE_ALIAS_MAP.put("iterator", Iterator.class.getName());
        TYPE_ALIAS_MAP.put("_integer", int.class.getName());
        TYPE_ALIAS_MAP.put("integer", Integer.class.getName());
        TYPE_ALIAS_MAP.put("int", Integer.class.getName());
        TYPE_ALIAS_MAP.put("_int", int.class.getName());
        TYPE_ALIAS_MAP.put("list", List.class.getName());
        TYPE_ALIAS_MAP.put("_double", double.class.getName());
        TYPE_ALIAS_MAP.put("double", Double.class.getName());
        TYPE_ALIAS_MAP.put("decimal", BigDecimal.class.getName());
        TYPE_ALIAS_MAP.put("_boolean", boolean.class.getName());
        TYPE_ALIAS_MAP.put("string", String.class.getName());
        TYPE_ALIAS_MAP.put("arraylist", ArrayList.class.getName());
        TYPE_ALIAS_MAP.put("hashmap", HashMap.class.getName());
    }

    /**
     * 注册
     *
     * @param alias 别称
     * @param typeName 类型名称
     * @return 结果
     * @since 0.0.5
     */
    @Override
    public DefaultTypeAliasRegister register(final String alias,
                                             final String typeName) {
        TYPE_ALIAS_MAP.put(alias, typeName);

        return this;
    }


    @Override
    public String getTypeOrDefault(String alias) {
        String fullName = TYPE_ALIAS_MAP.get(alias);

        if(StringUtil.isNotEmpty(fullName)) {
            return fullName;
        }
        return alias;
    }

}
