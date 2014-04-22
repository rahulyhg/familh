package org.demis.familh.service;

import org.demis.familh.model.FamilyTree;

public interface FamilyTreeService {

    public FamilyTree get(Integer primaryKey);

    public FamilyTree getEager(Integer primaryKey);
}
