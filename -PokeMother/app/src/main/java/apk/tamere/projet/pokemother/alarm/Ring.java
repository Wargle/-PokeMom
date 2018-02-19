package apk.tamere.projet.pokemother.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import apk.tamere.projet.pokemother.R;
import apk.tamere.projet.pokemother.activity.AlarmActivity;
import apk.tamere.projet.pokemother.activity.AlarmAddActivity;

public class Ring extends Service {

    private static boolean isRunning;
    private static MediaPlayer mMediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        final NotificationManager notifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent intentNotif = new Intent(this.getApplicationContext(), AlarmActivity.class);
        PendingIntent pIntentNotif = PendingIntent.getActivity(this, 0, intentNotif, 0);

        Notification notifyAlarm  = new Notification.Builder(this)
                .setContentTitle(getResources().getString(R.string.notif_title_wakeUp))
                .setContentText(getResources().getString(R.string.notif_content_wakeUp))
                .setSmallIcon(R.drawable.ic_access_alarm_black_24dp)
                .setContentIntent(pIntentNotif)
                .setVibrate(new long[] { 500, 450 })
                .setAutoCancel(true)
                .build();

        String action = intent.getExtras().getString(AlarmAddActivity.ACTION);

        switch (action) {
            case AlarmAddActivity.ACTION_LAUNCH :
                if(!isRunning) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.acdc_highway);
                    mMediaPlayer.start();
                    if (notifManager != null) {
                        notifManager.notify(0, notifyAlarm);
                    }

                    isRunning = true;
                }
                break;
            case AlarmAddActivity.ACTION_STOP :
                stop();
                break;
            default:
                stop();
                break;
        }
        return START_NOT_STICKY;
    }

    public static void stop() {
        if(isRunning) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();

            isRunning = false;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.isRunning = false;
    }
}