package com.pichs.xbase.utils

import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.widget.EditText
import android.text.InputType
import android.annotation.SuppressLint
import android.view.*
import android.view.View.OnLongClickListener
import android.view.View.OnTouchListener
import android.widget.TextView
import java.lang.Exception
import java.util.regex.Pattern

/**
 * 输入框的工具类
 */
object EditTextUtils {
    /**
     * 获取Filter普通的这种规则的
     *
     * @param regex
     * @return
     */
    fun getNormalFilter(regex: String?): InputFilter {
        return InputFilter { source, start, end, dest, dstart, dend ->
            val p = Pattern.compile(regex)
            val m = p.matcher(source)
            m.replaceAll("").trim { it <= ' ' }
        }
    }

    fun getNormalFilters(regex: String?): Array<InputFilter> {
        return arrayOf(
            InputFilter { source, start, end, dest, dstart, dend ->
                val p = Pattern.compile(regex)
                val m = p.matcher(source)
                m.replaceAll("").trim { it <= ' ' }
            })
    }

    fun getLengthFilters(maxLength: Int): Array<InputFilter> {
        return arrayOf(
            LengthFilter(maxLength)
        )
    }

    fun getLengthFilter(maxLength: Int): InputFilter {
        return LengthFilter(maxLength)
    }

    /**
     * 设置输入字符的个数最大不超过maxLength
     */
    fun setMaxLength(editText: EditText, maxLength: Int) {
        editText.filters = getLengthFilters(maxLength)
    }

    /**
     * 设置过滤器
     *
     * @param editText
     * @param regex
     */
    fun setFilters(editText: EditText, regex: String?) {
        editText.filters = getNormalFilters(regex)
    }

    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    fun isNumber(str: String?): Boolean {
        val pattern = Pattern.compile("[0-9]*")
        val isNum = pattern.matcher(str)
        return isNum.matches()
    }

    /**
     * 功能：判断字符串是否为日期格式
     *
     * @return
     */
    fun isDate(strDate: String?): Boolean {
        val pattern = Pattern
            .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$")
        val m = pattern.matcher(strDate)
        return m.matches()
    }

    /**
     * 显示密码
     *
     * @param editText
     */
    fun showPassword(editText: EditText) {
        editText.inputType = InputType.TYPE_CLASS_TEXT
        if (editText.text != null) {
            editText.setSelection(editText.text.length)
        }
    }

    /**
     * 隐藏密码
     *
     * @param editText
     */
    fun hidePassword(editText: EditText) {
        editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        if (editText.text != null) {
            editText.setSelection(editText.text.length)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun disableCopyAndPaste(editText: EditText?) {
        try {
            if (editText == null) {
                return
            }
            editText.setOnLongClickListener(OnLongClickListener { true })
            editText.isLongClickable = false
            editText.setOnTouchListener(OnTouchListener { v: View?, event: MotionEvent ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    // setInsertionDisabled when user touches the view
                    setInsertionDisabled(editText)
                }
                false
            })
            editText.customSelectionActionModeCallback = object : ActionMode.Callback {
                override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                    return false
                }

                override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
                    return false
                }

                override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                    return false
                }

                override fun onDestroyActionMode(mode: ActionMode) {}
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setInsertionDisabled(editText: EditText) {
        try {
            val editorField = TextView::class.java.getDeclaredField("mEditor")
            editorField.isAccessible = true
            val editorObject = editorField[editText]

            // if this view supports insertion handles
            val editorClass = Class.forName("android.widget.Editor")
            val mInsertionControllerEnabledField = editorClass.getDeclaredField("mInsertionControllerEnabled")
            mInsertionControllerEnabledField.isAccessible = true
            mInsertionControllerEnabledField[editorObject] = false

            // if this view supports selection handles
            val mSelectionControllerEnabledField = editorClass.getDeclaredField("mSelectionControllerEnabled")
            mSelectionControllerEnabledField.isAccessible = true
            mSelectionControllerEnabledField[editorObject] = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    interface Regex {
        companion object {
            //只允许数字
            const val NUMBER = "[^0-9]"

            //只允许字母、数字
            const val NUMBER_LETTER = "[^a-zA-Z0-9]"

            //只允许字母、数字和汉字
            const val NUMBER_LETTER_CHINESE = "[^a-zA-Z0-9\u4E00-\u9FA5]"
        }
    }
}