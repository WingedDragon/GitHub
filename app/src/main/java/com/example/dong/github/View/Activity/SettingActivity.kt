package com.example.dong.github.View.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.example.dong.github.R

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_setting)
    }
}
