package com.ergunozbudakli.cakmagram;

import android.app.Application;

import com.parse.Parse;

public class ParseStarterActivity extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
        Parse.initialize(new Parse.Configuration.Builder(this)

                        .clientKey("gp7Tab9ThFgSxFrlpfRmtgMux7PoICggdc94Os1c")
                        .server("https://parseapi.back4app.com/")
                        .applicationId("UL3WpCQfid7Fh0YLQ2wUg225ZvdSZyR0b1ZOTpcg")
                        .build()
        );
    }
}
