package org.demis.familh.dto;

import org.demis.familh.model.FamilyTree;

public class FamilyTreeDTOConverter {

    public static FamilyTreeDTO convert (FamilyTree entity) {
        FamilyTreeFullDTO dto = new FamilyTreeFullDTO();
        // Primary Key
        dto.setId(entity.getId());

        // raw properties
        dto.setName(entity.getName());
        dto.setUri("/rest/api/v1/family/" + entity.getId() );

        return dto;
    }
    
}
