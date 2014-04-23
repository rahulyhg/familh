package org.demis.familh.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "family_tree")
public class FamilyTree implements Serializable {


    private int id;
    private String name;
    private java.sql.Timestamp creationDate = null;
    private java.sql.Timestamp modificationDate = null;

    public FamilyTree() {
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

    @Column(name = "creation_date", nullable = true, unique = false, length = 29)
    public java.sql.Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(java.sql.Timestamp creationDate) {
        this.creationDate = creationDate;
    }
    @Column(name = "modification_date", nullable = true, unique = false, length = 29)
    public java.sql.Timestamp getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(java.sql.Timestamp modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Override
    public String toString() {
        return "FamilyTree{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
