package com.quan404.glmediaplayer.renderers;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;
import android.view.Surface;

import com.quan404.glmediaplayer.GLMediaPlayer;
import com.quan404.glmediaplayer.config.LogConfig;
import com.quan404.gltoolkit.objects.Video;
import com.quan404.gltoolkit.programs.VideoShaderProgram;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by quanhua on 06/01/2016.
 */
public class SplitVideoRenderer extends BaseVideoRenderer{

    private static final String TAG = "SplitVideoRenderer";
    private Video video_top;
    private Video video_bottom;

    public SplitVideoRenderer(Context context) {
        super(context);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        super.onSurfaceCreated(gl10, eglConfig);

        float[] VERTEX_DATA_TOP = {
                // X, Y, Z, U, V
                -1.0f, 0f, 0, 0.f, 0.f,
                1.0f, 0f, 0, 0.5f, 0.f,
                -1.0f,  1.0f, 0, 0.f, 1.f,
                1.0f,  1.0f, 0, 0.5f, 1.f,
        };
        video_top = new Video(VERTEX_DATA_TOP);
        float[] VERTEX_DATA_BOTTOM = {
                // X, Y, Z, U, V
                -1.0f, -1.0f, 0, 0.5f, 0.f,
                1.0f, -1.0f, 0, 1f, 0.f,
                -1.0f,  0f, 0, 0.5f, 1.f,
                1.0f,  0f, 0, 1f, 1.f,
        };
        video_bottom = new Video(VERTEX_DATA_BOTTOM);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        super.onDrawFrame(gl10);

        video_top.bindData(videoShaderProgram);
        video_top.draw();

        video_bottom.bindData(videoShaderProgram);
        video_bottom.draw();
    }
}
