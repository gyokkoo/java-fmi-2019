package bg.sofia.uni.fmi.mjt.jira.issues;

import bg.sofia.uni.fmi.mjt.jira.enums.IssuePriority;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueResolution;
import bg.sofia.uni.fmi.mjt.jira.enums.IssueStatus;

public class Feature extends Issue {

    public Feature(IssuePriority priority, Component component, String description) {
        super(priority, component, description);
    }

    @Override
    public void resolve(IssueResolution resolution) {
        if (resolution == null) {
            throw new RuntimeException("Resolution cannot be null!");
        }

        if (!hasDesign || !hasImplementation || !hasTests) {
            throw new RuntimeException("Cannot resolve feature without design, implementation and tests!");
        }

        setResolution(resolution);
        setStatus(IssueStatus.RESOLVED);
    }
}
