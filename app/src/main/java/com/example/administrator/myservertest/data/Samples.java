package com.example.administrator.myservertest.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Samples {
    @NonNull
    private static final List<Sample> audioSamples;
    @NonNull
    private static final List<Sample> videoSamples;

    static {
        String audioImage = "https://ia902708.us.archive.org/3/items/count_monte_cristo_0711_librivox/Count_Monte_Cristo_1110.jpg?cnt=0";

        //Audio items
        audioSamples = new LinkedList<>();
        audioSamples.add(new Sample("ThemeAction 4067", "http://pandahome.ifjing.com/action.ashx/ThemeAction/4067", "{\"PageIndex\":2,\"PageSize\":3,\"Mo\":2}"));
        audioSamples.add(new Sample("Father and Son", "https://archive.org/download/count_monte_cristo_0711_librivox/count_of_monte_cristo_002_dumas.mp3", audioImage));
        audioSamples.add(new Sample("The Catalans", "https://archive.org/download/count_monte_cristo_0711_librivox/count_of_monte_cristo_003_dumas.mp3", audioImage));
        audioSamples.add(new Sample("Conspiracy", "https://archive.org/download/count_monte_cristo_0711_librivox/count_of_monte_cristo_004_dumas.mp3", audioImage));


        //Video items
        videoSamples = new ArrayList<>();
        videoSamples.add(new Sample("ThemeAction 4067", "http://pandahome.ifjing.com/action.ashx/ThemeAction/4067", "{\"PageIndex\":2,\"PageSize\":3,\"Mo\":2}"));
        videoSamples.add(new Sample("HLS - ArtBeats", "http://cdn-fms.rbs.com.br/vod/hls_sample1_manifest.m3u8"));
        videoSamples.add(new Sample("HLS - Sintel by Blender", "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"));
        videoSamples.add(new Sample("MKV - Android Screens", "http://storage.googleapis.com/exoplayer-test-media-1/mkv/android-screens-lavf-56.36.100-aac-avc-main-1280x720.mkv"));
        videoSamples.add(new Sample("MP4 (VP9) - Google Glass", "http://demos.webmproject.org/exoplayer/glass.mp4"));
        videoSamples.add(new Sample("MPEG DASH - Sintel by Blender", "https://bitdash-a.akamaihd.net/content/sintel/sintel.mpd"));
        videoSamples.add(new Sample("MPEG DASH - Big Buck Bunny by Blender, Live", "https://wowzaec2demo.streamlock.net/live/bigbuckbunny/manifest_mpm4sav_mvtime.mpd"));
        videoSamples.add(new Sample("Smooth Stream - Caminandes: Llama Drama by Blender", "http://amssamples.streaming.mediaservices.windows.net/634cd01c-6822-4630-8444-8dd6279f94c6/CaminandesLlamaDrama4K.ism/manifest"));
        videoSamples.add(new Sample("Smooth Stream - Tears of Steel Teaser by Blender", "http://amssamples.streaming.mediaservices.windows.net/3d7eaff9-39fa-442f-81cc-f2ea7db1797e/TearsOfSteelTeaser.ism/manifest"));
        videoSamples.add(new Sample("WEBM - Big Buck Bunny", "http://dl1.webmfiles.org/big-buck-bunny_trailer.webm"));
        videoSamples.add(new Sample("WEBM - Elephants Dream", "http://dl1.webmfiles.org/elephants-dream.webm"));
    }

    @NonNull
    public static List<Sample> getAudioSamples() {
        return audioSamples;
    }

    @NonNull
    public static List<Sample> getVideoSamples() {
        return videoSamples;
    }

    /**
     * A container for the information associated with a
     * sample media item.
     */
    public static class Sample {
        @NonNull
        private String title;
        @NonNull
        private String requestUrl;
        @Nullable
        private String requestBody;

        public Sample(@NonNull String title, @NonNull String requestUrl) {
            this(title, requestUrl, null);
        }

        public Sample(@NonNull String title, @NonNull String requestUrl, @Nullable String requestBody) {
            this.title = title;
            this.requestUrl = requestUrl;
            this.requestBody = requestBody;
        }

        @NonNull
        public String getTitle() {
            return title;
        }

        @NonNull
        public String getRequestUrl() {
            return requestUrl;
        }

        @Nullable
        public String getRequestBody() {
            return requestBody;
        }
    }
}
