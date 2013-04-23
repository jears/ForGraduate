package jears.cslt.subtopic.eval;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import jears.cslt.util.fileOperation.ReadFile;
import jears.cslt.util.fileOperation.WriteFile;

public class ScoreExtractor {
	ArrayList<Score> scores;
	ArrayList<String> runs;
	final int numOfMetric;
	final int numOfBaseline;
	
	public static void main(String[] args) throws IOException {
		String path = "data/evalNTCIR9CH/";
		
		ArrayList<String> runs = new ArrayList<String>();
		ReadFile rf = new ReadFile(path + "runlist");
		String line = "";
		while((line = rf.readLine()) != null) {
			runs.add(line);
		}
		rf.close();
		
		ScoreExtractor test = new ScoreExtractor(runs.size());
		test.setRuns(runs);
		
		for (int i = 0; i < runs.size(); i++)
			test.extractorScore(path + "/" + runs.get(i), i+1);

		test.print(path + "runlist.score");
		
		test.extractMeanScore(path, "NTCIR10ChGOLD", path + "runlist.mean");
	}

	public ScoreExtractor(int numOfBaseline) throws IOException {
		this.numOfBaseline = numOfBaseline;
		numOfMetric = 3*numOfBaseline;
		scores = new ArrayList<ScoreExtractor.Score>();
	}
	
	public void setRuns(ArrayList<String> runs) {
		this.runs = runs;
	}
	
	public void extractorScore(String run, int runno) throws IOException {
		String[] filename = { run + ".l10.Dnev", run + ".l20.Dnev",
				run + ".l30.Dnev" };
		for (int i = 0; i < 3; i++) {
			extractorScoreOfCutOff(filename[i], runno, (i + 1));
		}
	}

	public void extractorScoreOfCutOff(String filename, int runno, int cutoff)
			throws IOException {
		ReadFile rf = new ReadFile(filename);
		int k = (runno - 1) * 3 + cutoff - 1;

		String line = "";
		int topicNoTemp = -1;
		while ((line = rf.readLine()) != null) {
			if (line.indexOf("MSnDCG@0") != -1 && line.indexOf("Dsharp") == -1) {
				topicNoTemp++;
				if (scores.size() <= topicNoTemp) {
					Score s = new Score();
					scores.add(s);
				}
				Scanner s = new Scanner(line);
				String topic = s.next();
				int topicno = Integer.valueOf(topic);
				scores.get(topicNoTemp).topicno = topic;

				while (s.hasNext()) {
					topic = s.next();
				}

				double score = Double.valueOf(topic);

				scores.get(topicNoTemp).scores[1][k] = score;
			} else if (line.indexOf("I-rec@0") != -1) {
				Scanner s = new Scanner(line);
				String topic = s.next();
				int topicno = Integer.valueOf(topic);
				scores.get(topicNoTemp).topicno = topic;

				while (s.hasNext()) {
					topic = s.next();
				}

				double score = Double.valueOf(topic);
				scores.get(topicNoTemp).scores[0][k] = score;
			} else if (line.indexOf("Dsharp-MSnDCG") != -1) {
				Scanner s = new Scanner(line);
				String topic = s.next();
				int topicno = Integer.valueOf(topic);
				scores.get(topicNoTemp).topicno = topic;

				while (s.hasNext()) {
					topic = s.next();
				}

				double score = Double.valueOf(topic);

				scores.get(topicNoTemp).scores[2][k] = score;

			}
		}

		rf.close();
	}

	public void print() {
		for (int i = 0; i < scores.size(); i++) {
			System.out.println(scores.get(i));
		}
	}
	
	public void print(String filename) throws IOException {
		WriteFile wf = new WriteFile(filename, false);
		String ret = "";
		for (int i = 0; i < scores.size(); i++) {
			wf.writeLine(scores.get(i).toString());
			ret += scores.get(i) + "\n";
		}
		
		wf.close();
	}


	public void extractMeanScore(String path, String name, String file) throws IOException {
		Score s = new Score();
		if (path.endsWith("/") || path.endsWith("\\"))
			;
		else
			path += "/";
		String prefix = path +  "runlist." + name + ".Iprob.l";
		String[] metrics = { ".Dnev.I-rec@00", ".Dnev.MSnDCG@00",
				".Dnev.Dsharp-MSnDCG@00" };
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				String filename = prefix + (j + 1) * 10 + metrics[i] + (j + 1)
						* 10;
				ReadFile rf = new ReadFile(filename);

				String line = "";
				for (int k = 0; k < numOfBaseline; k++) {
					line = rf.readLine();
//					System.out.println(line);
					Scanner c = new Scanner(line);
					c.next();
					double score = c.nextDouble();
					s.scores[i][k * 3 + j] = score;
				}

				rf.close();
			}
		}
		WriteFile wf = new WriteFile(file, false);
		wf.writeLine(s.toString());
		wf.close();
	}
	
	class Score {
		String topicno;
		double[][] scores = new double[3][numOfMetric];
		
		public void add(Score s0) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < numOfMetric; j++) {
					this.scores[i][j] += s0.scores[i][j];
				}
			}
		}
		
		public String toString() {
			String ret = "";
			ret += "\trun";
			for (int i = 0; i < numOfBaseline; i++) {
				ret += "\t\t" + runs.get(i) + "\t\t";
			}
			ret += "\n";
			ret += "\t@top n";
			for (int i = 0; i < numOfBaseline; i++) {
				ret += "\t10\t20\t30\t";
			}
			ret += "\n";
			
			for (int i = 0; i < 3; i++) {
				ret += topicno + "\t";
				switch (i) {
				case 0:
					ret += "I-rec\t";
					break;
				case 1:
					ret += "nDCG\t";
					break;
				case 2:
					ret += "D-nDCG\t";
					break;
				}
				for (int j = 0; j < numOfMetric; j++) {
					ret += scores[i][j] + "\t";
					if (j % 3 == 2)
						ret += "\t";
				}
				ret += "\n";
			}

			// ret+="\n";
			return ret;
		}		
	}
}

