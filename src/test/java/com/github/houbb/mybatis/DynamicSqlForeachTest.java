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

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author binbin.hou
 * @since 0.0.17
 */
@Ignore
public class DynamicSqlForeachTest {

    private UserMapper userMapper;

    @Before
    public void initUserMapper() {
        Config config = new XmlConfig("mybatis-config-8-0.xml");

        SqlSession sqlSession = new DefaultSessionFactory(config).openSession();
        this.userMapper = sqlSession.getMapper(UserMapper.class);
    }

    /**
     * @since 0.0.17
     */
    @Test
    public void selectByIdSetTest() {
        Set<Long> set = new HashSet<>();
        set.add(1L);
        set.add(2L);
        set.add(3L);
        System.out.println(userMapper.selectByIdSet(set));
    }

}
