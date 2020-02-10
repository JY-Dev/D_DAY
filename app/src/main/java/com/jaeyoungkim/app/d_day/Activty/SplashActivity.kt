package com.jaeyoungkim.app.d_day.Activty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.jaeyoungkim.app.d_day.DataProcess
import com.jaeyoungkim.app.d_day.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            if (DataProcess().dataLoad(this)!=null) startActivity(Intent(this,
                ShowActivity::class.java))
            else startActivity(Intent(this, MainActivity::class.java))
           finish()
        },1000)
    }
}
