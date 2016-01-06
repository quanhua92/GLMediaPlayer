package com.quan404.glmediaplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Surface;

import com.quan404.glmediaplayer.config.LogConfig;
import com.quan404.glmediaplayer.renderers.DefaultVideoRenderer;
import com.quan404.glmediaplayer.views.VideoSurfaceView;

import java.io.IOException;

/**
 * Created by quanhua on 06/01/2016.
 */
public class GLMediaPlayer {
    private final static String TAG = "GLMediaPlayer";
    private MediaPlayer mediaPlayer = null;
    private Context context = null;
    private VideoSurfaceView mVideoView;

    //=================== + Constructors + ===================
    public GLMediaPlayer(Context context) {
        this(context, new DefaultVideoRenderer(context));
    }

    public GLMediaPlayer(Context context, DefaultVideoRenderer renderer) {
        if(renderer == null) {
            if(LogConfig.ON){
                Log.e(TAG, "Renderer is null");
            }
            return;
        }
        this.mediaPlayer = new MediaPlayer();
        this.context = context;
        this.mVideoView = new VideoSurfaceView(context, this, renderer);
        renderer.setMediaPlayer(this);
    }

    //=================== + Getter + ===================

    public VideoSurfaceView getVideoSurfaceView(){
        return mVideoView;
    }

    //=================== + Setter + ===================
    public void setDataSource(String dataSource) {
        if (mediaPlayer != null){
            try {
                mediaPlayer.setDataSource(dataSource);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            if(LogConfig.ON){
                Log.e(TAG, "setDataSource: mediaPlayer == null");
            }
        }
    }

    public void pause(){
        if(LogConfig.ON){
            Log.d(TAG, "pause()");
        }
    }

    public void resume(){
        if(LogConfig.ON){
            Log.d(TAG, "resume()");
        }
    }

    public void setSurface(Surface surface) {
        this.mediaPlayer.setSurface(surface);
    }

    public void setScreenOnWhilePlaying(boolean b) {
        this.mediaPlayer.setScreenOnWhilePlaying(b);
    }

    public void prepare() {
        try{
            this.mediaPlayer.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void start() {
        try{
            this.mediaPlayer.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
