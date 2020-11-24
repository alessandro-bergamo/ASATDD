package core.entities.detector;

import core.entities.Commit;

import java.util.List;

public interface SATDDetector
{

    boolean detectSATD(List<Commit> commits);

}
