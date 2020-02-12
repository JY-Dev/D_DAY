package com.jaeyoungkim.app.d_day.Activty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.jaeyoungkim.app.d_day.*
import kotlinx.android.synthetic.main.activity_show.*

class ShowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val pagerAdapter = PageShowAdapter(this)
        viewPager.adapter = pagerAdapter
        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        if (pagerAdapter.count == 0){
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        val dataMul = DataProcess().dataLoad(this)
        dataMul?.forEachIndexed { position, dataPage ->
            val dday = Format().dday(dataPage.calMil,dataPage.selRepeat)
            Noti().RunNotification(this,position,dataPage.selToogle,dataPage.title,dataPage.calMil,dday)
        }
    }
}
