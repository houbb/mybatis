package com.github.houbb.mybatis.session;

import com.github.houbb.mybatis.config.Config;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public interface SqlSessionFactory {

    /**
     * 打开一个 session
     * @return session
     * @since 0.0.1
     */
    SqlSession openSession();

    /**
     * 获取配置信息
     * @return 配置信息
     * @since 0.0.1
     */
    Config getConfig();

}
