package com.excelToSQL.generator.payload.request;

import lombok.Data;

import java.util.List;

@Data
public class GenerateInsertSQLRequest {
    private String path;
    private List<String> valueColumns;
    private String tableName;
}