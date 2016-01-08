package com.quan404.glmediaplayer.players;

import android.media.MediaPlayer;
import android.view.Surface;

import java.io.IOException;

/**
 * Created by quanhua on 08/01/2016.
 */
public class AndroidPlayer implements BasePlayer, MediaPlayer.OnVideoSizeChangedListener {
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private BasePlayer.OnVideoSizeChangedListener listener;
    @Override
    public void setDataSource(String dataSource) throws IOException {
        mediaPlayer.setDataSource(dataSource);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public void setSurface(Surface surface) {
        mediaPlayer.setSurface(surface);
    }

    @Override
    public void prepare() throws Exception {
        mediaPlayer.prepare();
    }

    @Override
    public void setOnVideoSizeChangedListener(OnVideoSizeChangedListener listener) {
        this.listener = listener;
        mediaPlayer.setOnVideoSizeChangedListener(this);
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int width, int height) {
        this.listener.onVideoSizeChanged(width, height);
    }
}
