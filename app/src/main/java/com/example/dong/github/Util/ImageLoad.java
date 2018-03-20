package com.example.dong.github.Util;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by 振兴 on 2018.2.19.
 */

public class ImageLoad {
    public static void load(Context context, String uri, ImageView imageView){
        Glide.with(context).load(uri).into(imageView);
    }
}
