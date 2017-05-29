package dataPreprocess;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

import dataPreprocess.*;





public class DataReadCSVFiles {
	
	ArrayList<DataIssueTemplate> IssueData; 
	int intStartingRowofData; 
	HashMap<String, Integer> columnIndex = new HashMap <String, Integer> ();
	
	String strFilePath;
	String fileName; 
	String sheetName; 
	
	
	
	public DataReadCSVFiles(
			ArrayList<DataIssueTemplate> tmp_IssueData, 
			int tmp_intStartingRowofData, 
			String tmp_strFilePath, 
			String tmp_strFileName, 
			String tmp_strSheetName
	){
		
		this.IssueData = tmp_IssueData; 
		this.intStartingRowofData = tmp_intStartingRowofData;
		this.fileName= tmp_strFilePath+tmp_strFileName;
		this.strFilePath=tmp_strFilePath; 
		this.sheetName= tmp_strSheetName; 
		
		//this.columnIndex = tmp_columnIndex; 
	}
	
	
//	public static void main (String args []){
//		ArrayList<DataIssueTemplate> IssueData = new ArrayList<DataIssueTemplate>();  
//		String strFilePath = "C:/Users/S.M.Didar/Dropbox/Didar DBPC/PhD Research/Fall 2015/ICSE -Journal 2016/Evaluation/JIRA client/Archetype-451 - Copy/";
//		String strWrFile = "Out.xls"; 
//		int intStartingRowofData = 3; 
//		
//		createColumnIndex (strFilePath+strWrFile, strWrFile, IssueData); 
//		readExcelFiles (strFilePath+strWrFile, strWrFile, IssueData, intStartingRowofData); 
//		
//	}
	
	

