package ru.geekbrains.sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Ship extends Sprite {

    private static final int INVALID_POINTER = -1;

    private Vector2 vArrows0 = new Vector2(0.5f, 0f);
    private Vector2 vArrows = new Vector2();
    private Vector2 vBullet = new Vector2(0, 0.5f);
    private Rect worldBounds;
        private boolean pressedLeft = false;
    private boolean pressedRight = false;
//    private boolean pressedUp = false;
//    private boolean pressedDown = false;
//    private boolean pressedArrow = false;
    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Sound shotSound;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    private Vector2 pos;
    private Vector2 center;
    private Vector2 correction;
    private Vector2 target;
    private Vector2 targetTemp;
    private Vector2 v;
    private float accelepation;
    private boolean isFreeMove;

    private float reloadInterval;
    private float reloadTimer;

    public Ship(TextureAtlas atlas, BulletPool bulletPool, Sound shotSound) {
        super(atlas.findRegion("mainShip"), 1, 2, 2);
        this.bulletRegion = atlas.findRegion("bullet");
        this.bulletPool = bulletPool;
        this.shotSound = shotSound;
        this.reloadInterval = 0.2f;

        float size = 0.15f;
        setHeightProportion(size);
        center = new Vector2(size / 2, size / 2);
        correction = new Vector2(-0.006f, 0.06f);
        pos = new Vector2(-size / 2, -0.45f);
        target = new Vector2();
        targetTemp = new Vector2();
        v = new Vector2();
        isFreeMove = false;
    }

    public void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
//                pressedArrow = true;
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
//            case Input.Keys.UP:
//                pressedRight = true;
//                moveUp();
//                break;
//            case Input.Keys.DOWN:
//                pressedDown = true;
//                moveDown();
//                break;
        }
    }

    public void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) moveRight();
//                else if (pressedDown) moveDown();
//                else if (pressedUp) moveUp();
                else stop();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) moveLeft();
//                else if (pressedDown) moveDown();
//                else if (pressedUp) moveUp();
                else stop();
                break;
//            case Input.Keys.UP:
//                pressedUp = false;
//                if (pressedLeft) moveLeft();
//                else if (pressedRight) moveRight();
//                else if (pressedDown) moveDown();
//                else stop();
//            case Input.Keys.DOWN:
//                pressedDown = false;
//                if (pressedLeft) moveLeft();
//                else if (pressedRight) moveRight();
//                else if (pressedUp) moveUp();
//                else stop();
        }
    }

    private void moveRight() {
        isFreeMove = false;
        vArrows.set(vArrows0);
    }

    private void moveLeft() {
        isFreeMove = false;
        vArrows.set(vArrows0).rotate(180);
    }

//    private void moveUp() {
//        isFreeMove = false;
//        vArrows.set(vArrows0).rotate(90);
//    }
//
//    private void moveDown() {
//        isFreeMove = false;
//        vArrows.set(vArrows0).rotate(270);
//    }

    private void stop() {
        vArrows.setZero();
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        autoShot(delta);

        if (isFreeMove) moveSheep();

        pos.mulAdd(vArrows, delta);
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

    private void autoShot(float delta) {
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            shoot();
            reloadTimer = 0;
        }

    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
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
        bullet.set(this,
                bulletRegion,
                pos.cpy().add(center).add(correction),
                vBullet,
                0.03f,
                worldBounds,
                1);
        shotSound.play(1.0f);
    }
}
