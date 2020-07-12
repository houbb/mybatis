package com.github.houbb.mybatis.support.replace.impl;

import com.github.houbb.heaven.annotation.CommonEager;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassTypeUtil;
import com.github.houbb.mybatis.constant.MapperForeachConst;
import com.github.houbb.mybatis.constant.MapperSqlConst;
import com.github.houbb.mybatis.constant.enums.MapperSqlType;
import com.github.houbb.mybatis.exception.MybatisException;
import com.github.houbb.mybatis.mapper.MapperSqlItem;
import com.github.houbb.mybatis.mapper.component.MapperForeachProperty;
import com.github.houbb.mybatis.support.replace.ISqlReplace;
import com.github.houbb.mybatis.support.replace.SqlReplaceResult;

import java.util.*;

/**
 * foreach 循环的处理
 * <p>
 * 1. array
 * 2. list
 * 3. map
 * 4. set
 *
 * @since 0.0.17
 */
public class ForeachSqlReplace implements ISqlReplace {

    @Override
    public SqlReplaceResult replace(SqlReplaceResult sqlReplaceResult) {
        //1. 基本信息
        Map<String, Object> paramMap = sqlReplaceResult.paramMap();
        List<MapperSqlItem> sqlItemList = sqlReplaceResult.dynamicSqlItems();

        //2. 遍历处理
        for (MapperSqlItem sqlItem : sqlItemList) {
            if (MapperSqlType.FOREACH.equals(sqlItem.getType())) {
                // status in (?, ?, ?)
                // values (?, ?, ?), (?, ?, ?)
                // 就是直接对此处的信息，进行多次迭代循环处理
                MapperForeachProperty property = sqlItem.getForeachProperty();
                String collectionName = property.collection();

                Object collection = paramMap.get(collectionName);
                // 获取对应的 size
                if (null == collection) {
                    throw new MybatisException("Not found match collection for name: " + collectionName);
                }
                int size = getCollectionSize(collection);

                //1. 对于别名的处理
                // item 替换为 collection 名称
                String originalSql = sqlItem.getSql();

                StringBuilder sqlBuffer = new StringBuilder();
                sqlBuffer.append(property.open());
                List<String> sqlList = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    String newSql = buildSql(i, property, originalSql);
                    sqlList.add(newSql);
                }

                String allSql = StringUtil.join(sqlList, property.separator());
                sqlBuffer.append(allSql);
                sqlBuffer.append(property.close());

                sqlItem.setSql(sqlBuffer.toString());
                sqlItem.setReadyForSql(true);
            }
        }

        //3. 返回
        return sqlReplaceResult;
    }

    /**
     * 构建单个的 sql 信息
     *
     * @param index    下标
     * @param property 属性
     * @param sql      原始的 sql 属性
     * @return 结果
     * @since 0.0.17
     */
    private String buildSql(final int index,
                            final MapperForeachProperty property,
                            final String sql) {
        //2. 替换 item 名称
        StringBuilder sqlBuffer = new StringBuilder();
        // 这里定义相对特殊的 key
        // 占位符替换的时候，坐下对应的处理就行。
        Stack<Character> replaceStack = new Stack<>();
        Stack<Character> psStack = new Stack<>();
        StringBuilder psBuffer = new StringBuilder();
        StringBuilder replaceBuffer = new StringBuilder();
        for (int i = 0; i < sql.length(); i++) {
            char c = sql.charAt(i);
            if (c == '$') {
                replaceStack.push(c);
                // 判断下一个字符
                c = sql.charAt(i + 1);
                if (c != '{') {
                    throw new MybatisException("$ must next with char {, but found " + c);
                }
                i++;
                continue;
            }
            if (c == '#') {
                psStack.push(c);
                // 判断下一个字符
                c = sql.charAt(i + 1);
                if (c != '{') {
                    throw new MybatisException("# must next with char {, but found " + c);
                }
                i++;
                continue;
            }

            if (c == '}') {
                if (!psStack.isEmpty()) {
                    psStack.pop();
                    String ps = psBuffer.toString();
                    String psPlaceholder = buildPlaceHolder(property, index, ps);
                    sqlBuffer.append(MapperSqlConst.PS_PREFIX)
                            .append(psPlaceholder)
                            .append(MapperSqlConst.PS_SUFFIX);

                    psBuffer = new StringBuilder();
                    continue;
                }
                if (!replaceStack.isEmpty()) {
                    replaceStack.pop();

                    String replace = replaceBuffer.toString();
                    String replacePlaceholder = buildPlaceHolder(property, index, replace);
                    sqlBuffer.append(MapperSqlConst.REPLACE_PREFIX)
                            .append(replacePlaceholder)
                            .append(MapperSqlConst.REPLACE_SUFFIX);

                    // 重置
                    replaceBuffer = new StringBuilder();
                    continue;
                }

                throw new MybatisException("char } must start with ${ or #{ !");
            }

            // 判断当前字符
            if (!replaceStack.isEmpty()) {
                replaceBuffer.append(c);
            } else if (!psStack.isEmpty()) {
                psBuffer.append(c);
            } else {
                sqlBuffer.append(c);
            }
        }
        if (!psStack.isEmpty()) {
            throw new MybatisException("#{ not found } for enclosure!");
        }
        if (!replaceStack.isEmpty()) {
            throw new MybatisException("${ not found } for enclosure!");
        }

        return sqlBuffer.toString();
    }

    /**
     * 构建占位符信息
     *
     * 1. 名称必须为 itemName 或者 indexName
     * 2. itemName.xxx 替换为 collectionName.xxx:item:2
     * 3. indexName 替换为 collectionName:index:2
     * @param property 属性
     * @return 结果
     * @since 0.0.17
     */
    private String buildPlaceHolder(final MapperForeachProperty property,
                                    final int index,
                                    final String placeholder) {
        // 基本属性
        String itemName = property.item();
        String indexName = property.index();
        String collectionName = property.collection();

        //结果构建
        String[] strings = placeholder.split("\\.");
        String firstName = strings[0];
        if(firstName.equals(itemName)) {
            strings[0] = collectionName;

            return StringUtil.join(strings, ".")
                    + MapperForeachConst.SPLITTER
                    + MapperForeachConst.ITEM
                    + MapperForeachConst.SPLITTER
                    + index;
        }
        // 索引暂时不考虑特别丰富的场景
        if(firstName.equals(indexName)) {
            // 主要考虑到 map.key
            return collectionName
                    + MapperForeachConst.SPLITTER
                    + MapperForeachConst.INDEX
                    + MapperForeachConst.SPLITTER
                    + index;
        }
        throw new MybatisException("Foreach placeholder must start with ("+itemName
                +","+indexName+"), but found: " + placeholder);
    }

    /**
     * 获取集合的大小
     *
     * @param collection 集合
     * @return 结果
     * @since 0.0.17
     */
    @CommonEager
    private int getCollectionSize(Object collection) {
        Class<?> clazz = collection.getClass();

        if (ClassTypeUtil.isArray(clazz)) {
            Object[] array = (Object[]) collection;
            return array.length;
        }
        if (ClassTypeUtil.isSet(clazz)) {
            Set<?> set = (Set<?>) collection;
            return set.size();
        }
        if (ClassTypeUtil.isList(clazz)) {
            List<?> list = (List<?>) collection;
            return list.size();
        }
        if (ClassTypeUtil.isMap(clazz)) {
            Map<?, ?> map = (Map<?, ?>) collection;
            return map.size();
        }

        throw new MybatisException("Only support list, set, array, map. But found: " + clazz.getName());
    }

}
