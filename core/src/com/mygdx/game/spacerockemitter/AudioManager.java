package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntMap.Entry;

public class AudioManager {

	public static final int SOUND_LASER = 0;
	public static final int SOUND_EXPLOSION = 1;	
	public static final int SOUND_WARNING = 2;	
	public static final int SOUND_GAME_OVER = 3;
	public static final int SOUND_GAME_ON = 4;
	public static final int SOUND_WARP_ENGINE = 5;		

	public static final int MUSIC_MENU_LOOP = 100;
	public static final int MUSIC_LEVEL_LOOP = 101;
	
		
	private IntMap<Sound> soundPool;
	private IntMap<Music> musicPool;
	
	private float musicVolume;
	private float soundVolume;	
	
	public AudioManager() {
		this(0.5f);
	}
	
	public AudioManager(float volume) {
		soundPool = new IntMap<Sound>();
		musicPool = new IntMap<Music>();
		soundVolume = volume;
		musicVolume = volume;
	}
	
	public void registerAudio(int type, Sound sound) {
		soundPool.put(type, sound);
	}

	public void registerAudio(int type, Music music, boolean looping) {
		music.setLooping(looping);
		musicPool.put(type, music);
	}
	
	
	public void setMusicVolume(float volume) {
		musicVolume = volume;
		
		for (Entry<Music> entry : musicPool) {
			if(entry.value.isPlaying()) {
				entry.value.setVolume(musicVolume);
			}
		}
	}	

	public void setSoundVolume(float volume) {
		soundVolume = volume;
	}		
	
	public void setSoundVolume(int type, long soundId, float volume) {
		Sound audio = soundPool.get(type);
		audio.setVolume(soundId, volume);
	}	
	
	public void playMusic(int type) {
		Music audio = musicPool.get(type);
		audio.setVolume(musicVolume);
		audio.play();
	}

	public void stopMusic(int type) {
		Music audio = musicPool.get(type);
		audio.stop();
	}	
	
	public long playSound(int type) {
		Sound audio = soundPool.get(type);
		return audio.play(soundVolume);
	}
	
	public long loopSound(int type) {
		Sound audio = soundPool.get(type);
		return audio.loop(soundVolume);
	}	

	public void stopSound(int type) {
		Sound audio = soundPool.get(type);
		audio.stop();
	}	
	
	
	
}
