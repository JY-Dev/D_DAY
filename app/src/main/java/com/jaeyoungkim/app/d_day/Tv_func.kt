package com.jaeyoungkim.app.d_day

import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText

class Tv_func{
    fun title_tv_maxline(title_tv : EditText){
        var specialRequests = ""
        var lastSpecialRequestsCursorPosition = 0

        // maxline 3
        title_tv.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                lastSpecialRequestsCursorPosition = title_tv.selectionStart
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                title_tv.removeTextChangedListener(this)
                if (title_tv.lineCount > 3) {
                    title_tv.setText(specialRequests)
                    title_tv.setSelection(lastSpecialRequestsCursorPosition)
                    title_tv.imeOptions = EditorInfo.IME_ACTION_DONE
                }
                else
                    specialRequests = title_tv.text.toString()

                title_tv.addTextChangedListener(this);
            }

        })
    }
}