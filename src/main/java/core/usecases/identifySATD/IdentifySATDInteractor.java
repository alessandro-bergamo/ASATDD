package core.usecases.identifySATD;

import core.entities.Commit;
import core.entities.detector.SATDDetector;
import core.util.RetrieveCommitsLog;

import java.util.List;

public class IdentifySATDInteractor
{

    public IdentifySATDInteractor(RetrieveCommitsLog retrieveCommitsLog, SATDDetector SATDDetector, String repository_path)
    {
        this.retrieveCommitsLog = retrieveCommitsLog;
        this.SATDDetector = SATDDetector;
        this.repository_path = repository_path;
    }


    //Gestire le eccezioni
    public List<Commit> execute()
    {
        try {
            List<Commit> commits = retrieveCommitsLog.retrieveCommitsLogs(repository_path);
            return SATDDetector.detectSATD(commits);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }



    private final RetrieveCommitsLog retrieveCommitsLog;
    private final SATDDetector SATDDetector;
    private final String repository_path;

}
