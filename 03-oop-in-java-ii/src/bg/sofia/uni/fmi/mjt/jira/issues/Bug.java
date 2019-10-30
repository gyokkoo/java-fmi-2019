package bg.sofia.uni.fmi.mjt.jira.issues;

import bg.sofia.uni.fmi.mjt.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueStatus;

public class Bug extends Issue {

    public Bug(IssuePriority priority, Component component, String description) {
        super(priority, component, description);
    }

    @Override
    public void resolve(IssueResolution resolution) {
        if (!hasFixes || !hasTests) {
            throw new RuntimeException("Cannot resolve bug without fixes and tests!");
        }

        setResolution(resolution);
        setStatus(IssueStatus.RESOLVED);
    }
}
