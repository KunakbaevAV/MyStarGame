package ru.geekbrains.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.geekbrains.base.BaseScreen;

public class ScreenMatrix extends BaseScreen {
    private SpriteBatch batch;

    public ScreenMatrix(Game game) {
        super();
    }

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        batch.getProjectionMatrix().idt();
    }
}
