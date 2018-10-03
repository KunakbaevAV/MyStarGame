package ru.geekbrains.sprites.ships;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.math.Rect;
import ru.geekbrains.pools.BulletPool;
import ru.geekbrains.pools.ExplosionPull;

public class Enemy2 extends Enemy {

    public Enemy2(TextureAtlas atlas,
                  BulletPool bulletPool,
                  ExplosionPull explosionPull,
                  Sound shotSound,
                  Rect worldBonds,
                  MainShip mainShip) {
        super(atlas,
                "shipEnemy2", // название корабля
                explosionPull,
                bulletPool,
                shotSound,
                worldBonds,
                mainShip);
        setHeightProportion (0.2f); // размеры корабля
        setEnemyShipV       (0, -0.09f); // скорость корабля
        this.bulletRegion = atlas.findRegion("shotEnemy"); // название снаряда
        bulletHeight =      0.15f; // размеры снаряда
        bulletV.set         (0, -0.4f); // скорость снаряда
        bulledDamage =      2; // урон
        reloadInterval =    3.5f; // перезарядка
        hp =                2; // запас прочности
        reloadTimer = reloadInterval;
        generatePos();
    }
}
