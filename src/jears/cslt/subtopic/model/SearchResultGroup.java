package jears.cslt.subtopic.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SearchResultGroup {
	private String query;
	private List<SearchResult> resultGroup;

	public SearchResultGroup(String query) {
		this.query = query;
		resultGroup = new ArrayList<SearchResult>();
	}
	
	public SearchResultGroup(String query, String dir) throws IOException {
		this.query = query;
		resultGroup = new ArrayList<SearchResult>();
		File dirFile = new File(dir);
		String[] files = dirFile.list();
		for (String f : files) {
			SearchResult s = new SearchResult(dir + "/" + f);
			resultGroup.add(s);
		}
	}

	public void addResult(SearchResult r) {
		resultGroup.add(r);
	}

	public void addAllResult(List<SearchResult> rs) {
		resultGroup.addAll(rs);
	}

	public String getQuery() {
		return query;
	}
	
	public List<SearchResult> getResultGroup() {
		return Collections.unmodifiableList(resultGroup);
	}
	
	public SearchResult get(int i) {
		return resultGroup.get(i);
	}
	
	public int size() {
		return resultGroup.size();
	}
	
	public List<String> getUrls() {
		ArrayList<String> urls = new ArrayList<String>();
		for (int i = 0; i < resultGroup.size(); ++i) {
			urls.add(resultGroup.get(i).getUrl());
		}
		return urls;
	}
	
	public String getGroup() {
		String ret = "";
		for (int i = 0; i < resultGroup.size(); ++i) {
			ret += resultGroup.get(i).getSearchResult();
		}
		return ret;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
//		builder.append(query);
//		builder.append("\n");
//		builder.append(resultGroup.size());
//		builder.append(Constants.NEW_LINE);
//		builder.append(Constants.NEW_LINE);
		for (SearchResult result : resultGroup) {
			builder.append(result);
			builder.append("\n");
		}
		return builder.toString();
	}
}
