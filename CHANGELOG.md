# 变更日志

| 类型 | 说明 |
|:----|:----|
| A | 新增 |
| U | 更新 |
| D | 删除 |
| T | 测试 |
| O | 优化 |
| F | 修复BUG |

# release_0.0.1

| 序号 | 变更类型 | 说明 | 时间 | 备注 |
|:---|:---|:---|:---|:--|
| 1 | A | 基本功能的实现 | 2020-6-30 21:12:45 | |

# release_0.0.2

| 序号 | 变更类型 | 说明 | 时间 | 备注 |
|:---|:---|:---|:---|:--|
| 1 | A | 添加拦截器实现 | 2020-7-1 00:34:13 | |

# release_0.0.3

| 序号 | 变更类型 | 说明 | 时间 | 备注 |
|:---|:---|:---|:---|:--|
| 1 | O | 优化拦截器实现 | 2020-7-1 10:12:58 | |

# release_0.0.4

| 序号 | 变更类型 | 说明 | 时间 | 备注 |
|:---|:---|:---|:---|:--|
| 1 | A | 添加 TypeHandler 实现 | 2020-7-1 22:13:21 | |

# release_0.0.5

| 序号 | 变更类型 | 说明 | 时间 | 备注 |
|:---|:---|:---|:---|:--|
| 1 | A | 添加 TypeAlias 实现 | 2020-7-3 | |

# release_0.0.6

| 序号 | 变更类型 | 说明 | 时间 | 备注 |
|:---|:---|:---|:---|:--|
| 1 | A | 添加 ObjectFactory 实现 | 2020-7-3 | |

# release_0.0.7

| 序号 | 变更类型 | 说明 | 时间 | 备注 |
|:---|:---|:---|:---|:--|
| 1 | A | 添加 DTD 约束 | 2020-7-4 09:33:40 | |

# release_0.0.8

| 序号 | 变更类型 | 说明 | 时间 | 备注 |
|:---|:---|:---|:---|:--|
| 1 | A | 添加数据源优化 | 2020-7-7 23:01:25 | |

# release_0.0.9

| 序号 | 变更类型 | 说明 | 时间 | 备注 |
|:---|:---|:---|:---|:--|
| 1 | A | 优化字段结果集的映射 | 2020-7-7 23:01:50 | |

# release_0.0.10

| 序号 | 变更类型 | 说明 | 时间 | 备注 |
|:---|:---|:---|:---|:--|
| 1 | A | 添加对结果 List/Map 等支持 | 2020-7-8 19:45:10 | |

# release_0.0.11

| 序号 | 变更类型 | 说明 | 时间 | 备注 |
|:---|:---|:---|:---|:--|
| 1 | A | 添加 sql/include 模板引入的支持 | 2020-7-9 19:27:29 | |

# release_0.0.11

| 序号 | 变更类型 | 说明 | 时间 | 备注 |
|:---|:---|:---|:---|:--|
| 1 | A | 添加对 sql 和 include 的替换处理 | 2020-7-10 22:55:17 | |

# release_0.0.12

| 序号 | 变更类型 | 说明 | 时间 | 备注 |
|:---|:---|:---|:---|:--|
| 1 | A | 添加 ResultMap 基础映射 | 2020-7-11 16:21:57 | |

# release_0.0.13

| 序号 | 变更类型 | 说明 | 时间 | 备注 |
|:---|:---|:---|:---|:--|
| 1 | A | 添加 `${}` 的替换支持 | 2020-7-12 00:07:20 | |
| 2 | A | 初步添加 `#{}` 的替换支持 | 2020-7-12 00:07:20 | |

# release_0.0.14

| 序号 | 变更类型 | 说明 | 时间 | 备注 |
|:---|:---|:---|:---|:--|
| 1 | A | 添加 `#{}` 的多级引用 | 2020-7-12 12:19:52 | |
| 2 | A | 添加 `#{}` 的默认取第一个对象的参数 | 2020-7-12 12:19:52 | |

# release_0.0.15

| 序号 | 变更类型 | 说明 | 时间 | 备注 |
|:---|:---|:---|:---|:--|
| 1 | A | 添加 CUD 的特性支持 | 2020-7-12 14:34:33 | |

# release_0.0.16

| 序号 | 变更类型 | 说明 | 时间 | 备注 |
|:---|:---|:---|:---|:--|
| 1 | A | 添加 dynamic sql 对于 if 的支持 | 2020-7-12 20:20:26 | |

# release_0.0.17

| 序号 | 变更类型 | 说明 | 时间 | 备注 |
|:---|:---|:---|:---|:--|
| 1 | A | 添加 dynamic sql 对于 foreach 的支持 | 2020-7-12 21:24:45 | |
| 2 | F | 修复返回集合列表 | 2020-7-12 21:24:45 | |

# release_0.0.18

| 序号 | 变更类型 | 说明 | 时间 | 备注 |
|:---|:---|:---|:---|:--|
| 1 | A | 添加 transaction 支持 | 2020-7-15 22:36:47 | |

# release_0.1.0

| 序号 | 变更类型 | 说明                  | 时间                  | 备注 |
|:---|:-----|:--------------------|:--------------------|:--|
| 1  | O    | 移除代码中的 system.out   | 2023-10-22 22:36:47 | |
| 2  | O    | 调整测试环境到 mysql 8.0   | 2023-10-22 22:36:47 | |
| 3  | O    | 日志优化                | 2023-10-22 22:36:47 | |
| A  | O    | 数据源直接默认整合 jdbc-pool | 2023-10-22 22:36:47 | |