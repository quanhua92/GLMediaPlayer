package com.quan404.basic_custom_exo_player;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.quan404.glmediaplayer.GLMediaPlayer;

public class MainActivity extends AppCompatActivity {

    private GLMediaPlayer glMediaPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glMediaPlayer = new GLMediaPlayer(this, true);
        try{
            glMediaPlayer.setDataSource("http://html5demos.com/assets/dizzy.mp4");
//            glMediaPlayer.setDataSource("http://d2kzl73ve6fjh6.cloudfront.net/videos/teleport_22-11-2015_13-11-40__8kX6dwsyML.mp4");
//            glMediaPlayer.setDataSource("http://d2kzl73ve6fjh6.cloudfront.net/videos/teleport_22-11-2015_14-00-08__8kX6dwsyML.mp4");
        }catch (Exception e){
            e.printStackTrace();
        }

        setContentView(glMediaPlayer.getVideoSurfaceView());

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(glMediaPlayer != null){
            glMediaPlayer.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(glMediaPlayer != null){
            glMediaPlayer.pause();
        }
    }
}
