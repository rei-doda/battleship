package it.uniba.elements;

import it.uniba.exceptions.InvalidShipInstance;
import it.uniba.features.Direction;
import it.uniba.features.ShipsFeatures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test Suite per la classe Ship.
 */

class TestShip {

    private Set<Slot> slots;

    @BeforeEach
    public void setUp() {
        final short size = 3;
        slots = new HashSet<>();
        slots.add(new Slot((short) 1, (short) 1));
        slots.add(new Slot((short) 1, (short) 2));
        slots.add(new Slot((short) 1, size));
    }


    @Test
    @DisplayName("testShipInitialization(): Test inizializzazione di una nave")
    void testShipInitialization() {

        //Classe di equivalenza VALIDA per un valore specifiico di istanze nave
        try {
            Ship ship = new Ship(slots, ShipsFeatures.CRUISER, Direction.HORIZONTAL);
            assertEquals(slots, ship.getShipslots(), "Inizializzazione nave fallita");
        } catch (InvalidShipInstance e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    @DisplayName("testShipInitializationSmaller():"
    + "Test inizializzazione di una nave più piccola di quella istanziata")
    void testShipInitializationSmaller() {

        //Classe di equivalenza NON VALIDA per un valore specifiico (minore) di istanze nave
        try {
            Ship ship = new Ship(slots, ShipsFeatures.BATTLESHIP, Direction.HORIZONTAL);
            assertNotEquals(slots, ship.getShipslots(), "Inizializzazione nave fallita");
        } catch (InvalidShipInstance e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("testShipInitializationBigger():"
    + "Test inizializzazione di una nave più grande di quella istanziata")
    void testShipInitializationBigger() {

        //Classe di equivalenza NON VALIDA per un valore specifiico (maggiore) di istanze nave
        try {
            Ship ship = new Ship(slots, ShipsFeatures.AIRCARRIER, Direction.HORIZONTAL);
            assertNotEquals(slots, ship.getShipslots(), "Inizializzazione nave fallita");
        } catch (InvalidShipInstance e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    @DisplayName("testIsSunk(): Test che verifica se una nave è affondata")
    void testIsSunk() {
        try {
            Ship ship = new Ship(slots, ShipsFeatures.CRUISER, Direction.HORIZONTAL);
            assertEquals(false, ship.isSunk(), "La nave non è affondata");
        } catch (InvalidShipInstance e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    @DisplayName(" testIsNotSunk(): Test che verifica se una nave non è affondata")
    void testIsNotSunk() {
        try {
            Ship ship = new Ship(slots, ShipsFeatures.CRUISER, Direction.HORIZONTAL);
            assertNotEquals(true, ship.isSunk(), "La nave è affondata");
        } catch (InvalidShipInstance e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    @DisplayName("testGetAllSlotCoords(): Test che verifica se le coordinate restituite sono corrette")
    void testGetAllSlotCoords() {
        try {
            Ship ship = new Ship(slots, ShipsFeatures.CRUISER, Direction.HORIZONTAL);

            String expectedCoords = "{1, 1}|  {1, 2}|  {1, 3}|  ";
            String actualCoords = ship.getAllSlotCoords();
            assertEquals(expectedCoords, actualCoords, "Le coordinate non sono corrette");

        } catch (InvalidShipInstance e) {
            fail("Eccezione InvalidShipInstance inaspettata");
        }
    }


    @Test
    @DisplayName("testGetAllSlotCoordsWrong():"
    + "Test che verifica se le coordinate restituite non sono corrette")
    void testGetAllSlotCoordsWrong() {
        try {
            Ship ship = new Ship(slots, ShipsFeatures.CRUISER, Direction.HORIZONTAL);

            String expectedCoords = "{1, 1}|  {1, 2}|  {1, 4}|  ";
            String actualCoords = ship.getAllSlotCoords();
            assertNotEquals(expectedCoords, actualCoords, "Le coordinate non sono corrette");

        } catch (InvalidShipInstance e) {
            fail("Eccezione InvalidShipInstance inaspettata");
        }
    }

    @Test
    @DisplayName("testIsSunkBeforeHitShipShouldReturnFalse(): verifica che "
    + "isSunk() restituisca false prima di colpire la nave")
    void testIsSunkBeforeHitShipShouldReturnFalse() {
        // Creazione di una nave
        Set<Slot> coords = new HashSet<>();
        coords.add(new Slot((short) 0, (short) 0));
        coords.add(new Slot((short) 0, (short) 1));
        // Verifica che la nave non sia affondata
        try {
            Ship ship = new Ship(coords, ShipsFeatures.DESTROYER, Direction.HORIZONTAL);
            assertFalse(ship.isSunk(), "La nave è affondata");
        } catch (InvalidShipInstance e) {
            System.out.println(e.getMessage());
        }
    }
    @Test
    @DisplayName("testIsSunkAfterAllSlotsHitShouldReturnTrue(): verifica che isSunk() "
    + "restituisca true dopo aver colpito tutti gli slot della nave")
    void testIsSunkAfterAllSlotsHitShouldReturnTrue() {
        // Creazione di una nave
        Set<Slot> coords = new HashSet<>();
        coords.add(new Slot((short) 0, (short) 0));
        coords.add(new Slot((short) 0, (short) 1));
        try {
            Ship ship = new Ship(coords, ShipsFeatures.DESTROYER, Direction.HORIZONTAL);
            // Colpire tutti gli slot della nave
            for (Slot slot : coords) {
                ship.hitShip(slot);
            }
            // Verifica che la nave sia affondata
            assertTrue(ship.isSunk(), "La nave non è affondata");
        } catch (InvalidShipInstance e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("testHitShipValidSlotShouldSetSlotAsHit(): verifica che"
    + " hitShip() imposti correttamente lo slot come colpito")
    void testHitShipValidSlotShouldSetSlotAsHit() {
        // Creazione di una nave
        Set<Slot> coords = new HashSet<>();
        Slot slot1 = new Slot((short) 0, (short) 0);
        coords.add(slot1);
        try {
            Ship ship = new Ship(coords, ShipsFeatures.DESTROYER, Direction.HORIZONTAL);
            // Colpire lo slot della nave
            ship.hitShip(slot1);

            // Verifica che lo slot sia stato colpito
            assertTrue(slot1.isHit(), "Lo slot non è stato colpito");
        } catch (InvalidShipInstance e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("testHitShipInvalidSlotShouldNotChangeSlotStatus(): verifica che"
    + " hitShip() non cambi lo stato dello slot se non presente nella nave")
    void testHitShipInvalidSlotShouldNotChangeSlotStatus() {
        // Creazione di una nave
        Set<Slot> coords = new HashSet<>();
        Slot slot1 = new Slot((short) 0, (short) 0);
        coords.add(slot1);
        try {
            Ship ship = new Ship(coords, ShipsFeatures.DESTROYER, Direction.HORIZONTAL);
            // Creazione di uno slot non presente nella nave
            Slot invalidSlot = new Slot((short) 1, (short) 1);

            // Colpire lo slot non presente nella nave
            ship.hitShip(invalidSlot);

            // Verifica che lo stato dello slot non sia cambiato
            assertFalse(invalidSlot.isHit(), "Lo slot è stato colpito");
        } catch (InvalidShipInstance e) {
            System.out.println(e.getMessage());
        }
    }
}
