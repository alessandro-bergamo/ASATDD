package core.entities.detector;

import core.entities.Commit;

import java.util.List;

public class DummySATDDetector implements SATDDetector
{

    public DummySATDDetector() { }

    @Override
    public boolean detectSATD(List<Commit> commits) throws ImpossibleIdentification
    {
        if(commits.size()<15)
            return false;

        return true;
    }


}
