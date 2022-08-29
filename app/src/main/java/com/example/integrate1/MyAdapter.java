package com.example.integrate1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;


public class MyAdapter extends BaseAdapter {

    List<FileInfo_Results> list;
    LayoutInflater inflater;

    DisplayImageOptions options;


    public MyAdapter(Context context) {

        this.inflater = LayoutInflater.from(context);

        // 图片的显示设置(选项)
        options = new DisplayImageOptions.Builder()
                //.showImageOnLoading(R.drawable.ic_launcher)// 加载时的图标
                .showImageForEmptyUri(R.drawable.ic_launcher_foreground)//地址错误时的图标
                .showImageOnFail(R.drawable.ic_launcher_foreground)//加载失败的图标
                        //.resetViewBeforeLoading(true)// 在加载前重置View
                .cacheInMemory(true)
                .cacheOnDisk(true)// 在SDCard上缓存
                .imageScaleType(ImageScaleType.EXACTLY)//拉伸类型
                .bitmapConfig(Bitmap.Config.RGB_565)//图片的解码格式
                .considerExifParams(true)// 考虑JPEG的旋转
                .displayer(new RoundedBitmapDisplayer(14))// 淡入特效时间new FadeInBitmapDisplayer(1000)
                .build();

    }

    public void setList(List<FileInfo_Results> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return (list == null)?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item, null);
            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.play = (ImageView) convertView.findViewById(R.id.play);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.size = (TextView) convertView.findViewById(R.id.desc);
            holder.path = (TextView) convertView.findViewById(R.id.path);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        FileInfo_Results item = list.get(position);
        holder.play.setVisibility(View.GONE);// 不可见
        // 显示图标
        if(item.type == ShowResults_Sensor.T_PIC){// 文件类型--- 图片:缩略图

            String path = item.path;
//            BitmapFactory.Options opt = new BitmapFactory.Options();
//            opt.inSampleSize = 4; // 缩放因子, 4--1/4
//            Bitmap bitmap = BitmapFactory.decodeFile(path,opt);
//            holder.icon.setImageBitmap(bitmap);
            String uri = "file://" + path;
            ImageLoader.getInstance().displayImage(uri,holder.icon,options,
                    new SimpleImageLoadingListener(){

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            // 加载中
                        }

                        @Override
                        public void onLoadingCancelled(String imageUri, View view) {
                            // 取消
                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                            // 加载失败
                        }

                        @Override
                        public void onLoadingStarted(String imageUri, View view) {
                            // 加载开始
                        }
                    });

        }else if(item.type == ShowResults_Sensor.T_VIDEO) {// 视频
            // 生成缩略图 Thumbnails.MICRO_KIND(最小的缩略图)
            Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(item.path,
                    MediaStore.Video.Thumbnails.MICRO_KIND);

            holder.icon.setImageBitmap(bitmap);// 设定ImageView的Bitmap
            holder.play.setVisibility(View.VISIBLE);// 可见
        }else{
            holder.icon.setImageResource(item.icon);// 图标
        }



        // 高亮关键字
        String key = ShowResults_Sensor.KEYWORD;
        if (key != null && !key.equals("") ) {
            int start = item.name.toLowerCase().indexOf(key.toLowerCase());//
            int end = start + key.length();
            // 标题
            SpannableStringBuilder style = new SpannableStringBuilder(item.name);
            style.setSpan(
                    new ForegroundColorSpan(Color.RED),// 前景样式
                    start,// 起始坐标
                    end,// 终止坐标
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE// 旗标
            );
            holder.name.setText(style);
        }else{
            holder.name.setText(item.name);
        }

        //holder.path.setText(item.path);
        holder.size.setText(item.size + " " + item.time);

        return convertView;
    }

    public class ViewHolder{
        ImageView icon;
        ImageView play;
        TextView name;
        TextView size;
        TextView path;
    }
}
