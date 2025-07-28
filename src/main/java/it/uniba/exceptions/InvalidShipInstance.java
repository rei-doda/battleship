package it.uniba.exceptions;

/** Questa eccezione serve nel caso in durante l'istanziazione delle navi si
 *  andasse a costruire un oggetto della classe nave con un numero di slot
 *  diverso da quello dichiarato dal tipo in ShipsFeatures.
 */

public class InvalidShipInstance extends Exception {
    /** Costruttore dell'eccezione InvalidShipInstance.
     * @param message messaggio di errore.
     */
    public InvalidShipInstance(final String message) {
        super(message);
    }
}
