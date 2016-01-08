package com.quan404.glmediaplayer.players;

import android.media.MediaPlayer;
import android.view.Surface;

import java.io.IOException;

/**
 * Created by quanhua on 08/01/2016.
 */
public class CustomExoPlayer implements BasePlayer {
    @Override
    public void setDataSource(String dataSource) throws IOException {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public void pause() {

    }

    @Override
    public void start() {

    }

    @Override
    public void setSurface(Surface surface) {

    }

    @Override
    public void prepare() throws Exception {

    }

    @Override
    public void setOnVideoSizeChangedListener(MediaPlayer.OnVideoSizeChangedListener listener) {

    }
}
