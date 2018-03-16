package apk.tamere.projet.pokemother.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import apk.tamere.projet.pokemother.R;
import apk.tamere.projet.pokemother.metier.Channel;
import apk.tamere.projet.pokemother.metier.Condition;
import apk.tamere.projet.pokemother.metier.Conversation;
import apk.tamere.projet.pokemother.metier.Message;
import apk.tamere.projet.pokemother.weather.*;

public class WeatherActivity extends AppCompatActivity implements WeatherServiceListener, GeocodingServiceListener, LocationListener {

    public static int GET_WEATHER_FROM_CURRENT_LOCATION = 0x00001;

    private Conversation mess;

    private ImageView weatherIconImageView;
    private TextView temperatureTextView, conditionTextView , locationTextView, weatherError, momInfo;

    private ProgressDialog loadingDialog;

    private WeatherService weatherService;
    private GeocodingService geocodingService;

    private LocationManager locationManager;
    //private boolean isNetworkEnabled, isGPSEnabled;

    private SharedPreferences preferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("weather", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        /*isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);*/

        weatherIconImageView = findViewById(R.id.weatherIconImageView);
        temperatureTextView = findViewById(R.id.temperatureTextView);
        conditionTextView = findViewById(R.id.conditionTextView);
        locationTextView = findViewById(R.id.locationTextView);

        weatherError = findViewById(R.id.weatherError);
        momInfo = findViewById(R.id.momMess);

        weatherService = new WeatherService(this);
        //weatherService.setTemperatureUnit("C");

        geocodingService = new GeocodingService(this);

        mess = ViewModelProviders.of(this).get(Conversation.class);
    }

    @Override
    protected void onStart() {
        Log.d("weather", "onStart");
        super.onStart();

        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage(getString(R.string.loading));
        loadingDialog.setCancelable(false);
        loadingDialog.show();

        String location = null;

        String locationCache = preferences.getString(getString(R.string.pref_cached_location), null);

        if (locationCache == null)
            getWeatherFromCurrentLocation();
        else
            location = locationCache;

        if (location != null)
            weatherService.refreshWeather(location);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("weather", "onCreateOptionMenu");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("weather", "onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.currentLocation:
                loadingDialog.show();
                getWeatherFromCurrentLocation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getWeatherFromCurrentLocation() {
        Log.d("weather", "getWeatherFromCurrentLocation");

        boolean isNetworkEnabled, isGPSEnabled;
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
            }, GET_WEATHER_FROM_CURRENT_LOCATION);

            return;
        }

        Criteria locationCriteria = new Criteria();

        if (isNetworkEnabled)
            locationCriteria.setAccuracy(Criteria.ACCURACY_COARSE);
        else if (isGPSEnabled)
            locationCriteria.setAccuracy(Criteria.ACCURACY_FINE);

        locationManager.requestSingleUpdate(locationCriteria, this, null);
    }

    private String getIconByCode(int code) {
        int pic = getResources().getIdentifier("c" + code, "string", getPackageName());
        return getString(pic);
    }

    @Override
    public void serviceSuccess(Channel channel) {

        Log.d("weather", "serviceSucces");
        loadingDialog.hide();

        weatherError.setText("");

        Condition condition = channel.getCondition();
        //Units units = channel.getUnits();

        int weatherIconImageResource = getResources().getIdentifier("icon_weather_" + getIconByCode(condition.getCode()), "drawable", getPackageName());
        //Log.d("weather", "condition code:: " + condition.getCode());

        int id = getResources().getIdentifier("weather_info_" + getIconByCode(condition.getCode()), "string", getPackageName());
        String info = getResources().getString(id);
        momInfo.setText(info);

        weatherIconImageView.setImageResource(weatherIconImageResource);
        temperatureTextView.setText(getString(R.string.format_temperature_output, condition.getTemperature(), channel.getUnit()));
        conditionTextView.setText(condition.getDescription());
        locationTextView.setText(channel.getLocation());


        //cacheService.save(channel);
    }

    @Override
    public void serviceFailure(Exception exception) {
        Log.d("weather", "serviceFailure");
        Log.e("weather", exception.getMessage());
        loadingDialog.hide();

        //Toast.makeText(this, R.string.no_connection, Toast.LENGTH_LONG).show();
        weatherError.setText(exception.getMessage());
    }

    @Override
    public void geocodeSuccess(String location) {
        Log.d("weather", "geocodeSuccess");
        weatherService.refreshWeather(location);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(getString(R.string.pref_cached_location), location);
        editor.apply();
    }

    @Override
    public void geocodeFailure(Exception exception) {
        Log.d("weather", "geocodeFailure");
        //cacheService.load(this);
        loadingDialog.hide();
        //Toast.makeText(this, R.string.no_connection, Toast.LENGTH_LONG).show();
        weatherError.setText(exception.getMessage());
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("weather", "onLocationChanged");
        geocodingService.refreshLocation(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        // OPTIONAL: implement your custom logic here
    }

    @Override
    public void onProviderEnabled(String s) {
        // OPTIONAL: implement your custom logic here
    }

    @Override
    public void onProviderDisabled(String s) {
        // OPTIONAL: implement your custom logic here
    }
}
