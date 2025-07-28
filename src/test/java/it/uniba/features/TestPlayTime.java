package it.uniba.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Testare il tempo di gioco.
 */
class PlayTimeTest {

    private PlayTime playTime;

    /**
     * Metodo per testare il l'impostazione del tempo totale.
     */
    @BeforeEach
    void setUp() {
        playTime = PlayTime.INSTANCE;
        playTime.setTotalTime(1); // Set total time to 1 minute for testing
    }
    /**
     * Metodo per testare il tempo di gioco.
     */
    @Test
    @DisplayName(" testGetMinutesPerGame(): verifica che il tempo di gioco sia corretto (valore specifico)")
    void testGetMinutesDefaultPerGame() {
        final int minutes = 20;
        assertEquals(minutes, playTime.getMinutesPerGame(), "Il tempo di gioco è errato");
    }
    @Test
    @DisplayName(" testGetMinutesPerGame(): verifica che il tempo di gioco sia corretto (valore sotto specifico)")
    void testGetMinutesPerGameUnderTime() {
        final int minutes = 19;
        assertNotEquals(minutes, playTime.getMinutesPerGame());
    }

    @Test
    @DisplayName(" testGetMinutesPerGame(): verifica che il tempo di gioco sia corretto (valore sopra specifico)")
    void testGetMinutesPerGameOverTime() {
        final int minutes = 21;
        assertNotEquals(minutes, playTime.getMinutesPerGame());
    }
    /**
     * Metodo per testare il tempo rimanente.
     * @throws InterruptedException
     */
    @Test
    @DisplayName(" testGetRemainingTime(): verifica che il tempo rimanente sia corretto")
    void testGetRemainingTime() throws InterruptedException {
        playTime.start();
        final int millisecondi = 2000;
        Thread.sleep(millisecondi); // Wait for 2 seconds

        String remainingTime = playTime.getRemainingTime();
        assertEquals("00:58", remainingTime, "Il tempo rimanente è errato");

        playTime.stop();
    }
    @Test
    @DisplayName(" testGetRemainingTime(): verifica che il tempo rimanente sia corretto (valore sotto specifico)")
    void testGetRemainingTimeUnderTime() throws InterruptedException {
        playTime.start();
        final int millisecondi = 1000;
        Thread.sleep(millisecondi); // Wait for 1 seconds
        String remainingTime = playTime.getRemainingTime();
        assertNotEquals("00:58", remainingTime);
    }
    @Test
    @DisplayName(" testGetRemainingTime(): verifica che il tempo rimanente sia corretto (valore sopra specifico)")
    void testGetRemainingTimeOverTime() throws InterruptedException {
        playTime.start();
        final int millisecondi = 3000;
        Thread.sleep(millisecondi); // Wait for 3 seconds

        String remainingTime = playTime.getRemainingTime();
        assertNotEquals("00:58", remainingTime);
    }
    /**
     * Metodo per testare il tempo la conversione del tempo totale.
     */
    @Test
    @DisplayName(" testGetTotalTime(): verifica che il tempo totale sia corretto")
    void testGetTotalTime() {
        final int minutes = 20;
        playTime.setTotalTime(minutes);
        assertEquals("20:00", playTime.getTotalTime(), "Il tempo totale è errato");
    }
    @Test
    @DisplayName(" testGetTotalTime(): Test che verifica che il tempo totale sia corretto (valore sotto specifico)")
    void testGetTotalTimeUnderTime() {
        final int minutes = 19;
        playTime.setTotalTime(minutes);
        assertNotEquals("20:00", playTime.getTotalTime());
    }
    @Test
    @DisplayName(" testGetTotalTime(): verifica che il tempo totale sia corretto (valore sopra specifico)")
    void testGetTotalTimeOverTime() {
        final int minutes = 21;
        playTime.setTotalTime(minutes);
        assertNotEquals("20:00", playTime.getTotalTime());
    }
    /**
     * Metodo per testare il tempo trascorso.
     *
     * @throws InterruptedException
     */
    @Test
    @DisplayName(" testGetElapsedTime(): verifica che il tempo trascorso sia corretto")
    void testGetElapsedTime() throws InterruptedException {
        playTime.start();
        final int millisecondi = 3000;
        Thread.sleep(millisecondi); // Aspetta 3 secondi

        String elapsedTime = playTime.getElapsedTime();
        assertEquals("00:03", elapsedTime, "Il tempo trascorso è errato");

        playTime.stop();
    }
    @Test
    @DisplayName(" testGetElapsedTime(): verifica che il tempo trascorso sia corretto (valore sotto specifico)")
    void testGetElapsedTimeUnderTime() throws InterruptedException {
        playTime.start();
        final int millisecondi = 2000;
        Thread.sleep(millisecondi); // Aspetta 2 secondi

        String elapsedTime = playTime.getElapsedTime();
        assertNotEquals("00:03", elapsedTime);
    }
    @Test
    @DisplayName(" testGetElapsedTime(): verifica che il tempo trascorso sia corretto (valore sopra specifico)")
    void testGetElapsedTimeOverTime() throws InterruptedException {
        playTime.start();
        final int millisecondi = 4000;
        Thread.sleep(millisecondi); // Aspetta 4 secondi

        String elapsedTime = playTime.getElapsedTime();
        assertNotEquals("00:03", elapsedTime);
    }
}

