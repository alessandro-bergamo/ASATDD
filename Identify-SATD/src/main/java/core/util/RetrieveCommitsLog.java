package core.util;

import core.entities.Commit;
import core.entities.detector.DummySATDDetector;
import org.eclipse.jgit.api.Git; //inserire nel readme l'utlizzo di questa libreria
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class RetrieveCommitsLog
{

    public RetrieveCommitsLog()
    {
        repository = null;
        logCommits = new ArrayList<>();
    }


    public ArrayList<Commit> retrieveCommitsLogs() throws IOException, GitAPIException
    {
        repository = Git.open(new File(System.getProperty("user.dir"))).getRepository();

        Git git = new Git(repository);

        Iterable<RevCommit> log = git.log().call();

        for(Iterator<RevCommit> iterator = log.iterator(); iterator.hasNext();)
        {
            RevCommit rev = iterator.next();

            Commit commit = new Commit();

            commit.setCommitID(rev.getId().toString());
            commit.setCommitDate(rev.getCommitTime());   //in seconds
            commit.setCommitMessage(rev.getFullMessage());
            commit.setCommitUser(rev.getAuthorIdent().toString());

            logCommits.add(commit);
        }

        return logCommits;
    }


    public static void main(String[] args) throws IOException, GitAPIException
    {
        RetrieveCommitsLog retrieveCommitsLog = new RetrieveCommitsLog();

        ArrayList<Commit> list;

        list = retrieveCommitsLog.retrieveCommitsLogs();

        DummySATDDetector dummySATDDetector = new DummySATDDetector();

        if(!dummySATDDetector.detectSATD(list))
            System.out.println("\nNo Self-Admitted Technical Debt found");
        else
            System.out.println("\nSelf-Admitted Technical Debt found.");

        for(int I=0; I<list.size(); I++)
            System.out.println("USER: "+list.get(I).getCommitUser()+" MESSAGE: "+list.get(I).getCommitMessage()+" TIME: "+list.get(I).getCommitDate());
    }



    private Repository repository;
    private ArrayList<Commit> logCommits;

}
