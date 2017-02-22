package NaiveBayes;

import java.util.*;

public class DocumentClass {
	
	private int Document_Index;
	int totalWordCount = 0;
	private List <WordClass> token;
	
	public DocumentClass(int Document_Index) 
	{
		this.Document_Index = Document_Index;
		token = new ArrayList <WordClass> ();
	}

	public List<WordClass> getTokens() {
		return token;
	}
	
	public int getTotalWords() {
		return totalWordCount;
	}

	public void setTotalWords(int totalWords) {
		this.totalWordCount = totalWords;
	}
	public void addWords(WordClass word, int count){
		
		token.add (word);
		totalWordCount += count;
	}

	public int getDocument_Index() {
		return Document_Index;
	}

	public void setDocument_Index(int documentIdx) {
		this.Document_Index = documentIdx;
	}	
}
