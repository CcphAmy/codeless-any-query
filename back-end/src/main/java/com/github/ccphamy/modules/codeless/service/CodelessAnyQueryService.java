package com.github.ccphamy.modules.codeless.service;

import com.github.ccphamy.common.utils.PageUtils;
import com.github.ccphamy.modules.codeless.dto.DbTableColumnInfoDto;
import com.github.ccphamy.modules.codeless.dto.DbTableInfoDto;

import java.util.List;
import java.util.Map;

public interface CodelessAnyQueryService {

    /**
     * 获取数据库表名称和注释
     *
     * @param schemaName 数据库
     * @param prefix     前缀
     * @ list
     */
    List<DbTableInfoDto> getDbTableNameList(String schemaName, String prefix);


    /**
     * 获取数据库字段信息
     *
     * @param schemaName 数据库
     * @param tableName  表名称
     */
    List<DbTableColumnInfoDto> getDbTableColumnList(String schemaName, String tableName);

    /**
     * 数据返回
     *
     * @param tableName 数据库名称
     * @return list
     */
    PageUtils getDataList(String tableName, Integer page, Integer limit);

    /**
     * 生成业务表
     *
     * @param tableName     表名
     * @param tableComment  表名称
     * @param columnComment 字段备注
     * @return void
     */
    public void genTable(String tableName, String tableComment, List<String> columnComment);

    /**
     * 删除数据库
     *
     * @param tableName 表名
     */
    void dropTable(String tableName);


    /**
     * 导入数据
     *
     * @param dataList 数据列表
     * @return 结果
     */
    String importData(List<Map<String, Object>> list, String tableName);

    /**
     * 删除数据
     *
     * @param tableName 表格名称
     * @param ids       ids
     */
    void removeByIds(String tableName, List<Long> ids);
}
