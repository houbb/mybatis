# spring 整合

## maven 导入

```xml
<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>${最新版本}</version>
</dependency>
```

## 使用案例

- UserService.java

```java
@Service
public class UserService {

    private static final Log log = LogFactory.getLog(UserService.class);

    @Limit(interval = 2, limit = GlobalLimitFrequency.class)
    public void limitFrequencyGlobal(final long id) {
        log.info("{}", Thread.currentThread().getName());
    }

    @Limit(interval = 2, count = 5, limit = ThreadLocalLimitCount.class)
    public void limitCountThreadLocal() {
        log.info("{}", Thread.currentThread().getName());
    }

    @Limit(interval = 2, count = 5, limit = GlobalLimitCount.class)
    public void limitCountGlobal() {
        log.info("{}", Thread.currentThread().getName());
    }

}
```

## 注解说明 

- `@Limit`

注解定义如下：

```java
public @interface Limit {

    /**
     * 时间单位, 默认为秒
     * @see TimeUnit 时间单位
     * @return 时间单位
     * @since 0.0.1
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 时间间隔
     * (1) 需要填入正整数。
     * @return 时间间隔
     * @since 0.0.1
     */
    long interval() default 1;

    /**
     * 调用次数。
     * (1) 需要填入正整数。
     * @return 调用次数
     * @since 0.0.1
     */
    int count() default 100;

    /**
     * 限制策略
     * @return 限制策略
     * @since 0.0.3
     */
    Class<? extends ILimit> limit() default LimitFrequencyFixed.class;

}
```

效果同过程式调用。

## 测试案例

[SpringConfigTest](https://github.com/houbb/mybatis/blob/master/mybatis-test/src/test/java/com/github/houbb/rate/limit/test/spring/SpringConfigTest.java)