package com.munshig.shaw.munshig_business.Models;

import java.lang.reflect.Array;
import java.util.List;

public class MehboobModel{
    String name;
    String mobile_no;
    String score;
    String total_kiranas;
    String total_scans;
    String joining_date;
    List<String> kirana_progress;
    String city;

    public MehboobModel() {
    }

    public MehboobModel(String name, String mobile_no, String score, String total_kiranas, String total_scans, String joining_date, List<String> kirana_progress, String city) {
        this.name = name;
        this.mobile_no = mobile_no;
        this.score = score;
        this.total_kiranas = total_kiranas;
        this.total_scans = total_scans;
        this.joining_date = joining_date;
        this.kirana_progress = kirana_progress;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTotal_kiranas() {
        return total_kiranas;
    }

    public void setTotal_kiranas(String total_kiranas) {
        this.total_kiranas = total_kiranas;
    }

    public String getTotal_scans() {
        return total_scans;
    }

    public void setTotal_scans(String total_scans) {
        this.total_scans = total_scans;
    }

    public String getJoining_date() {
        return joining_date;
    }

    public void setJoining_date(String joining_date) {
        this.joining_date = joining_date;
    }

    public List<String> getKirana_progress() {
        return kirana_progress;
    }

    public void setKirana_progress(List<String> kirana_progress) {
        this.kirana_progress = kirana_progress;
    }
}
