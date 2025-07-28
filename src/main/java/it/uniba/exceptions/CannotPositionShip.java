package it.uniba.exceptions;

/**
 * Eccezione lanciata nel caso in cui non sia possibile posizionare una nave.
 */
public class CannotPositionShip extends Exception {
    /** Costruttore dell'eccezione CannotPositionShip.
     * @param msg messaggio di errore.
     */
    public CannotPositionShip(final String msg) {
        super(msg);
    }
}
