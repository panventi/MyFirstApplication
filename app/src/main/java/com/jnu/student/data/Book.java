package com.jnu.student.data;

import java.io.Serializable;

public class Book implements Serializable {
    private String title;
    private int coverResourceId;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCoverResourceId(int coverResourceId) {
        this.coverResourceId = coverResourceId;
    }

    public Book(String title, int coverResourceId) {
        this.title = title;
        this.coverResourceId = coverResourceId;
    }

    public String getTitle() {
        return title;
    }

    public int getCoverResourceId() {
        return coverResourceId;
    }
}
