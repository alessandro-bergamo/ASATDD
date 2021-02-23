package core.entities;

import core.util.WordSplit;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Document implements Comparable<Document>
{

	public int compareTo(@NotNull Document doc)
	{
		return 0;	//empty method
	}


	public Document(String content)
	{
		this.content = content;		
		this.words=WordSplit.split(content);
		
		//build term frequency
		this.tf = new HashMap<>();

		for(String word: this.words)
		{
			if(tf.containsKey(word))
				tf.put(word, tf.get(word)+1);
			else
				tf.put(word, 1.0);
		}

		//normalize tf
		for(Map.Entry<String, Double> entry:this.tf.entrySet())
		{
			entry.setValue(entry.getValue()*1.0/this.words.size());			
		}
	}



	public List<String> getWords() {
		return words;
	}

	public String getContent() {
		return content;
	}

	public String getProject() {
		return project;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}



	private String content, project, label;
	private List<String> words;

	private Map<String,Double> tf;

}
