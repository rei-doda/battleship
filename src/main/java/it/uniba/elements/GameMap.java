package it.uniba.elements;

import it.uniba.exceptions.CannotPositionShip;
import it.uniba.exceptions.InvalidShipInstance;
import it.uniba.features.Difficulty;
import it.uniba.features.Direction;
import it.uniba.features.ShipsFeatures;
import it.uniba.features.ShotResult;
import it.uniba.interfaces.Field;
import it.uniba.features.GridSize;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// <<control>> Questa classe implementa field.

/**
 * Classe che descrive la mappa di gioco e nello specifico implementa interfaces/Field.java.
 */
public class GameMap extends Field {
    private final Map<ShipsFeatures, List<Ship>> enemies;
    private final Set<Slot> forbidden;
    private final List<Slot> allowed;
    private int sunk = 0;
    private short shots = 0;
    private final short difficulty;
    private short failedShots = 0;

    /** Costruttore della classe GameMap.
     * @param size Enumerativo GridSize per settare la dimensione della griglia di Gioco.
     * @param hardnes Enumerativo Difficulty per settare la difficoltà di gioco.
     */
    public GameMap(final GridSize size, final Difficulty hardnes) {
        super(size);
        this.forbidden = new HashSet<>();
        this.enemies = new LinkedHashMap<>();
        this.resetEnemies();
        this.allowed = new ArrayList<>();
        this.resetAllowed();
        this.difficulty = hardnes.getAttempts();
    }

