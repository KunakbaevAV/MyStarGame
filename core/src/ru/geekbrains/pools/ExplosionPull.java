package ru.geekbrains.pools;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.sprites.Explosion;

public class ExplosionPull extends SpritesPool<Explosion> {
    private final TextureRegion textureRegion;

    public ExplosionPull(TextureAtlas atlas) {
        this.textureRegion = atlas.findRegion("explosion");
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(textureRegion, 11,10,98);
    }
}
