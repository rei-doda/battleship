package it.uniba.elements;

import it.uniba.features.ColorShell;
import it.uniba.features.MapChar;
import it.uniba.features.ShipsFeatures;
import it.uniba.features.ShotResult;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// <<boundary>>

/**
 * Questa classe definisce i metodi e attributi statici per la visualizzazione della mappa di gioco.
 * Gestisce tutta la parte grafica.
 */
public final class VisualMap {
    private static final Map<Character, String> GRAPHICS; // Per la visualizzazione della mappa
    private static final Map<ShipsFeatures, String> SHIPS_GRAPHICS; // Per la visualizzazione del metodo .showShip
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // Per la visualizzazione della mappa
    private static final short MIN_LIMIT_OUT_MAP = 6; // Per la visualizzazione della mappa (serve per mettere spazi
    // bianchi tra la griglia della mappa di gioco e il contorno della mappa), il minimo è 6
    private static final short MIN_WHITE_SPACES = 6; // per la visualizzazione nella funzione showShips
    private static final short DEC = 10; // Primo numero con due cifre
    static {
        GRAPHICS = new HashMap<>();
        GRAPHICS.put(MapChar.EDGE_TOKEN_X.getSymbol(), ColorShell.GREEN.getValue()
                + MapChar.EDGE_TOKEN_X.getSymbol() +  ColorShell.END_ESCAPE.getValue());
        GRAPHICS.put(MapChar.EDGE_TOKEN_Y.getSymbol(), ColorShell.GREEN.getValue()
                + MapChar.EDGE_TOKEN_Y.getSymbol() + ColorShell.END_ESCAPE.getValue());
        GRAPHICS.put(MapChar.PADDING_TOKEN.getSymbol(), ColorShell.BLACK.getValue()
                + MapChar.PADDING_TOKEN.getSymbol() + ColorShell.END_ESCAPE.getValue());
        GRAPHICS.put(MapChar.SEA_TOKEN.getSymbol(), ColorShell.CYAN.getValue()
                + MapChar.SEA_TOKEN.getSymbol() + ColorShell.END_ESCAPE.getValue());
        GRAPHICS.put(MapChar.HIT_TOKEN.getSymbol(), ColorShell.YELLOW.getValue()
                + MapChar.HIT_TOKEN.getSymbol() + ColorShell.END_ESCAPE.getValue());
        GRAPHICS.put(MapChar.SHIP_TOKEN.getSymbol(), ColorShell.WHITE.getValue()
                + MapChar.SHIP_TOKEN.getSymbol() + ColorShell.END_ESCAPE.getValue());
        GRAPHICS.put(MapChar.MISS_TOKEN.getSymbol(), ColorShell.PURPLE.getValue()
                + MapChar.MISS_TOKEN.getSymbol() + ColorShell.END_ESCAPE.getValue());
        GRAPHICS.put(MapChar.KILL_TOKEN.getSymbol(), ColorShell.RED.getValue()
                + MapChar.KILL_TOKEN.getSymbol() + ColorShell.END_ESCAPE.getValue());
        GRAPHICS.put(MapChar.ANGULAR_TOKEN.getSymbol(), ColorShell.ORANGE_1.getValue()
                + MapChar.ANGULAR_TOKEN.getSymbol() + ColorShell.END_ESCAPE.getValue());
        SHIPS_GRAPHICS = new HashMap<>();
        SHIPS_GRAPHICS.put(ShipsFeatures.AIRCARRIER, ColorShell.ORANGE_4.getValue()
                + MapChar.SHIP_TOKEN.getSymbol() + ColorShell.END_ESCAPE.getValue());
        SHIPS_GRAPHICS.put(ShipsFeatures.BATTLESHIP, ColorShell.ORANGE_3.getValue()
                + MapChar.SHIP_TOKEN.getSymbol() + ColorShell.END_ESCAPE.getValue());
        SHIPS_GRAPHICS.put(ShipsFeatures.CRUISER, ColorShell.ORANGE_2.getValue()
                + MapChar.SHIP_TOKEN.getSymbol() + ColorShell.END_ESCAPE.getValue());
        SHIPS_GRAPHICS.put(ShipsFeatures.DESTROYER, ColorShell.ORANGE_1.getValue()
                + MapChar.SHIP_TOKEN.getSymbol() + ColorShell.END_ESCAPE.getValue());
    }
    private VisualMap() { }
    /** Metodo statico che restituisce una stringa contenente le informazioni sul tipo
     * e sul numero di navi ancora in gioco (non affondate).
     * @param enemy Mappa contenente la lista di oggetti nave in gioco.
     * @return String stringa contenente le informazioni sul tipo e sul numero di navi ancora in gioco.
     */
    public static String showShip(final Map<ShipsFeatures, List<Ship>> enemy) {
        LinkedHashMap<ShipsFeatures, List<Ship>> aliveEnemy = enemy.entrySet().stream()
        .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().stream().filter(ship -> !ship.isSunk()).collect(Collectors.toList()),
                (v1, v2) -> v1, // preserva l'ordine di inserimento nella LinkedHashMap
                LinkedHashMap::new
        ));
        StringBuilder toshow = new StringBuilder();
        toshow.append(ColorShell.RED.getValue());
        toshow.append("__________Navi da affondare__________\n\n");
        toshow.append(ColorShell.END_ESCAPE.getValue());
        for (Map.Entry<ShipsFeatures, List<Ship>> entry : aliveEnemy.entrySet()) {
            String shipName = entry.getKey().getName();
            String shipSymbol = SHIPS_GRAPHICS.get(entry.getKey());
            int shipSize = entry.getKey().getNumSlots();
            int shipCount = entry.getValue().size();
            String shipInfo = String.format("|%-25s|%-11s|", shipName, shipSymbol.repeat(shipSize))
                    + " ".repeat(MIN_WHITE_SPACES - shipSize) + shipCount + "|\n";
            toshow.append(shipInfo);
        }
        toshow.append(ColorShell.RED.getValue());
        toshow.append("\n_____________________________________\n");
        toshow.append(ColorShell.END_ESCAPE.getValue());
        return toshow.toString();
    }
    /** Metodo che restituisce una stringa contenente la mappa di gioco (ocean).
     *  Costituita anche dalla cornicie di delimitazione, settata di default a 6 (3 spazi a destra,
     * sinistra, sopra e sotto).
     * @param ocean Mappa di gioco.
     * @return String stringa contenente la mappa di gioco.
     */
    public static String getMapRenewed(final Slot[][] ocean) {
        short outMap = MIN_LIMIT_OUT_MAP;
        StringBuilder map = new StringBuilder();
        map.append(GRAPHICS.get(MapChar.ANGULAR_TOKEN.getSymbol())).append(" ")
                .append(GRAPHICS.get(MapChar.EDGE_TOKEN_X.getSymbol()).concat(" ").repeat(ocean.length + outMap))
                .append(GRAPHICS.get(MapChar.ANGULAR_TOKEN.getSymbol())).append("\n");
        for (short i = 0; i < (outMap / 2) - 1; i++) {
            map.append(GRAPHICS.get(MapChar.EDGE_TOKEN_Y.getSymbol())).append(" ")
                    .append(GRAPHICS.get(MapChar.PADDING_TOKEN.getSymbol()).concat(" ").repeat(ocean.length + outMap))
                    .append(GRAPHICS.get(MapChar.EDGE_TOKEN_Y.getSymbol())).append("\n");
        }
        for (short i = 0; i < ocean.length; i++) {
            short numToPrint = (short) (ocean.length - i);
            short paddingToPrint = (short) (numToPrint >= DEC ? 2 : 1);
            String stringNum = Short.toString(numToPrint);
            map.append(GRAPHICS.get(MapChar.EDGE_TOKEN_Y.getSymbol())).append(" ")
                    .append(GRAPHICS.get(MapChar.PADDING_TOKEN.getSymbol())
                            .concat(" ").repeat((outMap / 2) - paddingToPrint))
                    .append(stringNum.length() == 2
                            ? stringNum.charAt(0) + " " + stringNum.charAt(1) + " "
                            : stringNum.concat(" "));
            for (short j = 0; j < ocean.length; j++) {
                map.append(GRAPHICS.get(ocean[i][j].getChar()).concat(" "));
            }
            map.append(GRAPHICS.get(MapChar.PADDING_TOKEN.getSymbol()).concat(" ").repeat(outMap / 2))
                    .append(GRAPHICS.get(MapChar.EDGE_TOKEN_Y.getSymbol())).append("\n");
        }
        map.append(GRAPHICS.get(MapChar.EDGE_TOKEN_Y.getSymbol())).append(" ")
                .append(GRAPHICS.get(MapChar.PADDING_TOKEN.getSymbol()).concat(" ").repeat(outMap / 2));
        for (short i = 0; i < ocean.length; i++) {
            map.append(ALPHABET.charAt(i)).append(" ");
        }
        map.append(GRAPHICS.get(MapChar.PADDING_TOKEN.getSymbol()).concat(" ").repeat(outMap / 2))
                .append(GRAPHICS.get(MapChar.EDGE_TOKEN_Y.getSymbol())).append("\n");
        for (short i = 0; i < (outMap / 2) - 1; i++) {
            map.append(GRAPHICS.get(MapChar.EDGE_TOKEN_Y.getSymbol())).append(" ")
                    .append(GRAPHICS.get(MapChar.PADDING_TOKEN.getSymbol()).concat(" ").repeat(ocean.length + outMap))
                    .append(GRAPHICS.get(MapChar.EDGE_TOKEN_Y.getSymbol())).append("\n");
        }
        map.append(GRAPHICS.get(MapChar.ANGULAR_TOKEN.getSymbol())).append(" ")
                .append(GRAPHICS.get(MapChar.EDGE_TOKEN_X.getSymbol()).concat(" ").repeat(ocean.length + outMap))
                .append(GRAPHICS.get(MapChar.ANGULAR_TOKEN.getSymbol())).append("\n");
        return map.toString();
    }
    /** Metodo che restituisce una stringa contenente la mappa di gioco (ocean).
     *  Costituita anche dalla cornicie di delimitazione, passata come parametro
     * @param ocean Mappa di gioco.
     * @return String stringa contenente la mappa di gioco.
     * @param outMap spazio di delimitazione.
     * @throws IllegalArgumentException se outMap non è un numero pari o è < di 6.
     */
    public static String getMapRenewed(final Slot[][] ocean, final short outMap) {
        if (outMap < MIN_LIMIT_OUT_MAP || outMap % 2 != 0) {
            throw new IllegalArgumentException("outMap deve essere un numero pari e deve essere >= di 6.");
        }
        StringBuilder map = new StringBuilder();
        map.append(GRAPHICS.get(MapChar.ANGULAR_TOKEN.getSymbol())).append(" ")
                .append(GRAPHICS.get(MapChar.EDGE_TOKEN_X.getSymbol()).concat(" ").repeat(ocean.length + outMap))
                .append(GRAPHICS.get(MapChar.ANGULAR_TOKEN.getSymbol())).append("\n");
        for (short i = 0; i < (outMap / 2) - 1; i++) {
            map.append(GRAPHICS.get(MapChar.EDGE_TOKEN_Y.getSymbol())).append(" ")
                    .append(GRAPHICS.get(MapChar.PADDING_TOKEN.getSymbol()).concat(" ").repeat(ocean.length + outMap))
                    .append(GRAPHICS.get(MapChar.EDGE_TOKEN_Y.getSymbol())).append("\n");
        }
        for (short i = 0; i < ocean.length; i++) {
            short numToPrint = (short) (ocean.length - i);
            short paddingToPrint = (short) (numToPrint >= DEC ? 2 : 1);
            String stringNum = Short.toString(numToPrint);
            map.append(GRAPHICS.get(MapChar.EDGE_TOKEN_Y.getSymbol())).append(" ")
                    .append(GRAPHICS.get(MapChar.PADDING_TOKEN.getSymbol())
                            .concat(" ").repeat((outMap / 2) - paddingToPrint))
                    .append(stringNum.length() == 2
                            ? stringNum.charAt(0) + " " + stringNum.charAt(1) + " "
                            : stringNum.concat(" "));
            for (short j = 0; j < ocean.length; j++) {
                map.append(GRAPHICS.get(ocean[i][j].getChar()).concat(" "));
            }
            map.append(GRAPHICS.get(MapChar.PADDING_TOKEN.getSymbol()).concat(" ").repeat(outMap / 2))
                    .append(GRAPHICS.get(MapChar.EDGE_TOKEN_Y.getSymbol())).append("\n");
        }
        map.append(GRAPHICS.get(MapChar.EDGE_TOKEN_Y.getSymbol())).append(" ")
                .append(GRAPHICS.get(MapChar.PADDING_TOKEN.getSymbol()).concat(" ").repeat(outMap / 2));
        for (short i = 0; i < ocean.length; i++) {
            map.append(ALPHABET.charAt(i)).append(" ");
        }
        map.append(GRAPHICS.get(MapChar.PADDING_TOKEN.getSymbol()).concat(" ").repeat(outMap / 2))
                .append(GRAPHICS.get(MapChar.EDGE_TOKEN_Y.getSymbol())).append("\n");
        for (short i = 0; i < (outMap / 2) - 1; i++) {
            map.append(GRAPHICS.get(MapChar.EDGE_TOKEN_Y.getSymbol())).append(" ")
                    .append(GRAPHICS.get(MapChar.PADDING_TOKEN.getSymbol()).concat(" ").repeat(ocean.length + outMap))
                    .append(GRAPHICS.get(MapChar.EDGE_TOKEN_Y.getSymbol())).append("\n");
        }
        map.append(GRAPHICS.get(MapChar.ANGULAR_TOKEN.getSymbol())).append(" ")
                .append(GRAPHICS.get(MapChar.EDGE_TOKEN_X.getSymbol()).concat(" ").repeat(ocean.length + outMap))
                .append(GRAPHICS.get(MapChar.ANGULAR_TOKEN.getSymbol())).append("\n");
        return map.toString();
    }
    /** Metodo che restituisce una stringa contenente la mappa di gioco (ocean).
     *  Costituita anche dalla cornicie di delimitazione. Potrebbe non funzionare o generare eccezioni.
     * @param ocean Mappa di gioco.
     * @return String stringa contenente la mappa di gioco.
     */
    @Deprecated
    public static String getMap(final Slot[][] ocean) {
        short outMap = MIN_LIMIT_OUT_MAP;
        StringBuilder map = new StringBuilder();
        final short magic1 = 3;
        final short magic2 = 12;
        map.append("\n");
        int limit = ocean.length + outMap;
        for (short i = 0; i < limit; i++) {
            for (short j = 0; j < limit; j++) {
                if (i == 0 || i == limit - 1) {
                    map.append(GRAPHICS.get(MapChar.EDGE_TOKEN_X.getSymbol()));
                    map.append(GRAPHICS.get(MapChar.PADDING_TOKEN.getSymbol()));
                } else if (j == 0 || (j == limit - 1 && i != limit - 1)) {
                    map.append(GRAPHICS.get(MapChar.EDGE_TOKEN_Y.getSymbol()));
                    map.append(GRAPHICS.get(MapChar.PADDING_TOKEN.getSymbol()));
                } else if ((i >= outMap - 2 && i < limit - (outMap - 2))
                        && (j >= outMap - 2 && j < limit - (outMap - 2))) {
                    map.append(GRAPHICS.get(ocean[(ocean.length - 1)
                            - (i - (outMap - 2))][j - (outMap - 2)].getChar()));
                    map.append(GRAPHICS.get(MapChar.PADDING_TOKEN.getSymbol()));
                } else if (j == 1 && i >= 2 && i < limit - 2) {
                    map.append((ocean.length + outMap) - i - magic1);
                    map.append(GRAPHICS.get(MapChar.PADDING_TOKEN.getSymbol()));
                } else if (i == magic2 && j >= 2 && j < limit - 2) {
                    map.append(ALPHABET.charAt(j - 2));
                    map.append(GRAPHICS.get(MapChar.PADDING_TOKEN.getSymbol()));
                } else {
                    map.append(GRAPHICS.get(MapChar.PADDING_TOKEN.getSymbol()));
                    map.append(GRAPHICS.get(MapChar.PADDING_TOKEN.getSymbol()));
                }
            }
            map.append("\n");
        }
        return map.toString();
    }
    public static String getAlphabet() {
        return ALPHABET;
    }
    /** restituisce il messaggio di un oggetto di tipo ShotResult.
     * @param msg oggetto di tipo ShotResult.
     * @return String messaggio.
     */
    public static String getMessage(final ShotResult msg) {
        return msg.getMessage();
    }
}
