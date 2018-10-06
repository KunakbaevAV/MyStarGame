package ru.geekbrains.sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;

public class Explosion extends Sprite {
    private float animateTimer;
    private float animateInterval = 0.17f;
    private Sound expSound;

    public Explosion(TextureRegion region, int rows, int cols, int frames, Sound expSound) {
        super(region, rows, cols, frames);
        this.expSound = expSound;
    }

    public void set(Vector2 posExplosion, float size) {
        this.pos.set(posExplosion);
        setHeightProportion(size);
        expSound.play(VOLUME);
    }

    public void set(Vector2 posExplosion, float size, float volumeMod) {
        this.pos.set(posExplosion);
        setHeightProportion(size);
        expSound.play(VOLUME * volumeMod);
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= animateInterval) animateTimer = 0;
        if (++frame == regions.length) destroy();
    }

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }
}
