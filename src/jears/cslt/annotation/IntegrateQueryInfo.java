package jears.cslt.annotation;

import java.io.IOException;
import java.util.List;

import jears.cslt.subtopic.eval.ScoreExtractor;
import jears.cslt.subtopic.model.GoldTopic;
import jears.cslt.subtopic.model.Intent;
import jears.cslt.subtopic.model.SearchResult;
import jears.cslt.subtopic.model.SearchResultGroup;
import jears.cslt.util.fileOperation.CreateDir;
import jears.cslt.util.fileOperation.ReadFile;
import jears.cslt.util.fileOperation.WriteFile;
import jears.cslt.xmlparser.TopicParser;

public class IntegrateQueryInfo {
	private String dir = "./data/annotation/";
	private List<GoldTopic> topics;

	public IntegrateQueryInfo(String xml) {
		getTopics(xml);
	}

	private void getTopics(String xml) {
		topics = TopicParser.getTopicFromXml(xml);
	}

	public void writeTopics(String query) throws IOException {
		CreateDir.createDir(dir + query);

		GoldTopic t = null;
		for (int i = 0; i < topics.size(); i++) {
			try {
				if (topics.get(i).getQuery().equals(query)) {
					t = topics.get(i);
					break;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		WriteFile wf = new WriteFile(dir + query + "/subTopics.txt", false,
				"gbk");

		List<Intent> intents = t.getIntents();
		for (int i = 0; i < intents.size(); i++) {
			Intent in = intents.get(i);
			wf.writeLine(in.getNo() + "\t" + in.getDescription() + "\t"
					+ in.getExample());
		}

		wf.close();
	}

	public void writeSearchResult(String query, String srDir)
			throws IOException {
		SearchResultGroup srg = new SearchResultGroup(query, srDir);
		CreateDir.createDir(dir + query);

		WriteFile wf = new WriteFile(dir + query + "/results.txt", false, "gbk");

		wf.writeLine(srg.getGroup());

		wf.close();
	}

	public void writeAnnotation(String query, String srDir) throws IOException {
		CreateDir.createDir(dir + query);
		WriteFile wf = new WriteFile(dir + query + "/annotation.txt", false,
				"gbk");

		GoldTopic t = null;
		for (int i = 0; i < topics.size(); i++) {
			try {
				if (topics.get(i).getQuery().equals(query)) {
					t = topics.get(i);
					break;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		SearchResultGroup srg = new SearchResultGroup(query, srDir);
		List<SearchResult> sr = srg.getResultGroup();

		List<Intent> intents = t.getIntents();
		for (int i = 0; i < intents.size(); i++) {
			Intent in = intents.get(i);
			wf.write(in.getNo() + "\t" + in.getDescription() + "\t"
					+ in.getExample() + "\t");
			wf.write(sr.get(0).getSearchResultAnnotation());
			for (int j = 1; j < sr.size(); j++) {
				wf.write(in.getNo() + "\t" + " " + "\t"
						+ " " + "\t");
				wf.write(sr.get(j).getSearchResultAnnotation());
			}
		}

		wf.close();
		
		wf = new WriteFile(dir + query + "/annotation2.txt", false,
				"gbk");
		
		for (int j = 0; j < sr.size(); j++) {
			wf.write(sr.get(j).getSearchResultAnnotation() + "\t");
			Intent in = intents.get(0);
			wf.write(in.getNo() + "\t" + in.getDescription() + "\t"
					+ in.getExample() + "\n");
			for (int i = 1; i < intents.size(); i++) {
				in = intents.get(i);
				wf.write(sr.get(j).getNo() + "\t" + "\t" + "\t");
				wf.write(in.getNo() + "\t" + in.getDescription() + "\t"
						+ in.getExample() + "\n");
			}
		}
		
		wf.close();
	}

	public static void main(String[] args) throws IOException {
		IntegrateQueryInfo test = new IntegrateQueryInfo(
				"./data/ntcir10intent.0201-0299.full.xml");
		ReadFile rf = new ReadFile("./data/testTopic.txt", "GBK");
		String line = "";
		while((line = rf.readLine()) != null) {
			test.writeTopics(line);
			test.writeSearchResult(line, "./data/Snippet/" + line);
			test.writeAnnotation(line, "./data/Snippet/" + line);
		}
		rf.close();
	}
}
