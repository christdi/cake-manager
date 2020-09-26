package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.controller.exception.BadRequestException;
import com.waracle.cakemgr.model.CakeModel;
import com.waracle.cakemgr.repository.CakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/cakes")
@CrossOrigin
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
    public void addCake(@RequestBody @Valid CakeModel cake) {
        Objects.requireNonNull(cake);

        if (cakeRepository.existsByTitle(cake.getTitle())) {
            throw new BadRequestException("Cake with that title already exists");
        }

        cakeRepository.save(cake);
    }
}
