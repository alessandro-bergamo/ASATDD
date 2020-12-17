package application.actions;

import com.intellij.openapi.project.Project;
import core.entities.Commit;
import core.entities.detector.RealSATDDetector;
import core.usecases.identifySATD.IdentifySATDInteractor;
import core.util.RetrieveCommitsLog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class testMain
{

    private static List<Commit> SATD_found;

    public static void main(String[] args)
    {
        RetrieveCommitsLog retrieveCommitsLog = new RetrieveCommitsLog();
        RealSATDDetector realSATDDetector = new RealSATDDetector();

        List<Commit> commits = new ArrayList<>();

        for(int I=0; I<10; I++)
        {
            Commit commit = new Commit(String.valueOf(I), "Alessandro Bergamo", "good commit", new Date());

            commits.add(commit);
        }

        Commit commitPEZZOTTO = new Commit("pezzott@1937", "Alessandro Bergamo", "something bad happened, remove this code, get rid of this", new Date());

        commits.add(commitPEZZOTTO);

        realSATDDetector.detectSATD(commits);
    }

}
