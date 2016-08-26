package part1;

import java.util.ArrayList;

public class Classifier {

	//------------------------------
	// Global Constants
	//------------------------------
	//These are all known constants from the given data
	final int NUM_SPAM_INSTANCES = 51;
	final int NUM_NOT_SPAM_INSTANCES = 149;
	final int TOTAL_NUM_INSTANCES = 200;
	
	//------------------------------
	// Global Fields
	//------------------------------
	ArrayList<Instance> instances;
	
	/*
	 * Constructor for the classifier.
	 * Simply sets the instances to the given instances
	 */
	public Classifier(ArrayList<Instance> instances) {
		this.instances = instances;
	}
	
	/*
	 * Naives Bayes -> P(Class|Data) = P(Data|Class) X P(C)
	 * With the data -> P(C|f1,f2,f3,...,f12) = P(f1|C) X P(f2|C) X P(f3|C) X ... X P(f4|C) X P(C)
	 * f1 to f12 -> features of the instance from the data file (1 or 0)
	 * C -> The class -> spam or not spam -> 1 or 0
	 * Don't have a denominator because it is always the same
	 * 
	 * Check the probability that the instance is spam
	 * Check the probability that the instance is not spam
	 * Which ever number is bigger is the result
	 * Return true for spam and false for not spam
	 */
	public boolean classify(Instance instance) {
		//initialize fields
		double classifySpam = 1.0;
		double classifyNotSpam = 1.0;
		
		//Calculate the probability for the email to be spam and not to be spam
		for(int i = 0; i < 12; i++) {
			classifySpam = classifySpam * probability(i, instance.get(i), 1);
			classifyNotSpam = classifyNotSpam * probability(i, instance.get(i), 0);
		}
		
		//Find the percentage
		classifySpam = classifySpam * NUM_SPAM_INSTANCES / TOTAL_NUM_INSTANCES;
		classifyNotSpam = classifyNotSpam * NUM_NOT_SPAM_INSTANCES / TOTAL_NUM_INSTANCES;
		
		//See if the email is spam or not
		if(classifySpam > classifyNotSpam) {
			System.out.println("The email is spam!\n");
			System.out.println("Probability spam: "+classifySpam);
			System.out.println("Probability not spam: " +classifyNotSpam);
			return true;
		}
		else if(classifySpam < classifyNotSpam) {
			System.out.println("The email is NOT spam!\n");
			System.out.println("Probability spam: "+classifySpam);
			System.out.println("Probability not spam: " +classifyNotSpam);
			return false;
		}
		else { //if they are equal. Should never happen, but there is theoretically a chance
			System.out.println("Cannot classify!\n");
			return false;
		}
	}
	
	/*
	 * featureIndexToCheck -> 0 to 12
	 * actualFeatureIndexValue -> 1 or 0
	 * classToCheck -> checking for spam (1) or not spam (0)
	 */
	public double probability(int featureIndexToCheck, int actualFeatureValue, int classToCheck) {
		//initialize fields
		double proba = 0.0;
		double correct = 0;
		double total = 0;
		
		for(Instance i : instances) { //for every instance
			if(i.get(12) == classToCheck) { //check if it's part of the class to check
				if(i.get(featureIndexToCheck) == actualFeatureValue) { //if the features of the two are the same
					correct++; //increment correct count
				}
				total++; //increment the total that had the correct class
			}
		}
		proba = correct/total;
		return proba;
	}
}
