package it.uniba.app;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import it.uniba.features.PlayTime;
import it.uniba.interfaces.CommandHandler;
import it.uniba.features.ColorShell;
import it.uniba.features.CommandType;
import it.uniba.features.Difficulty;
import it.uniba.features.GridSize;
import it.uniba.elements.GameMap;
import it.uniba.elements.Saver;
import it.uniba.elements.ShotParser;
import it.uniba.elements.VisualMap;
import it.uniba.features.ShotResult;
import it.uniba.interfaces.Field;
import it.uniba.elements.Slot;
import it.uniba.exceptions.InvalidShotException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class of the application.
 */
public final class App {
    // Mappa dei comandi utilizzabili prima di iniziare una partita
    // con valori che puntano a metodi statici che implementano l'interfaccia
    // funzionale CommandHandler.
    private static final Map<CommandType, CommandHandler> PRE_COMMANDS = new HashMap<>();
    // Mappa dei comandi utilizzabili dopo aver iniziato una partita
    // con valori che puntano a metodi statici che implementano l'interfaccia
    // funzionale CommandHandler.
    private static final Map<CommandType, CommandHandler> POST_COMMANDS = new HashMap<>();
    private static final String SAVE_DIR = "saves";
    private static final String SAVE_EXT = ".dat";
    private static String pathDir = null;
    // Difficolta della partita
    private static Difficulty gameDifficulty = Difficulty.EASY;
    private static boolean exit = false;
    //Mappa di gioco
    private static Field game = null;
    // Dimensione della mappa di gioco
    private static GridSize gridSize = GridSize.STANDARD;
    // Variabile che indica se è una partita in corso
    private static boolean gameRunning = true;
    //Tempo di gioco
    private static PlayTime playTime = PlayTime.INSTANCE;

    /**
     * Get a greeting sentence.
     *
     * @return the "Hello World!" string.
     */
    public String getGreeting() {
        return "Hello World!!!";
    }

    /**
     * Metodo che verifica se la cartella dei salvataggi esiste, altrimenti la crea.
     * @param nameDir nome della cartella
     * @return percorso della cartella
     */
    public static String checkIfNotCreatePath(final String nameDir) {
        String percorsoEseguibile = System.getProperty("user.dir");
        String percorsoCartella = percorsoEseguibile + File.separator + nameDir;
        File cartella = new File(percorsoCartella);
        if (cartella.exists()) {
            return percorsoCartella;
        }
        if (cartella.mkdir()) {
            return percorsoCartella;
        } else {
            return null;
        }
    }

