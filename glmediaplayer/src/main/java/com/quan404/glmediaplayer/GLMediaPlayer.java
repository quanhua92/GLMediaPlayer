package com.quan404.glmediaplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Surface;

import com.quan404.glmediaplayer.config.LogConfig;
import com.quan404.glmediaplayer.players.AndroidPlayer;
import com.quan404.glmediaplayer.players.BasePlayer;
import com.quan404.glmediaplayer.players.CustomExoPlayer;
import com.quan404.glmediaplayer.renderers.BaseVideoRenderer;
import com.quan404.glmediaplayer.renderers.DefaultVideoRenderer;
import com.quan404.glmediaplayer.views.VideoSurfaceView;

import java.io.IOException;

/**
 * Created by quanhua on 06/01/2016.
 */
public class GLMediaPlayer {
    private final static String TAG = "GLMediaPlayer";
    private BasePlayer mediaPlayer = null;
    private Context context = null;
    private VideoSurfaceView mVideoView;

    //=================== + Constructors + ===================
    public GLMediaPlayer(Context context) {
        this(context, new DefaultVideoRenderer(context), false);
    }

    public GLMediaPlayer(Context context, BaseVideoRenderer renderer, boolean useExoPlayer) {
        if(renderer == null) {
            if(LogConfig.ON){
                Log.e(TAG, "Renderer is null");
            }
            return;
        }
        if(useExoPlayer){
            this.mediaPlayer = new CustomExoPlayer();
        }else{
            this.mediaPlayer = new AndroidPlayer();
        }

        this.context = context;
        this.mVideoView = new VideoSurfaceView(context, renderer);
        renderer.setGlMediaPlayer(this);
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

        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    public void resume(){
        if(LogConfig.ON){
            Log.d(TAG, "resume()");
        }
        // TODO: Resume player

        if(mVideoView != null){
            if( mVideoView.getRenderer().isReady()){
                this.mediaPlayer.start();
            }
        }
    }

    public void setSurface(Surface surface) {
        this.mediaPlayer.setSurface(surface);
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
    public void setOnVideoSizeChangedListener(MediaPlayer.OnVideoSizeChangedListener listener){
        this.mediaPlayer.setOnVideoSizeChangedListener(listener);
    }
}
