package com.rahulcompany.takebook;

import java.util.Comparator;

public class DataModel {
    String date,msg, username;

    public static Comparator<DataModel> c=new Comparator<DataModel>() {
        @Override
        public int compare(DataModel dataModel, DataModel t1) {

            return t1.getDate().compareTo(dataModel.getDate());
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public DataModel(String date, String msg, String username) {
        this.date = date;
        this.msg = msg;
        this.username = username;
    }



    public DataModel() {
    }
}
