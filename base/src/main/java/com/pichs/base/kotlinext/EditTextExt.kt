package com.pichs.base.kotlinext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * EditText 监听，原接口需要重写三个方法，但是一般只需要在 onTextChanged 中处理逻辑，另外两个方法没用
 * 使用 onChange 扩展方法方便监听
 */
fun EditText.onChange(textChanged: ((String) -> Unit)) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            textChanged.invoke(s.toString())
        }
    })
}

fun EditText.onChangeWithEditable(textChanged: ((Editable?) -> Unit)) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            textChanged.invoke(s)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    })
}