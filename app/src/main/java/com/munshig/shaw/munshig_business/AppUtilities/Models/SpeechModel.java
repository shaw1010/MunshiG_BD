package com.munshig.shaw.munshig_business.AppUtilities.Models;

import java.util.ArrayList;

public class SpeechModel {
    String speechName;
    String englishTransliteration;
    ArrayList speechInventoryMapping = new ArrayList();
    String category;
    String subCategory;
    String packet;
    String loose;
    ArrayList packetPrice = new ArrayList();
    ArrayList loosePrice = new ArrayList();
    ArrayList units = new ArrayList();


    public SpeechModel() {
    }

    public String getSpeechName() {
        return speechName;
    }

    public void setSpeechName(String speechName) {
        this.speechName = speechName;
    }

    public String getEnglishTransliteration() {
        return englishTransliteration;
    }

    public void setEnglishTransliteration(String englishTransliteration) {
        this.englishTransliteration = englishTransliteration;
    }

    public ArrayList getSpeechInventoryMapping() {
        return speechInventoryMapping;
    }

    public void setSpeechInventoryMapping(ArrayList speechInventoryMapping) {
        this.speechInventoryMapping = speechInventoryMapping;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getPacket() {
        return packet;
    }

    public void setPacket(String packet) {
        this.packet = packet;
    }

    public String getLoose() {
        return loose;
    }

    public void setLoose(String loose) {
        this.loose = loose;
    }

    public ArrayList getPacketPrice() {
        return packetPrice;
    }

    public void setPacketPrice(ArrayList packetPrice) {
        this.packetPrice = packetPrice;
    }

    public ArrayList getLoosePrice() {
        return loosePrice;
    }

    public void setLoosePrice(ArrayList loosePrice) {
        this.loosePrice = loosePrice;
    }

    public ArrayList getUnits() {
        return units;
    }

    public void setUnits(ArrayList units) {
        this.units = units;
    }
}