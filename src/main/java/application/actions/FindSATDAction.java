package application.actions;

import application.presenters.BasicPresenter;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import core.entities.Commit;
import core.entities.detector.RealSATDDetector;
import core.usecases.identifySATD.IdentifySATDInteractor;
import core.util.RetrieveCommitsLog;

import java.util.*;
import javafx.util.Pair;

import org.jetbrains.annotations.NotNull;


public class FindSATDAction extends AnAction
{

    @Override
    public void actionPerformed(@NotNull AnActionEvent e)
    {
        ProgressManager.getInstance().runProcessWithProgressSynchronously(() ->
        {
            ProgressIndicator indicator = ProgressManager.getInstance().getProgressIndicator();
            indicator.setIndeterminate(true);
            indicator.setText("Analyzing project...");

            ApplicationManager.getApplication().runReadAction(() ->
            {
                Project project = e.getProject();

                RetrieveCommitsLog retrieveCommitsLog = new RetrieveCommitsLog();
                RealSATDDetector realSATDDetector = new RealSATDDetector();

                try {
                    IdentifySATDInteractor identifySATDInteractor = new IdentifySATDInteractor(retrieveCommitsLog, realSATDDetector, Objects.requireNonNull(project).getBasePath());
                    owner_commits = identifySATDInteractor.execute();

                    SATD_found = owner_commits.getValue();
                    repository_url = owner_commits.getKey();
                } catch (Exception exception) {
                    Messages.showMessageDialog(e.getProject(), "Detection not successfull", "WARNING!", Messages.getErrorIcon());
                }
            });

        }, "Detecting self-admitted technical debt", false, e.getProject());

        Messages.showMessageDialog(e.getProject(), "Detection completed successfully! Found " + SATD_found.size() + " Self-Admitted Technical Debt", "ATTENTION!", Messages.getInformationIcon());

        basicPresenter.runFrame(repository_url, SATD_found);
    }



    private Pair<String, List<Commit>> owner_commits;
    private String repository_url;
    private List<Commit> SATD_found;
    private final BasicPresenter basicPresenter = new BasicPresenter();

}

