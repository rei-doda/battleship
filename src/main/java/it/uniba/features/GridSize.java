package it.uniba.features;

// <<entity>>

/** Questa classe definisce gli enumerativi che vengono utilizzati per
 *  definire la dimensione della griglia.
 */
public enum GridSize {

    STANDARD((short) 10),
    LARGE((short) 18),
    EXTRALARGE((short) 26);

    private final short size;


    GridSize(final short sizeGrid) throws IllegalArgumentException {
        if (sizeGrid <= 0) {
            throw new IllegalArgumentException("size deve essere maggiore di 0");
        }
        this.size = sizeGrid;
    }

    /** Il metodo restituisce la dimensione della griglia.
     * @return size.
     */
    public short getSize() {
        return this.size;
    }


}
