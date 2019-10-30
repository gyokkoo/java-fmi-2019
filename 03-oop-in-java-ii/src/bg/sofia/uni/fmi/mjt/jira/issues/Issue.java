package bg.sofia.uni.fmi.mjt.jira.issues;

import bg.sofia.uni.fmi.mjt.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.mjt.jira.enums.WorkAction;

import java.time.LocalDateTime;

public abstract class Issue {

    private static final int MAX_HISTORY = 20;
    private static int issueNumber = 0;

    private IssuePriority priority;
    private Component component;
    private String description;

    private IssueStatus status;
    private IssueResolution resolution;

    private LocalDateTime creationDate;

    private LocalDateTime lastChange;

    private String[] workActions = new String[MAX_HISTORY];
    private int actionsCount = 0;

    private String issueId;

    protected boolean hasFixes;
    protected boolean hasTests;
    protected boolean hasDesign;
    protected boolean hasImplementation;

    protected Issue(IssuePriority priority, Component component, String description) {
        this.priority = priority;
        this.component = component;
        this.description = description;

        this.creationDate = LocalDateTime.now();
        this.lastChange = creationDate;
        this.resolution = IssueResolution.UNRESOLVED;
        this.status = IssueStatus.OPEN;

        this.issueId = this.component.getShortName() + "-" + issueNumber;
        issueNumber++;
    }

    public String getIssueID() {
        return issueId;
    }

    public String getDescription() {
        return description;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public IssueResolution getResolution() {
        return resolution;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public Component getComponent() {
        return component;
    }

    public LocalDateTime getCreatedOn() {
        return creationDate;
    }

    public LocalDateTime getLastModifiedOn() {
        return lastChange;
    }

    public String[] getActionLog() {
        return workActions;
    }

    public void setResolution(IssueResolution resolution) {
        if (resolution == null) {
            throw new RuntimeException("Resolution cannot be null!");
        }

        this.resolution = resolution;
        this.setLastChange(LocalDateTime.now());
    }

    public void setStatus(IssueStatus status) {
        if (status == null) {
            throw new RuntimeException("Status cannot be null");
        }

        this.status = status;
        this.setLastChange(LocalDateTime.now());
    }

    public void setLastChange(LocalDateTime lastChange) {
        if (lastChange == null) {
            throw new RuntimeException("Last change cannot be null!");
        }

        this.lastChange = lastChange;
    }

    public void addAction(WorkAction action, String description) {
        if (action == null || description == null) {
            throw new IllegalArgumentException("Action and description cannot be null!");
        }

        if (this.actionsCount >= MAX_HISTORY) {
            throw new RuntimeException("Actions limit exceeded!");
        }

        updateIssueStates(action);

        workActions[actionsCount++] = action.getAbbreviation() + ": " + description;
        setLastChange(LocalDateTime.now());
    }

    public abstract void resolve(IssueResolution resolution);

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        return this.getIssueID().equals(((Issue) obj).getIssueID());
    }

    private void updateIssueStates(WorkAction action) {
        switch (action) {
            case TESTS:
                hasTests = true;
                break;
            case FIX:
                hasFixes = true;
                break;
            case IMPLEMENTATION:
                hasImplementation = true;
                break;
            case DESIGN:
                hasDesign = true;
                break;
        }
    }
}
