package core.usecases.identifySATD;

import core.entities.Commit;
import core.entities.detector.SATDDetector;
import core.util.RetrieveCommitsLog;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;
import java.util.ArrayList;

public class IdentifySATDInteractor
{

    public IdentifySATDInteractor(RetrieveCommitsLog retrieveCommitsLog, SATDDetector SATDDetector)
    {
        this.retrieveCommitsLog = retrieveCommitsLog;
        this.SATDDetector = SATDDetector;
    }


    //Gestire le eccezioni
    public boolean execute() throws IOException, GitAPIException
    {
        ArrayList<Commit> commits = retrieveCommitsLog.retrieveCommitsLogs();

        return SATDDetector.detectSATD(commits);
    }



    private RetrieveCommitsLog retrieveCommitsLog;
    private SATDDetector SATDDetector;

}
