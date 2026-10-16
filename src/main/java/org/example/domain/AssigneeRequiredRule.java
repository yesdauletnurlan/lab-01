package org.example.domain;

public class AssigneeRequiredRule implements Rule {

    private final boolean hasAssignee;

    public AssigneeRequiredRule(boolean hasAssignee) {
        this.hasAssignee = hasAssignee;
    }

    @Override
    public void check(IssueStatus from, IssueStatus to) {
        if (to == IssueStatus.IN_PROGRESS && !hasAssignee) {
            throw new IllegalStateException(
                    "Cannot start work: no assignee on the issue"
            );
        }
    }
}