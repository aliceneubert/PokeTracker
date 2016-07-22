package biz.raspbert.zacneubert.poketracker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import java.util.Objects;

import biz.raspbert.zacneubert.poketracker.R;
import biz.raspbert.zacneubert.poketracker.Settings.CheckboxSetting_MuteSound;

/**
 * Created by zacneubert on 3/2/16.
 */
public class Music {
    private MediaPlayer mediaPlayer;
    public Context context;
    public boolean loop = true;
    public int musicID = -1;

    private static Music BackgroundMusic = null;

    public Music(Context context, int MusicID, boolean loop) {
        this.musicID = MusicID;
        this.context = context;
        this.loop = loop;
    }

    public void initialize() {
        mediaPlayer = MediaPlayer.create(context, musicID);
        mediaPlayer.setLooping(loop);
        if(!loop) {
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.reset();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
            });
        }
    }

    public void destroy() {
        if(mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void start(Context context) {
        if(!(new CheckboxSetting_MuteSound().soundMuted(context))) {
            mediaPlayer.start();
        }
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public static void pauseBackgroundMusic() {
        BackgroundMusic.pause();
    }

    public static void playSelectSound(Context context) {
        Music m = new Music(context, R.raw.confirm, false);
        m.initialize();
        m.start(context);
    }

    public static void playSoundById(Context context, int id) {
        if(new CheckboxSetting_MuteSound().soundMuted(context)) return;
        Music m = new Music(context, id, false);
        m.initialize();
        m.start(context);
    }

    public static Music playLoopingSoundById(Context context, int id) {
        if(new CheckboxSetting_MuteSound().soundMuted(context)) return null;
        Music m = new Music(context, id, true);
        m.initialize();
        m.start(context);
        return m;
    }

    public static void startBackgroundMusic(Context context) {
        if(BackgroundMusic == null) {
            PlayBackgroundMusic(context);
        }
        else {
            BackgroundMusic.start(context);
        }
    }

    public boolean toggle() {
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            return false;
        }
        else {
            mediaPlayer.start();
            return true;
        }
    }

    public static void BackgroundMusicChanged(int NewMusic) {
        BackgroundMusic.musicID = NewMusic;
        BackgroundMusic.destroy();
        BackgroundMusic.initialize();
        BackgroundMusic.start(BackgroundMusic.context);
    }

    public static void PlayBackgroundMusic(Context context) {
        if(BackgroundMusic != null) {
            BackgroundMusic.destroy();
        }
        //int mid = Setting_Music_Choice.getMusicId(context);
        //BackgroundMusic = new Music(context, mid, true);
        //BackgroundMusic.initialize();
        //BackgroundMusic.start(context);
    }
}
