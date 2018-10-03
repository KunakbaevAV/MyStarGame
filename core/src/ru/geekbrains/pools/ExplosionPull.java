package ru.geekbrains.pools;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.sprites.Explosion;

public class ExplosionPull extends SpritesPool<Explosion> {
    private final TextureRegion textureRegion;
    private Sound expSound;

    public ExplosionPull(TextureAtlas atlas, Sound expSound) {
        this.textureRegion = atlas.findRegion("explosion");
        this.expSound = expSound;
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(textureRegion, 11,10,98, expSound);
    }
}
