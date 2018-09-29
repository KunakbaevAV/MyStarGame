package ru.geekbrains.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;

public class Ship extends Sprite {
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
