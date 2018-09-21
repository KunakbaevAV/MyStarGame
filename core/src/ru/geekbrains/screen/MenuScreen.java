package ru.geekbrains.screen;
/**
 * author Kunakbaev Artem
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.MatrixUtils;

public class MenuScreen extends BaseScreen {

    private Texture ship;
    private Texture background;
    private Vector2 poz;
    private Vector2 center;
    private Vector2 target;
    private Vector2 targetTemp;
    private Vector2 speed;
    private float accelepation;
    private TextureRegion textureRegion;
//    private Sprite ship;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        textureRegion = new TextureRegion(new Texture("ship.png"),20,20);
//        ship = new Sprite(textureRegion);
        ship = new Texture("ship.png");
        background = new Texture("space.jpg");
        poz = new Vector2();
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
//        ship.draw(batch);
//        ship.update(delta);
        batch.draw(ship, poz.x,poz.y,0.2f,0.2f);
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
        ship.dispose();
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
    }

    private Vector2 findDirection() {
        return target.cpy().sub(poz);
    }

    private void setAcceleration(){
        accelepation = target.cpy().sub(poz).len() / 60;
    }

}

