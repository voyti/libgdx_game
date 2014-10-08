package com.wapsapps.scape.UI;

import com.badlogic.gdx.Gdx;

import java.text.ParseException;
import java.util.HashMap;

/**
 * Created by Wap on 2014-08-09
 */
public class StructureParser {


    private String LOCATION_SEPARATOR = ":";
    private String ELEMENT_SEPARATOR = "$";

    private String ELEMENT_INDICATOR = "#";
    private String RULE_SEPARATOR = ":";
    private String RULE_START = "\\{";
    private String RULE_END = "}";
    private String RULE_LINE_END = ";";
    private String COMMENT_START = "/*";

    public HashMap<String, String[]> parseStructure(StringBuffer structure) throws ParseException {
        HashMap<String, String[]> parsedStructure = new HashMap<String, String[]>();
        String lineSeparator = System.getProperty("line.separator");

        String[] lines = structure.toString().split(lineSeparator);
        String[] splitLine;

        for (String line: lines) {
            splitLine = line.split(this.LOCATION_SEPARATOR);

            Gdx.app.log("PARSER_SPAM", "[PARSER_SPAM] Parsing line : " + line);

            if (splitLine.length != 2) {
                throw new ParseException("Error parsing line, expected two parts separated by " + this.LOCATION_SEPARATOR + ": " + line, -1);
            }
            parsedStructure.put(this.getElementNameFromLine(splitLine[0]), this.getElementsFromLine(splitLine[1].trim()));
        }

        return parsedStructure;
    }

    private String[] getElementsFromLine(String chunk) {
        return chunk.split(this.ELEMENT_SEPARATOR);
    }

    /* STYLE */

    /**
     * Parse css-like file to hash maps
     * @param style StringBuffer with raw file data
     * @return hash map with style data
     * @throws ParseException
     */
    public HashMap<String, HashMap<String, String>> parseStyle(StringBuffer style) throws ParseException {
        HashMap<String, HashMap<String, String>> parsedStyle = new HashMap<String, HashMap<String, String>> ();
        String lineSeparator = System.getProperty("line.separator");

        String[] lines = style.toString().split(lineSeparator);

        parseStyleLines(parsedStyle, lines);

        return parsedStyle;
    }

    private void parseStyleLines(HashMap<String, HashMap<String, String>> parsedStyle, String[] lines) throws ParseException {
        boolean isInsideElement = false;
        String insideElementName = "";

        String[] rawRule;
        HashMap<String, String> currentElemRules = new HashMap<String, String>();


        for (String line: lines) {
            Gdx.app.debug("PARSER_SPAM", "[PARSER_SPAM] Parsing line : " + line);

            if (line.trim().isEmpty() || line.startsWith(this.COMMENT_START)) {
                continue;
            } else if (line.startsWith(this.RULE_END)) {

                isInsideElement = false;
                parsedStyle.put(insideElementName, currentElemRules);
                currentElemRules = new HashMap<String, String>();

            } else if (isInsideElement) {

                rawRule = line.split(this.RULE_SEPARATOR);
                currentElemRules.put(rawRule[0].trim(), this.trimRuleValue(rawRule[1]));

            } else if (line.startsWith(this.ELEMENT_INDICATOR)) {

                insideElementName = this.getElementNameFromLine(line);
                isInsideElement = true;

            }  else {
                throw new ParseException("Unknown state (outside element, not a rule end or start) parsing line : " + line, -1);
            }
        }
    }

    private String trimRuleValue(String ruleValue) {
        ruleValue = ruleValue.replace(this.RULE_LINE_END, "").trim();
        return ruleValue;
    }

    private String getElementNameFromLine(String line) {
        line = line.replaceAll(this.ELEMENT_INDICATOR, "");
        line = line.replaceAll(this.RULE_START, "");
        line = line.trim();

        return line;
    }



}
