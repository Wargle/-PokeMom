package apk.tamere.projet.pokemother.metier;

import java.io.Serializable;

/**
 * Created by Alexis Arnould on 16/02/2018.
 */

public class Alarm implements Serializable {
    private int day, month, year, hour, min;
    private boolean isActive;

    public Alarm(int day, int month, int year, int hour, int min) {
        this(day, month, year, hour, min, false);
    }

    public Alarm(int day, int month, int year, int hour, int min, boolean isActive) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.min = min;
        this.isActive = isActive;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getTime() {
        return hour + ":" + String.format("%02d",  min);
    }

    public String getDate() {
        return day + "/" + month + "/" + year;
    }
}
