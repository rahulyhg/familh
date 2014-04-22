package org.demis.familh.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "family_tree")
public class FamilyTree implements Serializable {


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

    @Id
    @Column(name = "family_tree_id", precision = 10)
    @SequenceGenerator(name="FamilyTreeSequence",sequenceName="family_tree_sequence")
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="FamilyTreeSequence")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false, unique = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
