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
    <version>0.0.4</version>
</dependency>
```

## 准备工作

- sql 建表

在 test 数据库执行下面的建表语句。

```sql
create table user
(
  id   int auto_increment
    primary key comment '唯一主键',
  name varchar(100) not null comment '姓名',
  password varchar(100) not null comment '密码',
  create_time char(17) not null comment '创建时间'
) CHARACTER SET utf8 COLLATE utf8_general_ci;


insert into user (name, password, create_time) value ('ryo', '123456', '20200701220301000');
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

# 后期 road-map

- [ ] typeAlias 实现

- [ ] 对象工厂支持

- [ ] 连接池 管理

- [ ] TX 管理

- [ ] 数据库厂商标识（databaseIdProvider）

- [ ] xml DTD 添加，便于使用

- [ ] 添加 MBG

- [ ] 添加 spring 整合实现