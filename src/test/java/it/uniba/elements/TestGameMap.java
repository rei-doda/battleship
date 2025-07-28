package it.uniba.elements;

import it.uniba.features.ShipsFeatures;
import it.uniba.features.ShotResult;
import it.uniba.exceptions.CannotPositionShip;
import it.uniba.exceptions.InvalidShipInstance;
import it.uniba.features.Difficulty;
import it.uniba.features.GridSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Test Suite per la classe GameMap.
 */

class TestGameMap {

    private static final int REPEAT_TEST = 20;
    private GameMap gameMap;

    @BeforeEach
    void setUp() {
        gameMap = new GameMap(GridSize.STANDARD, Difficulty.EASY);
    }


    @Test
    @RepeatedTest(REPEAT_TEST)
    @DisplayName("testShipsIstancesOnTheMap(): "
    + "Test che verifica che tutte le istanze di nave siano presenti nella mappa")
    void testShipsIstancesOnTheMap() {

        //Classe di equivalenza VALIDA per un valore specifico di istanze nave
        int totalInstance = 0;
        final int expectedIstance = 10;
        gameMap.init();
        Map<ShipsFeatures, List<Ship>> enemies = gameMap.getEnemies();
        for (ShipsFeatures ship : ShipsFeatures.values()) {
            totalInstance += enemies.get(ship).size();
        }
        assertEquals(totalInstance, expectedIstance, "Non sono presenti tutte le istanze di nave");
    }

    @Test
    @RepeatedTest(REPEAT_TEST)
    @DisplayName("testShipsIstancesOnTheMapNotLessThan10():"
    + "Test che verifica che il numero di istanze di nave non siano al di sotto di 10")
    void testShipsIstancesOnTheMapNotLessThan10() {

        //Classe di equivalenza NON VALIDA per un valore specifico al di sotto di istanze nave previste
        final int underIstances = 9;
        int totalInstance = 0;
        gameMap.init();
        Map<ShipsFeatures, List<Ship>> enemies = gameMap.getEnemies();
        for (ShipsFeatures ship : ShipsFeatures.values()) {
            totalInstance -= enemies.get(ship).size();
        }
        assertNotEquals(totalInstance, underIstances, "Sono presenti meno istanza di nave di quelle previste");
    }

    @Test
    @RepeatedTest(REPEAT_TEST)
    @DisplayName("testShipsIstancesOnTheMapNotMoreThan10():"
    + "Test che verifica che il numero di istanze di nave non siano al di sopra di 10")
    void testShipsIstancesOnTheMapNotMoreThan10() {

        //Classe di equivalenza NON VALIDA per un valore specifico al di sopra di istanze nave previste
        final int overIstances = 11;
        int totalInstance = 0;
        gameMap.init();
        Map<ShipsFeatures, List<Ship>> enemies = gameMap.getEnemies();
        for (ShipsFeatures ship : ShipsFeatures.values()) {
            totalInstance += enemies.get(ship).size();
        }
        assertNotEquals(totalInstance, overIstances, "Sono presenti più istanza di nave di quelle previste");
    }



    @Test
    @DisplayName("testCannotPlaceShipBecauseItsAlreadyAShip():"
    + "Test che verifica che non sia possibile posizionare una nave in una cella già occupata")
    void testCannotPlaceShipBecauseItsAlreadyAShip() {

        gameMap.init();
        Map<ShipsFeatures, List<Ship>> enemies = gameMap.getEnemies();
        Ship ship = enemies.get(ShipsFeatures.BATTLESHIP).get(0);
        Slot shipSlot = ship.getShipslots().iterator().next();

        assertThrows(CannotPositionShip.class, () -> {
            gameMap.placeShip(shipSlot.getX(), shipSlot.getY(), ShipsFeatures.BATTLESHIP);
        }, "pace");
    }



    @Test
    @DisplayName("testCannotPlaceShipBecauseItsOutsideMap():"
    + "Test che verifica che non sia possibile posizionare una nave fuori dalla mappa")
    void testCannotPlaceShipBecauseItsOutsideMap() {

        final short outSideX = 11;
        final short outSideY = 11;
        Slot outMap = new Slot(outSideX, outSideY);

        assertThrows(CannotPositionShip.class, () -> {
            gameMap.placeShip(outMap.getX(), outMap.getY(), ShipsFeatures.BATTLESHIP);
        }, "pace");
    }


