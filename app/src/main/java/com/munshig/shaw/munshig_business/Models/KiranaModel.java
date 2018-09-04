package com.munshig.shaw.munshig_business.Models;

public class KiranaModel {
    String name;
    String vendor_name;
    String address;
    String total_scanned;
    String size;
    String image_path;

    public KiranaModel(String name, String vendor_name, String address, String total_scanned, String size, String image_path) {
        this.name = name;
        this.vendor_name = vendor_name;
        this.address = address;
        this.total_scanned = total_scanned;
        this.size = size;
        this.image_path = image_path;
    }

    public KiranaModel() {
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
}
