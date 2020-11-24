package core.entities;

import java.time.LocalDateTime;

public class Commit
{

    public Commit()
    {
        //Empty constructor
    }

    public Commit(String commitID, String commitMessage, String commitUser, int commitDate)
    {
        this.commitID = commitID;
        this.commitMessage = commitMessage;
        this.commitUser = commitUser;
        this.commitDate = commitDate;
    }

    public String getCommitID() { return commitID; }

    public void setCommitID(String commitID) { this.commitID = commitID; }

    public String getCommitMessage() { return commitMessage; }

    public void setCommitMessage(String commitMessage) { this.commitMessage = commitMessage; }

    public String getCommitUser() { return commitUser; }

    public void setCommitUser(String commitUser) { this.commitUser = commitUser; }

    public int getCommitDate() { return commitDate; }

    public void setCommitDate(int commitDate) { this.commitDate = commitDate; }



    protected String commitID, commitMessage, commitUser;
    protected int commitDate;

}
