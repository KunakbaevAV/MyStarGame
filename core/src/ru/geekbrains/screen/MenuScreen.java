package ru.geekbrains.screen;
/**
 * author Kunakbaev Artem
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private Texture background;
    private Vector2 poz;
    private Vector2 center;
    private Vector2 target;
    private Vector2 targetTemp;
    private Vector2 speed;
    private float accelepation;
    private Sprite ship;
    private Sprite exit;
    private TextureRegion region;
    private ru.geekbrains.base.Sprite mySprite;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        Texture shipTexture = new Texture("ship.png");
        Texture exitTexture = new Texture("exit.png");
        region = new TextureRegion(new Texture("badlogic.jpg"),0,0,128,128);// вырезал часть картинки
        mySprite = new ru.geekbrains.base.Sprite(region);
        mySprite.setLeft(0.5f);// пытаюсь указать позицию в центре для левого края (горизонтальное положение)
        mySprite.setRight(0.2f);// пытаюсь сделать ширину на 1/5 экрана
        mySprite.setTop(0.2f);// пытаюсь сделать высоту на 1/5 экрана
        mySprite.setBottom(0.5f);// пытаюсь указать позицию в центре для нижнего края (вертикальное положение)

        exit = new Sprite(exitTexture);
        exit.setSize(0.1f,0.1f);
        exit.setPosition(0.4f,0.4f);
        ship = new Sprite(shipTexture);
        ship.setSize(0.2f,0.2f);
        background = new Texture("space.jpg");
        poz = new Vector2(0f,0f);
        center = new Vector2(0.1f, 0.1f);
        target = new Vector2();
        targetTemp = new Vector2();
        speed = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, -0.5f, -0.5f,1,1);
        ship.draw(batch);
        exit.draw(batch);
        mySprite.draw(batch);// не рисуется ((
        batch.end();

        moveSheep();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        target.set(touch);
        target.sub(center);
        System.out.println("touchDown touchX = " + touch.x + " touchY = " + touch.y);
        setAcceleration();
        speed.set(findDirection().setLength(accelepation));
        return super.touchDown(touch, pointer);
    }

    @Override
    public void dispose() {
//        ship.dispose();
        background.dispose();
        super.dispose();
    }

    private void moveSheep() {
        targetTemp.set(target);
        if(targetTemp.sub(poz).len() > accelepation){
            poz.add(speed);
        }
        else{
            poz.set(target);
        }
        ship.setPosition(poz.x,poz.y);
    }

    private Vector2 findDirection() {
        return target.cpy().sub(poz);
    }

    private void setAcceleration(){
        accelepation = target.cpy().sub(poz).len() / 60;
    }

}

