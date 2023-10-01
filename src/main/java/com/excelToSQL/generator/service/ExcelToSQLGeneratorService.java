package com.excelToSQL.generator.service;

import com.excelToSQL.generator.entity.GenerateUpdateSQLRequest;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelToSQLGeneratorService {

    public String generateUpdateSQLFromExcel(GenerateUpdateSQLRequest request) {
        String path = request.getPath();
        List<String> whereColumnNames = request.getWhereColumns();
        List<String> setColumnNames = request.getSetColumns();
        String tableName = request.getTableName();

        StringBuilder sqlBuilder = new StringBuilder();

        try (FileInputStream excelFile = new FileInputStream(path)) {
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();

            // Read the first row for setColumnNames
            Row firstRow = iterator.next();

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                StringBuilder updateQuery = new StringBuilder("UPDATE ");
                updateQuery.append(tableName).append(" SET ");

                for (int i = 0; i < setColumnNames.size(); i++) {
                    String setColumnName = setColumnNames.get(i);
                    Cell cell = currentRow.getCell(getColumnIndex(setColumnName));
                    Cell firstRowCell = firstRow.getCell(getColumnIndex(setColumnName));

                    if (cell != null && firstRowCell != null) {
                        updateQuery.append(getColumnName(setColumnName, CellValue(firstRowCell)))
                                .append("=")
                                .append(formatCellValue(cell));

                        // Add comma for multiple set values, except the last one
                        if (i < setColumnNames.size() - 1) {
                            updateQuery.append(", ");
                        }
                    }
                }

                updateQuery.append(" WHERE ");

                for (int i = 0; i < whereColumnNames.size(); i++) {
                    String whereColumnName = whereColumnNames.get(i);
                    Cell firstRowCell = firstRow.getCell(getColumnIndex(whereColumnName));
                    Cell cell = currentRow.getCell(getColumnIndex(whereColumnName));

                    if (cell != null && firstRowCell != null) {
                        updateQuery.append(getColumnName(whereColumnName, CellValue(firstRowCell)))
                                .append("=")
                                .append(formatCellValue(cell));

                        if (i < whereColumnNames.size() - 1) {
                            updateQuery.append(" AND ");
                        }
                    }
                }

                sqlBuilder.append(updateQuery).append(";\n");
            }

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "An error occurred while reading the Excel file.";
        }

        return sqlBuilder.toString();
    }

    private String formatCellValue(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((int) cell.getNumericCellValue());
        } else {
            return "'" + cell.toString() + "'";
        }
    }

    private String CellValue(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((int) cell.getNumericCellValue());
        } else {
            return cell.toString() ;
        }
    }

    private int getColumnIndex(String columnName) {
        return Character.toUpperCase(columnName.charAt(0)) - 'A';
    }

    private String getColumnName(String column, String value) {
        return value;
    }
}