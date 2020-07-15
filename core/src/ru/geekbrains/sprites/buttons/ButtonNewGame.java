package ru.geekbrains.sprites.buttons;

import ru.geekbrains.base.AnimatedButton;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;

public class ButtonNewGame extends AnimatedButton {
    private GameScreen screen;

    public ButtonNewGame(GameScreen screen) {
        super(screen.getAtlas(), "gameStart");
        this.screen = screen;
        setHeightProportion(0.3f);
        setBottom(-0.4f);
    }

    @Override
    public void doButtonAction() {
        screen.startNewGame();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + getHalfHeight());
    }
}
