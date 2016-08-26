package part1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class NaiveBayes {

	public static ArrayList<Instance> testing = new ArrayList<Instance>();
	public static ArrayList<Instance> training = new ArrayList<Instance>();
	
	public static ArrayList<Boolean> classifiedEmail = new ArrayList<Boolean>();
	public static ArrayList<Integer> classifiedEmailInteger = new ArrayList<Integer>();
	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		File trainingFile = new File(args[0]);
		File testingFile = new File(args[1]);
		
		//File trainingFile = new File("spamLabelled.dat");
		//File testingFile = new File("spamUnlabelled.dat");
		
		System.out.println("Loading Training Data...");
		readTrainingData(trainingFile);
		
		Classifier classifier = new Classifier(training);
		
		System.out.println("Loading Testing Data...");
		readTestingData(testingFile);
		System.out.println("CLASSYING EMAILS\n");
		
		int count = 1;
		for(Instance i : testing) {
			System.out.println("Classifying email " +count+ "...");
			boolean spamOrNot = classifier.classify(i);
			classifiedEmail.add(spamOrNot);
			count++;
			System.out.println("----------");
		}

		//makeBooleansIntegers();
		//makeOutputFile(testingFile);
	}
	
	/*
	 * Makes true into a 1 and false into a 0 in the respective arrays
	 */
	private static void makeBooleansIntegers() {
		for(boolean b : classifiedEmail) {
			if(b == true) {
				classifiedEmailInteger.add(1);
			}
			else {
				classifiedEmailInteger.add(0);
			}
		}
	}

	/*
	 * Makes and print to an output file
	 * Output file is the unlabelled data with another coloumn for the classification
	 * 1 for spam and 0 for not spam
	 */
	private static void makeOutputFile(File file) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("sampleoutput.txt", "UTF-8");
		Scanner scData = new Scanner(file);
		
		while (scData.hasNextInt()) {
			for (int s : classifiedEmailInteger) {
				String line = "";
				for (int i = 0; i < 12; i++) {
					if(i == 0) {
						line = "" +scData.nextInt();
					}
					else {
						line += " " + scData.nextInt();
					}
					
				}
				line += " " + s;
				writer.println(line);
			}
		}
		writer.flush();
		writer.close();
		scData.close();
		System.out.println("Output file has been created and filed!");
	}

	/*
	 * Loading for the training data
	 * Loads 13 integers (last one is special to this file -> spam or not spam)
	 */
	public static void readTrainingData(File file) {
		try {
			Scanner scData = new Scanner(file);
			
			while(scData.hasNextLine()) {
				ArrayList<Integer> currLine = new ArrayList<Integer>();
				for(int i=0; i < 13; i++) {
					currLine.add(scData.nextInt());
				}
				scData.nextLine();
				training.add(new Instance(currLine));
			}
			scData.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Training Data LOADED");
	}
	
	/*
	 * Loading for the testing data
	 * Loads 12 integers (does not have weather it is spam or not -> point is to figure this out)
	 */
	public static void readTestingData(File file) {
		try {
			Scanner scData = new Scanner(file);
			
			while(scData.hasNextLine()) {
				ArrayList<Integer> currLine = new ArrayList<Integer>();
				for(int i=0; i < 12; i++) {
					currLine.add(scData.nextInt());
				}
				scData.nextLine();
				testing.add(new Instance(currLine));
			}
			scData.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Testing Data LOADED\n");
	}

	
}
