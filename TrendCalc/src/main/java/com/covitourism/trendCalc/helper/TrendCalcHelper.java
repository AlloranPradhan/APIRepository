package com.covitourism.trendCalc.helper;

import org.springframework.stereotype.Component;

import com.aspose.cells.CellArea;
import com.aspose.cells.Cells;
import com.aspose.cells.DataSorter;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.SortOrder;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;

@Component
public class TrendCalcHelper {

	/**
	 * To convert the .csv to excel and also sort based on specific column
	 * @return String path
	 */
	public String convertToExcel() {
		String path = "";
		try {
			String csvFilePath = "/home/files/states_latest.csv";
			path =  "/home/files/output.xlsx";
			Workbook book = new Workbook(csvFilePath);
	        
	        book.save(path, SaveFormat.XLSX);
			Worksheet worksheet = book.getWorksheets().get(0);
			Cells cells = worksheet.getCells();
	
			DataSorter sorter = book.getDataSorter();
			sorter.setOrder1(SortOrder.ASCENDING);
			sorter.setKey1(0);
			sorter.setOrder2(SortOrder.ASCENDING);
			sorter.setKey2(1);
			CellArea ca = new CellArea();
			
			ca.StartRow = 1;
			
			ca.StartColumn = 0;
			ca.EndRow = 38;
			ca.EndColumn = 11;
			sorter.sort(cells, ca);
			book.save(path);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return path;
		
	}
}
