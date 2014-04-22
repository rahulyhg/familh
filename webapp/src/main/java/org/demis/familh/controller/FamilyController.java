package org.demis.familh.controller;

import org.demis.familh.dto.FamilyTreeDTO;
import org.demis.familh.dto.FamilyTreeDTOConverter;
import org.demis.familh.dto.FamilyTreeFullDTO;
import org.demis.familh.model.FamilyTree;
import org.demis.familh.service.FamilyTreePage;
import org.demis.familh.service.FamilyTreeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletResponse;

@Controller
public class FamilyController {

    public static final String REST_API_V1_FAMILY_TREE = "/rest/api/v1/familyTree";
    private final Logger logger = LoggerFactory.getLogger(FamilyController.class);

    @Autowired
    private FamilyTreeService service;

    @Autowired
    private View jsonView;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // GET
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @RequestMapping(value = {REST_API_V1_FAMILY_TREE + "/{id}", REST_API_V1_FAMILY_TREE + "/{id}/"}, method = RequestMethod.GET)
    @ResponseBody
    public Object getFamily(@PathVariable("id") Integer id, HttpServletResponse httpResponse) {
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");

        FamilyTree familyTree = null;

        try {
            familyTree = service.getEager(id);
        } catch (Exception e) {
            logger.error("Internal Error", e);
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new APIError("INTERNAL_ERROR", "Internal error");
        }
        if (familyTree == null) {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return new APIError("RESOURCE_NOT_FOUND", "Resource #familyTree(" + id + ") not found");
        }

        return FamilyTreeDTOConverter.convert(familyTree);
    }

    @RequestMapping(value = {REST_API_V1_FAMILY_TREE + "/", REST_API_V1_FAMILY_TREE}, method = RequestMethod.GET)
    @ResponseBody
    public Object getFamilyTreePage(@RequestParam(value="pageNumber", defaultValue="0") Integer pageNumber,
                                      @RequestParam(value="pageSize", defaultValue="10") Integer pageSize,
                                      HttpServletResponse httpResponse) {
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");

        FamilyTreePage page = service.getPage(pageNumber.intValue(), pageSize.intValue());

        httpResponse.setStatus(HttpStatus.OK.value());
        return FamilyTreeDTOConverter.convertPage(page);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // POST
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @RequestMapping(value = {REST_API_V1_FAMILY_TREE + "/", REST_API_V1_FAMILY_TREE}, method = RequestMethod.POST)
    @ResponseBody
    public Object createFamilyTree(@RequestBody FamilyTreeFullDTO dto, HttpServletResponse httpResponse) {
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");

        FamilyTree FamilyTree = FamilyTreeDTOConverter.convert(dto);
        service.save(FamilyTree);
        httpResponse.setStatus(HttpStatus.OK.value());
        FamilyTreeDTO FamilyTreeDTO = FamilyTreeDTOConverter.convert(FamilyTree);
        return FamilyTreeDTO;
    }

    @RequestMapping(value = {REST_API_V1_FAMILY_TREE + "/{id}", REST_API_V1_FAMILY_TREE + "/{id}/"}, method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteFamilyTree(@PathVariable("id") Integer id, HttpServletResponse httpResponse) {
        FamilyTree familyTree = service.getEager(id);
        service.delete(familyTree);
        httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
        return null;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // DELETE
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @RequestMapping(value = {REST_API_V1_FAMILY_TREE + "/", REST_API_V1_FAMILY_TREE}, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Object deleteAllFamilyTree(HttpServletResponse httpResponse) {
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
        return null;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // PUT
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // HEAD
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // PATCH
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // OPTIONS
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @RequestMapping(value = {REST_API_V1_FAMILY_TREE + "/{id}", REST_API_V1_FAMILY_TREE + "/{id}/"}, method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object optionResource(HttpServletResponse httpResponse) {
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpResponse.setHeader("Allow", "GET,PUT,DELETE,HEAD,OPTIONS");
        return null;
    }

    @RequestMapping(value = {REST_API_V1_FAMILY_TREE + "/", REST_API_V1_FAMILY_TREE}, method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object optionCollection(HttpServletResponse httpResponse) {
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpResponse.setHeader("Allow", "GET,POST,HEAD,OPTIONS");
        return null;
    }
}
