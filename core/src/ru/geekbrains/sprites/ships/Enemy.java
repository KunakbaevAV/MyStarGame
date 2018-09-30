package ru.geekbrains.sprites.ships;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pools.BulletPool;
import ru.geekbrains.sprites.Bullet;

public class Enemy extends Ship {
    private MainShip mainShip;
    Vector2 v0 = new Vector2();

    public Enemy(
            TextureAtlas atlas,
            String shipName,
            BulletPool bulletPool,
            Sound shotSound,
            MainShip mainShip) {
        super(atlas, shipName, bulletPool, shotSound);
        this.atlas = atlas;
        this.bulletPool = bulletPool;
//        bulletHeight = 0.2f;
//        bulledDamage = 1;
//        bulletRegion = atlas.findRegion("bullet");
        this.mainShip = mainShip;
        this.v.set(v0);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        autoShot(delta);
        pos.mulAdd(v0, delta);
    }

    @Override
    void shoot() {
        Bullet bullet = bulletPool.obtain();
        Vector2 posBullet = new Vector2(pos.x, getBottom());
        bullet.set(this,
                bulletRegion,
                posBullet,
                this.bulletV,
                bulletHeight,
                worldBounds,
                bulledDamage);
        shotSound.play(1.0f);
    }
}
