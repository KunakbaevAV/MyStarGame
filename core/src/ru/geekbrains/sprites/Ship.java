package ru.geekbrains.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Ship extends Sprite {

    private static final int INVALID_POINTER = -1;

    private Vector2 vHorizon0 = new Vector2(0.5f, 0f);
    private Vector2 vHorizon = new Vector2();
    private Vector2 bulletV = new Vector2(0,0.5f);
    private Rect worldBounds;
    private boolean pressedLeft;
    private boolean pressedRight;
    private BulletPool bulletPool;
    private TextureRegion bulletRegion;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    private Vector2 pos;
    private Vector2 center;
    private Vector2 target;
    private Vector2 targetTemp;
    private Vector2 v;
    private float accelepation;

    @Override
    public void update(float delta) {
        super.update(delta);
        moveSheep();

        pos.mulAdd(vHorizon, delta);
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    public void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
            case Input.Keys.UP:
                shoot();
                break;
        }
    }

    public void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
    }

    private void moveRight() {
        vHorizon.set(vHorizon0);
    }

    private void moveLeft() {
        vHorizon.set(vHorizon0).rotate(180);
    }

    private void stop() {
        vHorizon.setZero();
    }

    public void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, 0.01f, worldBounds, 1);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    public Ship(TextureAtlas atlas, String path, float size) {
        super(atlas, path);
        setHeightProportion(size);
        pos = new Vector2();
        center = new Vector2(size/2, size/2);
        target = new Vector2();
        targetTemp = new Vector2();
        v = new Vector2();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        target.set(touch);
        target.sub(center);
        System.out.println("touchDown touchX = " + touch.x + " touchY = " + touch.y);
        setAcceleration();
        v.set(findDirection().setLength(accelepation));
        return super.touchDown(touch, pointer);
    }
    private void moveSheep() {
        targetTemp.set(target);
        if (targetTemp.sub(pos).len() > accelepation) {
            pos.add(v);
        } else {
            pos.set(target);
        }
        this.setPosition(pos.x, pos.y);
    }

    private Vector2 findDirection() {
        return target.cpy().sub(pos);
    }

    private void setAcceleration() {
        accelepation = target.cpy().sub(pos).len() / 60;
    }
}
