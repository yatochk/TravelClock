package com.yatochk.travelclock;

public class Settings {
    private static final Settings instance = new Settings();

    public float alarmVolume = 0.5f;
    public int distanceToAlarm = 500;

    public static Settings getInstance() {
        return instance;
    }

    private Settings() {
    }
}
