package core.process;

import core.entities.Document;
import core.util.FileUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataReader {

	public static void outputArffData(List<Document> comments, String outputFilePath) {
		// notice: here we assume positive class is SATD

		// arff declare info
		List<String> lines = new ArrayList<String>();
		lines.add("@relation 'technicalDebt'");
		lines.add("");
		lines.add("@attribute Text string");
		lines.add("@attribute class-att {negative,positive}");
		lines.add("");
		lines.add("@data");
		lines.add("");

		for (Document doc : comments) {

			String tmp = "'";
			for (String word : doc.getWords())
				tmp = tmp + word + " ";
			if (doc.getLabel().equals("WITHOUT_CLASSIFICATION")) {
				tmp = tmp + "',negative";// negative comments

			} else {
				tmp = tmp + "',positive";

			}
			lines.add(tmp);
		}

		FileUtil.writeLinesToFile(lines, outputFilePath);

	}

	public static List<Document> selectProject(List<Document> comments, Set<String> projectName) {

		List<Document> res = new ArrayList<Document>();

		for (Document doc : comments) {
			if (projectName.contains(doc.getProject()))
				res.add(doc);
		}
		return res;

	}

	public static List<Document> readComments(String path) {

		List<Document> comments = new ArrayList<Document>();

		// read comments' content first
		List<String> lines = FileUtil.readLinesFromFile(path + "comments");

		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			if (!line.contains("\"/*")) {
				comments.add(new Document(line));
			} else {

				String tmp = "";
				for (int j = i; j < lines.size(); j++) {
					tmp = tmp + lines.get(j);
					if (lines.get(j).contains("*/\"")) {
						i = j;
						break;
					}
				}
				comments.add(new Document(tmp));

			}

		}

		// System.out.println(comments.size());

		// read label
		lines = FileUtil.readLinesFromFile(path + "labels");
		// System.out.println(lines.size());
		for (int i = 0; i < lines.size(); i++)
			comments.get(i).setLabel(lines.get(i));

		// read project name
		lines = FileUtil.readLinesFromFile(path + "projects");
		// System.out.println(lines.size());
		for (int i = 0; i < lines.size(); i++)
			comments.get(i).setProject(lines.get(i));

		// remove duplicate and empty comments
		List<Document> res = new ArrayList<Document>();
		Set<String> content = new HashSet<String>();
		for (Document doc : comments) {
			if (doc.getWords().isEmpty() || content.contains(doc.getContent()))
				continue;
			content.add(doc.getContent());
			res.add(doc);
		}

		return res;

	}

}
