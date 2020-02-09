package com.jaeyoungkim.app.d_day.Dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.jaeyoungkim.app.d_day.R
import kotlinx.android.synthetic.main.datepicker_dialog.*
import java.util.*

class DatePickerDialog(context: Context, onCallbackListner : (year : Int, month : Int, day : Int) -> Unit) : Dialog(context){
    private val MIN_MONTH = 1
    private val MAX_MONTH = 12
    private var MIN_DAY = 1
    private val MAX_YEAR = 2099
    private var currentYear = 0
    private var currentMonth = 0
    private var currentDay = 0
    private var changeYear = 0
    private var changeMonth = 0
    private var changeDay = 0
    private var flag = ""

    private var cal = Calendar.getInstance()
    init {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.setContentView(R.layout.datepicker_dialog)
        this.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.setCanceledOnTouchOutside(false)

        currentYear = cal.get(Calendar.YEAR)
        currentMonth = cal.get(Calendar.MONTH) +1
        currentDay = cal.get(Calendar.DAY_OF_MONTH) + 1
        changeYear = cal.get(Calendar.YEAR)
        changeMonth = cal.get(Calendar.MONTH) +1
        changeDay = cal.get(Calendar.DAY_OF_MONTH) + 1
        setValue()
        picker_year.setOnValueChangedListener { picker, oldVal, newVal ->
            flag = "year"
            cal.set(Calendar.YEAR , newVal)
            checkDateYear()
        }

        picker_month.setOnValueChangedListener { picker, oldVal, newVal ->
            flag = "month"
            cal.set(Calendar.MONTH, newVal-1)
            checkDateMonth()
        }

        btn_cancel.setOnClickListener {
            this.dismiss()
        }

        btn_confirm.setOnClickListener {
            onCallbackListner(picker_year.value,picker_month.value,picker_day.value)
            this.dismiss()
        }

        this.show()
    }

    private fun setValue(){
        picker_month.minValue = currentMonth
        picker_month.maxValue = MAX_MONTH
        picker_month.value = currentMonth
        picker_year.minValue = currentYear
        picker_year.maxValue = MAX_YEAR
        picker_year.value = currentYear
        picker_day.maxValue = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        picker_day.minValue = currentDay
    }

    private fun changeValue(){
        changeYear = cal.get(Calendar.YEAR)
        changeMonth = cal.get(Calendar.MONTH) + 1
        changeDay = cal.get(Calendar.DAY_OF_MONTH) + 1
    }

    private fun checkDateYear(){
        changeValue()
        picker_day.maxValue = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        if(currentYear < changeYear){
            picker_month.minValue = MIN_MONTH
            picker_day.minValue = MIN_DAY
        } else {
            if (currentMonth == changeMonth) picker_day.minValue = currentDay
            picker_month.minValue = currentMonth
        }

    }

    private fun checkDateMonth(){
        changeValue()
        picker_day.maxValue = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        if (currentMonth < changeMonth || currentYear < changeYear) {
            picker_day.minValue = MIN_DAY
        } else {
            picker_day.minValue = currentDay
        }
    }
}