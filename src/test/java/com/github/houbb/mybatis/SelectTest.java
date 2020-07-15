package com.github.houbb.mybatis;

import com.github.houbb.mybatis.config.Config;
import com.github.houbb.mybatis.config.impl.XmlConfig;
import com.github.houbb.mybatis.domain.User;
import com.github.houbb.mybatis.domain.UserExample;
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
public class SelectTest {

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

    /**
     * @since 0.0.13
     */
    @Test
    public void selectWithReplaceTest() {
        System.out.println(userMapper.selectWithReplace("id asc"));
    }

    /**
     * @since 0.0.13
     */
    @Test
    public void selectByIdWithReplaceTest() {
        System.out.println(userMapper.selectByIdWithReplace(1L, "id asc"));
    }

    /**
     * @since 0.0.13
     */
    @Test
    public void selectByIdAndNameTest() {
        System.out.println(userMapper.selectByIdAndName(1L, "ryo"));
//        System.out.println(userMapper.selectByIdAndName(1L, "ryo2"));
    }

    /**
     * @since 0.0.14
     */
    @Test
    public void selectByIdPlaceHolderTest() {
        System.out.println(userMapper.selectByIdPlaceHolder(1L));
    }

    /**
     * @since 0.0.14
     */
    @Test
    public void selectByExampleTest() {
        UserExample userExample = new UserExample();
        userExample.setId(1L);
        System.out.println(userMapper.selectByExample(userExample));
    }

    /**
     * @since 0.0.14
     */
    @Test
    public void selectByExampleOrderByTest() {
        UserExample userExample = new UserExample();
        userExample.setId(1L);
        System.out.println(userMapper.selectByExampleOrderBy(userExample, "id asc"));
    }

}
