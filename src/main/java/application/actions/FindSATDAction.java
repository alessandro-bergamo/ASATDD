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

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class FindSATDAction extends AnAction
{

    @Override
    public void actionPerformed(@NotNull AnActionEvent event)
    {
        ProgressManager.getInstance().runProcessWithProgressSynchronously(() ->
        {
            ProgressIndicator indicator = ProgressManager.getInstance().getProgressIndicator();
            indicator.setIndeterminate(true);
            indicator.setText("Analyzing project...");

            ApplicationManager.getApplication().runReadAction(() ->
            {
                Project project = event.getProject();

                RetrieveCommitsLog retrieveCommitsLog = new RetrieveCommitsLog();
                RealSATDDetector realSATDDetector = new RealSATDDetector();

                try {
                    IdentifySATDInteractor identifySATDInteractor = new IdentifySATDInteractor(retrieveCommitsLog, realSATDDetector, Objects.requireNonNull(project).getBasePath());
                    SATD_found = identifySATDInteractor.execute();
                } catch (Exception exception) {
                    Messages.showMessageDialog(event.getProject(), "Identification not successfull", "WARNING!", Messages.getErrorIcon());
                }
            });

        }, "Identifying self-admitted technical debt", false, event.getProject());

        Messages.showMessageDialog(event.getProject(), "Identification completed successfully! Found " + SATD_found.size() + " Self-Admitted Technical Debt", "ATTENTION!", Messages.getInformationIcon());

        basicPresenter.runFrame(SATD_found);
    }



    private List<Commit> SATD_found;
    private final BasicPresenter basicPresenter = new BasicPresenter();

}

