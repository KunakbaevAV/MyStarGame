package ru.geekbrains.math;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.pools.EnemyPool;
import ru.geekbrains.sprites.ships.Enemy;
import ru.geekbrains.sprites.ships.Enemy1;

public class EnemiesEmitter {
    private final EnemyPool enemyPool;
    private Rect worldBounds;
    private float generateInterval = 4f;
    private float generateTimer;
//    private TextureAtlas atlas;

    public EnemiesEmitter(Rect worldBounds, EnemyPool enemyPool) {
        this.enemyPool = enemyPool;
        this.worldBounds = worldBounds;
    }

    public void generateEnemies(float delta){
        generateTimer += delta;
        if (generateTimer >= generateInterval){
            generateTimer = 0;
            Enemy enemy = enemyPool.obtain();
            getEnemy(enemy);
            // настроить корабль врага
            enemy.pos.x = (Rnd.nextFloat(
                    worldBounds.getLeft() + enemy.getHalfWidth(),
                    worldBounds.getRight()) - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
            enemy.resize(worldBounds);
        }
    }

    private void getEnemy(Enemy enemy) {
        float type = (float) Math.random();
        enemy = new Enemy1(
                enemyPool.getAtlas(),
                enemyPool.getBulletPool(),
                enemyPool.getShotSound(),
                enemyPool.getMainShip());
    }
}
