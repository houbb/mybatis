package com.github.houbb.mybatis.support;

import com.github.houbb.mybatis.domain.User;
import com.github.houbb.mybatis.support.factory.impl.DefaultObjectFactory;

import java.util.Date;

/**
 * @author binbin.hou
 * @since 0.0.6
 */
public class MyObjectFactory extends DefaultObjectFactory {

    @Override
    public <T> T newInstance(Class<T> type) {
        if(User.class.equals(type)) {
            // 特殊处理
            User user = new User();
            user.setCreateTime(new Date());
            return (T) user;
        }

        return super.newInstance(type);
    }
}
