package org.demis.familh.service;

import org.demis.familh.model.FamilyTree;

public interface FamilyTreeService {

    public FamilyTree get(Integer primaryKey);

    public FamilyTree getEager(Integer primaryKey);

    public FamilyTreePage getPage(int pageNumber, int size);

    public void save(FamilyTree familyTree);

    public void delete(FamilyTree familyTree);
}
