package com.github.houbb.mybatis.session;

/**
 * 数据源连接信息
 * @author binbin.hou
 * @since 0.0.1
 */
public class DataSource {

    /**
     * url 连接信息
     * @since 0.0.1
     */
    private String url;

    /**
     * 驱动信息
     * @since 0.0.1
     */
    private String driver;

    /**
     * 用户名
     * @since 0.0.1
     */
    private String username;

    /**
     * 密码
     * @since 0.0.1
     */
    private String password;

    public String url() {
        return url;
    }

    public DataSource url(String url) {
        this.url = url;
        return this;
    }

    public String driver() {
        return driver;
    }

    public DataSource driver(String driver) {
        this.driver = driver;
        return this;
    }

    public String username() {
        return username;
    }

    public DataSource username(String username) {
        this.username = username;
        return this;
    }

    public String password() {
        return password;
    }

    public DataSource password(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "DataSource{" +
                "url='" + url + '\'' +
                ", driver='" + driver + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
