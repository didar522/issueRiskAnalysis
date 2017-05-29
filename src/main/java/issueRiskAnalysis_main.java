

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import dataPreprocess.*;
import textTFIDF.Issue;
import topicAnalysis.mainTACalc;
import textTFIDF.*;


public class issueRiskAnalysis_main {

	//-------------------------- Details related to input file location -----------------------------
	
	public static String strFilePath = "C:/Users/Didar/Desktop/Summary/";
	public static String strFileName = "Summary_Out.xls"; 
	public static String strSheetName = strFileName; 
	public static int intStartingRowofData = 1;
	
	//Key input string for analyzing in this program------------------------
	public static String searchString = ""; 
	
	//-------------------------- Details related to input file location -----------------------------
	
	
	//-------------------------- Declaration of all key Arraylists used throughout the program  -----------------------------
	
	public static HashMap<String, Integer> excelFileIndex = new HashMap <String, Integer> ();  
	public static HashMap<String, Integer> bur_ftr_FileIndex = new HashMap <String, Integer> ();  
	public static ArrayList<DataIssueTemplate> IssueData = new ArrayList<DataIssueTemplate>();  
	public static ArrayList<String> strlistUniqueTags = new ArrayList<String> (); 
	private static ArrayList<DataIssueTemplate> bugIssueData = new ArrayList<DataIssueTemplate>();  
	public static ArrayList<DataIssueTemplate> processedbugIssueData = new ArrayList<DataIssueTemplate>(); 
	
	//-------------------------- Declaration of all key Arraylists used throughout the program  -----------------------------
	
	//-------------------------- Declaration of Topic Model & TFIDF Setup  -----------------------------
	public static int intAnalysisStart4mWeek=1;   
	public static int AnalysisUptoWeek=getBugIssueData().size();
	public static int numTopics=3; 
	public static double taAlpha = 0.1; 
	public static int taIteration = 1; 
	public static String topicFilesFilePath = strFilePath+ "/Topic files"; 
	public static double  luceneThreshold =0.1; 
	//-------------------------- Declaration of Topic Model & TFIDF Setup -----------------------------
	
	
	
	public static void main(String[] args) {
		ReadingExcelsheet (getBugIssueData(), intStartingRowofData, strFilePath, strFileName, strSheetName); 
	
		//----------------------Optional: If we want to select only bugs and perform analysis within this subset. 
		
//		IdentifyingUniqueTagsFromAllIssues ();
//		DistributingIssuesInMultipleTags ();
//		ReadingExcelsheet (getBugIssueData(), intStartingRowofData, strFilePath, strFileName, "Issue_Bug"); 
		
		//----------------------Optional: If we want to select only bugs and perform analysis within this subset. 
		
		TopicModeling ();
		performingTFIDF ();
	}
	
	
	public static void ReadingExcelsheet (ArrayList<DataIssueTemplate> tmp_IssueData, 
			int tmp_intStartingRowofData, 
			String tmp_strFilePath, 
			String tmp_strFileName, 
			String tmp_strSheetName){
		
		DataReadExcelFiles objDataReadExcelFiles = new DataReadExcelFiles(tmp_IssueData, tmp_intStartingRowofData, tmp_strFilePath, tmp_strFileName, tmp_strSheetName); 
		excelFileIndex = objDataReadExcelFiles.createColumnIndex(0); 
		objDataReadExcelFiles.readExcelFiles(false);
		
		
//		---------------Debug Sensor console output - For sake of checking excel index output only 0---------------------
//		Iterator<String> keySetIterator = excelFileIndex.keySet().iterator(); 
//		while(keySetIterator.hasNext()){ 
//			String key = keySetIterator.next(); 
//			System.out.println("key: " + key + " value: " + excelFileIndex.get(key)); 
//		}
		
//		---------------Debug Sensor console output - for sake of checking output of the issue data list ------------------
//		for (DataIssueTemplate issueCounter:IssueData ){
//			System.out.println(issueCounter.getStrAffectVersion());	
//		}
	}
	
	
	public static void IdentifyingUniqueTagsFromAllIssues (){
		
//		ReadingExcelsheet (IssueData, intStartingRowofData, strFilePath, strFileName, strSheetName); 
		
		DataFindUniqueTags objDataFindUniqueTags = new DataFindUniqueTags (strFilePath, strFileName, strFileName, IssueData, strlistUniqueTags); 
		objDataFindUniqueTags.identifyUniqueLabels();
		
//		---------------Debug Sensor console output - printing all tags ------------------
//		 for (String counter:strlistUniqueTags){
//			 System.out.println(counter);
//		 }
	}
	
	public static void DistributingIssuesInMultipleTags (){
		int intStartingRowofData = 1;
		DataReadExcelFiles objDataReadExcelFiles = new DataReadExcelFiles(IssueData, intStartingRowofData, strFilePath, strFileName, strFileName); 
		excelFileIndex = objDataReadExcelFiles.createColumnIndex(0); 
		objDataReadExcelFiles.readExcelFiles(false);
		
		
		String uniqueTagFileName = "UniqueTags.xls";
		ArrayList<String> unTagSheetName = new ArrayList <String> ();
		unTagSheetName.add("Bug"); 
		unTagSheetName.add("Feature");
		DataReadUniqueTags objDataReadUniqueTags = new DataReadUniqueTags (strFilePath, strFileName, uniqueTagFileName, unTagSheetName, IssueData); 
		objDataReadUniqueTags.readUniqueLabels();
		objDataReadUniqueTags.differentiateBugVsFtr();
	}
	
