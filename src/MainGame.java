package com.wapsapps.scape;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.wapsapps.scape.Logic.Mission;
import com.wapsapps.scape.Drawing.AnimActor;
import com.wapsapps.scape.UI.UIBuilder;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;

    private int screenWidth = 1280;
    private int screenHeight = 720;

    Stage stage;

    private PerspectiveCamera camera;
    private UIBuilder uiBuilder;
    private AnimActor privateAnim;

    @Override
	public void create () {
        Gdx.app.log("GDX", "create: start");

        batch = new SpriteBatch();
        uiBuilder = new UIBuilder();

        camera = new PerspectiveCamera();
        ExtendViewport viewport = new ExtendViewport(screenWidth, screenHeight);
        
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        this.buildBeforeMissionScene(Mission.TYPE_SCAVENGE);
        this.createAnimation();
	}

    private void createAnimation() {
        Gdx.app.log("GDX", "createAnimation");

        privateAnim = new AnimActor("priv_anim.png", 63, 102, 1, 2, 0.5f);

        stage.addActor(privateAnim);
    }

    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
	public void render () {
        //Gdx.app.log("GDX", "render");

		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void buildBeforeMissionScene(int type) {
        Gdx.app.log("GDX", "buildBeforeMissionScene");

        switch (type) {
            case Mission.TYPE_EXPLORE:
                buildBeforeMissionUI();
                break;
            case Mission.TYPE_SCAVENGE:
                buildBeforeMissionUI();
                break;
            case Mission.TYPE_DEFEND:
                buildBeforeMissionUI();
                break;
            default:
                Gdx.app.error("GDX", "buildBeforeMissionScene: unknown type: " + type, null);
                break;
        }
    }

    private void buildBeforeMissionUI() {
        Gdx.app.log("GDX", "buildBeforeMissionUI");

        final int[] personelCount = {1};


        TextButton addPvtBtn = uiBuilder.buildTextButton("Add private", 100, 500, 120, 50);
        stage.addActor(addPvtBtn);

        TextButton addSgtBtn = uiBuilder.buildTextButton("Add a sergeant", 240, 500, 120, 50);
        stage.addActor(addSgtBtn);

        TextButton addOfcBtn = uiBuilder.buildTextButton("Add an officer", 380, 500, 120, 50);
        stage.addActor(addOfcBtn);

        stage.addActor(uiBuilder.buildTextButton("Send", 100, 400, 100, 50));

        addPvtBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                personelCount[0]++;

                privateAnim = new AnimActor("priv_anim.png", 63, 102, 1, 2, 0.5f);
                privateAnim.setX(68 * (personelCount[0] - 1));
                stage.addActor(privateAnim);
            }
        });

        addSgtBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                personelCount[0]++;

                privateAnim = new AnimActor("sgt_1.png", 63, 102, 1, 1, 0.5f);
                privateAnim.setX(68 * (personelCount[0] - 1));
                stage.addActor(privateAnim);
            }
        });

        addOfcBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                personelCount[0]++;

                privateAnim = new AnimActor("co_1.png", 42, 99, 1, 1, 0.5f);
                privateAnim.setX(68 * (personelCount[0] - 1));
                stage.addActor(privateAnim);
            }
        });
    }

    @Override
    public void dispose () {
        Gdx.app.log("GDX", "dispose");
        stage.dispose();

    }

}
