package com.iviberberi.sfgpetclinic.controllers;

import com.iviberberi.sfgpetclinic.fauxspring.Model;
import com.iviberberi.sfgpetclinic.services.VetService;

public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    public String listVets(Model model){

        model.addAttribute("vets", vetService.findAll());

        return "vets/index";
    }
}
