package core.entities.detector;

import core.entities.Commit;
import core.entities.Document;
import core.process.DataReader;
import core.util.FileUtil;

import org.apache.commons.io.FileUtils;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayesMultinomial;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.stemmers.SnowballStemmer;
import weka.core.stopwords.WordsFromFile;

import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.*;

import java.util.ArrayList;
import java.util.List;

public class RealSATDDetector implements SATDDetector
{

    public RealSATDDetector()
    {
        super();

        index_identified = new ArrayList<>();
        commits_identified = new ArrayList<>();
    }


    @Override
    public List<Commit> detectSATD(List<Commit> commits) throws Exception
    {
        String dataDirectoryPath = System.getProperty("user.home") + File.separator + ".identifySATD" + File.separator + "data";
        File dataFileDirectory = new File(dataDirectoryPath);

        if(!dataFileDirectory.isDirectory())
            dataFileDirectory.mkdirs();
        else {
            if(dataFileDirectory.listFiles().length != 0)
            {
                for(File file : dataFileDirectory.listFiles())
                    file.delete();
            }
        }

        if(dataFileDirectory.isDirectory() && dataFileDirectory.listFiles().length == 0)
        {
            List<String> commit_messages = new ArrayList<>();

            for(int I=0; I<commits.size(); I++)
            {
                String commitMessage = commits.get(I).getCommitMessage();
                commitMessage = commitMessage.replace("\n", " ").replace("\r", "");

                commit_messages.add("// " + commitMessage);
            }

            FileUtil.writeLinesToFile(commit_messages, dataFileDirectory + File.separator + "comments");
            FileUtil.removeBlankLines(dataDirectoryPath + File.separator + "comments");
        }

        List<Document> comments = DataReader.readComments(dataFileDirectory + File.separator);

        String testDataPath = dataFileDirectory + File.separator + "testData.arff";
        DataReader.outputArffData(comments, testDataPath);

        StringToWordVector stw = new StringToWordVector(100000);
        stw.setOutputWordCounts(true);
        stw.setIDFTransform(true);
        stw.setTFTransform(true);

        SnowballStemmer stemmer = new SnowballStemmer();
        stw.setStemmer(stemmer);

        WordsFromFile stopwords = new WordsFromFile();
        stopwords.setStopwords(new File("dic/stopwords.txt"));
        stw.setStopwordsHandler(stopwords);

        Instances commitData = DataSource.read(testDataPath);
        stw.setInputFormat(commitData);
        commitData = Filter.useFilter(commitData, stw);
        commitData.setClassIndex(0);

        AttributeSelection attSelection = new AttributeSelection();
        Ranker ranker = new Ranker();
        ranker.setNumToSelect((int) (commitData.numAttributes() * ratio));
        InfoGainAttributeEval ifg = new InfoGainAttributeEval();
        attSelection.setEvaluator(ifg);
        attSelection.setSearch(ranker);
        attSelection.setInputFormat(commitData);

        commitData = Filter.useFilter(commitData, attSelection);

        try {
            index_identified = classifyCommit(commitData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(index_identified.isEmpty())
            return null;
        else {
            for(Integer index : index_identified)
                commits_identified.add(commits.get(index));

            return commits_identified;
        }
    }


    public Classifier loadOrTrainClassifier() throws Exception
    {
        Classifier classifier;

        String dataDirectoryPath = System.getProperty("user.home") + File.separator + ".identifySATD" + File.separator + "data";
        String path = System.getProperty("user.home") + File.separator + ".identifySATD" + File.separator + "models";
        directory_classifiers = new File(path);

        String classifierFileName = directory_classifiers + File.separator + "SATDClassifier.model";
        //String trainingPath = "data" + File.separator + "trainingData.arff";
        //String stopwordsPath = "dic" + File.separator + "stopwords.txt";

        InputStream trainingFile = this.getClass().getResourceAsStream("/trainingData.arff");
        InputStream stopWordsIS = this.getClass().getResourceAsStream("/stopwords.txt");

        if(directory_classifiers.isDirectory() && directory_classifiers.listFiles().length == 1)
        {
            classifier = (Classifier) SerializationHelper.read(classifierFileName);
        } else {
            StringToWordVector stw = new StringToWordVector(100000);
            stw.setOutputWordCounts(true);
            stw.setIDFTransform(true);
            stw.setTFTransform(true);

            SnowballStemmer stemmer = new SnowballStemmer();
            stw.setStemmer(stemmer);

            FileUtils.copyInputStreamToFile(stopWordsIS, new File(dataDirectoryPath + File.separator + "stopwords"));

            WordsFromFile stopwords = new WordsFromFile();
            stopwords.setStopwords(new File(dataDirectoryPath + File.separator + "stopwords.txt"));
            stw.setStopwordsHandler(stopwords);

            Instances trainSet = ConverterUtils.DataSource.read(trainingFile);
            stw.setInputFormat(trainSet);
            trainSet = Filter.useFilter(trainSet, stw);
            trainSet.setClassIndex(0);

            AttributeSelection attSelection = new AttributeSelection();

            Ranker ranker = new Ranker();
            ranker.setNumToSelect((int) (trainSet.numAttributes() * ratio));

            InfoGainAttributeEval ifg = new InfoGainAttributeEval();
            attSelection.setEvaluator(ifg);
            attSelection.setSearch(ranker);
            attSelection.setInputFormat(trainSet);

            trainSet = Filter.useFilter(trainSet, attSelection);

            classifier = new NaiveBayesMultinomial();
            classifier.buildClassifier(trainSet);

            directory_classifiers = new File(path);

            if(!directory_classifiers.isDirectory())
                directory_classifiers.mkdirs();

            SerializationHelper.write(classifierFileName, classifier);
        }

        return classifier;
    }


    private List<Integer> classifyCommit(Instances instances) throws Exception
    {
        Classifier classifier = loadOrTrainClassifier();

        for(int I=0; I<instances.numInstances(); I++)
        {
            Instance instance = instances.get(I);

            double result = classifier.classifyInstance(instance);

            if(result == 1.0)
                index_identified.add(I);

        }

        return index_identified;
    }



    private List<Integer> index_identified;
    private List<Commit> commits_identified;
    private double ratio = 0.1;
    private File directory_classifiers;

}

