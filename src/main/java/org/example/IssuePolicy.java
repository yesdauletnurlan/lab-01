package org.example;

public class IssuePolicy {

    public IssueStatus move(IssueStatus from, IssueStatus to) {
        // Allowed transitions
        if (from == IssueStatus.TODO && to == IssueStatus.IN_PROGRESS) {
            return to;
        }
        if (from == IssueStatus.IN_PROGRESS && to == IssueStatus.DONE) {
            return to;
        }

        // Forbidden
        throw new IllegalStateException(
                String.format("Transition from %s to %s is forbidden in Jira workflow.", from, to)
        );
    }
}