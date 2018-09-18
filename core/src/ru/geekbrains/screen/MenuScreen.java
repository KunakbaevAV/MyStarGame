package ru.geekbrains.screen;
/**
 * @author Kunakbaev Artem
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private SpriteBatch batch;
    private Texture ship;
    private Texture background;
    private Vector2 poz;
    private Vector2 speed;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        ship = new Texture("ship.png");
        background = new Texture("space.jpg");
        poz = new Vector2(0f, 0f);
        speed = new Vector2(0.5f, 0.5f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(ship, poz.x, poz.y);
        poz.add(speed);
        batch.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("Нажатие: screenX=" + screenX + "  screenY=" + screenY);// не работает
        poz.add(speed);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public void dispose() {
        batch.dispose();
        ship.dispose();
        background.dispose();
        super.dispose();

    }
}

