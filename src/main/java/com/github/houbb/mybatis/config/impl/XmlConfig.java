package com.github.houbb.mybatis.config.impl;

import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.mybatis.constant.enums.TransactionIsolationLevel;
import com.github.houbb.mybatis.datasource.unpooled.UnPooledDataSource;
import com.github.houbb.mybatis.exception.MybatisException;
import com.github.houbb.mybatis.handler.type.handler.TypeHandler;
import com.github.houbb.mybatis.handler.type.register.TypeHandlerRegister;
import com.github.houbb.mybatis.handler.type.register.impl.DefaultTypeHandlerRegister;
import com.github.houbb.mybatis.mapper.MapperMethod;
import com.github.houbb.mybatis.mapper.MapperRegister;
import com.github.houbb.mybatis.plugin.Interceptor;
import com.github.houbb.mybatis.support.alias.TypeAliasRegister;
import com.github.houbb.mybatis.support.alias.impl.DefaultTypeAliasRegister;
import com.github.houbb.mybatis.support.factory.ObjectFactory;
import com.github.houbb.mybatis.support.factory.impl.DefaultObjectFactory;
import com.github.houbb.mybatis.transaction.Transaction;
import com.github.houbb.mybatis.transaction.impl.JdbcTransaction;
import com.github.houbb.mybatis.transaction.impl.ManageTransaction;
import com.github.houbb.mybatis.util.XmlUtil;
import org.dom4j.Element;

import javax.sql.DataSource;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.*;

/**
 * 配置信息
 *
 * @author binbin.hou
 * @since 0.0.1
 */
public class XmlConfig extends ConfigAdaptor {

    /**
     * 文件配置路径
     *
     * @since 0.0.1
     */
    private final String configPath;

    /**
     * 配置文件信息
     *
     * @since 0.0.1
     */
    private Element root;

    /**
     * 数据源信息
     *
     * @since 0.0.8
     */
    private DataSource dataSource;

    /**
     * 事务管理器
     * @since 0.0.18
     */
    private Transaction transaction;

    /**
     * mapper 注册类
     *
     * @since 0.0.1
     */
    private final MapperRegister mapperRegister = new MapperRegister(this);

    /**
     * 拦截列表
     * @since 0.0.3
     */
    private final List<Interceptor> interceptorList = new ArrayList<>();

    /**
     * 类处理注册器
     * @since 0.0.4
     */
    private final TypeHandlerRegister typeHandlerRegister = new DefaultTypeHandlerRegister();

    /**
     * 别称注册类
     * @since 0.0.5
     */
    private final TypeAliasRegister typeAliasRegister = new DefaultTypeAliasRegister();

    /**
     * 对象工厂类
     *
     * @since 0.0.6
     */
    private ObjectFactory objectFactory = new DefaultObjectFactory();

    public XmlConfig(String configPath) {
        this.configPath = configPath;

        // 配置初始化
        initProperties();

        // 初始化数据连接信息
        initDataSource();

        // 初始化事务管理器
        initTransaction();

        // 初始化对象工厂类
        initObjectFactory();

        // 设置类型别称
        initTypeAlias();

        // mapper 信息
        initMapper();

        // 拦截器
        initInterceptorList();

        // 类型处理器
        initTypeHandler();
    }

    @Override
    public List<Interceptor> getInterceptorList() {
        return this.interceptorList;
    }

    @Override
    public Connection getConnection() {
        return this.transaction.getConnection();
    }

    @Override
    public MapperMethod getMapperMethod(Class clazz, String methodName) {
        return this.mapperRegister.getMapperMethod(clazz, methodName);
    }

    @Override
    @SuppressWarnings("all")
    public <T> TypeHandler<T> getTypeHandler(Class<T> javaType) {
        return (TypeHandler<T>) this.typeHandlerRegister.getTypeHandler(javaType);
    }

    @Override
    public String getTypeAlias(String alias) {
        return this.typeAliasRegister.getTypeOrDefault(alias);
    }

    @Override
    public <T> T newInstance(Class<T> tClass) {
        return this.objectFactory.newInstance(tClass);
    }

    /**
     * 初始化对象工厂类
     * @since 0.0.6
     */
    private void initObjectFactory() {
        Element objectFactory = root.element("objectFactory");

        if(objectFactory != null) {
            String type = objectFactory.attributeValue("type");

            Class<?> clazz = ClassUtil.getClass(type);
            this.objectFactory = (ObjectFactory) ClassUtil.newInstance(clazz);
        }
    }

    /**
     * 初始化配置文件信息
     *
     * @since 0.0.1
     */
    private void initProperties() {
        this.root = XmlUtil.getRoot(configPath);
    }

