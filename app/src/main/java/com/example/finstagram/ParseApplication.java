package com.example.finstagram;

import android.app.Application;

import com.parse.Parse;

public  class ParseApplication extends Application {

    // Installs Parse SDK
    @Override
    public void onCreate() {
        super.onCreate();


        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("PrizTK5goSwWNSCu1u0O11OCiHHB5JVlWjIFfakC")
                .clientKey("4V2jAjQL3Aw9SIaQJ07iMLO0MFKqi93dePmFkGii")
                .server("https://parseapi.back4app.com")
                .build());
    }
}
