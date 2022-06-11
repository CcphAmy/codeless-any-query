package com.github.ccphamy.modules.codeless.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * 搜索DTO
 * @author ccphamy
 */
@Data
public class DataSearchParamDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 类型
     * eq, like
     */
    private String type;

    private String searchValue;
}
