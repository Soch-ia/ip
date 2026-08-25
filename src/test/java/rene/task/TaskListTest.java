package rene.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rene.exception.ReneException;

class TaskListTest {
    private TaskList tasks;

    @BeforeEach
    void setUp() {
        tasks = new TaskList();
        tasks.add(new Todo("first task"));
        tasks.add(new Todo("second task"));
    }

    @Test
    void mark_incompleteTask_marksRequestedTask() throws ReneException {
        Task markedTask = tasks.mark(2);

        assertEquals("second task", markedTask.getDescription());
        assertTrue(markedTask.isDone());
        assertFalse(tasks.getTasks().get(0).isDone());
    }

    @Test
    void mark_completedTask_throwsExceptionWithoutChangingOtherTask() throws ReneException {
        tasks.mark(1);

        ReneException exception = assertThrows(ReneException.class, () -> tasks.mark(1));

        assertEquals("That task is already done — no need to mark it twice.", exception.getMessage());
        assertFalse(tasks.getTasks().get(1).isDone());
    }

    @Test
    void unmark_completedTask_marksRequestedTaskIncomplete() throws ReneException {
        tasks.mark(1);

        Task unmarkedTask = tasks.unmark(1);

        assertFalse(unmarkedTask.isDone());
    }

    @Test
    void remove_firstTask_renumbersRemainingTask() throws ReneException {
        Task removedTask = tasks.remove(1);

        assertEquals("first task", removedTask.getDescription());
        assertEquals(1, tasks.size());
        assertEquals("second task", tasks.getTasks().get(0).getDescription());
    }

    @Test
    void remove_invalidPositions_throwsExceptionWithoutChangingList() {
        assertThrows(ReneException.class, () -> tasks.remove(0));
        assertThrows(ReneException.class, () -> tasks.remove(-1));
        assertThrows(ReneException.class, () -> tasks.remove(3));

        assertEquals(2, tasks.size());
    }

    @Test
    void getTasks_returnedSnapshotCannotModifyTaskList() {
        assertThrows(UnsupportedOperationException.class, () -> tasks.getTasks().clear());
        assertEquals(2, tasks.size());
    }
}
