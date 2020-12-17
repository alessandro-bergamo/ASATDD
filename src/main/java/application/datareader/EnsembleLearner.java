package application.datareader;

import core.entities.Document;
import core.process.DataReader;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils;
import weka.core.stemmers.SnowballStemmer;
import weka.core.stopwords.WordsFromFile;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EnsembleLearner
{

    public EnsembleLearner()
    {
        classifiers = new ArrayList<>();

        try {
            classifiers = getClassifiers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public double classifyCommit(Instance instance) throws Exception
    {
        vote = new double[] {0, 0, 0, 0, 0, 0, 0, 0};

        for(Classifier classifier : classifiers)
        {
            vote = classifier.distributionForInstance(instance);
        }

        for(int I=0; I<vote.length; I++)
        {
            if(I==0)
            {
                result = vote[I];
                continue;
            }

            if(vote[I] > vote[I-1])
                result = vote[I];
        }

        return result;
    }


    public List<Classifier> getClassifiers() throws Exception
    {
        String path = System.getProperty("user.home") + File.separator + ".identifySATD" + File.separator + "models";
        directory_classifiers = new File(path);

        if(directory_classifiers.isDirectory())
        {
            for(final File classifier_file : directory_classifiers.listFiles())
            {
                classifier = (Classifier) SerializationHelper.read(directory_classifiers + File.separator + classifier_file.getName());
                classifiers.add(classifier);
            }
        } else {
            List<String> projects = new ArrayList<>();
            double ratio = 0.1;

            projects.add("argouml");
            projects.add("columba-1.4-src");
            projects.add("hibernate-distribution-3.3.2.GA");
            projects.add("jEdit-4.2");
            projects.add("jfreechart-1.0.19");
            projects.add("apache-jmeter-2.10");
            projects.add("jruby-1.4.0");
            projects.add("sql12");

            List<Document> comments = DataReader.readComments("C:\\Users\\Alessandro\\IdeaProjects\\Identify-SATD\\Identify-SATD\\data\\");

            for (int source = 0; source < projects.size(); source++)
            {
                Set<String> projectForTraining = new HashSet<>();
                projectForTraining.add(projects.get(source));

                List<Document> trainDoc = DataReader.selectProject(comments, projectForTraining);

                // System.out.println("building dataset for training");
                String trainingDataPath = "C:\\Users\\Alessandro\\IdeaProjects\\Identify-SATD\\Identify-SATD\\tmp\\trainingData.arff";
                DataReader.outputArffData(trainDoc, trainingDataPath);

                // string to word vector (both for training and testing data)
                StringToWordVector stw = new StringToWordVector(100000);
                stw.setOutputWordCounts(true);
                stw.setIDFTransform(true);
                stw.setTFTransform(true);

                SnowballStemmer stemmer = new SnowballStemmer();
                stw.setStemmer(stemmer);

                WordsFromFile stopwords = new WordsFromFile();
                stopwords.setStopwords(new File("dic/stopwords.txt"));
                stw.setStopwordsHandler(stopwords);

                Instances trainSet = ConverterUtils.DataSource.read(trainingDataPath);
                stw.setInputFormat(trainSet);
                trainSet = Filter.useFilter(trainSet, stw);
                trainSet.setClassIndex(0);

                ArffSaver saver = new ArffSaver();
                saver.setInstances(trainSet);
                saver.setFile(new File("./data/" + projects.get(source) + ".arff"));
                saver.writeBatch();

                // attribute selection for training data
                AttributeSelection attSelection = new AttributeSelection();

                Ranker ranker = new Ranker();
                ranker.setNumToSelect((int) (trainSet.numAttributes() * ratio));

                InfoGainAttributeEval ifg = new InfoGainAttributeEval();
                attSelection.setEvaluator(ifg);

                attSelection.setSearch(ranker);
                attSelection.setInputFormat(trainSet);

                trainSet = Filter.useFilter(trainSet, attSelection);

                Classifier classifier = new NaiveBayesMultinomial();
                classifier.buildClassifier(trainSet);

                directory_classifiers = new File(System.getProperty("user.home") + File.separator + ".identifySATD" + File.separator + "models");

                if(!directory_classifiers.isDirectory())
                    directory_classifiers.mkdirs();

                SerializationHelper.write(System.getProperty("user.home") + File.separator + ".identifySATD" + File.separator + "models" + File.separator + projects.get(source) + ".model", classifier);            }
        }

        return classifiers;
    }


    private double[] vote;
    private double result;
    private List<Classifier> classifiers;
    private Classifier classifier;
    private File directory_classifiers;

}
