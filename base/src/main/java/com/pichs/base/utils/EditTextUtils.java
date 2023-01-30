package com.pichs.base.utils;

import android.annotation.SuppressLint;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 输入框的工具类
 */
public class EditTextUtils {

    public interface Regex {
        //只允许数字
        String NUMBER = "[^0-9]";
        //只允许字母、数字
        String NUMBER_LETTER = "[^a-zA-Z0-9]";
        //只允许字母、数字和汉字
        String NUMBER_LETTER_CHINESE = "[^a-zA-Z0-9\u4E00-\u9FA5]";
    }


    /**
     * 获取Filter普通的这种规则的
     *
     * @param regex
     * @return
     */
    public static InputFilter getNormalFilter(final String regex) {
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(source);
                return m.replaceAll("").trim();
            }
        };
    }

    public static InputFilter[] getNormalFilters(final String regex) {
        return new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        Pattern p = Pattern.compile(regex);
                        Matcher m = p.matcher(source);
                        return m.replaceAll("").trim();
                    }
                }};
    }

    public static InputFilter[] getLengthFilters(int maxLength) {
        return new InputFilter[]{
                new InputFilter.LengthFilter(maxLength)
        };
    }

    public static InputFilter getLengthFilter(int maxLength) {
        return new InputFilter.LengthFilter(maxLength);
    }

    /**
     * 设置输入字符的个数最大不超过maxLength
     */
    public static void setMaxLength(EditText editText, int maxLength) {
        editText.setFilters(getLengthFilters(maxLength));
    }


    /**
     * 设置过滤器
     *
     * @param editText
     * @param regex
     */
    public static void setFilters(EditText editText, String regex) {
        editText.setFilters(getNormalFilters(regex));
    }

    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 功能：判断字符串是否为日期格式
     *
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        return m.matches();
    }

    /**
     * 显示密码
     *
     * @param editText
     */
    public static void showPassword(EditText editText) {
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        if (editText.getText() != null) {
            editText.setSelection(editText.getText().length());
        }
    }

    /**
     * 隐藏密码
     *
     * @param editText
     */
    public static void hidePassword(EditText editText) {
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        if (editText.getText() != null) {
            editText.setSelection(editText.getText().length());
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public static void disableCopyAndPaste(final EditText editText) {
        try {
            if (editText == null) {
                return;
            }

            editText.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });

            editText.setLongClickable(false);
            editText.setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // setInsertionDisabled when user touches the view
                    setInsertionDisabled(editText);
                }
                return false;
            });

            editText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setInsertionDisabled(EditText editText) {
        try {
            Field editorField = TextView.class.getDeclaredField("mEditor");
            editorField.setAccessible(true);
            Object editorObject = editorField.get(editText);

            // if this view supports insertion handles
            Class editorClass = Class.forName("android.widget.Editor");
            Field mInsertionControllerEnabledField = editorClass.getDeclaredField("mInsertionControllerEnabled");
            mInsertionControllerEnabledField.setAccessible(true);
            mInsertionControllerEnabledField.set(editorObject, false);

            // if this view supports selection handles
            Field mSelectionControllerEnabledField = editorClass.getDeclaredField("mSelectionControllerEnabled");
            mSelectionControllerEnabledField.setAccessible(true);
            mSelectionControllerEnabledField.set(editorObject, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
