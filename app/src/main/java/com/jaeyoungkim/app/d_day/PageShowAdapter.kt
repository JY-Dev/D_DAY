package com.jaeyoungkim.app.d_day

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.v4.view.PagerAdapter
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.jaeyoungkim.app.d_day.Activty.MainActivity
import com.jaeyoungkim.app.d_day.Activty.SettingActivity


class PageShowAdapter(context: Context) : PagerAdapter() {


    // LayoutInflater 서비스 사용을 위한 Context 참조 저장.
    private var mContext: Context = context
    private var settingData = SettingActivity()

    // Context를 전달받아 mContext에 저장하는 생성자 추가.


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var dataMul = DataProcess().dataLoad(mContext)
        var view: View? = null
            // LayoutInflater를 통해 "/res/layout/page.xml"을 뷰로 생성.
            val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.pageshow_layout, container, false)
            val addPageBtn = view.findViewById<ImageButton>(R.id.add_page_btn)
            val tv1 = view.findViewById<TextView>(R.id.title_tv)
            val tv2 = view.findViewById<TextView>(R.id.seldate_tv)
            val tv_dday = view.findViewById<TextView>(R.id.d_day_tv)
            val img = view.findViewById<ImageView>(R.id.img_layout)
            if (dataMul!=null){
                tv1.text = dataMul[position].title
                tv2.text = Format().dateFormat1(dataMul[position].calMil)
                tv_dday.text = Format().dday(dataMul[position].calMil).toString()
                img.setImageURI(Uri.parse(dataMul[position].imgUrl))
            }

            addPageBtn.setOnClickListener {
                mContext.startActivity(Intent(mContext, MainActivity::class.java))
            }
        // 뷰페이저에 추가.
        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        // 뷰페이저에서 삭제.
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        // 전체 페이지 수는 10개로 고정.
        return DataProcess().dataLoad(mContext)!!.size
    }

    override fun isViewFromObject(view: View, ob : Any): Boolean {
        return view == ob
    }

}