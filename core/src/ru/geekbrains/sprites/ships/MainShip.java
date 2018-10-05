package ru.geekbrains.sprites.ships;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pools.BulletPool;
import ru.geekbrains.pools.ExplosionPull;
import ru.geekbrains.sprites.Explosion;

public class MainShip extends Ship {

    private Vector2 vArrows0 = new Vector2(0.5f, 0f);
    private Vector2 vArrows = new Vector2();

    private boolean pressedLeft = false;
    private boolean pressedRight = false;

    private Vector2 target;
    private Vector2 targetTemp;
    private Vector2 vMoveToTouch;
    private float accelepation;
    private boolean isFreeMove;

    public MainShip(
            TextureAtlas atlas,
            ExplosionPull explosionPull,
            BulletPool bulletPool,
            Sound shotSound){
        super(atlas,
                "mainShip",
                1,
                2,
                2,
                explosionPull,
                bulletPool,
                "bullet",
                shotSound);
        this.bulletV = new Vector2(0, 0.5f);
        this.reloadInterval = 0.4f;
        setHp(20);
        float size = 0.15f;
        setHeightProportion(size);
        target = new Vector2();
        targetTemp = new Vector2();
        vMoveToTouch = new Vector2();
        isFreeMove = false;
    }

    public void startNewGame(){
        setHp(20);
        flushDestroy();
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        autoShot(delta);

        if (isFreeMove) moveSheepFree(delta);
        else moveSheepHorisont(delta);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        startFreeMoving(touch);
        return super.touchDown(touch, pointer);
    }

    // движение стрелками
    private void moveSheepHorisont(float delta) {
        pos.mulAdd(vArrows, delta);
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
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

    private void stop() {
        vArrows.setZero();
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
//            case Input.Keys.UP:
//                frame = 1;
//                break;
        }
    }
    public void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) moveRight();
                else stop();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) moveLeft();
                else stop();
                break;
//            case Input.Keys.UP:
//                frame = 0;
//                break;
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + 0.03f);
    }

    // движение за курсором
    private void startFreeMoving(Vector2 touch) {
        isFreeMove = true;
        target.set(touch);
        accelepation = target.cpy().sub(pos).len() / 60;
        Vector2 direction = target.cpy().sub(pos);
        vMoveToTouch.set(direction.setLength(accelepation));
    }
    private void moveSheepFree(float delta) {
        targetTemp.set(target);
        if (targetTemp.sub(pos).len() > accelepation) {
            pos.add(vMoveToTouch);
        } else {
            pos.set(target);
            isFreeMove = false;
        }
    }
}
