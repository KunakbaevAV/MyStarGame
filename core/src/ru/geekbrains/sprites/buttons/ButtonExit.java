package ru.geekbrains.sprites.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.AnimatedButton;
import ru.geekbrains.base.ButtonAction;
import ru.geekbrains.math.Rect;

public class ButtonExit extends AnimatedButton implements ButtonAction {

    public ButtonExit(TextureAtlas atlas) {
        super(atlas, "exit");
        setHeightProportion(0.15f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom());
        setLeft(worldBounds.getLeft());
    }

    @Override
    public void doButtonAction() {
        Gdx.app.exit();
    }
}
