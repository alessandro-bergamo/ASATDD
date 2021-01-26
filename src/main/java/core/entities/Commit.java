package core.entities;

import java.util.Date;

public class Commit
{

    public Commit(String commitID, String commitUser, String commitMessage, Date commitDate)
    {
        this.commitID = commitID;
        this.commitUser = commitUser;
        this.commitMessage = commitMessage;
        this.commitDate = commitDate;
    }

    public String getCommitID() { return commitID; }

    public void setCommitID(String commitID) { this.commitID = commitID; }

    public String getCommitUser() { return commitUser; }

    public void setCommitUser(String commitUser) { this.commitUser = commitUser; }

    public String getCommitMessage() { return commitMessage; }

    public void setCommitMessage(String commitMessage) { this.commitMessage = commitMessage; }

    public Date getCommitDate() { return commitDate; }

    public void setCommitDate(Date commitDate) { this.commitDate = commitDate; }

    @Override
    public String toString() { return "Commit ID: "+commitID+" -/- Commit User: "+commitUser+" -/- Commit Date: "+commitDate+" -/- Commit Message: "+commitMessage;}

    protected String commitID, commitUser, commitMessage;
    protected Date commitDate;

}
