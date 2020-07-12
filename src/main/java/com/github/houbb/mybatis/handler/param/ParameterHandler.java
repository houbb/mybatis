package com.github.houbb.mybatis.handler.param;

import com.github.houbb.heaven.annotation.CommonEager;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassTypeUtil;
import com.github.houbb.heaven.util.lang.reflect.ReflectFieldUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.heaven.util.util.MapUtil;
import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.constant.MapperSqlConst;
import com.github.houbb.mybatis.exception.MybatisException;
import com.github.houbb.mybatis.handler.type.handler.TypeHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * 反射设置属性值
 * <p>
 * TODO: 这里应该分为两个部分
 * <p>
 * （1）java to jdbc 类型
 * （2）jdbc to java 类型
 *
 * @author binbin.hou
 * @since 0.0.1
 */
public class ParameterHandler {

    /**
     * 语句信息
     *
     * @since 0.0.1
     */
    private final PreparedStatement statement;

    /**
     * 配置信息
     *
     * @since 0.0.4
     */
    private final Config config;

    public ParameterHandler(PreparedStatement statement, Config config) {
        this.statement = statement;
        this.config = config;
    }

    /**
     * 设置参数信息
     * <p>
     * 可以处理的方式：
     * <p>
     * （1）设置处理对应的值
     * （2）设置处理对应的类型
     *
     * @param psNames  参数列表
     * @param paramMap 参数值
     * @since 0.0.1
     */
    @SuppressWarnings("all")
    public void setParams(final List<String> psNames,
                          final Map<String, Object> paramMap) {
        try {
            // 跳过处理
            if (CollectionUtil.isEmpty(psNames)
                    || MapUtil.isEmpty(paramMap)) {
                return;
            }

           List<Object> params = new ArrayList<>(psNames.size());
            for (int i = 0; i < psNames.size(); i++) {
                String fieldName = psNames.get(i);
                Object value = this.getParamValue(fieldName, paramMap);

                // 获取对应的处理类
                Class valueType = value.getClass();
                TypeHandler typeHandler = config.getTypeHandler(valueType);

                params.add(value);
                typeHandler.setParameter(statement, i + 1, value);
            }

            System.out.println("------ Prams: " + params);
        } catch (SQLException throwables) {
            throw new MybatisException(throwables);
        }
    }

    /**
     * 获取参数对应的值
     * <p>
     * 1. 直接根据名称获取，如 orderBy，存在则直接返回。
     * <p>
     * 2. 名称不存在，是否属于多级引用。比如 user.name
     * <p>
     * 暂时只支持2级。
     * <p>
     * 3. 如果只有一个参数
     * <p>
     * 3.1 用户自定义 && 包含 field: 反射当前元素，是否包含这个 field 比如 name
     * 3.2 直接返回当前值   比如直接返回 #{id}
     *
     * @param paramName 参数名称
     * @param paramMap  参数对应的 map
     * @return 结果
     * @since 0.0.14
     */
    private Object getParamValue(final String paramName,
                                 final Map<String, Object> paramMap) {
        // 对应的列表 index
        if (paramName.contains(":index:")) {
            return getCollectionIndexValue(paramName, paramMap);
        }
        // 对应的列表 value
        if (paramName.contains(":item:")) {
            return getCollectionItem(paramName, paramMap);
        }

        //1. 名称是否存在
        if (paramMap.containsKey(paramName)) {
            return paramMap.get(paramName);
        }

        //2. 多级引用
        if (paramName.contains(MapperSqlConst.MULTI_REF)) {
            String[] paramNames = paramName.split("\\.");
            if (paramNames.length != 2) {
                throw new MybatisException("Only support two level reference, but found: " + paramName);
            }

            String firstParam = paramNames[0];
            String secondParam = paramNames[1];

            Object firstRef = paramMap.get(firstParam);
            // 第一层引用如果值为空，则直接报错
            if (ObjectUtil.isNull(firstRef)) {
                throw new MybatisException("Not found match param for name: " + paramName);
            }
            // 获取第二层的值
            return ReflectFieldUtil.getValue(secondParam, firstRef);
        }

        //3. 只有一个参数
        if (paramMap.size() == 1) {
            Map.Entry<String, Object> firstEntry = MapUtil.getFirstEntry(paramMap);
            //3.1 这个值不能为空
            if (ObjectUtil.isNull(firstEntry)
                    || ObjectUtil.isNull(firstEntry.getValue())) {
                throw new MybatisException("The first param value is null for paramName: " + paramName);
            }

            final Object mapValue = firstEntry.getValue();
            Class<?> type = mapValue.getClass();

            //3.2 对应的字段属性
            if (ClassTypeUtil.isBean(type)) {
                return ReflectFieldUtil.getValue(paramName, mapValue);
            }

            //3.3 基本类型
            return mapValue;
        }

        //4. 直接报错
        throw new MybatisException("Not found match param for name: " + paramName);
    }

