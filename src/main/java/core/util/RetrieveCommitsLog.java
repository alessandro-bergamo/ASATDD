package core.util;

import com.intellij.ide.ui.EditorOptionsTopHitProvider;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.thoughtworks.xstream.core.util.ObjectIdDictionary;
import core.entities.Commit;
import core.entities.detector.DummySATDDetector;
import core.entities.detector.ImpossibleIdentification;
import org.eclipse.jgit.api.Git; //inserire nel readme l'utlizzo di questa libreria
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.io.DisabledOutputStream;


import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RetrieveCommitsLog
{

    public RetrieveCommitsLog()
    {
        repository = null;
        logCommits = new ArrayList<>();
    }


    public ArrayList<Commit> retrieveCommitsLogs(String repository_path)
    {
        try {
            repository = Git.open(new File(repository_path)).getRepository();
        } catch (Exception e) {
            throw new ImpossibleIdentification();
        }

        Git git = new Git(repository);
        Iterable<RevCommit> log;

        try {
            log = git.log().call();
        } catch (Exception e) {
            throw new ImpossibleIdentification();
        }

        for(Iterator<RevCommit> iterator = log.iterator(); iterator.hasNext();)
        {
            RevCommit rev = iterator.next();

            Commit commit = new Commit (
                    rev.getId().getName(),
                    rev.getCommitterIdent().getName(),
                    rev.getFullMessage(),
                    rev.getAuthorIdent().getWhen()
            );

            /* CODICE DI STAMPA DEI FILE MODIFICATI NEL COMMIT
            System.out.println("\nCOMMIT START ----------------------------"+commit.getCommitID()+" MESSAGE: "+commit.getCommitMessage());

            List<DiffEntry> diffs;
            try (RevWalk rw = new RevWalk(repository))
            {

                DiffFormatter df = new DiffFormatter(DisabledOutputStream.INSTANCE);

                df.setRepository(repository);
                df.setDiffComparator(RawTextComparator.DEFAULT);
                df.setDetectRenames(true);

                if (iterator.hasNext()) {
                    RevCommit parent = rw.parseCommit(rev.getParent(0).getId());

                    diffs = df.scan(parent.getTree(), rev.getTree());
                } else {
                    diffs = df.scan(new EmptyTreeIterator(), new CanonicalTreeParser(null, rw.getObjectReader(), rev.getTree()));
                }
            } catch(Exception e) {
                throw new ImpossibleIdentification();
            }

            for (DiffEntry diff : diffs)
            {
                if(!diff.getChangeType().name().equals("DELETE"))
                    System.out.println("Change Type: " + diff.getChangeType().name() + " -/- File Path: " + diff.getNewPath());
            }

            System.out.println("COMMIT END ----------------------------");
            */

            logCommits.add(commit);
        }

        return logCommits; //CAMBIARE CIO CHE TORNA QUESTO METODO SE NECESSARIO
    }



    private Repository repository;
    private final ArrayList<Commit> logCommits;

}
