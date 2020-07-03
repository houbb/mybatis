package com.github.houbb.mybatis.mapper;

import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.util.XmlUtil;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public class MapperRegister {

    /**
     * 映射的方法信息
     * @since 0.0.1
     */
    private static final Map<String, MapperMethod> MAPPER_METHOD_MAP = new ConcurrentHashMap<>();

    /**
     * 配置信息
     * @since 0.0.5
     */
    private final Config config;

    public MapperRegister(Config config) {
        this.config = config;
    }

    /**
     * 添加映射
     * @param mapperPath 文件路径
     * @return 结果
     * @since 0.0.1
     */
    public MapperRegister addMapper(final String mapperPath) {
        MapperClass mapperClass = buildMapperData(mapperPath);
        String namespace = mapperClass.getNamespace();
        Class clazz = ClassUtil.getClass(namespace);

        this.addMapper(clazz, mapperClass);
        return this;
    }

    /**
     * 添加映射
     * @param clazz 类
     * @param mapperClass 实现信息
     * @return 结果
     * @since 0.0.1
     */
    public MapperRegister addMapper(final Class<?> clazz,
                                    final MapperClass mapperClass) {
        // 添加方法信息
        String className = clazz.getName();
        for(MapperMethod mapperMethod : mapperClass.getMethodList()) {
            String methodName = mapperMethod.getMethodName();
            String key = className+"#"+methodName;
            MAPPER_METHOD_MAP.put(key, mapperMethod);
        }

        return this;
    }

    /**
     * 获取 mapper 方法的信息
     * @param clazz 类
     * @param methodName 方法
     * @return 结果
     * @since 0.0.1
     */
    public MapperMethod getMapperMethod(final Class<?> clazz,
                                        final String methodName) {
        String key = clazz.getName()+"#"+methodName;
        return MAPPER_METHOD_MAP.get(key);
    }

    /**
     * 初始化 mapper data
     * @param path 文件路径
     * @return 结果
     * @since 0.0.1
     */
    private MapperClass buildMapperData(final String path) {
        MapperClass mapperClass = new MapperClass();
        Element root = XmlUtil.getRoot(path);

        // 接口名称
        String namespace = root.attributeValue("namespace");
        List list = root.elements();
        List<MapperMethod> methodList = new ArrayList<>(list.size());

        // 遍历下面的所有元素
        for(Object item : list) {
            Element element = (Element) item;
            MapperMethod mapperMethod = new MapperMethod();

            mapperMethod.setType(element.getName());
            mapperMethod.setMethodName(element.attributeValue("id"));
            String paramType = element.attributeValue("paramType");
            String resultType = element.attributeValue("resultType");
            mapperMethod.setParamType(ClassUtil.getClass(config.getTypeAlias(paramType)));
            mapperMethod.setResultType(ClassUtil.getClass(config.getTypeAlias(resultType)));
            mapperMethod.setSql(element.getTextTrim());

            methodList.add(mapperMethod);
        }

        mapperClass.setNamespace(namespace);
        mapperClass.setMethodList(methodList);
        return mapperClass;
    }

}
