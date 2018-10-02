package ru.geekbrains.sprites.ships;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.pools.BulletPool;

public class Enemy1 extends Enemy {
//    private String          SHIP_NAME       = "shipEnemy1";
//    private final float     SHIP_SIZE       = 0.2f;
//    private final float     SHIP_SPEED_Y    = -0.1f;
//    private final int       HP              = 1;
//    private final String    BULLET_NAME     = "shotEnemy";
//    private final float     BULLET_SIZE     = 0.1f;
//    private final int       BULLET_DAMAGE   = 1;
//    private final float     BULLET_SPEED_Y  = -0.1f;
//    private final float     RELOAD_INTERVAL = 3;

    public Enemy1(TextureAtlas atlas,
                  BulletPool bulletPool,
                  Sound shotSound,
                  Rect worldBonds,
                  MainShip mainShip) {
        super(atlas,
                "shipEnemy1", // название корабля
                bulletPool,
                shotSound,
                worldBonds,
                mainShip);
        setHeightProportion (0.15f); // размеры корабля
        setEnemyShipV       (0, -0.1f); // скорость корабля
        this.bulletRegion = atlas.findRegion("shotEnemy"); // название снаряда
        bulletHeight =      0.1f; // размеры снаряда
        bulletV.set         (0, -0.5f); // скорость снаряда
        bulledDamage =      1; // урон
        reloadInterval =    3; // перезарядка
        hp =                1; // запас прочности
        reloadTimer = reloadInterval;
    }
}
