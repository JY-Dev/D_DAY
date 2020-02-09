package com.jaeyoungkim.app.d_day.Activty

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jaeyoungkim.app.d_day.PageShowAdapter
import com.jaeyoungkim.app.d_day.R
import kotlinx.android.synthetic.main.activity_show.*

class ShowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        val pagerAdapter = PageShowAdapter(this)
        viewPager.adapter = pagerAdapter
    }
}
