package com.wapsapps.scape.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

import java.text.ParseException;
import java.util.HashMap;

/**
 * Created by wap30_000 on 2014-08-04
 */
public class UIBuilder {


    private static final String UI_STRUCTURE_FILE_PATH = "data/ui.struct";
    private static final String UI_STYLE_FILE_PATH = "data/ui.style";

    private TextButton.TextButtonStyle textButtonStyle;

    private StringBuffer structureBuffer;
    private StringBuffer styleBuffer;

    private HashMap<String, String[]> structureMap;
    private HashMap<String, HashMap<String, String>> styleMap;

    private StructureParser structureParser;
    private StyleProcessor styleProcessor;

    public UIBuilder () {
        this.init();
    }

    private void init() {
        structureParser = new StructureParser();
        structureBuffer = new StringBuffer();
        styleBuffer = new StringBuffer();

        try {
            this.checkFileIntegrity();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        this.initTextButtons();
        this.loadUiStructure();
        this.loadUiStyle();
    }

    private void checkFileIntegrity() throws Exception {
        boolean structureExists = Gdx.files.internal(UI_STRUCTURE_FILE_PATH).exists(),
                styleExists = Gdx.files.internal(UI_STYLE_FILE_PATH).exists();

        if (!structureExists || ! styleExists) {
            throw new Exception("Could not find UI files! Looked in " + UI_STRUCTURE_FILE_PATH + " and " + UI_STYLE_FILE_PATH);
        }

    }

    private void loadUiStructure() {
        FileHandle handle = Gdx.files.internal(UI_STRUCTURE_FILE_PATH);

        structureBuffer.append(handle.readString());
        try {
            structureMap = structureParser.parseStructure(structureBuffer);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void loadUiStyle() {
        FileHandle handle = Gdx.files.internal(UI_STYLE_FILE_PATH);

        styleBuffer.append(handle.readString());
        try {
            styleMap = structureParser.parseStyle(styleBuffer);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void initTextButtons() {
        BitmapFont font = new BitmapFont();
        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/button.pack"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("btn_up");
        textButtonStyle.down = skin.getDrawable("btn_down");
        //textButtonStyle.checked = skin.getDrawable("btn_checked");
    }

    public TextButton buildTextButton(String text, float x, float y) {
        TextButton textButton = new TextButton(text, textButtonStyle);
        textButton.setPosition(x, y);
        return textButton;
    }

    public TextButton buildTextButton(String text, float x, float y, float width, float height) {
        TextButton textButton = new TextButton(text, textButtonStyle);

        textButton.setWidth(width);
        textButton.setHeight(height);

        textButton.setPosition(x, y);
        return textButton;
    }

    /**
     * Looks for parsed structure data for given ID and builds element using style data
     * @param elementName - string with element name, like "top-bar"
     * @return libgdx Table ui element
     */
    public Table buildMenuElement(String elementName) {
        String[] structure = this.structureMap.get(elementName);
        HashMap<String, String> rawStyle;

        Table resultTable = new Table();
        Widget currentWidget = null;

        for (String elementId : structure) {

            rawStyle = this.getStyleForElementId(elementId);
            this.buildWidgetFromRawStyle(currentWidget, rawStyle);
            resultTable.add(currentWidget);
        }
        return resultTable;
    }

    private void buildWidgetFromRawStyle(Widget currentWidget, HashMap<String, String> rawStyle) {
        try {
            currentWidget = styleProcessor.processWidgetStyle(rawStyle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HashMap<String, String> getStyleForElementId(String elementId) {
        return this.styleMap.get(elementId);
    }

}