	// Creating an index for all the columns in excel file to easily search for values. 
	public HashMap<String, Integer> createColumnIndex (int rowNumForIndex){
		 
		
		System.out.println("Reading Excel file" + fileName + " sheet "+ sheetName);
        
		try{
				FileInputStream file = new FileInputStream(new File(fileName));		
				//Create Workbook instance holding reference to .xlsx file
		        HSSFWorkbook workbook = new HSSFWorkbook(file);
		        //Get first/desired sheet from the workbook
		        HSSFSheet sheet = workbook.getSheet(sheetName);
		        System.out.println(rowNumForIndex);
	        	Row row = sheet.getRow(rowNumForIndex); 
	        	
	        	
	        	 
	        	for (int cellCounter = 0; cellCounter <= row.getLastCellNum(); cellCounter++){
	        		if (row.getCell(cellCounter)!=null) {
	        			columnIndex.put(row.getCell(cellCounter).getStringCellValue(), cellCounter); 
		        	}	
		        }	
	        	System.out.println("Seccuessfully read "+ fileName + " sheet "+ sheetName +", created the columnIndex");
	        	file.close();
		}
		catch (Exception e){
			e.printStackTrace (); 
			System.out.println("Problem in reading " + fileName + " sheet "+ sheetName);
		}
		
		return columnIndex; 
		
		
//		---------------Just checking the index by printing it out , required for debugging ---------------
//		Iterator<String> keySetIterator = columnIndex.keySet().iterator(); 
//		while(keySetIterator.hasNext()){ 
//			String key = keySetIterator.next(); 
//			System.out.println("key: " + key + " value: " + columnIndex.get(key)); 
//		}

	}

	
	public Date convertStringDate (String datestr){
		String startDateString = datestr; //"06/27/2007";
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
	    Date startDate= null;
	    try {
	        startDate = df.parse(startDateString);
	        String newDateString = df.format(startDate);
//	        System.out.println(newDateString);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    
	    return startDate; 
	}
	
	
	
	
	
	public void readExcelFiles (boolean bool_ConversionRequired){
		try {
			System.out.println("Reading Excel file" + fileName + " sheet "+ sheetName);
	        
			FileInputStream file = new FileInputStream(new File(fileName));		
			//Create Workbook instance holding reference to .xlsx file
	        HSSFWorkbook workbook = new HSSFWorkbook(file);
	        //Get first/desired sheet from the workbook
	        HSSFSheet sheet = workbook.getSheet(sheetName);
			
	        
	        Iterator<Row> rowIterator = sheet.iterator();
	        
	        while (rowIterator.hasNext()) {
	        	Row row = rowIterator.next();
	        	intStartingRowofData--; // reducing starting row number, Wheen the number is zero we will start intaking data from the file 
	        	
	        	if (intStartingRowofData<0){
	        	
		        	DataIssueTemplate tempIssueData = new DataIssueTemplate(); 
		    		
		        	System.out.println("hello");
		    		if (row.getCell(columnIndex.get("Project name"))!=null) tempIssueData.setStrProject(row.getCell(columnIndex.get("Project name")).getStringCellValue());
		    		if (row.getCell(columnIndex.get("Issue key"))!=null) tempIssueData.setStrKey(row.getCell(columnIndex.get("Issue key")).getStringCellValue());
		    		if (row.getCell(columnIndex.get("Summary"))!=null) tempIssueData.setStrSummary(row.getCell(columnIndex.get("Summary")).getStringCellValue());
		    		if (row.getCell(columnIndex.get("Issue Type"))!=null) tempIssueData.setStrIssueType(row.getCell(columnIndex.get("Issue Type")).getStringCellValue());
		    		if (row.getCell(columnIndex.get("Status"))!=null) tempIssueData.setStrStatus(row.getCell(columnIndex.get("Status")).getStringCellValue());
		    		
		    		if (row.getCell(columnIndex.get("Priority"))!=null) tempIssueData.setStrPriority(row.getCell(columnIndex.get("Priority")).getStringCellValue());
		    		if (row.getCell(columnIndex.get("Resolution"))!=null) tempIssueData.setStrResolution(row.getCell(columnIndex.get("Resolution")).getStringCellValue());
		    		if (row.getCell(columnIndex.get("Assignee"))!=null) tempIssueData.setStrAssignee(row.getCell(columnIndex.get("Assignee")).getStringCellValue());
		    		if (row.getCell(columnIndex.get("Reporter"))!=null) tempIssueData.setStrReporter(row.getCell(columnIndex.get("Reporter")).getStringCellValue());
		    		if (row.getCell(columnIndex.get("Creator"))!=null) tempIssueData.setStrCreator(row.getCell(columnIndex.get("Creator")).getStringCellValue());
		    		if (row.getCell(columnIndex.get("Component/s"))!=null) tempIssueData.setStrComponent(row.getCell(columnIndex.get("Component/s")).getStringCellValue());
		    		if (row.getCell(columnIndex.get("Description"))!=null) tempIssueData.setStrDescription(row.getCell(columnIndex.get("Description")).getStringCellValue());
		    		
		    		
//		    		For analyzing commit information we need to use the following three fields. We can give create and resolve date same as the commit date and use this files to understand comkit erate , loc arate and files rate per week/day 
		    		
		    		
//		    		
//		    		if (row.getCell(columnIndex.get("Files"))!=null) tempIssueData.setDblNumberofFiles(Double.parseDouble(row.getCell(columnIndex.get("Files")).getStringCellValue()));
//		    		System.out.println("---------"+tempIssueData.getDblNumberofFiles());
//		    		if (row.getCell(columnIndex.get("Churn_Addition"))!=null) tempIssueData.setDblAdditionChurn (Double.parseDouble(row.getCell(columnIndex.get("Churn_Addition")).getStringCellValue()));
//		    		if (row.getCell(columnIndex.get("Churn_Deletion"))!=null) tempIssueData.setDblDeletionChurn(Double.parseDouble(row.getCell(columnIndex.get("Churn_Deletion")).getStringCellValue()));
		    		
		    		
		    		
		    		
		    		// --------------------- DATA READ EXCEL SETTINGS NEED TO BE ALTERED BASED ON IF A CONVERSION IS REQUIRED. ----------------------
		    		
		    		
		    		// Conversion not required
		    		if (bool_ConversionRequired == false){
		    		
			    		if (row.getCell(columnIndex.get("Created"))!=null && row.getCell(columnIndex.get("Created")).getCellType()!=HSSFCell.CELL_TYPE_STRING) {
			    			tempIssueData.setDateCreated(row.getCell(columnIndex.get("Created")).getDateCellValue());
			    		}
			    		if (row.getCell(columnIndex.get("Updated"))!=null && row.getCell(columnIndex.get("Updated")).getCellType()!=HSSFCell.CELL_TYPE_STRING) tempIssueData.setDateUpdated(row.getCell(columnIndex.get("Updated")).getDateCellValue());
			    		if (row.getCell(columnIndex.get("Resolved"))!=null && row.getCell(columnIndex.get("Resolved")).getCellType()!=HSSFCell.CELL_TYPE_STRING) tempIssueData.setDateResolved(row.getCell(columnIndex.get("Resolved")).getDateCellValue());
		    		}
		    		
		    		//CONVERSION REQUIRED-----------
		    		
		    		if (bool_ConversionRequired == true){
			    		if (row.getCell(columnIndex.get("Created"))!=null && row.getCell(columnIndex.get("Created")).getCellType()==HSSFCell.CELL_TYPE_STRING){
			    			tempIssueData.setDateCreated(convertStringDate(row.getCell(columnIndex.get("Created")).getStringCellValue()));
			    		}
			    		if (row.getCell(columnIndex.get("Updated"))!=null && row.getCell(columnIndex.get("Updated")).getCellType()==HSSFCell.CELL_TYPE_STRING) tempIssueData.setDateUpdated(convertStringDate(row.getCell(columnIndex.get("Updated")).getStringCellValue()));
			    		if (row.getCell(columnIndex.get("Resolved"))!=null && row.getCell(columnIndex.get("Resolved")).getCellType()==HSSFCell.CELL_TYPE_STRING) tempIssueData.setDateResolved(convertStringDate(row.getCell(columnIndex.get("Resolved")).getStringCellValue()));
		    		}
		    		
		    		// --------------------- DATA READ EXCEL SETTINGS NEED TO BE ALTERED BASED ON IF A CONVERSION IS REQUIRED. ----------------------
		    		
		    		
		    		
		    		System.out.println( "XXXXXXXx"+ tempIssueData.getStrKey());
		    		System.out.println( "XXXXXXXx"+ tempIssueData.getDateCreated());
		    		
		    		
		    		
		    		if (row.getCell(columnIndex.get("Affects Version/s"))!=null){
		    			if (row.getCell(columnIndex.get("Affects Version/s")).getCellType() != HSSFCell.CELL_TYPE_STRING ){
		    				tempIssueData.setStrAffectVersion(Double.toString(row.getCell(columnIndex.get("Affects Version/s")).getNumericCellValue()));
		    			}
		    			else tempIssueData.setStrAffectVersion(row.getCell(columnIndex.get("Affects Version/s")).getStringCellValue());
		    		}
		    		
		    		if (row.getCell(columnIndex.get("Fix Version/s"))!=null){
		    			if (row.getCell(columnIndex.get("Fix Version/s")).getCellType() != HSSFCell.CELL_TYPE_STRING ){
		    				tempIssueData.setStrFixVersion(Double.toString(row.getCell(columnIndex.get("Fix Version/s")).getNumericCellValue()));
		    			}
		    			else tempIssueData.setStrFixVersion(row.getCell(columnIndex.get("Fix Version/s")).getStringCellValue());
		    		}
		    				
		    		IssueData.add(tempIssueData); 
	        	}
	        }
	        
	        System.out.println("Seccuessfully read "+ fileName + " sheet "+ sheetName +", created the isslueList");
	        file.close();
		}
		catch (Exception e){
			e.printStackTrace (); 
			System.out.println("Problem in reading "+ fileName + " sheet "+ sheetName);
		}
	} 
	
	
}//End of class



//-----------------Imp : Not a part of this program but useful later for Date manipulation ------------------------------		    		

//Date date = row.getCell(columnIndex.get("Created")).getDateCellValue(); 
//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//Calendar c = Calendar.getInstance();
//c.setTime(date); // Now use today date.
//c.add(Calendar.DATE, 5); // Adding 5 days
//String output = sdf.format(c.getTime());
//System.out.println("-------------"+output);


//-----------------Imp : Not a part of this program but useful later for Date manipulation ------------------------------