package com.ivchen.refresh;

public class Drink {
    private String name;
    private int drawableId;

    public Drink(String name, int drawableId) {
        this.name = name;
        this.drawableId = drawableId;
    }

    public String getName() {
        return name;
    }

    public int getDrawableId() {
        return drawableId;
    }
}
