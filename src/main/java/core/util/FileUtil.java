package core.util;

import java.io.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileUtil
{
	
	public static List<String> readLinesFromFile(String filePath)
	{
		BufferedReader reader = null;
		List<String> lines = new ArrayList<>();

		try {
			reader = new BufferedReader(new FileReader(new File(filePath)));
			
			String line = null;

			while ((line = reader.readLine()) != null)
				lines.add(line);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return lines;
	}


	public static void writeLinesToFile(List<String> lines, String filePath)
	{
		BufferedWriter writer = null;

		try {
			writer = new BufferedWriter(new FileWriter(new File(filePath)));
			for(String line : lines)
			{
				writer.write(line);
				writer.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	public static void removeBlankLines(String filePath)
	{
		List<String> lines = FileUtil.readLinesFromFile(filePath);

		Iterator<String> iterator = lines.iterator();

		while (iterator.hasNext()) {
			String line = iterator.next();
			if (line.trim().isEmpty())
				iterator.remove();
		}

		FileUtil.writeLinesToFile(lines, filePath);
	}


	public static void compactMessage(String filePath)
	{
		List<String> lines = FileUtil.readLinesFromFile(filePath);


	}

}
