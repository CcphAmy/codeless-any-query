package com.github.ccphamy.modules.codeless.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class DbTableInfoDto implements Serializable {

    private String tableName;
    private String tableComment;

}
