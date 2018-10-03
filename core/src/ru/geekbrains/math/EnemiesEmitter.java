package ru.geekbrains.math;

import ru.geekbrains.pools.EnemyPool;

public class EnemiesEmitter {
    private final EnemyPool enemyPool;
    private float generateInterval = 4f;
    private float generateTimer;

    public EnemiesEmitter(EnemyPool enemyPool) {
        this.enemyPool = enemyPool;
    }

    public void generateEnemies(float delta){
        generateTimer += delta;
        if (generateTimer >= generateInterval){
            generateTimer = 0;
            getEnemyType(enemyPool); // выбор типа корабля
            enemyPool.obtain(); // добавить корабль
        }
    }

    private void getEnemyType(EnemyPool enemyPool) {
        float type = (float) Math.random();
        if(type < 0.5f){
            enemyPool.setShipType(1);
        }else if (type < 0.8f){
            enemyPool.setShipType(2);
        }else {
            enemyPool.setShipType(3);
        }
    }
}
