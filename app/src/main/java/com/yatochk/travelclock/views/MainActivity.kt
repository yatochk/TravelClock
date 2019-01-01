package com.yatochk.travelclock.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yatochk.travelclock.R
import com.yatochk.travelclock.presenters.ViewsPresenter

class MainActivity : AppCompatActivity() {
    private val presenter: ViewsPresenter = ViewsPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter
    }
}