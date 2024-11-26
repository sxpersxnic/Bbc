package bbcspaceinvaders.game;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Sound {

    // Need to be an Instance-Variable for the music,
    // else the Garbage Collector stop the music.
    private static MediaPlayer musicPlayer;
    private final static Map<String, Media> cache = new HashMap<>();

    public static void play(MusicType music) {
        if (musicPlayer != null) {
            musicPlayer.stop();
        }
        musicPlayer = createMediaPlayer(getSoundFileName(music));
        musicPlayer.play();
    }

    public static void play(SoundEffectType soundEffect) {
        MediaPlayer player = createMediaPlayer(getSoundFileName(soundEffect));
        player.play();
    }

    private static MediaPlayer createMediaPlayer(String filePath) {
        filePath = "/sounds/" + filePath;

        if (!cache.containsKey(filePath)) {
            URL url = Sound.class.getResource(filePath);
            if (url == null) {
                throw new RuntimeException("Could not load file: " + filePath);
            }

            cache.put(filePath, new Media(url.toString()));
        }

        return new MediaPlayer(cache.get(filePath));
    }

    private static String getSoundFileName(SoundEffectType soundEffect) {
        switch (soundEffect) {
            case LASER_FIRED:
                return "laser_fired.wav";
            case SPACESHIP_EXPLODE:
                return "explode_spaceship.wav";
            default:
                throw new RuntimeException("No Soundfilename set for this enum value:" + soundEffect);
        }
    }

    private static String getSoundFileName(MusicType music) {
        switch (music) {
            case BACKGROUND:
                return "music_ingame.wav";
            case INTRO:
                return "music_startscreen.wav";
            default:
                throw new RuntimeException("No Soundfilename set for this enum value:" + music);
        }
    }

}