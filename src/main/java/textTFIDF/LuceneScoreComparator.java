package textTFIDF;

import java.util.Comparator;

public class LuceneScoreComparator  implements Comparator<LuceneScores> 
{
	public int compare(LuceneScores doc1, LuceneScores doc2)
	{
		double val1=0.0,val2=0.0;
		
		val1 = doc1.getdocumentSimilarityValue();
		val2 = doc2.getdocumentSimilarityValue();
		 
		return (-1)*Double.compare(val1, val2);
	}

}