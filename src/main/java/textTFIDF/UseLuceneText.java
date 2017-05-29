package textTFIDF;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.DFISimilarity;
import org.apache.lucene.search.similarities.TFIDFSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import dataPreprocess.DataIssueTemplate;

public class UseLuceneText {

	ArrayList<Issue> documents = null;
	Analyzer analyzer = null;
	Directory directory = null;
	IndexWriter iwriter = null;
	
	double threshold = (double) 0.00;

	public UseLuceneText(ArrayList<Issue> documents, double threshold) {
		this.documents = documents;
		this.threshold = threshold;
	}

	public void buildInLuceneIndexes() {

		try {
			analyzer = new StandardAnalyzer();
			directory = new RAMDirectory();

//			 ClassicSimilarity TFIDF = new ClassicSimilarity();
			BM25Similarity TFIDF = new BM25Similarity();

			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			config.setSimilarity(TFIDF);

			iwriter = new IndexWriter(directory, config);

			Issue currentFile = null;
			int docCounter = 0;
			for (int i = 0; i < this.documents.size(); i++) {
				currentFile = this.documents.get(i);

				if (!currentFile.getprocessedString().trim().isEmpty()) {
					addDocsInLuceneIndexes(currentFile.getissueId(), currentFile.getprocessedString());
					docCounter++;

				} else {
					System.out.println(currentFile.getissueId() + "- processed string empty");
					continue;
				}
			}
			iwriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void closeLuceneIndexes() {
		try {
			directory.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addDocsInLuceneIndexes(String docId, String docContents) {
		try {
			Document doc = new Document();

			Field idField = new StringField("documentId", docId, Field.Store.YES);
			doc.add(idField);

			Field contentField = new TextField("content", docContents, Field.Store.YES);
			doc.add(contentField);

			iwriter.addDocument(doc);
		} catch (Exception e) {
			System.out.println("Got an Exception while adding a document in the index: " + e.getMessage());
		}
	}

	public ArrayList<Issue> searchInLuceneIndexes(String processedText) {

//		HashMap<String, Float> matchedIssues = new HashMap<String, Float>();
		
		ArrayList<Issue> matchedIssues = new ArrayList<Issue>();
		ArrayList<LuceneScores> allScores = new ArrayList<LuceneScores>();

		try {
			DirectoryReader ireader = DirectoryReader.open(directory);
			IndexSearcher isearcher = new IndexSearcher(ireader);

//			 ClassicSimilarity TFIDF = new ClassicSimilarity();
//			 isearcher.setSimilarity(TFIDF);

			BM25Similarity bm25 = new BM25Similarity();
			isearcher.setSimilarity(bm25);

			/*
			 * DFISimilarity dfis = new DFISimilarity(null);
			 * isearcher.setSimilarity(dfis);
			 */

			QueryParser parser = new QueryParser("content", analyzer);
			Query query = parser.parse(processedText);

			ScoreDoc[] hits = isearcher.search(query, documents.size()).scoreDocs;

			// System.out.println("UseLucene - Number of documents passed-" +
			// documents.size());
			// System.out.println("Use Luceen - Search results hits size" +
			// hits.length);

			for (int i = 0; i < hits.length; i++) {

				float temp = hits[i].score;
				Document hitDoc = isearcher.doc(hits[i].doc);

				allScores.add(new LuceneScores(hitDoc.getField("documentId").stringValue(), temp));
				//matchedIssues.put(hitDoc.getField("documentId").stringValue(), temp);

				// System.out.println("Matched issue (use lucene):
				// "+hitDoc.getField("documentId").stringValue());
				// System.out.println(hitDoc.getField("documentId").stringValue()
				// + " || Score: " + temp);
				// System.out.println(hitDoc.getField("content"));
				// System.out.print(" || Score: " + temp);
			}
			ireader.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (org.apache.lucene.queryparser.classic.ParseException e) {
			e.printStackTrace();
		}

		Collections.sort(allScores, new LuceneScoreComparator());

		double max = 0.00;
		
		// Finding max value
		for (int i = 0; i < allScores.size(); i++) {
			max = Math.max(max, allScores.get(i).getdocumentSimilarityValue());
		}
		
		// Normalizing : Dividing all similarity values by max
		for (int i = 0; i < allScores.size(); i++) {
			allScores.get(i).setDocumentSimilarityValue(allScores.get(i).getdocumentSimilarityValue() / max);
//			System.out.println(allScores.get(i).getdocumentSimilarityValue());
		}
		
		// If sim > threshold, add to matchedIssues
		for (int i = 0; i < allScores.size(); i++) {
			if(allScores.get(i).getdocumentSimilarityValue() > this.threshold){
//				matchedIssues.put(allScores.get(i).getdoucmentName(), (float) allScores.get(i).getdocumentSimilarityValue());
				
				for(Issue iterator: documents){
					if (iterator.getissueId().matches(allScores.get(i).getdoucmentName())){
						iterator.similarityvalue = (float) allScores.get(i).getdocumentSimilarityValue();
						matchedIssues.add(iterator);
					}
				}
			}
		}

		StringBuilder sbLuceneScoreSortTest = new StringBuilder();
		sbLuceneScoreSortTest.append("Max" + "," + max + "\n");

		for (int i = 0; i < allScores.size(); i++) {
			sbLuceneScoreSortTest.append(
					allScores.get(i).getdoucmentName() + "," + allScores.get(i).getdocumentSimilarityValue() + "\n");
		}

//		try {
//			PrintWriter pw = new PrintWriter("test-lucene-scores.csv");
//			pw.write(sbLuceneScoreSortTest.toString());
//
//			pw.flush();
//			pw.close();
//
//		} catch (FileNotFoundException fen) {
//			fen.printStackTrace();
//
//		} catch (NullPointerException ne) {
//			ne.printStackTrace();
//		}
		
		allScores.clear();
		return matchedIssues;

	}

}
