# UI test plan

Each test case records its aim, run command, inputs, and complete expected console output. The runner compares output exactly, apart from platform line-ending differences.

## Test case: Exit politely

### Aim

Verify that the chatbot greets the user and exits with its farewell message.

### Run command

```sh
rm -rf _temp/ui-test-classes && mkdir -p _temp/ui-test-classes && javac -d _temp/ui-test-classes $(rg --files src/main/java -g '*.java') && rm -f _temp/ui-test-data.txt && java -cp _temp/ui-test-classes rene.Rene _temp/ui-test-data.txt
```

### Inputs

```text
bye
```

### Expected output

```text
____________________________________________________________
 ____
|  _ \ ___ _ __   ___
| |_) / _ \ '_ \ / _ \
|  _ <  __/ | | |  __/
|_| \_\___|_| |_|\___|
Hello! I'm Rene.
What can I do for you?
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case: Save tasks for the next session

### Aim

Verify that Rene saves todos, deadlines, events, and completion status for a later application session.

### Run command

```sh
rm -rf _temp/ui-test-classes && mkdir -p _temp/ui-test-classes && javac -d _temp/ui-test-classes $(rg --files src/main/java -g '*.java') && rm -f _temp/ui-test-persistence.txt && java -cp _temp/ui-test-classes rene.Rene _temp/ui-test-persistence.txt
```

### Inputs

```text
todo read book
deadline return book /by 2026-06-06
event project meeting /from Aug 6th 2pm /to 4pm
mark 2
unmark 2
mark 3
delete 1
bye
```

### Expected output

```text
____________________________________________________________
 ____
|  _ \ ___ _ __   ___
| |_) / _ \ '_ \ / _ \
|  _ <  __/ | | |  __/
|_| \_\___|_| |_|\___|
Hello! I'm Rene.
What can I do for you?
____________________________________________________________
____________________________________________________________
 Got it. I've added this task:
   [T][ ] read book
 Now you have 1 task in the list.
