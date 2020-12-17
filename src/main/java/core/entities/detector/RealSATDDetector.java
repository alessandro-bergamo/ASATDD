package core.entities.detector;

import application.datareader.EnsembleLearner;
import core.entities.Commit;
import weka.core.*;

import java.util.ArrayList;
import java.util.List;

public class RealSATDDetector implements SATDDetector
{

    public RealSATDDetector()
    {
        super();

        commits_identified = new ArrayList<>();
        ensembleLearner = new EnsembleLearner();
    }

    @Override
    public List<Commit> detectSATD(List<Commit> commits) throws ImpossibleIdentification
    {
        for(int I=0; I<commits.size(); I++)
        {
            commit_message = commits.get(I).getCommitMessage();

            /*ArrayList<Attribute> attribute_list = new ArrayList<>();
            Attribute attribute = new Attribute("commit_message", true);
            attribute_list.add(attribute);

            Instances data = new Instances("Instances", attribute_list, 0);
            Instance instance = new DenseInstance(data.numAttributes());

            data.add(instance);

            instance.setDataset(data);
            instance.setValue(0, commit_message);

            data.setClassIndex(data.numAttributes() - 1);*/

            Instance instance = new SparseInstance(null);

            try {
                vote = ensembleLearner.classifyCommit(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(vote > 0)
            {
                commits_identified.add(commits.get(I));
                System.out.println("Commits identificati: "+commits_identified.size()+" VOTE: "+ vote);
            } else {
                System.out.println("NON SONO UN SATD");
            }
        }

        return commits_identified;
    }


    private EnsembleLearner ensembleLearner;
    private final List<Commit> commits_identified;
    private String commit_message;
    private double vote;

}

