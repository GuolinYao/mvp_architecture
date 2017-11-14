package com.hishixi.tiku.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;

import com.hishixi.tiku.custom.view.DownloadProgressdialog;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author guolin
 */
public class FileModel {
    /**
     * 将图片复制至SD卡
     *
     * @param fileName
     * @param bitmap
     * @param quality
     */
    public static void copyToSDCard(String fileSdPath, String fileName, Bitmap bitmap,
                                    int quality, String newPath) {
        if (!ActivityUtils.isSDCardHas()) {
            return;
        }
        if (bitmap == null) {
            return;
        }
        FileOutputStream fos = null;
        File file = null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();//
        // 可以捕获内存缓冲区的数据，转换成字节数组。
        bitmap.compress(CompressFormat.JPEG, quality, stream);
        byte[] bytes = stream.toByteArray();// 获取内存缓冲中的数据,一个字节流转换为一个字节数组
        try {
            File dir = new File(fileSdPath);
            dir.deleteOnExit();// 如果存在清除文件
            if (!dir.exists()) {
                dir.mkdirs();
            }
            file = new File(fileSdPath + "/", fileName + ".jpg");
            File newFile = new File(fileSdPath + "/", newPath + ".jpg");
            if (!file.exists()) {
                file.createNewFile();
            }
            boolean b = file.renameTo(newFile);
            fos = new FileOutputStream(newFile);
            fos.write(bytes);
            fos.close();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除目录
     */
    public static void deleteDir(String path) {
        File dir = new File(path);
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDir(path); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    /**
     * 获取到指定文件夹(用来上传)
     */
    public static File getPostFile(String sourcePath) {
        File file = new File(sourcePath);
        File[] files = file.listFiles();
        if (null == files)
            return null;
        return file;
    }

    /**
     * 删除指定文件
     */
    public static void delFile(String path, String fileName) {
        File file = new File(path + "/" + fileName);
        if (file.isFile()) {
            file.delete();
        }
        file.exists();
    }

    /**
     * 删除指定文件
     */
    public static void delFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile()) {
            file.delete();
        }
        file.exists();
    }

    /**
     * 下载文件到sd卡
     *
     * @param progressDialog   progressDialog
     * @param url              下载地址
     * @param storageDirectory 存储地址
     */
    public static void downLoadFile(final DownloadProgressdialog progressDialog, String url,
                                    final String storageDirectory) {
        final File file = new File(storageDirectory);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        progressDialog.show();
        FileDownloader.getImpl().create(url)
                .setPath(storageDirectory)
                .setForceReDownload(true)
                .setAutoRetryTimes(3)
                .setListener(new FileDownloadListener() {

                    @Override
                    protected void connected(BaseDownloadTask task, String etag,
                                             boolean isContinue, int soFarBytes, int
                                                     totalBytes) {
                        super.connected(task, etag, isContinue, soFarBytes, totalBytes);

                    }

                    @Override
                    protected boolean isInvalid() {
                        return super.isInvalid();
                    }

                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int
                            totalBytes) {
                        Log.d("Main", "pending");
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int
                            totalBytes) {
                        int progress = soFarBytes * 100 / totalBytes;
                        progressDialog.setProgress(progress);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        progressDialog.setMessage("下载完成");
                        progressDialog.setProgress(100);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                            }
                        }, 1000);

                        if (null != downloadFinishListener) {
                            downloadFinishListener.onDownloadFinish();
                        }
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int
                            totalBytes) {
                        Log.d("Main", "paused");
                        progressDialog.setMessage("下载暂停");
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.d("Main", "error");
                        progressDialog.setMessage("下载失败");
                        if (file.exists())
                            file.delete();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                            }
                        }, 1000);
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        Log.d("Main", "warn");
                    }
                })
                .start();
    }

    /**
     * 下载文件到sd卡
     *
     * @param url              下载地址
     * @param storageDirectory 存储地址
     */
    public static void downLoadFile(String url, final String storageDirectory) {
        final File file = new File(storageDirectory);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileDownloader.getImpl().create(url)
                .setPath(storageDirectory)
                .setForceReDownload(true)
                .setAutoRetryTimes(3)
                .setListener(new FileDownloadListener() {

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean
                            isContinue, int soFarBytes, int totalBytes) {
                        super.connected(task, etag, isContinue, soFarBytes, totalBytes);
                    }

                    @Override
                    protected boolean isInvalid() {
                        return super.isInvalid();
                    }

                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int
                            totalBytes) {
                        Log.d("Main", "pending");
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int
                            totalBytes) {
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        if (null != downloadFinishListener) {
                            downloadFinishListener.onDownloadFinish();
                        }
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int
                            totalBytes) {
                        Log.d("Main", "paused");
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.d("Main", "error");
                        if (file.exists())
                            file.delete();
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        Log.d("Main", "warn");
                    }
                }).start();
    }

    //下载完成的监听
    private static DownloadFinishListener downloadFinishListener;

    public interface DownloadFinishListener {
        boolean onDownloadFinish();
    }

    public void setDownloadFinishListener(DownloadFinishListener downloadFinishListener) {
        this.downloadFinishListener = downloadFinishListener;
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     * @throws Exception
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    public static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    /**
     * Try to return the absolute file path from the given Uri
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore
                    .Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public static void changeFileName(String oldPath, String newPath) {
        try {
            File oldFile = new File(oldPath);
            File newFile = new File(newPath);
            if(!newFile.exists()) newFile.mkdirs();
            File[] files = oldFile.listFiles();
            for (File f : files) {
                if (f.isFile())
                    f.renameTo(new File(newPath, f.getName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
