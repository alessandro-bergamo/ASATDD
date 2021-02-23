package application.presenters;

import com.intellij.openapi.ui.Messages;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import core.entities.Commit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.Date;
import java.util.List;

import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;


public class BasicPresenter
{

    public void runFrame(String repository_url, List<Commit> commitArrayList)
    {
        JFrame frame = new JFrame("Automatic Self-Admitted Technical Debt Detection");

        String[] col = {"ID", "User", "Date", "Message"};

        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        table_SATD = new JTable(tableModel);
        table_SATD.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        for(Commit commit : commitArrayList)
        {
            String id = commit.getCommitID();
            String user = commit.getCommitUser();
            Date date = commit.getCommitDate();
            String message = commit.getCommitMessage();

            Object[] data = {id, user, date, message};

            tableModel.addRow(data);
        }

        JScrollPane scrollPane = new JScrollPane(table_SATD);

        table_SATD.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                int row = table_SATD.rowAtPoint(evt.getPoint());
                int col = table_SATD.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0)
                {
                    String commit_url = repository_url.substring(0, repository_url.length()-4) + "/commit/" +commitArrayList.get(row).getCommitID();
                    StringSelection stringSelection = new StringSelection(commit_url);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
                }

                Messages.showMessageDialog("Link copied to clipboard", "", Messages.getInformationIcon());
            }
        });

        frame.setSize(1200, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        frame.add(scrollPane);
    }


    private JTable table_SATD;
    private JPanel mainPanel;

}
