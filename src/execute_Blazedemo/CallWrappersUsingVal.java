package execute_Blazedemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import wrapper.KeywordWrapper;
import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

public class CallWrappersUsingVal {
	

	static Logger logger = Logger.getLogger("CallWrapperUsingVal");
			
	
	public void getAndCallKeywordVal(String fileName) throws Exception {
	
		PropertyConfigurator.configure(".\\Properties\\log4j.properties");
		
		FileInputStream file = new FileInputStream(new File(fileName));

		// Create Workbook instance holding reference to .xlsx file
		HSSFWorkbook workbook = new HSSFWorkbook(file);

		Class<KeywordWrapper> wrapper = KeywordWrapper.class;
		Object wM = wrapper.newInstance();

		// Get first/desired sheet from the workbook
		HSSFSheet sh = workbook.getSheet("TestData");
		//GenericWrapperMethods w = new GenericWrapperMethods();
		for (int i = 1; i <= sh.getLastRowNum(); i++) {

			HSSFRow row = sh.getRow(i);
		//	System.out.println(row);
			String aa = row.getCell(0).getStringCellValue().toLowerCase();
			System.out.println("maguda"+aa.length());
			String b = row.getCell(1).getStringCellValue().toLowerCase();
			System.out.println("maguda"+b);
			if (aa.length() > 0 && b.equals("yes")) {
				System.out.println(row.getCell(0).getStringCellValue());
				System.out.println(row.getCell(1).getStringCellValue());
				System.out.println(row.getCell(2).getStringCellValue());
				System.out.println(row.getCell(3).getStringCellValue());
				ATUReports.add("Executing Test Case - " + row.getCell(0).getStringCellValue(), "------------:", row
						.getCell(3).getStringCellValue(), ":------------", LogAs.INFO, new CaptureScreen(
						ScreenshotOf.BROWSER_PAGE));
				logger.info(row.getCell(0).getStringCellValue() + ": "+row.getCell(3).getStringCellValue()+ " test case started");
				System.out.println(row.getCell(0).getStringCellValue());
				System.out.println(row.getCell(1).getStringCellValue());
			} else if (aa.length() > 0 && b.equals("no")) {
				System.out.println(row.getCell(0).getStringCellValue());
				System.out.println(row.getCell(1).getStringCellValue());
			}
			if (b.equals("yes")) {
				//int col_no = row.getLastCellNum();
				String keyword = "";
				String locator = "",Page="";
				String data = "";
				String value1 = "", value2 = "", value3 = "";
				try {
					keyword = sh.getRow(i).getCell(4).getStringCellValue().trim();
					System.out.println("xyz");
					System.out.println(keyword);
					logger.info(i+": '"+keyword+"' wrapper in use");
					String data1=sh.getRow(i).getCell(5).getStringCellValue().trim();
					System.out.println("xyzq");
					System.out.println(data1);
					if(!data1.equals(""))  {
						Page = data1.substring(0,data1.indexOf("."));
						logger.info("'"+Page+"' Page property file will be retrieved");
						locator = loadObjectRepository(".\\Properties\\"+Page+".properties").getProperty(data1);
						logger.info("'"+locator+"' locator value is retrieved");
					} 
		//			else if (((data1.substring(0,data1.indexOf("."))).equals ("bulk"))) {
		//			}
					else {
					locator = sh.getRow(i).getCell(5).getStringCellValue().trim();
					logger.info("No Locator detail received or BULK file");					
					}
					data = sh.getRow(i).getCell(6).getStringCellValue().trim();
					logger.info("'"+data+"' data for processing");
					value1 = sh.getRow(i).getCell(7).getStringCellValue().trim();
					logger.info("'"+value1+"' value for processing");
					value2 = sh.getRow(i).getCell(8).getStringCellValue().trim();
					value3 = sh.getRow(i).getCell(9).getStringCellValue().trim();
				} catch (NullPointerException e) {
					// ignore
				}
				try {
					Method[] methodName = wrapper.getDeclaredMethods();
					for (Method method : methodName) {

						int a = method.getParameterCount();

						/*String text = method.getName();
						System.out.println("Test"+text);
						String text1 = keyword;
						System.out.println("Test1"+text1);*/
						
						if (method.getName().toLowerCase().equals(keyword.toLowerCase().trim())) 
												{
							
							if (locator.equals("") && data.equals("") && value1.equals("") && value2.equals("")
									&& value3.equals("") && (a == 0)) {
								logger.info(a+" Argument method");
								method.invoke(wM);
								break;
							} else if (!locator.equals("") && data.equals("") && value1.equals("") && value2.equals("")
									&& value3.equals("") && (a == 1)) {
								logger.info(a+" Argument method /locator/");
								method.invoke(wM, locator);
								break;
							} else if (locator.equals("") && !data.equals("") && value1.equals("") && value2.equals("")
									&& value3.equals("") && (a == 1)) {
								logger.info(a+" Argument method /data/");
								method.invoke(wM, data);
								break;
							} else if (locator.equals("") && data.equals("") && !value1.equals("") && value2.equals("")
									&& value3.equals("") && (a == 1)) {
								logger.info(a+" Argument method /value1/");
								method.invoke(wM, value1);
								break;
							} else if (locator.equals("") && data.equals("") && value1.equals("") && !value2.equals("")
									&& value3.equals("") && (a == 1)) {
								logger.info(a+" Argument method /value2/");
								method.invoke(wM, value2);
								break;
							} else if (locator.equals("") && data.equals("") && value1.equals("") && value2.equals("")
									&& !value3.equals("") && (a == 1)) {
								logger.info(a+" Argument method /value3/");
								method.invoke(wM, value3);
								break;
							} else if (!locator.equals("") && !data.equals("") && value1.equals("") && value2.equals("")
									&& value3.equals("") && (a == 2)) {
								System.out.println("maguda");
								logger.info(a+" Argument method /locator,data/");
								method.invoke(wM, locator, data);
								break;
							} else if (!locator.equals("") && data.equals("") && !value1.equals("") && value2.equals("")
									&& value3.equals("") && (a == 2)) {
								System.out.println("maguda");
								logger.info(a+" Argument method /locator,value1/");
								method.invoke(wM, locator, value1);
								break;
							} else if (!locator.equals("") && data.equals("") && value1.equals("") && !value2.equals("")
									&& value3.equals("") && (a == 2)) {
								logger.info(a+" Argument method /locator,value2/");
								method.invoke(wM, locator, value2);
								break;
							} else if (!locator.equals("") && data.equals("") && value1.equals("") && value2.equals("")
									&& !value3.equals("") && (a == 2)) {
								logger.info(a+" Argument method /locator,value3/");
								method.invoke(wM, locator, value3);
								break;
							} else if (locator.equals("") && !data.equals("") && !value1.equals("") && value2.equals("")
									&& value3.equals("") && (a == 2)) {
								logger.info(a+" Argument method /data,value1/");
								method.invoke(wM, data, value1);
								break;
							} else if (locator.equals("") && !data.equals("") && value1.equals("") && !value2.equals("")
									&& value3.equals("") && (a == 2)) {
								logger.info(a+" Argument method /data,value2/");
								method.invoke(wM, data, value2);
								break;
							} else if (locator.equals("") && !data.equals("") && value1.equals("") && value2.equals("")
									&& !value3.equals("") && (a == 2)) {
								logger.info(a+" Argument method /data,value3/");
								method.invoke(wM, data, value3);
								break;
							} else if (locator.equals("") && data.equals("") && !value1.equals("") && !value2.equals("")
									&& value3.equals("") && (a == 2)) {
								logger.info(a+" Argument method /value1,value2/");
								method.invoke(wM, value1, value2);
								break;
							} else if (locator.equals("") && data.equals("") && !value1.equals("") && value2.equals("")
									&& !value3.equals("") && (a == 2)) {
								logger.info(a+" Argument method /value1,value3/");
								method.invoke(wM, value1, value3);
								break;
							} else if (locator.equals("") && data.equals("") && value1.equals("") && !value2.equals("")
									&& !value3.equals("") && (a == 2)) {
								logger.info(a+" Argument method /value2,value3/");
								method.invoke(wM, value2, value3);
								break;
							} else if (!locator.equals("") && !data.equals("") && !value1.equals("") && value2.equals("")
									&& value3.equals("") && (a == 3)) {
								logger.info(a+" Argument method /locator,data,value1/");
								method.invoke(wM, locator, data, value1);
								break;
							} else if (!locator.equals("") && !data.equals("") && value1.equals("") && !value2.equals("")
									&& value3.equals("") && (a == 3)) {
								logger.info(a+" Argument method /locator,data,value2/");
								method.invoke(wM, locator, data, value2);
								break;
							} else if (!locator.equals("") && !data.equals("") && value1.equals("") && value2.equals("")
									&& !value3.equals("") && (a == 3)) {
								logger.info(a+" Argument method /locator,data,value3/");
								method.invoke(wM, locator, data, value3);
								break;
							} else if (!locator.equals("") && data.equals("") && !value1.equals("") && !value2.equals("")
									&& value3.equals("") && (a == 3)) {
								logger.info(a+" Argument method /locator,value1,value2/");
								method.invoke(wM, locator, value1, value2);
								break;
							} else if (!locator.equals("") && data.equals("") && !value1.equals("") && value2.equals("")
									&& !value3.equals("") && (a == 3)) {
								logger.info(a+" Argument method /locator,value2,value3/");
								method.invoke(wM, locator, value2, value3);
								break;
							} else if (!locator.equals("") && data.equals("") && value1.equals("") && !value2.equals("")
									&& !value3.equals("") && (a == 3)) {
								logger.info(a+" Argument method /locator,value1,value3/");
								method.invoke(wM, locator, value1, value3);
								break;
							} else if (locator.equals("") && !data.equals("") && !value1.equals("") && !value2.equals("")
									&& value3.equals("") && (a == 3)) {
								logger.info(a+" Argument method /data,value1,value2/");
								method.invoke(wM, data, value1, value2);
								break;
							} else if (locator.equals("") && !data.equals("") && !value1.equals("") && value2.equals("")
									&& !value3.equals("") && (a == 3)) {
								logger.info(a+" Argument method /data,value1,value3/");
								method.invoke(wM, data, value1, value3);
								break;
							} else if (locator.equals("") && !data.equals("") && value1.equals("") && !value2.equals("")
									&& !value3.equals("") && (a == 3)) {
								logger.info(a+" Argument method /data,value2,value3/");
								method.invoke(wM, data, value2, value3);
								break;
							} else if (locator.equals("") && data.equals("") && !value1.equals("") && !value2.equals("")
									&& !value3.equals("") && (a == 3)) {
								logger.info(a+" Argument method /value1,value2,value3/");
								method.invoke(wM, value1, value2, value3);
								break;
							} else if (!locator.equals("") && !data.equals("") && !value1.equals("") && !value2.equals("")
									&& value3.equals("") && (a == 4)) {
								logger.info(a+" Argument method /locator,data,value1,value2/");
								method.invoke(wM, locator, data, value1, value2);
								break;
							} else if (!locator.equals("") && !data.equals("") && value1.equals("") && !value2.equals("")
									&& !value3.equals("") && (a == 4)) {
								logger.info(a+" Argument method /locator,data,value2,value3/");
								method.invoke(wM, locator, data, value2, value3);
								break;
							} else if (!locator.equals("") && !data.equals("") && !value1.equals("") && value2.equals("")
									&& !value3.equals("") && (a == 4)) {
								logger.info(a+" Argument method /locator,data,value1,value3/");
								method.invoke(wM, locator, data, value1, value3);
								break;
							} else if (locator.equals("") && !data.equals("") && !value1.equals("") && !value2.equals("")
									&& !value3.equals("") && (a == 4)) {
								logger.info(a+" Argument method /data,value1,value2,value3/");
								method.invoke(wM, data, value1, value2, value3);
								break;
							} else if (!locator.equals("") && data.equals("") && !value1.equals("") && !value2.equals("")
									&& !value3.equals("") && (a == 4)) {
								logger.info(a+" Argument method /locator,value1,value2,value3/");
								method.invoke(wM, locator, value1, value2, value3);
								break;
							} else if (!locator.equals("") && !data.equals("") && !value1.equals("") && !value2.equals("")
									&& !value3.equals("") && (a == 5)) {
								logger.info(a+" Argument method /locator,data,value1,value2,value3/");
								method.invoke(wM, locator, data, value1, value2, value3);
								break;
							}
							else{
									ATUReports.add("Method not found " + method.getName(),data,LogAs.INFO, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
									logger.info("No method found for processing");
							}
						}
						
					}
				} catch (Exception e) {
				}

			}

		} // End of for loop line 35
		workbook.close();
		file.close();
	}


	private Properties loadObjectRepository(String FileName) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		Properties p = new Properties();
		// Step 2: Load the Property file
		p.load(new FileInputStream(new File(FileName)));
		return p;
	}


	
	
}
