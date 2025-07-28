package it.uniba.features;

// <<entity>>

/** Questa classe definisce gli enumerativi che vengono utilizzati per
 *  definire il numero di tentativi disponibili a seconda del tipo di difficoltà.
*/
public enum Difficulty {
    EASY("Facile", (short) 50),
    MEDIUM("Medio", (short) 30),
    HARD("Difficile", (short) 10);

    private short attempts;
    private String name;

    Difficulty(final String alias, final short shots) throws IllegalArgumentException {
        if (shots <= 0) {
            throw new IllegalArgumentException("attempts deve essere maggiore di 0.");
        }
        this.name = alias;
        this.attempts = shots;
    }

    /** Il metodo restituisce il numero di tentativi per il tipo di difficoltà.
     * @return Intero.
     */
    public short getAttempts() {
        return this.attempts;
    }

    public String getName() {
        return this.name;
    }
    /** Il metodo imposta il numero di tentativi per il tipo di difficoltà.
     * @param tentatives short indica i tentativi che si vogliono impostare
     */
    public void setAttempts(final short tentatives) throws IllegalArgumentException {
        if (attempts <= 0) {
            throw new IllegalArgumentException("tentatives deve essere maggiore di 0.");
        }
        this.attempts = tentatives;
    }
    /**
     * Il metodo imposta il nome della difficoltà.
     * @param alias
     */
    public void setName(final String alias) throws IllegalArgumentException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Il nome della difficoltà non può essere vuoto");
        }
        this.name = alias;
    }
}
