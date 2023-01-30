package com.pichs.base.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;


public class FileUtils {

    /**
     * 拷贝assets中的文件到内存中
     *
     * @param context     Context
     * @param assetFile   assets中的文件路径名
     * @param destDir     目标文件路径
     * @param dstFileName 目标文件名
     * @return 目标文件的全路径
     */
    public static String copyAssetsFile(Context context, String assetFile, File destDir, String dstFileName) {
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            if (TextUtils.isEmpty(assetFile)) {
                return null;
            }
            File file = createIfNotExist(destDir, dstFileName);
            InputStream inputStream = context.getResources().getAssets().open(assetFile);
            bis = new BufferedInputStream(inputStream);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bos = new BufferedOutputStream(fileOutputStream);
            byte[] buffer = new byte[1024];
            int byteRead;
            while (-1 != (byteRead = bis.read(buffer))) {
                bos.write(buffer, 0, byteRead);
            }
            bos.flush();
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (bis != null) {
                    bis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static String copyFile(File oldFile, File destFile) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            if (!oldFile.exists()) {
                return null;
            } else if (!oldFile.isFile()) {
                return null;
            } else if (!oldFile.canRead()) {
                return null;
            }
            deleteIfExist(destFile);
            createIfNotExist(destFile);
            FileInputStream fileInputStream = new FileInputStream(oldFile);
            bis = new BufferedInputStream(fileInputStream);
            FileOutputStream fileOutputStream = new FileOutputStream(destFile);
            bos = new BufferedOutputStream(fileOutputStream);
            byte[] buffer = new byte[4096];
            int byteRead;
            while (-1 != (byteRead = bis.read(buffer))) {
                bos.write(buffer, 0, byteRead);
            }
            bos.flush();
            return destFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String copyFile(File oldFile, String destDir, String fileName) {
        try {
            File file = deleteIfExist(new File(destDir, fileName));
            File destFile = createIfNotExist(file);
            return copyFile(oldFile, destFile);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File deleteIfExist(File file) {
        if (file.isFile() && file.exists()) {
            boolean delete = file.delete();
            Log.d("DELETE FILE", "deleteIfExist: " + delete);
        }
        return file;
    }

    public static File createIfNotExist(File file) {
        if (file.isDirectory() && !file.exists()) {
            boolean mkdirs = file.mkdirs();
        } else if (file.isFile() && !file.exists()) {
            try {
                boolean isCreated = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static File createIfNotExist(File dir, String fileName) {
        if (dir.isDirectory()) {
            if (!dir.exists()) {
                boolean mkdirs = dir.mkdirs();
            }
            File file = new File(dir, fileName);
            deleteIfExist(file);
            try {
                boolean success = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return file;
        } else {
            try {
                boolean success = dir.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return dir;
        }
    }


    public static String readFile(String filePath) {
        return readFile(new File(filePath));
    }

    public static String readFile(File file) {
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }

    public static boolean isFileExist(String jsonPath) {
        if (jsonPath == null) return false;
        if (jsonPath.length() == 0 || jsonPath.trim().length() == 0) return false;
        File file = new File(jsonPath);
        return file.exists() && file.isFile();
    }

    public static boolean isDirExist(String jsonPath) {
        if (jsonPath == null) return false;
        if (jsonPath.length() == 0 || jsonPath.trim().length() == 0) return false;
        File file = new File(jsonPath);
        return file.exists() && file.isDirectory();
    }
}
