package apk.tamere.projet.pokemother.weather;

import apk.tamere.projet.pokemother.metier.Channel;

public interface WeatherServiceListener {
    void serviceSuccess(Channel channel);

    void serviceFailure(Exception exception);
}
