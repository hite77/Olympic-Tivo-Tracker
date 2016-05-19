package com.hiteware.olympics;

import java.util.ArrayList;

/**
 * Created on 5/3/16.
 */
public class parser {

    ArrayList<Recording> recordings = new ArrayList<>();
    ArrayList<String> folders = new ArrayList<>();

    private String baseFolderName = "https://192.168.50.146/nowplaying/";

    private String replaceQuot(String text) { return text.replaceAll("&quot;", "\""); }
    private String replaceAmp(String text) { return text.replaceAll("&amp;", "&"); }

    public void parse(String result) {
        result = replaceAmp(replaceQuot(result));
        while (true) {
            String line = findPieceOfString(result,"<tr ","</tr>");
            if (line.equals("")) break;

            result = result.substring(result.indexOf("</tr>") + 1);

            String folder = findPieceOfString(line,"<a href=\"","\">folder</a>");
            if (!folder.equals("")) {
                folders.add(baseFolderName+folder);
            }
            else {
                Recording recording = new Recording();

                String title = findPieceOfString(line, "<b>", "</b>");
                recording.setTitle(title);
                recording.setDescription(findPieceOfString(line, "<br>", "</td>"));
                recording.setChannel(findPieceOfString(line, "alt=\"", "\">"));

                if (!title.equals("")) recordings.add(recording);
            }
        }
    }

    private String findPieceOfString(String line, String startString, String endString) {
        String result = "";
        int leftSide = line.indexOf(startString) + startString.length();
        int rightSide = line.indexOf(endString, leftSide);

        if ((leftSide != -1) && (rightSide != -1)) {
            result = line.substring(leftSide, rightSide);
        }
        return result;
    }

    public ArrayList<Recording> getRecordings() {
        return recordings;
    }

    public ArrayList<String> getFolders() { return folders; }
}
