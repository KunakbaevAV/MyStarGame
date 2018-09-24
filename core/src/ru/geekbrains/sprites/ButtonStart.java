package ru.geekbrains.sprites;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.AnimatedButton;
import ru.geekbrains.base.ButtonAction;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;

public class ButtonStart extends AnimatedButton implements ButtonAction{
    private Game game;
    public ButtonStart(TextureAtlas atlas, Game game) {
        super(atlas, "menu/play");
        setHeightProportion(0.1f);
        this.game = game;
    }

    @Override
    public void doButtonAction() {
        game.setScreen(new GameScreen(game));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom());
        setRight(worldBounds.getRight());
    }
}
