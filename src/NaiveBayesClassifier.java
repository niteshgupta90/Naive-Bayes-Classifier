package NaiveBayes;

import NaiveBayes.DocumentClass;
import NaiveBayes.WordClass;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class NaiveBayesClassifier {
	
	//Initialization 
	HashMap <Integer, ArrayList <Integer>> train_label = new HashMap <Integer, ArrayList <Integer>> ();	
	HashMap <Integer, ArrayList <Integer>> test_label = new HashMap <Integer, ArrayList <Integer>> ();			
	HashMap <Integer, DocumentClass> train_data = new HashMap <Integer, DocumentClass> ();		
	HashMap <Integer, DocumentClass> test_data = new HashMap <Integer, DocumentClass> ();
	int vocabWordsLength=0,categoriesNumber=0;		
	
	double[] probabilityOfPrior;
	double[][] probailityMaximumLikelihood;
	double[][] probabilityByesianEstimate;
	
	//Print Accuracy Data and Confusion Matrix
	public void printTrainingOverallAccuracy(double totalCorrectDocuments, double totalDocuments){
		double overallAccuracy = 0.0;
		overallAccuracy = totalCorrectDocuments / totalDocuments;
		System.out.printf("\n\nOverall Accuracy for Training Data = %.4f\n\n",overallAccuracy);
	}

	public void printTrainingClassAccuracy(double[] classAccuracy){
		int i =0;
		System.out.println("Class Accuracy for Training Data:\n");
		for (i = 0; i < classAccuracy.length; i++)
            System.out.printf("Group %2d = %.4f\n",i+1, classAccuracy[i]);
		System.out.println("\n");

	}
	
	public void printTrainingConfusionMatrix(HashMap <Integer, ArrayList <Integer>> correctLabel , HashMap <Integer, ArrayList <Integer>> estimateLabel){
		int i=0, j=0;
        int matrix[][] = new int[categoriesNumber][categoriesNumber];
		System.out.println("Confusion Matrix for Training Data:\n");
        for(i = 0; i < matrix.length; i++)
        {
        	List <Integer> actualDocs = correctLabel.get (i+1);
        	for(j = 0; j < matrix.length; j++)
        	{
                List <Integer> value = new ArrayList<>(estimateLabel.get(j+1));
                value.retainAll(actualDocs);
                matrix[i][j] = value.size();
                System.out.printf("%5d ", matrix[i][j]);
        	}
        	System.out.println('\n');
        }
	}	
	
	//Print Accuracy Data and Confusion Matrix
	public void printTestOverallAccuracy(double totalCorrectDocuments, double totalDocuments){
		double overallAccuracy = 0.0;
		overallAccuracy = totalCorrectDocuments / totalDocuments;
		System.out.printf("\n\nOverall Accuracy for Test Data = %.4f\n\n",overallAccuracy);
	}

	public void printTestClassAccuracy(double[] classAccuracy){
		int i =0;
		System.out.println("Class Accuracy for Test Data:\n");
		for (i = 0; i < classAccuracy.length; i++)
            System.out.printf("Group %2d = %.4f\n",i+1, classAccuracy[i]);
		System.out.println("\n");

	}
	
	public void printTestConfusionMatrix(HashMap <Integer, ArrayList <Integer>> correctLabel , HashMap <Integer, ArrayList <Integer>> estimateLabel){
		int i=0, j=0;
        int matrix[][] = new int[categoriesNumber][categoriesNumber];
		System.out.println("Confusion Matrix for Test Data:\n");
        for(i = 0; i < matrix.length; i++)
        {
        	List <Integer> actualDocs = correctLabel.get (i+1);
        	for(j = 0; j < matrix.length; j++)
        	{
                List <Integer> value = new ArrayList<>(estimateLabel.get(j+1));
                value.retainAll(actualDocs);
                matrix[i][j] = value.size();
                System.out.printf("%5d ", matrix[i][j]);
        	}
        	System.out.println('\n');
        }
	}
	
	//Print Accuracy Data and Confusion Matrix
	public void printTestOverallAccuracyMLE(double totalCorrectDocuments, double totalDocuments){
		double overallAccuracy = 0.0;
		overallAccuracy = totalCorrectDocuments / totalDocuments;
		System.out.printf("\n\nOverall Accuracy for Test Data using MLE = %.4f\n\n",overallAccuracy);
	}

	public void printTestClassAccuracyMLE(double[] classAccuracy){
		int i =0;
		System.out.println("Class Accuracy for Test Data using MLE:\n");
		for (i = 0; i < classAccuracy.length; i++)
            System.out.printf("Group %2d = %.4f\n",i+1, classAccuracy[i]);
		System.out.println("\n");

	}
	
	public void printTestConfusionMatrixMLE(HashMap <Integer, ArrayList <Integer>> correctLabel , HashMap <Integer, ArrayList <Integer>> estimateLabel){
		int i=0, j=0;
        int matrix[][] = new int[categoriesNumber][categoriesNumber];
		System.out.println("Confusion Matrix for Test Data using MLE:\n");
        for(i = 0; i < matrix.length; i++)
        {
        	List <Integer> actualDocs = correctLabel.get (i+1);
        	for(j = 0; j < matrix.length; j++)
        	{
                List <Integer> value = new ArrayList<>(estimateLabel.get(j+1));
                value.retainAll(actualDocs);
                matrix[i][j] = value.size();
                System.out.printf("%5d ", matrix[i][j]);
        	}
        	System.out.println('\n');
        }
	}	
	//Constructor
	public NaiveBayesClassifier(String trainLabel, String trainData, String testLabel, String testData, String vocabData, String map) throws NumberFormatException, IOException
	{
		int class_label=0;
		int doc_label=1;
		int doc_Index = 0;
		int word_Index = 0;
		int frequency = 0;
		String line;
		String splitBy = ",";
		BufferedReader fileRead = null;

		//Store Training data
		fileRead = new BufferedReader(new FileReader(trainData));
		while ((line = fileRead.readLine()) != null)
		{
          	String[] docData = line.split(splitBy);
			doc_Index = Integer.parseInt(docData[0]);
			word_Index = Integer.parseInt(docData[1]);
			frequency = Integer.parseInt(docData[2]);
			DocumentClass document = train_data.get(doc_Index);
			if (document == null )
			{
				document = new DocumentClass (doc_Index);
				train_data.put (doc_Index, document);
			}
			WordClass word = new WordClass(word_Index, frequency);
			document.addWords(word, frequency);							
		
		}
		fileRead.close();
		
		//Store Train Data
		fileRead = new BufferedReader(new FileReader (testData));
		while ((line = fileRead.readLine()) != null)
		{	
          	String[] docData = line.split(splitBy);
			doc_Index = Integer.parseInt(docData[0]);
			word_Index = Integer.parseInt(docData[1]);
			frequency = Integer.parseInt(docData[2]);
			DocumentClass document = test_data.get(doc_Index);
			if (document == null )
			{
				document = new DocumentClass(doc_Index);
				test_data.put(doc_Index, document);
			}
			WordClass word = new WordClass(word_Index, frequency);
			document.addWords(word, frequency);									
		}
		fileRead.close();
		
		//read number of classes and words
		fileRead = new BufferedReader (new FileReader (map));
		while (fileRead.readLine() != null) 
			categoriesNumber++;
		fileRead.close();
		fileRead = new BufferedReader (new FileReader (vocabData));
		while (fileRead.readLine() != null) 
			vocabWordsLength++;
		fileRead.close();
			
		//Categorize Training documents in class
		fileRead = new BufferedReader(new FileReader(trainLabel));
		while ((line = fileRead.readLine()) != null)
		{
			class_label = Integer.parseInt(line);
			if (!train_label.containsKey(class_label))
				train_label.put (class_label, new ArrayList <Integer>());
			train_label.get (class_label).add(doc_label++);					
		}
		fileRead.close();
		
		//Categorize Test documents in class
		fileRead = new BufferedReader(new FileReader(testLabel));
		doc_label = 1;
		while ((line = fileRead.readLine()) != null)
		{
			class_label = Integer.parseInt (line);
			if (! test_label.containsKey (class_label))
				test_label.put (class_label, new ArrayList<Integer>());
			test_label.get(class_label).add(doc_label++);				
		
		}
		fileRead.close();		
	}
	
	//calculate probability of MaximumLikelihood
	public void probabilityOfMaximumLikelihood()
	{
		int i=0, j=0;
		double totalWordCount = 0.0; 											
		int tokenCount[] = new int[vocabWordsLength];
		probailityMaximumLikelihood = new double[categoriesNumber][vocabWordsLength];
		
		for (i = 0; i < categoriesNumber; i++)
		{
			List<Integer> documnet_List = train_label.get(i+1);			
			for (j = 0; j < documnet_List.size (); j++)
			{
				DocumentClass doc = train_data.get(documnet_List.get(j));
				totalWordCount += doc.getTotalWords();
				List <WordClass> words_List = doc.getTokens();
				for (int k = 0; k < words_List.size(); k++)
				{
					WordClass word = words_List.get(k);
					tokenCount[word.getWord_Index()-1] +=word.getCount();
				}
			}
			for (j = 0; j < vocabWordsLength; j++)
			{
				probailityMaximumLikelihood[i][j] = tokenCount[j] / totalWordCount;
			}
		}	
	}
	
	//calculate probability of BayesianEstimate
	public void probabilityOfByesianEstimate()
	{
		int i=0, j=0;

		probabilityByesianEstimate = new double[categoriesNumber][vocabWordsLength];
		
		for (i = 0; i < categoriesNumber; i++)
		{
			double totalWordCount = 0.0; 
			List<Integer> documnet_List = train_label.get(i+1);											
			int tokenCount[] = new int[vocabWordsLength];
			for (j = 0; j < documnet_List.size (); j++)
			{
				DocumentClass doc = train_data.get(documnet_List.get(j));
				totalWordCount += doc.getTotalWords();
				List <WordClass> words_List = doc.getTokens();
				for (int k = 0; k < words_List.size(); k++)
				{
					WordClass word = words_List.get(k);
					tokenCount[word.getWord_Index()-1] +=word.getCount();
				}
			}
			for (j = 0; j < vocabWordsLength; j++)
			{
				probabilityByesianEstimate[i][j] = (tokenCount[j]+1) / (totalWordCount+vocabWordsLength);
			}
		}	
	}
	
	// Get Estimated class for Test Document
	public HashMap<Integer, ArrayList<Integer>> predictClass(HashMap<Integer, DocumentClass> testDoc, double[][] predictClass)
	{
		int i =0;
		int classLabel = 0;
		HashMap<Integer, ArrayList <Integer>> estimateLabel = new HashMap<Integer, ArrayList <Integer>> ();
		List<Double> predictionList = new ArrayList<Double>();	
		double value = 0.0, maxValue = 0.0;
		
        for(HashMap.Entry<Integer, DocumentClass> entry : testDoc.entrySet())
		{
			DocumentClass document = entry.getValue();
			for (i = 0; i < categoriesNumber; i++)
			{
				List <WordClass> tokenCount = document.getTokens();
				value += Math.log(probabilityOfPrior[i]);
				for (WordClass token : tokenCount ){
					double loglikelihood = Math.log(predictClass[i][token.getWord_Index()-1]);
					value += (token.getCount() * loglikelihood);
				}
				predictionList.add(value);
				value = 0.0;
			}
			maxValue = Collections.max (predictionList);
			classLabel = predictionList.indexOf(maxValue) + 1;
			if (!estimateLabel.containsKey(classLabel))
				estimateLabel.put(classLabel, new ArrayList<Integer>());
			estimateLabel.get (classLabel).add(document.getDocument_Index());
			predictionList = new ArrayList<Double>();
		}
		return estimateLabel;
	}
	
	//Wrappers
	public void getPerformanceDataTrainingSetBE(HashMap <Integer, ArrayList <Integer>> correctLabel , HashMap <Integer, ArrayList <Integer>> estimateLabel, String dataType)
	{
		getPerformanceData(correctLabel,estimateLabel, dataType);
	}
	
	public void getPerformanceDataTestSetBE(HashMap <Integer, ArrayList <Integer>> correctLabel , HashMap <Integer, ArrayList <Integer>> estimateLabel, String dataType)
	{
		getPerformanceData(correctLabel,estimateLabel, dataType);
	}
	
	public void getPerformanceDataTestSetMLE(HashMap <Integer, ArrayList <Integer>> correctLabel , HashMap <Integer, ArrayList <Integer>> estimateLabel, String dataType)
	{
		getPerformanceData(correctLabel,estimateLabel, dataType);
	}
	
	//Performance Evaluation
	public void getPerformanceData (HashMap <Integer, ArrayList <Integer>> correctLabel , HashMap <Integer, ArrayList <Integer>> estimateLabel, String dataType)
	{
		int i = 1;
		double totalCorrectDocuments = 0.0;
		double totalDocuments = 0.0;
		double[] classAccuracy = new double[categoriesNumber];
		String[] type = {"trainBE", "testBE", "testMLE"};
		for(i = 1; i <= categoriesNumber; i++)
        {
        	ArrayList <Integer> actualDocuments = correctLabel.get(i);
        	ArrayList <Integer> estimateDocuments = estimateLabel.get(i);
        	ArrayList <Integer> correctDocuments = new ArrayList<>(estimateDocuments);
        	correctDocuments.retainAll(actualDocuments); 
			totalCorrectDocuments += correctDocuments.size();
        	totalDocuments += actualDocuments.size();
        	
			classAccuracy[i-1] = (double)correctDocuments.size()/actualDocuments.size();
        }
		
		if(dataType.equals(type[0])){
			printTrainingOverallAccuracy(totalCorrectDocuments,totalDocuments);			
			printTrainingClassAccuracy(classAccuracy);			
			printTrainingConfusionMatrix(correctLabel,estimateLabel);
		}
		else if(dataType.equals(type[1])){
			printTestOverallAccuracy(totalCorrectDocuments,totalDocuments);			
			printTestClassAccuracy(classAccuracy);			
			printTestConfusionMatrix(correctLabel,estimateLabel);
		}
		else if(dataType.equals(type[2])){
			printTestOverallAccuracyMLE(totalCorrectDocuments,totalDocuments);			
			printTestClassAccuracyMLE(classAccuracy);			
			printTestConfusionMatrixMLE(correctLabel,estimateLabel);
		}
	}
	
	//Store priorProbabilities
	public void probabilityOfPriors ()
	{
		int i=0;
		probabilityOfPrior = new double[categoriesNumber];
		int totalDocs = train_data.size ();
		System.out.println("\nClass Priors:\n");
		for (i = 0; i < categoriesNumber; i++)
		{
			probabilityOfPrior [i] = train_label.get(i+1).size() / (double) totalDocs;
			System.out.printf("P(Omega = %2d): %.4f\n", i+1, probabilityOfPrior[i]);
		}			
	}
}
