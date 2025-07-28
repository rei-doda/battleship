package it.uniba.features;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

// <<entity>>

/** Questa classe definisce gli enumerativi e le relative regex che rappresenteranno
 *  i comandi richiamabili.
*/
public enum CommandType {
    START(new Pattern[] {Pattern.compile("^\\/gioca$"),
        Pattern.compile("^\\/play$")}),
    EXIT(new Pattern[] {Pattern.compile("^\\/esci$"),
        Pattern.compile("^\\/exit$")}),
    HELP(new Pattern[] {Pattern.compile("^\\/aiuto$"),
        Pattern.compile("^\\/help$")}),
    UNVEIL_GRID(new Pattern[] {Pattern.compile("^\\/svelagriglia$"),
        Pattern.compile("^\\/unveilgrid$")}),
    SHOW_GRID(new Pattern[] {Pattern.compile("^\\/mostragriglia$"),
        Pattern.compile("^\\/showgrid$")}),
    SHOW_LEVEL(new Pattern[] {Pattern.compile("^\\/mostralivello$"),
        Pattern.compile("^\\/showlevel$")}),
    SHOW_SHIPS(new Pattern[] {Pattern.compile("^\\/mostranavi$"),
        Pattern.compile("^\\/showships$")}),
    SET_EASY(new Pattern[] {Pattern.compile("^\\/facile$"),
        Pattern.compile("^\\/easy$")}),
    EASY_SET(new Pattern[] {Pattern.compile("^\\/facile\\s+(\\d+)$"),
        Pattern.compile("^\\/easy\\s+(\\d+)$")}),
    SET_MEDIUM(new Pattern[] {Pattern.compile("^\\/medio$"),
        Pattern.compile("^\\/medium$")}),
    MEDIUM_SET(new Pattern[] {Pattern.compile("^\\/medio\\s+(\\d+)$"),
        Pattern.compile("^\\/medium\\s+(\\d+)$")}),
    SET_HARD(new Pattern[] {Pattern.compile("^\\/difficile$"),
        Pattern.compile("^\\/hard$")}),
    HARD_SET(new Pattern[] {Pattern.compile("^\\/difficile\\s+(\\d+)$"),
        Pattern.compile("^\\/hard\\s+(\\d+)$")}),
    SHOW_ATTEMPTS(new Pattern[] {Pattern.compile("^\\/mostratentativi$"),
        Pattern.compile("^\\/showattempts$")}),
    ATTEMPTS(new Pattern[] {Pattern.compile("^\\/tentativi\\s+(\\d+)$"),
        Pattern.compile("^\\/attempts\\s+(\\d+)$")}),
    TIME(new Pattern[] {Pattern.compile("^\\/tempo\\s+(\\d+)$"),
        Pattern.compile("^\\/time\\s+(\\d+)$")}),
    SHOW_TIME(new Pattern[] {Pattern.compile("^\\/mostratempo$"),
        Pattern.compile("^\\/showtime$")}),
    GIVE_UP(new Pattern[] {Pattern.compile("^\\/abbandona$"),
        Pattern.compile("^\\/giveup$")}),
    SHOT(new Pattern[] {Pattern.compile("^([a-z])-(1\\d|2[0-6]|[1-9])$")}),
    B_STANDARD(new Pattern[] {Pattern.compile("^\\/standard$")}),
    B_LARGE(new Pattern[] {Pattern.compile("^\\/large$")}),
    B_X_LARGE(new Pattern[] {Pattern.compile("^\\/extralarge$")}),
    SAVE(new Pattern[] {Pattern.compile("^\\/salva$"),
        Pattern.compile("^\\/save$")}),
    LOAD(new Pattern[] {Pattern.compile("^\\/carica\\s+\\S+$"),
        Pattern.compile("^\\/load\\s+\\S+$")}),
    LIST(new Pattern[] {Pattern.compile("^\\/lista$"),
        Pattern.compile("^\\/list$")}),
    REMOVE(new Pattern[] {Pattern.compile("^\\/rm\\s+\\S+$")});

    private final Set<Pattern> compiled;

    CommandType(final Pattern[] commcompiled) throws IllegalArgumentException {
        if (commcompiled.length == 0) {
            throw new IllegalArgumentException(
                    "compiled deve contenere almeno un elemento.");
        }
        this.compiled = new HashSet<>(Arrays.asList(commcompiled));
    }

     /**
     * @return Set di stringhe che rappresenta gli alias dei comandi.
     */
    public HashSet<Pattern> getCommandPattern() {
        return new HashSet<>(this.compiled);
    }
}