    /**
     * 获取对应的值
     * @param paramName 名称
     * @param paramMap 参数
     * @return 结果
     * @since 0.0.17
     */
    private Object getCollectionItem(final String paramName,
                                           final Map<String, Object> paramMap) {
        //foreach 对应的数值
        String[] names = paramName.split(":");
        int index = Integer.parseInt(names[names.length - 1]);

        String name = names[0];
        String[] nameArray = name.split("\\.");
        Object value = paramMap.get(nameArray[0]);

        if (value == null) {
            throw new MybatisException("Not found value for name: " + nameArray[0]);
        }

        // 获取对应下表的值
        Object indexValue = getValueByIndex(value, index);
        return getActualValueByName(indexValue, nameArray);
    }

    /**
     * 获取以及或者多级引用对应的值
     * @param instance 对象实例
     * @param nameArray 名称数组
     * @return 结果
     * @since 0.0.17
     */
    private Object getActualValueByName(final Object instance,
                                        final String[] nameArray) {
        if (nameArray.length == 1) {
            return instance;
        }
        if (nameArray.length == 2) {
            String fieldName = nameArray[1];
            return ReflectFieldUtil.getValue(fieldName, instance);
        }
        throw new MybatisException("Not support to 2+ level ref, but found: " + Arrays.toString(nameArray));
    }

    /**
     * 获取集合对应的索引值
     *
     * @param paramName 参数名称
     * @param paramMap  参数集合
     * @return 结果
     * @since 0.0.17
     */
    private Object getCollectionIndexValue(final String paramName,
                                           final Map<String, Object> paramMap) {
        //foreach 对应的数值
        String[] names = paramName.split(":");
        int index = Integer.parseInt(names[names.length - 1]);

        String name = names[0];
        String[] nameArray = name.split("\\.");
        Object value = paramMap.get(nameArray[0]);

        if (value == null) {
            throw new MybatisException("Not found value for name: " + nameArray[0]);
        }

        if (ClassTypeUtil.isMap(value.getClass())) {
            Map<?, ?> map = (Map<?, ?>) value;
            Map.Entry<?, ?> entry = getMapEntry(map, index);

            Object keyValue = entry.getKey();
            return getActualValueByName(keyValue, nameArray);
        }

        // 其他，直接返回 index 值
        return index;
    }

    /**
     * 根据下标志获取对应的值
     * @param value 原始值
     * @param index 下表
     * @return 结果
     * @since 0.0.17
     */
    @CommonEager
    private Object getValueByIndex(Object value,
                                   final int index) {
        Class<?> clazz = value.getClass();
        if(ClassTypeUtil.isMap(clazz)) {
            Map<?, ?> map = (Map<?, ?>) value;
            Map.Entry<?, ?> entry = getMapEntry(map, index);
            return entry.getValue();
        }
        if(ClassTypeUtil.isSet(clazz)) {
            return getSetByIndex((Set<?>) value, index);
        }
        if(ClassTypeUtil.isArray(clazz)) {
            //TODO: 这里后期需要处理下 8 大基本类型
            Object[] array = (Object[]) value;
            return array[index];
        }
        if(ClassTypeUtil.isList(clazz)) {
            List<?> list = (List<?>) value;
            return list.get(index);
        }

        throw new MybatisException("Only support collection for map/array/list/set, " +
                "but found " + clazz.getName());
    }


    /**
     * 获取指定下标的明细
     *
     * @param map   map
     * @param index 下标
     * @return 结果
     * @since 0.0.17
     */
    @CommonEager
    private Map.Entry<?, ?> getMapEntry(Map<?, ?> map, final int index) {
        int i = 0;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (index == i) {
                return entry;
            }
            i++;
        }

        throw new MybatisException("Map.size() is " + map.size() + ", but index is " + index);
    }

    /**
     * 获取指定下标的明细
     *
     * @param sets 集合
     * @param index 下标
     * @return 结果
     * @since 0.0.17
     */
    @CommonEager
    private Object getSetByIndex(Set<?> sets, final int index) {
        int i = 0;
        for (Object set : sets) {
            if (index == i) {
                return set;
            }
            i++;
        }

        throw new MybatisException("Set.size() is " + sets.size() + ", but index is " + index);
    }

}
