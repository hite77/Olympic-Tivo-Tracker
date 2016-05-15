package com.hiteware.olympics;

import java.util.ArrayList;

/**
 * Created on 5/3/16.
 */
public class parser {

    ArrayList<Recording> recordings = new ArrayList<>();

    private String replaceQuot(String text) { return text.replaceAll("&quot;", "\""); }

    public void parse(String result) {
        result = replaceQuot(result);
        while (true) {
            String line = findPieceOfString(result,"<tr ","</tr>", 4);
            if (line.equals("")) break;

            result = result.substring(result.indexOf("</tr>") + 1);
            Recording recording = new Recording();

            String title = findPieceOfString(line,"<b>","</b>",3);
            recording.setTitle(title);
            recording.setDescription(findPieceOfString(line, "<br>", "</td>",4));

            if (!title.equals("")) recordings.add(recording);
        }
    }

    private String findPieceOfString(String line, String startString, String endString, int startOffset) {
        String result = "";
        int leftSide = line.indexOf(startString) + startOffset;
        int rightSide = line.indexOf(endString, leftSide);

        if ((leftSide != -1) && (rightSide != -1)) {
            result = line.substring(leftSide, rightSide);
        }
        return result;
    }

    public ArrayList<Recording> getRecordings() {
        return recordings;
    }
}
