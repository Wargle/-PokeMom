package apk.tamere.projet.pokemother.metier;

import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexis Arnould on 19/02/2018.
 */

public class SaveLoadMessageJSon implements ISaveLoad<Message> {
    @Override
    public void save(String path, List<Message> o) {
        File file;
        FileWriter writer;
        try {
            Gson gson = new Gson();
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), path);
            int start = o.size() - 23;
            if (start < 0) start = 0;

            writer = new FileWriter(file);
            writer.write(gson.toJson(o.subList(start, o.size())));
            writer.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Message> load(String path) {
        List<Message> messages = null;
        File file;
        FileReader reader;
        try {
            Gson gson = new Gson();
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), path);
            reader = new FileReader(file);
            int c;

            String temp = "";
            while((c = reader.read()) != -1){
                temp = temp + Character.toString((char)c);
            }

            messages = gson.fromJson(temp, new TypeToken<List<Message>>(){}.getType());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if(messages == null) messages = new ArrayList<>();
        return messages;
    }
}
