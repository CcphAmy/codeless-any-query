package com.github.ccphamy.modules.codeless.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class DbTableInfoDto implements Serializable {
//    SELECT t.table_name, t.table_comment FROM information_schema.TABLES t, information_schema.SCHEMATA d WHERE  t.table_name like 'pzzs_%' AND  d.SCHEMA_NAME = 'pzzs_master';

    private String tableName;
    private String tableComment;

}
