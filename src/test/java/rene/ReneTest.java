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
        assertEquals(" Here is your current action-item backlog:\n 1.[T][ ] read book", listResponse);
    }

    @Test
    public void getResponse_invalidCommand_returnsFriendlyError() {
        Rene rene = new Rene(temporaryDirectory.resolve("rene.txt"));

        assertEquals(
                " Apologies — That falls outside my scope of operations. Standard procedures are: todo, "
                        + "deadline, event, list, mark, unmark, delete, find, help, or bye.",
                rene.getResponse("unknown"));
    }

    @Test
    public void getResponse_help_returnsEveryCommandSyntax() {
        Rene rene = new Rene(temporaryDirectory.resolve("rene.txt"));

        assertEquals(
                " Per your request, here is the standard operating procedure for working with Rene:\n"
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

        assertEquals("Thank you for your time. Standing by — let's touch base again soon.", rene.getResponse("bye"));
    }
}
