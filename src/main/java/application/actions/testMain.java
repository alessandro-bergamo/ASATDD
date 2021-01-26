package application.actions;

import core.entities.Commit;
import core.entities.detector.RealSATDDetector;
import core.usecases.identifySATD.IdentifySATDInteractor;
import core.util.RetrieveCommitsLog;

import java.util.List;
import java.util.Objects;

public class testMain
{

    private static List<Commit> SATD_found;

    public static void main(String[] args)
    {
        RetrieveCommitsLog retrieveCommitsLog = new RetrieveCommitsLog();
        RealSATDDetector realSATDDetector = new RealSATDDetector();

        IdentifySATDInteractor identifySATDInteractor = new IdentifySATDInteractor(retrieveCommitsLog, realSATDDetector, "C:\\Users\\Alessandro\\IdeaProjects\\testrepositoryforsatd");
        SATD_found = identifySATDInteractor.execute();

        /*List<Commit> commits = retrieveCommitsLog.retrieveCommitsLogs("C:\\Users\\Alessandro\\IdeaProjects\\socket.io");

        try {
            SATD_found = realSATDDetector.detectSATD(commits);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        for(Commit satd_commit : SATD_found)
        {
            System.out.println("COMMIT ID: "+ satd_commit.getCommitID() + "\nCOMMIT USER: " + satd_commit.getCommitUser() + "\nCOMMIT MESSAGE: " + satd_commit.getCommitMessage() + "\nCOMMIT DATE: " + satd_commit.getCommitDate() + "\n");
        }

        System.out.println("COMMIT IDENTIFICATI: "+SATD_found.size());
    }

}
