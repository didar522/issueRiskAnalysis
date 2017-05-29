package dataPreprocess;

public class issueTopicInfo {
	public int topicNum; 
	public double topicSimilarity;
	public String topicLabels = ""; 
	
	public issueTopicInfo (int topicNum, double topicSimilarity){
		this.topicNum = topicNum; 
		this.topicSimilarity = topicSimilarity; 
	}
	
	public issueTopicInfo(){
		
	}
	
}