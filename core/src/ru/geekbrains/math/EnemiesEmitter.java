package ru.geekbrains.math;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.pools.EnemyPool;
import ru.geekbrains.sprites.ships.Enemy;

public class EnemiesEmitter {
    private final EnemyPool enemyPool;
    private Rect worldBounds;
    private float generateInterval = 4f;
    private float generateTimer;

    public EnemiesEmitter(Rect worldBounds, EnemyPool enemyPool) {
        this.worldBounds = worldBounds;
        this.enemyPool = enemyPool;
    }

    public void generateEnemies(float delta){
        generateTimer += delta;
        if (generateTimer >= generateInterval){
            generateTimer = 0;
            Enemy enemy = enemyPool.obtain();
            // настроить корабль врага
            enemy.setLeft(Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight()) - enemy.getHeight());
            enemy.setBottom(worldBounds.getTop());
        }
    }
}
