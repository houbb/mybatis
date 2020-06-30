package com.github.houbb.mybatis;

import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.config.impl.XmlConfig;
import com.github.houbb.mybatis.domain.User;
import com.github.houbb.mybatis.mapper.UserMapper;
import com.github.houbb.mybatis.session.SqlSession;
import com.github.houbb.mybatis.session.impl.DefaultSessionFactory;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class Main {

    public static void main(String[] args) {
        Config config = new XmlConfig("mybatis-config-5-7.xml");

        SqlSession sqlSession = new DefaultSessionFactory(config).openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

}
