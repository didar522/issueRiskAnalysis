package textTFIDF;

import java.util.ArrayList;

import dataPreprocess.issueTopicInfo;

public class Issue {

	// private String fileName;
	int index;
	String issueId;
	String issueSummary;
	String issueDescription;
	String issueType;
	private String issuePriority; 
	String labelsTopcModelling;
	ArrayList<String> fileNames = new ArrayList<String>();
	double numberOfFiles;
	String processedString;
	public float similarityvalue; 
	
	public ArrayList <issueTopicInfo> alIssueTopicInfo = new ArrayList <issueTopicInfo> (); 
	public issueTopicInfo maxSimilarTopicInfo = new issueTopicInfo();
	

	public Issue(String issueId, String issuePriority, String issueSummary, String issueDescription,
			double numberOfFiles, String processedString, String issueType, ArrayList <issueTopicInfo> alIssueTopicInfo, issueTopicInfo maxSimilarTopicInfo) {
		this.index = index;
		this.issueId = issueId;
		this.issueSummary = issueSummary;
		this.issueDescription = issueDescription;
//		this.fileNames = fileNames;
		this.numberOfFiles = numberOfFiles;
		this.processedString = processedString;
		this.issueType = issueType;
//		this.labelsTopcModelling = labelsTopcModelling;
		this.maxSimilarTopicInfo = maxSimilarTopicInfo; 
		this.alIssueTopicInfo = alIssueTopicInfo;
		this.issuePriority = issuePriority; 
		
	}

	public int getindex() {
		return this.index;
	}

	public void setindex(Integer index) {
		this.index = index;
	}

	public String getissueId() {
		return this.issueId;
	}

	public void setissueId(String issueId) {
		this.issueId = issueId;
	}

	public String getissueSummary() {
		return this.issueSummary;
	}

	public void setissueSummary(String issueSummary) {
		this.issueSummary = issueSummary;
	}

	public String getissueDescription() {
		return this.issueDescription;
	}

	public String getlabelsTopcModelling() {
		return this.labelsTopcModelling;
	}
	
	public void setlabelsTopcModelling(String topicModelLabels){
		this.labelsTopcModelling = topicModelLabels;
	}

	public void setissueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}

	public void addfileNames(String fN) {
		this.fileNames.add(fN);
	}

	public  double getnumberOfFiles() {
		return this.numberOfFiles;
	}

	public double setnumberOfFiles() {
		return this.numberOfFiles;
	}

	public String getprocessedString() {
		return this.processedString;
	}

	public String getissueType() {
		return this.issueType;
	}

	public void setprocessedString(String processedString) {
		this.processedString = processedString;
	}

	public void updateNumberOfFiles() {
		this.numberOfFiles++;
	}

	public String getfileNames() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.fileNames.size(); i++) {
			sb.append(this.fileNames.get(i) + "\n");

		}
		return sb.toString();
	}

	public ArrayList<String> getFileList() {
		return this.fileNames;
	}

	@Override
	public String toString() {
		return "[index=" + index + ", issueId=" + issueId + ", issueSummary=" + issueSummary + ", issueDescription="
				+ issueDescription + ", fileNames:" + fileNames.toString() + ", numberofFiles=" + numberOfFiles + "]";
	}

	public String getIssuePriority() {
		return issuePriority;
	}

	public void setIssuePriority(String issuePriority) {
		this.issuePriority = issuePriority;
	}

}

