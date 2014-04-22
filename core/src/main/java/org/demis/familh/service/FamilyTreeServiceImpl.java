package org.demis.familh.service;

import org.demis.familh.model.FamilyTree;
import org.demis.familh.repository.FamilyTreeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value = "FamilyTreeService")
public class FamilyTreeServiceImpl implements FamilyTreeService {

    @Resource
    private FamilyTreeRepository familyTreeRepository;

    @Override
    public FamilyTree get(Integer primaryKey) {
        return  familyTreeRepository.findOne(primaryKey);
    }

    @Override
    public FamilyTree getEager(Integer primaryKey) {
        return familyTreeRepository.findByIdEagerly(primaryKey);
    }
}
