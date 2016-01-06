package com.quan404.glmediaplayer.views;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.quan404.glmediaplayer.GLMediaPlayer;
import com.quan404.glmediaplayer.renderers.BaseVideoRenderer;
import com.quan404.glmediaplayer.renderers.DefaultVideoRenderer;

/**
 * Created by quanhua on 06/01/2016.
 */
public class VideoSurfaceView extends GLSurfaceView{

    private GLMediaPlayer mediaPlayer;
    private BaseVideoRenderer mRenderer;

    public VideoSurfaceView(Context context, GLMediaPlayer mp, BaseVideoRenderer renderer) {
        super(context);

        //TODO: Check if OpenGLES 2.0 is supported
        setEGLContextClientVersion(2);
        mediaPlayer = mp;
        mRenderer = renderer;
        setRenderer(mRenderer);

    }

}
