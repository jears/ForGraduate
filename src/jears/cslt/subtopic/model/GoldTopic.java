package jears.cslt.subtopic.model;

import java.util.ArrayList;
import java.util.List;

/**
 * topic class
 * @author jj
 *
 */

public class GoldTopic {
	private String topicNo;
	private String query;
	private List<Intent> intents;
	
	public GoldTopic(String n, String q) {	
		topicNo = n;
		query = q;
		intents = new ArrayList<Intent>();
	}
	
	public GoldTopic(String n, String q, String source) {
		this.topicNo = n;
		this.query = q;
	}
	
	public void addIntent(Intent intent) {
		intents.add(intent);
	}
	
	public String getQuery() {
		return query;
	}

	public String getNo() {
		return topicNo;
	}
	
	public int getIntentNum() {
		return intents.size();
	}
	
	public String getIprob() {
		String ret = "";
		for (Intent temp : intents) {
			ret += temp.getIprob();
		}
		return ret;
	}
	
	public String getDqrel() {
		String ret = "";
		for (Intent temp : intents) {
			ret += temp.getDqrel();
		}
		return ret;
	}
	
	public List<Intent> getIntents() {
		return intents;
	}
}
