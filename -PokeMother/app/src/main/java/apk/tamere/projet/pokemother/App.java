package apk.tamere.projet.pokemother;

import android.app.Application;
import android.content.Context;

/**
 * Created by Alexis Arnould on 05/03/2018.
 */

public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
