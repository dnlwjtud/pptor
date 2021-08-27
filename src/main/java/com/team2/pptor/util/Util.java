package com.team2.pptor.util;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {

    /*
    마크다운을 MAP 으로
     */
    public static Map<String, Object> shapeMarkdown(String source) {

        Map<String, Object> result = new HashMap<>();

        String[] sources = source.trim().split("-----");

        String[] wholeContents = sources[1].split("\r\n");

        List<String> content = new ArrayList<>();

        for ( String data : wholeContents ) {

            if ( data.isBlank() ) {
                continue;
            }

            content.add(data);

        }

        result.put("code", content.get(0));
        content.remove(0);

        ArrayList<ArrayList<String>> contentsBox = new ArrayList<ArrayList<String>>();

        for ( String data : content ) {

            ArrayList<String> contentTexts = new ArrayList<String>();

            String[] splitData = data.split(" ", 2);

            contentTexts.add(splitData[0]);
            contentTexts.add(splitData[1]);

            contentsBox.add(contentTexts);

        }

        result.put("content", contentsBox);

        return result;
    }
    
    /*
    HTML 파싱
     */
    public static List<String> htmlParsing(String html) {

        List<String> parsedHTML = new ArrayList<>();

        for ( String arg : html.trim().split("\r\n") ) {
            parsedHTML.add(arg);
        }

        parsedHTML.set(0, "<section>" );

        if ( parsedHTML.get(parsedHTML.size()-1).startsWith("__") ) {

            String parser = parsedHTML.get(parsedHTML.size()-2).replace("<br>", "</p>");
            parsedHTML.set(parsedHTML.size()-2,parser);
            parsedHTML.set(parsedHTML.size()-1, "</section>");

        } else if ( parsedHTML.get(parsedHTML.size()-1).startsWith("<p>") ) {

            parsedHTML.set(parsedHTML.size()-1, "</section>");

        }

        return parsedHTML;

    }


}
