package apk.tamere.projet.pokemother.metier;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

import apk.tamere.projet.pokemother.App;
import apk.tamere.projet.pokemother.R;

/**
 * Created by Alexis Arnould on 09/02/2018.
 */

public class Conversation extends ViewModel {
    private MutableLiveData<List<Message>> messages;
    private ISaveLoad<Message> io;

    public static final String FILE_SAVE_NAME = "momConv.json";

    public Conversation() {
        io = new SaveLoadMessageJSon();
    }

    public LiveData<List<Message>> getMessages() {
        if (messages == null) {
            messages = new MutableLiveData<>();
            messages.setValue(new ArrayList<Message>());
            loadMessages(FILE_SAVE_NAME);

            messages.getValue().add(0, new Message(App.getContext().getString(R.string.momHello), true));
        }
        return messages;
    }

    public void saveMessages(String path) {
        io.save(path, messages.getValue());
    }

    private void loadMessages(String path) {
        messages.getValue().addAll(io.load(path));
    }
}
