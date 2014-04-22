package org.demis.familh.service;

import org.demis.familh.model.FamilyTree;
import org.demis.familh.repository.FamilyTreeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public FamilyTreePage getPage(int pageNumber, int size) {
        Page<FamilyTree> all = familyTreeRepository.findAll(new PageRequest(pageNumber, size));
        FamilyTreePage page = new FamilyTreePage();
        page.setSize(all.getSize());
        page.setTotalElements(all.getTotalElements());
        page.setHasPrevious(all.hasPreviousPage());
        page.setTotalPage(all.getTotalPages());
        page.setNumber(all.getNumber());
        page.setHasNext(all.hasNextPage());
        page.setElements(all.getContent());

        return page;
    }

    @Transactional
    @Override
    public void save(FamilyTree familyTree) {
        familyTreeRepository.save(familyTree);
    }

    @Transactional
    @Override
    public void delete(FamilyTree familyTree) {
        familyTreeRepository.delete(familyTree);
    }
}
