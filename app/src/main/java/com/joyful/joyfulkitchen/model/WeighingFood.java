package com.joyful.joyfulkitchen.model;

/**
 * Created by Administrator on 2017/4/18.
 */

public class WeighingFood {

    private String name;

    private String weight;

    private boolean selected = false;

    public WeighingFood() {
    }

    public WeighingFood(String name, String weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
