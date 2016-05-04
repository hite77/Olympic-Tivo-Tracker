package com.hiteware.olympics;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created on 5/3/16.
 */
public class parser {

    ArrayList<String> items = new ArrayList<>();

    public void parse(String result) {

        while (true) {
            //<b>Archer: &quot;Motherless Child&quot;</b>

            int left = result.indexOf("<b>")+ 2;
            int right = result.indexOf("</b>");

            if (left == -1) break;

            String sub = result.substring(left + 1, right);

            items.add(sub);
            Log.v("Output", sub);

            // Put together a new string
            result =
                    result.substring(right + 5);
        }
        items.size();
    }
}
