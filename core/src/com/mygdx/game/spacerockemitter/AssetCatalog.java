package com.mygdx.game.spacerockemitter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.assets.loaders.ShaderProgramLoader;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * load all the asset in the asset manager and contain all the AssetDescriptor
 * 
 * @author Alessandro
 *
 */
public class AssetCatalog {

	///////////////////////
	//UI references
	//////////////////////
	public static final String UI_ATLAS  = "spacerockemitter/quantum-horizon-ui.atlas";
	public static final String UI_JSON  = "spacerockemitter/quantum-horizon-ui.json";	
	
	///////////////////////
	//MUSIC
	//////////////////////
	public static AssetDescriptor<Music> MUSIC_MENU_LOOP;
	public static AssetDescriptor<Music> MUSIC_LEVEL_LOOP;	
	
	///////////////////////
	//SOUND
	//////////////////////
	public static AssetDescriptor<Sound> SOUND_GAME_ON;
	public static AssetDescriptor<Sound> SOUND_GAME_OVER;
	public static AssetDescriptor<Sound> SOUND_LASER;
	public static AssetDescriptor<Sound> SOUND_EXPLOSION;
	public static AssetDescriptor<Sound> SOUND_WARNING;	
	
	//////////////////////
	//TEXTURE
	//////////////////////
	public static AssetDescriptor<Texture> TEXTURE_SPACE_BACKGROUND;
	public static AssetDescriptor<Texture> TEXTURE_SHIP_0;		
	public static AssetDescriptor<Texture> TEXTURE_SHIP_1;
	public static AssetDescriptor<Texture> TEXTURE_SHIP_2;
	public static AssetDescriptor<Texture> TEXTURE_SHIP_SHILED;		
	public static AssetDescriptor<Texture> TEXTURE_SPOT_LIGHT;	
	public static AssetDescriptor<Texture> TEXTURE_LASER;	
	public static AssetDescriptor<Texture> TEXTURE_HEART;	
	public static AssetDescriptor<Texture> TEXTURE_ROCK_0;	
	public static AssetDescriptor<Texture> TEXTURE_ROCK_1;
	public static AssetDescriptor<Texture> TEXTURE_ROCK_2;
	public static AssetDescriptor<Texture> TEXTURE_ROCK_3;	

