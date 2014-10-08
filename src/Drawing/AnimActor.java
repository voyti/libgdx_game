package com.wapsapps.scape.Drawing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by wap30_000 on 2014-08-04.
 */
public class AnimActor extends Actor {

    private String texturePath = null;
    private final float width;
    private final float height;
    private final int frameRows;
    private final int frameCols;
    private float frameDuration;

    private Texture animSheet;
    private TextureRegion[] frames;
    private Animation animation;
    private float stateTime;
    private TextureRegion frame;
    private float delta;


    public AnimActor(String texturePath, float width, float height,  int frameRows, int frameCols, float frameDuration) {
        this.texturePath = texturePath;
        this.width = width;
        this.height = height;
        this.frameRows = frameRows;
        this.frameCols = frameCols;
        this.frameDuration = frameDuration;

        this.init();
    }

    /**
     * Optimize AnimActor reusing texture
     */
    public AnimActor(Texture animSheet, float width, float height, int frameRows, int frameCols, float frameDuration) {
        this.animSheet = animSheet;
        this.width = width;
        this.height = height;
        this.frameRows = frameRows;
        this.frameCols = frameCols;
        this.frameDuration = frameDuration;

        this.init();
    }

    private void init() {
        int index = 0;

        this.setWidth(this.width);
        this.setHeight(this.height);

        animSheet = new Texture(Gdx.files.internal(texturePath));
        TextureRegion[][] tmp = TextureRegion.split(animSheet, animSheet.getWidth() / frameCols, animSheet.getHeight() / frameRows);
        frames = new TextureRegion[frameRows * frameCols];


        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        animation = new Animation(frameDuration, frames);
        stateTime = 0f;
    }

    @Override
    public void act(float delta) {
        this.stateTime += delta;
        
        super.act(delta);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
//        Gdx.app.log("GDX", "draw: delta:" + delta);
        frame = animation.getKeyFrame(stateTime, true);

        batch.draw(frame, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}
