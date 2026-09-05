# Rene User Guide

Rene is a personal task chatbot. It keeps todos, deadlines, and events in
`data/rene.txt` so they remain available the next time the application starts.

## Command summary

Enter `help` to see the command reference inside Rene:

```text
 Here are Rene's commands:
 todo DESCRIPTION
 deadline DESCRIPTION /by yyyy-MM-dd
 event DESCRIPTION /from START /to END
 list
 mark NUMBER
 unmark NUMBER
 delete NUMBER
 find KEYWORD
 help
 bye
```

## Managing tasks

| Purpose | Command | Example |
| --- | --- | --- |
| Add a todo | `todo DESCRIPTION` | `todo read chapter 3` |
| Add a deadline | `deadline DESCRIPTION /by yyyy-MM-dd` | `deadline submit report /by 2026-09-30` |
| Add an event | `event DESCRIPTION /from START /to END` | `event study group /from 2pm /to 4pm` |
| Show all tasks | `list` | `list` |
| Mark a task done | `mark NUMBER` | `mark 1` |
| Mark a task not done | `unmark NUMBER` | `unmark 1` |
| Remove a task | `delete NUMBER` | `delete 1` |
| Find tasks by description | `find KEYWORD` | `find report` |
| Show command help | `help` | `help` |
| Exit Rene | `bye` | `bye` |

Task numbers are the one-based positions shown by `list`. Deadline dates use
the ISO `yyyy-MM-dd` format. Searches ignore letter case and match task
descriptions only.
