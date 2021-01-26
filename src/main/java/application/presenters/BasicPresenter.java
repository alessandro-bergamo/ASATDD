package application.presenters;

import core.entities.Commit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.Date;
import java.util.List;

public class BasicPresenter
{

    public void runFrame(List<Commit> commitArrayList)
    {
        JFrame frame = new JFrame("SATD-Detector");

        String col[] = {"ID", "User", "Date", "Message"};

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

        scrollPane = new JScrollPane(table_SATD);

        frame.setSize(1200, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        frame.add(scrollPane);
    }


    private JTable table_SATD;
    private JScrollPane scrollPane;

}
