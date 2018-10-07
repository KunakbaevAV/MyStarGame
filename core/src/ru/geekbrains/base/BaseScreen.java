package ru.geekbrains.base;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.math.MatrixUtils;
import ru.geekbrains.math.Rect;

/**
 * @author Kunakbaev Artem
 */
public class BaseScreen implements Screen, InputProcessor{
    protected final float VOLUME = 0.02f;
    private Rect screenBounds;
    protected Rect worldBounds;
    private Rect glBounds;
    protected SpriteBatch batch;
    private Matrix4 worldToGl;
    private Matrix3 screenToWorld;
    private Vector2 touch;

    public BaseScreen() {
        Gdx.input.setInputProcessor(this);
        screenBounds = new Rect();
        worldBounds = new Rect();
        glBounds = new Rect(0,0,1f,1f);
        batch = new SpriteBatch();
        worldToGl = new Matrix4();
        screenToWorld = new Matrix3();
        touch = new Vector2();
    }

    public Rect getWorldBounds() {
        return worldBounds;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        screenBounds.setSize(width,height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        float aspectWorld = width / (float) height;
        worldBounds.setHeight(1f);
        worldBounds.setWidth(1f * aspectWorld);

        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        batch.setProjectionMatrix(worldToGl);

        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);

        resize(worldBounds);
    }

    public void resize(Rect worldBounds){
        System.out.println("resize width=" + worldBounds.getWidth() + " height=" + worldBounds.getHeight());
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, reverseY(screenY)).mul(screenToWorld);
        touchDown(touch, pointer);
        return false;
    }

    public boolean touchDown(Vector2 touch, int pointer){
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, reverseY(screenY)).mul(screenToWorld);
        touchUp(touch,pointer);
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer){
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touch.set(screenX, reverseY(screenY)).mul(screenToWorld);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        touch.set(screenX, reverseY(screenY)).mul(screenToWorld);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private int reverseY(int y){
        return Gdx.graphics.getHeight() - y;
    }
}

