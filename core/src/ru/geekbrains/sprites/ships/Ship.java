package ru.geekbrains.sprites.ships;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pools.BulletPool;
import ru.geekbrains.sprites.Bullet;

public class Ship extends Sprite {

    TextureAtlas atlas;

    Vector2 pos;
    Rect worldBounds;
    Vector2 vBullet;
    BulletPool bulletPool;
    TextureRegion bulletRegion;
    float bulletHeight;
    int bulledDamage;
    Sound shotSound;

    Vector2 center;
    Vector2 correction;

    public Ship(
            TextureAtlas atlas,
            String shipName,
            int rows,
            int cols,
            int frames,
            BulletPool bulletPool,
            String bulletName,
            Sound shotSound) {
        super(atlas.findRegion(shipName), rows, cols, frames);
        this.atlas = atlas;
        this.bulletPool = bulletPool;
        this.bulletRegion = atlas.findRegion(bulletName);
        this.bulletHeight = 0.03f;
        this.bulledDamage = 1;
        this.shotSound = shotSound;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this,
                bulletRegion,
                pos.cpy().add(center).add(correction),
                vBullet,
                bulletHeight,
                worldBounds,
                bulledDamage);
        shotSound.play(1.0f);
    }
}
