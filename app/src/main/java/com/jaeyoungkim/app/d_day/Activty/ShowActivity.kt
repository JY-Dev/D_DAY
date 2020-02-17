package com.jaeyoungkim.app.d_day.Activty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.jaeyoungkim.app.d_day.*
import kotlinx.android.synthetic.main.activity_show.*

class ShowActivity : AppCompatActivity() {
    private var backKeyPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val pagerAdapter = PageShowAdapter(this)
        viewPager.adapter = pagerAdapter
        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        line_indicator.setupWithViewPager(viewPager)
        if (pagerAdapter.count == 0){
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

    }

    override fun onBackPressed() {

            if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis()
                Toast.makeText(this, "'뒤로가기' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
                return
            }
            if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                finish()
            }
        }


}
