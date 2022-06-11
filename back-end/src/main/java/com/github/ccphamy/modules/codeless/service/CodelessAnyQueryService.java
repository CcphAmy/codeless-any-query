package com.github.ccphamy.modules.codeless.service;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.ccphamy.common.exception.RRException;
import com.github.ccphamy.common.utils.PageUtils;
import com.github.ccphamy.modules.codeless.dto.DataSearchParamDto;
import com.github.ccphamy.modules.codeless.dto.DbTableColumnInfoDto;
import com.github.ccphamy.modules.codeless.dto.DbTableInfoDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface CodelessAnyQueryService {

    /**
     * 获取数据库表名称和注释
     * @ list
     */
    List<DbTableInfoDto> getDbTableNameList();


    /**
     * 获取数据库字段信息
     *
     * @param tableName  表名称
     */
    List<DbTableColumnInfoDto> getDbTableColumnList(String tableName);


    /**
     * 权限检查
     * @param tableName 表名
     */
    void permissionCheck(String tableName);

    /**
     * 数据返回
     *
     * @param tableName 数据库名称
     * @param search    搜索参数
     * @param page      页
     * @param column    offset
     * @return list
     */
    PageUtils getDataList(String tableName, List<DataSearchParamDto> search, Integer page, Integer limit);

    /**
     * 生成业务表
     *
     * @param tableComment  表名称
     * @param columnComment 字段备注
     * @return void
     */
    public void genTable(String tableComment, List<String> columnComment);

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
