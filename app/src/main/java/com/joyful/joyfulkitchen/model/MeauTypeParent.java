package com.joyful.joyfulkitchen.model;

/**
 * 大类型
 */

public class MeauTypeParent {
    private int parentId;
    private String name;

    public MeauTypeParent() {
        super();
    }

    public MeauTypeParent(int parentId, String name) {
        this.parentId = parentId;
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MeauTypeParent{" +
                "parentId=" + parentId +
                ", name='" + name + '\'' +
                '}';
    }
}
