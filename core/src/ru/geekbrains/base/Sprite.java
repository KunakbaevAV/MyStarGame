package ru.geekbrains.base;

/**
 * оболочка для объекта
 */
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Regions;

public class Sprite extends Rect {

    protected float angle;
    protected float scale = 1f;
    protected TextureRegion[] regions;
    protected int frame;
    private boolean isDestroyed;

    public Sprite() {
    }

    public Sprite(TextureRegion region, int rows, int cols, int frames) {
        this.regions = Regions.split(region, rows, cols, frames);
    }

    public Sprite(TextureRegion region) {
        if (region == null) {
            throw new NullPointerException("region == null");
        }
        regions = new TextureRegion[1];
        regions[0] = region;
        setHeightProportion(0.1f);
    }

    public Sprite(TextureRegion region, float size) {
        if (region == null) {
            throw new NullPointerException("region == null");
        }
        regions = new TextureRegion[1];
        regions[0] = region;
        setHeightProportion(size);
    }

    public Sprite(TextureAtlas atlas, String path){
        if (atlas == null) {
            throw new NullPointerException("atlas == null");
        }
        regions = new TextureRegion[1];
        regions[0] = atlas.findRegion(path);
    }

    public void setHeightProportion(float height) {
        setHeight(height);
        Float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }

    public void setWightProportion(float widht) {
        setWidth(widht);
        Float aspect = regions[frame].getRegionHeight() / (float) regions[frame].getRegionWidth();
        setHeight(widht * aspect);
    }

//    public void setPosition(Vector2 position){
//        setLeft(position.x);
//        setBottom(position.y);
//    }

    public void setPosition(float x, float y){
        setLeft(x);
        setBottom(y);
    }

    public void resize(Rect worldBounds) {
    }

    public void update(float delta) {

    }

    public boolean touchDown(Vector2 touch, int pointer) {

        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer) {

        return false;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void destroy() {
        this.isDestroyed = true;
    }

    public void flushDestroy() {
        this.isDestroyed = false;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                getLeft(), getBottom(), // точка отрисовки
                halfWidth, halfHeight, // точка вращения
                getWidth(), getHeight(),
                scale, scale, // масштаб по x и y
                angle // угол поворота
        );
    }
}
