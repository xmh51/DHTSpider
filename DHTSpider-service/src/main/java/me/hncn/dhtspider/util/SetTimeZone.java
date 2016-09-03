package me.hncn.dhtspider.util;

import java.util.TimeZone;

/**
 * Created by XMH on 2016/7/16.
 */
public class SetTimeZone {
    private String timeZone;
    public  void init() {
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
    }



    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
