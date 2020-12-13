package core.entities.detector;

import core.entities.Commit;

import java.util.List;

public interface SATDDetector
{

    List<Commit> detectSATD(List<Commit> commits);

}
