package apk.tamere.projet.pokemother.metier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import apk.tamere.projet.pokemother.App;
import apk.tamere.projet.pokemother.R;

/**
 * Created by Alexis Arnould on 16/02/2018.
 */

public class Alarm implements Serializable {
    private int day, month, year, hour, min;
    private boolean isActive;

    private List<Integer> repeats = new ArrayList<>();

    public Alarm(int day, int month, int year, int hour, int min) {
        this(day, month, year, hour, min, false);
    }

    public Alarm(int day, int month, int year, int hour, int min, boolean isActive) {
        this.day = day;
        this.month = month + 1;
        this.year = year;
        this.hour = hour;
        this.min = min;

        this.isActive = isActive;
    }

    public List<Integer> getRepeats() {
        return repeats;
    }

    public void setRepeats(List<Integer> repeats) {
        this.repeats = repeats;
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
        return String.format("%02d",  day) + "/" + String.format("%02d",  month) + "/" + year;
    }

    public String getRepeatsText() {
        if(repeats == null || repeats.size() == 0)
            return App.getContext().getString(R.string.utils_none);

        String text = "";
        String corres[] = { App.getContext().getString(R.string.cbTextD),
                App.getContext().getString(R.string.cbTextL),
                App.getContext().getString(R.string.cbTextMa),
                App.getContext().getString(R.string.cbTextMe),
                App.getContext().getString(R.string.cbTextJ),
                App.getContext().getString(R.string.cbTextV),
                App.getContext().getString(R.string.cbTextS),
        };

        for (Integer r : repeats)
            text += corres[r - 1] + "\t";
        return text;
    }
}
