package jears.cslt.xmlparser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jears.cslt.subtopic.model.Intent;
import jears.cslt.subtopic.model.GoldTopic;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import jears.cslt.util.fileOperation.WriteFile;

public class TopicParser {

	public static List<GoldTopic> getTopicFromXml(String filename) {
		List<GoldTopic> ret = new ArrayList<GoldTopic>();

		try {
			SAXBuilder builder = new SAXBuilder();
			Document read_doc = builder.build(filename);
			Element revs = read_doc.getRootElement();
			List list = revs.getChildren();

			for (int i = 0; i < list.size(); i++) {
				Element topic = (Element) list.get(i);
				String num = topic.getAttributeValue("number");
				List queryAndIntentList = topic.getChildren();

				Element query = (Element) queryAndIntentList.get(0);
				String queryText = query.getText();

				GoldTopic thisTopic = new GoldTopic(num, queryText);

				for (int j = 1; j < queryAndIntentList.size(); j++) {

					Element intent = (Element) queryAndIntentList.get(j);
					String intentNo = intent.getAttributeValue("number");
					String probability = intent
							.getAttributeValue("probability");

					List intentList = intent.getChildren();
					Element description = (Element) intentList.get(0);
					String descriptionText = description.getText().trim();

					Intent thisIntent = new Intent(num, intentNo, probability,
							descriptionText);
					
					List examplesList = intent.getChildren("examples");
					if (examplesList.size() == 1) {
						Element example = (Element) examplesList.get(0);
						String[] examples = example.getText().split(";");
						for (int k = 0; k < examples.length; k++) {
							thisIntent.addExample(examples[k]);
						}
					}


					thisTopic.addIntent(thisIntent);
				}

				// System.out.println();
//				System.out.print(thisTopic);
				ret.add(thisTopic);
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (JDOMException ex) {
			ex.printStackTrace();
		}

		return ret;
	}

	public static void main(String[] args) throws IOException {
		List<GoldTopic> topics = getTopicFromXml("./data/ntcir10intent.0201-0299.full.xml");
		WriteFile wf = new WriteFile("./data/NTCIR10ChGold.Iprob", false);
		WriteFile wf1 = new WriteFile("./data/NTCIR10ChGold.Dqrel", false, "GBK");
		for (int i = 0; i < topics.size(); i++) {
			System.out.println(topics.get(i).getQuery());
//			System.out.println(topics.get(i));
//			System.out.print(topics.get(i).getQrels());		
			wf.write(topics.get(i).getIprob());
			wf1.write(topics.get(i).getDqrel());
		}
		wf1.close();
		wf.close();
		
		for (int i = 0; i < topics.size(); i++) {
			GoldTopic temp = topics.get(i);
			System.out.println(temp.getNo() + "\t" + temp.getQuery() + "\t" + temp.getIntentNum());
		}
	}
}
