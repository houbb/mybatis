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

    @Test
    public void xmlMapperTest() {
        String path = "mybatis-config-5-7.xml";

        Config config = new XmlConfig(path);
        MapperMethod mapperMethod = config.getMapperMethod(UserMapper.class,
                "selectById");

        System.out.println(mapperMethod.toString());
    }

}
