package ru.geekbrains.screen;
/**
 * @author Kunakbaev Artem
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private SpriteBatch batch;
    private Texture ship;
    private Texture background;
    private Vector2 poz;
    private Vector2 center;
    private Vector2 target;
    private Vector2 targetTemp;
    private Vector2 speed;
    float accelepation;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        ship = new Texture("ship.png");
        background = new Texture("space.jpg");
        poz = new Vector2(200, 200);
        center = new Vector2(ship.getWidth() / 2, ship.getWidth() / 2);
        target = new Vector2(200,200);
        targetTemp = new Vector2(200,200);
        speed = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(ship, poz.x, poz.y);
        batch.end();

        moveSheep();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenY = reverseY(screenY);
        target.set(screenX,screenY);
        target.sub(center);
        setAcceleration();
        speed.set(findDirection().setLength(accelepation));
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public void dispose() {
        batch.dispose();
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

