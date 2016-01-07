package com.quan404.split_translation_video_player;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.quan404.glmediaplayer.GLMediaPlayer;
import com.quan404.glmediaplayer.renderers.SplitSquareVideoRenderer;
import com.quan404.glmediaplayer.renderers.SplitVideoRenderer;

public class MainActivity extends Activity {

    private ViewGroup videoLayout;
    private GLMediaPlayer glMediaPlayer = null;

    private SplitSquareVideoRenderer renderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoLayout = (ViewGroup) findViewById(R.id.video);

        renderer = new SplitSquareVideoRenderer(this);
        glMediaPlayer = new GLMediaPlayer(this, renderer);
        try{
//            glMediaPlayer.setDataSource("http://html5demos.com/assets/dizzy.mp4");
//            glMediaPlayer.setDataSource("http://d2kzl73ve6fjh6.cloudfront.net/videos/teleport_22-11-2015_13-11-40__8kX6dwsyML.mp4");
//            glMediaPlayer.setDataSource("http://d2kzl73ve6fjh6.cloudfront.net/videos/teleport_22-11-2015_13-22-00__8kX6dwsyML.mp4");
            glMediaPlayer.setDataSource("http://d2kzl73ve6fjh6.cloudfront.net/videos/teleport_22-11-2015_14-00-08__8kX6dwsyML.mp4");
//            glMediaPlayer.setDataSource("http://storage.googleapis.com/exoplayer-test-media-1/mkv/android-screens-lavf-56.36.100-aac-avc-main-1280x720.mkv");

            // Emulator can only play 3GP files.
            // More files: http://download.wavetlan.com/SVV/Media/HTTP/http-3gp.htm
//            glMediaPlayer.setDataSource("http://download.wavetlan.com/SVV/Media/HTTP/3GP/QuickTime/Quicktime_test1_3GP2_H263_xbit_176x144_AR1.22_25fps_KF1in15_256kbps_AAC_Mono_48000Hz_96kbps.3g2");
        }catch (Exception e){
            e.printStackTrace();
        }

        videoLayout.addView(glMediaPlayer.getVideoSurfaceView());

        findViewById(R.id.left).setOnClickListener(leftButtonOnClick);
        findViewById(R.id.right).setOnClickListener(rightButtonOnClick);

    }

    View.OnClickListener leftButtonOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            renderer.adjustLeftPosition(-0.01f);
        }
    };
    View.OnClickListener rightButtonOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            renderer.adjustLeftPosition(0.01f);
        }
    };

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
