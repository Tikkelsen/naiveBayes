package part1;

import java.util.ArrayList;

public class Instance {

	ArrayList<Integer> features = new ArrayList<Integer>();

	public Instance(ArrayList<Integer> features) {
		this.features = features;
	}

	/*
	 * Method to get the integer at the given value
	 */
	public Integer get(int value) {
		return features.get(value);
	}
}