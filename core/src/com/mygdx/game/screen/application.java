package com.mygdx.game.screen;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;
public class application implements ApplicationListener {
    Texture eatImage;
    Texture catImage;
    Sound justMusicSound;
    Music meowingMusic;

    Array<Rectangle> catFoods;
    SpriteBatch batch = null;

    @Override
    public void create() {
// Загрузка изображений капли и ведра, каждое размером 64x64 пикселей
        eatImage = new Texture(Gdx.files.internal("pictures/background.png"));
        catImage = new Texture(Gdx.files.internal("pictures/cat.png"));

// Загрузка звукового эффекта падающей капли и фоновой "музыки" дождя
        justMusicSound = Gdx.audio.newSound(Gdx.files.internal("assets/music/2cellos — now we are free.mp3"));
        meowingMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/music/2cellos — now we are free.mp3"));

// Сразу же воспроизводиться музыка для фона
        meowingMusic.setLooping(true);
        meowingMusic.play();

        catFoods = new Array<Rectangle>();
        spawnEat ();

    }
    long lastEatTime;
    private void spawnEat() {
        Rectangle catFood = new Rectangle();
        catFood.x = MathUtils.random(0, 800-64);
        catFood.y = 480;
        catFood.width = 64;
        catFood.height = 64;
        catFoods.add(catFood);

        lastEatTime = TimeUtils.nanoTime();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Rectangle catDowl;
        catDowl = new Rectangle();
        catDowl.x = 800 / 2 - 64 / 2;
        catDowl.y = 20;
        catDowl.width = 64;
        catDowl.height = 64;
        if(catDowl.x < 0) catDowl.x = 0;
        if(catDowl.x > 800 - 64) catDowl.x = 800 - 64;

        OrthographicCamera camera;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        if(Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            catDowl.x = (int) (touchPos.x - 64 / 2);
        }

        if(Gdx.input.isKeyPressed(Keys.LEFT)) catDowl.x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Keys.RIGHT)) catDowl.x += 200 * Gdx.graphics.getDeltaTime();

/*сколько времени прошло, с тех пор как была создана новая капля и если
необходимо, создавать еще одну новую каплю.*/
        if(TimeUtils.nanoTime() - lastEatTime > 1000000000) spawnEat();

/* двигаются с постоянной скоростью 200 пикселей в секунду. Если капля находится
ниже нижнего края экрана, мы удаляем ее из массива.*/
        Iterator<Rectangle> iter = catFoods.iterator();
        while(iter.hasNext()) {

            Rectangle food = iter.next();
            food.y -= 200 * Gdx.graphics.getDeltaTime();
            if(food.y + 64 < 0) iter.remove();
            if(food.overlaps(catDowl)) {
                meowingMusic.play();
                iter.remove();
            }

        }

//код визуализации на экране
        batch.begin();
        batch.draw(catImage, catDowl.x, catDowl.y);
        for(Rectangle food: catFoods) {
            batch.draw(eatImage, food.x, food.y);
        }
        batch.end();


/*Метод Rectangle.overlaps() проверяет, если прямоугольник пересекается с другим прямоугольником.
В нашем случае воспроизводится звук, и капля удаляется из массива.*/




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
    public void dispose() {
        eatImage.dispose();
        catImage.dispose();
        justMusicSound.dispose();
        meowingMusic.dispose();
        batch.dispose();
    }
}