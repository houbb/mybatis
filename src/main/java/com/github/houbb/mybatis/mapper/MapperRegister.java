package com.github.houbb.mybatis.mapper;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.constant.MapperTypeConst;
import com.github.houbb.mybatis.exception.MybatisException;
import com.github.houbb.mybatis.util.XmlUtil;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import org.dom4j.tree.DefaultText;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
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
        Map<String, Method> methodMap = buildMethodMap(namespace);

        List<MapperMethod> methodList = new ArrayList<>();
        List<MapperSqlTemplate> sqlTemplateList = new ArrayList<>();
        // 遍历下面的所有元素
        for(Object item : list) {
            Element element = (Element) item;
            MapperMethod mapperMethod = new MapperMethod();
            String type = element.getName();
            mapperMethod.setType(type);
            //select
            String id = element.attributeValue("id");

            //sql
            if(MapperTypeConst.SQL.equals(type)) {
                MapperSqlTemplate sqlTemplate = new MapperSqlTemplate();
                sqlTemplate.setId(id);
                sqlTemplate.setSql(element.getTextTrim());
                sqlTemplateList.add(sqlTemplate);
            }
            if(MapperTypeConst.SELECT.equals(type)) {
                mapperMethod.setMethodName(id);
                Method method = methodMap.get(id);
                mapperMethod.setMethod(method);
                String paramType = element.attributeValue("paramType");
                String resultType = element.attributeValue("resultType");

                // 入参可能不存在
                if(StringUtil.isNotEmpty(paramType)) {
                    mapperMethod.setParamType(ClassUtil.getClass(config.getTypeAlias(paramType)));
                }

                mapperMethod.setResultType(ClassUtil.getClass(config.getTypeAlias(resultType)));
                // 使用 content 替代简单的文本
                List contentList = element.content();
                List<MapperSqlItem> sqlItemList = new ArrayList<>();
                for(Object content : contentList) {
                    MapperSqlItem sqlItem = new MapperSqlItem();
                    if(content instanceof DefaultText) {
                        DefaultText defaultText = (DefaultText)content;
                        sqlItem.setType("text");
                        sqlItem.setSql(defaultText.getText().trim());
                    } else if(content instanceof DefaultElement) {
                        // 这里后期会有更加复杂的处理，暂时只考虑简单的 refId
                        DefaultElement defaultElement = (DefaultElement)content;
                        sqlItem.setType("include");
                        sqlItem.setRefId(defaultElement.attributeValue("refid"));
                    }

                    sqlItemList.add(sqlItem);
                }

                mapperMethod.setSqlItemList(sqlItemList);
                // 这个是暂时的 sql
                mapperMethod.setSql(element.getTextTrim());

                methodList.add(mapperMethod);
            }
        }

        mapperClass.setNamespace(namespace);
        mapperClass.setMethodList(methodList);
        mapperClass.setSqlTemplateList(sqlTemplateList);

        // 替换模板
        replaceSqlTemplate(mapperClass);
        return mapperClass;
    }

    /**
     * 替换掉对应的 sql 模板
     *
     * 1. sql 模板列表为空，直接返回。
     *
     * 2. 遍历每一条 sql，遍历其实是否包含 include
     * 根据 refId 找到对应的 sql，然后替换内容。
     *
     * 重新设置 sql 为替换后的 sql
     * @param mapperClass mapper 类
     * @since 0.0.11
     */
    private void replaceSqlTemplate(final MapperClass mapperClass) {
        List<MapperSqlTemplate> sqlTemplates = mapperClass.getSqlTemplateList();
        List<MapperMethod> methods = mapperClass.getMethodList();
        if(CollectionUtil.isEmpty(sqlTemplates)) {
            return;
        }
        if(CollectionUtil.isEmpty(methods)) {
            return;
        }

        for(MapperMethod mapperMethod : methods) {
            StringBuilder sqlBuffer = new StringBuilder();

            List<MapperSqlItem> sqlItems = mapperMethod.getSqlItemList();
            for(MapperSqlItem sqlItem : sqlItems) {
                if("text".equals(sqlItem.getType())) {
                    sqlBuffer.append(sqlItem.getSql()).append(" ");
                } else if("include".equals(sqlItem.getType())) {
                    String refSql = getRefIdSql(sqlTemplates, sqlItem.getRefId());
                    sqlBuffer.append(refSql).append(" ");
                }
            }

            String newSql = sqlBuffer.toString();
            mapperMethod.setSql(newSql);
        }
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

    /**
     * 获取方法的 map 信息
     *
     * TODO: 此处暂时不考虑重名的情况
     * @param namespace 命名空间
     * @return 结果
     * @since 0.0.10
     */
    private Map<String, Method> buildMethodMap(final String namespace) {
        Class clazz = ClassUtil.getClass(namespace);
        Method[] methods = clazz.getMethods();
        Map<String, Method> resultMap = new HashMap<>(methods.length);

        for(Method method : methods) {
            resultMap.put(method.getName(), method);
        }
        return resultMap;
    }

}
