package ru.geekbrains.sprites.ships;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Regions;
import ru.geekbrains.pools.BulletPool;
import ru.geekbrains.pools.ExplosionPull;
import ru.geekbrains.sprites.Bullet;
import ru.geekbrains.sprites.Explosion;

public class Ship extends Sprite {

    TextureAtlas atlas;
    Vector2 v = new Vector2();
    final Vector2 INPUT_V = new Vector2(0, -0.3f);

    protected Rect worldBounds;

    Vector2 bulletV = new Vector2();
    BulletPool bulletPool;
    ExplosionPull explosionPull;
    private String bulletName;
    TextureRegion bulletRegion;
    float bulletHeight;
    int bulledDamage;
    Sound shotSound;

    float reloadInterval;
    float reloadTimer;

    float damageAnimateTimer;
    float damageAnimateInterval = 0.5f;

    int hp;

    Ship(
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

    Ship( // конструктор для врагов
          TextureAtlas atlas,
          String shipName,
          ExplosionPull explosionPull,
          BulletPool bulletPool,
          String bulletName,
          Sound shotSound,
          Rect worldBounds) {
        super(atlas.findRegion(shipName), 1, 2, 2);
        this.atlas = atlas;
        this.explosionPull = explosionPull;
        this.bulletPool = bulletPool;
        this.bulletName = bulletName;
        this.bulletRegion = atlas.findRegion(this.bulletName);
        bulletHeight = 0.03f;
        bulledDamage = 1;
        this.shotSound = shotSound;
        this.worldBounds = worldBounds;
    }

    void setHp(int hp) {
        this.hp = hp;
    }

    void setShipVY(float y){
        this.v.y = y;
    }

    void setBulletVY(float y) {
        this.bulletV.y = y;
    }

    void setBulletName(String bulletName) {
        this.bulletName = bulletName;
    }

    public void setBulletRegion(TextureRegion bulletRegion) {
        this.bulletRegion = bulletRegion;
    }

    void setBulletHeight(float bulletHeight) {
        this.bulletHeight = bulletHeight;
    }

    void setBulledDamage(int bulledDamage) {
        this.bulledDamage = bulledDamage;
    }

    void setReloadInterval(float reloadInterval) {
        this.reloadInterval = reloadInterval;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
//        damageAnimateTimer += delta;
//        if ()
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
        shotSound.play(VOLUME);
    }
    void autoShot(float delta) {
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            shoot();
            reloadTimer = 0;
        }
    }
}
