package com.munshig.shaw.munshig_business.Models;

import java.util.List;

public class BarcodeModel {
    String barcode;
    String name;
    List<Long> price;
    Boolean modified;
    long stock;

    public BarcodeModel() {
    }

    public Boolean getModified() {
        return modified;
    }

    public void setModified(Boolean modified) {
        this.modified = modified;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getPrice() {
        return price;
    }

    public void setPrice(List<Long> price) {
        this.price = price;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }
}
