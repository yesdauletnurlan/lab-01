# Product: Jira

We build **Jira**. People track **issues**.

## Core item
**Issue** (IssueId, IssueStatus, Rule)

Two rules implement the `Rule` interface:
- `TransitionRule` — checks the status table.
- `AssigneeRequiredRule` — stop-factor: cannot start work without an assignee.

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

## Architecture

The project follows Clean Architecture — dependencies point inward.
The `domain` package is plain Java and never imports `org.springframework`.

### Package diagram

    dto          client        handler         config
    (JSON later) (HTTP later)  (HTTP week 9)   Application
                                                 @Service
           \            \            /                |
            \            \          /           injects Rule
             \            \        /
                         domain
                  IssueId  IssueStatus  Rule
                  TransitionRule  AssigneeRequiredRule
                         (no Spring)

Arrows point inward. Domain never imports org.springframework.

### Packages

| Package  | Responsibility |
| :--- | :--- |
| `domain` | Pure business logic: `IssueId`, `IssueStatus`, `Rule` + 2 implementations. No Spring. |
| `config` | Spring Boot wiring: `Application`, `IssueService` (`@Service`), `RuleConfig` (`@Bean`). |
| `dto`    | Data Transfer Objects — JSON mapping (later). |
| `client` | Outgoing HTTP clients (later). |
| `handler`| Incoming HTTP handlers / REST controllers (week 9). |

### Flow at runtime

1. Spring Boot starts via `Application` (`@SpringBootApplication`).
2. `RuleConfig` registers two `Rule` beans: `TransitionRule`, `AssigneeRequiredRule`.
3. Spring injects `List<Rule>` into `IssueService` (`@Service`).
4. `IssueService.move(from, to)` runs every rule in order.
5. Any failing rule throws `IllegalStateException`.