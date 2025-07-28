package it.uniba.elements;

import it.uniba.exceptions.InvalidShipInstance;
import it.uniba.features.Direction;
import it.uniba.features.ShipsFeatures;
import java.io.Serializable;
import java.util.Set;
import java.util.Iterator;

import java.util.HashSet;

// <<entity>>

/**
 * Classe che descrive l'oggetto nave.
 */
public class Ship implements Serializable {
    private boolean sunk;
    private final Set<Slot> shipslots;
    private final String shipname;
    private final Direction direction;
    private short slotHitted;

    /**
     * Costruttore della classe Ship.
     * @param slots insieme di slot che compongono la nave.
     * @param type  tipo di nave.
     * @param verse orientamento nave.
     * @throws InvalidShipInstance eccezione lanciata nel caso in cui il numero di
     *                             slot
     *                             passati non sia uguale al numero di istanze
     *                             possibili per tipo di nave.
     */
    public Ship(final Set<Slot> slots, final ShipsFeatures type, final Direction verse)
            throws InvalidShipInstance {
        this.sunk = false;
        if (!(slots.size() == type.getNumSlots())) {
            throw new InvalidShipInstance(
                    "Errore: Numero di Slot passati e numero di istanze possibili per tipo di nave non valido.");
        }
        this.shipslots = new HashSet<>(slots);
        this.shipname = type.getName();
        this.direction = verse;
        this.slotHitted = type.getNumSlots();
    }

    /**
     * Metodo che verifica se la nave è affondata.
     * @return true se la nave è affondata, false altrimenti.
     */

    public boolean isSunk() {
        return this.sunk;
    }

    /**
     * Metodo che restituisce gli slot che compongono la nave.
     * @return insieme di slot che compongono la nave.
     */
    public Set<Slot> getShipslots() {
        return new HashSet<>(this.shipslots);
    }

    /**
     * Metodo che restituisce il nome della nave.
     * @return nome della nave.
     */
    public String getShipname() {
        return this.shipname;
    }

    /**
     * Metodo che imposta la nave come affondata.
     */
    public void setSunk() {
        for (Slot coord : this.shipslots) {
            coord.setSunk();
        }
        this.sunk = true;
    }
    /**
     * Metodo che restituisce l'orientamento della nave.
     * @return orientamento della nave.
     */
    public Direction getDirection() {
        return direction;
    }
    /** Metodo che restituisce la tipologia di nave e gli slot associati sotto forma
     * di stringa.
     * @return stringa contenente le coordinate di tutti gli slot che compongono la
     *         nave.
     */
    public String getAllSlotCoords() {
        StringBuilder sb = new StringBuilder();
        for (Slot coord : this.shipslots) {
            sb.append("{").append(coord.getX()).append(", ").append(coord.getY()).append("}").append("|  ");
        }
        return sb.toString();
    }
    /**
     * Metodo che imposta come colpito lo slot passato come parametro.
     * se tutti gli slot della nave sono colpiti, imposta la nave come affondata.
     * @param slot slot da colpire.
     */
    public void hitShip(final Slot slot) {
        Iterator<Slot> it = this.shipslots.iterator();
        while (it.hasNext()) {
            Slot s = it.next();
            if (s.equals(slot)) {
                s.setHit();
                this.slotHitted--;
                if (this.slotHitted == 0) {
                    this.setSunk();
                }
                return;
            }
        }
    }
}
