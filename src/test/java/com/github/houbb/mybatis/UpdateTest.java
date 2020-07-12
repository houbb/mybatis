package com.github.houbb.mybatis;

import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.config.impl.XmlConfig;
import com.github.houbb.mybatis.domain.User;
import com.github.houbb.mybatis.domain.UserExample;
import com.github.houbb.mybatis.mapper.UserMapper;
import com.github.houbb.mybatis.session.SqlSession;
import com.github.houbb.mybatis.session.impl.DefaultSessionFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;

/**
 * @author binbin.hou
 * @since 0.0.15
 */
@Ignore
public class UpdateTest {

    private UserMapper userMapper;

    @Before
    public void initUserMapper() {
        Config config = new XmlConfig("mybatis-config-5-7.xml");

        SqlSession sqlSession = new DefaultSessionFactory(config).openSession();
        this.userMapper = sqlSession.getMapper(UserMapper.class);
    }

    /**
     * @since 0.0.15
     */
    @Test
    public void insertUserTest() {
        User user = new User();
        user.setName("tom");
        user.setPassword("654312");
        Date date = new Date();
        user.setCreateTime(date);
        System.out.println(userMapper.insertUser(user));
    }

    /**
     * @since 0.0.15
     */
    @Test
    public void updateUserPasswordTest() {
        System.out.println(userMapper.updateUserPassword(1L, "789456"));
    }

    /**
     * @since 0.0.15
     */
    @Test
    public void deleteUserByIdTest() {
        System.out.println(userMapper.deleteUserById(1L));
    }

}
