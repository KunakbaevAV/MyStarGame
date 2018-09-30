package ru.geekbrains.sprites.ships;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pools.BulletPool;

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
        bulletHeight = 0.3f;
        bulledDamage = 1;
        bulletV = new Vector2(0, -3);
        bulletRegion = atlas.findRegion("bullet");
        this.mainShip = mainShip;
        this.v.set(v0);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        autoShot(delta);
        pos.mulAdd(v0, delta);
    }
//    public void set(
//            TextureRegion[] regions,
//            Vector2 v0,
//            TextureRegion bulletRegion,
//            float bulletHeight,
//            float bulletVY,
//            int bulletDamage,
//            float reloadInterval,
//            float height,
//            int hp
//    ) {
//        this.regions = regions;
//        this.v0.set(v0);
//        this.bulletRegion = bulletRegion;
//        this.bulletHeight = bulletHeight;
//        this.bulletV.set(0, bulletVY);
//        this.bulletDamage = bulletDamage;
//        this.reloadInterval = reloadInterval;
//        this.hp = hp;
//        setHeightProportion(height);
//        reloadTimer = reloadInterval;
//        v.set(v0);
//    }
}
