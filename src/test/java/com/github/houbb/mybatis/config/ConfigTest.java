package com.github.houbb.mybatis.config;

import com.github.houbb.mybatis.config.impl.XmlConfig;
import com.github.houbb.mybatis.mapper.MapperMethod;
import com.github.houbb.mybatis.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public class ConfigTest {

//    @Test
//    public void propertiesTest() {
//        String path = "mybatis-config-5-7.properties";
//
//        Config config = new PropertiesConfig(path);
//        DataSource dataSource = config.getDataSource();
//
//        Assert.assertEquals("DataSource{url='jdbc:mysql://localhost:3306/test', driver='com.mysql.jdbc.Driver', username='root', password='123456'}", dataSource.toString());
//    }


    @Test
    public void xmlTest() {
        String path = "mybatis-config-5-7.xml";

        Config config = new XmlConfig(path);
        Map<String, String> dataSource = config.getDataSourceConfig();

        Assert.assertEquals("DataSource{url='jdbc:mysql://localhost:3306/test', driver='com.mysql.jdbc.Driver', username='root', password='123456'}", dataSource.toString());
    }

    @Test
    public void xmlMapperTest() {
        String path = "mybatis-config-5-7.xml";

        Config config = new XmlConfig(path);
        MapperMethod mapperMethod = config.getMapperMethod(UserMapper.class,
                "selectById");

        Assert.assertEquals("MapperMethod{type='select', sql='select * from user where id = ?', methodName='selectById', resultType=class com.github.houbb.mybatis.domain.User, paramType=class java.lang.Long}", mapperMethod.toString());
    }

}
