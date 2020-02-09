package com.jaeyoungkim.app.d_day.Dialog


import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.jaeyoungkim.app.d_day.R
import kotlinx.android.synthetic.main.img_sel_dialog.*

class ImgSelDialog(context: Context, onCameraSelCallBack : () -> Unit , onGallerySelCallBack: () -> Unit) : Dialog(context){
    init {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.setContentView(R.layout.img_sel_dialog)
        this.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.setCanceledOnTouchOutside(false)

        camera_sel_btn.setOnClickListener {
            onCameraSelCallBack()
            this.dismiss()
        }
        gallery_sel_btn.setOnClickListener {
            onGallerySelCallBack()
            this.dismiss()
        }
        cancel_sel_btn.setOnClickListener {
            this.dismiss()
        }
        this.show()
    }
}