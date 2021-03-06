package com.jaeyoungkim.app.d_day.Activty

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.jaeyoungkim.app.d_day.Dialog.DatePickerDialog
import com.jaeyoungkim.app.d_day.Format
import com.jaeyoungkim.app.d_day.R
import com.jaeyoungkim.app.d_day.Tv_func
import java.util.*


class MainActivity : AppCompatActivity() {

    private val cal : Calendar = Calendar.getInstance() // 캘린더 설정
    private var toggleData = false
    private var selRepeat = ""
    private lateinit var tv_func : Tv_func

    private val format = Format()
    private lateinit var imm : InputMethodManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        tv_func = Tv_func()

        //초기셋팅
        init()
        //데이트피커 클릭리스너
        datepicker_tv.setOnClickListener {
//            datePicker().show()
            DatePickerDialog(this) { year, month, day ->  setDate(year,month,day)
            }
            imm.hideSoftInputFromWindow(title_tv.windowToken, 0)
        }

        next_btn.setOnClickListener {
            if (title_tv.text.toString()!=""&&datepicker_tv.text.toString()!=resources.getString(R.string.input_date)){
                val intent = Intent(this, SettingActivity::class.java)
                intent.putExtra("title",title_tv.text.toString())
                intent.putExtra("selRepeat",selRepeat)
                intent.putExtra("toggleData",toggleData)
                intent.putExtra("cal",cal.timeInMillis)
                startActivity(intent)
            } else {
                if (title_tv.text.toString()==""&&datepicker_tv.text.toString()==resources.getString(R.string.input_date)) Toast.makeText(this,resources.getString(R.string.no_title_date),Toast.LENGTH_SHORT).show()
                else if(title_tv.text.toString() =="") Toast.makeText(this,resources.getString(R.string.no_title),Toast.LENGTH_SHORT).show()
                else Toast.makeText(this,resources.getString(R.string.no_date),Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun init(){
        tv_func.title_tv_maxline(title_tv)
        cal.set(Calendar.DAY_OF_MONTH,cal.get(Calendar.DAY_OF_MONTH)+1)
        //텍스트색 초기화
        no_repeat_tv.isSelected = true
        every_year_tv.isSelected = false
        every_month_tv.isSelected = false


        selRepeat = no_repeat_tv.text.toString()

        //반복없음 리스너
        no_repeat_tv.setOnClickListener {
            no_repeat_tv.isSelected = true
            every_year_tv.isSelected = false
            every_month_tv.isSelected = false
            selRepeat = no_repeat_tv.text.toString()
            imm.hideSoftInputFromWindow(title_tv.windowToken, 0)
        }

        //매년 리스너
        every_year_tv.setOnClickListener {
            no_repeat_tv.isSelected = false
            every_year_tv.isSelected = true
            every_month_tv.isSelected = false
            selRepeat = every_year_tv.text.toString()
            imm.hideSoftInputFromWindow(title_tv.windowToken, 0)
        }

        //매월 리스너
        every_month_tv.setOnClickListener {
            no_repeat_tv.isSelected = false
            every_year_tv.isSelected = false
            every_month_tv.isSelected = true
            selRepeat = every_month_tv.text.toString()
            imm.hideSoftInputFromWindow(title_tv.windowToken, 0)
        }

        toogle_btn.setOnClickListener {
            toggleData = toogle_btn.isChecked
            imm.hideSoftInputFromWindow(title_tv.windowToken, 0)
        }

        parent_layout.setOnClickListener {
            imm.hideSoftInputFromWindow(title_tv.windowToken, 0)
        }

    }

//    private fun datePicker() : DatePickerDialog {
//        val listener = OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
//            cal.set(Calendar.YEAR,year)
//            cal.set(Calendar.MONTH,monthOfYear)
//            cal.set(Calendar.DAY_OF_MONTH,dayOfMonth)
//            datepicker_tv.text = format.dateFormat1(cal)
//        }
//
//        return  DatePickerDialog(this,listener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
//    }

private fun setDate(year:Int , month:Int , day:Int){
     cal.set(Calendar.YEAR,year)
     cal.set(Calendar.MONTH,month-1)
     cal.set(Calendar.DAY_OF_MONTH,day)
     datepicker_tv.text = format.dateFormat1(cal)
}

}