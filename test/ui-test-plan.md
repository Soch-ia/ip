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
 Now you have 1 tasks in the list.
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
 Now you have 1 tasks in the list.
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
