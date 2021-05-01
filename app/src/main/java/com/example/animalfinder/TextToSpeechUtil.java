package com.example.animalfinder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import java.util.Locale;

/**
 * Created by mdev on 1/10/2018 AD.
 * <p>
 * How to use:
 * <p>
 *  TextToSpeechUtil.getInstance(getApplicationContext())
 *  .setEngine(TextToSpeechUtil.ENGINE_GOOGLE_ANDROID_TTS_PKG)
 *  .setLocale(new Locale(TextToSpeechUtil.LANGUAGE_TH))
 *  .setSpeed(1.5f)
 *  .setPitch(1.5f)
 *  .speak("สวัสดี");
 */

public class TextToSpeechUtil extends UtteranceProgressListener implements TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener {

    private Context context;
    private TextToSpeech tts;
    private Locale locale = Locale.getDefault();
    private String enginePackageName;
    private String message;
    private boolean isRunning;
    private int speakCount;
    private float speed = -1;
    private float pitch = -1;

    public TextToSpeechUtil(Context context) {
        this.context = context;
    }

    public void speak(String message) {
        this.message = message;

        if (tts == null || !isRunning) {
            speakCount = 0;

            if (enginePackageName != null && !enginePackageName.isEmpty()) {
                tts = new TextToSpeech(context, this, enginePackageName);
            } else {
                tts = new TextToSpeech(context, this);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                tts.setOnUtteranceProgressListener(this);
            } else {
                tts.setOnUtteranceCompletedListener(this);
            }

            isRunning = true;
        } else {
            startSpeak();
        }
    }

    public TextToSpeechUtil setEngine(String packageName) {
        enginePackageName = packageName;
        return this;
    }

    public TextToSpeechUtil setLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    public TextToSpeechUtil setSpeed(float speed) {
        this.speed = speed;
        return this;
    }

    public TextToSpeechUtil setPitch(float pitch) {
        this.pitch = pitch;
        return this;
    }

    private void startSpeak() {
        speakCount++;

        if (locale != null) {
            tts.setLanguage(locale);
        }

        if (speed > -1) {
            tts.setSpeechRate(speed);
        }

        if (pitch > -1) {
            tts.setPitch(pitch);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null, "");
        } else {
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private void clear() {
        speakCount--;

        if (speakCount == 0) {
            tts.shutdown();
            isRunning = false;
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            startSpeak();
        }
    }

    @Override
    public void onStart(String utteranceId) {
    }

    @Override
    public void onDone(String utteranceId) {
        clear();
    }

    @Override
    public void onError(String utteranceId) {
        clear();
    }

    @Override
    public void onUtteranceCompleted(String utteranceId) {
        clear();
    }

    @SuppressLint("StaticFieldLeak")
    private static TextToSpeechUtil instance;

    public static TextToSpeechUtil getInstance(Context context) {
        return instance == null ? new TextToSpeechUtil(context) : instance;
    }

    public static final String LANGUAGE_DE = "de_DE";
    public static final String LANGUAGE_GB = "en_GB";
    public static final String LANGUAGE_US = "en_US";
    public static final String LANGUAGE_ES = "es_ES";
    public static final String LANGUAGE_FR = "fr_FR";
    public static final String LANGUAGE_IT = "it_IT";
    public static final String LANGUAGE_RU = "ru_RU";
    public static final String LANGUAGE_TH = "th_TH";

    public static final String ENGINE_GOOGLE_ANDROID_TTS_PKG = "com.google.android.tts";
}