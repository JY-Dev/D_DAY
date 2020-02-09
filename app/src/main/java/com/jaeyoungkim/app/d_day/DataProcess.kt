package com.jaeyoungkim.app.d_day

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jaeyoungkim.app.d_day.Activty.SettingActivity

class DataProcess{
    private val listType : TypeToken<MutableList<SettingActivity.DataPage>> = object : TypeToken<MutableList<SettingActivity.DataPage>>() {}
    private val gson = GsonBuilder().create()

    fun dataSave(context: Context, dataPage: MutableList<SettingActivity.DataPage>?) {
        val userLocalData = context.getSharedPreferences("datapage", Context.MODE_PRIVATE)
        val editor = userLocalData!!.edit()
        editor.clear()
        editor.commit()

// 데이터를 Json 형태로 변환

        val str = gson.toJson(dataPage,listType.type)
        editor.putString("oneMessage", str) // Json 으로 변환한 객체 저장
        editor.commit()
    }

    fun dataLoad(context: Context) : MutableList<SettingActivity.DataPage>?{
        val sp = context.getSharedPreferences("datapage", Context.MODE_PRIVATE)
        val strContact = sp.getString("oneMessage", "")
// 변환
        val datas : MutableList<SettingActivity.DataPage>? = gson.fromJson(strContact,listType.type)
        return datas
    }
}
