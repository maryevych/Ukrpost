package ua.pp.a_i.ukrpost.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by em on 09.04.2014.
 */
public class UkrpostApp extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        context=getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
