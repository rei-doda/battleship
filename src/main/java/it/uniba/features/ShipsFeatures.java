package it.uniba.features;

// <<entity>>

/** Questa classe descrive le caratteristiche che devono avere le tipologie
 * (Nome, Numero di slot occupati, Numero di istanze presente in game) tramite
 * enumerativi.
 */

public enum ShipsFeatures {
    DESTROYER("Cacciator_Pediniere", (short) 2, (short) 4),
    CRUISER("Incrociatore", (short) 3, (short) 3),
    BATTLESHIP("Corazzata", (short) 4, (short) 2),
    AIRCARRIER("Porta_Aerei", (short) 5, (short) 1);

    private final short numslots;
    private final short instances;
    private final String name;

    ShipsFeatures(final String alias, final short slots, final short cases) {
        if (slots <= 0) {
            throw new IllegalArgumentException("slots deve essere maggiore di 0.");
        }
        if (cases <= 0) {
            throw new IllegalArgumentException("instances deve essere maggiore di 0.");
        }
        this.numslots = slots;
        this.instances = cases;
        this.name = alias;
    }

    /** Il metodo restituisce il numero di slot che la nave deve occupare sulla mappa di gioco.
     * @return il numero di slot.
     */
    public short getNumSlots() {
        return this.numslots;
    }

    /** Il metodo restituisce il numero navi, di una determinata tipologia, che deve essere sulla mappa di gioco.
     * @return il numero di navi.
     */
    public short getInstances() {
        return this.instances;
    }

    /** Il metodo restituisce il nome della nave.
     * @return il nome della nave.
     */
    public String getName() {
        return this.name;
    }
}
