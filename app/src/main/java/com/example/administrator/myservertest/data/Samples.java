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

    public static final String HOST = "http://api.zlauncher.cn/";
    static {
        String audioImage = "https://ia902708.us.archive.org/3/items/count_monte_cristo_0711_librivox/Count_Monte_Cristo_1110.jpg?cnt=0";

        //Audio items
        audioSamples = new LinkedList<>();
        audioSamples.add(new Sample("ThemeAction 4067", HOST+"action.ashx/commonaction/1", "{\"PageIndex\":2,\"PageSize\":3,\"Mo\":2}"));
        audioSamples.add(new Sample("Father and Son", "https://archive.org/download/count_monte_cristo_0711_librivox/count_of_monte_cristo_002_dumas.mp3", audioImage));
        audioSamples.add(new Sample("The Catalans", "https://archive.org/download/count_monte_cristo_0711_librivox/count_of_monte_cristo_003_dumas.mp3", audioImage));
        audioSamples.add(new Sample("Conspiracy", "https://archive.org/download/count_monte_cristo_0711_librivox/count_of_monte_cristo_004_dumas.mp3", audioImage));

        //Video items
        videoSamples = new ArrayList<>();
        videoSamples.add(new Sample("获取服务端当前日期时间(1)", HOST+"action.ashx/commonaction/1", ""));
        videoSamples.add(new Sample("获取后台配置的参数信息(2)", HOST+"action.ashx/commonaction/2","{\"paramname\":\"Config001\",\"ver\":\"0\"}"));
        videoSamples.add(new Sample("获取消息推送(3)", HOST+"action.ashx/commonaction/3",""));
        videoSamples.add(new Sample("下载测试(4)", "",""));
        videoSamples.add(new Sample("获取天气城市(101)", HOST+"action.ashx/weather/101","{\"longitude\":\"116.4\",\"latitude\":\"39.1\"}"));
        videoSamples.add(new Sample("获取实况天气(102)", HOST+"action.ashx/weather/102","{\"location\":\"CN101010100\"}"));
        videoSamples.add(new Sample("获取天气预报(103)", HOST+"action.ashx/weather/103","{\"location\":\"CN101010100\"}"));
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
