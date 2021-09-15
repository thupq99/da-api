package com.globits.da.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.globits.da.dto.EmployeeDto;

public class EmployeeExporter {

	 private XSSFWorkbook workbook;
	 private XSSFSheet sheet;
	 private List<EmployeeDto> listEmps;
	 public EmployeeExporter(List<EmployeeDto> listEmps) {
		 this.listEmps = listEmps;
		 workbook = new XSSFWorkbook();
	 }
	
	 private void writeHeaderLine() {
	        sheet = workbook.createSheet("Employees");
	         
	        Row row = sheet.createRow(0);
	         
	        CellStyle style = workbook.createCellStyle();
	        XSSFFont font = workbook.createFont();
	        font.setBold(true);
	        font.setFontHeight(16);
	        style.setFont(font);
	         
	        createCell(row, 0, "code", style);      
	        createCell(row, 1, "name", style);       
	        createCell(row, 2, "email", style);    
	        createCell(row, 3, "phone", style);
	        createCell(row, 4, "age", style);
	         
	    }
	     
	    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
	        sheet.autoSizeColumn(columnCount);
	        Cell cell = row.createCell(columnCount);
	        if (value instanceof Integer) {
	            cell.setCellValue((Integer) value);
	        } else if (value instanceof Boolean) {
	            cell.setCellValue((Boolean) value);
	        }else {
	            cell.setCellValue((String) value);
	        }
	        cell.setCellStyle(style);
	    }
	     
	    private void writeDataLines() {
	        int rowCount = 1;
	 
	        CellStyle style = workbook.createCellStyle();
	        XSSFFont font = workbook.createFont();
	        font.setFontHeight(14);
	        style.setFont(font);
	                 
	        for (EmployeeDto empl : listEmps) {
	            Row row = sheet.createRow(rowCount++);
	            int columnCount = 0;
	             
	            createCell(row, columnCount++, empl.getCode(), style);
	            createCell(row, columnCount++, empl.getName(), style);
	            createCell(row, columnCount++, empl.getEmail(), style);
	            createCell(row, columnCount++, empl.getPhone(), style);
	            createCell(row, columnCount++, empl.getAge(), style); 
	        }
	    }
	     
	    public void export(HttpServletResponse response) throws IOException {
	        writeHeaderLine();
	        writeDataLines();
	         
	        ServletOutputStream outputStream = response.getOutputStream();
	        workbook.write(outputStream);
	        workbook.close();
	         
	        outputStream.close();
	         
	    }
	 
	 
}
