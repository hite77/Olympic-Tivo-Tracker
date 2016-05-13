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
            //<b>Archer: &quot;Motherless Child&quot;</b>
            int left = result.indexOf("<b>")+ 2;
            int right = result.indexOf("</b>");

            if ((left == -1) || (right == -1)) {
                break;
            }

            String sub = result.substring(left + 1, right);
            Recording recording = new Recording();
            recording.setTitle(sub);
            recordings.add(recording);

            // Put together a new string
            result =
                    result.substring(right + 1);
        }
    }

    public ArrayList<Recording> getRecordings() {
        return recordings;
    }
}
