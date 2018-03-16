package apk.tamere.projet.pokemother.metier;

import org.json.JSONObject;

public interface IJsonParser {
    void parse(JSONObject data);

    JSONObject toJson();
}
