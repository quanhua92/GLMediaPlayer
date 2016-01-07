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

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by quanhua on 06/01/2016.
 */
public class DefaultVideoRenderer extends BaseVideoRenderer{

    private static final String TAG = "DefaultVideoRenderer";
    private Video video;

    public DefaultVideoRenderer(Context context) {
        super(context);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        super.onSurfaceCreated(gl10, eglConfig);
        video = new Video(VERTEX_DATA);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        super.onDrawFrame(gl10);
        video.bindData(videoShaderProgram);
        video.draw();
    }

    @Override
    protected void updateVertexArray() {
        super.updateVertexArray();
        video.setVertexArray(VERTEX_DATA);
    }
}
