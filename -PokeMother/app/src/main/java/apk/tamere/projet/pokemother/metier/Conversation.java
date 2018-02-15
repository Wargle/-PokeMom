package apk.tamere.projet.pokemother.metier;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexis Arnould on 09/02/2018.
 */

public class Conversation extends ViewModel {
    private MutableLiveData<List<Message>> messages;

    public Conversation() {}

    public LiveData<List<Message>> getMessages() {
        if (messages == null) {
            messages = new MutableLiveData<>();
            messages.setValue(new ArrayList<Message>());
            loadMessages();
        }
        return messages;
    }

    private void loadMessages() {
        messages.getValue().add(new Message("1"));
        messages.getValue().add(new Message("2"));
    }
}
