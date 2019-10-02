package com.example.dincerkizildere.notes;

import java.util.Date;

public class ClipObject {

    public final static String markStar = "☆★☆";

    protected String text;
    protected Date date;
    protected boolean star;

    public ClipObject(String text, Date date) {
        this.text = text;
        this.date = date;
        this.star = false;
    }
    public ClipObject(String text, Date date, boolean star) {
        this.text = text;
        this.date = date;
        this.star = star;
    }
    public String getText() {
        return text;
    }
    public Date getDate() {
        return date;
    }
    public  boolean isStarred() {
        return star;
    }
    public ClipObject setStarred(boolean isStarred) {
        this.star = isStarred;
        return this;
    }
}