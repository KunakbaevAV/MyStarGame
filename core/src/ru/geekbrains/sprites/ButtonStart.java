package ru.geekbrains.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.AnimatedButton;
import ru.geekbrains.base.ButtonAction;
import ru.geekbrains.math.Rect;

public class ButtonStart extends AnimatedButton implements ButtonAction{
    public ButtonStart(TextureAtlas atlas) {
        super(atlas, "menu/play");
        setHeightProportion(0.15f);
    }

    @Override
    public void doButtonAction() {
        System.out.println("Game start!");
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom());
        setRight(worldBounds.getRight());
    }
}
