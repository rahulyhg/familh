package org.demis.familh.repository;

import org.demis.familh.model.FamilyTree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FamilyTreeRepository extends JpaRepository<FamilyTree, Integer> {

    @Query("SELECT entity FROM FamilyTree entity WHERE entity.id = (:id)")
    public FamilyTree findByIdEagerly(@Param("id") Integer id);
}