    /**
     * 初始化数据源
     *
     * @since 0.0.8
     */
    private void initDataSource() {
        // 根据配置初始化连接信息
        Element dsElem = root.element("dataSource");

        Properties properties = new Properties();

        for (Object property : dsElem.elements("property")) {
            Element element = (Element) property;
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");

            final String key = "jdbc."+name;
            properties.setProperty(key, value);
        }

        // 反射构建对象
        String type = dsElem.attributeValue("type");
        if(StringUtil.isEmpty(type)) {
            // 后期可以调整为 pooled 实现
            type = UnPooledDataSource.class.getName();
        }

        Class<?> dataSourceClass = ClassUtil.getClass(type);
        try {
            Constructor<?> constructor = dataSourceClass.getConstructor(Properties.class);
            this.dataSource = (DataSource) constructor.newInstance(properties);
        } catch (NoSuchMethodException | IllegalAccessException
                | InstantiationException | InvocationTargetException e) {
            throw new MybatisException("DataSource class must has public constructor with args properties!");
        }
    }

    /**
     * 初始化事务管理器
     *
     * @since 0.0.18
     */
    private void initTransaction() {
        // 根据配置初始化连接信息
        Element dsElem = root.element("transaction");
        String type = dsElem.attributeValue("type");

        Map<String, String> map = new HashMap<>();
        for (Object property : dsElem.elements("property")) {
            Element element = (Element) property;
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");

            map.put(name, value);
        }

        TransactionIsolationLevel level = TransactionIsolationLevel.READ_COMMITTED;
        boolean autoCommit = "true".equals(map.get("autoCommit"));
        String isolationLevelStr = map.get("isolationLevel");
        if(StringUtil.isNotEmpty(isolationLevelStr)) {
            level = TransactionIsolationLevel.valueOf(isolationLevelStr);
        }

        // jdbc
        if("jdbc".equals(type)) {
            this.transaction = new JdbcTransaction(dataSource, level, autoCommit);
        } else {
            this.transaction = new ManageTransaction(dataSource, level);
        }
    }

    /**
     * 初始化类型别称
     * @since 0.0.5
     */
    private void initTypeAlias() {
        Element mappers = root.element("typeAliases");

        // 遍历所有需要初始化的 mapper 文件路径
        if(ObjectUtil.isNull(mappers)) {
            return;
        }

        for (Object item : mappers.elements("typeAlias")) {
            Element mapper = (Element) item;
            String alias = mapper.attributeValue("alias");
            String type = mapper.attributeValue("type");

            typeAliasRegister.register(alias, type);
        }
    }

    /**
     * 初始化 mapper 信息
     *
     * @since 0.0.1
     */
    private void initMapper() {
        Element mappers = root.element("mappers");

        if(ObjectUtil.isNull(mappers)) {
            return;
        }
        // 遍历所有需要初始化的 mapper 文件路径
        for (Object item : mappers.elements("mapper")) {
            Element mapper = (Element) item;
            String path = mapper.attributeValue("resource");
            mapperRegister.addMapper(path);
        }
    }

    /**
     * 初始化拦截器列表
     *
     * 1. 暂时不支持添加属性
     * @since 0.0.2
     */
    private void initInterceptorList() {
        Element mappers = root.element("plugins");

        if(ObjectUtil.isNull(mappers)) {
            return;
        }

        // 遍历所有需要初始化的 mapper 文件路径
        for (Object item : mappers.elements("plugin")) {
            Element mapper = (Element) item;
            String value = mapper.attributeValue("interceptor");
            Class clazz = ClassUtil.getClass(value);

            // 创建拦截器实例
            Interceptor interceptor = (Interceptor) this.newInstance(clazz);
            interceptorList.add(interceptor);
        }
    }

    /**
     * 初始化类型处理类
     *
     * TODO: 这些类似的实现可以抽取为更加简单的方法实现
     *
     * 后续这里可以优化，自己反射获取对应的类型。
     * @since 0.0.4
     */
    @SuppressWarnings("all")
    private void initTypeHandler() {
        Element mappers = root.element("typeHandlers");

        if(ObjectUtil.isNull(mappers)) {
            return;
        }

        // 遍历所有需要初始化的 mapper 文件路径
        for (Object item : mappers.elements("typeHandler")) {
            Element mapper = (Element) item;
            String javaTypeClassName = mapper.attributeValue("javaType");
            // 处理别名
            String fullTypeClassName = getTypeAlias(javaTypeClassName);
            Class javaTypeClass = ClassUtil.getClass(fullTypeClassName);

            String handlerClassName = mapper.attributeValue("handler");
            Class handlerClass = ClassUtil.getClass(handlerClassName);

            // 创建拦截器实例
            TypeHandler typeHandler = (TypeHandler) this.newInstance(handlerClass);
            typeHandlerRegister.register(javaTypeClass, typeHandler);
        }
    }

}
