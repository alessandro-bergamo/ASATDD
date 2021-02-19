package application.actions;

import core.entities.Commit;
import core.entities.detector.RealSATDDetector;
import core.util.RetrieveCommitsLog;
import javafx.util.Pair;

import java.util.List;

public class testMain
{

    private static List<Commit> SATD_found;

    public static void main(String[] args)
    {
        RetrieveCommitsLog retrieveCommitsLog = new RetrieveCommitsLog();
        RealSATDDetector realSATDDetector = new RealSATDDetector();

        Pair<String, List<Commit>> owner_commits = retrieveCommitsLog.retrieveCommitsLogs("C://Users//Alessandro//IdeaProjects//testrepositoryforsatd");

        List<Commit> commits = owner_commits.getValue();

        try {
            SATD_found = realSATDDetector.detectSATD(commits);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(Commit satd_commit : SATD_found)
        {
            System.out.println("COMMIT ID: "+ satd_commit.getCommitID() + "\nCOMMIT USER: " + satd_commit.getCommitUser() + "\nCOMMIT MESSAGE: " + satd_commit.getCommitMessage() + "\nCOMMIT DATE: " + satd_commit.getCommitDate() + "\n");
        }
    }

}
