package it.uniba.elements;

import it.uniba.features.Difficulty;
import it.uniba.features.GridSize;
import it.uniba.features.PlayTime;
import it.uniba.interfaces.Field;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test Suite per la classe Saver.
 */
class TestSaver {

    @TempDir
    private Path tempDir;

    @Test
    @DisplayName("testSaveGame(): Test che verifica che il salvataggio di un gioco sia corretto")
    void testSaveGame() {
        Field game = new GameMap(GridSize.LARGE, Difficulty.EASY);
        PlayTime timer = PlayTime.INSTANCE;
        String fileName = "testFile";
        String fileExt = ".dat";
        String pathDir = tempDir.toString();

        Saver.saveGame(game, timer, fileName, fileExt, pathDir);

        File file = new File(pathDir + File.separator + fileName + fileExt);
        assertTrue(file.exists(), "File should exist after saving game");
    }

    @Test
    @DisplayName("testLoadObjects(): Test che verifica che il caricamento di un salvataggio "
    + "non restituisce oggetti null")
    void testLoadObjects() {
        Field game = new GameMap(GridSize.EXTRALARGE, Difficulty.MEDIUM);
        PlayTime timer = PlayTime.INSTANCE;
        timer.start();
        String fileName = "testFile";
        String fileExt = ".dat";
        String pathDir = tempDir.toString();

        Saver.saveGame(game, timer, fileName, fileExt, pathDir);
        String pathFile = pathDir + File.separator + fileName + fileExt;

        Object[] loadedObjects = Saver.loadObjects(pathFile);

        assertNotNull(loadedObjects, "Loaded objects should not be null");
    }

    @Test
    @DisplayName("testLoadNumObjects(): Test che verifica che il caricamento di un salvataggio "
    + "restituisce 2 oggetti")
    void testLoadNumObjects() {
        Field game = new GameMap(GridSize.EXTRALARGE, Difficulty.MEDIUM);
        PlayTime timer = PlayTime.INSTANCE;
        timer.start();
        String fileName = "testFile";
        String fileExt = ".dat";
        String pathDir = tempDir.toString();

        Saver.saveGame(game, timer, fileName, fileExt, pathDir);
        String pathFile = pathDir + File.separator + fileName + fileExt;

        Object[] loadedObjects = Saver.loadObjects(pathFile);

        assertEquals(2, loadedObjects.length, "Loaded objects array should have 2 elements");
    }
}
