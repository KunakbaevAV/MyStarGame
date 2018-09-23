package ru.geekbrains.screen;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;

public class ExitButton extends Sprite {
    private float aspect;

    public ExitButton(TextureRegion region, float aspect) {
        super(region);
        this.aspect = aspect;
        setPosition(0.4f * aspect, 0.4f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (isMe(touch)) {
            System.out.println("Exit");
            System.out.println(aspect);
            setPosition(0.4f * aspect, 0.4f);
        }
        return super.touchDown(touch, pointer);
    }
}
