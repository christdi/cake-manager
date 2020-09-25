package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.model.CakeModel;
import com.waracle.cakemgr.repository.CakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/cakes")
public class CakeController {

    private CakeRepository cakeRepository;

    @Autowired
    public CakeController(CakeRepository cakeRepository) {
        this.cakeRepository = cakeRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<CakeModel> retrieveAllCakes() {
        return cakeRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void addCake(@RequestBody CakeModel cake) {
        Objects.requireNonNull(cake);

        cakeRepository.save(cake);
    }
}
