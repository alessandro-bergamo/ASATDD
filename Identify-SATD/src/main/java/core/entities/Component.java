package core.entities;

import java.time.LocalDateTime;

public class Component
{

    public Component(int commitID, String commitMessage, String commitUser, LocalDateTime commitDate)
    {
        this.commitID = commitID;
        this.commitMessage = commitMessage;
        this.commitUser = commitUser;
        this.commitDate = commitDate;
    }


    protected int commitID;
    protected String commitMessage, commitUser;
    protected LocalDateTime commitDate;

}
