package com.sigon.coinman.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

public class CoinMan extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture background;
    private Texture coin;
    private Texture bomb;
    private Texture[] man;
    private int manState = 0;
    private int pause = 0;
    private float gravity = 0.2f;
    private float velocity = 0;
    private int manY = 0;
    private int coinCount;
    private int bombCount;
    private Random random;

    private ArrayList<Integer> coinXs = new ArrayList<>();
    private ArrayList<Integer> coinYs = new ArrayList<>();
    private ArrayList<Integer> bombXs = new ArrayList<>();
    private ArrayList<Integer> bombYs = new ArrayList<>();

    @Override
    public void create () {
        batch = new SpriteBatch();
        background = new Texture("bg.png");
        bomb = new Texture("bomb.png");

        man = new Texture[4];
        man[0] = new Texture("frame-1.png");
        man[1] = new Texture("frame-2.png");
        man[2] = new Texture("frame-3.png");
        man[3] = new Texture("frame-4.png");


        manY = Gdx.graphics.getHeight() / 2;
        coin = new Texture("coin.png");

        random = new Random();
    }

    private void makeCoin(){
        float height = random.nextFloat() * Gdx.graphics.getHeight();
        coinYs.add((int) height);
        coinXs.add(Gdx.graphics.getWidth());
    }

    private void makeBomb(){
        float height = random.nextFloat() * Gdx.graphics.getHeight();
        bombYs.add((int) height);
        bombXs.add(Gdx.graphics.getWidth());
    }

    @Override
    public void render () {
        batch.begin();
        batch.draw(background,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (bombCount < 250)
            bombCount++;
        else{
            bombCount = 0;
            makeBomb();
        }
        for (int i = 0; i < bombXs.size(); i++) {
            batch.draw(bomb, bombXs.get(i), bombYs.get(i));
            bombXs.set(i, bombXs.get(i));
        }

        if (coinCount < 100)
            coinCount++;
        else {
            coinCount = 0;
            makeCoin();
        }

        for (int i = 0; i < coinXs.size(); i++) {
            batch.draw(coin, coinXs.get(i), coinYs.get(i));
            coinXs.set(i, coinXs.get(i) - 4);
        }



        if (Gdx.input.justTouched()){
            velocity = -10;
        }
/*
        if (pause < 1)
            pause++;
        else {
            pause = 0;

*/
            if (manState < 3)
                manState++;
            else
                manState = 0;
//        }


        velocity += gravity;
        manY -= velocity;

        if (manY <= 0){
            manY = 0;
        }

        batch.draw(man[manState], Gdx.graphics.getWidth() / 2 - man[0].getWidth() / 2,  manY);

        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
    }
}
