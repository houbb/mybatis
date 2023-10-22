package com.github.houbb.mybatis.config;

import com.github.houbb.heaven.util.io.FileUtil;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
@Ignore
public class TypeAliasTest {

    @Test
    public void genTest() {
        String format = "TYPE_ALIAS_MAP.put(\"%s\", %s.class.getName());";

        final String path = "D:\\github\\mybatis\\src\\test\\resources\\type.txt";

        Map<String, String> map = FileUtil.readToMap(path, " ");
        for(String key : map.keySet()) {
            String value = map.get(key);
            System.out.println(String.format(format, key, value));
        }
    }

}
