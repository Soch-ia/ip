package rene;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class ReneTest {
    @TempDir
    private Path temporaryDirectory;

    @Test
    public void getResponse_addThenList_returnsPersistedTask() {
        Rene rene = new Rene(temporaryDirectory.resolve("rene.txt"));

        String addResponse = rene.getResponse("todo read book");
        String listResponse = rene.getResponse("list");

        assertTrue(addResponse.contains("[T][ ] read book"));
        assertEquals(" Here are the tasks in your list:\n 1.[T][ ] read book", listResponse);
    }

    @Test
    public void getResponse_invalidCommand_returnsFriendlyError() {
        Rene rene = new Rene(temporaryDirectory.resolve("rene.txt"));

        assertEquals(
                " Oops — I don't know that command yet. "
                        + "Try todo, deadline, event, list, mark, unmark, delete, find, help, or bye.",
                rene.getResponse("unknown"));
    }

    @Test
    public void getResponse_help_returnsEveryCommandSyntax() {
        Rene rene = new Rene(temporaryDirectory.resolve("rene.txt"));

        assertEquals(
                " Here are Rene's commands:\n"
                        + " todo DESCRIPTION\n"
                        + " deadline DESCRIPTION /by yyyy-MM-dd\n"
                        + " event DESCRIPTION /from START /to END\n"
                        + " list\n"
                        + " mark NUMBER\n"
                        + " unmark NUMBER\n"
                        + " delete NUMBER\n"
                        + " find KEYWORD\n"
                        + " help\n"
                        + " bye",
                rene.getResponse("help"));
    }

    @Test
    public void getResponse_bye_returnsFarewell() {
        Rene rene = new Rene(temporaryDirectory.resolve("rene.txt"));

        assertEquals("Bye. Hope to see you again soon!", rene.getResponse("bye"));
    }
}
