package com.excelToSQL.generator.payload.request;

import lombok.Data;

import java.util.List;

@Data
public class GenerateUpdateSQLRequest {
    private String path;
    private List<String> whereColumns;
    private List<String> setColumns;
    private String tableName;
}