    /**
     * Metodo che restituisce una lista dei file con una determinata estensione
     * presenti in una cartella.
     * @param percorsoCartella percorso della cartella
     * @param estensione estensione dei file
     * @return lista dei file con una determinata estensione presenti in una cartella
     */
    public static List<String> listFilesForExt(final String percorsoCartella, final String estensione) {
        List<String> listaFile = new ArrayList<>();
        File cartella = new File(percorsoCartella);
        File[] files = cartella.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(estensione)) {
                    listaFile.add(file.getName());
                }
            }
            return listaFile;
        }
        return null;
    }

    /**
     * Metodo che verifica che un determinato percorso esista.
     * @param path percorso da verificare
     * @return true se il percorso esiste, false altrimenti
     */
    public static boolean isTherePathSaves(final String path) {
        File cartella = new File(path);
        return cartella.exists();
    }

    /**
     * Metodo che stampa il messaggio di benvenuto sul System.out.
     *
     * @param color del messaggio "Battleship"
     */
    public static void printWelcome(final ColorShell color) {
        System.out.print(VisualMap.getMessage(ShotResult.WELCOME));
        System.out.println("/help per avere un aiuto soldato!");
    }

    /**
     * Metodo che controlla se il comando inserito dall'utente è presente tra i
     * comandi disponibili.
     *
     * @param commands Mappa dei comandi Map<CommandType, CommandHandler>
     * @param value    comando inserito dall'utente
     *
     * @return il comando inserito dall'utente se alias corretto | null altrimenti
     */
    public static CommandType checkCommand(final Map<CommandType, CommandHandler> commands, final String value) {
        for (CommandType iteration : commands.keySet()) {
            for (Pattern patt : iteration.getCommandPattern()) {
                if (patt.matcher(value).matches()) {
                    return iteration;
                }
            }
        }
        return null;
    }

    // Inizializzazione delle mappe dei comandi.
    // Creazione della cartella dei salvataggi qualora non ci fosse.
    static {
        PRE_COMMANDS.put(CommandType.HELP, App::handleHelp);
        PRE_COMMANDS.put(CommandType.START, App::handlePlay);
        PRE_COMMANDS.put(CommandType.EXIT, App::handleExit);
        PRE_COMMANDS.put(CommandType.SET_EASY, App::handleDifficulty);
        PRE_COMMANDS.put(CommandType.EASY_SET, App::handleDifficultySet);
        PRE_COMMANDS.put(CommandType.SET_MEDIUM, App::handleDifficulty);
        PRE_COMMANDS.put(CommandType.MEDIUM_SET, App::handleDifficultySet);
        PRE_COMMANDS.put(CommandType.SET_HARD, App::handleDifficulty);
        PRE_COMMANDS.put(CommandType.HARD_SET, App::handleDifficultySet);
        PRE_COMMANDS.put(CommandType.ATTEMPTS, App::handleAttemptsSet);
        PRE_COMMANDS.put(CommandType.B_LARGE, App::handleSetBoard);
        PRE_COMMANDS.put(CommandType.B_X_LARGE, App::handleSetBoard);
        PRE_COMMANDS.put(CommandType.B_STANDARD, App::handleSetBoard);
        PRE_COMMANDS.put(CommandType.TIME, App::handleSetTime);
        POST_COMMANDS.put(CommandType.SHOW_LEVEL, App::handleShowLevel);
        POST_COMMANDS.put(CommandType.EXIT, App::handleExit);
        POST_COMMANDS.put(CommandType.HELP, App::handleHelp);
        POST_COMMANDS.put(CommandType.SHOW_SHIPS, App::handleShowShips);
        POST_COMMANDS.put(CommandType.UNVEIL_GRID, App::handleUnveilGrid);
        POST_COMMANDS.put(CommandType.SHOT, App::handleShot);
        POST_COMMANDS.put(CommandType.GIVE_UP, App::handleGiveUp);
        POST_COMMANDS.put(CommandType.SHOW_TIME, App::handleShowTime);
        POST_COMMANDS.put(CommandType.SHOW_ATTEMPTS, App::handleShowAttempts);
        POST_COMMANDS.put(CommandType.SHOW_GRID, App::handleShowGrid);

        pathDir = checkIfNotCreatePath(SAVE_DIR);
        if (pathDir != null) {
            PRE_COMMANDS.put(CommandType.LIST, App::handleList);
            PRE_COMMANDS.put(CommandType.LOAD, App::handleLoad);
            PRE_COMMANDS.put(CommandType.REMOVE, App::handleRemove);

            POST_COMMANDS.put(CommandType.SAVE, App::handleSave);
        } else {
            System.out.println("\nErrore durante la creazione della cartella 'saves'.");
            System.out.println("Non puoi utilizzare i comandi: { /lista, /salva, /carica, /rm }");
            System.out.println("Se l'errore persiste, prova a contattare gli sviluppatori o apri una issue su GitHub.");
        }
    }

    /**
     * Entrypoint of the application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            if (args.length >= 1 && (args[0].equalsIgnoreCase("--help") || args[0].equalsIgnoreCase("-h"))) {
                handleHelp(null, null, null);
            }
            printWelcome(ColorShell.BLUE);
            System.out.println("Premi Invio per continuare...");
            scanner.nextLine(); // Aspetta che l'utente prema invio
            while (!exit) {
                System.out.print("\n| MENU |> ");
                String input = scanner.nextLine().toLowerCase().trim();
                CommandType command = checkCommand(PRE_COMMANDS, input);
                if (command != null) {
                    CommandHandler handler = PRE_COMMANDS.get(command);
                    handler.handle(scanner, new Scanner(input), command);
                } else {
                    System.out.println("\nComando non valido, riprova..");
                }
            }
            System.out.println("\nAlla prossima partita soldato |*|*|*| !!\n");
        } catch (IOException e) {
            System.out.println("Errore di I/O durante l'apertura o dell'origine di input da cui lo scanner"
                    + " sta leggendo");
            System.out.println("Errore:-> " + e.getMessage());
        }
    }

    /**
     * Metodo statico che stampa l'elenco dei comandi disponibili sul System.out.
     *
     * @param input   parametro locale non utile
     * @param value   parametro locale non utile
     * @param command parametro locale non utile
     *
     * @throws java.io.IOException se si verifica un errore di I/O durante
     *                             l'apertura o la lettura dello Scanner
     */
    public static void handleHelp(final Scanner input, final Scanner value, final CommandType command)
            throws IOException {
        System.out.println("\n  Elenco comandi:");
        System.out.println("\t- /gioca || /play: per avviare una partita e posizionare "
                + "le navi da colpire sulla mappa.");
        System.out.println("\t- /esci || /exit: per uscire dal gioco.");
        System.out.println("\t- /standard: per settare la dimensione della mappa di gioco 10x10.");
        System.out.println("\t- /large: per settare la dimensione della mappa di gioco 18x18.");
        System.out.println("\t- /extralarge: per settare la dimensione della mappa di gioco 26x26.");
        System.out.println("\t- /facile || /easy: per settare il numero di tentativi massimo a 80.");
        System.out.println("\t- /medio || /medium: per settare il numero di tentativi massimo a 50.");
        System.out.println("\t- /difficile || /hard: per settare il numero di tentativi massimo a 35.");
        System.out.println("\t\tInoltre se si vuole impostare un numero di tentativi personalizzato "
                + "si può inserire il comando: /<difficoltà> <numero>");
        System.out.println("\t- /tentativi <numero> || /attempts <number>: per impostare un livello di difficoltà "
                + "personalizzato che non va a modificare i livelli di difficoltà presenti.");
        System.out.println("\t- /mostratentativi || /showattempts: per mostrare numero di tentativi già effettuati, "
                + "il numero di tentativi falliti e il numero massimo di tentativi falliti");
        System.out.println("\t- /tempo <minuti> || /time <minutes>: per impostare il tempo di gioco.");
        System.out.println("\t- /mostratempo || /showtime: per mostrare il tempo trascorso e il tempo rimanente.");
        System.out.println("\t- /mostralivello || /showlevel: per mostrare il livello di gioco."
                + "impostato con il numero di tentativi rimanenti (utilizzabile dopo aver cominciato una partita).");
        System.out.println("\t- /mostranavi || /showships: mostra il numero e la tipologia ancora da affondare.");
        System.out.println("\t- /svelagriglia || /showgrid: svela dove sono posizionate le "
                + "navi all'interno della mappa.");
        System.out.println("\t- <LETTERACOLONNA>-<NUMERORIGA>: per effetturare un colpo.");
        System.out.println("\t- /abbandona || /giveup: per abbandonare la partita in corso.");
        System.out.println("\t- /lista || /list: per visualizzare l'elenco dei salvataggi nella cartella 'saves'"
        + "nel percorso dell'eseguibile del gioco.");
        System.out.println("\t- /salva || /save: per salvare una partita in corso.");
        System.out.println("\t- /carica <nomefile> || /load <filename>: per caricare un salvataggio"
        + "specifico contenuto nella cartella 'saves'.");
        System.out.println("\t- /rm <filename>: per rimuovere un salvataggio specifico contenuto nella cartella "
        + "'saves'.");
        System.out.print("\n  Comandi utilizzabili prima di aver cominciato una partita:\n    { ");
        System.out.print("/gioca, /esci, /standard, /large, /extralarge, /facile, /medio, /difficile, ");
        System.out.print("/<difficoltà> <numero>, /tentativi <numero>, /tempo <minuti>, /help, "
        + "/carica, /lista, /rm }\n");
        System.out.print("\n  Comandi utilizzabili dopo aver cominciato una partita:\n    { ");
        System.out.print("/esci, /abbandona, /help, /mostranavi, /mostratempo, /mostralivello, /mostragriglia, ");
        System.out.print("<LETTERACOLONNA>-<NUMERORIGA>, /svelagriglia, /salva }\n");
        System.out.println("\n  - Difficoltà settata di default: /facile");
        System.out.println("  - Dimensione griglia settata di default: /standard");
        System.out.println("  - Tempo di gioco settato di default: 20 minuti");
        System.out.println("\n !!ATTENZIONE la cartella dei salvataggi 'saves' verrà"
        + " creata nello stesso percorso da cui si esegue l'eseguibile.");
    }

    /**
     * Metodo statico che imposta il livello di difficoltà.
     *
     * @param input   parametro locale non utile
     * @param value   parametro locale non utile
     * @param command Parametro che indica nello specifico il livello di difficoltà
     *                richiesto dall'utente
     *
     * @throws java.io.IOException se si verifica un errore di I/O durante
     *                             l'apertura o la lettura dello Scanner
     */
    public static void handleDifficulty(final Scanner input, final Scanner value, final CommandType command)
            throws IOException {
        switch (command) {
            case SET_EASY -> {
                gameDifficulty = Difficulty.EASY;
            }
            case SET_MEDIUM -> {
                gameDifficulty = Difficulty.MEDIUM;
            }
            default -> {
                gameDifficulty = Difficulty.HARD;
            }
        }
        System.out.println("\nOK, difficolta impostata: "
                + gameDifficulty.getName() + " tentativi impostati: "
                + gameDifficulty.getAttempts());
    }

    /**
     * Metodo statico che richiede all'utente se vuole uscire dal gioco.
     *
     * @param input   Rappresenta lo scanner di input passato dal main. Viene
     *                utilizzato per leggere l'input dell'utente
     * @param value   parametro locale non utile
     * @param command parametro locale non utile
     *
     * @throws java.io.IOException se si verifica un errore di I/O durante
     *                             l'apertura o la lettura dello Scanner
     */
    public static void handleExit(final Scanner input, final Scanner value, final CommandType command)
            throws IOException {
        boolean handleLoop = true;
        while (handleLoop) {
            System.out.print("\nSicuro di voler uscire? (si/no) > ");
            String choice = input.nextLine();
            choice = choice.trim();
            switch (choice.toLowerCase()) {
                case "si" -> {
                    exit = true;
                    handleLoop = false;
                    playTime.stop();
                }
                case "no" -> {
                    handleLoop = false;
                }
                default -> System.out.println("\nScelta non valida, riprova..");
            }
        }
    }

    /**
     * Metodo statico che gestisce l'inizio di una partita.
     *
     * @param input   Racchiude lo scanner di input passato dal main. Viene
     *                utilizzato per leggere l'input dell'utente
     * @param value   parametro locale non utile
     * @param command parametro locale non utile
     *
     * @throws java.io.IOException se si verifica un errore di I/O durante
     *                             l'apertura o la lettura dello Scanner
     */
    public static void handlePlay(final Scanner input, final Scanner value, final CommandType command)
            throws IOException {
        gameRunning = false;
        CommandType commPlay;
        String inputPlay;
        playTime.start();
        if (command == CommandType.START) {
            game = new GameMap(gridSize, gameDifficulty);
            game.init();
        }
        System.out.println(VisualMap.getMapRenewed(game.getOcean()));
        while (!gameRunning && !exit && playTime.isRunning()) {
            System.out.print("\n| GAME |> ");
            inputPlay = input.nextLine().toLowerCase().trim();
            commPlay = checkCommand(POST_COMMANDS, inputPlay);
            if (commPlay != null && playTime.isRunning()) {
                CommandHandler handler = POST_COMMANDS.get(commPlay);
                handler.handle(input, new Scanner(inputPlay), commPlay);
                if (game.isWin()) {
                    System.out.println(ShotResult.WIN.getMessage());
                    playTime.stop();
                    gameRunning = true;
                } else if (game.isLose()) {
                    System.out.println(ShotResult.RIP_ATTEMPTS.getMessage());
                    System.out.println(ShotResult.LOSE.getMessage());
                    playTime.stop();
                    gameRunning = true;
                }
            } else if (commPlay == null) {
                System.out.println("\nComando non valido, riprova..");
            }
            if (!playTime.isRunning() && !game.isWin() && !game.isLose()) {
                handleUnveilGrid(input, value, command);
                System.out.println("\n" + VisualMap.getMessage(ShotResult.NO_TIME));
                System.out.println("\n" + VisualMap.getMessage(ShotResult.LOSE));
                playTime.stop();
                gameRunning = true;
            }
        }
    }

    /**
     * Metodo statico che svela la griglia di gioco.
     *
     * @param input   parametro locale non utile
     * @param value   parametro locale non utile
     * @param command parametro locale non utile
     *
     * @throws java.io.IOException se si verifica un errore di I/O durante
     *                             l'apertura o la lettura dello Scanner
     */
    public static void handleUnveilGrid(final Scanner input, final Scanner value, final CommandType command)
            throws IOException {
        game.showGrid();
        System.out.println("\n" + VisualMap.getMapRenewed(game.getOcean()));
        game.showGrid();
    }

    /**
     * Metodo statico che mostra le navi rimanenti da affondare.
     *
     * @param input   parametro locale non utile
     * @param value   parametro locale non utile
     * @param command parametro locale non utile
     *
     * @throws java.io.IOException se si verifica un errore di I/O durante
     *                             l'apertura o la lettura dello Scanner
     */
    public static void handleShowShips(final Scanner input, final Scanner value, final CommandType command)
            throws IOException {
        System.out.println("\n" + VisualMap.showShip(game.getEnemies()));
    }

    /**
     * Metodo statico che mostra il livello di difficoltà impostato e il numero di
     * tentativi falliti.
     *
     * @param input   parametro locale non utile
     * @param value   parametro locale non utile
     * @param command parametro locale non utile
     *
     * @throws IOException se si verifica un errore di I/O durante l'apertura o la
     *                     lettura dello Scanner
     */
    public static void handleShowLevel(final Scanner input, final Scanner value, final CommandType command)
            throws IOException {
        System.out.println("\nDifficolta impostata: "
                + gameDifficulty.getName() + " || numero massimo di tentativi che puoi fallire: "
                + gameDifficulty.getAttempts());
    }

    /**
     * Metodo statico che mostra la griglia di gioco.
     *
     * @param input   parametro locale non utile
     * @param value   parametro locale non utile
     * @param command parametro locale non utile
     *
     * @throws IOException se si verifica un errore di I/O durante l'apertura o la
     *                     lettura dello Scanner
     */
    public static void handleShowGrid(final Scanner input, final Scanner value, final CommandType command)
            throws IOException {
        System.out.println("\n" + VisualMap.getMapRenewed(game.getOcean()));
    }

    /**
     * Metodo statico che setta il numero di tentativi che si possono fallire per
     * tipologia di difficoltà.
     *
     * @param input   parametro locale non utile
     * @param value   Parametro che indica il numero di tentativi che si possono
     *                fallire che verranno settati
     * @param command Parametro che indica nello specifico il livello di difficoltà
     *                richiesto dall'utente
     *
     * @throws IOException se si verifica un errore di I/O durante l'apertura o la
     *                     lettura dello Scanner
     */
    public static void handleDifficultySet(final Scanner input, final Scanner value, final CommandType command)
        throws IOException {
        String[] inputSplit = value.nextLine().trim().split("\\s+");
        short attempts = Short.parseShort(inputSplit[1]);
        switch (command) {
            case EASY_SET -> {
                Difficulty.EASY.setAttempts(attempts);
                gameDifficulty = Difficulty.EASY;
            }
            case MEDIUM_SET -> {
                Difficulty.MEDIUM.setAttempts(attempts);
                gameDifficulty = Difficulty.MEDIUM;
            }
            default -> {
                Difficulty.HARD.setAttempts(attempts);
                gameDifficulty = Difficulty.HARD;
            }
        }
            System.out.println("\nNumero di tentativi di: " + gameDifficulty.getName()
            + " impostati a: " + gameDifficulty.getAttempts());
    }

    /**
     * Metodo statico che setta il numero di tentativi che si possono fallire senza
     * considerare.
     * le difficoltà preimpostate, creando una specie di livello di gioco
     * "personalizzato"
     *
     * @param input   parametro locale non utile
     * @param value   Perametro che indica il numero di tentativi che si possono
     *                fallire
     * @param command Parametro che indica nello specifico che si vuole settare un
     *                livello di gioco "personalizzato"
     *
     * @throws IOException se si verifica un errore di I/O durante l'apertura o la
     *                     lettura dello Scanner
     */
    public static void handleAttemptsSet(final Scanner input, final Scanner value, final CommandType command)
        throws IOException {
        String[] inputSplit = value.nextLine().trim().split("\\s+");
        short attempts = Short.parseShort(inputSplit[1]);
        gameDifficulty.setAttempts(attempts);
        gameDifficulty.setName("personalizzata");
        System.out.println("\nDifficoltà impostata: " + gameDifficulty.getName()
        + " con tentativi: " + gameDifficulty.getAttempts());
    }

    /**
     * Metodo statico che mostra il numero di tentativi già effettuati, il numero di
     * tentativi falliti e
     * il numero massimo di tentativi falliti.
     *
     * @param input   parametro locale non utile
     * @param value   parametro locale non utile
     * @param command parametro locale non utile
     *
     * @throws IOException se si verifica un errore di I/O durante l'apertura o la
     *                     lettura dello Scanner
     */
    public static void handleShowAttempts(final Scanner input, final Scanner value, final CommandType command)
            throws IOException {
        System.out.println("\nI tentativi effettuati sono: " + game.getShots());
        System.out.println("\nI tentativi falliti sono: " + game.getFailedShots());
        System.out.println("\nIl numero massimo di tentativi che puoi sbagliare è: "
                + gameDifficulty.getAttempts());

    }

    /**
     * Metodo statico che setta la griglia di gioco in base a dimensioni
     * preimpostate scelte dall'utente.
     *
     * @param input   parametro locale non utile
     * @param value   parametro locale non utile
     * @param command Parametro che indica la taglia della griglia di gioco che si
     *                vuole impostare
     *
     * @throws IOException se si verifica un errore di I/O durante l'apertura o la
     *                     lettura dello Scanner
     */
    public static void handleSetBoard(final Scanner input, final Scanner value, final CommandType command)
            throws IOException {
        switch (command) {
            case B_STANDARD -> {
                gridSize = GridSize.STANDARD;
            }
            case B_LARGE -> {
                gridSize = GridSize.LARGE;
            }
            default -> {
                gridSize = GridSize.EXTRALARGE;
            }
        }
        System.out.println("\nFatto, griglia impostata: " + gridSize.getSize());
    }

    /**
     * Metodo statico che setta il tempo di gioco di una partita.
     *
     * @param input   parametro locale non utile
     * @param value   Parametro che indica il tempo di gioco che si vuole impostare
     *                in minuti
     * @param command Parametro che indica il comando per settare il tempo di gioco
     *                di una partita
     *
     * @throws IOException se si verifica un errore di I/O durante l'apertura o la
     *                     lettura dello Scanner
     */
    public static void handleSetTime(final Scanner input, final Scanner value, final CommandType command)
            throws IOException {
        playTime = PlayTime.INSTANCE;
        String[] stringTime = value.nextLine().trim().split("\\s+");
        long time = Short.parseShort(stringTime[1]);
        if (time > 0) {
            playTime.setTotalTime(time);
            System.out.println("\nTempo impostato: " + playTime.getTotalTime() + " minuti");
        } else {
            System.out.println("\nTempo non valido, riprova..");
    }
}

    /**
     * Metodo statico che gestice il comando per effettuare un colpo sulla griglia
     * di gioco.
     *
     * @param input   parametro locale non utile
     * @param value   Parametro che indica la posizione in cui si vuole effettuare
     *                il colpo
     * @param command parametro locale non utile
     *
     * @throws IOException se si verifica un errore di I/O durante l'apertura o la
     *                     lettura dello Scanner
     */
    public static void handleShot(final Scanner input, final Scanner value, final CommandType command)
            throws IOException {
        String[] inputSplit = value.nextLine().trim().split("-");
        try {
            Slot inputSlot = ShotParser.parseShot(inputSplit, gridSize.getSize(),
             VisualMap.getAlphabet().substring(0, gridSize.getSize()));
            switch (game.shot(inputSlot)) {
                case MISS -> {
                    System.out.println("\n" + VisualMap.getMapRenewed(game.getOcean()));
                    System.out.println("\n" + ShotResult.MISS.getMessage());
                }
                case HIT -> {
                    System.out.println("\n" + VisualMap.getMapRenewed(game.getOcean()));
                    System.out.println("\n" + ShotResult.HIT.getMessage());
                }
                case HIT_SUNK -> {
                    System.out.println("\n" + VisualMap.getMapRenewed(game.getOcean()));
                    System.out.println("\n" + ShotResult.HIT_SUNK.getMessage());
                }
                case NO_TIME -> {
                    System.out.println("\n" + ShotResult.NO_TIME.getMessage());
                    System.out.println(ShotResult.LOSE.getMessage());
                }
                case ALREADY_SHOT -> {
                    System.out.println("\n" + VisualMap.getMapRenewed(game.getOcean()));
                    System.out.println("\n" + ShotResult.ALREADY_SHOT.getMessage());
                }
                default -> {
                    System.out.println("riprova..");
                }
            }
        } catch (InvalidShotException e) {
            System.out.println("\nPosizione inserita non valida, riprova..");
        }
    }

    /**
     * Metodo statico che gestisce il comando per mostrare il tempo di gioco
     * trascorso.
     *
     * @param input   parametro locale non utile
     * @param value   parametro locale non utile
     * @param command parametro locale non utile
     *
     * @throws IOException se si verifica un errore di I/O durante l'apertura o la
     *                     lettura dello Scanner
     */
    public static void handleShowTime(final Scanner input, final Scanner value, final CommandType command)
            throws IOException {
        System.out.println("\nTempo totale: " + playTime.getTotalTime() + " minuti");
        System.out.println("Tempo trascorso: " + playTime.getElapsedTime() + " minuti");
        System.out.println("Tempo rimanente: " + playTime.getRemainingTime() + " minuti");
    }

    /** Metodo statico che gestisce la stampa dei file contenuti nella cartella dei salvataggi.
     * @param input parametro locale non utile
     * @param value parametro locale non utile
     * @param command parametro locale non utile
     * @throws IOException
     */
    public static void handleList(final Scanner input, final Scanner value, final CommandType command)
            throws IOException {
        if (isTherePathSaves(pathDir)) {
            List<String> files = listFilesForExt(pathDir, SAVE_EXT);
            if (!files.isEmpty()) {
                System.out.println("\nNumero di salvataggi presenti nella cartella: " + files.size());
                System.out.println("\nEcco i salvataggi:");
                for (String file: files) {
                    System.out.println("- " + file);
                }
            } else {
                System.out.println("\nNon ci sono salvataggi nella cartella 'saves'.");
            }
        } else {
            System.out.println("\n!!ERRORE cartella dei salvataggi non trovata, non puoi usare questo comando.");
        }
    }

    /** Metodo statico che gestisce il comando per caricare una partita salvata
     * conenuta nella cartella dei salvataggi.
     * @param input parametro locale non utile
     * @param value parametro che contiene il nome del file da caricare
     * @param command parametro locale non utile
     * @throws IOException
     */
    public static void handleLoad(final Scanner input, final Scanner value, final CommandType command)
            throws IOException {
        if (isTherePathSaves(pathDir)) {
            String choiceFile = value.nextLine().trim().split("\\s+")[1];
            choiceFile = choiceFile.endsWith(SAVE_EXT) ? choiceFile : choiceFile + SAVE_EXT;
            List<String> files = listFilesForExt(pathDir, SAVE_EXT);
            if (files.contains(choiceFile)) {
                Object[] save = Saver.loadObjects(pathDir + File.separator + choiceFile);
                game = (GameMap) save[0];
                playTime.setTotalTimeMillis((long) save[1]);
                System.out.println("\nSalvataggio caricato con successo, ricominciando la partita..\n");
                handlePlay(input, value, command);
            } else {
                System.out.println("\n!!ERRORE salvataggio non trovato.. Usa /list o "
                + "/lista per vedere i salvataggi disponibili.");
            }
        } else {
            System.out.println("\n!!ERRORE cartella dei salvataggi non trovata, non puoi usare questo comando.");
        }
    }

    /** Metodo che gestisce il comando per salvare la partita corrente.
     * @param input parametro locale non utile
     * @param value parametro locale non utile
     * @param command parametro locale non utile
     * @throws IOException
     */
    public static void handleSave(final Scanner input, final Scanner value, final CommandType command)
            throws IOException {
        if (isTherePathSaves(pathDir)) {
            Pattern patternName = Pattern.compile("^[a-zA-Z0-9]+$");
            List<String> files = listFilesForExt(pathDir, SAVE_EXT);
            System.out.print("\nInserisci il nome del file (solo lettere e numeri) "
            + "(non scrivere nulla se vuoi uscire dalla modalità salvataggio): ");
            String name = input.nextLine().strip();
            name = name.endsWith(SAVE_EXT) ? name.replace(SAVE_EXT, "") : name;
            while (!patternName.matcher(name).matches() || files.contains(name + SAVE_EXT)) {
                if (name.isBlank()) {
                    System.out.println("\nTornando al gioco..");
                    return;
                }
                System.out.println("\nNome di file inserito non valido oppure nome già utilizzato"
                + " per un altro salvataggio, riprova..");
                System.out.print("\nInserisci il nome del file: ");
                name = input.nextLine().strip();
            }
            boolean save = Saver.saveGame(game, playTime, name, SAVE_EXT, pathDir);
            if (save) {
                gameRunning = true;
                playTime.stop();
                System.out.println("\nOk salvataggio effettuato con successo, abbandonando la partita...");
                System.out.println("\nFatto!! Scegli un comando per continuare.");
            } else {
                System.out.println("!!ERRORE durante il salvataggio..");
            }
        } else {
            System.out.println("\n!!ERRORE cartella dei salvataggi non trovata, non puoi usare questo comando.");
        }
    }

    /** Metodo che gestisce il comando per rimuovere un salvataggio, dalla cartella dei salvataggi.
     * @param input parametro locale non utile
     * @param value parametro che contiene il nome del file da eliminare
     * @param command parametro locale non utile
     * @throws IOException
     */
    public static void handleRemove(final Scanner input, final Scanner value, final CommandType command)
            throws IOException {
        if (isTherePathSaves(pathDir)) {
            List<String> files = listFilesForExt(pathDir, SAVE_EXT);
            String fileRm = (value.nextLine().trim().split("\\s+"))[1];
            fileRm = fileRm.endsWith(SAVE_EXT) ? fileRm : fileRm + SAVE_EXT;
            if (!files.isEmpty()) {
                if (files.contains(fileRm)) {
                    File file = new File(pathDir + File.separator + fileRm);
                    boolean del = file.delete();
                    files.remove(fileRm);
                    if (del) {
                        System.out.println("\nFile " + fileRm + " eliminato con successo.");
                    } else {
                        System.out.println("\n!!ERRORE durante l'eliminazione del file " + fileRm + ".");
                    }
                } else {
                    System.out.println("\nIl file che si vuole eliminare non esiste nella cartella 'saves'.");
                }
            } else {
                System.out.println("\nNon ci sono salvataggi nella cartella 'saves'. Non"
                + " puoi rimuovere " + fileRm + " perchè non esiste o è stato spostato.");
            }
        } else {
            System.out.println("\n!!ERRORE cartella dei salvataggi non trovata, non puoi usare questo comando.");
        }
    }

    /**
     * Metodo statico che gestisce l'abbandono di una partita in corso da parte
     * dell'utente.
     *
     * @param input   Racchiude lo scanner di input passato dal main. Viene
     *                utilizzato per leggere l'input dell'utente
     * @param value   parametro locale non utile
     * @param command parametro locale non utile
     *
     * @throws IOException se si verifica un errore di I/O durante l'apertura o la
     *                     lettura dello Scanner
     */
    public static void handleGiveUp(final Scanner input, final Scanner value, final CommandType command)
            throws IOException {
        boolean handleLoop = true;
        while (handleLoop) {
            System.out.print("\nSicuro di voler abbandonare la partita? (si/no) > ");
            String choice = input.nextLine();
            choice = choice.trim();
            switch (choice.toLowerCase()) {
                case "si" -> {
                    System.out.println("\nOk! Hai deciso di abbandonare la partita.");
                    handleUnveilGrid(input, value, command);
                    System.out.println("\nScegli un comando per continuare.");
                    gameRunning = true;
                    handleLoop = false;
                    playTime.stop();
                }
                case "no" -> {
                    System.out.println("\nLa partita continua. Puoi effettuare nuovi tentativi.");
                    handleLoop = false;
                }
                default -> System.out.println("\nScelta non valida, riprova..");
            }
        }
    }
}