____________________________________________________________
____________________________________________________________
 Got it. I've added this task:
   [D][ ] return book (by: Jun 6 2026)
 Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
 Got it. I've added this task:
   [E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
 Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
 Nice! I've marked this task as done:
   [D][X] return book (by: Jun 6 2026)
____________________________________________________________
____________________________________________________________
 OK, I've marked this task as not done yet:
   [D][ ] return book (by: Jun 6 2026)
____________________________________________________________
____________________________________________________________
 Nice! I've marked this task as done:
   [E][X] project meeting (from: Aug 6th 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
 Noted. I've removed this task:
   [T][ ] read book
 Now you have 2 tasks in the list.
 The remaining tasks have been renumbered.
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case: Load tasks from the previous session

### Aim

Verify that Rene reloads every saved task and its completion status when the application starts again.

### Run command

```sh
rm -rf _temp/ui-test-classes && mkdir -p _temp/ui-test-classes && javac -d _temp/ui-test-classes $(rg --files src/main/java -g '*.java') && java -cp _temp/ui-test-classes rene.Rene _temp/ui-test-persistence.txt; ui_status=$?; rm -f _temp/ui-test-persistence.txt; exit $ui_status
```

### Inputs

```text
list
bye
```

### Expected output

```text
____________________________________________________________
 ____
|  _ \ ___ _ __   ___
| |_) / _ \ '_ \ / _ \
|  _ <  __/ | | |  __/
|_| \_\___|_| |_|\___|
Hello! I'm Rene.
What can I do for you?
____________________________________________________________
____________________________________________________________
 Here are the tasks in your list:
 1.[D][ ] return book (by: Jun 6 2026)
 2.[E][X] project meeting (from: Aug 6th 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case: Reject repeated status changes

### Aim

Verify that marking a completed task and unmarking an incomplete task are rejected without changing the task's status.

### Run command

```sh
rm -rf _temp/ui-test-classes && mkdir -p _temp/ui-test-classes && javac -d _temp/ui-test-classes $(rg --files src/main/java -g '*.java') && rm -f _temp/ui-test-data.txt && java -cp _temp/ui-test-classes rene.Rene _temp/ui-test-data.txt
```

### Inputs

```text
todo revise notes
unmark 1
mark 1
mark 1
unmark 1
unmark 1
list
bye
```

### Expected output

```text
____________________________________________________________
 ____
|  _ \ ___ _ __   ___
| |_) / _ \ '_ \ / _ \
|  _ <  __/ | | |  __/
|_| \_\___|_| |_|\___|
Hello! I'm Rene.
What can I do for you?
____________________________________________________________
____________________________________________________________
 Got it. I've added this task:
   [T][ ] revise notes
 Now you have 1 task in the list.
____________________________________________________________
____________________________________________________________
 Oops — That task is not done yet, so there is nothing to unmark.
____________________________________________________________
____________________________________________________________
 Nice! I've marked this task as done:
   [T][X] revise notes
____________________________________________________________
____________________________________________________________
 Oops — That task is already done — no need to mark it twice.
____________________________________________________________
____________________________________________________________
 OK, I've marked this task as not done yet:
   [T][ ] revise notes
____________________________________________________________
____________________________________________________________
 Oops — That task is not done yet, so there is nothing to unmark.
____________________________________________________________
____________________________________________________________
 Here are the tasks in your list:
 1.[T][ ] revise notes
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case: Add and list all task types

### Aim

Verify that todos, deadlines, and events are stored polymorphically and displayed with their type-specific details.

### Run command

```sh
rm -rf _temp/ui-test-classes && mkdir -p _temp/ui-test-classes && javac -d _temp/ui-test-classes $(rg --files src/main/java -g '*.java') && rm -f _temp/ui-test-data.txt && java -cp _temp/ui-test-classes rene.Rene _temp/ui-test-data.txt
```

### Inputs

```text
todo borrow book
deadline return book /by 2026-08-30
event project meeting /from Mon 2pm /to 4pm
deadline do homework /by 2026-09-01
list
bye
```

### Expected output

```text
____________________________________________________________
 ____
|  _ \ ___ _ __   ___
| |_) / _ \ '_ \ / _ \
|  _ <  __/ | | |  __/
|_| \_\___|_| |_|\___|
Hello! I'm Rene.
What can I do for you?
____________________________________________________________
____________________________________________________________
 Got it. I've added this task:
   [T][ ] borrow book
 Now you have 1 task in the list.
____________________________________________________________
____________________________________________________________
 Got it. I've added this task:
   [D][ ] return book (by: Aug 30 2026)
 Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
 Got it. I've added this task:
   [E][ ] project meeting (from: Mon 2pm to: 4pm)
 Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
 Got it. I've added this task:
   [D][ ] do homework (by: Sep 1 2026)
 Now you have 4 tasks in the list.
____________________________________________________________
____________________________________________________________
 Here are the tasks in your list:
 1.[T][ ] borrow book
 2.[D][ ] return book (by: Aug 30 2026)
 3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
 4.[D][ ] do homework (by: Sep 1 2026)
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case: Mark and unmark a typed task

### Aim

Verify that completion status changes preserve the todo type marker.

### Run command

```sh
rm -rf _temp/ui-test-classes && mkdir -p _temp/ui-test-classes && javac -d _temp/ui-test-classes $(rg --files src/main/java -g '*.java') && rm -f _temp/ui-test-data.txt && java -cp _temp/ui-test-classes rene.Rene _temp/ui-test-data.txt
```

### Inputs

```text
todo submit assignment
mark 1
unmark 1
bye
```

### Expected output

```text
____________________________________________________________
 ____
|  _ \ ___ _ __   ___
| |_) / _ \ '_ \ / _ \
|  _ <  __/ | | |  __/
|_| \_\___|_| |_|\___|
Hello! I'm Rene.
What can I do for you?
____________________________________________________________
____________________________________________________________
 Got it. I've added this task:
   [T][ ] submit assignment
 Now you have 1 task in the list.
____________________________________________________________
____________________________________________________________
 Nice! I've marked this task as done:
   [T][X] submit assignment
____________________________________________________________
____________________________________________________________
 OK, I've marked this task as not done yet:
   [T][ ] submit assignment
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case: Reject malformed commands without changing tasks

### Aim

Verify that invalid commands are reported through ReneException, and that later valid commands still operate on the correct task list.

### Run command

```sh
rm -rf _temp/ui-test-classes && mkdir -p _temp/ui-test-classes && javac -d _temp/ui-test-classes $(rg --files src/main/java -g '*.java') && rm -f _temp/ui-test-data.txt && java -cp _temp/ui-test-classes rene.Rene _temp/ui-test-data.txt
```

### Inputs

