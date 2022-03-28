package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.MyGdxGame;

public class endGameScreen implements Screen {
    final MyGdxGame game;
    OrthographicCamera camera;
    Texture background;
    int toothpickCollected;
    endGameScreen(final MyGdxGame gam, int toothpickCollected){
        game = gam;
        this.toothpickCollected = toothpickCollected;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 750);
    }




    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        if (this.toothpickCollected >= 70){
            background = new Texture("pictures/good.jpg");
        } else {
            background = new Texture("pictures/bad.png");
        }
        game.batch.draw(background, 0, 0);
        game.batch.end();
        if (Gdx.input.isTouched()) {
            game.setScreen(new mainMenuScreen(game));
            dispose();
        }

    }
    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}