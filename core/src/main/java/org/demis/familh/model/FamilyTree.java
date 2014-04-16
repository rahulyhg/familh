package org.demis.familh.model;

public class FamilyTree {


    private int id;
    private String name;

    public FamilyTree() {
    }

    @Override
    public String toString() {
        return "FamilyTree{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
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
}
