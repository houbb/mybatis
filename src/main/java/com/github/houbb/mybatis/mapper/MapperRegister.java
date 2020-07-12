package com.github.houbb.mybatis.mapper;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.constant.MapperAttrConst;
import com.github.houbb.mybatis.constant.MapperTypeConst;
import com.github.houbb.mybatis.constant.enums.MapperSqlType;
import com.github.houbb.mybatis.exception.MybatisException;
import com.github.houbb.mybatis.mapper.component.MapperResultMapItem;
import com.github.houbb.mybatis.support.replace.ISqlReplace;
import com.github.houbb.mybatis.support.replace.SqlReplaceResult;
import com.github.houbb.mybatis.support.replace.impl.IncludeRefSqlReplace;
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
        Class<?> clazz = ClassUtil.getClass(namespace);

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
        String namespace = root.attributeValue(MapperAttrConst.NAMESPACE);
        List<?> list = root.elements();
        Map<String, Method> methodMap = buildMethodMap(namespace);

        List<MapperMethod> methodList = new ArrayList<>();
        List<MapperSqlTemplate> sqlTemplateList = new ArrayList<>();
        Map<String, List<MapperResultMapItem>> resultMapMapping = new HashMap<>();
        // 遍历下面的所有元素
        for(Object item : list) {
            Element element = (Element) item;
            MapperMethod mapperMethod = new MapperMethod();
            // 设置依赖的 class
            mapperMethod.setRefClass(mapperClass);

            String type = element.getName();
            mapperMethod.setType(type);
            //select
            String id = element.attributeValue(MapperAttrConst.ID);

            //sql
            if(MapperTypeConst.SQL.equals(type)) {
                MapperSqlTemplate sqlTemplate = new MapperSqlTemplate();
                sqlTemplate.setId(id);
                sqlTemplate.setSql(element.getTextTrim());
                sqlTemplateList.add(sqlTemplate);
            } else if(MapperTypeConst.RESULT_MAP.equals(type)) {
                List<MapperResultMapItem> mapItems = buildResultMapItems(element);
                resultMapMapping.put(id, mapItems);
            } else if(MapperTypeConst.SELECT.equals(type)) {
                mapperMethod.setMethodName(id);
                Method method = methodMap.get(id);
                mapperMethod.setMethod(method);

                String paramType = element.attributeValue(MapperAttrConst.PARAM_TYPE);
                String resultType = element.attributeValue(MapperAttrConst.RESULT_TYPE);
                String resultMap = element.attributeValue(MapperAttrConst.RESULT_MAP);
                if(StringUtil.isNotEmpty(paramType)) {
                    // 入参可能不存在
                    mapperMethod.setParamType(ClassUtil.getClass(config.getTypeAlias(paramType)));
                }
                if(StringUtil.isNotEmpty(resultType)) {
                    // 出参可能不存在
                    mapperMethod.setResultType(ClassUtil.getClass(config.getTypeAlias(resultType)));
                }

                mapperMethod.setResultMap(resultMap);

                // 构建 sql 信息
                List<MapperSqlItem> sqlItemList = buildSqlItems(element);
                mapperMethod.setSqlItemList(sqlItemList);
                // 这个是暂时的 sql
                mapperMethod.setSql(element.getTextTrim());

                methodList.add(mapperMethod);
            } else if(MapperTypeConst.UPDATE.equals(type)
                || MapperTypeConst.DELETE.equals(type)
                || MapperTypeConst.INSERT.equals(type)) {
                mapperMethod.setMethodName(id);
                Method method = methodMap.get(id);
                mapperMethod.setMethod(method);

                String paramType = element.attributeValue(MapperAttrConst.PARAM_TYPE);
                if(StringUtil.isNotEmpty(paramType)) {
                    mapperMethod.setParamType(ClassUtil.getClass(config.getTypeAlias(paramType)));
                }

                // 构建 sql 信息
                List<MapperSqlItem> sqlItemList = buildSqlItems(element);
                mapperMethod.setSqlItemList(sqlItemList);
                // 这个是暂时的 sql
                mapperMethod.setSql(element.getTextTrim());

                methodList.add(mapperMethod);
            }
        }

        mapperClass.setNamespace(namespace);
        mapperClass.setMethodList(methodList);
        mapperClass.setSqlTemplateList(sqlTemplateList);
        mapperClass.setResultMapMapping(resultMapMapping);

        // 替换模板
        replaceSqlTemplate(mapperClass);
        return mapperClass;
    }

    /**
     * 构建对应的结果映射元素列表
     * @param element 当前元素
     * @return 结果列表
     * @since 0.0.12
     */
    private List<MapperResultMapItem> buildResultMapItems(final Element element) {
        List<?> itemDocs = element.elements(MapperTypeConst.RESULT);
        List<MapperResultMapItem> mapItems = new ArrayList<>(itemDocs.size());

        for(Object doc : itemDocs) {
            DefaultElement elem = (DefaultElement) doc;

            MapperResultMapItem mapItem = new MapperResultMapItem();
            mapItem.setColumn(elem.attributeValue(MapperAttrConst.COLUMN));
            mapItem.setProperty(elem.attributeValue(MapperAttrConst.PROPERTY));
            mapItem.setJavaType(elem.attributeValue(MapperAttrConst.JAVA_TYPE));
            mapItem.setJdbcType(elem.attributeValue(MapperAttrConst.JDBC_TYPE));
            mapItem.setTypeHandler(elem.attributeValue(MapperAttrConst.TYPE_HANDLER));
            mapItems.add(mapItem);
        }

        return mapItems;
    }

    /**
     * 构建 sql 元素列表
     * @param element 元素
     * @return 结果
     * @since 0.0.12
     */
    private List<MapperSqlItem> buildSqlItems(Element element) {
        // 使用 content 替代简单的文本
        List<?> contentList = element.content();
        List<MapperSqlItem> sqlItemList = new ArrayList<>();
        for(Object content : contentList) {
            MapperSqlItem sqlItem = new MapperSqlItem();
            if(content instanceof DefaultText) {
                DefaultText defaultText = (DefaultText)content;
                // 纯文本是可以直接拼接的
                sqlItem.setReadyForSql(true);
                sqlItem.setType(MapperSqlType.TEXT);
                sqlItem.setSql(defaultText.getText().trim());
            } else if(content instanceof DefaultElement) {
                DefaultElement defaultElement = (DefaultElement)content;
                String type = defaultElement.getName();
                if(MapperTypeConst.INCLUDE.equals(type)) {
                    // 这里后期会有更加复杂的处理，暂时只考虑简单的 refId
                    // 后期如果引入动态 SQL，则需要做更加细化的处理。
                    sqlItem.setType(MapperSqlType.INCLUDE);
                    sqlItem.setRefId(defaultElement.attributeValue(MapperAttrConst.REF_ID));
                }
                if(MapperTypeConst.IF.equals(type)) {
                    sqlItem.setType(MapperSqlType.IF);
                    sqlItem.setTestCondition(defaultElement.attributeValue(MapperAttrConst.TEST));
                }
            }

            sqlItemList.add(sqlItem);
        }

        return sqlItemList;
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
            ISqlReplace sqlReplace = new IncludeRefSqlReplace();

            // 入参
            SqlReplaceResult in = SqlReplaceResult.newInstance()
                    .mapperMethod(mapperMethod);

            // 出参
            // sql item 列表在方法中被重新设置
            sqlReplace.replace(in);
        }
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
        Class<?> clazz = ClassUtil.getClass(namespace);
        Method[] methods = clazz.getMethods();
        Map<String, Method> resultMap = new HashMap<>(methods.length);

        for(Method method : methods) {
            resultMap.put(method.getName(), method);
        }
        return resultMap;
    }

}
