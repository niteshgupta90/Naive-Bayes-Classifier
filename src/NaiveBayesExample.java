package NaiveBayes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class NaiveBayesExample {
	
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		if (args.length!=6)	
		{		
			System.out.println("Please pass all the six files: vocabulary.txt, map.csv, train_label.csv, train_data.csv, test_label.csv, test_data.csv.");
		}
		String trainLabel = args[0];
		String trainData = args[1];
		String testLabel = args[2];
		String testData = args[3];
		String vocabulary = args[4];
		String map = args[5];
		
		NaiveBayesClassifier naiveBayes = new NaiveBayesClassifier (trainLabel, trainData, testLabel, testData, vocabulary, map);
		
		naiveBayes.probabilityOfPriors();
		
		naiveBayes.probabilityOfMaximumLikelihood();
		
		naiveBayes.probabilityOfByesianEstimate();
		
		HashMap <Integer, ArrayList <Integer>> trainEstimateLabelBE = naiveBayes.predictClass(naiveBayes.train_data, naiveBayes.probabilityByesianEstimate);
		HashMap <Integer, ArrayList <Integer>> testEstimateLabelBE = naiveBayes.predictClass(naiveBayes.test_data, naiveBayes.probabilityByesianEstimate);
		HashMap <Integer, ArrayList <Integer>> testEstimateLabelMLE = naiveBayes.predictClass(naiveBayes.test_data, naiveBayes.probailityMaximumLikelihood);


		naiveBayes.getPerformanceDataTrainingSetBE (naiveBayes.train_label, trainEstimateLabelBE, "trainBE");
		
		naiveBayes.getPerformanceDataTestSetBE (naiveBayes.test_label, testEstimateLabelBE, "testBE");
		
		naiveBayes.getPerformanceDataTestSetMLE (naiveBayes.test_label, testEstimateLabelMLE, "testMLE");	
	}

}
