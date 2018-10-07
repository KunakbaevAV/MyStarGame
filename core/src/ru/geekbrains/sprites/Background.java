package ru.geekbrains.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Background extends Sprite {
//    Texture backgroudTexture = new Texture("space.png");
//    TextureRegion region = new TextureRegion(backgroudTexture);
    public Background(TextureRegion region) {
        super(region);
        setHeightProportion(1);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        if(worldBounds.getHeight() > worldBounds.getWidth()) setHeightProportion(1);
        else setWightProportion(worldBounds.getWidth());
    }
}
