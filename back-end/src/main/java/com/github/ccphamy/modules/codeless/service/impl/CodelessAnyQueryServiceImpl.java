package com.github.ccphamy.modules.codeless.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.ccphamy.common.exception.RRException;
import com.github.ccphamy.common.utils.PageUtils;
import com.github.ccphamy.common.utils.R;
import com.github.ccphamy.modules.codeless.dao.CodelessAnyQueryDao;
import com.github.ccphamy.modules.codeless.dto.DataSearchParamDto;
import com.github.ccphamy.modules.codeless.dto.DbTableColumnInfoDto;
import com.github.ccphamy.modules.codeless.dto.DbTableInfoDto;
import com.github.ccphamy.modules.codeless.service.CodelessAnyQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class CodelessAnyQueryServiceImpl implements CodelessAnyQueryService {


    private static final String SCHEMA_NAME = "any_query";

    private final static String PREFIX_SOURCE_NAME = "custom_table_";


    @Resource
    private CodelessAnyQueryDao noCodeTableDao;

    /**
     * 获取数据库表名称和注释
     */
    @Override
    public List<DbTableInfoDto> getDbTableNameList() {
        return noCodeTableDao.getDbTableNameList(SCHEMA_NAME, PREFIX_SOURCE_NAME);
    }

    /**
     * 获取数据库字段信息
     *
     * @param tableName 表名称
     */
    @Override
    public List<DbTableColumnInfoDto> getDbTableColumnList(String tableName) {
        return noCodeTableDao.getDbTableColumnList(SCHEMA_NAME, tableName);
    }

    /**
     * 权限检查
     *
     * @param tableName 表名
     */
    @Override
    public void permissionCheck(String tableName) {

        if (StringUtils.isBlank(tableName)) {
            throw new RRException("无法访问该表.");
        }

        String[] prefixArr = {PREFIX_SOURCE_NAME};
        if (Stream.of(prefixArr).noneMatch(tableName::startsWith)) {
            throw new RRException("无法访问该表.");
        }
    }

    /**
     * 数据返回
     *
     * @param tableName 数据库名称
     * @param search    搜索参数
     * @param page      页
     * @param column    offset
     * @return list
     */
    @Override
    public PageUtils getDataList(String tableName, List<DataSearchParamDto> search, Integer page, Integer limit) {

        if (StringUtils.isBlank(tableName)) {
            throw new RRException("参数异常");
        }

        List<DbTableColumnInfoDto> columnList = getDbTableColumnList(tableName);

        if (CollectionUtils.isEmpty(columnList)) {
            throw new RRException("无法访问该表");
        }

        if (!CollectionUtils.isEmpty(search)) {

            // 提取所有key
            Set<String> fieldNames = new HashSet<>(columnList.size());
            columnList.forEach(column -> {
                if (StringUtils.isNotBlank(column.getColumnName())) {
                    fieldNames.add(column.getColumnName());
                }
            });

            if (search.stream().anyMatch(param -> !fieldNames.contains(param.getFieldName()))){
                throw new RRException("不允许的搜索参数");
            }
        }


        long total = noCodeTableDao.getDataListSize(tableName, search);
        if (total < 1) {
            return new PageUtils(new ArrayList<>(0), 0, 1, 1);
        }
        List<Map<String, Object>> list = noCodeTableDao.getDataList(tableName, search, page, limit);
        return new PageUtils(list, (int) total, limit, page);
    }

    /**
     * 生成业务表
     *
     * @param tableComment  表名称
     * @param columnComment 字段备注
     * @return void
     */
    @Override
    public void genTable(String tableComment, List<String> columnComment) {
        noCodeTableDao.genTable(PREFIX_SOURCE_NAME + Long.toHexString(System.currentTimeMillis()), tableComment, columnComment);
    }

    /**
     * 删除数据库
     *
     * @param tableName 表名
     */
    @Override
    public void dropTable(String tableName) {
        noCodeTableDao.dropTable(tableName);
    }


    /**
     * 导入数据
     *
     * @param dataList 数据列表
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String importData(List<Map<String, Object>> dataList, String tableName) {
        if (CollectionUtils.isEmpty(dataList)) {
            throw new RRException("导入数据不能为空！");
        }

        int successNum = 0;
        int failureNum = 0;
        int presentNum = 0;

        StringBuilder failureMsg = new StringBuilder();
        for (Map<String, Object> data : dataList) {
            presentNum++;
            try {
                noCodeTableDao.insertData(tableName, data);
                successNum++;
            } catch (Exception e) {
                failureNum++;
                String msg = "\n第" + presentNum + "条数据" + " 数据有误，数据为" + data;
                failureMsg.append(msg);
                log.error(msg, e);
            }
        }

        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + "条数据格式不正确，需要重新导入数据，错误如下：");
            throw new RRException(failureMsg.toString());
        }
        return "数据已全部导入成功！共 " + successNum + " 条";
    }

    /**
     * 删除数据
     *
     * @param tableName 表格名称
     * @param ids       ids
     */
    @Override
    public void removeByIds(String tableName, List<Long> ids) {
        noCodeTableDao.removeByIds(tableName, ids);
    }
}
