package jears.cslt.subtopic.model;

import java.io.File;
import java.io.IOException;

import jears.cslt.util.fileOperation.ReadFile;

public class SearchResult {
	private String no;
	private String title;
	private String snippet;
	private String url;

	public SearchResult(String title, String snippet, String url) {
		this.title = title;
		this.snippet = snippet;
		this.url = url;
	}
	
	public SearchResult(String file) throws IOException {
		ReadFile rf = new ReadFile(file, "gbk");
		String name = new File(file).getName();
		no = name.substring(0, name.indexOf('.'));
		this.title = rf.readLine();
		this.url = rf.readLine();
		this.snippet = rf.readLine();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSnippet() {
		return snippet;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getNo() {
		return no;
	}
	
	public String getSearchResult() {
		return no + "\t" +  url + "\t" + title + "\t" + snippet + "\n";
	}
	
	public String getSearchResultAnnotation() {
		return no + "\t" + title + "\t" + snippet;
	}

	@Override
	public String toString() {
		return title + "\n" + snippet + "\n" + url + "\n";
	}
}
