package com.quan404.split_video_player;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.quan404.glmediaplayer.GLMediaPlayer;
import com.quan404.glmediaplayer.renderers.SplitVideoRenderer;

public class MainActivity extends AppCompatActivity {

    private GLMediaPlayer glMediaPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glMediaPlayer = new GLMediaPlayer(this, new SplitVideoRenderer(this));
        try{
//            glMediaPlayer.setDataSource("http://html5demos.com/assets/dizzy.mp4");
//            glMediaPlayer.setDataSource("http://d2kzl73ve6fjh6.cloudfront.net/videos/teleport_22-11-2015_13-11-40__8kX6dwsyML.mp4");
            glMediaPlayer.setDataSource("http://storage.googleapis.com/exoplayer-test-media-1/mkv/android-screens-lavf-56.36.100-aac-avc-main-1280x720.mkv");
        }catch (Exception e){
            e.printStackTrace();
        }

        setContentView(glMediaPlayer.getVideoSurfaceView());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(glMediaPlayer != null){
            glMediaPlayer.pause();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(glMediaPlayer != null){
            glMediaPlayer.resume();
        }
    }
}
