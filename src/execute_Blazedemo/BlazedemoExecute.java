package execute_Blazedemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class BlazedemoExecute {

	{
		try {
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
			String time = dateFormat.format(now);
			FileInputStream in = new FileInputStream("atu.properties");
			Properties props = new Properties();
			props.load(in);
			in.close();
			FileOutputStream out = new FileOutputStream("atu.properties");
			props.setProperty("atu.reports.dir", "ATU_Reports\\" + time);
			props.store(out, null);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	
	 System.setProperty("atu.reporter.config", "atu.properties");  
	}
		
	@Test
	public void runScripts() throws Exception {
			
			CallWrappersUsingVal em=new CallWrappersUsingVal();
		try {
			FileInputStream fis = new FileInputStream(new File(System.getProperty("user.dir")+"\\keywords\\KeywordDriver.xls")); 
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			HSSFSheet sheet = workbook.getSheetAt(0);	
			// get the number of rows
			int rowCount = sheet.getLastRowNum();			
			// loop through the rows
			for(int i=1; i <rowCount+1; i++){
				try {
					//XSSFRow row = sheet.getRow(i);
					HSSFRow row=sheet.getRow(i);
					System.out.println(row.getCell(1).getStringCellValue());
					System.out.println(row.getCell(3).getStringCellValue()+"\n");
					if(row.getCell(3).getStringCellValue().toLowerCase().equals("yes")){
						ATUReports.add("Executing Test Case - "+row.getCell(1).getStringCellValue(), LogAs.INFO, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
						em.getAndCallKeywordVal(".\\keywords\\"+row.getCell(1).getStringCellValue()+".xls");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			fis.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}	
	}
		