```text
todo
todo read chapter 3
blah
deadline return book
deadline return book /by 2026-08-28
event group study /from 2pm /to 4pm
event movie /from 7pm
mark nope
mark 9
mark 1
unmark 1
list
bye
```

### Expected output

```text
____________________________________________________________
 ____
|  _ \ ___ _ __   ___
| |_) / _ \ '_ \ / _ \
|  _ <  __/ | | |  __/
|_| \_\___|_| |_|\___|
Hello! I'm Rene.
What can I do for you?
____________________________________________________________
____________________________________________________________
 Oops — A todo needs a description. Try: todo read chapter 3
____________________________________________________________
____________________________________________________________
 Got it. I've added this task:
   [T][ ] read chapter 3
 Now you have 1 task in the list.
____________________________________________________________
____________________________________________________________
 Oops — I don't know that command yet. Try todo, deadline, event, list, mark, unmark, delete, or bye.
____________________________________________________________
____________________________________________________________
 Oops — A deadline needs /by. Try: deadline submit report /by 2026-08-31
____________________________________________________________
____________________________________________________________
 Got it. I've added this task:
   [D][ ] return book (by: Aug 28 2026)
 Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
 Got it. I've added this task:
   [E][ ] group study (from: 2pm to: 4pm)
 Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
 Oops — An event needs /from and /to. Try: event study group /from 2pm /to 4pm
____________________________________________________________
____________________________________________________________
 Oops — Please give me a whole-number task position, like: mark 1
____________________________________________________________
____________________________________________________________
 Oops — That task number is not in the list yet.
____________________________________________________________
____________________________________________________________
 Nice! I've marked this task as done:
   [T][X] read chapter 3
____________________________________________________________
____________________________________________________________
 OK, I've marked this task as not done yet:
   [T][ ] read chapter 3
____________________________________________________________
____________________________________________________________
 Here are the tasks in your list:
 1.[T][ ] read chapter 3
 2.[D][ ] return book (by: Aug 28 2026)
 3.[E][ ] group study (from: 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case: Mark after deleting the first task

### Aim

Verify that deleting the first task renumbers the remaining tasks and that `mark 1` marks the new first task rather than the removed task.

### Run command

```sh
rm -rf _temp/ui-test-classes && mkdir -p _temp/ui-test-classes && javac -d _temp/ui-test-classes $(rg --files src/main/java -g '*.java') && rm -f _temp/ui-test-data.txt && java -cp _temp/ui-test-classes rene.Rene _temp/ui-test-data.txt
```

### Inputs

```text
todo read book
deadline return book /by 2026-06-06
event project meeting /from Aug 6th 2pm /to 4pm
delete 1
mark 1
list
bye
```

### Expected output

```text
____________________________________________________________
 ____
|  _ \ ___ _ __   ___
| |_) / _ \ '_ \ / _ \
|  _ <  __/ | | |  __/
|_| \_\___|_| |_|\___|
Hello! I'm Rene.
What can I do for you?
____________________________________________________________
____________________________________________________________
 Got it. I've added this task:
   [T][ ] read book
 Now you have 1 task in the list.
