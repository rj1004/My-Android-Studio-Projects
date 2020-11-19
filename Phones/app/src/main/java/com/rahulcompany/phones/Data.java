package com.rahulcompany.phones;

import java.util.Comparator;

public class Data {
    String name;
    String no;

    public Data(String name, String no) {
        this.name = name;
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public String getNo() {
        return no;
    }

    public Data() {
    }

    public static Comparator<Data> c = new Comparator<Data>() {
        @Override
        public int compare(Data data, Data t1) {
            String name1 = data.getName().toLowerCase();
            String name2 = t1.getName().toLowerCase();
            return name1.compareTo(name2);
        }
    };
}
