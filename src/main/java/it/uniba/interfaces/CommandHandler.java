package it.uniba.interfaces;

import it.uniba.features.CommandType;
import java.io.IOException;
import java.util.Scanner;
/**
 * Interfaccia per il gestore di un comando.
*/
public interface CommandHandler {
        /**
        * Funzione richiamata per gestire il comando.
        * @param input
        * @param value
        * @param command
        * @throws java.io.IOException
        */
        void handle(Scanner input, Scanner value, CommandType command) throws IOException;
}
