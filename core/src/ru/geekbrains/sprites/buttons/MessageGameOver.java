package ru.geekbrains.sprites.buttons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class MessageGameOver extends Sprite {
    public MessageGameOver(TextureAtlas atlas) {
        super(atlas, "gameOver");
        setHeightProportion(0.35f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setTop(worldBounds.getTop() - getHalfHeight());
    }
}
