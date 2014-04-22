package org.demis.familh.controller;

import org.demis.familh.dto.FamilyTreeDTO;
import org.demis.familh.dto.FamilyTreeDTOConverter;
import org.demis.familh.model.FamilyTree;
import org.demis.familh.service.FamilyTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletResponse;

@Controller
public class FamilyController {

    @Autowired
    private FamilyTreeService service;

    @Autowired
    private View jsonView;

    @RequestMapping(value = {"/rest/api/v1/family/{id}", "/rest/api/v1/family/{id}/"}, method = RequestMethod.GET)
    @ResponseBody
    public Object getFamily(@PathVariable("id") Integer id, HttpServletResponse httpResponse) {
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");

        FamilyTree familyTree = service.getEager(id);

        return FamilyTreeDTOConverter.convert(familyTree);
    }
}
