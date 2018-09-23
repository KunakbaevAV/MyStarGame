package ru.geekbrains.screen;
/**
 * author Kunakbaev Artem
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprites.ButtonExit;
import ru.geekbrains.sprites.ButtonStart;
import ru.geekbrains.sprites.Star;

public class MenuScreen extends BaseScreen{

    private static final int STAR_COUNT = 256;
    private static final int STAR_COUNT2 = 128;
    private static final int STAR_COUNT3 = 64;
    private Vector2 pos;
    private Vector2 center;
    private Vector2 target;
    private Vector2 targetTemp;
    private Vector2 speed;
    private float accelepation;
    private Sprite ship;
    private Sprite exit;
    private Sprite background;
    private Sprite btnExit;
    private Sprite btnStart;
    private Sprite[] star;
    private Sprite[] star2;
    private Sprite[] star3;
    TextureAtlas atlas;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/textures.pack");
        addShip(0.3f);
        addExit();
        addBackgroud();
        pos = new Vector2();
        target = new Vector2();
        targetTemp = new Vector2();
        speed = new Vector2();
        btnExit = new ButtonExit(atlas);
        btnStart = new ButtonStart(atlas);
        star = new Star[STAR_COUNT];
        star2 = new Star[STAR_COUNT2];
        star3 = new Star[STAR_COUNT3];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(atlas, "star", 0.01f,0.05f);
        }
        for (int i = 0; i < star2.length; i++) {
            star2[i] = new Star(atlas, "star2", 0.01f, 0.1f);
        }
        for (int i = 0; i < star3.length; i++) {
            star3[i] = new Star(atlas, "star3", 0.01f, 0.1f);
        }
    }

    private void addBackgroud() {
        Texture backgroudTexture = new Texture("space.png");
        background = new Sprite(new TextureRegion(backgroudTexture), 1);
        background.setPosition(-0.5f, -0.5f);
    }

    private void addExit() {
        Texture exitTexture = new Texture("exit.png");
        exit = new ExitButton(new TextureRegion(exitTexture), this.getAspectWorld());
    }

    private void addShip(float size) {
        Texture shipTexture = new Texture("SpaceShips.png");
        TextureRegion shipsRegion = new TextureRegion(shipTexture, 146, 4, 432, 479);
        ship = new Sprite(shipsRegion, size);
        center = new Vector2(size / 2, size / 2);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    private void draw(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Sprite s : star) {
            s.draw(batch);
        }
        for (Sprite s : star2) {
            s.draw(batch);
        }
        for (Sprite s : star3) {
            s.draw(batch);
        }
        ship.draw(batch);
        btnExit.draw(batch);
        btnStart.draw(batch);
        batch.end();
    }

    private void update(float delta){
        for (Sprite s : star) {
            s.update(delta);
        }
        for (Sprite s : star2) {
            s.update(delta);
        }
        for (Sprite s : star3) {
            s.update(delta);
        }
        moveSheep();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        btnExit.touchDown(touch, pointer);
        btnStart.touchDown(touch,pointer);
        target.set(touch);
        target.sub(center);
        System.out.println("touchDown touchX = " + touch.x + " touchY = " + touch.y);
        setAcceleration();
        speed.set(findDirection().setLength(accelepation));
        exit.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        btnExit.touchUp(touch, pointer);
        btnStart.touchUp(touch,pointer);
        return super.touchUp(touch, pointer);
    }

    @Override
    public void resize(Rect worldBounds) {
        btnExit.resize(worldBounds);
        btnStart.resize(worldBounds);
        for (Sprite s : star) {
            s.resize(worldBounds);
        }
        for (Sprite s : star2) {
            s.resize(worldBounds);
        }
        for (Sprite s : star3) {
            s.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        atlas.dispose();
        super.dispose();
    }

    private void moveSheep() {
        targetTemp.set(target);
        if (targetTemp.sub(pos).len() > accelepation) {
            pos.add(speed);
        } else {
            pos.set(target);
        }
        ship.setPosition(pos.x, pos.y);
    }

    private Vector2 findDirection() {
        return target.cpy().sub(pos);
    }

    private void setAcceleration() {
        accelepation = target.cpy().sub(pos).len() / 60;
    }
}

