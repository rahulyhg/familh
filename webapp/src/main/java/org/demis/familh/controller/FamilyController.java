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
    public Object getResource(@PathVariable("id") Integer id, HttpServletResponse httpResponse) {
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");

        FamilyTree familyTree = null;

        try {
            familyTree = service.getEager(id);
        } catch (Exception e) {
            logger.error("Internal Error", e);
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new APIError("INTERNAL_ERROR", "Internal error, see the application log files");
        }
        if (familyTree == null) {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return new APIError("RESOURCE_NOT_FOUND", "Resource #familyTree (" + id + ") not found");
        }

        return FamilyTreeDTOConverter.convert(familyTree);
    }

    @RequestMapping(value = {REST_API_V1_FAMILY_TREE + "/", REST_API_V1_FAMILY_TREE}, method = RequestMethod.GET)
    @ResponseBody
    public Object getCollection(@RequestParam(value="pageNumber", defaultValue="0") Integer pageNumber,
                                      @RequestParam(value="pageSize", defaultValue="10") Integer pageSize,
                                      HttpServletResponse httpResponse) {
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");

        FamilyTreePage page = null;

        try {
            page = service.getPage(pageNumber.intValue(), pageSize.intValue());
        } catch (Exception e) {
            logger.error("Internal Error", e);
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new APIError("INTERNAL_ERROR", "Internal error, see the application log files");
        }
        if (page == null) {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return new APIError("RESOURCE_NOT_FOUND", "Resources #familyTree (" + pageNumber + ", " + pageSize + ") not found");
        }
        else {
            httpResponse.setStatus(HttpStatus.OK.value());
            return FamilyTreeDTOConverter.convertPage(page);
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // POST
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @RequestMapping(value = {REST_API_V1_FAMILY_TREE + "/", REST_API_V1_FAMILY_TREE}, method = RequestMethod.POST)
    @ResponseBody
    public Object postCollection(@RequestBody FamilyTreeFullDTO dto, HttpServletResponse httpResponse) {
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");

        try {
            FamilyTree FamilyTree = FamilyTreeDTOConverter.convert(dto);
            service.save(FamilyTree);
            httpResponse.setStatus(HttpStatus.OK.value());
            FamilyTreeDTO FamilyTreeDTO = FamilyTreeDTOConverter.convert(FamilyTree);
            return FamilyTreeDTO;
        }
        catch (Exception e) {
            logger.error("Internal Error", e);
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new APIError("INTERNAL_ERROR", "Internal error, see the application log files");
        }
    }

    @RequestMapping(value = {REST_API_V1_FAMILY_TREE + "/{id}", REST_API_V1_FAMILY_TREE + "/{id}/"}, method = RequestMethod.POST)
    @ResponseBody
    public Object postResource(@PathVariable("id") Integer id, @RequestBody FamilyTreeFullDTO dto, HttpServletResponse httpResponse) {
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");

        httpResponse.setStatus(HttpStatus.FORBIDDEN.value());
        return new APIError("METHOD_NOT_ALLOWED", "This method is not allowed to modify a resource, if you want to modify this resource use the HTTP PUT method");
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // DELETE
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @RequestMapping(value = {REST_API_V1_FAMILY_TREE + "/{id}", REST_API_V1_FAMILY_TREE + "/{id}/"}, method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteResource(@PathVariable("id") Integer id, HttpServletResponse httpResponse) {

        try {
            FamilyTree familyTree = service.getEager(id);

            if (familyTree == null) {
                httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
                return new APIError("RESOURCE_NOT_FOUND", "Resource #familyTree (\" + id + \") not found, or already deleted");
            }
            service.delete(familyTree);
            httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
            return null;
        }
        catch (Exception e) {
            logger.error("Internal Error", e);
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new APIError("INTERNAL_ERROR", "Internal error, see the application log files");
        }
    }

    @RequestMapping(value = {REST_API_V1_FAMILY_TREE + "/", REST_API_V1_FAMILY_TREE}, method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteCollection(HttpServletResponse httpResponse) {
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");

        httpResponse.setStatus(HttpStatus.FORBIDDEN.value());
        return new APIError("METHOD_NOT_ALLOWED", "This method is not allowed, you can't delete all familyTrees");
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // PUT
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @RequestMapping(value = {REST_API_V1_FAMILY_TREE + "/{id}", REST_API_V1_FAMILY_TREE + "/{id}/"}, method = RequestMethod.PUT)
    @ResponseBody
    public Object putResource(@PathVariable("id") Integer id, @RequestBody FamilyTreeFullDTO dto, HttpServletResponse httpResponse) {
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");

        try {
            FamilyTree familyTree = FamilyTreeDTOConverter.convert(dto);
            familyTree.setId(id);
            service.save(familyTree);
            httpResponse.setStatus(HttpStatus.OK.value());
            FamilyTreeDTO FamilyTreeDTO = FamilyTreeDTOConverter.convert(familyTree);
            return FamilyTreeDTO;
        }
        catch (Exception e) {
            logger.error("Internal Error", e);
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new APIError("INTERNAL_ERROR", "Internal error, see the application log files");
        }
    }

    @RequestMapping(value = {REST_API_V1_FAMILY_TREE + "/", REST_API_V1_FAMILY_TREE}, method = RequestMethod.PUT)
    @ResponseBody
    public Object putCollection(HttpServletResponse httpResponse) {
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");

        httpResponse.setStatus(HttpStatus.FORBIDDEN.value());
        return new APIError("METHOD_NOT_ALLOWED", "This method is not allowed, you can't update all familyTrees");
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // HEAD
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @RequestMapping(value = {REST_API_V1_FAMILY_TREE + "/{id}", REST_API_V1_FAMILY_TREE + "/{id}/"}, method = RequestMethod.HEAD)
    @ResponseBody
    public Object headResource(@PathVariable("id") Integer id, HttpServletResponse httpResponse) {
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");

        FamilyTree familyTree = null;

        try {
            familyTree = service.getEager(id);
        } catch (Exception e) {
            logger.error("Internal Error", e);
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new APIError("INTERNAL_ERROR", "Internal error, see the application log files");
        }
        if (familyTree == null) {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return new APIError("RESOURCE_NOT_FOUND", "Resource #familyTree (" + id + ") not found");
        }

        return null;
    }

    @RequestMapping(value = {REST_API_V1_FAMILY_TREE + "/", REST_API_V1_FAMILY_TREE}, method = RequestMethod.HEAD)
    @ResponseBody
    public Object headCollection(@RequestParam(value="pageNumber", defaultValue="0") Integer pageNumber,
                                    @RequestParam(value="pageSize", defaultValue="10") Integer pageSize,
                                    HttpServletResponse httpResponse) {
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");

        FamilyTreePage page = null;

        try {
            page = service.getPage(pageNumber.intValue(), pageSize.intValue());
        } catch (Exception e) {
            logger.error("Internal Error", e);
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new APIError("INTERNAL_ERROR", "Internal error, see the application log files");
        }
        if (page == null) {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return new APIError("RESOURCE_NOT_FOUND", "Resources #familyTree (" + pageNumber + ", " + pageSize + ") not found");
        }
        else {
            httpResponse.setStatus(HttpStatus.OK.value());
            return null;
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // PATCH
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @RequestMapping(value = {REST_API_V1_FAMILY_TREE + "/", REST_API_V1_FAMILY_TREE}, method = RequestMethod.PATCH)
    @ResponseBody
    public Object patchCollection(HttpServletResponse httpResponse) {
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");

        httpResponse.setStatus(HttpStatus.FORBIDDEN.value());
        return new APIError("METHOD_NOT_ALLOWED", "This method is not supported yet");
    }

    @RequestMapping(value = {REST_API_V1_FAMILY_TREE + "/{id}", REST_API_V1_FAMILY_TREE + "/{id}/"}, method = RequestMethod.PATCH)
    @ResponseBody
    public Object patchResource(HttpServletResponse httpResponse) {
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");

        httpResponse.setStatus(HttpStatus.FORBIDDEN.value());
        return new APIError("METHOD_NOT_ALLOWED", "This method is not supported yet");
    }

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
