package core.usecases.identifySATD;

import core.entities.Commit;
import core.entities.detector.SATDDetector;
import core.util.RetrieveCommitsLog;
import javafx.util.Pair;

import java.util.List;

public class IdentifySATDInteractor
{

    public IdentifySATDInteractor(RetrieveCommitsLog retrieveCommitsLog, SATDDetector SATDDetector, String repository_path)
    {
        this.retrieveCommitsLog = retrieveCommitsLog;
        this.SATDDetector = SATDDetector;
        this.repository_path = repository_path;
    }


    public Pair<String, List<Commit>> execute()
    {
        try {
            Pair<String, List<Commit>> repositoryUrl_commits = retrieveCommitsLog.retrieveCommitsLogs(repository_path);

            return new Pair<>(repositoryUrl_commits.getKey(), SATDDetector.detectSATD(repositoryUrl_commits.getValue()));
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }



    private final RetrieveCommitsLog retrieveCommitsLog;
    private final SATDDetector SATDDetector;
    private final String repository_path;

}
