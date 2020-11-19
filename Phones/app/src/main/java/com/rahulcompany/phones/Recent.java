package com.rahulcompany.phones;

import java.util.Comparator;
import java.util.Date;

public class Recent {
    String name;
    String no;
    String duration;
    String type;
    String calltime;

    public String getName() {
        return name;
    }

    public String getNo() {
        return no;
    }

    public String getDuration() {
        return duration;
    }

    public String getType() {
        return type;
    }

    public String getCalltime() {
        return calltime;
    }

    public Recent(String name, String no, String duration, String type, String calltime) {
        this.name = name;
        this.no = no;
        this.duration = duration;
        this.type = type;
        this.calltime = calltime;
    }

    public static Comparator<Recent> c=new Comparator<Recent>() {
        @Override
        public int compare(Recent recent, Recent t1) {
            long rec = Long.parseLong(recent.getCalltime());
            long t = Long.parseLong(t1.getCalltime());
            return Long.compare(t,rec);
        }
    };
}
