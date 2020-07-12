package com.github.houbb.mybatis;

import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.config.impl.XmlConfig;
import com.github.houbb.mybatis.domain.User;
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
public class DynamicSqlTest {

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
    public void selectByIdConditionTest() {
        User user = new User();
        user.setId(2L);
        user.setName("tom");
        user.setPassword("654312");
        Date date = new Date();
        user.setCreateTime(date);
        System.out.println(userMapper.selectByIdCondition(null, null));
        System.out.println(userMapper.selectByIdCondition(1L, null));
        System.out.println(userMapper.selectByIdCondition( null, "id asc"));
        System.out.println(userMapper.selectByIdCondition(1L, "id asc"));
    }

}
