package com.hiteware.olympics;

/**
 * Created on 5/11/16.
 */
public class Recording {
    String title = "";
    String description = "";
    String channel = "";
    String date = "";
    int time = 0;
    float size = 0;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChannel() { return channel; }

    public void setChannel(String channel) { this.channel = channel; }

    public String getDate() { return date; }

    public int getTime() { return time; }

    public Float getSize() { return size; }
}
