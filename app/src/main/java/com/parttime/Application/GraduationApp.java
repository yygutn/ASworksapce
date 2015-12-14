package com.parttime.Application;

import android.app.Application;
import cn.bmob.v3.Bmob;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.squareup.okhttp.OkHttpClient;

/**
 * Created by Jumy on 15/12/6 下午6:08.
 * deadline is the first productivity
 */
public class GraduationApp extends Application{
    private static final String TAG = GraduationApp.class.getSimpleName();

    public static final String API_HOST = "";
    public static final String Application_ID = "e408f24fa1fabefe818138015bdc8d3a";
    public static final String REST_API_Key = "5ff017d8fc7a8ab85f06749140d69054";
    public static final String Master_Key = "765cdf28387b3a90247ff0be1fdf23a0";
    public static final String Access_Key = "bff26b6202ff2714ea7d4b8c7f7c833e";
    public static final String Secret_Key = "c9c8ad301629c2e1";

    private static GraduationApp instance;

    public static GraduationApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
                .newBuilder(this,new OkHttpClient())
                .build();
        Fresco.initialize(this,config);
        Bmob.initialize(this,Application_ID);
    }
}