    /** Implementazione del metodo astratto placeShip() della classe Field.java.
     * Serve ad verificare e nel caso piazzare una nave nella mappa di gioco (ocean).
     * @param startX coordinata x di partenza.
     * @param startY coordinata y di partenza.
     * @param ship  tipo di nave da piazzare.
     * @throws CannotPositionShip eccezione lanciata nel caso in cui la nave non possa essere piazzata.
     * @throws InvalidShipInstance eccezione lanciata nel caso in cui il numero di slot passati non sia
     * uguale al numero di istanze possibili per tipo di nave.
     */
    @Override
    public void placeShip(final short startX, final short startY, final ShipsFeatures ship)
            throws CannotPositionShip, InvalidShipInstance {
        Direction verse = super.getRandomDirection();
        Set<Slot> tempSlot = new HashSet<>();
        int length = ship.getNumSlots();
        if (verse == Direction.HORIZONTAL) {
            if (startY + length > this.getOcean().length) {
                throw new CannotPositionShip("La nave generata va oltre i limiti della mappa.");
            }
            for (short i = startY; i < startY + length; i++) {
                if (this.forbidden.contains(this.getSlotOcean(startX, i))) {
                    throw new CannotPositionShip("La nave generata si sovrappone ad una nave già esistente.");
                }
            }
            for (short i = startY; i < startY + length; i++) {
                this.setSlotShip(startX, i);
                tempSlot.add(this.getSlotOcean(startX, i));
                this.forbidden.add(this.getSlotOcean(startX, i));
                this.allowed.remove(this.getSlotOcean(startX, i));
                HashSet<Slot> surrounding;
                surrounding = GameMap.getSurroundingCells(this.getOcean(), startX, i);
                this.forbidden.addAll(surrounding);
                this.allowed.removeAll(surrounding);
            }
            this.enemies.get(ship).add(new Ship(tempSlot, ship, verse));
        } else {
            if (startX + length > this.getOcean().length) {
                throw new CannotPositionShip("La nave generata va oltre i limiti della mappa.");
            }
            for (short i = startX; i < startX + length; i++) {
                if (this.forbidden.contains(this.getSlotOcean(i, startY))) {
                    throw new CannotPositionShip("La nave generata si sovrappone ad una nave già esistente.");
                }
            }
            for (short i = startX; i < startX + length; i++) {
                this.setSlotShip(i, startY);
                tempSlot.add(this.getSlotOcean(i, startY));
                this.forbidden.add(this.getSlotOcean(i, startY));
                HashSet<Slot> surrounding;
                surrounding = GameMap.getSurroundingCells(this.getOcean(), i, startY);
                this.forbidden.addAll(surrounding);
                this.allowed.removeAll(surrounding);
            }
            this.enemies.get(ship).add(new Ship(tempSlot, ship, verse));
        }
    }
    /** Implementazione del metodo astratto placeAlLShips() della classe Field.java.
     * Serve a piazzare tutte le navi nella mappa di gioco (ocean).
     * @return boolean
     */
    @Override
    public boolean placeAllShips() {
        short mountedShip = 0;
        short totalInstances = (short) Arrays.stream(ShipsFeatures.values())
                                    .mapToInt(ShipsFeatures::getInstances)
                                    .sum();
        Set<ShipsFeatures> tempSlot = Arrays.stream(ShipsFeatures.values())
                .sorted(Comparator.comparing(ShipsFeatures::getNumSlots).reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));
        System.out.println();
        for (ShipsFeatures ship: tempSlot) {
            System.out.println("Posizionando Navi '" + ship.getName() + "' ... ");
            for (short j = 0; j < ship.getInstances(); j++) {
                    while (!this.allowed.isEmpty() && mountedShip < totalInstances) {
                        short numchoose = (short) RANDOMIZER.nextInt(this.allowed.size());
                        Slot choose = this.allowed.get(numchoose);
                        try {
                            placeShip(choose.getX(), choose.getY(), ship);
                            mountedShip += 1;
                            break;
                        } catch (CannotPositionShip e) {
                            // Riprova con altre coordinate
                            this.allowed.remove(choose);
                        } catch (InvalidShipInstance ex) {
                        }
                    }
                }
        }
        System.out.println();
        return mountedShip == totalInstances;
    }
    /** Implementazione del metodo astratto init() della classe Field.java.
     * Serve a inizializzare la mappa di gioco (ocean). Fino a quando non
     * riesce a piazzare tutte le navi, riprova.
     */
    @Override
    public void init() {
        for (short i = 0; i < this.getOcean().length; i++) {
            for (short j = 0; j < this.getOcean().length; j++) {
                this.initSlot(i, j);
            }
        }
        while (!this.placeAllShips()) {
            for (short i = 0; i < this.getOcean().length; i++) {
                for (short j = 0; j < this.getOcean().length; j++) {
                    this.initSlot(i, j);
                }
            }
            System.out.println("Aborting.. generation failed.. resetting..\n");
            this.resetAllowed();
            this.resetEnemies();
            this.forbidden.clear();
        }
        this.setSetted();
    }
    /** Implementazione del metodo astratto resetAllowed() della classe Field.java.
     * Serve a resettare la lista delle coordinate disponibili.
     */
    public final void resetAllowed() {
        this.allowed.clear();
        IntStream.range(0, this.getOcean().length).forEach(x ->
                IntStream.range(0, this.getOcean().length).forEach(y ->
                    this.allowed.add(new Slot((short) x, (short) y))
                )
            );
    }
    /** Implementazione del metodo astratto resetEnemies() della classe Field.java.
     * Serve a resettare la lista delle navi generate.
     */
    public final void resetEnemies() {
        Arrays.stream(ShipsFeatures.values())
            .forEach(feature -> enemies.put(feature, new ArrayList<>()));
    }
    /** Implementazione del metodo astratto getShipsCoords() della classe Field.java.
     * Serve a stampare le coordinate di tutte le navi piazzate.
     * @param enemies Map<ShipsFeatures, List<Ship>>
     * @return String
     */
    public static String getShipsCoords(final Map<ShipsFeatures, List<Ship>> enemies) {
        StringBuilder enemy = new StringBuilder();
        enemy.append("\nEcco le coordinate delle navi generate: \n\n");
        for (Map.Entry<ShipsFeatures, List<Ship>> entry: enemies.entrySet()) {
            enemy.append(entry.getKey().getName()).append(":").append("\n");
            for (short i = 0; i < enemies.get(entry.getKey()).size(); i++) {
                enemy.append(i + 1)
                        .append(": ")
                        .append(enemies.get(entry.getKey()).get(i).getAllSlotCoords())
                        .append("\n");
            }
            enemy.append("\n");
        }
        return enemy.toString();
    }
    /**
     * Metodo che calcola il numero di navi posizionate.
     * @return
     */
    public static int getNumShips() {
        return Arrays.stream(ShipsFeatures.values())
                .mapToInt(ShipsFeatures::getInstances)
                .sum();
    }
    /**
     * Metodo che si occupa di gestire i tentativi di colpo.
     * @return ShotResult esito dello sparo effetuato sulla griglia di gioco
     */
    @Override
    public ShotResult shot(final Slot slot) {
        ShotResult result = ShotResult.MISS;
        Slot shotedSlot = this.getSlotOcean(slot.getX(), slot.getY());
        if (shotedSlot.isHit()) {
            return ShotResult.ALREADY_SHOT;
        } else {
            shots += 1;
            shotedSlot.setHit();
            Iterator<Map.Entry<ShipsFeatures, List<Ship>>> iterator = this.enemies.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<ShipsFeatures, List<Ship>> entry = iterator.next();
                for (Ship ship: entry.getValue()) {
                    if (ship.getShipslots().contains(shotedSlot)) {
                        result = ShotResult.HIT;
                        ship.hitShip(slot);
                        if (ship.isSunk()) {
                            sunk += 1;
                            result = ShotResult.HIT_SUNK;
                            System.out.println("\nNave '" + ship.getShipname() + "' affondata!");
                            System.out.println("Navi affondate: " + sunk + "/" + getNumShips());
                        }
                        if (this.enemies.get(entry.getKey()).stream().allMatch(Ship::isSunk)) {
                            System.out.println("Hai affondato tutte le navi '" + entry.getKey().getName() + "'!");
                        }
                        return result;
                    }
                }
            }
        }
        failedShots += 1;
        return result;
    }

    /** Getter delle navi piazzate per tipologia.
     * @return Map<ShipsFeatures, List<Ship>>
     */
    @Override
    public Map<ShipsFeatures, List<Ship>> getEnemies() {
        return new LinkedHashMap<>(this.enemies);
    }
    /** Getter delle coordinate degli slot non utilizzabili.
     * @return String
     */
    public HashSet<Slot> getForbidden() {
        return new HashSet<>(this.forbidden);
    }
    /** Getter delle coordinate degli slot utilizzabili.
     * @return List<Slot>
     */
    public List<Slot> getAllowed() {
        return new ArrayList<>(this.allowed);
    }
    /** Getter del numero di colpi sparati dal giocatore.
     * @return short
     */
    @Override
    public short getShots() {
        return this.shots;
    }
    /**
     * Metodo per verificare se il giocatore ha vinto.
     * @return boolean true se il giocatore ha vinto, false altrimenti.
     */
    @Override
    public boolean isWin() {
        return this.sunk == getNumShips();
    }
    /**
     * Metodo per verificare se il giocatore ha perso.
     * @return boolean true se il giocatore ha perso, false altrimenti.
     */
    @Override
    public boolean isLose() {
        return this.failedShots == difficulty;
    }

    /** Getter del numero di colpi falliti dal giocatore.
     * @return short
     */
    @Override
    public short getFailedShots() {
        return this.failedShots;
    }
}
