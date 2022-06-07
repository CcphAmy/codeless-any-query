package com.github.ccphamy.modules.codeless.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DbTableColumnInfoDto implements Serializable {
//    select COLUMN_NAME,COLUMN_TYPE,COLUMN_COMMENT from information_schema.COLUMNS where table_name = 'pzzs_config' and table_schema = 'pzzs_master';

    private String columnName;
    private String columnType;
    private String columnComment;

}
