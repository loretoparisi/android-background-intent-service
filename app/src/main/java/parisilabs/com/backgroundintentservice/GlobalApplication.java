package parisilabs.com.backgroundintentservice;

import android.app.Application;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * Created by admin on 13/01/16.
 */
public class GlobalApplication extends Application {

    @Override
    public void onCreate()
    {
        super.onCreate();

        // Initialize the singletons so their instances
        // are bound to the application process.
        initSingletons();
    }

    protected void initSingletons()
    {
        // Initialize the instance of MySingleton
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

    }

}
