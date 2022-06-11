package com.github.ccphamy.modules.codeless.dao;

import com.github.ccphamy.modules.codeless.dto.DataSearchParamDto;
import com.github.ccphamy.modules.codeless.dto.DbTableColumnInfoDto;
import com.github.ccphamy.modules.codeless.dto.DbTableInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CodelessAnyQueryDao {


    /**
     * 获取数据库表名称和注释
     *
     * @param schemaName 数据库
     * @param prefix     前缀
     */
    List<DbTableInfoDto> getDbTableNameList(@Param("schemaName") String schemaName, @Param("prefix") String prefix);


    /**
     * 获取数据库字段信息
     *
     * @param schemaName 数据库
     * @param tableName  表名称
     */
    List<DbTableColumnInfoDto> getDbTableColumnList(@Param("schemaName") String schemaName, @Param("tableName") String tableName);


    /**
     * 数据返回
     *
     * @param tableName 数据库名称
     * @return list
     */
    public List<Map<String, Object>> getDataList(@Param("tableName") String tableName,  @Param("search") List<DataSearchParamDto> search, @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 数据返回
     *
     * @param tableName 数据库名称
     * @param seach 搜索参数
     * @return list
     */
    public Long getDataListSize(@Param("tableName") String tableName, @Param("search") List<DataSearchParamDto> search);


    /**
     * 生成业务表
     *
     * @param tableName     表名
     * @param columnComment 字段备注
     * @return void
     */
    public void genTable(@Param("tableName") String tableName, @Param("tableComment") String tableComment, @Param("columnComment") List<String> columnComment);

    /**
     * 删除数据库
     *
     * @param tableName 表名
     */
    public void dropTable(@Param("tableName") String tableName);

    /**
     * 插入数据
     *
     * @param tableName 表名
     * @param data      数据
     */
    public void insertData(@Param("tableName") String tableName, @Param("data") Map<String, Object> data);


    /**
     * 删除数据
     *
     * @param tableName 表格名称
     * @param ids       ids
     */
    public void removeByIds(@Param("tableName") String tableName, @Param("ids") List<Long> ids);

}