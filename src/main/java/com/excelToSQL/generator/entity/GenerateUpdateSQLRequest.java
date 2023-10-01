package com.excelToSQL.generator.entity;

import lombok.Data;

import java.util.List;

@Data
public class GenerateUpdateSQLRequest {
    private String path;
    private List<String> whereColumns;
    private List<String> setColumns;
    private String tableName;
}

