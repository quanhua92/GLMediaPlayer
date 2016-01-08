package com.quan404.glmediaplayer.players;

import android.media.MediaPlayer;
import android.view.Surface;

import java.io.IOException;

/**
 * Created by quanhua on 08/01/2016.
 */
public interface BasePlayer {
    void setDataSource(String dataSource) throws IOException;

    boolean isPlaying();

    void pause();

    void start();

    void setSurface(Surface surface);

    void prepare() throws Exception;

    void setOnVideoSizeChangedListener(MediaPlayer.OnVideoSizeChangedListener listener);
}
