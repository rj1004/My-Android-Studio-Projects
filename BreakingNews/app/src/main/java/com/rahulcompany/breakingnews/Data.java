package com.rahulcompany.breakingnews;

public class Data {
    String title,desc, time,url,auther;

    public Data(String title, String desc, String time, String url,String auther) {
        this.title = title;
        this.desc = desc;
        this.time = time;
        this.url = url;
        this.auther = auther;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public Data() {
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getTime() {
        return time;
    }

    public String getUrl() {
        return url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
