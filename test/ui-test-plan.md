# UI test plan

Each test case records its aim, run command, inputs, and complete expected console output. The runner compares output exactly, apart from platform line-ending differences.

## Test case: Exit politely

### Aim

Verify that the chatbot greets the user and exits with its farewell message.

### Run command

```sh
javac src/main/java/*.java && java -cp src/main/java Rene
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

## Test case: Reject repeated status changes

### Aim

Verify that marking a completed task and unmarking an incomplete task are rejected without changing the task's status.

### Run command

```sh
javac src/main/java/*.java && java -cp src/main/java Rene
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

Verify that todos, deadlines, and events are stored polymorphically and displayed with their type-specific details. Also verify that date and time values are kept as strings.

### Run command

```sh
javac src/main/java/*.java && java -cp src/main/java Rene
```

### Inputs

```text
todo borrow book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
deadline do homework /by no idea :-p
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
   [D][ ] return book (by: Sunday)
 Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
 Got it. I've added this task:
   [E][ ] project meeting (from: Mon 2pm to: 4pm)
 Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
 Got it. I've added this task:
   [D][ ] do homework (by: no idea :-p)
 Now you have 4 tasks in the list.
____________________________________________________________
____________________________________________________________
 Here are the tasks in your list:
 1.[T][ ] borrow book
 2.[D][ ] return book (by: Sunday)
 3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
 4.[D][ ] do homework (by: no idea :-p)
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
javac src/main/java/*.java && java -cp src/main/java Rene
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
javac src/main/java/*.java && java -cp src/main/java Rene
```

### Inputs

```text
todo
todo read chapter 3
blah
deadline return book
deadline return book /by Friday
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
 Oops — A deadline needs /by. Try: deadline submit report /by Friday
____________________________________________________________
____________________________________________________________
 Got it. I've added this task:
   [D][ ] return book (by: Friday)
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
 2.[D][ ] return book (by: Friday)
 3.[E][ ] group study (from: 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test case: Delete a task and renumber the list

### Aim

Verify that `delete` removes the selected task, reports its original completed status, updates the task count, and closes the numbering gap in a list backed by a dynamic collection.

### Run command

```sh
javac src/main/java/*.java && java -cp src/main/java Rene
```

### Inputs

```text
todo read book
deadline return book /by June 6th
event project meeting /from Aug 6th 2pm /to 4pm
mark 2
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
   [T][ ] read book
 Now you have 1 task in the list.
____________________________________________________________
____________________________________________________________
 Got it. I've added this task:
   [D][ ] return book (by: June 6th)
 Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
 Got it. I've added this task:
   [E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
 Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
 Nice! I've marked this task as done:
   [D][X] return book (by: June 6th)
____________________________________________________________
____________________________________________________________
 Noted. I've removed this task:
   [D][X] return book (by: June 6th)
 Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
 Here are the tasks in your list:
 1.[T][ ] read book
 2.[E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```
