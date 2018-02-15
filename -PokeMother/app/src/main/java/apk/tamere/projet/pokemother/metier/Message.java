package apk.tamere.projet.pokemother.metier;

import java.io.Serializable;

/**
 * Created by Alexis Arnould on 09/02/2018.
 */

public class Message implements Serializable {
    private String message;
    private boolean isMom;

    public Message(String mess) {
        this(mess, true);
    }
    public Message(String mess, boolean isMom) {
        message = mess;
        this.isMom = isMom;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isMom() {
        return isMom;
    }
}
