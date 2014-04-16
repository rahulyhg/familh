package org.demis.familh.controller;

import org.demis.familh.model.FamilyTree;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
public class FamilyController {

    @RequestMapping(value = {"/rest/api/v1/family/{id}", "/rest/api/v1/family/{id}/"}, method = RequestMethod.GET)
    @ResponseBody
    public Object getFamily(@PathVariable("id") Integer id, HttpServletResponse httpResponse) {
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");

        return new FamilyTree();
    }
}
