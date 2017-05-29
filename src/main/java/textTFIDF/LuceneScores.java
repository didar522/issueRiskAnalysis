package textTFIDF;

public class LuceneScores {
	String doucmentName;
	double documentSimilarityValue;
	
	public LuceneScores(String doucmentName, double documentSimilarityValue){
		
		this.documentSimilarityValue = documentSimilarityValue;
		this.doucmentName = doucmentName;
	}
	
	public void setDocumentName(String documentName){
		this.doucmentName = documentName;
	}
	
	public void setDocumentSimilarityValue(double documentSimilarityValue){
		this.documentSimilarityValue = documentSimilarityValue;
	}
	
	public String getdoucmentName(){
		return this.doucmentName;
	}
	
	public double getdocumentSimilarityValue(){
		return this.documentSimilarityValue;
	}
}
