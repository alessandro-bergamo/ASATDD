package application.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.ui.Messages;
import core.entities.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class FindSATDAction extends AnAction
{

    private int SATD_found;
    private Collection<Component> results;

    @Override
    public void actionPerformed(@NotNull AnActionEvent e)
    {
        return;
    }
}

