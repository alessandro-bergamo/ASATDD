package application.actions;

import com.intellij.openapi.project.Project;
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

        IdentifySATDInteractor identifySATDInteractor = new IdentifySATDInteractor(retrieveCommitsLog, realSATDDetector, "C:\\Users\\Alessandro\\IdeaProjects\\TechLord-v2");
        SATD_found = identifySATDInteractor.execute();

        System.out.println("\n"+SATD_found);
    }

}