    @Test
    @DisplayName("testResetAllowed():"
    + "Test verifica che la lista degli slot utilizzabili sia stata resettata correttamente")
    void testResetAllowed() {

        gameMap.resetAllowed();

        List<Slot> allowed = gameMap.getAllowed();
        assertEquals(GridSize.STANDARD.getSize() * GridSize.STANDARD.getSize(), allowed.size(),
        "Lista slot utilizzabili non resettata correttamente");

    }



    @Test
    @DisplayName("testResetEnemies():"
    + "Test verifica che la lista delle navi sia stata resettata correttamente")
    void testResetEnemies() {

        gameMap.resetEnemies();
        assertEquals(0, gameMap.getEnemies().get(ShipsFeatures.BATTLESHIP).size(),
        "Lista navi non resettata correttamente");
    }

    @Test
    @DisplayName("testPlaceShipValidPlacementShouldNotThrownException():"
    + "verifica che una nave sia stata posizionata senza lanciare eccezioni")
    void testPlaceShipValidPlacementShouldNotThrowException() {
        GameMap game = new GameMap(GridSize.STANDARD, Difficulty.EASY);
        ShipsFeatures ship = ShipsFeatures.BATTLESHIP;
        for (short i = 0; i < game.getOcean().length; i++) {
            for (short j = 0; j < game.getOcean().length; j++) {
                game.initSlot(i, j);
            }
        }
        short startX = 1;
        short startY = 1;

        assertDoesNotThrow(() -> {
            game.placeShip(startX, startY, ship);
        });
    }

    @Test
    @DisplayName("testPlaceAllShipsValidPlacementShouldReturnTrue(): "
    + "verifica che tutte le navi siano state posizionate correttamente")
    void testPlaceAllShipsValidPlacementShouldReturnTrue() {
        GameMap game = new GameMap(GridSize.LARGE, Difficulty.EASY);
        for (short i = 0; i < game.getOcean().length; i++) {
            for (short j = 0; j < game.getOcean().length; j++) {
                game.initSlot(i, j);
            }
        }
        assertTrue(game.placeAllShips(), "Non tutte le navi sono state posizionate");
    }

    @Test
    @DisplayName("testShotValidShotOnEmptySlotShouldReturnMiss(): "
    + "verifica che il risultato dello sparo sia MISS")
    void testShotValidShotOnEmptySlotShouldReturnMiss() {
        GameMap game = new GameMap(GridSize.STANDARD, Difficulty.EASY);
        short x = 1;
        short y = 1;
        game.initSlot(x, y);
        Slot slot = new Slot(x, y);

        ShotResult result = game.shot(slot);

        assertEquals(ShotResult.MISS, result, "Il risultato dello sparo non è MISS");
    }

    @Test
    @DisplayName("testShotValidShotOnShipShouldReturnHit(): "
    + "verifica che il risultato dello sparo sia HIT")
    void testShotValidShotOnShipShouldReturnHit() throws CannotPositionShip, InvalidShipInstance {
        GameMap game = new GameMap(GridSize.STANDARD, Difficulty.EASY);
        ShipsFeatures ship = ShipsFeatures.CRUISER;
        final short startX = 4;
        final short startY = 4;
        for (short i = 0; i < game.getOcean().length; i++) {
            for (short j = 0; j < game.getOcean().length; j++) {
                game.initSlot(i, j);
            }
        }
        game.placeShip(startX, startY, ship);
        Slot slot = new Slot(startX, startY);

        ShotResult result = game.shot(slot);

        assertEquals(ShotResult.HIT, result, "Il risultato dello sparo non è HIT");
    }

    @Test
    @DisplayName("testShotValidShotOnAlreadyShotSlotShouldReturnAlreadyShot: "
    + "verifica che il risultato dello sparo sia ALREADY_SHOT")
    void testShotValidShotOnAlreadyShotSlotShoulReturnAlreadyShot() {
        GameMap game = new GameMap(GridSize.STANDARD, Difficulty.EASY);
        game.init();
        final short x = 1;
        final short y = 1;
        Slot slot = new Slot(x, y);

        // First shot
        game.shot(slot);

        // Second shot on the same slot should throw AlreadyShotException
        ShotResult result = game.shot(slot);
        assertEquals(ShotResult.ALREADY_SHOT, result, "Il risultato dello sparo non è ALREADY_SHOT");
    }
}
