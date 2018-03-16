package apk.tamere.projet.pokemother.weather;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class GeocodingService {
    private GeocodingServiceListener listener;
    private Exception error = new Exception("Geocode:: No information...");

    public GeocodingService(GeocodingServiceListener listener) {
        this.listener = listener;
    }

    @SuppressLint("StaticFieldLeak")
    public void refreshLocation(Location location) {
        new AsyncTask<Location, Void, String>() {
            @Override
            protected String doInBackground(Location... locations) {

                Location location = locations[0];

                String endpoint = String.format("https://maps.googleapis.com/maps/api/geocode/json?latlng=%s,%s&key=%s", location.getLatitude(), location.getLongitude(), API_KEY);

                try {
                    URL url = new URL(endpoint);

                    URLConnection connection = url.openConnection();
                    connection.setUseCaches(false);

                    InputStream inputStream = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    reader.close();

                    JSONObject data = new JSONObject(result.toString());
                    JSONArray results = data.optJSONArray("results");

                    if (results.length() == 0) {
                        error = new ReverseGeolocationException("Could not reverse geocode " + location.getLatitude() + ", " + location.getLongitude());
                        return null;
                    }

                    return results.optJSONObject(0).optString("formatted_address");

                }
                catch (Exception e) {
                    error = e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(String location) {
                if (location == null) {
                    Log.e("weather", "service geocode failure");
                    listener.geocodeFailure(error);
                }
                else {
                    Log.i("weather", "service geocode success");
                    listener.geocodeSuccess(location);
                }

            }

        }.execute(location);
    }

    private static final String API_KEY = "";

    private class ReverseGeolocationException extends Exception {
        public ReverseGeolocationException(String detailMessage) {
            super(detailMessage);
        }
    }
}
