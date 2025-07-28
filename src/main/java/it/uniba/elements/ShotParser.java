package it.uniba.elements;

import it.uniba.exceptions.InvalidShotException;

// <<boundary>> Questa classe definisce il parser per convertire la stringa inserita dall'utente di un slot.

/**
 * Questa classe definisce il parser per le coordinate di un colpo.
 */
public final class ShotParser {
    private ShotParser() { }
    /**
     * Il metodo restituisce la cella corrispondente alle coordinate inserite.
     * @param inputString
     * @param gridSize
     * @param alphabet
     * @return
     * @throws InvalidShotException
     */
    public static Slot parseShot(final String[] inputString, final Short gridSize, final String alphabet)
     throws InvalidShotException {
        short col = (short) alphabet.indexOf(inputString[0].toUpperCase());
        short row = Short.parseShort(inputString[1]);
        if (col == -1) {
            throw new InvalidShotException(alphabet + " non contiene " + inputString[0]);
        } else if (row > gridSize || row <= 0) {
            throw new InvalidShotException("La riga inserita non è valida per la dimensione della griglia");
        }
        row = (short) ((gridSize - row));

        return new Slot(row, col);
    }
}
