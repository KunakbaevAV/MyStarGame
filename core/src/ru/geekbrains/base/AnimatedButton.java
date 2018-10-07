package ru.geekbrains.base;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class AnimatedButton extends Sprite implements ButtonAction {
    private int pointer;
    private boolean pressed;
    private float pressScale;

    public AnimatedButton(TextureAtlas atlas, String path) {
        super(atlas, path);
        pressScale = 0.8f;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (pressed || !isMe(touch)) return false;
        this.pointer = pointer;
        scale = pressScale;
        pressed = true;
        return true;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (this.pointer != pointer || !pressed) return false;
        if (isMe(touch)){
            scale = 1;
            doButtonAction();
            return true;
        }
        pressed = false;
        scale = 1;
        return false;
    }

    @Override
    public void doButtonAction() {
    }
}
