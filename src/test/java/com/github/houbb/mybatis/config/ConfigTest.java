package com.github.houbb.mybatis.config;

import com.github.houbb.mybatis.config.impl.XmlConfig;
import com.github.houbb.mybatis.mapper.MapperMethod;
import com.github.houbb.mybatis.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public class ConfigTest {

    @Test
    public void xmlMapperTest() {
        String path = "mybatis-config-8-0.xml";

        Config config = new XmlConfig(path);
        MapperMethod mapperMethod = config.getMapperMethod(UserMapper.class,
                "selectById");

        Assert.assertEquals("selectById", mapperMethod.getMethodName());
        Assert.assertEquals("select name, password from user where id = #{id}", mapperMethod.getSql());
    }

}
