package com.jaeyoungkim.app.d_day

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class Format{

    //yyyy년 MM월 dd일
    fun dateFormat1(cal : Calendar) : String{
        val dateFormat = SimpleDateFormat("yyyy년 M월 d일") //날짜포맷
        return dateFormat.format(cal.time)
    }

    fun dateFormat1(cal : Long) : String{
        val dateFormat = SimpleDateFormat("yyyy년 M월 d일") //날짜포맷
        return dateFormat.format(Date(cal))
    }

    fun dday(cal: Long) : Long{
        var dday = 0L
        dday = TimeUnit.MILLISECONDS.toDays(cal) - TimeUnit.MILLISECONDS.toDays(Calendar.getInstance().timeInMillis)

        return dday
    }

}