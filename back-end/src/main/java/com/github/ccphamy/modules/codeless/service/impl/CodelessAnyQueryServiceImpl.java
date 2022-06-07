package com.github.ccphamy.modules.codeless.service.impl;

import com.github.ccphamy.common.exception.RRException;
import com.github.ccphamy.common.utils.PageUtils;
import com.github.ccphamy.modules.codeless.dao.CodelessAnyQueryDao;
import com.github.ccphamy.modules.codeless.dto.DbTableColumnInfoDto;
import com.github.ccphamy.modules.codeless.dto.DbTableInfoDto;
import com.github.ccphamy.modules.codeless.service.CodelessAnyQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CodelessAnyQueryServiceImpl implements CodelessAnyQueryService {

    @Resource
    private CodelessAnyQueryDao noCodeTableDao;

    /**
     * 获取数据库表名称和注释
     *
     * @param schemaName 数据库
     * @param prefix     前缀
     */
    @Override
    public List<DbTableInfoDto> getDbTableNameList(String schemaName, String prefix) {
        return noCodeTableDao.getDbTableNameList(schemaName, prefix);
    }

    /**
     * 获取数据库字段信息
     *
     * @param schemaName 数据库
     * @param tableName  表名称
     */
    @Override
    public List<DbTableColumnInfoDto> getDbTableColumnList(String schemaName, String tableName) {
        return noCodeTableDao.getDbTableColumnList(schemaName, tableName);
    }

    /**
     * 数据返回
     *
     * @param tableName 数据库名称
     * @return list
     */
    @Override
    public PageUtils getDataList(String tableName, Integer page, Integer limit) {
        long total = noCodeTableDao.getDataListSize(tableName);
        if (total < 1) {
            return new PageUtils(new ArrayList<>(0), 0, 1, 1);
        }
        List<Map<String, Object>> list = noCodeTableDao.getDataList(tableName, page, limit);
        return new PageUtils(list, (int) total, limit, page);
    }

    /**
     * 生成业务表
     *
     * @param tableName     表名
     * @param tableComment  表名称
     * @param columnComment 字段备注
     * @return void
     */
    @Override
    public void genTable(String tableName, String tableComment, List<String> columnComment) {
        noCodeTableDao.genTable(tableName, tableComment, columnComment);
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
