package com.github.houbb.mybatis;

import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.config.impl.XmlConfig;
import com.github.houbb.mybatis.domain.User;
import com.github.houbb.mybatis.mapper.UserMapper;
import com.github.houbb.mybatis.session.SqlSession;
import com.github.houbb.mybatis.session.impl.DefaultSessionFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class Main {

    private UserMapper userMapper;

    @Before
    public void initUserMapper() {
        Config config = new XmlConfig("mybatis-config-5-7.xml");

        SqlSession sqlSession = new DefaultSessionFactory(config).openSession();
        this.userMapper = sqlSession.getMapper(UserMapper.class);
    }

    @Test
    public void selectByIdTest() {
        System.out.println(userMapper.selectById(1L));
    }

    @Test
    public void selectListTest() {
        System.out.println(userMapper.selectList());
    }

    @Test
    public void selectMapTest() {
        System.out.println(userMapper.selectMap(1L));
    }

    @Test
    public void selectByIdWithResultMapTest() {
        System.out.println(userMapper.selectByIdWithResultMap(1L));
    }

}
