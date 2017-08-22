package com.mark.ss;

import java.util.Comparator;

/**
 * Created by 33154 on 2017/8/22.
 */

public class Number implements Comparator<Number>{
    private String comCode;
    private String id;
    private int noCount;
    private String noPre;
    private String startTime;

    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNoCount() {
        return noCount;
    }

    public void setNoCount(int noCount) {
        this.noCount = noCount;
    }

    public String getNoPre() {
        return noPre;
    }

    public void setNoPre(String noPre) {
        this.noPre = noPre;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public int compare(Number o1, Number o2) {
        return o1.noCount-o2.noCount;
    }
}
