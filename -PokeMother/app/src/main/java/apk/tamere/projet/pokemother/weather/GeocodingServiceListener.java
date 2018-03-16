package apk.tamere.projet.pokemother.weather;

public interface GeocodingServiceListener {
    void geocodeSuccess(String location);

    void geocodeFailure(Exception exception);
}
