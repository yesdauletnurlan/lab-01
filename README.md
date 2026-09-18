# Product: Jira

We build **Jira**. People track **issues**.

## Core item
**Issue** (IssueId, IssueStatus, IssuePolicy)

## Status table
| From | To | Allowed? | Business Reason |
| :--- | :--- | :--- | :--- |
| TODO | IN_PROGRESS | Allowed | A developer starts working on an issue. |
| IN_PROGRESS | DONE | Allowed | The work is complete and verified by QA. |
| DONE | TODO | Forbidden | A closed issue cannot be reopened as TODO — would lose resolution history. |
| TODO | DONE | Forbidden | Cannot mark as DONE without working on it — bypasses the workflow. |

## Forbidden — why
1. **DONE → TODO**: A resolved issue must be reopened through a proper "Reopen" action, not reset to TODO — otherwise we lose resolution audit.
2. **TODO → DONE**: Skipping IN_PROGRESS means no one actually worked on the issue — bypasses code review and QA.