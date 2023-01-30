package com.pichs.base.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import okio.BufferedSource;
import okio.Okio;
import okio.Sink;

public class GalleryUtils {

    private static final String VIDEO_BASE_URI = "content://media/external/video/media";

    /***
     * @param srcPath
     * @param context
     */
    public static void insertImage(String srcPath, String destDir, String destName, Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, destName);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            ContentResolver contentResolver = context.getContentResolver();
            Uri collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
            Uri item = contentResolver.insert(collection, values);
            writeFile(srcPath, values, contentResolver, item);
            contentResolver.update(item, values, null, null);
        } else {
            copyFile(new File(srcPath), destDir, destName);
        }
    }

    /***
     *
     * @param videoPath
     * @param context
     */
    public static void insertVideo(String videoPath, Context context) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(videoPath);
        int nVideoWidth = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
        int nVideoHeight = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
        int duration = Integer
                .parseInt(retriever
                        .extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        long dateTaken = System.currentTimeMillis();
        File file = new File(videoPath);
        String title = file.getName();
        String filename = file.getName();
        String mime = "video/mp4";
        ContentValues mCurrentVideoValues = new ContentValues(9);
        mCurrentVideoValues.put(MediaStore.Video.Media.TITLE, title);
        mCurrentVideoValues.put(MediaStore.Video.Media.DISPLAY_NAME, filename);
        mCurrentVideoValues.put(MediaStore.Video.Media.DATE_TAKEN, dateTaken);
        mCurrentVideoValues.put(MediaStore.MediaColumns.DATE_MODIFIED, dateTaken / 1000);
        mCurrentVideoValues.put(MediaStore.Video.Media.MIME_TYPE, mime);
        mCurrentVideoValues.put(MediaStore.Video.Media.DATA, videoPath);
        mCurrentVideoValues.put(MediaStore.Video.Media.WIDTH, nVideoWidth);
        mCurrentVideoValues.put(MediaStore.Video.Media.HEIGHT, nVideoHeight);
        mCurrentVideoValues.put(MediaStore.Video.Media.RESOLUTION, Integer.toString(nVideoWidth) + "x" + Integer.toString(nVideoHeight));
        mCurrentVideoValues.put(MediaStore.Video.Media.SIZE, new File(videoPath).length());
        mCurrentVideoValues.put(MediaStore.Video.Media.DURATION, duration);
        ContentResolver contentResolver = context.getContentResolver();
        Uri videoTable = Uri.parse(VIDEO_BASE_URI);
        Uri uri = contentResolver.insert(videoTable, mCurrentVideoValues);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            writeFile(videoPath, mCurrentVideoValues, contentResolver, uri);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private static void writeFile(String imagePath, ContentValues values, ContentResolver contentResolver, Uri item) {
        try (OutputStream rw = contentResolver.openOutputStream(item, "rw")) {
            // Write data into the pending image.
            Sink sink = Okio.sink(rw);
            BufferedSource buffer = Okio.buffer(Okio.source(new File(imagePath)));
            buffer.readAll(sink);
            values.put(MediaStore.Video.Media.IS_PENDING, 0);
            contentResolver.update(item, values, null, null);
            new File(imagePath).delete();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Cursor query = contentResolver.query(item, null, null, null);
                if (query != null) {
                    int count = query.getCount();
                    Log.e("writeFile", "writeFile result :" + count);
                    query.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String copyFile(String oldFile, String destFile) {
        return copyFile(new File(oldFile), createIfNotExist(new File(destFile)));
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


}
