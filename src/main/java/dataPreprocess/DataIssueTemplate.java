package dataPreprocess;

import java.util.ArrayList;
import java.util.Date;

public class DataIssueTemplate {
	
	private String strProject; 
	private String strKey; 
	private String strIssueType; 
	private String strStatus; 
	
	private Date dateCreated; 
	private Date dateUpdated; 
	
	private Date dateResolved; 
	
	private String strAffectVersion; 
	private String strFixVersion;
	
	private String strSummary; 
	private String strDescription; 
	private String strPriority; 
	private String strResolution; 
	
	private String strAssignee; 
	private String strReporter; 
	private String strCreator; 
	
	private String strComponent; 
	private String strEnvironment;
	
	private double dblNumberofFiles; 
	private double dblAdditionChurn;
	private double dblDeletionChurn; 
	
	private String strProcessedText; 
	
	
	public ArrayList <issueTopicInfo> alIssueTopicInfo = new ArrayList <issueTopicInfo> (); 
	public issueTopicInfo maxSimilarTopicInfo = new issueTopicInfo();
	
	
	
	
	
	
	
	/**
	 * @return the strProject
	 */
	public String getStrProject() {
		return strProject;
	}
	/**
	 * @param strProject the strProject to set
	 */
	public void setStrProject(String strProject) {
		this.strProject = strProject;
	}
	/**
	 * @return the strKey
	 */
	public String getStrKey() {
		return strKey;
	}
	/**
	 * @param strKey the strKey to set
	 */
	public void setStrKey(String strKey) {
		this.strKey = strKey;
	}
	/**
	 * @return the strIssueType
	 */
	public String getStrIssueType() {
		return strIssueType;
	}
	/**
	 * @param strIssueType the strIssueType to set
	 */
	public void setStrIssueType(String strIssueType) {
		this.strIssueType = strIssueType;
	}
	/**
	 * @return the strStatus
	 */
	public String getStrStatus() {
		return strStatus;
	}
	/**
	 * @param strStatus the strStatus to set
	 */
	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}
	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}
	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	/**
	 * @return the dateUpdated
	 */
	public Date getDateUpdated() {
		return dateUpdated;
	}
	/**
	 * @param dateUpdated the dateUpdated to set
	 */
	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
	/**
	 * @return the dateResolved
	 */
	public Date getDateResolved() {
		return dateResolved;
	}
	/**
	 * @param dateResolved the dateResolved to set
	 */
	public void setDateResolved(Date dateResolved) {
		this.dateResolved = dateResolved;
	}
	/**
	 * @return the strAffectVersion
	 */
	public String getStrAffectVersion() {
		return strAffectVersion;
	}
	/**
	 * @param strAffectVersion the strAffectVersion to set
	 */
	public void setStrAffectVersion(String strAffectVersion) {
		this.strAffectVersion = strAffectVersion;
	}
	/**
	 * @return the strFixVersion
	 */
	public String getStrFixVersion() {
		return strFixVersion;
	}
	/**
	 * @param strFixVersion the strFixVersion to set
	 */
	public void setStrFixVersion(String strFixVersion) {
		this.strFixVersion = strFixVersion;
	}
	/**
	 * @return the strSummary
	 */
	public String getStrSummary() {
		return strSummary;
	}
	/**
	 * @param strSummary the strSummary to set
	 */
	public void setStrSummary(String strSummary) {
		this.strSummary = strSummary;
	}
	/**
	 * @return the strDescription
	 */
	public String getStrDescription() {
		return strDescription;
	}
	/**
	 * @param strDescription the strDescription to set
	 */
	public void setStrDescription(String strDescription) {
		this.strDescription = strDescription;
	}
	/**
	 * @return the strPriority
	 */
	public String getStrPriority() {
		return strPriority;
	}
	/**
	 * @param strPriority the strPriority to set
	 */
	public void setStrPriority(String strPriority) {
		this.strPriority = strPriority;
	}
	/**
	 * @return the strResolution
	 */
	public String getStrResolution() {
		return strResolution;
	}
	/**
	 * @param strResolution the strResolution to set
	 */
	public void setStrResolution(String strResolution) {
		this.strResolution = strResolution;
	}
	/**
	 * @return the strAssignee
	 */
	public String getStrAssignee() {
		return strAssignee;
	}
	/**
	 * @param strAssignee the strAssignee to set
	 */
	public void setStrAssignee(String strAssignee) {
		this.strAssignee = strAssignee;
	}
	/**
	 * @return the strReporter
	 */
	public String getStrReporter() {
		return strReporter;
	}
	/**
	 * @param strReporter the strReporter to set
	 */
	public void setStrReporter(String strReporter) {
		this.strReporter = strReporter;
	}
	/**
	 * @return the strCreator
	 */
	public String getStrCreator() {
		return strCreator;
	}
	/**
	 * @param strCreator the strCreator to set
	 */
	public void setStrCreator(String strCreator) {
		this.strCreator = strCreator;
	}
	/**
	 * @return the strComponent
	 */
	public String getStrComponent() {
		return strComponent;
	}
	/**
	 * @param strComponent the strComponent to set
	 */
	public void setStrComponent(String strComponent) {
		this.strComponent = strComponent;
	}
	/**
	 * @return the strEnvironment
	 */
	public String getStrEnvironment() {
		return strEnvironment;
	}
	/**
	 * @param strEnvironment the strEnvironment to set
	 */
	public void setStrEnvironment(String strEnvironment) {
		this.strEnvironment = strEnvironment;
	}
	public double getDblNumberofFiles() {
		return dblNumberofFiles;
	}
	public void setDblNumberofFiles(double dblNumberofFiles) {
		this.dblNumberofFiles = dblNumberofFiles;
	}
	public double getDblAdditionChurn() {
		return dblAdditionChurn;
	}
	public void setDblAdditionChurn(double dblAdditionChurn) {
		this.dblAdditionChurn = dblAdditionChurn;
	}
	public double getDblDeletionChurn() {
		return dblDeletionChurn;
	}
	public void setDblDeletionChurn(double dblDeletionChurn) {
		this.dblDeletionChurn = dblDeletionChurn;
	}
	public String getStrProcessedText() {
		return strProcessedText;
	}
	public void setStrProcessedText(String strProcessedText) {
		this.strProcessedText = strProcessedText;
	}
}



