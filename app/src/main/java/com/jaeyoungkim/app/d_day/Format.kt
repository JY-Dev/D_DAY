package com.jaeyoungkim.app.d_day

import android.widget.TextView
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

    fun dday(cal: Long , selRepeat : String) : Long{
        var dday = 0L
        var ddayCheck = 0L
        var checkYearFlag = 0
        var checkMonthFlag = 0
        val curCal = Calendar.getInstance()
        val preCal = Calendar.getInstance()

        preCal.timeInMillis = cal
        ddayCheck = TimeUnit.MILLISECONDS.toDays(preCal.timeInMillis) - TimeUnit.MILLISECONDS.toDays(Calendar.getInstance().timeInMillis)
        when(selRepeat){
            "반복없음" -> { }
            "매년" -> {
                if (ddayCheck != 0L) {
                    checkYearFlag = when {
                        preCal.get(Calendar.MONTH) > curCal.get(Calendar.MONTH) -> 0
                        preCal.get(Calendar.MONTH) == curCal.get(Calendar.MONTH) -> {
                            if(preCal.get(Calendar.DAY_OF_MONTH) >= curCal.get(Calendar.DAY_OF_MONTH)) 0
                            else 1
                        }
                        else -> 1
                    }
                    preCal.set(Calendar.YEAR,curCal.get(Calendar.YEAR)+checkYearFlag)
                }

            }
            "매월" -> {
                if (ddayCheck != 0L){
                    preCal.set(Calendar.YEAR,curCal.get(Calendar.YEAR))
                    checkMonthFlag = when {
                        preCal.get(Calendar.DAY_OF_MONTH) > curCal.get(Calendar.DAY_OF_MONTH) -> 0
                        preCal.get(Calendar.DAY_OF_MONTH) == curCal.get(Calendar.DAY_OF_MONTH) -> 0
                        else -> 1
                    }
                    preCal.set(Calendar.MONTH,curCal.get(Calendar.MONTH)+checkMonthFlag)
                }
            }
        }
        dday = TimeUnit.MILLISECONDS.toDays(preCal.timeInMillis) - TimeUnit.MILLISECONDS.toDays(Calendar.getInstance().timeInMillis)
        return dday
    }

    fun ddayCheck(dday : Long,textView: TextView) : Long{
        if (dday < 0) {
            textView.text = "+"
            return Math.abs(dday)
        } else textView.text = "-"
        return dday
    }

    fun ddayCheck(dday : Long) : Long{
        if (dday < 0) return Math.abs(dday)
        return dday
    }

}