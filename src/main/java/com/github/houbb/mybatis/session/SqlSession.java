package com.github.houbb.mybatis.session;

import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.mapper.MapperMethod;

import java.io.Closeable;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public interface SqlSession extends Closeable {

    /**
     * 查询单个
     * @param mapperMethod 方法
     * @param args 参数
     * @param <T> 泛型
     * @return 结果
     * @since 0.0.1
     */
    <T> T selectOne(final MapperMethod mapperMethod, Object[] args);

    /**
     * Execute an insert statement with the given parameter object. Any generated
     * autoincrement values or selectKey entries will modify the given parameter
     * object properties. Only the number of rows affected will be returned.
     * @param mapperMethod Unique identifier matching the statement to execute.
     * @param args A parameter object to pass to the statement.
     * @return int The number of rows affected by the insert.
     * @since 0.0.15
     */
    int insert(final MapperMethod mapperMethod, Object[] args);

    /**
     * Execute an update statement. The number of rows affected will be returned.
     * @param mapperMethod Unique identifier matching the statement to execute.
     * @param args A parameter object to pass to the statement.
     * @return int The number of rows affected by the update.
     * @since 0.0.15
     */
    int update(final MapperMethod mapperMethod, Object[] args);

    /**
     * Execute a delete statement. The number of rows affected will be returned.
     * @param mapperMethod Unique identifier matching the statement to execute.
     * @param args A parameter object to pass to the statement.
     * @return int The number of rows affected by the delete.
     * 0.0.15
     */
    int delete(final MapperMethod mapperMethod, Object[] args);

    /**
     * Retrieves a mapper.
     * @param <T> the mapper type
     * @param type Mapper interface class
     * @return a mapper bound to this SqlSession
     * @since 0.0.1
     */
    <T> T getMapper(Class<T> type);

    /**
     * 获取配置信息
     * @return 配置
     * @since 0.0.1
     */
    Config getConfig();

}
