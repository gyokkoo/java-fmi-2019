package bg.sofia.uni.fmi.mjt.jira;

import bg.sofia.uni.fmi.mjt.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.mjt.jira.enums.WorkAction;
import bg.sofia.uni.fmi.mjt.jira.interfaces.Filter;
import bg.sofia.uni.fmi.mjt.jira.interfaces.Repository;
import bg.sofia.uni.fmi.mjt.jira.issues.Issue;

public class Jira implements Filter, Repository {

    private static final int MAX_ISSUES_COUNT = 100;

    private Issue[] issues = new Issue[MAX_ISSUES_COUNT];
    private int issuesCount = 0;

    public Jira() {
    }

    public void addActionToIssue(Issue issue, WorkAction action, String actionDescription) {
        if (issue == null) {
            throw new RuntimeException("Issue cannot be null");
        }

        issue.addAction(action, actionDescription);
    }

    public void resolveIssue(Issue issue, IssueResolution resolution) {
        if (issue == null) {
            throw new RuntimeException("Arguments cannot be null!");
        }

        issue.resolve(resolution);
    }

    @Override
    public Issue find(String issueId) {
        for (int i = 0; i < issuesCount; i++) {
            if (issues[i].getIssueID().equals(issueId)) {
                return issues[i];
            }
        }

        return null;
    }

    @Override
    public void addIssue(Issue issueToAdd) {
        if (issuesCount >= MAX_ISSUES_COUNT) {
            throw new RuntimeException("Cannot add more issues, limit exceeded!");
        }

        if (issueToAdd == null || issueToAdd.getIssueID() == null) {
            throw new RuntimeException("Issue cannot be null!");
        }

        if (find(issueToAdd.getIssueID()) != null) {
            throw new RuntimeException("Issue already exists!");
        }

        issues[issuesCount++] = issueToAdd;
    }
}
