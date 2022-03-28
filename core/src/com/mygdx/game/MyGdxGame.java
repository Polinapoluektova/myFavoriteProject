

package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screen.mainMenuScreen;

import java.util.ArrayList;

public class MyGdxGame extends Game {

	public SpriteBatch batch;
	public BitmapFont font;
	public ArrayList<Music> musicArrayList;
	public Music music;
	public int musicID;
	public float volume;


	@Override
	public void create () {

		volume = 0.15f;
		batch = new SpriteBatch();
		font = new BitmapFont();
		musicArrayList = new ArrayList<>();
		musicArrayList.add(Gdx.audio.newMusic(Gdx.files.internal("music/song.mp3")));
		//musicArrayList.add(Gdx.audio.newMusic(Gdx.files.internal("music/fool_and_lightning.mp3")));
		this.setScreen(new mainMenuScreen(this));

		batch = new SpriteBatch();
		// libGDX по умолчанию использует Arial шрифт.
		font = new BitmapFont();
		this.setScreen(new mainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}

