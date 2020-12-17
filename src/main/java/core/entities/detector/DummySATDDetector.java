package core.entities.detector;

import core.entities.Commit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DummySATDDetector implements SATDDetector
{

    public DummySATDDetector()
    {
        commits_identified = new ArrayList<>();
    }


    @Override
    public List<Commit> detectSATD(List<Commit> commits) throws ImpossibleIdentification
    {
        Random random = new Random();

        if(commits.size()>15)
        {
            for(int I=0; I<random.nextInt(commits.size()); I++)
                commits_identified.add(commits.get(I));
        }

        return commits_identified;
    }



    private final List<Commit> commits_identified;

}
