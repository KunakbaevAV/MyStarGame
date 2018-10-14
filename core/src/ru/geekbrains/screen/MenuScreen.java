package ru.geekbrains.screen;
/**
 * author Kunakbaev Artem
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprites.Background;
import ru.geekbrains.sprites.BigStar;
import ru.geekbrains.sprites.buttons.ButtonExit;
import ru.geekbrains.sprites.buttons.ButtonStart;
import ru.geekbrains.sprites.Star;

public class MenuScreen extends BaseGameScreen {

    private Music gameMusic;
    private Music menuMusic;

    public MenuScreen(Game game) {
        super(game);
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/backSound.mp3"));
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/Twelve Titans Music - Monolith.mp3"));
        menuMusic.play();
        menuMusic.setVolume(VOLUME);
    }

    @Override
    public void show() {
        super.show();
        ButtonStart btnStart = new ButtonStart(this);
        spites.add(btnStart);
        addLogo();
    }

    private void addLogo() {
        Sprite logo = new Sprite(atlas, "logo");
        logo.setHeightProportion(0.3f);
        spites.add(logo);
    }

    @Override
    public void dispose() {
        super.dispose();
        menuMusic.dispose();
        gameMusic.dispose();
    }

    public void startGame(){
        menuMusic.stop();
        game.setScreen(new GameScreen(game, gameMusic));
    }
}

