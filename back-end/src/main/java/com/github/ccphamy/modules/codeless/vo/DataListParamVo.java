package com.github.ccphamy.modules.codeless.vo;

import com.github.ccphamy.modules.codeless.dto.DataSearchParamDto;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 数据搜索
 * @author ccphamy
 */
@Data
@ToString
public class DataListParamVo {
    private String tableName;
    private List<DataSearchParamDto> search;
}
