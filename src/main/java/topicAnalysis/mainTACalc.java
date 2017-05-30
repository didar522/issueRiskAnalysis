package topicAnalysis;

import java.awt.PageAttributes.OriginType;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.nio.file.Files;

import org.apache.poi.hssf.record.FilePassRecord;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.tartarus.snowball.ext.PorterStemmer;

import cc.mallet.util.FileUtils;
import dataPreprocess.DataIssueTemplate;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import topicAnalysis.malletTopicAnalysis;
import topicAnalysis.topicAnalysisListStr;


public class mainTACalc {
	
	String filePath;
	String topicFilesFilePath; 
	ArrayList<DataIssueTemplate> bugIssueData; 
	
	File dest; 
	String eachIssue, eachIssueFilterPunc; 
	

	public mainTACalc (
			
			String filePath,
			String topicFilesFilePath, 
			ArrayList<DataIssueTemplate> bugIssueData
			){
	
		this.filePath=filePath;
		this.topicFilesFilePath = topicFilesFilePath; 
		this.bugIssueData = bugIssueData; 
		
	}
	
	
public void preProcessing (){	
		
	File BackUpTopics = new File (topicFilesFilePath); 
	if (!BackUpTopics.exists() || !BackUpTopics.isDirectory()){
		new File(topicFilesFilePath).mkdir();
	}
	
	
	
	
		String tmpProcessedString;
		int filecounter =0; 
		
		
		for (int i=0; i<bugIssueData.size();i++){
			
			tmpProcessedString= "";
			filecounter++; 
			dest = new File(topicFilesFilePath+"/"+bugIssueData.get(i).getStrKey()+".txt");
			
			try {
				
				eachIssue = bugIssueData.get(i).getStrSummary() + " " + bugIssueData.get(i).getStrDescription();
			
				eachIssueFilterPunc= eachIssue.replaceAll("[^a-zA-Z ]", " "); 
			
				BufferedWriter out = new BufferedWriter(new FileWriter(dest));
				
				InputStream is = new FileInputStream(filePath+"en-token.bin");
				
				TokenizerModel model = new TokenizerModel(is);
	 
				Tokenizer tokenizer = new TokenizerME(model);
	 
				String tokens[] = tokenizer.tokenize(eachIssueFilterPunc);
	 
				for (String a : tokens){
//					EnglishStemmer stemmer = new EnglishStemmer();
					PorterStemmer stemmer = new PorterStemmer();
					stemmer.setCurrent(a); //set string you need to stem
					stemmer.stem();  //stem the word
					
					tmpProcessedString = tmpProcessedString+ " "+ stemmer.getCurrent(); 
//					out.newLine();
					
				}
					
	 			out.write(tmpProcessedString);
				bugIssueData.get(i).setStrProcessedText(tmpProcessedString);
				
				is.close();
				out.close(); 
				
			
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			
			
		}
		
//		------------------- Debug Console output ------------------------
	    System.out.println("Preprocessing completed successfully");
	   
	
	}
	
	
	
 public void funcTopicAnalysis (int numTrainingWeeks,
			int commentAnalysisUptoWeek,
			int numTopics,
			double taAlpha,
			int taIteration){
		
		
		File results = new File (topicFilesFilePath); 
		
		
		if (!results.exists() || !results.isDirectory()){
			new File(topicFilesFilePath).mkdir();
		}

		try {
			malletTopicAnalysis mTA = new malletTopicAnalysis(); 
			mTA.topicAnalysis (bugIssueData, topicFilesFilePath, topicFilesFilePath+"/", "All Topic Info.xls",  numTopics, taAlpha, taIteration);
			mTA.bugissuedataTopicLabelOrganize (); 
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
	} 
	
}// end of class	
	
	
	