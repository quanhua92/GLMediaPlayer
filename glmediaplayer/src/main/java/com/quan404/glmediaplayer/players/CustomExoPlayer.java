package com.quan404.glmediaplayer.players;

import com.google.android.exoplayer.DummyTrackRenderer;
import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.MediaCodecTrackRenderer;
import com.google.android.exoplayer.MediaCodecVideoTrackRenderer;
import com.google.android.exoplayer.TrackRenderer;
import com.google.android.exoplayer.audio.AudioCapabilities;
import com.google.android.exoplayer.audio.AudioTrack;
import com.google.android.exoplayer.extractor.ExtractorSampleSource;
import com.google.android.exoplayer.upstream.Allocator;
import com.google.android.exoplayer.upstream.BandwidthMeter;
import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.DefaultAllocator;
import com.google.android.exoplayer.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer.upstream.DefaultUriDataSource;
import com.google.android.exoplayer.util.Util;
import com.quan404.glmediaplayer.config.LogConfig;
import com.quan404.glmediaplayer.players.exorenderers.TextureVideoTrackRenderer;

import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Surface;

import java.io.IOException;

/**
 * Created by quanhua on 08/01/2016.
 */
public class CustomExoPlayer implements BasePlayer, TextureVideoTrackRenderer.EventListener, MediaCodecAudioTrackRenderer.EventListener {
    private static final String TAG = "CustomExoPlayer";
    private ExoPlayer player;
    private TrackRenderer videoRenderer;
    private Handler mainHandler;
    private Context context;
    private String userAgent;
    private String dataPath;
    private Surface surface;
    private static final int BUFFER_SEGMENT_SIZE = 64 * 1024;
    private static final int BUFFER_SEGMENT_COUNT = 256;
    public static final int RENDERER_COUNT = 2;
    public static final int TYPE_VIDEO = 0;
    public static final int TYPE_AUDIO = 1;

    public CustomExoPlayer(Context context) {
        this.context = context;
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.userAgent = Util.getUserAgent(context, "ExoPlayerDemo");

        // 1. Instantiate the player.
        player = ExoPlayer.Factory.newInstance(2);
//        // 2. Construct renderers.
//        MediaCodecVideoTrackRenderer videoRenderer = ...
//        MediaCodecAudioTrackRenderer audioRenderer = ...
//        // 3. Inject the renderers through prepare.
//        player.prepare(videoRenderer, audioRenderer);
//        // 4. Pass the surface to the video renderer.
//        player.sendMessage(videoRenderer, MediaCodecVideoTrackRenderer.MSG_SET_SURFACE, surface);
//        // 5. Start playback.
//        player.setPlayWhenReady(true);
    }

    @Override
    public void setDataSource(String dataPath) throws IOException {
        if(LogConfig.ON){
            Log.d(TAG, "setDataSource " + dataPath);
        }
        this.dataPath = dataPath;
    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public void pause() {
//        this.player.stop();
    }

    @Override
    public void start() {
    }

    @Override
    public void setSurface(Surface surface) {
        if(LogConfig.ON){
            Log.d(TAG, "setSurface " + surface);
        }
        this.surface = surface;
    }

    @Override
    public void prepare() throws Exception {
        Uri uri = Uri.parse(dataPath);
        Allocator allocator = new DefaultAllocator(BUFFER_SEGMENT_SIZE);
        // Build the video and audio renderers.
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter(getMainHandler(), null);
        DataSource dataSource = new DefaultUriDataSource(context, bandwidthMeter, userAgent);
        ExtractorSampleSource sampleSource = new ExtractorSampleSource(uri, dataSource, allocator,
                BUFFER_SEGMENT_COUNT * BUFFER_SEGMENT_SIZE, null);


        TextureVideoTrackRenderer videoRenderer = new TextureVideoTrackRenderer(context, sampleSource, MediaCodec.VIDEO_SCALING_MODE_SCALE_TO_FIT, 5000
                , null, true, getMainHandler(), this, 50);

        MediaCodecAudioTrackRenderer audioRenderer = new MediaCodecAudioTrackRenderer(sampleSource,
                null, true, getMainHandler(), this, AudioCapabilities.getCapabilities(context));

        // Invoke the callback.
        TrackRenderer[] renderers = new TrackRenderer[RENDERER_COUNT];
        renderers[TYPE_VIDEO] = videoRenderer;
        renderers[TYPE_AUDIO] = audioRenderer;

        player.sendMessage(
                videoRenderer, MediaCodecVideoTrackRenderer.MSG_SET_SURFACE, surface);
        Log.e(TAG, "after send Message");
//        onRenderers(renderers, bandwidthMeter);
        player.prepare(renderers);
        player.setPlayWhenReady(true);
    }

    @Override
    public void setOnVideoSizeChangedListener(MediaPlayer.OnVideoSizeChangedListener listener) {

    }

    public Handler getMainHandler(){
        return mainHandler;
    }

    @Override
    public void onDroppedFrames(int count, long elapsed) {

    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        if(LogConfig.ON){
            Log.d(TAG, "onVideoSizeChanged : " + width + " " + height);
        }
    }

    @Override
    public void onDrawnToSurface(Surface surface) {
        if(LogConfig.ON){
            Log.d(TAG, "onDrawnToSurface : " + surface);
        }
    }

    @Override
    public void onDecoderInitializationError(MediaCodecTrackRenderer.DecoderInitializationException e) {

    }

    @Override
    public void onCryptoError(MediaCodec.CryptoException e) {

    }

    @Override
    public void onDecoderInitialized(String decoderName, long elapsedRealtimeMs, long initializationDurationMs) {

    }

    @Override
    public void onAudioTrackInitializationError(AudioTrack.InitializationException e) {

    }

    @Override
    public void onAudioTrackWriteError(AudioTrack.WriteException e) {

    }

    @Override
    public void onAudioTrackUnderrun(int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {

    }

    public void onRenderers(TrackRenderer[] renderers, BandwidthMeter bandwidthMeter) {
        for (int i = 0; i < RENDERER_COUNT; i++) {
            if (renderers[i] == null) {
                Log.e(TAG, "onRenderers dummy " + i);
                // Convert a null renderer to a dummy renderer.
                renderers[i] = new DummyTrackRenderer();
            }
        }
        // Complete preparation.
        this.videoRenderer = renderers[TYPE_VIDEO];
        pushSurface(false);
        player.prepare(renderers);
    }
    private void pushSurface(boolean blockForSurfacePush) {
        Log.e(TAG, "pushSurface " + blockForSurfacePush);
        if (videoRenderer == null) {
            Log.e(TAG, "pushSurface (videoRenderer == null)");
            return;
        }
        if (surface == null) {
            Log.e(TAG, "pushSurface (surface == null)");
            return;
        }

        Log.e(TAG, "pushSurface " + blockForSurfacePush + " to " + videoRenderer);
        if (blockForSurfacePush) {
            player.blockingSendMessage(
                    videoRenderer, MediaCodecVideoTrackRenderer.MSG_SET_SURFACE, surface);
        } else {
            player.sendMessage(
                    videoRenderer, MediaCodecVideoTrackRenderer.MSG_SET_SURFACE, surface);
        }
    }
}