	//////////////////////
	//PARTICLE EFFECT
	//////////////////////
	private static String PARTICLE_IMAGES_DIR = "spacerockemitter/";		
	public static AssetDescriptor<ParticleEffect> PARTICLE_THRUSTER;
	public static AssetDescriptor<ParticleEffect> PARTICLE_LASER;	
	public static AssetDescriptor<ParticleEffect> PARTICLE_EXPLOSION;	
		
	
	//////////////////////
	//SHADERS
	//////////////////////
	public static AssetDescriptor<ShaderProgram> SHADER_FLICKER;
	
	
	/**
	 * prepare here all the asset descriptor
	 */
	public void init() {		
		
		//music
		MUSIC_MENU_LOOP = new AssetDescriptor<>("spacerockemitter/entry_loop.wav", Music.class);
		MUSIC_LEVEL_LOOP = new AssetDescriptor<>("spacerockemitter/theme_loop.wav", Music.class);
		
		//sound
		SOUND_GAME_ON = new AssetDescriptor<>("spacerockemitter/game on.wav", Sound.class);
		SOUND_LASER = new AssetDescriptor<>("spacerockemitter/laser.wav", Sound.class);		
		SOUND_EXPLOSION = new AssetDescriptor<>("spacerockemitter/explosion.wav", Sound.class);		
		SOUND_WARNING = new AssetDescriptor<>("spacerockemitter/warning.wav", Sound.class);	
		SOUND_GAME_OVER = new AssetDescriptor<>("spacerockemitter/game over.wav", Sound.class);	
		
		//texture
		TextureLoader.TextureParameter textureParam = new TextureLoader.TextureParameter();
		textureParam.magFilter = TextureFilter.Linear;
		textureParam.minFilter = TextureFilter.Linear;
		
		TEXTURE_SPACE_BACKGROUND = new AssetDescriptor<>("spacerockemitter/space.png", Texture.class,textureParam);
		TEXTURE_SHIP_0 = new AssetDescriptor<>("spacerockemitter/spaceship-0.png", Texture.class,textureParam);
		TEXTURE_SHIP_1 = new AssetDescriptor<>("spacerockemitter/spaceship-1.png", Texture.class,textureParam);
		TEXTURE_SHIP_2 = new AssetDescriptor<>("spacerockemitter/spaceship-2.png", Texture.class,textureParam);
		TEXTURE_SHIP_SHILED = new AssetDescriptor<>("spacerockemitter/shield.png", Texture.class,textureParam);
		TEXTURE_SPOT_LIGHT = new AssetDescriptor<>("spacerockemitter/spotLight.png", Texture.class,textureParam);		
		TEXTURE_LASER = new AssetDescriptor<>("spacerockemitter/laser.png", Texture.class,textureParam);		
		TEXTURE_HEART = new AssetDescriptor<>("spacerockemitter/heart.png", Texture.class,textureParam);
		TEXTURE_ROCK_0 = new AssetDescriptor<>("spacerockemitter/rock0.png", Texture.class,textureParam);
		TEXTURE_ROCK_1 = new AssetDescriptor<>("spacerockemitter/rock1.png", Texture.class,textureParam);
		TEXTURE_ROCK_2 = new AssetDescriptor<>("spacerockemitter/rock2.png", Texture.class,textureParam);
		TEXTURE_ROCK_3 = new AssetDescriptor<>("spacerockemitter/rock3.png", Texture.class,textureParam);		
		

		//particle
		ParticleEffectLoader.ParticleEffectParameter particleParam = new ParticleEffectLoader.ParticleEffectParameter();
		particleParam.imagesDir = Gdx.files.internal(PARTICLE_IMAGES_DIR);
		
		PARTICLE_THRUSTER = new AssetDescriptor<>("spacerockemitter/thruster.pfx", ParticleEffect.class, particleParam);
		PARTICLE_LASER = new AssetDescriptor<>("spacerockemitter/laser.pfx", ParticleEffect.class, particleParam);		
		PARTICLE_EXPLOSION = new AssetDescriptor<>("spacerockemitter/explosion.pfx", ParticleEffect.class, particleParam);
		
		//shaders
		SHADER_FLICKER = new AssetDescriptor<>("SHADER_FLICKER", ShaderProgram.class,  new ShaderProgramLoader.ShaderProgramParameter(){
																											{
																												vertexFile = "shader/passthrough.vrtx";
																												fragmentFile = "shader/Flicker.frgm";
																											}
																										});
		
	
		
	}
	
	public void loadAssetsManagerQueue(AssetManager assetManager) {
		
		//load UI
		SkinParameter params = new SkinParameter(UI_ATLAS);
		assetManager.load(UI_JSON, Skin.class, params);
		
		//load music
		assetManager.load(MUSIC_MENU_LOOP);
		assetManager.load(MUSIC_LEVEL_LOOP);		
		
		//load  sound
		assetManager.load(SOUND_GAME_ON);
		assetManager.load(SOUND_LASER);	
		assetManager.load(SOUND_EXPLOSION);		
		assetManager.load(SOUND_WARNING);
		assetManager.load(SOUND_GAME_OVER);		
		
		//load texture
		assetManager.load(TEXTURE_SPACE_BACKGROUND);		
		assetManager.load(TEXTURE_SHIP_0);
		assetManager.load(TEXTURE_SHIP_1);
		assetManager.load(TEXTURE_SHIP_2);
		assetManager.load(TEXTURE_SHIP_SHILED);		
		assetManager.load(TEXTURE_SPOT_LIGHT);		
		assetManager.load(TEXTURE_LASER);		
		assetManager.load(TEXTURE_HEART);
		assetManager.load(TEXTURE_ROCK_0);
		assetManager.load(TEXTURE_ROCK_1);	
		assetManager.load(TEXTURE_ROCK_2);	
		assetManager.load(TEXTURE_ROCK_3);			
		
		
		//load particle effects
		assetManager.load(PARTICLE_THRUSTER);
		assetManager.load(PARTICLE_LASER);
		assetManager.load(PARTICLE_EXPLOSION);		
		
		//load shaders
		assetManager.load(SHADER_FLICKER);
		
	}
	
}