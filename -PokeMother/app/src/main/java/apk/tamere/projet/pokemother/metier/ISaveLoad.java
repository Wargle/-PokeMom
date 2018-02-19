package apk.tamere.projet.pokemother.metier;

import java.util.List;

/**
 * Created by Alexis Arnould on 19/02/2018.
 */

public interface ISaveLoad<T> {
    public void save(String name, List<T> o);
    public List<T> load(String name);
}
