package com.joyful.joyfulkitchen.model;

/**
 * 大类型--> 小类型
 */

public class MeauType {
    private int id;
    private String name;

    private int parentId;

    public MeauType() {
    }

    public MeauType(int id, String name, int parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "MeauType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}
