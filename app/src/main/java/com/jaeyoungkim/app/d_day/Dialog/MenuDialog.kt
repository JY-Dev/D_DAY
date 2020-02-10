package com.jaeyoungkim.app.d_day.Dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.jaeyoungkim.app.d_day.R
import kotlinx.android.synthetic.main.menu_dialog.*

class MenuDialog(context: Context, onModifylCallBack : () -> Unit, onDeleteCallBack: () -> Unit) : Dialog(context){
    init {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.setContentView(R.layout.menu_dialog)
        this.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.setCanceledOnTouchOutside(false)

        modify_btn.setOnClickListener {
            onModifylCallBack()
            this.dismiss()
        }
        delete_btn.setOnClickListener {
            CompleteDelDialog(context) {
                onDeleteCallBack()
            }
            this.dismiss()
        }
        cancel_sel_btn.setOnClickListener {
            this.dismiss()
        }

        this.show()
    }
}