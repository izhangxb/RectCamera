package com.example;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author tanhuohui
 * @description 对文件操作工具类;主要对上传图片时使用
 * @data 2015-01-05
 * @class FileTools
 */
public class FileTools {
    public final static String ALBUM_PATH = Environment.getExternalStorageDirectory() + "/Cheshang/cheshangpic/";
    public final static String ALBUM_PATH_TEMP = Environment.getExternalStorageDirectory() +
            "/Cheshang/cheshangpictemp/";

    /**
     * Save image to the SD card
     **/

    public static void savePhotoToSDCard(String path, String photoName,

                                         Bitmap photoBitmap) {

        if (Environment.getExternalStorageState().equals(

                Environment.MEDIA_MOUNTED)) {

            File dir = new File(path);

            if (!dir.exists()) {

                dir.mkdirs();

            }
            File photoFile = new File(path, photoName); //在指定路径下创建文件

            FileOutputStream fileOutputStream = null;

            try {

                fileOutputStream = new FileOutputStream(photoFile);

                if (photoBitmap != null) {

                    if (photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100,

                            fileOutputStream)) {

                        fileOutputStream.flush();

                    }

                }

            } catch (FileNotFoundException e) {

                photoFile.delete();

                e.printStackTrace();

            } catch (IOException e) {

                photoFile.delete();

                e.printStackTrace();

            } finally {

                try {

                    fileOutputStream.close();

                } catch (IOException e) {

                    e.printStackTrace();

                }
            }
        }
    }

    //保存图片
    public static void savePic(Context context, Bitmap photo, String path) {
        long millis = System.currentTimeMillis();
        String dir = getDir(path);
        File dirFile = new File(dir);
        dirFile.mkdirs();
        if (!dirFile.exists()) {
            Toast.makeText(context.getApplicationContext(), "无法创建SD卡目录,图片无法保存", Toast.LENGTH_LONG).show();
        }
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(path));
            photo.compress(Bitmap.CompressFormat.JPEG, 60, bos);// (0 - 100)压缩文件
        } catch (Exception e) {
            Toast.makeText(context.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public static String getDir(String filePath) {
        int lastSlastPos = filePath.lastIndexOf('/');
        return filePath.substring(0, lastSlastPos);
    }



    /**
     * 缩放Bitmap图片
     **/

    @SuppressLint("NewApi")
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {

        int w = bitmap.getWidth();

        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();

        float scaleWidth = ((float) width / w);

        float scaleHeight = ((float) height / h);

        matrix.postScale(scaleWidth, scaleHeight);// 利用矩阵进行缩放不会造成内存溢出

        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;

    }

    /**
     * 保存文件
     *
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static void saveFile(Bitmap bm, String fileName) throws IOException {
        File dirFile = new File(ALBUM_PATH);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(ALBUM_PATH + fileName);
        if (!myCaptureFile.exists()) {
            myCaptureFile.mkdirs();
        }
        myCaptureFile.createNewFile();
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 60, bos);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }

    }

    /**
     * 删除文件
     *
     * @param fileName
     */
    public static void deleteImageFile(String fileName) {
        File dirFile = new File(ALBUM_PATH);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        if (dirFile.listFiles().length > 0) {
            for (File mFile : dirFile.listFiles()) {
                if (mFile.getName().contains(fileName)) {
                    mFile.delete();
                }
            }
        }

    }

    /**
     * 删除文件
     *
     * @param fileName
     */
    public static void deleteTempImageFile(String fileName) {
        File dirFile = new File(ALBUM_PATH_TEMP);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        if (dirFile.listFiles().length > 0) {
            for (File mFile : dirFile.listFiles()) {
                if (mFile.getName().contains(fileName)) {
                    mFile.delete();
                }
            }
        }

    }

    /**
     * 清除所有文件
     *
     */
    public static void deleteALLImageFile() {
        File dirFile = new File(ALBUM_PATH);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        if (dirFile.listFiles().length > 0) {
            for (File mFile : dirFile.listFiles()) {
                mFile.delete();
            }
        }

    }

    /**
     * 清除所有文件
     *
     * @param path
     */
    public static void deleteALLImageFile(String path) {
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        if (dirFile.listFiles().length > 0) {
            for (File mFile : dirFile.listFiles()) {
                mFile.delete();
            }
        }

    }

    /**
     * 文件数量
     *
     * @return int
     */
    public static int imageFileNum() {
        File dirFile = new File(ALBUM_PATH);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        return dirFile.listFiles().length;
    }

    /**
     * 判断文件是否存在
     *
     * @return int
     */
    public static boolean imageFileIsExit(String fileNamePath) {
        File myCaptureFile = new File(fileNamePath);
        return myCaptureFile.exists();
    }



}