____________________________________________________________
____________________________________________________________
 Got it. I've added this task:
   [D][ ] return book (by: Jun 6 2026)
 Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
 Got it. I've added this task:
   [E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
 Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
 Noted. I've removed this task:
   [T][ ] read book
 Now you have 2 tasks in the list.
 The remaining tasks have been renumbered.
____________________________________________________________
____________________________________________________________
 Nice! I've marked this task as done:
   [D][X] return book (by: Jun 6 2026)
____________________________________________________________
____________________________________________________________
 Here are the tasks in your list:
 1.[D][X] return book (by: Jun 6 2026)
 2.[E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case: Reject commands for a deleted only task

### Aim

Verify that deleting the only task empties the list and prevents later mark, unmark, and delete commands from accessing it.

### Run command

```sh
rm -rf _temp/ui-test-classes && mkdir -p _temp/ui-test-classes && javac -d _temp/ui-test-classes $(rg --files src/main/java -g '*.java') && rm -f _temp/ui-test-delete-only.txt && java -cp _temp/ui-test-classes rene.Rene _temp/ui-test-delete-only.txt
```

### Inputs

```text
todo only task
delete 1
mark 1
unmark 1
delete 1
list
bye
```

### Expected output

```text
____________________________________________________________
 ____
|  _ \ ___ _ __   ___
| |_) / _ \ '_ \ / _ \
|  _ <  __/ | | |  __/
|_| \_\___|_| |_|\___|
Hello! I'm Rene.
What can I do for you?
____________________________________________________________
____________________________________________________________
 Got it. I've added this task:
   [T][ ] only task
 Now you have 1 task in the list.
____________________________________________________________
____________________________________________________________
 Noted. I've removed this task:
   [T][ ] only task
 Now you have 0 tasks in the list.
____________________________________________________________
____________________________________________________________
 Oops — That task number is not in the list yet.
____________________________________________________________
____________________________________________________________
 Oops — That task number is not in the list yet.
____________________________________________________________
____________________________________________________________
 Oops — That task number is not in the list yet.
____________________________________________________________
____________________________________________________________
 Here are the tasks in your list:
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case: Reject invalid delete positions

### Aim

Verify that nonnumeric, zero, negative, and out-of-range delete positions are rejected without removing a task.

### Run command

```sh
rm -rf _temp/ui-test-classes && mkdir -p _temp/ui-test-classes && javac -d _temp/ui-test-classes $(rg --files src/main/java -g '*.java') && rm -f _temp/ui-test-invalid-delete.txt && java -cp _temp/ui-test-classes rene.Rene _temp/ui-test-invalid-delete.txt
```

### Inputs

```text
todo keep this task
delete nope
delete 0
delete -1
delete 2
list
bye
```

### Expected output

```text
____________________________________________________________
 ____
|  _ \ ___ _ __   ___
| |_) / _ \ '_ \ / _ \
|  _ <  __/ | | |  __/
|_| \_\___|_| |_|\___|
Hello! I'm Rene.
What can I do for you?
____________________________________________________________
____________________________________________________________
 Got it. I've added this task:
   [T][ ] keep this task
 Now you have 1 task in the list.
____________________________________________________________
____________________________________________________________
 Oops — Please give me a whole-number task position, like: delete 1
____________________________________________________________
____________________________________________________________
 Oops — That task number is not in the list yet.
____________________________________________________________
____________________________________________________________
 Oops — That task number is not in the list yet.
____________________________________________________________
____________________________________________________________
 Oops — That task number is not in the list yet.
____________________________________________________________
____________________________________________________________
 Here are the tasks in your list:
 1.[T][ ] keep this task
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case: Parse and reject deadline dates

### Aim

Verify that deadlines accept real ISO dates, display readable dates, and reject impossible or incorrectly formatted dates.

### Run command

```sh
rm -rf _temp/ui-test-classes && mkdir -p _temp/ui-test-classes && javac -d _temp/ui-test-classes $(rg --files src/main/java -g '*.java') && rm -f _temp/ui-test-deadline-dates.txt && java -cp _temp/ui-test-classes rene.Rene _temp/ui-test-deadline-dates.txt
```

### Inputs

```text
deadline leap day /by 2024-02-29
deadline year end /by 2026-12-31
deadline impossible /by 2026-02-29
deadline invalid month /by 2026-13-01
deadline wrong format /by 31-12-2026
deadline short date /by 2026-2-3
list
bye
```

### Expected output

```text
____________________________________________________________
 ____
|  _ \ ___ _ __   ___
| |_) / _ \ '_ \ / _ \
|  _ <  __/ | | |  __/
|_| \_\___|_| |_|\___|
Hello! I'm Rene.
What can I do for you?
____________________________________________________________
____________________________________________________________
 Got it. I've added this task:
   [D][ ] leap day (by: Feb 29 2024)
 Now you have 1 task in the list.
____________________________________________________________
____________________________________________________________
 Got it. I've added this task:
   [D][ ] year end (by: Dec 31 2026)
 Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
 Oops — A deadline needs a valid date in yyyy-MM-dd format. Try: deadline submit report /by 2026-08-31
____________________________________________________________
____________________________________________________________
 Oops — A deadline needs a valid date in yyyy-MM-dd format. Try: deadline submit report /by 2026-08-31
____________________________________________________________
____________________________________________________________
 Oops — A deadline needs a valid date in yyyy-MM-dd format. Try: deadline submit report /by 2026-08-31
____________________________________________________________
____________________________________________________________
 Oops — A deadline needs a valid date in yyyy-MM-dd format. Try: deadline submit report /by 2026-08-31
____________________________________________________________
____________________________________________________________
 Here are the tasks in your list:
 1.[D][ ] leap day (by: Feb 29 2024)
 2.[D][ ] year end (by: Dec 31 2026)
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```
