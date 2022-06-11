package com.github.ccphamy.modules.codeless.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DbTableColumnInfoDto implements Serializable {

    private String columnName;
    private String columnType;
    private String columnComment;

}
