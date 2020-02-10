package com.jaeyoungkim.app.d_day.Dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.jaeyoungkim.app.d_day.R
import kotlinx.android.synthetic.main.complete_del_dialog.*

class CompleteDelDialog(context: Context, onDeleteCallBack : () -> Unit) : Dialog(context){
    init {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.setContentView(R.layout.complete_del_dialog)
        this.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.setCanceledOnTouchOutside(false)

        cancel_sel_btn.setOnClickListener {
            this.dismiss()
        }

        delete_btn.setOnClickListener {
            onDeleteCallBack()
            this.dismiss()
        }

        this.show()
    }
}