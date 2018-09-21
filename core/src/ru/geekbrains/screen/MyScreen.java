package ru.geekbrains.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;

public class MyScreen extends BaseScreen {
    private Texture texture;
    private Sprite sprite;


    public MyScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        texture = new Texture("badlogic.jpg");
        sprite = new Sprite(texture);
        sprite.setSize(0.5f,0.5f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render(delta);
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        sprite.setPosition(touch.x, touch.y);
        return super.touchDown(touch, pointer);
    }
}
