package com.covitourism.trendCalc.scheduler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.covitourism.trendCalc.helper.TrendCalcHelper;
import com.covitourism.trendCalc.service.TrendCalcService;

@Component
public class TrendCalcScheduler {
	
	@Autowired
	TrendCalcService trendServ;
	
	@Autowired
	TrendCalcHelper trendCalchelper;

	/**
	 * To download from a site at scheduled time
	 */
	@Scheduled(cron = "0 0 07 * * ?")
	public void downloadDocumentJob() {
		try {
			long start = System.currentTimeMillis();
			
			InputStream inputStream = new URL("https://api.covid19india.org/csv/latest/state_wise.csv").openStream();
			Files.copy(inputStream, Paths.get("/home/files/states_latest.csv"), StandardCopyOption.REPLACE_EXISTING);
			String excelFilePath = trendCalchelper.convertToExcel();
			long end = System.currentTimeMillis();
		    System.out.printf("Download doc done in %d ms\n", (end - start));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * To persist the excel entries in DB
	 */
	@Scheduled(cron = "0 01 07 * * ?")
	public void ExcelToDatabase() {
		String jdbcURL = "jdbc:mysql://trendcalc-db:3307/mydatabase";
        String username = "root";
        String password = "root";
		   
	    int batchSize = 20;
	    Connection connection = null;
	    try {

	    		long start = System.currentTimeMillis();
		        
		        FileInputStream inputStream = new FileInputStream("/home/files/output.xlsx");
		       
		        Workbook xssfworkbook = new XSSFWorkbook(inputStream);
		        
		        Sheet firstSheet = xssfworkbook.getSheetAt(0);
		        Iterator<Row> rowIterator = firstSheet.iterator();

		        connection = DriverManager.getConnection(jdbcURL, username, password);
		        connection.setAutoCommit(false);

		        String sql = "INSERT INTO src_coviddata (state, conf_case, lastupdatedtime, load_dt) "
		        		+ "VALUES (?,?,?,?)";
		        
		        PreparedStatement statement_latest = connection.prepareStatement(sql);  
		        Date date=new Date();  
		        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(date);
		        statement_latest.setString(4, timeStamp);
		        int count = 0; 
		        rowIterator.next(); // skip the header row	       
		        while (rowIterator.hasNext()) {
		            Row nextRow = rowIterator.next();
		            Iterator<Cell> cellIterator = nextRow.cellIterator();
		            
		            while (cellIterator.hasNext()) {
		                Cell nextCell = cellIterator.next();

		                int columnIndex = nextCell.getColumnIndex();
		                switch (columnIndex) {
		                
		                case 0:
		                    String state = nextCell.getStringCellValue();
		                    statement_latest.setString(1, state);
		                    break;
		                case 1:
		                    long confcurr = (long) nextCell.getNumericCellValue();		                    
		                    statement_latest.setLong(2, confcurr);
		                    break;
		                case 5:		                	
		                	Date lastupdatedtime = nextCell.getDateCellValue();		                	
		                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		                    String strDate = dateFormat.format(lastupdatedtime);
		                    statement_latest.setString(3, strDate);
		                    break;
		                }       
		            }
		            statement_latest.addBatch();
		            if (count % batchSize == 0 ) {
		            	statement_latest.executeBatch();
		            }              
		        }
		        //execute the remaining queries
		        statement_latest.executeBatch();
		        connection.commit();
		        
		        //loading data from excel to src table 
		        trendServ.loadDataTarget();
		        
		        //update the data based on changes in src table
		        trendServ.updateData();
		        
		        //Updating trend based on the newly updated data
		        trendServ.updateTrend();
		        connection.close();
		         
		        long end = System.currentTimeMillis();
		        System.out.printf("Insert done in %d ms\n", (end - start));
		        
	    }
		catch (IOException ex1) {
	        System.out.println("Error reading file");
	        ex1.printStackTrace();
	    } catch (SQLException ex2) {
	        System.out.println("Database error");
	        ex2.printStackTrace();
	    }
		
	}
}
