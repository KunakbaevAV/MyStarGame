package ru.geekbrains.math;

import ru.geekbrains.pools.EnemyPool;
import ru.geekbrains.sprites.ships.Enemy;

public class EnemiesEmitter {
    private final EnemyPool enemyPool;
    private Rect worldBounds;
    private float generateInterval = 4f;
    private float generateTimer;

    public EnemiesEmitter(Rect worldBounds, EnemyPool enemyPool) {
        this.enemyPool = enemyPool;
        this.worldBounds = worldBounds;
    }

    public void generateEnemies(float delta){
        generateTimer += delta;
        if (generateTimer >= generateInterval){
            generateTimer = 0;
            getEnemyType(enemyPool); // выбор типа корабля
            Enemy enemy = enemyPool.obtain(); // добавить корабль
            enemy.pos.x = (Rnd.nextFloat(
                    worldBounds.getLeft() + enemy.getWidth(),
                    worldBounds.getRight()) - enemy.getWidth());
            enemy.setBottom(worldBounds.getTop());
            enemy.resize(worldBounds);
        }
    }

    private void getEnemyType(EnemyPool enemyPool) {
        float type = (float) Math.random();
        if(type < 0.5f){
            System.out.println(1);
            enemyPool.setShipType(1);
        }else if (type < 0.8f){
            System.out.println(2);
            enemyPool.setShipType(2);
        }else {
            enemyPool.setShipType(3);
        }
    }
}
