package com.quan404.glmediaplayer.renderers;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;
import android.view.Surface;

import com.quan404.glmediaplayer.GLMediaPlayer;
import com.quan404.glmediaplayer.config.LogConfig;
import com.quan404.glmediaplayer.players.BasePlayer;
import com.quan404.gltoolkit.programs.VideoShaderProgram;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by quanhua on 06/01/2016.
 */
public class BaseVideoRenderer implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener, BasePlayer.OnVideoSizeChangedListener {

    private static String TAG = "BaseVideoRenderer";
    private GLMediaPlayer glMediaPlayer;
    private boolean MEDIA_PLAYER_READY = false;
    private static int GL_TEXTURE_EXTERNAL_OES = 0x8D65;

    // Properties
    protected Context context;
    protected VideoShaderProgram videoShaderProgram;
    private int mTextureID;
    private SurfaceTexture mSurface;
    private boolean updateSurface = false;

    protected float curHeight = 1.0f;
    protected int VIDEO_WIDTH = -1;
    protected int VIDEO_HEIGHT = -1;
    protected int SURFACE_WIDTH = -1;
    protected int SURFACE_HEIGHT = -1;

    // Renderer Properties
    private float[] mMVPMatrix = new float[16];
    private float[] mSTMatrix = new float[16];

    protected float[] VERTEX_DATA = {
            // X, Y, Z, U, V
            -1.0f, -1.0f, 0, 0.f, 0.f,
            1.0f, -1.0f, 0, 1f, 0.f,
            -1.0f,  1.0f, 0, 0.f, 1.f,
            1.0f,  1.0f, 0, 1f, 1.f,
    };

    public BaseVideoRenderer(Context context) {
        this.context = context;
    }
    public void setGlMediaPlayer(GLMediaPlayer player){
        if (LogConfig.ON) {
            Log.d(TAG, "setGlMediaPlayer");
        }
        this.glMediaPlayer = player;
    }

    @Override
    synchronized public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        updateSurface = true;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        if (LogConfig.ON) {
            Log.d(TAG, "onSurfaceCreated");
        }

        videoShaderProgram = new VideoShaderProgram(
                context,
                VideoShaderProgram.DEFAULT_VERTEX_SHADER,
                VideoShaderProgram.DEFAULT_FRAGMENT_SHADER);

        int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, 0);
        mTextureID = textures[0];
        GLES20.glBindTexture(GL_TEXTURE_EXTERNAL_OES, mTextureID);

        GLES20.glTexParameterf(GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        // Create SurfaceTexture that will feed this textureId and pass to MediaPlayer
        mSurface = new SurfaceTexture(mTextureID);
        mSurface.setOnFrameAvailableListener(this);

        Surface surface = new Surface(mSurface);
        glMediaPlayer.setSurface(surface);
//        surface.release();
        glMediaPlayer.prepare();
        glMediaPlayer.setOnVideoSizeChangedListener(this);

        synchronized (this){
            updateSurface = false;
        }
        MEDIA_PLAYER_READY = true;
        glMediaPlayer.start();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        if (LogConfig.ON) {
            Log.d(TAG, "onSurfaceChanged : width " + width + " height " + height);
        }

        SURFACE_WIDTH = width;
        SURFACE_HEIGHT = height;

        // Set the OpenGL viewport to file the entire surface
        GLES20.glViewport(0, 0, width, height);
        Matrix.setIdentityM(mMVPMatrix, 0);

        updateWidthHeight();
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        synchronized (this) {
            if(updateSurface){
                mSurface.updateTexImage();
                mSurface.getTransformMatrix(mSTMatrix);
                updateSurface = false;
            }
        }

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

        videoShaderProgram.useProgram();
        videoShaderProgram.setUniforms(mMVPMatrix, mSTMatrix);
    }

    @Override
    public void onVideoSizeChanged(int width, int height) {
        VIDEO_WIDTH = width;
        VIDEO_HEIGHT = height;

        updateWidthHeight();
    }

    protected void updateWidthHeight(){
        if (SURFACE_HEIGHT > 0 && VIDEO_HEIGHT > 0) {

            float HEIGHT_IN_PX = VIDEO_HEIGHT * SURFACE_WIDTH / VIDEO_WIDTH;
            curHeight = HEIGHT_IN_PX * 2.0f / SURFACE_HEIGHT * 1.0f;

            updateVertexArray();
        }
    }

    protected void updateVertexArray(){
        float[] NEW_VERTEX_DATA = {
                // X, Y, Z, U, V
                -1.0f, -curHeight / 2 , 0, 0.f, 0.f,
                1.0f, -curHeight / 2, 0, 1f, 0.f,
                -1.0f,  curHeight / 2, 0, 0.f, 1.f,
                1.0f,  curHeight / 2, 0, 1f, 1.f,
        };
        System.arraycopy(NEW_VERTEX_DATA, 0 , VERTEX_DATA, 0, NEW_VERTEX_DATA.length );
    }

    public boolean isReady(){
        return MEDIA_PLAYER_READY;
    }
}