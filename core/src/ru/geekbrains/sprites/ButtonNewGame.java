package ru.geekbrains.sprites;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.AnimatedButton;
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
        System.out.println("new game");
        screen.startNewGame();
    }
}
