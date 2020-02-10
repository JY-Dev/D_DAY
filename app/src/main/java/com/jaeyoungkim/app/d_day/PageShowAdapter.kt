package com.jaeyoungkim.app.d_day

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.v4.view.PagerAdapter
import android.view.View
import com.jaeyoungkim.app.d_day.Activty.MainActivity
import com.jaeyoungkim.app.d_day.Activty.SettingActivity
import com.jaeyoungkim.app.d_day.Activty.ShowActivity
import com.jaeyoungkim.app.d_day.Dialog.MenuDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.graphics.BitmapFactory
import android.app.PendingIntent
import android.support.v4.content.ContextCompat.getSystemService
import android.widget.*


class PageShowAdapter(context: Context) : PagerAdapter() {


    // LayoutInflater 서비스 사용을 위한 Context 참조 저장.
    private var mContext: Context = context
    private var settingData = SettingActivity()

    // Context를 전달받아 mContext에 저장하는 생성자 추가.


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var dataMul = DataProcess().dataLoad(mContext)
        var view: View? = null
        var dday = 0L
            // LayoutInflater를 통해 "/res/layout/page.xml"을 뷰로 생성.
            val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.pageshow_layout, container, false)
            val addPageBtn = view.findViewById<ImageButton>(R.id.add_page_btn)
            val menuBtn = view.findViewById<ImageButton>(R.id.menu_btn)
            val notiBtn = view.findViewById<Button>(R.id.noti_btn)
            val tv1 = view.findViewById<TextView>(R.id.title_tv)
            val tv2 = view.findViewById<TextView>(R.id.seldate_tv)
            val tv_dday_sign = view.findViewById<TextView>(R.id.d_day_tv_sign)
            val tv_dday = view.findViewById<TextView>(R.id.d_day_tv)
            val img = view.findViewById<ImageView>(R.id.img_layout)

            if (dataMul!=null){
                dday = Format().dday(dataMul[position].calMil,dataMul[position].selRepeat)
                tv1.text = dataMul[position].title
                tv2.text = Format().dateFormat1(dataMul[position].calMil)
                tv_dday.text = if (dday!=0L) Format().ddayCheck(dday,tv_dday_sign).toString() else "DAY"
                img.setImageURI(Uri.parse(dataMul[position].imgUrl))
            }

            notiBtn.setOnClickListener {
                Noti().NotificationSomethings(mContext)
            }
            addPageBtn.setOnClickListener {
                val intent = Intent(mContext, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                mContext.startActivity(intent)
            }
            menuBtn.setOnClickListener {
                MenuDialog(mContext,{
                    //수정콜백
                    val intent = Intent(mContext,MainActivity::class.java)
                    intent.putExtra("modify",position)
                    mContext.startActivity(intent)
                },{
                    //삭제콜백
                    dataMul?.removeAt(position)
                    DataProcess().dataSave(mContext,dataMul)
                    val intent = Intent(mContext, ShowActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    mContext.startActivity(intent)
                })
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