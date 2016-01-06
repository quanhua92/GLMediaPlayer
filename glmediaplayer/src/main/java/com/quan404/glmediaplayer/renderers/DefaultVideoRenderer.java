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
public class DefaultVideoRenderer implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener{

    private static final String TAG = "DefaultVideoRenderer";

    private GLMediaPlayer mediaPlayer;
    private static int GL_TEXTURE_EXTERNAL_OES = 0x8D65;


    // Properties
    private Context context;
    private VideoShaderProgram videoShaderProgram;
    private int mTextureID;
    private SurfaceTexture mSurface;
    private boolean updateSurface = false;

    // Renderer Properties
    private Video video;
    private float[] mMVPMatrix = new float[16];
    private float[] mSTMatrix = new float[16];

    public DefaultVideoRenderer(Context context) {
        this.context = context;
    }
    public void setMediaPlayer(GLMediaPlayer player){
        if (LogConfig.ON) {
            Log.d(TAG, "setMediaPlayer");
        }
        this.mediaPlayer = player;
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

        video = new Video();

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
        mediaPlayer.setSurface(surface);
        mediaPlayer.setScreenOnWhilePlaying(true);
        surface.release();

        mediaPlayer.prepare();

        synchronized (this){
            updateSurface = false;
        }
        mediaPlayer.start();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        if (LogConfig.ON) {
            Log.d(TAG, "onSurfaceChanged : width " + width + " height " + height);
        }

        // Set the OpenGL viewport to file the entire surface
        GLES20.glViewport(0, 0, width, height);

        final float aspectRatio = width > height ? (float) width / (float) height : (float) height / (float)width;

        if (width > height){
            // Landscape
            Matrix.orthoM(mMVPMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f);
        } else{
            // Portrait
            Matrix.orthoM(mMVPMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        synchronized (this){
            if(updateSurface){
                mSurface.updateTexImage();
                mSurface.getTransformMatrix(mSTMatrix);
                updateSurface = false;
            }
        }

        GLES20.glClearColor(0.0f, 0.0f, 0.5f, 1.0f);
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

//        Matrix.setIdentityM(mMVPMatrix, 0);

        videoShaderProgram.useProgram();
        videoShaderProgram.setUniforms(mMVPMatrix, mSTMatrix);

        video.bindData(videoShaderProgram);
        video.draw();
    }
}
