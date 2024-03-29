package com.excelToSQL.generator.controller;

import com.excelToSQL.generator.payload.request.GenerateInsertSQLRequest;
import com.excelToSQL.generator.payload.request.GenerateUpdateSQLRequest;
import com.excelToSQL.generator.service.ExcelToSQLGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/generate")
@RequiredArgsConstructor
public class GenerateController {

    private final ExcelToSQLGeneratorService excelToSQLGeneratorService;

    @PutMapping("/update-sql")
    public String generateUpdateSQL(@RequestBody GenerateUpdateSQLRequest request) {
        return excelToSQLGeneratorService.generateUpdateSQLFromExcel(request);
    }

    @PutMapping("/insert-sql")
    public String generateInsertSQL(@RequestBody GenerateInsertSQLRequest request) {
        return excelToSQLGeneratorService.generateInsertSQLFromExcel(request);
    }
}
