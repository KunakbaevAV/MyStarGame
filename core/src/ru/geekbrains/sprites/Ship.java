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
    private boolean pressedLeft = false;
    private boolean pressedRight = false;
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
    private boolean isFreeMove;

    public Ship(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("mainShip"),1,2,2);
        this.bulletRegion = atlas.findRegion("bullet");
        this.bulletPool = bulletPool;
        pos = new Vector2();
        float size = 0.15f;
        setHeightProportion(size);
        center = new Vector2(size/2, size/2);
        target = new Vector2();
        targetTemp = new Vector2();
        v = new Vector2();
        isFreeMove = false;
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
        isFreeMove = false;
        vHorizon.set(vHorizon0);
    }

    private void moveLeft() {
        isFreeMove = false;
        vHorizon.set(vHorizon0).rotate(180);
    }

    private void stop() {
        vHorizon.setZero();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if(isFreeMove) moveSheep();

        pos.mulAdd(vHorizon, delta);
        setPosition(pos);
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        System.out.println("touchDown touchX = " + touch.x + " touchY = " + touch.y);
        startFreeMoving(touch);
        return super.touchDown(touch, pointer);
    }

    private void moveSheep() {
        targetTemp.set(target);
        if (targetTemp.sub(pos).len() > accelepation) {
            pos.add(v);
        } else {
            pos.set(target);
            isFreeMove = false;
        }
        this.setPosition(pos.x, pos.y);
    }

    private Vector2 findDirection() {
        return target.cpy().sub(pos);
    }

    private void startFreeMoving(Vector2 touch) {
        isFreeMove = true;
        target.set(touch);
        target.sub(center);
        accelepation = target.cpy().sub(pos).len() / 60;
        v.set(findDirection().setLength(accelepation));
    }

    public void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos.cpy().add(center), bulletV, 0.03f, worldBounds, 1);
    }
}
