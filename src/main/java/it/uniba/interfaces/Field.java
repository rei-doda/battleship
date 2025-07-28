package it.uniba.interfaces;

import it.uniba.elements.Ship;
import it.uniba.elements.Slot;
import it.uniba.exceptions.CannotPositionShip;
import it.uniba.exceptions.InvalidShipInstance;
import it.uniba.features.Direction;
import it.uniba.features.ShipsFeatures;
import it.uniba.features.ShotResult;
import it.uniba.features.GridSize;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

// <<control>> Questa classe astratta descrive le caratteristiche e i comportamenti che la mappa di gioco deve avere.
// Inoltre dichiara un metodi astratti per posizionimento delle navi sulla mappa di gioco,
// dichiara inoltre metodi per gestire lo sparo.

/** Classe astratta che descrive le caratteristiche e i comportamenti che la mappa di gioco deve avere. */
public abstract class Field implements Serializable {
    private static final short[][] OFFSETS_SURROUND = {{-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
    public static final Random RANDOMIZER = new Random();
    private final Slot[][] ocean;
    private final GridSize boardSize;
    private boolean setted;

    /** Costruttore della classe astratta Field.
     * @param size
     */
    public Field(final GridSize size) {
        this.boardSize = size;
        this.ocean = new Slot[this.boardSize.getSize()][this.boardSize.getSize()];
        this.setted = false;
    }

    /** Metodo che implementa il posizionamento di una nave sulla mappa di gioco(ocean).
     * @param startX Coordinata x di partenza.
     * @param startY Coordinata y di partenza.
     * @param ship Nave da posizionare.
     * @throws it.uniba.exceptions.CannotPositionShip
     * @throws it.uniba.exceptions.InvalidShipInstance
     */
    public abstract void placeShip(short startX, short startY, ShipsFeatures ship)
            throws CannotPositionShip, InvalidShipInstance;

    /** Metodo che implementa il posizionamento di tutte le navi sulla mappa di gioco (ocean).
     * @return true le navi sono state posizionate, false altrimenti.
     */
    public abstract boolean placeAllShips();
    /** Metodo che implementa l'inizializzazione la mappa posizionando le navi.
     */
    public abstract void init();
    /** Metodo che restituisce i nemici (e quindi le navi) posizionate sulla mappa di gioco (ocean).
     * @return
     */
    public abstract Map<ShipsFeatures, List<Ship>> getEnemies();
    /** Metodo che implementa la ricezione di un colpo in un determinato slot.
     * @param slot Slot in cui ricevere il colpo.
     * @return
     */
    public abstract ShotResult shot(Slot slot);

    /** Metodo che restituisce una direzione randomica a partire dall'enumerativ delle
     * direzioni possibili.
     * @return Direction direzione randomica.
     */
    public Direction getRandomDirection() {
        return Direction.values()[RANDOMIZER.nextInt(Direction.values().length)];
    }
    /**
     * Metodo che restituiscen il numero di tentativi falliti dal giocatore.
     * @return
     */
    public abstract short getFailedShots();
    /** Medoto che calcola e restituisce gli oggetti di classe Slot adiacenti ad un dato punto di coordinate
     * (x,y) sulla mappa di gioco (ocean).
     * @param ocean Mappa di gioco.
     * @param x Coordinata x del punto di riferimento.
     * @param y Coordinata y del punto di riferimento.
     * @return HashSet<Slot> insieme di oggetti di classe Slot adiacenti al punto di riferimento
      */
    public static HashSet<Slot> getSurroundingCells(final Slot[][] ocean, final short x, final short y) {
        HashSet<Slot> cells = new HashSet<>();
        for (short[] offset : Field.OFFSETS_SURROUND) {
            short newX = (short) (x + offset[0]);
            short newY = (short) (y + offset[1]);
            if (newX >= 0 && newX < ocean.length && newY >= 0 && newY < ocean[0].length) {
                cells.add(ocean[newX][newY]);
            }
        }
        return cells;
    }

    /** Metodo che imposta la visibilità di tutti gli slot della mappa a vero.
     */
    public void showGrid() {
        for (Slot[] coords : this.ocean) {
            for (Slot coord : coords) {
                coord.setVisibility();
            }
        }
    }
    /** Getter dell'attributo setted che indica se la mappa è stata
     * correttamente inizializzata.
     * @return setted booleano che indica se la mappa è stata correttamente inizializzata.
     */
    public final boolean isSetted() {
        return this.setted;
    }
    /** Setter dell'attributo setted che indica se la mappa è stata
     * correttamente inizializzata. Imposta setted a vero.
     */
    public final void setSetted() {
        this.setted = true;
    }
    /** Getter dell'attributo ocean che rappresenta la mappa di gioco.
     * @return Slot[][] mappa di gioco.
     */
    public final Slot[][] getOcean() {
        return this.ocean.clone();
    }

    /**
     * Getter di uno slot della mappa di gioco (ocean).
     * @param x Coordinata x dello slot.
     * @param y Coordinata y dello slot.
     * @return Slot slot della mappa di gioco.
     */
    public final Slot getSlotOcean(final short x, final short y) {
        return this.ocean[x][y];
    }

    /**
     * Getter del numero di colpi sparati.
     * @return short numero di colpi sparati.
     */
    public abstract short getShots();

    /**
     * Metodo per verificare se il giocatore ha vinto.
     * @return boolean true se il giocatore ha vinto, false altrimenti.
     */
    public abstract boolean isWin();

    /**
     * Metodo per verificare se il giocatore ha perso.
     * @return boolean true se il giocatore ha perso, false altrimenti.
     */
    public abstract boolean isLose();

    /** Setter di uno slot della mappa di gioco (ocean).
     * Imposta uno slot come nave.
     * @param x Coordinata x dello slot.
     * @param y Coordinata y dello slot.
     */
    public void setSlotShip(final short x, final short y) {
        this.ocean[x][y].setShip();
    }

    /** Inizializza uno slot istanziando un'oggetto di classe Slot
     * in un punto di coordinate (x,y) sulla mappa di gioco (ocean) specifico.
     * @param x Coordinata x dello slot.
     * @param y Coordinata y dello slot.
     */
    public void initSlot(final short x, final short y) {
        this.ocean[x][y] = new Slot(x, y);
    }
}
