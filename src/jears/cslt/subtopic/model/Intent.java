package jears.cslt.subtopic.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Intent {
	private String topicNo;
	private String intentNo;
	private String probability;
	private String description;
	private List<String> examples;
	private int current;
	
	public Intent(String topicNo, String number, String probability, String description) {
		this.topicNo = topicNo;
		this.intentNo = number;
		this.probability = probability;
		this.description = description;
		examples = new ArrayList<String>();
		current = 0;
	}
	
	public void addExample(String example) {
		examples.add(example);
	}
	
	public void addAllExample(List<String> examples, List<String> examples_cn) {
		examples.addAll(examples);
		examples_cn.addAll(examples_cn);
	}
	
	public String getNo() {
		return intentNo;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getNoAndProb() {
		return intentNo + ";" + probability;
	}
	
	public String next() {
		if (current == examples.size())
			return null;
		else {
			++current;
			return examples.get(current);
		}
	}
	
	public void resetCurrentNo() {
		current = 0;
	}
	
	public String getIprob() {
		return topicNo + ";" + intentNo + ";" + probability + "\n";
	}
	
	public String getDqrel() {
		String ret = "";
		for (String temp : examples) {
			ret += topicNo + ";" + intentNo + ";" + temp + ";" + "L1\n";
		}
		return ret;
	}
	
	public String getExample() {
		String ret = "";
		for (String temp : examples) {
			ret += temp + "; ";
		}
		return ret;
	}
	
	public String toString() {
		String ret = "";
		
		ret += intentNo + "\n";
		ret += probability + "\n";
		ret += description + "\n";
		ret += examples + "\n";
		
		return ret;
	}
}
