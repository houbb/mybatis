# 项目简介

[mybatis](https://github.com/houbb/mybatis) 是一款简化版的 mybatis 实现。

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.houbb/mybatis/badge.svg)](http://mvnrepository.com/artifact/com.github.houbb/mybatis)
[![Build Status](https://www.travis-ci.org/houbb/mybatis.svg?branch=master)](https://www.travis-ci.org/houbb/mybatis?branch=master)
[![](https://img.shields.io/badge/license-Apache2-FF0080.svg)](https://github.com/houbb/mybatis/blob/master/LICENSE.txt)
[![Open Source Love](https://badges.frapsoft.com/os/v2/open-source.svg?v=103)](https://github.com/houbb/mybatis)

## 创作目的

- 学习 mybatis 的原理

- 便于拓展自己的数据库工具

# 快速开始

## 需要

- jdk 1.7+

- maven 3.x+

## maven 引入

```xml
<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>mybatis</artifactId>
    <version>0.0.16</version>
</dependency>
```

## 准备工作

- sql 建表

在 test 数据库执行下面的建表语句。

```sql
-- auto-generated definition
use test;
create table user
(
  id   int auto_increment
    primary key comment '唯一主键',
  name varchar(100) not null comment '姓名',
  password varchar(100) not null comment '密码',
  create_time char(17) comment '创建时间'
) CHARACTER SET utf8 COLLATE utf8_general_ci;

-- init
insert into user (name, password) value ('luna', '123456');
```

- 配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>

    <dataSource>
        <property name="driver" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/test"/>
        <property name="username" value="root"/>
        <property name="password" value="123456"/>
    </dataSource>

    <mappers>
        <mapper resource="mapper/UserMapper.xml"/>
    </mappers>

    <plugins>
        <plugin interceptor="com.github.houbb.mybatis.plugin.SimpleLogInterceptor"/>
    </plugins>

    <typeHandlers>
        <typeHandler javaType="java.util.Date" handler="com.github.houbb.mybatis.typehandler.DateTypeHandler"/>
    </typeHandlers>

</configuration>
```

备注：默认使用的是 mysql 5.7，如果为 8.0+，需要自行引入 jar。

## 运行测试代码

```java
public static void main(String[] args) {
    Config config = new XmlConfig("mybatis-config-5-7.xml");

    SqlSession sqlSession = new DefaultSessionFactory(config).openSession();
    UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

    User user = userMapper.selectById(1L);
    System.out.println(user);
}
```

- 输出

```
User{id=1, name='ryo', password='123456', createTime=Wed Jul 01 22:03:01 CST 2020}
```

# 拓展阅读

[从零开始手写 mybatis（一）MVP 版本](https://mp.weixin.qq.com/s/8eF7oFxgLsilqLYGOVtkGg)

[手写 mybatis 系列（二）mybatis interceptor 插件机制详解](https://mp.weixin.qq.com/s?__biz=MzUyNjE3OTAyMw==&mid=2247484124&idx=1&sn=0547e9a6535c1de74fe570a1ed1f9099&chksm=fa138d7ccd64046a12343ab655dc748a48deeb2eae87aac29f49403eaedee99ed46c4923e9e9&cur_album_id=1406621227405688832&scene=189#wechat_redirect)

[从零开始手写 mybatis （三）jdbc pool 从零实现数据库连接池](https://mp.weixin.qq.com/s?__biz=MzUyNjE3OTAyMw==&mid=2247484130&idx=1&sn=0819286a310c9d1f77e57c28ca454925&chksm=fa138d42cd6404545874def08da9870db5c8ab3f0777e310196f64ebaed40353c11cf0cae379&cur_album_id=1406621227405688832&scene=189#wechat_redirect)

# 后期 road-map

- [ ] 连接池管理

- [ ] TX 管理

- [ ] 数据库厂商标识（databaseIdProvider）

- [ ] 添加 MBG

- [ ] 添加 spring 整合实现

- [ ] 添加 spring-boot 整合实现
