package ru.geekbrains.sprites.ships;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pools.BulletPool;
import ru.geekbrains.sprites.Bullet;

public class Ship extends Sprite {

    TextureAtlas atlas;
    Vector2 v = new Vector2();

    protected Rect worldBounds; // нужно инициализировать

    Vector2         bulletV = new Vector2();
    BulletPool      bulletPool;
    TextureRegion   bulletRegion;
//    String          bulletName;
    float           bulletHeight;
    int             bulledDamage;
    Sound shotSound;

    float reloadInterval;
    float reloadTimer;

    int hp;

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

    public Ship( // конструктор для создания врагов
                 TextureAtlas atlas,
                 String shipName,
                 BulletPool bulletPool,
                 Sound shotSound) {
        super(atlas.findRegion(shipName), 1, 2, 2);
        this.atlas = atlas;
        this.bulletPool = bulletPool;
//        this.bulletName = "shotEnemy";
        this.bulletRegion = atlas.findRegion("shotEnemy");
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
                pos.cpy().add(0, getHalfHeight()),
                bulletV,
                bulletHeight,
                worldBounds,
                bulledDamage);
        shotSound.play(1.0f);
    }

    void autoShot(float delta) {
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            shoot();
            reloadTimer = 0;
        }
    }
}
