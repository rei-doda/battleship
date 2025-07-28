package it.uniba.features;

// <<entity>>

/**
 * Questa classe implementa un timer che tiene traccia del tempo trascorso e del tempo rimanente.
 */
public enum PlayTime {

    /**
     * A singleton class that represents a timer.
     */
    INSTANCE;

    private static final int MILLIS_PER_SECOND = 1000;
    private static final int TOTAL_SECONDS = 1200;
    private static final int SECONDS_PER_MINUTE = 60;
    public static final int MINUTES_PER_GAME = 20;

    private long totalTime = TOTAL_SECONDS;
    private long startTime;
    private boolean running;
    private Thread timerThread;

    PlayTime() {
    }

    /**
     * Metodo per ottenere i minuti per partita.
     *
     * @return i minuti per partita
     */
    public int getMinutesPerGame() {
        return MINUTES_PER_GAME;
    }

    /**
     * Metodo per impostare il tempo totale.
     *
     * @param minutes i minuti per partita
     */
    public void setTotalTime(final long minutes) {
        totalTime = minutes * SECONDS_PER_MINUTE; // Convertire i minuti in secondi
        startTime = System.currentTimeMillis();
    }

    /**
     * Metodo per impostare il tempo totale di gioco in millisecondi.
     * @param millis il tempo totale di gioco in millisecondi
     */
    public void setTotalTimeMillis(final long millis) {
        totalTime = millis / MILLIS_PER_SECOND; // Convertire millisecondi in secondi
        startTime = System.currentTimeMillis();
    }

    /**
     * Metodo per ottenere il tempo rimanente.
     *
     * @return il tempo rimanente
     */
    public String getRemainingTime() {
        long remainingTime = totalTime - getElapsedTimeInSeconds();
        long minutes = remainingTime / SECONDS_PER_MINUTE;
        long seconds = remainingTime % SECONDS_PER_MINUTE;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * Metodo per ottenere il tempo rimanente in millisecondi.
     *
     * @return il tempo rimanente in millisecondi
     */
    public long getRemainingTimeInMillis() {
        return (totalTime - getElapsedTimeInSeconds()) * MILLIS_PER_SECOND;
    }

    /**
     * Metodo per ottenere il tempo totale.
     *
     * @return il tempo totale
     */
    public String getTotalTime() {
        long minutes = totalTime / SECONDS_PER_MINUTE;
        long seconds = totalTime % SECONDS_PER_MINUTE;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * Metodo per ottenere il tempo trascorso.
     *
     * @return il tempo trascorso
     */
    public String getElapsedTime() {
        long elapsedSeconds = getElapsedTimeInSeconds();
        long minutes = elapsedSeconds / SECONDS_PER_MINUTE;
        long seconds = elapsedSeconds % SECONDS_PER_MINUTE;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private long getElapsedTimeInSeconds() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        return elapsedTime / MILLIS_PER_SECOND; // Convertire i millisecondi in secondi
    }

    /**
     * Metodo per avviare il timer.
     */
    public void start() {
        if (!running && timerThread == null) {
            startTime = System.currentTimeMillis();
            running = true;
            timerThread = new Thread(new TimerRunnable());
            timerThread.start();
        }
    }

    /**
     * Metodo per fermare il timer.
     */
    public void stop() {
        running = false;
        if (timerThread != null) {
            timerThread.interrupt();
            timerThread = null;
        }
    }

    /**
     * Metodo per verificare se il timer è in esecuzione.
     *
     * @return true se il timer è in esecuzione, false altrimenti
     */
    public boolean isRunning() {
        return getRemainingTimeInMillis() > 0;
    }

    /**
     * Classe interna che implementa il timer.
     */
    private class TimerRunnable implements Runnable {
        @Override
        public void run() {
            while (running) {
                try {
                    Thread.sleep(MILLIS_PER_SECOND);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
