package com.joyful.joyfulkitchen.model;

/**
 * 用来显示记录 的
 */
public class RecordItem {
    private String meauName;
    private double totalWeight;
    private double totalEnergy;

    public String getMeauName() {
        return meauName;
    }

    public void setMeauName(String meauName) {
        this.meauName = meauName;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public double getTotalEnergy() {
        return totalEnergy;
    }

    public void setTotalEnergy(double totalEnergy) {
        this.totalEnergy = totalEnergy;
    }
}
