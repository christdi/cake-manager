package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.model.CakeModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cakes")
public class CakeController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<CakeModel> retrieveAllCakes() {
        List<CakeModel> cakes = new ArrayList<>();

        return cakes;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void addCake(@RequestBody CakeModel cake) {
    }
}
