package apk.tamere.projet.pokemother.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import apk.tamere.projet.pokemother.activity.AlarmActivity;
import apk.tamere.projet.pokemother.activity.AlarmAddActivity;

/**
 * Created by Alexis Arnould on 15/02/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getExtras().getString(AlarmAddActivity.ACTION);

        Intent serviceIntent = new Intent(context, Ring.class);
        serviceIntent.putExtra(AlarmAddActivity.ACTION, action);

        context.startService(serviceIntent);
    }
}
