package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.MyGdxGame;

import java.util.Iterator;

public class gameScreen implements Screen {
    final MyGdxGame game;
    SpriteBatch batch;
    Texture background;
    Texture playerImage;
    Array<Texture> eatTexture = new Array<>();
    Music music;
    OrthographicCamera camera;
    Rectangle cat;
    Array<Raindrop> eat = new Array<>();
    Array<Rectangle> raineat;
    long dropTime;
    int toothpickCollected = 0;
    long startTime;
    long finishTime;

    public gameScreen(final MyGdxGame game) {
        this.game = game;
        background = new Texture("pictures/background.png");
        playerImage = new Texture(Gdx.files.internal("pictures/cat.png"));
        eatTexture.add(new Texture(Gdx.files.internal("pictures/food.png")));

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1100, 750);

        // создается Rectangle для представления cat
        cat = new Rectangle();
        // центрируем cat по горизонтали
        cat.x = 1100 / 2 - 64 / 2;
        // размещаем на 20 пикселей выше нижней границы экрана.
        cat.y = 20;

        cat.width = 64;
        cat.height = 64;


        spawnToEat();

        spawnPlayer();
        spawnToEat();
        startTime = System.currentTimeMillis();
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(background, 0, 0);

        game.batch.draw(playerImage, cat.x, cat.y);

        for(Raindrop eat: eat) {
            game.batch.draw(eat.texture, eat.x, eat.y);
        }

        game.batch.end();

        if(Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            cat.x = touchPos.x - 230; // точка курсора
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            cat.x -= 500 * Gdx.graphics.getDeltaTime();}
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            cat.x += 500 * Gdx.graphics.getDeltaTime();}

        if(cat.x < 0){
            cat.x = 0;}
        if(cat.x > 1100 - cat.width){
            cat.x = 1100 - cat.width;}

        if(TimeUtils.nanoTime() - dropTime > 1000000000){ // интенсивность
            spawnToEat();}

        Iterator<Raindrop> iterator = eat.iterator();
        while(iterator.hasNext()) {
            Raindrop raindrop = iterator.next();
            raindrop.y -= 300 * Gdx.graphics.getDeltaTime(); // скорость
            if(raindrop.y + 64 < 0){
                iterator.remove();
            }
            if(raindrop.overlaps(cat)) {
                if ("toothpick".equals(raindrop.type)) {
                    toothpickCollected++;
                }
                iterator.remove();
            }
        }
        finishTime = System.currentTimeMillis();
        if (finishTime - startTime > 98000){ // время
            eat.clear();
            music.stop();
            eatTexture.clear();
            game.setScreen(new endGameScreen(game, toothpickCollected));
        }
    }

    @Override
    public void dispose () {
        playerImage.dispose();
        music.dispose();
        batch.dispose();
        background.dispose();
    }

    private void spawnToEat() {
        Raindrop raindrop = new Raindrop();
        raindrop.x = MathUtils.random(0, 1750); // диапазон падения
        raindrop.y = 750; //высота падения
        raindrop.width = 150;
        raindrop.height = 150;
        raindrop.texture = eatTexture.get(0);
        raindrop.type = "toothpick";
        eat.add(raindrop);
        dropTime = TimeUtils.nanoTime();
    }

    private void spawnPlayer() {
        cat = new Rectangle();
        cat.x = 850;
        cat.y = 0;
        cat.width = 250;
        cat.height = 150;
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        music.play();
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
}