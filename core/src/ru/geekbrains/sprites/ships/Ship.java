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

    protected Rect worldBounds;
    TextureAtlas atlas;
    Vector2 v = new Vector2();
    final Vector2 INPUT_V = new Vector2(0, -0.3f);
    private int hp;

    Vector2 bulletV = new Vector2();
    BulletPool bulletPool;
    ExplosionPull explosionPull;
    private String bulletName;
    TextureRegion bulletRegion;
    float bulletHeight;
    int bulledDamage;
    private Sound shotSound;

    private float reloadInterval;
    private float reloadTimer;

    private float damageAnimateTimer = 0;
    private float damageAnimateInterval = 0.2f;

    private float angleMod;
    private float angleAnimateTimer = 0;
    private float angleAnimateInterval = 0.5f;

    Ship(
            TextureAtlas atlas,
            String shipName,
            int rows,
            int cols,
            int frames,
            ExplosionPull explosionPull,
            BulletPool bulletPool,
            String bulletName,
            Sound shotSound
    ) {
        super(atlas.findRegion(shipName), rows, cols, frames);
        this.atlas = atlas;
        this.explosionPull = explosionPull;
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
          Rect worldBounds) {
        super(atlas.findRegion(shipName), 1, 2, 2);
        this.atlas = atlas;
        this.explosionPull = explosionPull;
        this.bulletPool = bulletPool;
        this.bulletName = bulletName;
        this.bulletRegion = atlas.findRegion(this.bulletName);
        bulletHeight = 0.03f;
        bulledDamage = 1;
        this.worldBounds = worldBounds;
    }

    void setAngleMod(float angleMod) {
        this.angleMod = angleMod;
    }

    void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    void setShipVY(float y) {
        this.v.y = y;
    }

    public void setShotSound(Sound shotSound) {
        this.shotSound = shotSound;
    }

    public Sound getShotSound() {
        return shotSound;
    }

    void setBulletVY(float y) {
        this.bulletV.y = y;
    }

    void setBulletName(String bulletName) {
        this.bulletName = bulletName;
        this.bulletRegion = atlas.findRegion(this.bulletName);
    }

    void setBulletHeight(float bulletHeight) {
        this.bulletHeight = bulletHeight;
    }

    void setBulledDamage(int bulledDamage) {
        this.bulledDamage = bulledDamage;
    }

    void setReloadInterval(float reloadInterval) {
        this.reloadInterval = reloadInterval;
        reloadTimer = reloadInterval;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        doAnimateDamage(delta);
        doAnimateAngle(delta);
    }

    public void doDamage(int damage) {
        frame = 1;
        damageAnimateTimer = 0;
        hp -= damage;
        if (hp <= 0) {
            boom();
            destroy();
        }
    }

    private void doAnimateDamage(float delta) {
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= damageAnimateInterval) {
            frame = 0;
        }
    }

    private void doAnimateAngle(float delta) {
        angleAnimateTimer += delta;
        if (angleAnimateTimer >= angleAnimateInterval) {
            setAngle(angle += angleMod);
        }
    }

    private void boom() {
        Explosion explosion = explosionPull.obtain();
        explosion.set(pos, getHeight());
        setHp(0);
    }

    void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this,
                bulletRegion,
                pos.cpy().add(0, getHalfHeight()),
                bulletV,
                bulletHeight,
                worldBounds,
                bulledDamage,
                explosionPull);
        shotSound.play(VOLUME);
    }

    void autoShot(float delta) {
        if (!isDestroyed()) {
            reloadTimer += delta;
            if (reloadTimer >= reloadInterval) {
                shoot();
                reloadTimer = 0;
            }
        }
    }
}
