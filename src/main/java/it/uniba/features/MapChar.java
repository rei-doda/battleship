package it.uniba.features;

// <<entity>>

/**Questa classe definisce degli enumerativi, nello specifico i caratteri che
 * dovranno essere visualizzati sulla mappa e cosa rappresentano.
 */

public enum MapChar {
    SEA_TOKEN('~'), // Carattere che rappresenta il mare
    HIT_TOKEN('⊠'), // Carattere che rappresenta una parte della nave colpita
    SHIP_TOKEN('□'), // Carattere che rappresenta una parte della nave
    MISS_TOKEN('¤'), // Carattere che rappresenta il colpo andato a vuoto
    KILL_TOKEN('#'), // Carattere che rappresenta la nave affondata
    EDGE_TOKEN_X('-'), // Carattere che rappresenta il delimitatore della mappa sull'asse x
    EDGE_TOKEN_Y('|'), // Carattere che rappresenta il delimitatore della mappa sull'asse y
    ANGULAR_TOKEN('+'), // Carattere che rappresenta il carattere stampato agli angoli del delimitatote
    PADDING_TOKEN(' '); // Carattere utilizzato per dividere la mappa dai caratteri delimitatore

    private final char symbol; // Carattere dell'enumerativo.

    MapChar(final char sign) {
        this.symbol = sign;
    }

    /** Il metodo restituisce il carattere utilizzato dall'enumerativo corrispondente.
     * @return Il carattere dell'enumerativo.
     */
    public char getSymbol() {
        return this.symbol;
    }
}
