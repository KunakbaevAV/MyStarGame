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

    public Enemy(
            TextureAtlas atlas,
            String shipName,
            BulletPool bulletPool,
            Sound shotSound,
            Rect worldBonds,
            MainShip mainShip) {
        super(atlas, shipName, bulletPool, "shotEnemy", shotSound, worldBonds);
        this.atlas = atlas;
        this.bulletPool = bulletPool;
        this.mainShip = mainShip;
//        generatePos();
    }

    public void setEnemyShipV(Vector2 enemyShipV) {
        v.set(enemyShipV);
    }

    public void setEnemyShipV(float x, float y) {
        v = new Vector2(x, y);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (getTop() > worldBounds.getTop()){
            pos.mulAdd(INPUT_V, delta);
        }else {
            autoShot(delta);
            pos.mulAdd(v, delta);
        }
//        if (getBottom() < worldBounds.getBottom()) System.out.println("destroy " + this.getClass());
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
        shotSound.play(VOLUME);
    }

    void generatePos(){
        float x = Rnd.nextFloat(worldBounds.getLeft() + getHalfWidth(), worldBounds.getRight() - getHalfWidth());
        Vector2 startPos = new Vector2(x, worldBounds.getTop());
        System.out.println(startPos.x);
        pos.set(startPos);
    }
}
