package it.uniba.elements;

import it.uniba.features.Difficulty;
import it.uniba.features.GridSize;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*Test Suite per il metodo statico (.getMapRenewed()) di VisualMap
per visualizzare la mappa con spazio tra cornice e mappa personalizzzato.*/
class TestVisualMap {
    private GameMap game;
    @BeforeEach
    void setUp() {
        game = new GameMap(GridSize.STANDARD, Difficulty.EASY);
    }


    @Test
    void testExceptionOnPersonalizedOddOutMap() {
        final short odd = 5;
        assertThrows(IllegalArgumentException.class, () -> {
            VisualMap.getMapRenewed(game.getOcean(), (short) odd);
        }, "Exception should be raised");
    }


    @Test
    void testExceptionOnPersonalizedNegativeOutMap() {
        final short negative = -1;
        assertThrows(IllegalArgumentException.class, () -> {
            VisualMap.getMapRenewed(game.getOcean(), (short) negative);
        }, "Exception should be raised");
    }

    @Test
    void testExceptionOnPersonalizedLessThanSixOutMap() {
        final short lessThanSix = 4;
        assertThrows(IllegalArgumentException.class, () -> {
            VisualMap.getMapRenewed(game.getOcean(), (short) lessThanSix);
        }, "Exception should be raised");
    }
}
