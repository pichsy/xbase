package com.pichs.base.utils;

import android.util.Base64;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5 tool class
 *
 * @author Crane
 */
public final class MD5 {
    private static MessageDigest digest = null;

    /**
     * Base64 encode
     *
     * @param data
     * @return
     */
    public synchronized static String base64(String data) {
        byte[] b = null;
        try {
            b = data.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String s = null;
        if (b != null) {
            //注：原先使用的是Base64.DEFAULT,这种方式会自动加上换行符
            s = Base64.encodeToString(b, Base64.NO_WRAP);
        }
        return s;
    }

    /**
     * Base64 decode
     *
     * @param data
     * @return
     */
    public synchronized static String decodeBase64(String data) {
        return new String(Base64.decode(data.getBytes(), Base64.DEFAULT));
    }

    /**
     * 获取md5 小写的字符串
     *
     * @param content
     * @return
     */
    public synchronized static String md5(String content) {
        return encode2Hex(content);
    }

    /**
     * MD5 encode
     *
     * @param data
     * @return
     */
    public synchronized static String encode2Hex(String data) {
        if (digest == null) {
            try {
                digest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        try {
            digest.update(data.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeHex(digest.digest());
    }

    public synchronized static String fileCheckSum(String filename) {
        if (digest == null) {
            try {
                digest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        try {
            InputStream fis = new FileInputStream(filename);

            byte[] buffer = new byte[1024];
            int numRead;
            do {
                numRead = fis.read(buffer);
                if (numRead > 0) {
                    digest.update(buffer, 0, numRead);
                }
            } while (numRead != -1);
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeHex(digest.digest());
    }

    private static String encodeHex(byte[] bytes) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        int i;
        for (i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buf.toString();
    }

    private static String encodeBase64(byte[] bytes) {
        return Base64Algo.encode(bytes);
    }
}
