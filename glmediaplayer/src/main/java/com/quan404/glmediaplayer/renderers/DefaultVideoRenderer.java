package com.quan404.glmediaplayer.renderers;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.quan404.glmediaplayer.GLMediaPlayer;
import com.quan404.glmediaplayer.config.LogConfig;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by quanhua on 06/01/2016.
 */
public class DefaultVideoRenderer implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener{

    private static final String TAG = "DefaultVideoRenderer";

    private GLMediaPlayer glMediaPlayer;
    public DefaultVideoRenderer(Context context) {

    }
    public void setMediaPlayer(GLMediaPlayer player){
        if (LogConfig.ON) {
            Log.d(TAG, "setMediaPlayer");
        }
        this.glMediaPlayer = player;
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {

    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        if (LogConfig.ON) {
            Log.d(TAG, "onSurfaceCreated");
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        if (LogConfig.ON) {
            Log.d(TAG, "onSurfaceChanged : width " + width + " height " + height);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl10) {

    }
}
