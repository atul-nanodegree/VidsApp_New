package com.wowapps.vidsapp;

import android.graphics.Bitmap;
/**
 * Created by somendra on 26-Dec-16.
 */

public class VidsSubCategoryItem {

    private Bitmap image;
    private String title;

    public VidsSubCategoryItem(Bitmap image, String title) {
        super();
        this.image = image;
        this.title = title;
    }
    public Bitmap getImage() {
        return image;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
