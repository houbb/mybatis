package com.github.houbb.mybatis.exception;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public class MybatisException extends RuntimeException {

    public MybatisException() {
    }

    public MybatisException(String message) {
        super(message);
    }

    public MybatisException(String message, Throwable cause) {
        super(message, cause);
    }

    public MybatisException(Throwable cause) {
        super(cause);
    }

    public MybatisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
