
To run the program:

- Copy all the files (.java files and given Dataset files) to one folder.
- Move inside the folder.
- In command line give following commands(Also can be seen in results images):

Compile:

javac -d . NaiveBayesExample.java NaiveBayesClassifier.java WordClass.java DocumentClass.java 

Run:

java NaiveBayes.NaiveBayesExample train_label.csv train_data.csv test_label.csv test_data.csv vocabulary.txt map.csv

