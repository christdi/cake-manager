package com.waracle.cakemgr.model;

import java.io.Serializable;

public class CakeModel implements Serializable {

    private static final long serialVersionUID = -2417760290457013668L;

    private String title;
    private String desc;
    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
