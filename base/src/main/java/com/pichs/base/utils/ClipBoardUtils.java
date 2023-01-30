package com.pichs.base.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.text.TextUtils;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * 注册监听板
 */
public class ClipBoardUtils {

    private static ClipboardManager mClipboardManager;

    public static String getClipBoardContent(Activity activity) {
        if (mClipboardManager == null) {
            mClipboardManager = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
        }
        try {
            if (mClipboardManager.hasPrimaryClip()) {
                ClipData primaryClip = mClipboardManager.getPrimaryClip();
                if (primaryClip != null && primaryClip.getItemCount() > 0) {
                    String content = primaryClip.getItemAt(0).getText().toString();
                    if (!TextUtils.isEmpty(content)) {
                        return content;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void copyToClipBoard(Activity activity, String content) {
        if (mClipboardManager == null) {
            mClipboardManager = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
        }
        try {
            ClipData clipData = ClipData.newPlainText("", content);
            mClipboardManager.setPrimaryClip(clipData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addClipBoardChaneListener(Activity activity, final OnClipBoardChangedListener onClipBoardChangedListener) {
        if (mClipboardManager == null) {
            mClipboardManager = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
        }
        mClipboardManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                try {
                    if (mClipboardManager.hasPrimaryClip()) {
                        ClipData primaryClip = mClipboardManager.getPrimaryClip();
                        if (primaryClip != null && primaryClip.getItemCount() > 0) {
                            String content = primaryClip.getItemAt(0).getText().toString();
                            if (!TextUtils.isEmpty(content)) {
                                if (onClipBoardChangedListener != null) {
                                    onClipBoardChangedListener.onChanged(content);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public interface OnClipBoardChangedListener {
        void onChanged(String text);
    }
}
