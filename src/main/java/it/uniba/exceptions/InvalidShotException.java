package it.uniba.exceptions;
/**
 * Questa classe definisce l'eccezione lanciata nel caso in cui il colpo inserito non sia valido.
 */
public class InvalidShotException extends Exception {
    /**
     * Costruttore della classe InvalidShotException.
     * @param message
     */
    public InvalidShotException(final String message) {
        super(message);
    }
}
