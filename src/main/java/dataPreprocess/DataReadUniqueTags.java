package dataPreprocess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import dataPreprocess.*;

public class DataReadUniqueTags {
	String filePath, fileName, unTagFile; 
	ArrayList<String> sheetName = new ArrayList <String> ();
	ArrayList<DataIssueTemplate> IssueData; 
	
	
	
	ArrayList <String> bugLabels = new ArrayList<String>();
	ArrayList <String> ftrLabels = new ArrayList<String>();
	
	public DataReadUniqueTags (String filePath, String fileName, String unTagFile, ArrayList<String> tmp_sheetName, ArrayList<DataIssueTemplate> tmp_IssueData){
		this.filePath=filePath;
		this.fileName=fileName;
		this.unTagFile = unTagFile; 
		this.sheetName=tmp_sheetName;
		this.IssueData = tmp_IssueData; 
		
	}
	
	public void readUniqueLabels (){
		try {
			System.out.println("Reading readUniqueLabel Excel file");
	        
			FileInputStream labelFile = new FileInputStream(new File(filePath+unTagFile));		
			//Create Workbook instance holding reference to .xlsx file
	        HSSFWorkbook labelWorkbook = new HSSFWorkbook(labelFile);
	        //Get first/desired sheet from the workbook
//	        HSSFSheet bugSheet = labelWorkbook.getSheet(sheetName.get(0));
//	        HSSFSheet ftrSheet = labelWorkbook.getSheet(sheetName.get(1));
	        
	        
	        
	        HSSFSheet bugSheet = labelWorkbook.getSheetAt(0);
	        HSSFSheet ftrSheet = labelWorkbook.getSheetAt(1);
	        
	        
	        
	        //Iterate through each rows one by one
	        Iterator<Row> rowIteratorBug = bugSheet.iterator();
	        
	        while (rowIteratorBug.hasNext()) {
	        	Row row = rowIteratorBug.next();
	        	bugLabels.add(row.getCell(0).getStringCellValue());
	        	
	        }	
	        
	        
	        Iterator<Row> rowIteratorFtr = ftrSheet.iterator();
	        while (rowIteratorFtr.hasNext()) {
	        	Row row = rowIteratorFtr.next();
	        	ftrLabels.add(row.getCell(0).getStringCellValue());
	        }
	        labelWorkbook.close();
	        labelFile.close();
	        System.out.println("Success: Completed reading unique labels");
		}
		catch (Exception e){
			e.printStackTrace (); 
			System.out.println("Problem in reading "+ filePath+fileName);
		}
		
		
		
		
//		----------------Just for debugging purpose
//		for (String s: sheetName){
//			System.out.println("sheet-----"+s);
//		}
//			
//		
//		for (String s:bugLabels){
//			System.out.println("bugs----"+s);
//		}
//		
//		for (String p: ftrLabels){
//			System.out.println("ftr-----"+p);
//		}
		
	}
	
	
	public void differentiateBugVsFtr(){
		try {
			FileInputStream inputInitialData = new FileInputStream(new File(filePath+ fileName));
			//Create Workbook instance holding reference to .xlsx file
	        HSSFWorkbook workbookInitial = new HSSFWorkbook(inputInitialData);
	        
	        HSSFSheet worksheetBug = null; 
	        HSSFSheet worksheetFeature = null; 
	        Row row= null; 
	        int ctrBugSheet=0,ctrFtrSheet =0; 
	        
	        if (workbookInitial.getSheet("Issue_Bug")==null && workbookInitial.getSheet("Issue_Ftr")==null) {
	        	
	        	worksheetBug = workbookInitial.createSheet("Issue_Bug");
	        	worksheetFeature = workbookInitial.createSheet("Issue_Ftr");
	        	//worksheetImp = workbookInitial.createSheet("Issue_Imp");
	        	
	        	
	        	
	        	
        			row = worksheetBug.createRow(ctrBugSheet); 
        			ctrBugSheet++;
        			
        			row.createCell(0).setCellValue("Project");
            		row.createCell(1).setCellValue("Key");
            		row.createCell(2).setCellValue("Summary");
            		row.createCell(3).setCellValue("Issue Type");
            		row.createCell(4).setCellValue("Status");
            		row.createCell(5).setCellValue("Priority");
            		row.createCell(6).setCellValue("Resolution");
            		row.createCell(7).setCellValue("Assignee");
            		row.createCell(8).setCellValue("Reporter");
            		row.createCell(9).setCellValue("Creator");
            		row.createCell(10).setCellValue("Affects Version/s");
            		row.createCell(11).setCellValue("Fix Version/s");
            		row.createCell(12).setCellValue("Component/s");
            		row.createCell(13).setCellValue("Description");
            		row.createCell(14).setCellValue("Created");
            		row.createCell(15).setCellValue("Resolved");
            		row.createCell(16).setCellValue("Updated");
    			
        		
        			
        			
        			row = worksheetFeature.createRow(ctrFtrSheet);    
        			ctrFtrSheet++;
        			
        			row.createCell(0).setCellValue("Project");
            		row.createCell(1).setCellValue("Key");
            		row.createCell(2).setCellValue("Summary");
            		row.createCell(3).setCellValue("Issue Type");
            		row.createCell(4).setCellValue("Status");
            		row.createCell(5).setCellValue("Priority");
            		row.createCell(6).setCellValue("Resolution");
            		row.createCell(7).setCellValue("Assignee");
            		row.createCell(8).setCellValue("Reporter");
            		row.createCell(9).setCellValue("Creator");
            		row.createCell(10).setCellValue("Affects Version/s");
            		row.createCell(11).setCellValue("Fix Version/s");
            		row.createCell(12).setCellValue("Component/s");
            		row.createCell(13).setCellValue("Description");
            		row.createCell(14).setCellValue("Created");
            		row.createCell(15).setCellValue("Resolved");
            		row.createCell(16).setCellValue("Updated");
	        	
	        	for (int i=0;i<IssueData.size();i++){
	        		String tempLabel = IssueData.get(i).getStrIssueType(); 
	        		boolean flagIsBug = false; 
	        		boolean flagIsFtr = false; 
	        		
	        		row = null; 
	        		
        			for (int j=0;j<bugLabels.size();j++){
		        			if (bugLabels.get(j).equals(tempLabel)){
		        				flagIsBug = true; 
		        			}
		        	}
	        		
	        		if (flagIsBug==true){
	        			row = worksheetBug.createRow(ctrBugSheet); 
	        			ctrBugSheet++;
        			}
	        		else{
	        			row = worksheetFeature.createRow(ctrFtrSheet);    
	        			ctrFtrSheet++;
	        		}
	        		
//	        		System.out.println("val---------"+i);
//	        		System.out.println("val---------"+IssueData.get(i).getStrKey());
//	        		System.out.println("val---------"+IssueData.get(i).getDateCreated());
	        		
	        		
	        		row.createCell(0).setCellValue(IssueData.get(i).getStrProject());
	        		row.createCell(1).setCellValue(IssueData.get(i).getStrKey());
	        		row.createCell(2).setCellValue(IssueData.get(i).getStrSummary());
	        		row.createCell(3).setCellValue(IssueData.get(i).getStrIssueType());
	        		row.createCell(4).setCellValue(IssueData.get(i).getStrStatus());
	        		row.createCell(5).setCellValue(IssueData.get(i).getStrPriority());
	        		row.createCell(6).setCellValue(IssueData.get(i).getStrResolution());
	        		row.createCell(7).setCellValue(IssueData.get(i).getStrAssignee());
	        		row.createCell(8).setCellValue(IssueData.get(i).getStrReporter());
	        		row.createCell(9).setCellValue(IssueData.get(i).getStrCreator());
	        		row.createCell(10).setCellValue(IssueData.get(i).getStrAffectVersion());
	        		row.createCell(11).setCellValue(IssueData.get(i).getStrFixVersion());
	        		row.createCell(12).setCellValue(IssueData.get(i).getStrComponent());
	        		row.createCell(13).setCellValue(IssueData.get(i).getStrDescription());
	        		System.out.println("---------------"+IssueData.get(i).getStrKey());
	        		System.out.println("---------------"+row.getRowNum());
	        		row.createCell(14).setCellValue(IssueData.get(i).getDateCreated());
	        		
	        		
	        		
	        		
	        		
	//////////////////////////////////////
	        		
	        		
	        		
	        		
//	        		HSSFRow row = sheet.createRow(0);
//	        		HSSFCell cell = row.createCell((short) 0);
//	        		
//
//	        		SimpleDateFormat datetemp = new SimpleDateFormat("yyyy-MM-dd");
//	        		Date cellValue = datetemp.parse("1994-01-01 12:00");
//	        		cell.setCellValue(cellValue);
//
//	        		//binds the style you need to the cell.
//	        		HSSFCellStyle dateCellStyle = wb.createCellStyle();
//	        		short df = wb.createDataFormat().getFormat("dd-mmm");
//	        		dateCellStyle.setDataFormat(df);
//	        		cell.setCellStyle(dateCellStyle);
	        		
	        		
	        		
	        		/////////////////////////////////////////
	        		
	        		
//	        		if (IssueData.get(i).getDateResolved()!=null){
//	        			
//	        			row.createCell(15); 
//	        			row.getCell(15).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//	        			
//	        			SimpleDateFormat datetemp = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		        		try{
//	        			Date cellValue = datetemp.parse(IssueData.get(i).getDateResolved().toString());
//		        		row.getCell(15).setCellValue(cellValue);
//		        		}
//		        		catch(Exception e){
//		        			
//		        		}
//	        			
//	        			
//	        			row.createCell(15).setCellValue(IssueData.get(i).getDateResolved());
//	        		}
//	        			else row.createCell(15).setCellValue("");
//	        		row.createCell(16).setCellValue(IssueData.get(i).getDateUpdated());	
	        		
	        		
	        	
	        		
	        		
	        		
	        		
	        		
	        		
	    ////////////////////////old version    		
	        		
	        		
	        		
	        		if (IssueData.get(i).getDateResolved()!=null){
	        			
	        			row.createCell(15).setCellValue(IssueData.get(i).getDateResolved());; 
	        		}
	        			else row.createCell(15).setCellValue("");
	        		row.createCell(16).setCellValue(IssueData.get(i).getDateUpdated());
	        	
	        		
	   ////////////////////old version      		
	        		
	        		
	        		
	        		
	        		
	        		
	        		
	        		
	        		
	        		
	        		
	        		
	        	}
	        } else System.out.println("Issue_Bug Issue_Ftr sheet already exist");
			
	        inputInitialData.close();
			
			
			FileOutputStream outputInitialData = new FileOutputStream(filePath+fileName);
			workbookInitial.write(outputInitialData);
			//fileOut.flush();
			
			outputInitialData.close();
			System.out.println("Success: Issue Bug and Issue Ftr written");
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Error: Issue Bug and Issue Ftr writing");
		}catch (IOException e2){
			e2.printStackTrace();
			System.out.println("Error: Issue Bug and Issue Ftr writing");
		}
	}
	
	
	
	
}


