package it.uniba.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.uniba.features.CommandType;
import it.uniba.interfaces.CommandHandler;

/**
 * Main test class of the application.
 */
class AppTest {
    /**
     * Test the getGreeting method of the App class.
     */
    @Test
    void appHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull(
                classUnderTest.getGreeting(), "app should have a greeting");
    }
    @Test
    @DisplayName("testCheckCommandValid(): Test che verifica che il comando sia valido")
    void testCheckCommandValid() {
        Map<CommandType, CommandHandler> commands = new HashMap<>();
        commands.put(CommandType.HELP, App::handleHelp);
        commands.put(CommandType.START, App::handlePlay);

        String input = "/help";
        CommandType result = App.checkCommand(commands, input);

        assertEquals(CommandType.HELP, result, "Il comando non è valido");
    }
    @Test
    @DisplayName("testCheckCommandInvalid(): Test che verifica che il comando sia invalido")
    void testCheckCommandInvalid() {
        Map<CommandType, CommandHandler> commands = new HashMap<>();
        commands.put(CommandType.HELP, App::handleHelp);
        commands.put(CommandType.START, App::handlePlay);

        String input = "/invalid";
        CommandType result = App.checkCommand(commands, input);

        assertNull(result, "Il comando è valido");
    }
}
