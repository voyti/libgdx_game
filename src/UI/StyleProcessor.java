package com.wapsapps.scape.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Wap on 2014-08-10
 */
public class StyleProcessor {

    private final static String TYPE_TABLE = "table";
    private final static String TYPE_LABEL = "label";
    private final static String TYPE_IMAGE = "image";



    public Widget processWidgetStyle(HashMap<String, String> rawStyle) throws Exception {
        Iterator iter = rawStyle.entrySet().iterator();
        Widget resultWidget;
        String key,
               value;

        for (Map.Entry<String, String> entry : rawStyle.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();

            Gdx.app.log("GDX", "Key = " + key + ", Value = " + value);

//            switch (key) {
//                case "type":
//                    resultWidget = this.createWidgetFromTypeString(value);
//                    break;
//                case "content":
//                    //this.createWidgetFromTypeString(resultWidget);
//                    break;
//            }

        }

        return null;
    }

    private Widget createWidgetFromTypeString(String value) throws Exception {
        Widget resultWidget = null;

        if (value.equals(TYPE_IMAGE)) {
            resultWidget = new Image();
        } else if (value.equals(TYPE_LABEL)) {
            resultWidget = new Label("", new Label.LabelStyle());
        } else if (value.equals(TYPE_TABLE)) {
            throw new Exception("Misused processWidgetStyle: Cannot create Table as Widget (rule type: table; passed to element which was expected to be a widget)");
        }
        return null;
    }
}

