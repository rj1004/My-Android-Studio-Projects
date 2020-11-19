package com.rahulcompany.papersin;

import java.io.Serializable;

public class Datas implements Serializable {
    String previewurl,orignalurl;
    int size;

    public String getPreviewurl() {
        return previewurl;
    }

    public void setPreviewurl(String previewurl) {
        this.previewurl = previewurl;
    }

    public String getOrignalurl() {
        return orignalurl;
    }

    public void setOrignalurl(String orignalurl) {
        this.orignalurl = orignalurl;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
