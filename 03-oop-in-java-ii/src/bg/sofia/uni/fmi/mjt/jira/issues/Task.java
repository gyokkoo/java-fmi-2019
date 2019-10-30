package bg.sofia.uni.fmi.mjt.jira.issues;

import bg.sofia.uni.fmi.mjt.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueStatus;
import bg.sofia.uni.fmi.mjt.jira.enums.WorkAction;

public class Task extends Issue {

    public Task(IssuePriority priority, Component component, String description) {
        super(priority, component, description);
    }

    @Override
    public void resolve(IssueResolution resolution) {
        if (resolution == null) {
            throw new RuntimeException("Resolution cannot be null!");
        }

        setResolution(resolution);
        setStatus(IssueStatus.RESOLVED);
    }

    @Override
    public void addAction(WorkAction action, String description) {
        if (WorkAction.TESTS.equals(action) ||
                WorkAction.FIX.equals(action) ||
                WorkAction.IMPLEMENTATION.equals(action)) {

            throw new RuntimeException("Task actions are not valid");
        }

        super.addAction(action, description);
    }
}
