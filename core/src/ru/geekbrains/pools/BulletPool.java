package ru.geekbrains.pools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.sprites.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    public final Sound[] shotSounds = new Sound[4];

    public BulletPool() {
        shotSounds[0] = Gdx.audio.newSound(Gdx.files.internal("sounds/shotSound.wav"));
        shotSounds[1] = Gdx.audio.newSound(Gdx.files.internal("sounds/shotProt1.wav"));
        shotSounds[2] = Gdx.audio.newSound(Gdx.files.internal("sounds/shotProt2.wav"));
        shotSounds[3] = Gdx.audio.newSound(Gdx.files.internal("sounds/shotProtBig.wav"));
    }

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }

    @Override
    public void dispose() {
        super.dispose();
        for (Sound sound : shotSounds) {
            sound.dispose();
        }
    }
}
