package com.yatochk.travelclock.model

class Settings private constructor() {

    var alarmVolume = 0.5f
    var distanceToAlarm = 500

    companion object {
        val instance = Settings()
    }
}
