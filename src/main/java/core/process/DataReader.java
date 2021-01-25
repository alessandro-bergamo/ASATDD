package core.process;

import core.entities.Document;
import core.util.FileUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataReader
{

	public static void outputArffData(List<Document> comments, String outputFilePath)
	{
		// arff declare info
		List<String> lines = new ArrayList<String>();
		lines.add("@relation 'CommitMessages'");
		lines.add("");
		lines.add("@attribute Text string");
		lines.add("@attribute class-att {negative,positive}");
		lines.add("");
		lines.add("@data");
		lines.add("");

		for (Document document : comments)
		{
			String temp = "'";
			for (String word : document.getWords())
				temp = temp + word + " ";
			if (document.getLabel().equals("WITHOUT_CLASSIFICATION"))
				temp = temp + "',negative";// negative comments
			else
				temp = temp + "',positive";

			lines.add(temp);
		}

		FileUtil.writeLinesToFile(lines, outputFilePath);
	}


	public static List<Document> selectProject(List<Document> comments, Set<String> projectName)
	{
		List<Document> res = new ArrayList<>();

		for (Document doc : comments) {
			if (projectName.contains(doc.getProject()))
				res.add(doc);
		}
		return res;
	}


	public static List<Document> readComments(String path)
	{
		List<Document> comments = new ArrayList<>();

		// read comments' content first
		List<String> lines = FileUtil.readLinesFromFile(path + "comments");

		for (int i = 0; i < lines.size(); i++)
		{
			String line = lines.get(i);
			if (!line.contains("\"/*"))
			{
				comments.add(new Document(line));
			} else {
				String temp = "";
				for (int j = i; j < lines.size(); j++)
				{
					temp = temp + lines.get(j);
					if (lines.get(j).contains("*/\""))
					{
						i = j;
						break;
					}
				}
				comments.add(new Document(temp));
			}
		}

		for (int i = 0; i < lines.size(); i++)
			comments.get(i).setLabel("WITHOUT_CLASSIFICATION");

		// remove duplicate and empty comments
		List<Document> res = new ArrayList<>();
		Set<String> content = new HashSet<>();
		for (Document doc : comments)
		{
			if (doc.getWords().isEmpty() || content.contains(doc.getContent()))
				continue;
			content.add(doc.getContent());
			res.add(doc);
		}

		return res;
	}


	private static int j = 0;

}
