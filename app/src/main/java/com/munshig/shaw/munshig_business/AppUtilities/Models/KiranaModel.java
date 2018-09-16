package com.munshig.shaw.munshig_business.AppUtilities.Models;

import java.util.List;

public class KiranaModel {
    String name;
    String vendor_name;
    String address;
    String total_scanned;
    String size;
    String image_path;
    List<String> mehboobs;
    String city;
    Boolean  stock_setting;
    String serial;


    public KiranaModel(String name,String serial, String city, String vendor_name,List<String> mehboobs, String address, String total_scanned, String size, Boolean stock_setting, String image_path) {
        this.name = name;
        this.vendor_name = vendor_name;
        this.address = address;
        this.serial = serial;
        this.total_scanned = total_scanned;
        this.size = size;
        this.image_path = image_path;
        this.mehboobs= mehboobs;
        this.city = city;
        this. stock_setting =  stock_setting;
    }

    public KiranaModel() {
    }

    public List<String> getMehboobs() {
        return mehboobs;
    }

    public void setMehboobs(List<String> mehboobs) {
        this.mehboobs = mehboobs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal_scanned() {
        return total_scanned;
    }

    public void setTotal_scanned(String total_scanned) {
        this.total_scanned = total_scanned;
    }

    public String getSize() {
        return size; }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getStock() {
        return  stock_setting;
    }

    public void setStock(Boolean  stock_setting) {
        this. stock_setting =  stock_setting;
    }

    public Boolean getStock_setting() {
        return stock_setting;
    }

    public void setStock_setting(Boolean stock_setting) {
        this.stock_setting = stock_setting;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}
