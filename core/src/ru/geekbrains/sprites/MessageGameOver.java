package ru.geekbrains.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.Sprite;

public class MessageGameOver extends Sprite {
    public MessageGameOver(TextureAtlas atlas) {
        super(atlas, "gameOver");
        setHeightProportion(0.3f);
    }
}
