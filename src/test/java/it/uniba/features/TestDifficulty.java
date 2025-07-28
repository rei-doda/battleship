package it.uniba.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test Suite per la classe Difficulty.
 */
class TestDifficulty {

    @Test
    @DisplayName(" testGetAttempts(): Test che verifica che il numero di tentativi sia corretto")
    void testGetAttempts() {

        final short attemptsFacile = 50;
        // Verifica il metodo getAttempts()
        assertEquals(attemptsFacile, Difficulty.EASY.getAttempts(), "Il numero di tentativi è errato");
    }

    @Test
    @DisplayName(" testGetName(): Test che verifica che il nome della difficoltà sia corretto")
    void testGetName() {
        // Verifica il metodo getName()
        assertEquals("Difficile", Difficulty.HARD.getName(), "Il nome della difficoltà è errato");
    }


}
