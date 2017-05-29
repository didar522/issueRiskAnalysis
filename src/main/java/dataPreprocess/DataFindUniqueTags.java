package dataPreprocess;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import dataPreprocess.*;

public class DataFindUniqueTags {
	
	
	ArrayList<String> strlistUniqueTags; 
	ArrayList<DataIssueTemplate> IssueData; 
	String fileName, filePath, sheetName; 
	
	public DataFindUniqueTags (String tmp_strFilePath, String tmp_strFileName, String tmp_sheetName, ArrayList<DataIssueTemplate> tmp_IssueData, ArrayList<String> tmp_strlistUniqueTags){
		this.fileName=tmp_strFileName;
		this.filePath=tmp_strFilePath;
		this.sheetName=tmp_sheetName; 
		this.strlistUniqueTags=tmp_strlistUniqueTags; 
		this.IssueData=tmp_IssueData; 
	}
	
	
	
	
	public void identifyUniqueLabels (){
		for (int i=0;i<IssueData.size();i++){
			boolean flagUniqueLabel = true; 
			String tempLabel = IssueData.get(i).getStrIssueType(); 
			for (int j=0;j<strlistUniqueTags.size();j++){
				
				System.out.println("--"+i+"--"+ tempLabel + "--" + strlistUniqueTags.get(j));
				if (strlistUniqueTags.get(j).equalsIgnoreCase(tempLabel)){
					
					flagUniqueLabel = false; 
				}
			}
			
			if (flagUniqueLabel==true){
				strlistUniqueTags.add(tempLabel); 	
			}
		}
		
		
		try {
			FileOutputStream fileOut = new FileOutputStream(filePath+"UniqueTags.xls");
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheetBug = workbook.createSheet("Bug");
			HSSFSheet worksheetFeature = workbook.createSheet("Feature");			
			int bugShtCounter=0, ftrShtCounter=0; 
			
			for (int i=0;i<strlistUniqueTags.size();i++){
				HSSFRow row; 
				
				if (strlistUniqueTags.get(i).contains("Bug") || strlistUniqueTags.get(i).contains("bug")|| strlistUniqueTags.get(i).contains("BUG")){
					row = worksheetBug.createRow(bugShtCounter);
					bugShtCounter++;
				}
				else {
					row = worksheetFeature.createRow(ftrShtCounter);
					ftrShtCounter++;
				}
				
				HSSFCell cell = row.createCell(0);
				cell.setCellValue(strlistUniqueTags.get(i));
			}
			
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
//			System.out.println("Success: Unique labels written");
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
//			System.out.println("Error: Unique labels writing");
		}catch (IOException e2){
			e2.printStackTrace();
//			System.out.println("Error: Unique labels writing");
		}
	}

}  // End of Class