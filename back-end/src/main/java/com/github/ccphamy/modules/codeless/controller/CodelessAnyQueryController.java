package com.github.ccphamy.modules.codeless.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.ccphamy.common.annotation.SysLog;
import com.github.ccphamy.common.exception.RRException;
import com.github.ccphamy.common.utils.PageUtils;
import com.github.ccphamy.common.utils.R;
import com.github.ccphamy.common.utils.excel.MapExcelUtil;
import com.github.ccphamy.modules.codeless.dto.DbTableColumnInfoDto;
import com.github.ccphamy.modules.codeless.service.CodelessAnyQueryService;
import com.github.ccphamy.modules.codeless.vo.DataListParamVo;
import com.github.ccphamy.modules.sys.controller.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * AQ
 *
 * @author ccphamy
 */
@RestController
@RequestMapping("/api/aq")
@Api(tags = {"无代码表格"})
public class CodelessAnyQueryController extends AbstractController {

    @Resource
    private CodelessAnyQueryService codelessAnyQueryService;


    /**
     * 数据库表列表
     */
    @RequiresPermissions("codeless:db:list")
    @GetMapping("/table/list")
    @ApiOperation(value = "数据库表列表", notes = "")
    public R tableList() {
        return R.ok().put("list", codelessAnyQueryService.getDbTableNameList());
    }


    /**
     * 数据库字段列表
     */
    @RequiresPermissions("codeless:db:list")
    @GetMapping("/column/list")
    @ApiOperation(value = "数据库字段列表", notes = "")
    public R columnList(@RequestParam String tableName) {
        codelessAnyQueryService.permissionCheck(tableName);
        return R.ok().put("list", codelessAnyQueryService.getDbTableColumnList(tableName));
    }


    /**
     * 数据列表
     */
    @RequiresPermissions("codeless:data:list")
    @PostMapping("/data/list")
    @ApiOperation(value = "数据列表", notes = "")
    public R dataList(@RequestBody DataListParamVo listParam, @RequestParam Integer page, @RequestParam Integer limit) {
        codelessAnyQueryService.permissionCheck(listParam.getTableName());
        PageUtils ret = codelessAnyQueryService.getDataList(listParam.getTableName(), listParam.getSearch(), page, limit);
        return R.ok().put("page", ret);
    }


    /**
     * 生成数据库表
     */
    @RequiresPermissions("codeless:db:create")
    @SysLog("生成数据库表")
    @ApiOperation(value = "生成数据库表", notes = "")
    @PostMapping("/table/create")
    public R createTable(@RequestParam("file") MultipartFile file, @RequestParam("tableComment") String tableComment) throws Exception {
        MapExcelUtil<Map> mapMapExcelUtil = new MapExcelUtil<>(Map.class);
        Map<Integer, String> map = mapMapExcelUtil.getExcelHeaderName(file.getInputStream());
        List<String> columnList = new ArrayList<>(map.size());
        map.forEach((k, v) -> {
            if (StringUtils.isBlank(v)) {
                throw new RRException("表字段存在为空.");
            }
            columnList.add(v);
        });

        codelessAnyQueryService.genTable(tableComment, columnList);
        return R.ok();
    }

    /**
     * 删除数据库表
     */
    @SysLog("删除数据库表")
    @PostMapping("/table/delete")
    @RequiresPermissions("codeless:db:delete")
    @ApiOperation(value = "删除数据库表", notes = "")
    public R delete(@RequestParam String tableName) {
        codelessAnyQueryService.permissionCheck(tableName);
        codelessAnyQueryService.dropTable(tableName);
        return R.ok();
    }


    /**
     * 导入数据
     */
    @RequiresPermissions("codeless:table:import")
    @SysLog("导入数据")
    @ApiOperation(value = "导入数据", notes = "")
    @PostMapping("/table/import")
    public R importTableData(@RequestParam("file") MultipartFile file, @RequestParam("tableName") String tableName) throws Exception {

        codelessAnyQueryService.permissionCheck(tableName);
        List<DbTableColumnInfoDto> columnList = codelessAnyQueryService.getDbTableColumnList(tableName);

        // 去除id
        for (int i = 0; i < columnList.size(); i++) {
            if ("id".equalsIgnoreCase(columnList.get(i).getColumnName())) {
                columnList.remove(i);
                break;
            }
        }

        if (CollectionUtils.isEmpty(columnList)) {
            throw new RRException("没有字段");
        }
        MapExcelUtil<Map> mapMapExcelUtil = new MapExcelUtil<>(Map.class);
        List<Map<String, Object>> list = mapMapExcelUtil.importExcel(columnList, file.getInputStream());

        if (CollectionUtils.isEmpty(list)) {
            throw new RRException("没有数据");
        }

        String message = codelessAnyQueryService.importData(list, tableName);
        return R.ok(message);
    }


    /**
     * 删除数据
     */
    @SysLog("删除数据")
    @PostMapping("/table/removeByIds")
    @RequiresPermissions("codeless:data:delete")
    @ApiOperation(value = "删除数据", notes = "")
    public R delete(@RequestBody List<Long> ids, @RequestParam("tableName") String tableName) {

        codelessAnyQueryService.permissionCheck(tableName);

        if (CollectionUtils.isEmpty(ids)) {
            throw new RRException("没有删除数据");
        }

        codelessAnyQueryService.removeByIds(tableName, ids);
        return R.ok();
    }


}
