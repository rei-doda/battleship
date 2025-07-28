package it.uniba.elements;

import it.uniba.features.MapChar;
import java.io.Serializable;

// <<entity>>

/** Questa classe definisce la cella della mappa di gioco.
 */

public class Slot implements Serializable {
    private static final int MAGIC_NUMBER_1 = 5;
    private static final int MAGIC_NUMBER_2 = 23;
    private boolean isShip; // è una nave
    private boolean isHit; // è colpita
    private boolean isHidden; // è nascosta
    private boolean isSunk; // è affondata.
    private final short x; // ascissa della cella.
    private final short y; // ordinata della cella.

    /**
     * Crea un'istanza della classe.
     * @param xcoord ascissa
     * @param ycoord ordinata
     */
    public Slot(final short xcoord, final short ycoord) {
        this.isHidden = true;
        this.isHit = false;
        this.isSunk = false;
        this.isShip = false;
        this.x = xcoord;
        this.y = ycoord;
    }
    /** Restituisce l'ascissa.
     * @return ascissa.
     */
    public short getX() {
        return this.x;
    }

    /**
     * Restituisce l'ordinata.
     * @return ordinata.
     */
    public short getY() {
        return this.y;
    }

    /**
     * Segna che nella cella c'è una parte della nave.
     */
    public void setShip() {
        this.isShip = true;
    }

    /**
     * Segna che la cella è stata colpita.
     */
    public void setHit() {
        this.isHit = true;
    }

    /**
     * Segna che la parte della nave è stata colpita e affondata.
     */
    public void setSunk() {
        this.isSunk = true;
    }

    /** Segna se la cella deve essere visibile o meno.
     */
    public void setVisibility() {
        if (this.isHidden) {
            this.isHidden = false;
        } else {
            this.isHidden = true;
        }
    }

    /** Restituisce se la cella contiene una parte della nave o meno.
     * @return se cella contiene nave.
     */
    public boolean isShip() {
        return isShip;
    }
    /** Restituisce se la cella è stata colpita.
     * @return se cella è stata colpita.
     */
    public boolean isHit() {
        return isHit;
    }

    /**
     * Restituisce se la cella è stata affondata.
     * @return se nave nella cella è stata affondata.
     */
    public boolean isSunk() {
        return isSunk;
    }
    /**
     * Restituisce se la cella con all'interno una parte della nave è nascosta.
     * @return se nave nella cella è nascosta.
     */
    public boolean isHidden() {
        return isHidden;
    }
    /** Questo metodo serve per confrontare due oggetti Slot.
     * @return vero se due oggetti Slot sono uguali, falso altrimenti.
     */
    @Override
    public boolean equals(final Object o) {
        if (o instanceof Slot slot) {
            return (slot.getX() == this.getX() && slot.getY() == this.getY());
        } else {
            return false;
        }
    }
    /** Questo metodo serve per calcolare l'hashcode di un oggetto Slot.
     * E' utilizzato da equals per confrontare gli oggetti.
     * @return hashcode dell'oggetto Slot.
     */
    @Override
    public int hashCode() {
        int hash = Slot.MAGIC_NUMBER_1;
        hash = Slot.MAGIC_NUMBER_2 * hash + this.x;
        hash = Slot.MAGIC_NUMBER_2 * hash + this.y;
        return hash;
    }

    /** Questo metodo restituisce il carattere che in base
     * agli attributi booleani, restituisce il carattere associato alla cella.
     *
     * @return carattere associato alla cella.
     */
    public char getChar() {
        if (this.isShip() && isHit() && isSunk()) {
            return MapChar.KILL_TOKEN.getSymbol();
        } else if (this.isShip() && isHit()) {
            return MapChar.HIT_TOKEN.getSymbol();
        } else if (this.isShip() && !this.isHidden()) {
            return MapChar.SHIP_TOKEN.getSymbol();
        } else if (this.isHit()) {
            return MapChar.MISS_TOKEN.getSymbol();
        }
        return MapChar.SEA_TOKEN.getSymbol();
    }
}
