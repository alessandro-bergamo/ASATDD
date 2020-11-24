package application.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import core.entities.Commit;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class FindSATDAction extends AnAction
{

    private int SATD_found;
    private Collection<Commit> results;

    @Override
    public void actionPerformed(@NotNull AnActionEvent e)
    {
        return;
    }
}

