package core.entities.detector;

import core.entities.Commit;
import core.entities.Document;
import core.process.DataReader;
import core.util.FileUtil;

import core.util.PorterStemmer;
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
import weka.core.stemmers.Stemmer;
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
        String commentsFilePath = dataDirectoryPath + File.separator + "comments";
        String testDataFilePath = dataDirectoryPath + File.separator + "testData.arff";

        String modelsDirectoryPath = System.getProperty("user.home") + File.separator + ".identifySATD" + File.separator + "models";
        String stopwordsFilePath = modelsDirectoryPath + File.separator + "stopwords.data";

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

            for(Commit commit : commits)
            {
                String commitMessage = commit.getCommitMessage();
                commitMessage = commitMessage.replace("\n", " ").replace("\r", "");

                commit_messages.add("// " + commitMessage);
            }

            FileUtil.writeLinesToFile(commit_messages, commentsFilePath);
            FileUtil.removeBlankLines(commentsFilePath);
        }

        List<Document> comments = DataReader.readComments(commentsFilePath);

        DataReader.outputArffData(comments, testDataFilePath);

        StringToWordVector stw = new StringToWordVector(100000);
        stw.setOutputWordCounts(true);
        stw.setIDFTransform(true);
        stw.setTFTransform(true);

        Stemmer stemmer = new PorterStemmer();
        stw.setStemmer(stemmer);

        InputStream stopWordsIS = this.getClass().getResourceAsStream("/stopwords.txt");
        FileUtils.copyInputStreamToFile(stopWordsIS, new File(stopwordsFilePath));

        WordsFromFile stopwords = new WordsFromFile();
        stopwords.setStopwords(new File(stopwordsFilePath));
        stw.setStopwordsHandler(stopwords);

        Instances commitData = DataSource.read(testDataFilePath);
        stw.setInputFormat(commitData);
        commitData = Filter.useFilter(commitData, stw);
        commitData.setClassIndex(0);

        Ranker ranker = new Ranker();
        ranker.setNumToSelect((int) (commitData.numAttributes() * ratio));

        AttributeSelection attSelection = new AttributeSelection();
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

        String modelsDirectoryPath = System.getProperty("user.home") + File.separator + ".identifySATD" + File.separator + "models";
        String classifierFilePath = modelsDirectoryPath + File.separator + "SATDClassifier.model";

        File directory_models = new File(modelsDirectoryPath);

        if(directory_models.isDirectory() && directory_models.listFiles().length == 2)
        {
            classifier = (Classifier) SerializationHelper.read(classifierFilePath);
        } else {
            String stopwordsFilePath = modelsDirectoryPath + File.separator + "stopwords.data";

            StringToWordVector stw = new StringToWordVector(100000);
            stw.setOutputWordCounts(true);
            stw.setIDFTransform(true);
            stw.setTFTransform(true);

            Stemmer stemmer = new PorterStemmer();
            stw.setStemmer(stemmer);

            InputStream stopWordsIS = this.getClass().getResourceAsStream("/stopwords.txt");
            FileUtils.copyInputStreamToFile(stopWordsIS, new File(stopwordsFilePath));

            WordsFromFile stopwords = new WordsFromFile();
            stopwords.setStopwords(new File(stopwordsFilePath));
            stw.setStopwordsHandler(stopwords);

            InputStream trainingFile = this.getClass().getResourceAsStream("/trainingData.arff");
            Instances trainSet = ConverterUtils.DataSource.read(trainingFile);
            stw.setInputFormat(trainSet);
            trainSet = Filter.useFilter(trainSet, stw);
            trainSet.setClassIndex(0);

            Ranker ranker = new Ranker();
            ranker.setNumToSelect((int) (trainSet.numAttributes() * ratio));

            AttributeSelection attSelection = new AttributeSelection();
            InfoGainAttributeEval ifg = new InfoGainAttributeEval();
            attSelection.setEvaluator(ifg);
            attSelection.setSearch(ranker);
            attSelection.setInputFormat(trainSet);

            trainSet = Filter.useFilter(trainSet, attSelection);

            classifier = new NaiveBayesMultinomial();
            classifier.buildClassifier(trainSet);

            if(!directory_models.isDirectory())
                directory_models.mkdirs();

            SerializationHelper.write(classifierFilePath, classifier);
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

}

