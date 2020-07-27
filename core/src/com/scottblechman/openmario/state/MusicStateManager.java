package com.scottblechman.openmario.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicStateManager {

    private Music music;
    private MusicState state;

    public MusicStateManager() {
        this.state = MusicState.NONE;
    }

    public void setState(MusicState state) {
        this.state = state;
        updateMusic();
    }

    public MusicState getState() {
        return state;
    }

    public void updateMusic() {
        switch (state) {
            case OVERWORLD:
                music = Gdx.audio.newMusic(Gdx.files.internal("music/overworld.mp3"));
                music.setLooping(true);
            case NONE:
            default:
                break;
        }
    }

    public void play() {
        music.play();
    }

    public void stop() {
        music.stop();
    }

    public void dispose() {
        music.dispose();
    }
}
