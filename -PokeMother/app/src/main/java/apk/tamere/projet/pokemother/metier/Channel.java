package apk.tamere.projet.pokemother.metier;

import org.json.JSONException;
import org.json.JSONObject;

public class Channel implements IJsonParser {
    private String unit;
    private Condition condition;
    private String location;

    public String getUnit() { return unit; }

    public Condition getCondition() { return condition; }

    public String getLocation() {
        return location;
    }

    @Override
    public void parse(JSONObject data) {

        unit = data.optJSONObject("units").optString("temperature");

        condition = new Condition();
        condition.parse(data.optJSONObject("item").optJSONObject("condition"));

        JSONObject locationData = data.optJSONObject("location");
        String region = locationData.optString("region");
        String country = locationData.optString("country");
        location = String.format("%s, %s", locationData.optString("city"), (region.length() != 0 ? region : country));
    }

    @Override
    public JSONObject toJson() {
        JSONObject data = new JSONObject();
        try {
            data.put("units", new JSONObject().put("temperature", unit));
            data.put("condition", condition.toJson());
            data.put("requestLocation", location);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

}
