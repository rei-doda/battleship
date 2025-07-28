package it.uniba.elements;

import it.uniba.features.PlayTime;
import it.uniba.interfaces.Field;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// <boundary>

/**
 * Classe statica che si occupa di salvare e caricare il gioco.
 */
public final class Saver {
    private Saver() { }
    /** Metodo statico che salva il gioco.
     * @param game Oggetto di tipo Field.
     * @param timer Oggetto di tipo PlayTime.
     * @param fileName Nome del file.
     * @param fileExt Estensione del file.
     * @param pathDir Percorso della directory dedicata al salvataggio.
     * @return true se il salvataggio è andato a buon fine, false altrimenti.
     */
    public static boolean saveGame(final Field game, final PlayTime timer, final String fileName, final String fileExt,
            final String pathDir) {
        ObjectOutputStream writer;
        boolean result = false;
        try {
            FileOutputStream fileOutput = new FileOutputStream(pathDir + File.separator + fileName + fileExt);
            writer = new ObjectOutputStream(fileOutput);
            writer.writeObject(game);
            writer.writeLong(timer.getRemainingTimeInMillis());
            writer.close();
            fileOutput.close();
            result = true;
        } catch (IOException e) {
            result = false;
        }
        return result;
    }

    /**
     * Metodo statico che carica il gioco.
     * @param pathFile Percorso del file da caricare.
     * @return Array di oggetti contenente il campo di gioco e il tempo di gioco rimanente.
     */
    public static Object[] loadObjects(final String pathFile) {
        ObjectInputStream reader;
        Object[] oggetti = new Object[2];
        try {
            FileInputStream fileInputStream = new FileInputStream(pathFile);
            reader = new ObjectInputStream(fileInputStream);
            oggetti[0] = reader.readObject();
            oggetti[1] = reader.readLong();
            reader.close();
            fileInputStream.close();
            return oggetti;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
