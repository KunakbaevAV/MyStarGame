package ru.geekbrains.sprites.buttons;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.AnimatedButton;
import ru.geekbrains.base.ButtonAction;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.MenuScreen;

public class ButtonStart extends AnimatedButton implements ButtonAction{
    private MenuScreen screen;
    public ButtonStart(MenuScreen screen) {
        super(screen.getAtlas(), "play");
        setHeightProportion(0.15f);
        this.screen = screen;
    }

    @Override
    public void doButtonAction() {
        screen.startGame();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom());
        setRight(worldBounds.getRight());
    }
}
