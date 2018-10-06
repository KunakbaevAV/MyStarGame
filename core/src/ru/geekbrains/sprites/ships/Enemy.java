package ru.geekbrains.sprites.ships;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pools.BulletPool;
import ru.geekbrains.pools.ExplosionPull;
import ru.geekbrains.sprites.Bullet;
import ru.geekbrains.sprites.Explosion;

public class Enemy extends Ship {
    private MainShip mainShip;

    public Enemy(
            TextureAtlas atlas,
            String shipName,
            ExplosionPull explosionPull,
            BulletPool bulletPool,
            Sound shotSound,
            Rect worldBonds,
            MainShip mainShip) {
        super(atlas, shipName, explosionPull, bulletPool, "shotEnemy", shotSound, worldBonds);
        this.atlas = atlas;
        this.bulletPool = bulletPool;
        this.mainShip = mainShip;
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

        if (pos.y > worldBounds.getTop()) {
            pos.mulAdd(INPUT_V, delta);
        } else {
            autoShot(delta);
            pos.mulAdd(v, delta);
            if (pos.y < worldBounds.getBottom()) doDamage(getHp());
        }
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

    public void destroyShipOut() {
        Explosion explosion = explosionPull.obtain();
        explosion.set(pos, getHeight());
        destroy();
    }

    private void generatePos() {
        float x = Rnd.nextFloat(worldBounds.getLeft() + getHalfWidth(), worldBounds.getRight() - getHalfWidth());
        Vector2 startPos = new Vector2(x, worldBounds.getTop());
        pos.set(startPos);
    }

    public void setShip(int type) {
        switch (type) {
            case 1:
                setRegions(atlas, "shipEnemy1");          // название текстуры корабля
                setHeightProportion(0.15f);                     // размеры корабля
                setShipVY(-0.1f);                               // скорость падения корабля
                setHp(3);                                       // прочность корабля
                setBulletName("shotEnemy");                     // название текстуры пули
                setBulletHeight(0.05f);                         // размеры пули
                setBulledDamage(1);                             // урон пули
                setBulletVY(-0.5f);                             // скорость пули
                setReloadInterval(3);                           // частота стрельбы
                setAngle(0);
                setAngleMod(0);
                break;
            case 2:
                setRegions(atlas, "shipEnemy2");          // название текстуры корабля
                setHeightProportion(0.20f);                     // размеры корабля
                setShipVY(-0.09f);                              // скорость падения корабля
                setHp(5);                                       // прочность корабля
                setBulletName("shotEnemy");                     // название текстуры пули
                setBulletHeight(0.07f);                         // размеры пули
                setBulledDamage(2);                             // урон пули
                setBulletVY(-0.5f);                             // скорость пули
                setReloadInterval(2);                           // частота стрельбы
                setAngle(0);
                setAngleMod(0);
                break;
            case 3:
                setRegions(atlas, "shipEnemy3");          // название текстуры корабля
                setHeightProportion(0.3f);                      // размеры корабля
                setShipVY(-0.06f);                              // скорость падения корабля
                setHp(7);                                       // прочность корабля
                setBulletName("shotEnemy");                    // название текстуры пули
                setBulletHeight(0.1f);                          // размеры пули
                setBulledDamage(3);                             // урон пули
                setBulletVY(-0.4f);                             // скорость пули
                setReloadInterval(1.5f);                        // частота стрельбы
                setAngle(0);
                setAngleMod(0);
                break;
            case 4:
                setRegions(atlas, "shipEnemy4");          // название текстуры корабля
                setHeightProportion(0.4f);                      // размеры корабля
                setShipVY(-0.03f);                              // скорость падения корабля
                setHp(10);                                       // прочность корабля
                setBulletName("shotEnemy");                    // название текстуры пули
                setBulletHeight(0.15f);                         // размеры пули
                setBulledDamage(4);                             // урон пули
                setBulletVY(-0.3f);                             // скорость пули
                setReloadInterval(3);                           // частота стрельбы
                setAngle(0);
                setAngleMod(0.5f);
                break;
            case 5:
                setRegions(atlas, "shipEnemy5");          // название текстуры корабля
                setHeightProportion(0.6f);                      // размеры корабля
                setShipVY(-0.02f);                              // скорость падения корабля
                setHp(15);                                      // прочность корабля
                setBulletName("shotEnemy2");                    // название текстуры пули
                setBulletHeight(0.2f);                          // размеры пули
                setBulledDamage(5);                             // урон пули
                setBulletVY(-0.2f);                             // скорость пули
                setReloadInterval(3);                           // частота стрельбы
                setAngle(0);
                setAngleMod(0);
                break;
            default:
        }
        generatePos();
        setReloadTimer(reloadInterval);
    }
}
