package com.quan404.glmediaplayer.renderers;

import android.content.Context;
import android.util.Log;

import com.quan404.gltoolkit.objects.Video;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by quanhua on 06/01/2016.
 */
public class SplitSquareVideoRenderer extends BaseVideoRenderer{

    private static final String TAG = "SplitVideoRenderer";
    private Video video_top;
    private Video video_bottom;

    private float curLeft = 0.1f;
    private final float curWidth = 0.25f;

    public SplitSquareVideoRenderer(Context context) {
        super(context);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        super.onSurfaceCreated(gl10, eglConfig);

        video_top = new Video(getTopVertexData());

        video_bottom = new Video(getBottomVertexData());
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        super.onDrawFrame(gl10);

        video_top.bindData(videoShaderProgram);
        video_top.draw();

        video_bottom.bindData(videoShaderProgram);
        video_bottom.draw();
    }
    public float[] getTopVertexData(){
        float[] VERTEX_DATA_TOP = {
                // X, Y, Z, U, V
                -1.0f, 0f, 0, curLeft, 0.f,
                1.0f, 0f, 0, curLeft + curWidth, 0.f,
                -1.0f,  1.0f, 0, curLeft, 1.f,
                1.0f,  1.0f, 0, curLeft + curWidth, 1.f,
        };
        return VERTEX_DATA_TOP;
    }
    public float[] getBottomVertexData(){
        float[] VERTEX_DATA_BOTTOM = {
                // X, Y, Z, U, V
                -1.0f, -1.0f, 0, curLeft + 0.5f, 0.f,
                1.0f, -1.0f, 0, curLeft + curWidth + 0.5f, 0.f,
                -1.0f,  0f, 0, curLeft + 0.5f, 1.f,
                1.0f,  0f, 0, curLeft + curWidth + 0.5f, 1.f,
        };
        return VERTEX_DATA_BOTTOM;
    }

    public float getCurLeft(){
        return curLeft;
    }

    public void adjustLeftPosition(float delta) {
        float value = getCurLeft();
        value += delta;
        value = Math.min(Math.max(value, 0.0f), 0.5f - curWidth);

        curLeft = value;

        video_top.setVertexArray(getTopVertexData());
        video_bottom.setVertexArray(getBottomVertexData());
    }
}
