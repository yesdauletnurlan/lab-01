package org.example.domain;

public class TransitionRule implements Rule {

    @Override
    public void check(IssueStatus from, IssueStatus to) {
        if (from == IssueStatus.TODO && to == IssueStatus.IN_PROGRESS) {
            return;
        }
        if (from == IssueStatus.IN_PROGRESS && to == IssueStatus.DONE) {
            return;
        }
        throw new IllegalStateException(
                String.format("Transition from %s to %s is forbidden.", from, to)
        );
    }
}