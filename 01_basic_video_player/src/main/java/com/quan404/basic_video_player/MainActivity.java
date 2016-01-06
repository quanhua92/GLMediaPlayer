package com.quan404.basic_video_player;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.quan404.glmediaplayer.GLMediaPlayer;
import com.quan404.glmediaplayer.views.VideoSurfaceView;

public class MainActivity extends AppCompatActivity {

    private GLMediaPlayer glMediaPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glMediaPlayer = new GLMediaPlayer(this);
        try{
            glMediaPlayer.setDataSource("http://html5demos.com/assets/dizzy.mp4");
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
