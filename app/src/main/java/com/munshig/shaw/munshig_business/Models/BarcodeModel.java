package com.munshig.shaw.munshig_business.Models;

public class BarcodeModel {
    String barcode;
    String name;
    String price;
    String packet;

    public BarcodeModel() {
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPacket() {
        return packet;
    }

    public void setPacket(String packet) {
        this.packet = packet;
    }
}