	public static void TopicModeling (){

		mainTACalc objmainTACalc = new mainTACalc (strFilePath, topicFilesFilePath, bugIssueData); 
		
		objmainTACalc.preProcessing();
		objmainTACalc.funcTopicAnalysis(intAnalysisStart4mWeek,AnalysisUptoWeek,numTopics,taAlpha,taIteration);
		
		
		//-------------Debug Sensor console output - Is bugIssuedata properly achiving related topics and their similarity? 
		
//		for (DataIssueTemplate iterator:bugIssueData){
//			for (int i=0;i<iterator.alIssueTopicInfo.size();i++){
//				System.out.println(iterator.alIssueTopicInfo.get(i).topicNum);
//				System.out.println(iterator.alIssueTopicInfo.get(i).topicSimilarity);
//				System.out.println(iterator.alIssueTopicInfo.get(i).topicLabels);
//				System.out.println("====================================");
//				
//			}
//			
//			System.out.println(iterator.maxSimilarTopicInfo.topicNum);
//			System.out.println(iterator.maxSimilarTopicInfo.topicSimilarity);
//			System.out.println(iterator.maxSimilarTopicInfo.topicLabels);
//		}
		
		//-------------Debug Sensor console output - Is bugIssuedata properly achiving related topics and their similarity? 
		
	}
	
	
	public static void performingTFIDF (){
		
		
		//Optional: Just for test purpose---------------------
		searchString = bugIssueData.get(2).getStrSummary(); 
		
		ArrayList<Issue> trainSet = new ArrayList<Issue>();
		int index = 0; 
		
		
//		mainTACalc objmainTACalc = new mainTACalc (strFilePath,topicFilesFilePath, bugIssueData);
//		objmainTACalc.preProcessing();
		
		for (DataIssueTemplate iterator: bugIssueData){
			trainSet.add(new Issue(iterator.getStrKey(), iterator.getStrPriority(),iterator.getStrSummary(), iterator.getStrDescription(),
					iterator.getDblNumberofFiles(), iterator.getStrProcessedText(), iterator.getStrIssueType(), iterator.alIssueTopicInfo, iterator.maxSimilarTopicInfo)); 
			
			
		}
		
		
		UseLuceneText lucene = new UseLuceneText(trainSet, luceneThreshold);
		lucene.buildInLuceneIndexes();

		ArrayList<Issue> matchedList = lucene.searchInLuceneIndexes(searchString);
		
	try{	
		
		File printdest = new File(strFilePath+"/results.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(printdest));
		
		for (int count=0;count<10;count++){
			out.write("Issue ID -> "+matchedList.get(count).getissueId()+"\n"); out.newLine();
			out.write("Similarity -> "+matchedList.get(count).similarityvalue+"\n"); out.newLine();
			out.write("Priority -> "+matchedList.get(count).getIssuePriority()+"\n"); out.newLine();
			out.write("Issue Type -> "+matchedList.get(count).getissueType()+"\n"); out.newLine();
			out.write("Most prominent topic num -> "+matchedList.get(count).maxSimilarTopicInfo.topicNum+"\n"); out.newLine();
			out.write("Most prominent topic similarity -> "+ matchedList.get(count).maxSimilarTopicInfo.topicSimilarity+"\n"); out.newLine();
			out.write("Most prominent topic keywords -> "+ matchedList.get(count).maxSimilarTopicInfo.topicLabels+"\n"); out.newLine();
			out.write("=========================================================================="+"\n"+"\n"); out.newLine();
		}
		
		out.close(); 
		
		
	} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} 
		
	
//	---------------Debug console print -----------------------
//		for (int count=0;count<10;count++){
//			System.out.println("Issue ID -> "+matchedList.get(count).getissueId());
//			System.out.println("Similarity -> "+matchedList.get(count).similarityvalue);
//			System.out.println("Priority -> "+matchedList.get(count).getIssuePriority());
//			System.out.println("Issue Type -> "+matchedList.get(count).getissueType());
//			System.out.println("Most prominent topic num -> "+matchedList.get(count).maxSimilarTopicInfo.topicNum);
//			System.out.println("Most prominent topic similarity -> "+ matchedList.get(count).maxSimilarTopicInfo.topicSimilarity);
//			System.out.println("Most prominent topic keywords -> "+ matchedList.get(count).maxSimilarTopicInfo.topicLabels);
//			System.out.println("==========================================================================");
//		}
		
	
	}


	public static ArrayList<DataIssueTemplate> getBugIssueData() {
		return bugIssueData;
	}


	public static void setBugIssueData(ArrayList<DataIssueTemplate> bugIssueData) {
		issueRiskAnalysis_main.bugIssueData = bugIssueData;
	}
	
	
	
	

}
