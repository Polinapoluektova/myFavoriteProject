package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.MyGdxGame;

public class mainMenuScreen implements Screen {
    MyGdxGame game;
    OrthographicCamera camera;
    Texture background;

    public mainMenuScreen(final MyGdxGame rgt) {
        game = rgt;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1100, 750);
        background = new Texture("pictures/background.png");

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0,0 );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Cats!!! ", 100, 150);
        game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new gameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}