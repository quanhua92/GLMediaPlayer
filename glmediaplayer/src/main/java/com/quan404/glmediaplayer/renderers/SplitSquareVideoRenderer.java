package com.quan404.glmediaplayer.renderers;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.quan404.gltoolkit.objects.Video;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by quanhua on 06/01/2016.
 */
public class SplitSquareVideoRenderer extends BaseVideoRenderer{

    private static final String TAG = "SplitSquareRenderer";
    private Video video_left;
    private Video video_right;

    private float curLeft = 0.1f;
    private float curWidth = 0.25f;

    public SplitSquareVideoRenderer(Context context) {
        super(context);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        super.onSurfaceCreated(gl10, eglConfig);

        video_left = new Video(getVideoLeftVertexData());
        video_right = new Video(getVideoRightVertexData());
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        super.onDrawFrame(gl10);

        video_left.bindData(videoShaderProgram);
        video_left.draw();

        video_right.bindData(videoShaderProgram);
        video_right.draw();
    }
    public float[] getVideoLeftVertexData(){

        float[] VERTEX_DATA = {
                // X, Y, Z, U, V
                -1.0f,-curHeight / 2, 0, curLeft, 0.f,
                0.0f, -curHeight / 2, 0, curLeft + curWidth, 0.f,
                -1.0f, curHeight / 2, 0, curLeft, 1.f,
                0.0f,  curHeight / 2, 0, curLeft + curWidth, 1.f,
        };
        return VERTEX_DATA;
    }
    public float[] getVideoRightVertexData(){
        float[] VERTEX_DATA = {
                // X, Y, Z, U, V
                0.0f, -curHeight / 2, 0, curLeft + 0.5f, 0.f,
                1.0f, -curHeight / 2, 0, curLeft + curWidth + 0.5f, 0.f,
                0.0f,  curHeight / 2, 0, curLeft + 0.5f, 1.f,
                1.0f,  curHeight / 2, 0, curLeft + curWidth + 0.5f, 1.f,
        };
        return VERTEX_DATA;
    }

    @Override
    protected void updateWidthHeight(){
        if (SURFACE_HEIGHT > 0 && VIDEO_HEIGHT > 0) {

            float new_width = VIDEO_HEIGHT * 2;
            float HEIGHT_IN_PX = VIDEO_HEIGHT * SURFACE_WIDTH / new_width;
            curHeight = HEIGHT_IN_PX * 2.0f / SURFACE_HEIGHT * 1.0f;

            if ( VIDEO_WIDTH > 2 * VIDEO_HEIGHT){
                // 3D side by side video
                curWidth = VIDEO_HEIGHT * 1.0f / (VIDEO_WIDTH * 1.0f);
            } else {
                // other video
                curWidth = 0.1f;
            }
            updateVertexArray();
        }
    }

    @Override
    protected void updateVertexArray(){
        video_left.setVertexArray(getVideoLeftVertexData());
        video_right.setVertexArray(getVideoRightVertexData());
    }
    public float getCurLeft(){
        return curLeft;
    }

    public void adjustLeftPosition(float delta) {
        float value = getCurLeft();
        value += delta;
        value = Math.min(Math.max(value, 0.0f), 0.5f - curWidth);

        curLeft = value;

        updateVertexArray();
    }
}
