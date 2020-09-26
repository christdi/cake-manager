package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.model.CakeModel;
import com.waracle.cakemgr.repository.CakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class WebAppController {

    @Autowired
    private CakeRepository cakeRepository;

    @GetMapping("/")
    public String viewCakes(Model model) {
        model.addAttribute("cakes", cakeRepository.findAll());

        return "index";
    }

    @GetMapping("/add")
    public String addCake(Model model) {
        model.addAttribute("cake", new CakeModel());

        return "add";
    }

    @PostMapping("/add")
    public String addCakeSubmit(@Valid @ModelAttribute("cake") CakeModel cake, BindingResult bindingResult, Model model) {
        model.addAttribute("cake", cake);

        if (bindingResult.hasErrors()) {
            return "add";
        }

        if (cakeRepository.existsByTitle(cake.getTitle())) {
            bindingResult.rejectValue("title", "error.user", "must be unique");

            return "add";
        }

        cakeRepository.save(cake);

        return "redirect:/";
    }
}
