package org.demis.familh.dto;

import org.demis.familh.model.FamilyTree;
import org.demis.familh.service.FamilyTreePage;

import java.util.ArrayList;
import java.util.List;

public class FamilyTreeDTOConverter {

    public static FamilyTreeDTO convert (FamilyTree entity) {
        FamilyTreeFullDTO dto = new FamilyTreeFullDTO();
        // Primary Key
        dto.setId(entity.getId());

        // raw properties
        dto.setName(entity.getName());
        dto.setUri("/rest/api/v1/familyTree/" + entity.getId() );

        return dto;
    }

    public static FamilyTreeDTOPage convertPage(FamilyTreePage entities) {
        FamilyTreeDTOPage dto = new FamilyTreeDTOPage();
        dto.hasNext(entities.isHasNext());
        dto.hasPrevious(entities.isHasPrevious());
        dto.setPageNumber(entities.getNumber());
        dto.setPageSize(entities.getSize());
        dto.setTotalElements(entities.getTotalElements());
        dto.setTotalPage(entities.getTotalPage());

        List<FamilyTreeDTO> elements = new ArrayList<FamilyTreeDTO>();

        for (FamilyTree entity: entities.getElements()) {
            elements.add(convert(entity));
        }
        dto.setElements(elements);

        // next and previous uri
        if (entities.isHasNext()) {
            dto.setNextUri("/rest/api/v1/familyTree/?pageNumber=" + (entities.getNumber() + 1) + "&pageSize=" +  entities.getSize());
        }
        if (entities.isHasPrevious()) {
            dto.setPreviousUri("/rest/api/v1/familyTree/?pageNumber=" + (entities.getNumber() - 1) + "&pageSize=" +  entities.getSize());
        }

        return dto;
    }

    public static FamilyTree convert(FamilyTreeFullDTO dto) {
        FamilyTree entity = new FamilyTree();
        // raw properties
        entity.setName(dto.getName());

        return entity;
    }
    
}
