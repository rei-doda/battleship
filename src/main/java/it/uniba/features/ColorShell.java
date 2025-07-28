package it.uniba.features;

// <<entity>>

/** Questa classe definisce le sequenze di escape di caratteri Unicode (sottoforma di enumerativi) per
 * cambiare colore del testo o dello sfondo della console del terminale.
*/

public enum ColorShell {
    BLACK("\033[0;30m"),   // BLACK
    RED("\033[0;31m"),     // RED
    GREEN("\033[0;32m"),   // GREEN
    YELLOW("\033[0;33m"),  // YELLOW
    BLUE("\033[0;34m"),    // BLUE
    PURPLE("\033[0;35m"),  // PURPLE
    CYAN("\033[0;36m"),    // CYAN
    WHITE("\033[0;37m"),   // WHITE
    ROSE("\033[1;35m"),    // ROSE
    ORANGE_1("\033[38;2;255;165;0m"), // ORANGE LIGHT
    ORANGE_2("\033[38;2;255;140;0m"), // ORANGE MID LIGHT
    ORANGE_3("\033[38;2;255;127;80m"), // ORANGE LOW LIGHT
    ORANGE_4("\033[38;2;255;69;0m"), // ORANGE LOW
    END_ESCAPE("\u001B[0m"); // ENDING SEQUENCE

    private final String color;

    ColorShell(final String col) {
        this.color = col;
    }

    /** Il metodo restituisce la stringa di escape di caratteri Unicode utilizzato dall'enumerativo corrispondente.
     * @return Il la stringa che indica il colore dell'enumerativo corrispondente.
     */
    public String getValue() {
        return this.color;
    }
}
