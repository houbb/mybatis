package com.github.houbb.mybatis.util;

import com.github.houbb.heaven.util.io.StreamUtil;
import com.github.houbb.mybatis.exception.MybatisException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public final class XmlUtil {

    private XmlUtil(){}

    /**
     * 获取根节点
     * @param path 配置路径
     * @return 元素
     * @since 0.0.1
     */
    public static Element getRoot(final String path) {
        try {
            // 初始化数据库连接信息
            InputStream inputStream = StreamUtil.getInputStream(path);

            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            return document.getRootElement();
        } catch (DocumentException e) {
            throw new MybatisException(e);
        }
    }

}
