package com.example.integrate1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 工具类
 * @author spl
 *
 */
public class Utils_Results {

    /**
     * 获取SDcard根路径
     * @return
     */
    public static String getSDCardPath(){
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 通过传入的路径,返回该路径下的所有的文件和文件夹列表
     * @param path
     * @return
     */
    public static List<FileInfo_Results> getListData(String path) {

        List<FileInfo_Results> list = new ArrayList<FileInfo_Results>();

        File pfile = new File(path);// 文件对象
        File[] files = null;// 声明了一个文件对象数组
        if (pfile.exists()) {// 判断路径是否存在
            files = pfile.listFiles();// 该文件对象下所属的所有文件和文件夹列表
        }

        if (files != null && files.length > 0) {// 非空验证
            for (File file : files) {// foreach循环遍历
                FileInfo_Results item = new FileInfo_Results();
                if(file.isHidden()){
                    continue;// 跳过隐藏文件
                }
                if (file.isDirectory()// 文件夹
                        && file.canRead()//是否可读
                        ) {
                    file.isHidden();//  是否是隐藏文件
                    // 获取文件夹目录结构
                   item.icon=R.drawable.folder;//图标
                    item.size="";//大小
                        item.type = ShowResults.T_DIR;


                } else if(file.isFile()){// 文件

                    Log.i("spl",file.getName());
                    String ext = getFileEXT(file.getName());
                    Log.i("spl", "ext="+ext);

                    // 文件的图标
                    item.icon=getDrawableIcon(ext);// 根据扩展名获取图标
                    // 文件的大小
                    String size = getSize(file.length());
                    item.size=size;
                    item.type = ShowResults.T_FILE;
                    if (checkEndsInArray(ext, new String[]{"png","gif","jpg","bmp"})) {
                        item.type = ShowResults.T_PIC;
                    }
                    if (checkEndsInArray(ext, new String[]{"mp4","3gp","mpeg","mov","flv"})) {
                        // video
                        item.type = ShowResults.T_VIDEO;
                    }


                }else{// 其它
                    item.icon=R.drawable.mul_file;
                }
                item.name=file.getName();// 名称

                item.path=file.getPath();// 路径
                // 最后修改时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date = new Date(file.lastModified());
                String time = sdf.format(date);
                item.time=time;

                list.add(item);
            }
        }
        files = null;
        return list;
    }

    /**
     * 通过传入的路径,返回该路径下的所有的文件和文件夹列表
     * @param path
     * @return
     */
    public static void getSearchList(String path, String key, List<FileInfo_Results> list) {

        File pfile = new File(path);// 文件对象
        File[] files = null;// 声明了一个文件对象数组
        if (pfile.exists()) {// 判断路径是否存在
            files = pfile.listFiles();// 该文件对象下所属的所有文件和文件夹列表
        }

        if (files != null && files.length > 0) {// 非空验证
            for (File file : files) {// foreach循环遍历

                if(file.isHidden()){
                    continue;// 跳过隐藏文件
                }
                if (file.isDirectory()// 文件夹
                        && file.canRead()//是否可读
                        ) {

                    // 判断文件夹的名字是否包含关键字
                    if (file.getName().toLowerCase().contains(key.toLowerCase())) {
                        FileInfo_Results item = new FileInfo_Results();
                        // 获取文件夹目录结构
                        item.icon = R.drawable.folder;//图标
                        item.size = "";//大小
                        item.type = ShowResults.T_DIR;
                        item.name = file.getName();// 名称

                        item.path = file.getPath();// 路径
                        // 最后修改时间
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date date = new Date(file.lastModified());
                        String time = sdf.format(date);
                        item.time = time;

                        list.add(item);// 添加搜索结果
                    }
                    // 递归调用
                    getSearchList(file.getPath(),key,list);


                } else if(file.isFile()){// 文件

                    // 判断文件夹的名字是否包含关键字
                    if (file.getName().toLowerCase().contains(key.toLowerCase())) {
                        FileInfo_Results item = new FileInfo_Results();
                        Log.i("spl", file.getName());
                        String ext = getFileEXT(file.getName());
                        Log.i("spl", "ext=" + ext);

                        // 文件的图标
                        item.icon = getDrawableIcon(ext);// 根据扩展名获取图标
                        // 文件的大小
                        String size = getSize(file.length());
                        item.size = size;
                        item.type = ShowResults.T_FILE;
                        if (checkEndsInArray(ext, new String[]{"png","gif","jpg","bmp"})) {
                            item.type = ShowResults.T_PIC;
                        }
                        if (checkEndsInArray(ext, new String[]{"mp4","3gp","mpeg","mov","flv"})) {
                            // video
                            item.type = ShowResults.T_VIDEO;
                        }
                        item.name = file.getName();// 名称

                        item.path = file.getPath();// 路径
                        // 最后修改时间
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date date = new Date(file.lastModified());
                        String time = sdf.format(date);
                        item.time = time;

                        list.add(item);// 添加搜索结果
                    }

                }

            }
        }
        files = null;

    }

    /**
     * 截取文件的扩展名
     * @param filename 文件全名
     * @return 扩展名(mp3, txt)
     */
    public static String getFileEXT(String filename){
        if (filename.contains(".")) {
            int dot = filename.lastIndexOf(".");// 123.abc.txt
            String ext = filename.substring(dot + 1);
            return ext;
        }else{
            return "";
        }
    }

    /**
     * 获得与扩展名对应的图标资源id
     *
     * @param end
     *            扩展名
     * @return
     */
    public static int getDrawableIcon(String end) {
        int id = 0;
        if (end.equals("asf")) {
            id = R.drawable.asf;
        } else if (end.equals("avi")) {
            id = R.drawable.avi;
        } else if (end.equals("bmp")) {
            id = R.drawable.bmp;
        } else if (end.equals("doc")) {
            id = R.drawable.doc;
        } else if (end.equals("gif")) {
            id = R.drawable.gif;
        } else if (end.equals("html")) {
            id = R.drawable.html;
        } else if (end.equals("apk")) {
            id = R.drawable.iapk;
        } else if (end.equals("ico")) {
            id = R.drawable.ico;
        } else if (end.equals("jpg")) {
            id = R.drawable.jpg;
        } else if (end.equals("log")) {
            id = R.drawable.log;
        } else if (end.equals("mov")) {
            id = R.drawable.mov;
        } else if (end.equals("mp3")) {
            id = R.drawable.mp3;
        } else if (end.equals("mp4")) {
            id = R.drawable.mp4;
        } else if (end.equals("mpeg")) {
            id = R.drawable.mpeg;
        } else if (end.equals("pdf")) {
            id = R.drawable.pdf;
        } else if (end.equals("png")) {
            id = R.drawable.png;
        } else if (end.equals("ppt")) {
            id = R.drawable.ppt;
        } else if (end.equals("rar")) {
            id = R.drawable.rar;
        } else if (end.equals("txt") || end.equals("dat") || end.equals("ini")
                || end.equals("java")) {
            id = R.drawable.txt;
        } else if (end.equals("vob")) {
            id = R.drawable.vob;
        } else if (end.equals("wav")) {
            id = R.drawable.wav;
        } else if (end.equals("wma")) {
            id = R.drawable.wma;
        } else if (end.equals("wmv")) {
            id = R.drawable.wmv;
        } else if (end.equals("xls")) {
            id = R.drawable.xls;
        } else if (end.equals("xml")) {
            id = R.drawable.xml;
        } else if (end.equals("zip")) {
            id = R.drawable.zip;
        } else if (end.equals("3gp")|| end.equals("flv")) {
            id = R.drawable.file_video;
        } else if (end.equals("amr")) {
            id = R.drawable.file_audio;
        } else {
            id = R.drawable.default_fileicon;
        }
        return id;
    }


    /**
     * 格式转换应用大小 单位"B,KB,MB,GB"
     */
    public static String getSize(float length) {

        long kb = 1024;
        long mb = 1024*kb;
        long gb = 1024*mb;
        if(length<kb){
            return String.format("%dB", (int)length);
        }else if(length<mb){
            return String.format("%.2fKB", length/kb);
        }else if(length<gb){
            return String.format("%.2fMB", length/mb);
        }else {
            return String.format("%.2fGB", length/gb);
        }
    }

    /**
     * 检查扩展名end 是否在ends数组中
     * @param end
     * @param ends
     * @return
     */
    public static boolean checkEndsInArray(String end, String[] ends) {
        for (String aEnd : ends) {
            if (end.equals(aEnd)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 打开文件
     * @param context
     * @param aFile
     */
    public static void openFile(Context context, File aFile) {

        // 实例化意图
        Intent intent = new Intent();
        // 添加动作(干什么?)
        intent.setAction(Intent.ACTION_VIEW);
        //取得文件名
        String fileName = aFile.getName();
        String end = getFileEXT(fileName).toLowerCase();
        if (aFile.exists()) {
            // 根据不同的文件类型来打开文件
            if (checkEndsInArray(end, new String[]{"png","gif","jpg","bmp"})) {
                // 图片
                intent.setDataAndType(Uri.fromFile(aFile), "image/*");//MIME TYPE
            } else if (checkEndsInArray(end, new String[]{"apk"})) {
                // apk
                intent.setDataAndType(Uri.fromFile(aFile), "application/vnd.android.package-archive");
            } else if (checkEndsInArray(end, new String[]{"mp3","amr","ogg","mid","wav"})) {
                // audio
                intent.setDataAndType(Uri.fromFile(aFile), "audio/*");
            } else if (checkEndsInArray(end, new String[]{"mp4","3gp","mpeg","mov","flv"})) {
                // video
                intent.setDataAndType(Uri.fromFile(aFile), "video/*");
            } else if (checkEndsInArray(end, new String[]{"txt","ini","log","java","xml","html"})) {
                // text
                intent.setDataAndType(Uri.fromFile(aFile), "text/plain");
            } else if (checkEndsInArray(end, new String[]{"doc","docx"})) {
                // word
                intent.setDataAndType(Uri.fromFile(aFile), "application/msword");
            } else if (checkEndsInArray(end, new String[]{"xls","xlsx"})) {
                // excel
                intent.setDataAndType(Uri.fromFile(aFile), "application/vnd.ms-excel");
            } else if (checkEndsInArray(end, new String[]{"ppt","pptx"})) {
                // ppt
                intent.setDataAndType(Uri.fromFile(aFile), "application/vnd.ms-powerpoint");
            } else if (checkEndsInArray(end, new String[]{"chm"})) {
                // chm
                intent.setDataAndType(Uri.fromFile(aFile), "application/x-chm");
            } else {
                // 其他
                intent.setDataAndType(Uri.fromFile(aFile), "application/" + end);
            }
            try {
                // 发送意图
                context.startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(context, "没有找到适合打开此文件的应用", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
