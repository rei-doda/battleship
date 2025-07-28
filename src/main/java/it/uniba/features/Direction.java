package it.uniba.features;

// <<entity>>

/**Questa classe definisce gli enumerativi che vengono utilizzati per definire
 *  la direzione di inserimento della nave nella mappa. */

public enum Direction {
    HORIZONTAL('H'),
    VERTICAL('V');

    private final char character;

    Direction(final char car) {
        this.character = car;
    }

    /**Il metodo restituisce il carattere che rappresenta la direzione.
     * @return Carattere che rappresenta la direzione.
     */

    public char getCharacter() {
        return character;
    }
}
